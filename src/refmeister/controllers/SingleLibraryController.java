package refmeister.controllers;

import refmeister.XML.FileManager;
import refmeister.XML.XMLParser;
import refmeister.entity.*;
import refmeister.entity.Interfaces.*;

import java.io.*;
import java.util.*;

/**
 * The Controller class controls the flow of the program, saving and loading a full library, and
 * allows the user to communicate with the program using a menu.
 * @author Peter Gardner
 * @version 25, 3, 2018
 */
public class SingleLibraryController implements Controller{

    /** The current object the controller is pointing to. */
	private Entity selected;
	/** The current library that selected is/located in. */
	private Library currentLib;
	/** The current working directory for this controller. */
	private WorkingDirectory workingDir;
	/** The File that a library is stored in. */
	private File libFile;

	/** The current object the controller is pointing to, typecast as an Editable. */
	private Editable edSelected;
    /** The current object the controller is pointing to, typecast as a Displayable. */
	private Displayable dispSelected;


    /**
     * Constructor for the Controller class. Sets a specified WorkingDirectory to the workingDir
     * field.
     * @param workingDir The specified WorkingDirectory to be set to workingDir.
     */
	public SingleLibraryController(WorkingDirectory workingDir) {
	    this.workingDir = workingDir;
	    this.dispSelected = workingDir;
    }

    @Override
    //TODO
    public List<String> displaySelected() {
        return dispSelected.listOptions();
    }




    /**
     * Saves the library and all of its children. If there is no specified libFile, then the
     * method creates a new one to save the current library to.
     */
	public void saveLibrary() {
        FileManager.getInstance().save(currentLib);
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
        boolean loadSuccess = FileManager.getInstance().load(file);
        if(loadSuccess) {
            currentLib = FileManager.getInstance().getLibrary();
        }
	}


    @Override
    public void editAttribute(String attrTitle, String attrValue) {
        edSelected.setAttribute(attrTitle, attrValue);
    }



    @Override
    public String[] getAttributeTitles() {
        return edSelected.listAttributeTitles().toArray(new String[0]);
    }


    @Override
    public String[] getAttributes() {
        return edSelected.listAttributes().toArray(new String[0]);
    }


    /**
     * Creates a new library with a specified title and description.
     * @param title The specified title for the new library.
     * @param description The specified description for the new library.
     */
    public void createLibrary(String title, String description) {
        currentLib = new Library(title, description);
        saveLibrary();
        setSelected(currentLib);
    }

    /**
     * Creates a nice default library, for testing.
     */
    public void createLibrary() {
        createLibrary("Default Library Title", "Default Library Description");
    }

    /**
     * Deletes a Library from memory and from the hard disk, and returns the view to the working
     * Directory.
     */
    public void deleteRoot() { //TODO Wesley please implement this. Want to nuke the library file.
//        FileManager.getInstance().deleteFile(); //TODO This line right here
        viewDir();
    }



    public void delete() {
        if(selected.getParent() == null) {
            deleteRoot();
        } else {
            sendFunc("delete", null);
            setSelected(selected.getParent());
        }
    }

    public void sendFunc(String func, String[] param) {
        switch (func) {
            case "delete": //TODO fix for relatables(Idea, Argument)
                selected.delete();
                break;
            case "sort":
                selected.sort(param[0]);
                break;
//            case "rate"://TODO fix for rating? Don't even know how to mess with
//                if(selected instanceof Relatable) {
//                    Relatable rel = (Relatable)selected;
//                }
            case "":

                break;
        }
    }

    public void viewDir() {
        currentLib = null;
        dispSelected = workingDir;
        edSelected = null;
    }


    /**
     * Sets selected to an object's parent, if that object has a parent.
     */
    public void traverseUp() {
        if(selected.getParent() != null) {
            setSelected(selected.getParent());
        } else {
            viewDir();
        }
    }

    /**
     * Retrieves the current selected field.
     * @return The current selected field.
     */
    public Entity getSelected() {
        return selected;
    }

    /**
     * Sets the selected field to a specified Editable.
     * @param newSelect The specified Editable to set to selected.
     */
    public void setSelected(Entity newSelect) {
        this.selected = newSelect;
        if(newSelect instanceof Editable) {
            edSelected = (Editable)newSelect;
        } else {
            edSelected = null;
        }
        if(newSelect instanceof Displayable) {
            dispSelected = (Displayable)newSelect;
        } else {
            dispSelected = null;
        }
    }

    /**
     * Sets the workingDir field to a specified WorkingDirectory.
     * @param workingDir The specified WorkingDirectory to assign to the field.
     */
    public void setWorkingDir(WorkingDirectory workingDir) {
        this.workingDir = workingDir;
    }



}