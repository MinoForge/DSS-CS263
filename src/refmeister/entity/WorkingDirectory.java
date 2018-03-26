package refmeister.entity;

import com.sun.corba.se.impl.io.TypeMismatchException;

import java.io.File;
import java.nio.file.AccessDeniedException;

public class WorkingDirectory implements Displayable{

	private File workingDir;

	public WorkingDirectory(File dir) {
		try {
			setDirectory(dir);
		} catch(TypeMismatchException | AccessDeniedException e) {
			System.out.println(e.getMessage());
		}
    }

	public File getDirectory() {
		return workingDir;
	}

	/**
	 * 
	 * @param file
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