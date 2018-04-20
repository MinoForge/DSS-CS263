package refmeister.entity;

import refmeister.entity.Interfaces.Displayable;
import refmeister.management.RefMeisterExec;

import java.io.File;
import java.nio.file.AccessDeniedException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

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
	 * Default constructor. Tries to set the working directory to "./refmeister_saved"
     * @throws AccessDeniedException if default file is not writable.
	 */
	public WorkingDirectory() throws AccessDeniedException {
		this(Paths.get(RefMeisterExec.DEFAULT_DIRECTORY).toFile());
	}


    /**
     * Constructor for a WorkingDirectory object. Tries to set the specified file as a directory,
     * but catches any exceptions that can occur.
     * @param dir The specified file to attempt to set to a directory.
     * @throws AccessDeniedException if the file cannot be written to or read
     */
	public WorkingDirectory(File dir) throws AccessDeniedException {
        setDirectory(dir);
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
     * @throws AccessDeniedException if the file cannot be read or written to.
	 */
	public void setDirectory(File file) throws AccessDeniedException {
	    if(!file.isDirectory()) {
	        file.mkdir();
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

	@Override
	public List<String> listOptions() {
		List<String> options = new ArrayList<>();
//		if(workingDir.getParentFile().exists()) { TODO
//		    options.add("Parent Directory");
//        }
        File[] allFiles = workingDir.listFiles();
        for(File f: allFiles) {
		    if (f.isDirectory()) {
		        options.add(f.getName());
            }
            if (f.isFile() && f.getName().endsWith(".rl")) {
		        options.add(f.getName());
            }
        }

        options.add("Create Library");
        options.add("Quit");

        return options;
	}

	public List<String> getFuncs() {
		List<String> funcs = new ArrayList<String>();
		funcs.add("create");
		return funcs;
	}
}