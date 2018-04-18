package refmeister.XML;

import refmeister.entity.Library;
import refmeister.entity.WorkingDirectory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Manages saving on a seperate thread.
 */
public final class FileManager {
    /**
     * The singular instance of the FileManager
     */
    private static FileManager instance;

    /**
     * A boolean whether the save system should be alive and waiting for requests.
     */
    private boolean alive;

    /**
     * The thread managing file reading and library traversal.
     */
    private Thread fileThread;

    /**
     * The library being saved.
     */
    private Library library;

    /**
     * The current working directory.
     */
    private WorkingDirectory directory;

    /**
     * The left side of the file name. Thus the file name as saved will be fileName + ".rl"
     */
    private String fileName;

    /**
     * The libraryLock for managing access to the library. THIS SHOULD <b>ALWAYS</b> BE LOCKED
     * WHEN READING OR WRITING TO THE LIBRARY TO ENSURE VALID LIBRARY STATE WHEN SAVING.
     */
    private Lock libraryLock;

    /**
     *
     */
    private Lock loggerLock;

    /**
     * A deque of actions to run on the file manager thread.
     */
    private Deque<Runnable> actions;

    private FileManager(){
        alive = false;
        fileThread = new Thread(this::runLoop);
        libraryLock = new ReentrantLock();
        loggerLock = new ReentrantLock();
        this.actions = new ArrayDeque<>();
        this.fileName = "default";
        try {
            this.directory = new WorkingDirectory();
        } catch (AccessDeniedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Writes an error to the log file
     * @param s     the severity of the error
     * @param msg   the message to write
     */
    private void writeError(Severity s, String msg){
        libraryLock.lock();
        try {
            File autosave = new File(directory.getDirectory(), "refmeister.log");
            FileWriter fw = new FileWriter(autosave, true);
            String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
            fw.append(String.format("[%s][%s] %s\n", timestamp, s.toString(), msg));
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            libraryLock.unlock();
        }
    }

    /**
     * The run loop for the other thread.
     */
    private void runLoop(){
        while(alive){
            try {
                Thread.sleep(30000);
            } catch (InterruptedException e) {
                //save
            }

            for(Runnable r : this.actions){
                r.run();
            }

            libraryLock.lock();
            saveWithName(this.fileName + "-autosave.rl");
            libraryLock.unlock();

            this.actions.clear();
        }

    }

    /**
     * Saves the library with the given name.
     * @param s the file name
     */
    private void saveWithName(String s){
        libraryLock.lock();
        try {
            XMLManager man = new XMLManager(library);
            File autosave = new File(directory.getDirectory(), s);
            FileWriter fw = new FileWriter(autosave, true);
            fw.write(man.getXML());
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            libraryLock.unlock();
        }
    }

    /**
     * Starts teh file thread.
     */
    public void start(){
        this.alive = true;
        this.fileThread.start();
    }

    /**
     * Saves the given library to the disk.
     * @param l the library to save
     */
    public synchronized void save(Library l){
        try {
            this.libraryLock.lock();
            this.library = l;
            this.actions.addFirst(() -> saveWithName(this.fileName + ".rl"));
        } finally {
            this.libraryLock.unlock();
        }
    }

    /**
     * Stops the file saving thread.
     */
    public synchronized void stop(){
        this.libraryLock.lock();
        try {
            this.alive = false;
            this.fileThread.interrupt();
        } finally {
            this.libraryLock.unlock();
        }
    }

    /**
     * Sets the file name.
     * @param fileName  the file name
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Gets the file name
     * @return  the file name, without the extension
     */
    public String getFileName(){
        return fileName;
    }

    /**
     * Gets the instance
     * @return the instance
     */
    public static FileManager getInstance(){
        if(FileManager.instance == null)
            FileManager.instance = new FileManager();
        return FileManager.instance;
    }

    /**
     * Logs the message with the given severity.
     * @param severity  the severity of the message
     * @param msg       the message
     */
    public synchronized void log(Severity severity, String msg){
        this.loggerLock.lock();
        try{
            actions.addLast(() -> writeError(severity, msg));
        } finally {
            this.loggerLock.unlock();
        }
    }

    /**
     * Logs an exception thrown.
     * @param severity  the severity of the exception
     * @param e         the exception
     */
    public void log(Severity severity, Exception e){
        StringBuilder s = new StringBuilder(e.toString());
        for(StackTraceElement st : e.getStackTrace()){
            s.append("\n\t");
            s.append(st.toString());
        }
        System.out.println(s);
        log(severity, s.toString());
    }

    /**
     * Loads the given library on the calling thread, and forces the file thread to wait until
     * finished.
     * @param file  the file to load
     * @return      true if the library loaded correctly, false otherwise
     */
    public boolean load(File file){
        boolean out = true;
        libraryLock.lock();
        try{
            XMLParser p = new XMLParser();
            Scanner input;
            try{
                input = new Scanner(file);
            } catch (FileNotFoundException e) {
                log(Severity.MAJOR_ERROR, e);
                libraryLock.unlock();
                return false;
            }
            input.useDelimiter("\\Z");
            try {
                String xml = input.next();
                this.library = p.loadLibrary(xml);
            } catch (MalformedXMLException|NoSuchElementException e){
                log(Severity.MAJOR_ERROR, e);
                out = false;
            }
        } finally {
            libraryLock.unlock();
        }
        return out;
    }

    /**
     * Gets the library this File Manager is currently bound to. The library currently used by
     * the File Manager should <b>ALWAYS</b> be locked using using the provided lock when
     * editing <i>any</i> child of this library, to ensure that the library isn't
     * changed mid-save.
     * @return the library.
     */
    public Library getLibrary(){
        return library;
    }

    /**
     * The libraryLock for the library. This (reentrant) lock should be locked any time an edit
     * to the library is made. This prevents saving the library mid-change, and also allows
     * @return the library lock
     */
    public Lock getLibraryLock(){
        return libraryLock;
    }

    /**
     * The severity of a given error.
     */
    public enum Severity {
        DEBUG("[DEBUG]"),
        LOG("[LOG]"),
        MINOR_ERROR("[MINOR ERROR]"),
        MAJOR_ERROR("[MAJOR ERROR]");

        private String name;
        private Severity(String s){
            this.name = s;
        }

        @Override
        public String toString() {
            return name;
        }
    }

}
