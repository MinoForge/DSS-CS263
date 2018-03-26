package refmeister.entity;

import com.sun.corba.se.impl.io.TypeMismatchException;

import java.io.File;
import java.nio.file.AccessDeniedException;
import java.rmi.AccessException;

public class WorkingDirectory implements Displayable{

	private File workingDir;

	public WorkingDirectory(File dir) {
	    setDirectory(dir);
    }

	public File getDirectory() {
		return workingDir;
	}

	/**
	 * 
	 * @param path
	 */
	public void setDirectory(File file) throws TypeMismatchException, AccessDeniedException {
	    if(!file.isDirectory()) {
	        throw new TypeMismatchException("Must select a Directory.");
        }
        if(!file.canRead()) {
	    	throw new AccessDeniedException("Cannot read from Directory.");
		}
		if(!file.canWrite()) {
	    	throw new AccessDeniedException("")
		}
		workingDir = file;
	}

    @Override
    public String[] display() {
	    File[] list = workingDir.listFiles();
        String[] display = new String[1 + list.length];
        int i = 1;
        for(File f : list) {
            if(f.isDirectory() || f.getName().matches(".*\\.rl"))
            display[i] = f.getName();
            i++;
        }
        return display;
    }
}