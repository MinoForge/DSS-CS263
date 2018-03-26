package refmeister.entity;

import refmeister.XML.SaveSystem;

import java.io.*;
import java.nio.file.*;
import java.util.Scanner;

/**
 * The Controller class controls the flow of the program, saving and loading a full library, and
 * allows the user to communicate with the program using a menu.
 * @author Peter Gardner
 * @version 25, 3, 2018
 */
public class Controller {

    /** The current object the controller is pointing to. */
	private Editable selected;
	/** The current library that selected is/located in. */
	private Library currentLib;
	/** The current working directory for this controller. */
	private WorkingDirectory workingDir;
	/** The File that a library is stored in. */
	private File libFile;

    /**
     * Constructor for the Controller class. Sets a specified WorkingDirectory to the workingDir
     * field.
     * @param workingDir The specified WorkingDirectory to be set to workingDir.
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

        String xml = SaveSystem.saveLibrary(currentLib);
        try (FileWriter fileWriter = new FileWriter(libFile)) {
            fileWriter.write(xml);
        } catch (IOException e) {
            System.out.println("File is not writable! Please change your file name/directory.");
        }
    }

    /**
     * Loads a library from a specified title.
     * @param title The specified title to load from.
     */
	public void loadLibrary(String title) {
        loadLibrary(new File(title));
    }

	/**
	 * Loads a library from a specified file.
	 * @param file The specified file to load from.
	 */
	public void loadLibrary(File file) {
	    String xml = null;
        try {
            Scanner fileReader = new Scanner(file);
            fileReader.useDelimiter("\\Z");
            xml = fileReader.next();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        this.currentLib = SaveSystem.loadLibrary(xml);
	}

	public void startUp() {
	    //TODO: Make this functional to traverse folders and make libraries with user titles and
        //TODO: descriptions.
	    createLibrary("Test", "TestDescription");
	    if(selected == null) {
	        startUp();
        }
    }


    /**
     * Prints out a command-line menu that displays options based on what array is passed.
     * Then call menuChoose to allow a user to select a menu option.
     */
    public void displayMenu() {
        String[] menuItems = selected.display();
	    int i;
	    for(i = 0; menuItems[i] != null; i++) {
            System.out.println(menuItems[i]);
        }
        String[] choices = new String[selected.getChildren().size()];
        int j;
	    for(j = 0; i < menuItems.length; i++, j++) {
            choices[j] = (menuItems[i]);
            System.out.println(j + ": " + menuItems[i]);
        }
        menuChoose(j, choices);
    }

    /**
     * Creates a new library with a specified title and description.
     * @param title The specified title for the new library.
     * @param description The specified description for the new library.
     */
    public void createLibrary(String title, String description) {
        libFile = new File(workingDir.getDirectory().getPath() + title + ".rl");
        currentLib = new Library(title, description);
        saveLibrary();
        selected = currentLib;
    }

    /**
     * Allows a user to select a menu option based on what selected is. The only way to access
     * this method is through the displayMenu() method.
     * @param maxChoice The last choice on a user's menu.
     * @param choices An array of all of the choices you have.
     */
    private void menuChoose(int maxChoice, String[] choices) {
        Scanner scanIn = new Scanner(System.in);
        boolean goodChoice = false;
        while(!goodChoice) {
            System.out.print("Choose your Answer: ");
            if (scanIn.hasNextInt()) {
                int choice = scanIn.nextInt();
                if (choice < maxChoice && choice >= 0) {
                    for(Editable e : selected.getChildren()) {
                        if(e.getTitle().equals(choices[choice])) {
                            setSelected(e);
                        }
                    }
                    goodChoice = true;
                }
            } else if(scanIn.hasNext()) {
                String choiceString = scanIn.next();
                if(choiceString.equals("e")) {
                    editMenu();
                } else if(choiceString.equals("u")) {
                    traverseUp();
                } else if(choiceString.equals("c")) {
                    createChild();
                }
            }else {
                System.out.println("Error: Choice must be in range [0-" + (maxChoice - 1) + "]");

            }
        }
    }

    public void createChild() {

    }

    public void traverseUp() {
        if(selected.getParent() != null) {
            setSelected(selected.getParent());
        }
    }

    public Editable getSelected() {
        return selected;
    }

    public void setSelected(Editable newSelect) {
        this.selected = newSelect;
    }

    public void setWorkingDir(WorkingDirectory workingDir) {
        this.workingDir = workingDir;
    }

    /**
     * Allows a user to edit a selected object.
     */
    public void editMenu() {
        String[] edits = {selected.getTitle(), selected.getDescription()};
	    Scanner scanIn = new Scanner(System.in);
        System.out.println("0: Edit Title\n1: Edit Description\n2: Go Back");
        System.out.println("Please choose a number: ");
        while(true) {
            if(scanIn.hasNextInt()) {
                int choice = scanIn.nextInt();
                switch(choice) {
                    case 0:
                        System.out.print("New Title: ");
                        edits[0] = scanIn.nextLine();
                        selected.edit(edits);
                        return;
                    case 1:
                        System.out.print("New Description: "); //No line breaks, currently.
                        edits[1] = scanIn.nextLine();
                        selected.edit(edits);
                        return;
                    case 2:
                        return;
                    default:

                        break;
                }
            }
            System.out.println("Please choose an integer in the range [0-2]");
        }
    }

}