package refmeister.entity;

import refmeister.XML.Saveable;
import refmeister.XML.XMLManager;
import refmeister.entity.Interfaces.Entity;
import refmeister.entity.Interfaces.Editable;

import java.util.ArrayList;
import java.util.List;

/**
 * The Library models a library which contains topics. It must have a
 * specified title.
 * @author Peter Gardner, Caleb Dinehart
 * @version 25, 3, 2018
 */

public class Library extends Editable {

    /** All references used in this Library. */
    private List<Entity> references;


    /**
     * Constructor for a Library object. Takes a String to be specified as the title, a String to
     * be specified as the description, and an ArrayList to be this Libraries topics.
     * @param title The String to be specified as this Library's title.
     * @param description The String to be specified as this Library's description.
     * @param topics The ArrayList which is the list of this Library's topics.
     */
    public Library(String title, String description, List<Entity> topics) {
        this.setTitle(title);
        this.setDescription(description);
        this.children = topics;
        this.setParent(null);
    }

    /**
     * A constructor for a Library object with a specified title and description, but with no
     * specified list of Topics.
     * @param title The String to be specified as this Library's title.
     * @param description The String to be specified as this Library's
     *                    description.
     */
    public Library(String title, String description) {
        this(title, description, new ArrayList<Entity>());
    }

    /**
     * A default constructor where the user only specifies that they want a title for their
     * Library. The description and list of Topics will be set to default values.
     * @param title The String to be specified as this Library's title.
     */
    public Library(String title) {
        this(title, "Unset Description", new ArrayList<Entity>());
    }

    /**
     * Default Constructor. At this time, we do not support creating a Library without a title.
     */
    public Library() {
        throw new UnsupportedOperationException("Must specify at least a title for this Library.");
    }

    /**
     * Returns a list of this library's XML children.
     * @return A list of all of this library's children
     */
    public List<Saveable> getSaveableChildren() {
        return new ArrayList<>(children);
    }

    /**
     * Retrieves the list of this Entity's children.
     * @return The list of this Entity's children.
     */
    public List<Entity> getEntityChildren() {
        return new ArrayList<>(children);
    }



    /**
     * Gets the XML String of this library, with all topics as it's children.
     * @return The formatted XML save string.
     * @param manager the XML manager to use.
     */
    public String getSaveString(XMLManager manager) {
        return super.getSaveString("library", manager);
    }
    /**
     * Creates a child for this Entity.
     * @param title The title for the child.
     * @param description The description for the child.
     * @return true if the child was able to be created, false otherwise.
     */
    @Override
    public Entity createChild(String title, String description) {
        for(Entity t : children) {
            if(t.getTitle().equals(title)) {
                return t;
            }
        }
        return new Topic(title, description, this);
    }

    /**
     * Checks the equality between this Library and a passed in object
     * @param o object to be checked
     * @return boolean of
     */
    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        if(o instanceof Library){
            Library temp = (Library) o;
            return this.getTitle().equals(temp.getTitle());
        }
        return false;

    }

    /**
     * Returns a list of strings that will be displayed for the menu.
     * @return A list of strings that will be displayed for the menu.
     */
    public List<String> listOptions(){
        List<String> options = new ArrayList<>();
        options.add("Delete Library");
        options.add("Edit Library");
        options.add("Add Topic");
        options.add("View Directory");
        options.add("Sort Topics A-Z");
        options.add("Sort Topics Z-A");
        options.add("Quit");
        for(Entity child : children){
            options.add(child.getTitle());
        }
        return options;
    }

    /**
     * Returns the list of functions the class can perform.
     * @return String Array List of the functions this Editable can perform.
     */
    @Override
    public List<String> getFunc(){
        List<String> funcs = new ArrayList<>();
        funcs.add("delete");
        funcs.add("edit");
        funcs.add("add");
        funcs.add("view");
        funcs.add("sortAlphA");
        funcs.add("sortAlphD");
        funcs.add("quit");

        return funcs;
    }
    /**
     * Returns a list of attributes that contains the title and description of a library.
     * @return A list of attributes that contains the title and description of a library.
     */
    public List<String> listAttributes(){
        List<String> attr = new ArrayList<>();
        attr.add(this.getTitle());
        attr.add(this.getDescription());

        return attr;
    }


    public void delete() {
        throw new UnsupportedOperationException();
    }
}