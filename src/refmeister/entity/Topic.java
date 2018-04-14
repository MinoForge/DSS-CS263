package refmeister.entity;

import refmeister.XML.Saveable;
import refmeister.XML.XMLManager;
import refmeister.entity.Interfaces.Entity;
import refmeister.entity.Interfaces.Editable;
import refmeister.entity.Interfaces.Relation;

import java.util.ArrayList;
import java.util.List;

public class Topic extends Editable implements Comparable<Entity> {

    /**
     * Creates a topic.
     * @param title         The title of this topic
     * @param description   The description of this topic
     * @param parent        This topic's parent
     * @param themes        A list of this topic's themes
     */
    public Topic(String title, String description, Entity parent, List<Entity> themes){
        this.setTitle(title);
        this.setDescription(description);
        this.setParent(parent);
        parent.registerChild(this);
        this.children = themes;

    }

    /**
     * Creates a topic.
     * @param title         The title of this topic
     * @param description   The description of this topic
     * @param parent        This topic's parent
     */
    public Topic(String title, String description, Entity parent){
        this(title, description, parent, new ArrayList<Entity>());
    }

    /**
     * Creates a topic.
     * @param title     The title of this topic
     * @param parent    The parent library of this topic
     */
    public Topic(String title, Entity parent){
        this(title, "Unset Description", parent, new ArrayList<Entity>());
    }

    public Topic() {
        throw new UnsupportedOperationException("Must specify at least a title for this Topic");
    }
    /**
     * Retrieves the list of this Entity's children.
     * @return The list of this Entity's children.
     */
    @Override
    public List<Entity> getEntityChildren() {
        return children;
    }

    @Override
    public String toString() {
        return "Topic{" +
                "title='" + getTitle() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", themes=" + children +
                '}';
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

}