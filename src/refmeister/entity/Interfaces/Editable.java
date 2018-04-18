package refmeister.entity.Interfaces;

import refmeister.XML.Saveable;
import refmeister.XML.XMLManager;
import refmeister.entity.Argument;
import refmeister.entity.Idea;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Editable is an abstract class which our other classes will implement. If the child class has a
 * title, description, etc. it will extend Editable.
 * @author Peter Gardner, Caleb Dinehart
 * @version 26, 3, 2018
 */
public abstract class Editable implements Displayable, Saveable, Entity, Comparable<Entity>{

    /** The title for any Editable objects. */
	private String title;
	/** The description for any Editable objects. */
	private String description;
	/** The parent of an Editable object if it is specified to have a parent. */
	protected Entity parent;

    protected List<Entity> children;

    public Editable(){
        this.children = new ArrayList<>();
    }

    /**
     * Retrieves either the title, description, or null depending on the String passed to the
     * method.
     * @param attribute The String that determines what attribute to choose.
     * @return this.getTitle() if attribute is "title", this.getDescription() if attribute is
     * "description", or null otherwise.
     */
    public String getAttribute(String attribute){
        if(attribute.equals("title")){
            return this.getTitle();
        }
        else if(attribute.equals("description")){
            return this.getDescription();
        }else{
            return null;
        }
    }

    /**
     * Sets contents to a specified attribute, if that attribute is "title" or "description". If
     * it is not either, it will throw an InvalidParameterException.
     * @param attribute The String that specifies what should be set to the value of contents.
     * @param contents The value to be set to a specified attribute.
     */
    public void setAttribute(String attribute, String contents){
        if(attribute.equals("title")){
            this.setTitle(contents);
        }
        else if(attribute.equals("description")){
            this.setDescription(contents);
        }else{
            throw new InvalidParameterException();
        }
    }

    /**
     * Retrieves this Entity's parent.
     * @return This Entity's parent.
     */
    public Entity getParent() {
        return parent;
    }

    /**
     * Sets this Entity's parent.
     * @param parent The new Entity to be set to this.parent.
     */
    public void setParent(Entity parent) {
        this.parent = parent;
    }

    /**
     * Retrieves this Entity's title.
     * @return This Entity's title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets this Entity's title.
     * @param title The new String to be set to this.title.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Retrieves this Entity's description.
     * @return This Entity's description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets this Entity's description.
     * @param description The new String to be set to this.description.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Creates an XML tag for an editable object, given it's tag name.
     * @param tagName   The tag name of this tag.
     * @param manager   The XMLManager that this traversal is being used with.
     * @return          A formatted XML String.
     */
    protected String getSaveString(String tagName, XMLManager manager){
        StringBuilder out = new StringBuilder();
        out.append(String.format("<%s title=\"%s\" description=\"%s\">\n",
                tagName, this.getTitle(), this.getDescription()));

        for(Saveable child : this.getSaveableChildren()){
            String childString = child.getSaveString(manager);
            if(childString != null){
                out.append(childString);
            }
        }

        out.append("</");
        out.append(tagName);
        out.append(">\n");
        return out.toString();
    }

    /**
     * Sets title and description to the indices in edits.
     * @param edits An array of Strings which contains the new title and description for this
     *              Entity.
     */
    public void edit(String[] edits) {
        setTitle(edits[0]);
        setDescription(edits[1]);
    }

    /**
     * Gets an array of elements that this object has, with title being at
     * index 0, description at index 1, and each child as a sub index.
     * @return An array of Strings to be displayed by the Controller.
     */
    public String[] display() {
        String[] display = new String[7 + getEntityChildren().size()];
        int i = 0;
        display[i++] = getTitle();
        display[i++] = getDescription();
        display[i++] = "e: Edit this Object";
        display[i++] = "u: Select Parent";
        if(getEntityChildren() != null) {
            display[i++] = "c: Create a New Child";
        }
        display[i++] = "q: Quit RefMeister";
        display[i++] = null;
        for(Entity e : getEntityChildren()) {
            display[i++] = e.getTitle();
        }
        return display;
    }

    /**
     * Method compares this Entity with another Entity
     * @param e the Entity that is being compared
     * @return a negative integer, zero, or a positive integer as this object
     *          is less than, equal to, or greater than the specified object.
     */
    public int compareTo(Entity e){
        return this.getTitle().compareTo(e.getTitle());
    }

    /**
     * Registers a child entity of the object
     * @param e the new child entity
     */
    public void registerChild(Entity e){
        children.add(e);
    }

    /**
     * Removes the given child. <b>Currently broken?</b>
     * @param e the entity to remove
     * @return  this object
     */
    public Entity removeChild(Entity e){
        if(e.getEntityChildren().isEmpty()){
            if(e instanceof Argument){
                //((Argument) e).removeRelation();
            }
            if(e instanceof Idea){
                //((Idea) e).removeRelation();
            }
            e.setParent(null);
            return this;
        }
        for(Entity t : children){
            removeChild(t);
        }
        children.clear();
        return this;
    }
    /**
     * Creates a child for this Entity. This is very bad code smell, but was used to try to get
     * valid working code.
     * @param title The title for the child.
     * @param description The description for the child.
     * @return true if the child was able to be created, false otherwise.
     */
    public abstract Entity createChild(String title, String description);

    /**
     * Retrieves the list of this Entity's children.
     * @return The list of this Entity's children.
     */
    public List<Entity> getEntityChildren(){
        return new ArrayList<Entity>(children);
    }

    /**
     * Sorts an Entities children in ascending or descending order based on their titles.
     * @param order Specifies either ascending or descending order.
     */
    @Override
    public void sort(String order) {
        List<Entity> kids = getEntityChildren();
        if(order.toLowerCase().equals("a-z")) {
            kids.sort(Comparator.naturalOrder());
        } else if(order.toLowerCase().equals("z-a")) {
            kids.sort(Comparator.reverseOrder());
        }
    }
}