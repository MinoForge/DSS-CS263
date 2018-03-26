package refmeister.entity;

import java.io.*;
import java.nio.file.*;
import java.util.Scanner;

/**
 * TODO
 * @author Peter Gardner
 * @version 25, 3, 2018
 */
public class Controller {

    /** The current object the controller is pointing to. */
	private Editable selected;
	/** The current library that selected is/located in. */
	private Library currentLib;
	/**  */
	private WorkingDirectory workingDir;
	/**  */
	private File libFile;

    /**
     * TODO
     * @param workingDir
     */
	public Controller(WorkingDirectory workingDir) {
	    this.workingDir = workingDir;
    }

    /**
     * Saves the library and all of its children. If there is no specified libFile, then the
     * method creates a new one to save the current library to.
     */
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

    /**
     * TODO
     * @param title
     */
	public void loadLibrary(String title) {
	    if(workingDir.getDirectory().listFiles() != null) {
            for (File f : workingDir.getDirectory().listFiles()) {
                if (f.toString().equals(title)) {
                    loadLibrary(f);
                    return;
                }
            }
        }
    }

	/**
	 * TODO
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

    /**
     * TODO
     * @param reader
     */
	public void parseXML(BufferedReader reader) {


        Library loadedLib = new Library(libFile.getName());

        // TODO: Write XML parser

        this.currentLib = loadedLib;

    }

    /**
     * TODO
     */
    public void menu() {
	    String[] menuItems = selected.display();
	    int i = 0;
	    for(i = 0; menuItems[i] != null; i++) {
            System.out.println(menuItems[i]);
        }
        for(int j = 0; i < menuItems.length; i++, j++) {
            System.out.println(j + ": " + menuItems[i]);
        }
        Scanner scanIn = new Scanner(System.in);
	    scanIn.nextInt();
    }

    /**
     * TODO
     * @param title
     * @param description
     */
    public void createLibrary(String title, String description) {
	    File file = new File(workingDir.getDirectory().getPath() + title + ".rl");
	    currentLib = new Library(title, description);
	    saveLibrary();
	    selected = currentLib;
    }

    /**
     * TODO
     * @param edits
     */
    public void edit(String[] edits) {
	    selected.edit(edits);
    }

}