package refmeister.entity;

import refmeister.XML.Saveable;
import refmeister.XML.XMLManager;
import refmeister.entity.Interfaces.Editable;
import refmeister.entity.Interfaces.Entity;
import refmeister.entity.Interfaces.Relation;

import java.util.*;

/**
 * The Idea class models an Idea that would be listed in one or multiple References.
 * @author Brandon Townsend
 * @version 25, 3, 2018
 */
public class Idea extends Editable {

	/** ArrayList of RefIdeas that show what this Idea instance is associated with. */
	private List<RefIdea> ideas;

	/**
	 * Constructor for an Idea that is given a specified title, description, and an ArrayList of
	 * RefIdeas.
	 * @param title The specified String to be set as this Idea's title.
	 * @param desc The specified String to be set as this Idea's description.
	 * @param ideas The specified ArrayList to be set to ideas.
	 */
	public Idea(String title, String desc, List<RefIdea> ideas) {
		setTitle(title);
		setDescription(desc);
		this.ideas = ideas;
	}

	/**
	 * Constructor which sets the ArrayList of RefIdeas to a default value, while still passing
	 * the others.
	 * @param title The specified String to be set as this Idea's title.
	 * @param desc The specified String to be set as this Idea's description.
	 */
	public Idea(String title, String desc) {
		this(title, desc, new ArrayList<RefIdea>());
	}

	/**
	 * Constructor which sets the description to a default value, while still passing the others.
	 * @param title The specified String to be set as this Idea's title.
	 * @param ideas The specified ArrayList to be set to this Idea's ideas.
	 */
	public Idea(String title, List<RefIdea> ideas) {
		this(title, "Unset Description", ideas);
	}

	/**
	 * Default Constructor that is passed the title. All others are set to default values.
	 * @param title The specified String to be set as this Idea's title.
	 */
	public Idea(String title) {
		this(title, "Unset Description", new ArrayList<RefIdea>());
	}

	/**
	 * Retrieves the ArrayList of RefIdeas.
	 * @return The ArrayList of RefIdeas.
	 */
	public List<RefIdea> getRefIdea() {
		return ideas;
	}

	/**
	 * Sets the ArrayList of RefIdeas to a specified List of RefIdea.
	 * @param ideas The specified List of RefIdeas.
	 */
	public void setRefIdea(List<RefIdea> ideas) {
		this.ideas = ideas;
	}

	/**
	 * Disassociates all of this Idea's RefIdeas from this Idea.
	 */
	public void destroy() {
		for(RefIdea ri : ideas) {
			ri.destroy();
		}
	}

    /**
     *
     * @param o
     * @return
     */
	@Override
	public boolean equals(Object o) {
	    if(this == o) {
	        return true;
        }
        if(o instanceof Idea) {
	        Idea temp = (Idea) o;
	        return this.getTitle().equals(temp.getTitle());
        }
        return false;
    }

	/**
	 * Ideas should have no children, so this method should return null.
	 * @return null, since Ideas do not have children.
	 */
	@Override
	public List<Saveable> getSaveableChildren() {
		return Collections.emptyList();
	}
	/**
	 * Retrieves the list of this Editable's children.
	 * @return The list of this Editable's children.
	 */
	@Override
	public List<Editable> getChildren() {
		return null;
	}
	/**
	 * Does nothing in Idea
	 * @param title The title for the child.
	 * @param description The description for the child.
	 * @return true if the child was able to be created, false otherwise.
	 */
	@Override
	public boolean createChild(String title, String description) {
		return false;
	}

	/**
	 * TODO Will edit this later.
	 * @return
	 * @param manager
	 */
	@Override
	public String getSaveString(XMLManager manager) {
		return String.format("<idea title=\"%s\" description=\"%s\">\n", getTitle(), getDescription());
	}

    void registerRefIdea(RefIdea refIdea) {
        this.ideas.add(refIdea);
	}

	@Override
	public void registerRelation(Relation r) {

	}

	@Override
	public void registerChild(Entity e) {

	}

	@Override
	public void removeRelation(Relation r) {

	}

	@Override
	public void removeChild(Entity e) {

	}
}