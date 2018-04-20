package refmeister.controllers;

import refmeister.XML.FileManager;
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

    /**
     * Returns a list of options for the display that is currently selected.
     * @return a list of options for the display that is currently selected.
     */
    @Override
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

    /**
     * Sets the attribute of an entity to a string passed in.
     * @param attrTitle a string to specify what attribute we're editing.
     * @param attrValue the string to set our attribute to.
     */
    @Override
    public void editAttribute(String attrTitle, String attrValue) {
        edSelected.setAttribute(attrTitle, attrValue);
    }

    /**
     * Returns an array of strings that contain the titles of the entities.
     * @return an array of strings that contain the titles of the entities.
     */
    @Override
    public String[] getAttributeTitles() {
	    if(edSelected != null) {
            return edSelected.listAttributeTitles().toArray(new String[0]);
        }
        return null;
    }

    /**
     * Returns an array of strings that contain the actual values of the attributes.
     * @return an array of strings that contain the actual values of the attributes.
     */
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
    public void deleteRoot() {
        FileManager.getInstance().deleteFile();
        viewDir();
    }

    /**
     * Calls helper functions to delete the currently selected entity.
     */
    public void delete() {
        if(selected.getParent() == null) {
            deleteRoot();
        } else {
            sendFunc("delete", null);
            setSelected(selected.getParent());
        }
    }

    /**
     * Sends user input to our controller.
     * @param func the tags which specify the type of input.
     * @param param all the information that the user sends in.
     */
    public void sendFunc(String func, String[] param) {
        switch (func) {
            case "select":
                for(Entity e: selected.getEntityChildren()) {
                    if(e.getTitle().equals(param[0])) {
                        setSelected(e);
                    }
                }
            break;
            case "delete":
                Entity temp = selected;
                traverseUp();
                temp.delete();
                break;
            case "sort":
                selected.sort(param[0]);
                break;
            case "rate"://TODO could replace with a call to arg.changeRating
                if(selected instanceof Relatable) {
                    Relatable rel = (Relatable)selected;
                    List<Relation> listRelations = rel.getRelations();
                    listRelations.removeIf(o -> !(o instanceof RatedRelation));
                    listRelations.removeIf(o -> !(o.getEntity().getTitle().equals(param[1])));
                    if(!listRelations.isEmpty()){
                        RatedRelation r = (RatedRelation) listRelations.get(0);
                        r.setRating(Float.parseFloat(param[0]));
                    }
                }
                break;
            case "add":
                Entity ent = edSelected.createChild(param[0], param[1]);
                setSelected(ent);
                break;
            case "addA":
                ent = null;
                if(selected instanceof Reference) {//hard-code
                    Reference rel = (Reference)selected;
                    ent = (rel.createIdea(param[0], param[1]));
                }
                setSelected(ent);
                break;
            case "addI":
                ent = null;
                if(selected instanceof Reference) {//hard-code
                    Reference rel = (Reference)selected;
                    ent = (rel.createIdea(param[0], param[1]));
                }
                setSelected(ent);
                break;
            case "edit":
                for(int i = 0; i < param.length; i++) {
                    editAttribute(param[i], param[++i]);
                }
//                edSelected.setTitle(param[0]);
//                edSelected.setDescription(param[1]);
                break;
            case "moveTheme":
                temp = selected;
                traverseUp();
                traverseUp();
                List<Entity> topics = selected.getEntityChildren();
                for(Entity e: topics) {
                    if(e.getTitle().equals(param[0])) {
                        e.registerChild(temp);
                    }
                }
                setSelected(temp);
                break;
            case "change":
                System.out.println("Not implemented yet. Will be able to reassign entities which are related," +
                        "to be related to other entities.");
                break;//TODO
            case "generate":
                System.out.println("Not implemented yet.");
                break; //TODO


        }
    }

    /**
     * Sets currentLib to null, dispSelected to workingDir and edSelected to null.
     */
    public void viewDir() {
        currentLib = null;
        dispSelected = workingDir;
        edSelected = null;
    }

    /**
     * Returns a list of rated relatables.
     * @return a list of rated relatables.
     */
    @Override
    public List<Relatable> getRatedRelatables() {
        if(selected instanceof Relatable) {
            Relatable tempRelatable = (Relatable)selected;
            List<Relatable> result = new ArrayList<Relatable>();
            for(Relation r: tempRelatable.getRelations()) {
                result.add(r.getReference());
            }
            return result;
        }
        return null;
    }


    /**
     * Sets selected to an object's parent, if that object has a parent.
     */
    public void traverseUp() {
        if (selected.getParent() != null) {
            setSelected(selected.getParent());
        } else if(selected instanceof Relatable) {
            Relatable r = (Relatable)selected;
            for(Relation relate: r.getRelations().toArray(new Relation[0])) {
                Entity parent = relate.getEntity().getParent();
                if (parent != null) {
                    setSelected(parent);
                }
            }
        } else {
            System.out.println("Failed to move up: currently in: " + selected);
            System.out.println("Has parent: " + selected.getParent());
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

    /**
     * Returns a list of entities that are parents to a given entity.
     * @return a list of entities that are parents to a given entity.
     */
    public List<Entity> getParentEntities() {
        Entity temp = selected;
        traverseUp();
        traverseUp();
        List<Entity> list = selected.getEntityChildren();
        return list;
    }

    /**
     * Returns a list of strings that is returned from dispSelected's getFunc() method.
     * @return a list of strings that is returned from dispSelected's getFunc() method.
     */
    public List<String> getFuncs() {
        return dispSelected.getFunc();
    }

}