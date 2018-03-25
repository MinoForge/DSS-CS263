package refmeister.entity;

import com.sun.corba.se.impl.io.TypeMismatchException;

import java.io.File;

public class WorkingDirectory implements Displayable{

	private File workingDir;

	public WorkingDirectory(File dir) {
	    this.workingDir = dir;
    }

	public File getDirectory() {
		return workingDir;
	}

	/**
	 * 
	 * @param path
	 */
	public void setDirectory(File file) throws TypeMismatchException {
	    if(!file.isDirectory()) {
	        throw new TypeMismatchException("Must select a Directory.");
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