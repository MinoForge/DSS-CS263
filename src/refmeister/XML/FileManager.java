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
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Manages saving on a seperate thread.
 * @author Wesley Rogers
 * @version 20 April, 2018
 */
public final class FileManager {
    /**
     * The singular instance of the FileManager
     */
    private static FileManager instance;

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
    private final Lock libraryLock;

    /**
     * The lock for the l
     */
    private final Lock loggerLock;

    private ScheduledFuture<?> autosave;
    private final ScheduledThreadPoolExecutor executor;

    private FileManager(){
        libraryLock = new ReentrantLock();
        loggerLock = new ReentrantLock();
        this.executor = new ScheduledThreadPoolExecutor(2);
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
        try {
            loggerLock.lock();
            File autosave = new File(directory.getDirectory(), "refmeister.log");
            FileWriter fw = new FileWriter(autosave, true);
            String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
            fw.append(String.format("[%s][%s] %s\n", timestamp, s.toString(), msg));
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            loggerLock.unlock();
        }
    }

    /**
     * Saves the library with the given name.
     * @param s the file name
     */
    private void saveWithName(String s){
        try {
            libraryLock.lock();
            XMLManager man = new XMLManager(library);
            File autosave = new File(directory.getDirectory(), s);
            FileWriter fw = new FileWriter(autosave);
            String xml = man.getXML();
            fw.write(xml);
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
    public void start(boolean autosave){
        if(autosave) {
            if(this.autosave != null){
                this.autosave.cancel(false);
            }
            this.autosave = this.executor.schedule(() -> {
                this.saveWithName(fileName + "-autosave.rl");
            }, 30, TimeUnit.SECONDS);
        }
        log(Severity.DEBUG, "Application started with autosave=" + autosave);
        executor.setRemoveOnCancelPolicy(true);
    }

    /**
     * Saves the given library to the disk.
     * @param l the library to save
     */
    public synchronized void save(Library l){
        try {
            this.libraryLock.lock();
            this.library = l;
            this.executor.execute(() -> saveWithName(this.fileName + ".rl"));
        } finally {
            this.libraryLock.unlock();
        }
    }

    /**
     * Stops the file saving thread.
     */
    public synchronized void stop(){
        try {
            this.log(Severity.DEBUG, "Application stopped");

            if(autosave != null) {
                this.executor.execute(()->this.saveWithName(fileName + "-autosave.rl"));
                autosave.cancel(false);
            }

            this.executor.shutdown();
            System.out.println(executor.getQueue());
            if(!this.executor.awaitTermination(30, TimeUnit.SECONDS)){
                System.err.println("Forcing Shutdown");
                this.executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            System.err.println("Failed to terminate!");
            this.executor.shutdownNow();
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
        this.executor.submit(() -> writeError(severity, msg));
    }

    /**
     * Logs an exception thrown.
     * @param severity  the severity of the exception
     * @param e         the exception
     */
    public void log(Severity severity, Throwable e){
        StringBuilder s = new StringBuilder(e.toString());
        for(StackTraceElement st : e.getStackTrace()){
            s.append("\n\t");
            s.append(st.toString());
        }
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

        XMLParser p = new XMLParser();
        libraryLock.lock();
        try(Scanner input = new Scanner(file)){
            StringBuilder b = new StringBuilder();
            input.useDelimiter("\n");
            input.forEachRemaining(s -> {
                b.append(s);
                b.append("\n");
            });
            String xml = b.toString();
            this.library = p.loadLibrary(xml);
        } catch (FileNotFoundException|MalformedXMLException e) {
            log(Severity.MINOR_ERROR, e);
            out = false;
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
     * Deletes the autosave and regular save file for the current file.
     */
    public void deleteFile(){
        executor.submit(() -> {
            loggerLock.lock();
            try {
                File save = new File(directory.getDirectory(), fileName + ".rl");
                File autosave = new File(directory.getDirectory(), fileName + "-autosave.rl");
                if(!save.delete())
                    log(Severity.MINOR_ERROR, "Failed deleting file: " + fileName + ".rl");
                if(autosave.delete())
                    log(Severity.MINOR_ERROR, "Failed deleting file: " + fileName + "-autosave.rl");
            } catch (SecurityException e){
                log(Severity.MAJOR_ERROR, e);
            } finally {
                loggerLock.unlock();
            }
        });
    }

    /**
     * The severity of a given error.
     */
    public enum Severity {
        /**
         * Severity level indicating debug logs messages, like method entries, etc.
         */
        DEBUG("[DEBUG]"),

        /**
         * Used for basic logging messages.
         */
        LOG("[LOG]"),

        /**
         * Used for minor, recoverable exceptions. These should be addressed, but the method can
         * still succeed in doing it's function.
         */
        MINOR_ERROR("[MINOR ERROR]"),

        /**
         * Used for major errors, such as a library failing to load. This severity level
         * indicates a nonrecoverable error, as such the method should not return a "success"
         * result under any circumstances.
         */
        MAJOR_ERROR("[MAJOR ERROR]");

        /**
         * The log name of the severity type
         */
        private final String name;

        Severity(String s){
            this.name = s;
        }

        @Override
        public String toString() {
            return name;
        }
    }

}
