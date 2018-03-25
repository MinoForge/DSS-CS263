package refmeister.entity;

import java.io.File;

public class Controller {

	private Editable selected;
	private Library currentLib;
	private WorkingDirectory workingDir;
	private File libFile;

	public void saveLibrary() {
		if(libFile == null) {
		    try {
		        libFile = new File(workingDir.getPath()
            }
        }
	    currentLib.getSaveString();

		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param file
	 */
	public void loadLibrary(File file) {
		// TODO - implement Controller.loadLibrary
		throw new UnsupportedOperationException();
	}

	public Editable traverseUp() {

    }

}