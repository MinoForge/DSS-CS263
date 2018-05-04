package refmeister.entity;

import refmeister.XML.Saveable;
import refmeister.XML.XMLManager;
import refmeister.entity.interfaces.Entity;
import refmeister.entity.interfaces.Editable;

import java.util.ArrayList;
import java.util.List;

/**
 * The Topic class models a topic that is a child of a library and may have many themes for
 * children.
 * @author Caleb Dinehart
 * @version 20 April, 2018
 */

public class Topic extends Editable implements Comparable<Entity> {

    /**
     * Topic Constructor. Creates a topic.
     * @param title         The title of this topic
     * @param description   The description of this topic
     * @param parent        This topic's parent. As of now, should be a library
     * @param themes        A list of this topic's children. As of now, they are themes.
     */
    public Topic(String title, String description, Entity parent, List<Entity> themes){
        this.setTitle(title);
        this.setDescription(description);
        this.setParent(parent);
        this.children = themes;
        parent.registerChild(this);
    }

    /**
     * Topic Constructor. Creates a topic, but no children have to be specified.
     * @param title         The title of this topic
     * @param description   The description of this topic
     * @param parent        This topic's parent
     */
    public Topic(String title, String description, Entity parent){
        this(title, description, parent, new ArrayList<Entity>());
    }

    /**
     * Topic Constructor. Creates a topic, but no description or children have to be specified.
     * @param title     The title of this topic
     * @param parent    The parent library of this topic
     */
    public Topic(String title, Entity parent){
        this(title, "Unset Description", parent, new ArrayList<Entity>());
    }

    /**
     * Default Constructor for a Topic. As of now, we are not allowing the creation of Topics
     * without at least a title.
     */
    public Topic() {
        throw new UnsupportedOperationException("Must specify at least a title for this Topic");
    }

    /**
     * Retrieves the list of this Entity's children.
     * @return The list of this Entity's children.
     */
    @Override
    public List<Entity> getEntityChildren() {
        return new ArrayList<>(children);
    }

    /**
     * Creates an XML tag for an editable object.
     * @param manager   The XMLManager that this traversal is being used with.
     * @return          A formatted XML String.
     */
    @Override
    public String getSaveString(XMLManager manager) {
        return super.getSaveString("topic", manager);
    }

    /**
     * Gets a list of this topic's XML children
     * @return A list of this topic's children.
     */
    public List<Saveable> getSaveableChildren() {
        return new ArrayList<>(children);
    }

    /**
     * Creates a child for this Entity.
     * @param title The title for the child.
     * @param description The description for the child.
     * @return the new child if it was created, or the preexisting child.
     */
    @Override
    public Entity createChild(String title, String description) {
        for(Entity t : children) {
            if(t.getTitle().equals(title)) {
                return t;
            }
        }
        return new Theme(title, description, this);
    }

    /**
     * Checks the equality between this Library and a passed in object.
     * @param o object to be checked
     * @return boolean of
     */
    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        if(o instanceof Topic){
            Topic temp = (Topic) o;
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
        options.add("Delete Topic");
        options.add("Edit Topic");
        options.add("Add Theme");
        options.add("Sort Themes A-Z");
        options.add("Sort Themes Z-A");
        for(Entity child : children){
            options.add(child.getTitle());
        }
        return options;
    }

    /**
     * Returns a list of attributes that contains the title and description of the topic.
     * @return A list of attributes that contains the title and description of the topic.
     */
    public List<String> listAttributes(){
        List<String> attr = new ArrayList<>();
        attr.add(this.getTitle());
        attr.add(this.getDescription());

        return attr;
    }

    /**
     * Removes this topic from it's parent's list of children.
     */
    public void delete() {
        this.getParent().removeChild(this);
    }


    /**
     * Returns a string representation of this topic.
     *
     * @return a string representation of this topic.
     */
    @Override
    public String toString() {
        return "Topic{" +
                "parent=" + parent +
                ", children=" + children +
                '}';
    }
}