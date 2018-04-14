package refmeister.XML;

import refmeister.entity.Library;
import refmeister.entity.WorkingDirectory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Manages saving on a seperate thread.
 */
public enum SaveSystem{
    FILE_SYSTEM;

    private boolean alive;
    private Thread fileThread;
    private Library library;
    private WorkingDirectory directory;
    private String fileName;
    private Lock libraryLock;

    private SaveSystem(){
        alive = false;
        fileThread = new Thread(this::runLoop);
        libraryLock = new ReentrantLock();
        this.directory = new WorkingDirectory(new File("refmeister-wd"));
    }

    private void runLoop(){
        while(alive){
            boolean interrupted = false;
            try {
                Thread.sleep(300000);
            } catch (InterruptedException e) {
                interrupted = true;
            }

            if(interrupted && alive)
                saveWithName(fileName + ".rl");
            else
                saveWithName(fileName + "-autosave.rl");
        }

    }

    private void saveWithName(String s){
        libraryLock.lock();
        try {
            System.out.println("Saving...");
            XMLManager man = new XMLManager(library);
            File autosave = new File(directory.getDirectory(), s);
            FileWriter fw = new FileWriter(autosave);
            fw.write(man.getXML());
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            libraryLock.unlock();
        }
    }

    public void start(){
        this.alive = true;
        this.fileThread.start();
    }

    /**
     * Forces the save system to save immediately, if not already in the process.
     */
    public void forceSave(){
        fileThread.interrupt();
    }

    /**
     * Saves the given library to the disk.
     */
    public synchronized void save(Library l){
        try {
            this.libraryLock.lock();
            this.library = l;

        } finally {
            this.libraryLock.unlock();
        }
        forceSave();
    }

    public void stop(){
        this.libraryLock.lock();
        try {
            this.alive = false;
            this.fileThread.interrupt();
        } finally {
            this.libraryLock.unlock();
        }
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
