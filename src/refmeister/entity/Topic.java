package refmeister.entity;

import refmeister.XML.Saveable;
import refmeister.XML.XMLManager;
import refmeister.entity.Interfaces.Editable;
import refmeister.entity.Interfaces.Entity;
import refmeister.entity.Interfaces.Relation;

import java.util.ArrayList;
import java.util.List;

public class Topic extends Editable implements Comparable<Entity> {
    private List<Entity> themes;
    private Entity parent;

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
        this.parent = parent;
        parent.registerChild(this);
        this.themes = themes;

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


	/**
	 * Deletes a theme with the given title.
	 * @param e The title of the theme to remove.
	 */
	@Override
	public void removeChild(Entity e) {
        boolean result = themes.removeIf(ed -> ed.equals(e));
        if(result){
            ((Theme) e).setParent(null);
        }

	}


    /**
     * Gets this topic's parent.
     * @return the topic's parent.
     */
    public Entity getParent() {
        return parent;
    }


    /**
     * Sets this editable's parent.
     * @param parent The topic's new parent.
     */
    public void setParent(Editable parent) {
        this.parent = parent;
    }
    /**
     * Retrieves the list of this Editable's children.
     * @return The list of this Editable's children.
     */
    @Override
    public List<Entity> getEntityChildren() {
        return themes;
    }

    @Override
    public String toString() {
        return "Topic{" +
                "title='" + getTitle() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", themes=" + themes +
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
        return new ArrayList<>(themes);
    }
    /**
     * Creates a child for this Editable.
     * @param title The title for the child.
     * @param description The description for the child.
     * @return true if the child was able to be created, false otherwise.
     */
    @Override
    public boolean createChild(String title, String description) {
        for(Entity t : themes) {
            if(t.getTitle().equals(title)) {
                return false;
            }
        }
        Entity newTheme = new Theme(title, description, this);
        return true;
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