package refmeister.entity;

import java.io.*;
import java.nio.file.*;


public class Controller {

	private Editable selected;
	private Library currentLib;
	private WorkingDirectory workingDir;
	private File libFile;

	public Controller(WorkingDirectory workingDir) {
	    this.workingDir = workingDir;
    }

	public void saveLibrary() {
		if(libFile == null) {
		    try {
		        libFile = new File(workingDir.getDirectory() + currentLib.getTitle());
            } catch(NullPointerException e) {
                System.out.println("No Library title. This should not be possible.");
            }
        }
        Path file = Paths.get(workingDir.getDirectory() + currentLib.getTitle());
        try(BufferedWriter writer = Files.newBufferedWriter(file)) {
		    writer.write(currentLib.getSaveString());
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
	}

	public void loadLibrary(String title) {
	    for(File f : workingDir.getDirectory().listFiles()) {
	        if(f.toString().equals(title)) {
	            loadLibrary(f);
	            return;
            }
        }
    }

	/**
	 * 
	 * @param file
	 */
	public void loadLibrary(File file) {
        BufferedReader reader = null;
	    try {
            reader = new BufferedReader(new FileReader(libFile));
        } catch(FileNotFoundException fnfe) {
            System.out.println("File Not Found. This should not be possible.");
        }

        parseXML(reader);
	}

	public void parseXML(BufferedReader reader) {


        Library loadedLib = new Library(libFile.getName());

        // TODO: Write XML parser

        this.currentLib = loadedLib;

    }

    public void createLibrary(String title, String description) {
	    File file = new File(workingDir.getDirectory().getPath() + title + ".rl");
	    currentLib = new Library(title, description);
	    saveLibrary();
	    selected = currentLib;
    }

    public void edit(String[] edits) {
	    selected.edit(edits);
    }

}