package refmeister.controllers;

import refmeister.XML.FileManager;
import refmeister.XML.XMLParser;
import refmeister.entity.*;
import refmeister.entity.Interfaces.Displayable;
import refmeister.entity.Interfaces.Editable;
import refmeister.entity.Interfaces.Entity;

import java.io.*;
import java.util.List;
import java.util.Scanner;

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

	private Editable edSelected;

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

    /**
     * //TODO Wesley please do magic
     * @param choice The functionality being requested.
     * @return true if "quit" is the choice
     */
    public boolean functionality (String choice) {
        return false;
    }

    @Override
    public String[] getAttributeTitles() {
        return dispSelected.listAttributes().toArray(new String[0]);
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
        selected = currentLib;
    }


    /**
     * Sets selected to an object's parent, if that object has a parent.
     */
    public void traverseUp() {
        if(selected.getParent() != null) {
            setSelected(selected.getParent());
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