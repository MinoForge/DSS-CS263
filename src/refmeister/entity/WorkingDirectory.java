package refmeister.entity;

import com.sun.corba.se.impl.io.TypeMismatchException;
import refmeister.entity.Interfaces.Displayable;

import java.io.File;
import java.nio.file.AccessDeniedException;

/**
 * The WorkingDirectory class models the directory that a library will be added, modified, and
 * removed from.
 *
 * @author Peter Gardner
 * @version 25, 3, 2018
 */
public class WorkingDirectory implements Displayable {

    /** The directory that is to be set as our directory for a library. */
	private File workingDir;

    /**
     * Constructor for a WorkingDirectory object. Tries to set the specified file as a directory,
     * but catches any exceptions that can occur.
     * @param dir The specified file to attempt to set to a directory.
     */
	public WorkingDirectory(File dir) {
		try {
			setDirectory(dir);
		} catch(TypeMismatchException | AccessDeniedException e) {
			System.out.println(e.getMessage());
		}
    }

    /**
     * Retrieves the File specified as the working directory.
     * @return The File specified as the working directory.
     */
	public File getDirectory() {
		return workingDir;
	}

	/**
	 * Sets the File specified to be the new working directory.
	 * @param file The file specified to be the new working directory.
	 */
	public void setDirectory(File file) throws TypeMismatchException, AccessDeniedException {
	    if(!file.isDirectory()) {
	        throw new TypeMismatchException("Must select a Directory.");
        }
        if(!file.canRead()) {
	    	throw new AccessDeniedException("Cannot read from Directory.");
		}
		if(!file.canWrite()) {
	    	throw new AccessDeniedException("Cannot write from Directory.");
		}
		workingDir = file;
	}

    @Override
    public String getAttribute(String attribute) {
        return null;
    }

    @Override
    public void setAttribute(String attribute, String contents) {

    }
}