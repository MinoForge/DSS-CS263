package refmeister.entity;

import java.io.BufferedWriter;
import java.io.File;
import java.nio.file.*;


public class Controller {

	private Editable selected;
	private Library currentLib;
	private WorkingDirectory workingDir;
	private File libFile;

	public void saveLibrary() {
		if(libFile == null) {
		    try {
		        libFile = new File(workingDir.getPath() + currentLib.getTitle());
            } catch(NullPointerException e) {
                System.out.println("No Library title. This should not be possible.");
            }
        }
        Path file = Paths.get(workingDir.getPath() + currentLib.getTitle());
        try(BufferedWriter writer = Files.newBufferedWriter(file)) {
		    writer.write(currentLib.getSaveString());
        } catch {
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


}