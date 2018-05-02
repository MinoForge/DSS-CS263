package refmeister.controllers;

import refmeister.display.elements.Interfaces.RefSubject;
import refmeister.entity.Interfaces.Entity;
import refmeister.entity.Interfaces.Relatable;
import refmeister.entity.WorkingDirectory;

import java.io.File;
import java.util.List;

/**
 * Controller interface by which to build other controllers.
 * @author Brandon Townsend
 * @version 20 April, 2018
 */
public interface Controller extends RefSubject {

    /**
     * Returns a list of options for the display that is currently selected.
     * @return a list of options for the display that is currently selected.
     */
    List<String> displaySelected();

    /**
     * Saves the library and all of its children. If there is no specified libFile, then the
     * method creates a new one to save the current library to.
     */
    void saveLibrary();

    /**
     * Loads a library from a specified title.
     * @param title The specified title to load from.
     */
    void loadLibrary(String title);

    /**
     * Loads a library from a specified file.
     * @param file The specified file to load from.
     */
    boolean loadLibrary(File file);

    /**
     * Returns an array of strings that contain the titles of the entities.
     * @return an array of strings that contain the titles of the entities.
     */
    String[] getAttributeTitles();

    /**
     * Returns an array of strings that contain the actual values of the attributes.
     * @return an array of strings that contain the actual values of the attributes.
     */
    String[] getAttributes();

    /**
     * Creates a nice default library, for testing.
     */
    void createLibrary();

    /**
     * Creates a new library with a specified title and description.
     * @param title The specified title for the new library.
     * @param description The specified description for the new library.
     */
    void createLibrary(String title, String description);

    /**
     * Deletes a Library from memory and from the hard disk, and returns the view to the working
     * Directory.
     */
    void deleteRoot();

    /**
     * Sets selected to an object's parent, if that object has a parent.
     */
    void traverseUp();

    /**
     * Calls helper functions to delete the currently selected entity.
     */
    void delete();

    /**
     * Sets the attribute of an entity to a string passed in.
     * @param attrTitle a string to specify what attribute we're editing.
     * @param attrValue the string to set our attribute to.
     */
    void editAttribute(String attrTitle, String attrValue);

    /**
     * Sends user input to our controller.
     * @param funcName the tags which specify the type of input.
     * @param params all the information that the user sends in.
     */
    void sendFunc(String funcName, String... params);

    /**
     * Sets currentLib to null, dispSelected to workingDir and edSelected to null.
     */
    void viewDir();

    /**
     * Returns a list of rated relatables.
     * @return a list of rated relatables.
     */
    List<Relatable> getRatedRelatables();

    /**
     * Returns a list of entities that are parents to a given entity.
     * @return a list of entities that are parents to a given entity.
     */
    List<Entity> getParentEntities();

    /**
     * Returns a list of strings that is returned from dispSelected's getFunc() method.
     * @return a list of strings that is returned from dispSelected's getFunc() method.
     */
    List<String> getFuncs();

    WorkingDirectory getWorkingDirectory();

    Entity getSelected();

    List<Entity> getBranch();

    void setSelected(Entity e);
}
