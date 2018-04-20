package refmeister.entity;

import refmeister.XML.Saveable;
import refmeister.XML.XMLManager;
import refmeister.entity.Interfaces.*;

import java.util.*;

/**
 * The Idea class models an Idea that would be listed in one or multiple References.
 * @author Brandon Townsend
 * @version 17, 4, 2018
 */

public class Idea extends Editable implements Relatable {

	/** ArrayList of RefIdeas that show what this Idea instance is associated with. */
	private List<Relation> ideas;

	/**
	 * Constructor for an Idea that is given a specified title, description, and an ArrayList of
	 * RefIdeas.
	 * @param title The specified String to be set as this Idea's title.
	 * @param desc The specified String to be set as this Idea's description.
	 * @param ideas The specified ArrayList to be set to ideas.
	 */
	public Idea(String title, String desc, List<Relation> ideas) {
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
		this(title, desc, new ArrayList<Relation>());
	}

	/**
	 * Constructor which sets the description to a default value, while still passing the others.
	 * @param title The specified String to be set as this Idea's title.
	 * @param ideas The specified ArrayList to be set to this Idea's ideas.
	 */
	public Idea(String title, List<Relation> ideas) {
		this(title, "Unset Description", ideas);
	}

	/**
	 * Default Constructor that is passed the title. All others are set to default values.
	 * @param title The specified String to be set as this Idea's title.
	 */
	public Idea(String title) {
		this(title, "Unset Description", new ArrayList<>());
	}

    /**
     * Default Constructor. At this time, we do not support this.
     */
	public Idea() {
	    throw new UnsupportedOperationException("Must specify at least a title for this Idea.");
    }

	/**
	 * Disassociates all of this Idea's RefIdeas from this Idea.
	 */
	public void delete() {
		for(Relation ri : ideas) {
			ri.destroy();
		}
	}

    /**
     * Checks equality between this Idea and a passed in object.
     * @param o The object to have its equality is checked against this Idea.
     * @return true if they are equal, false otherwise.
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
	 * Retrieves the list of this Entity's children.
	 * @return The list of this Entity's children.
	 */
	@Override
	public List<Entity> getEntityChildren() {
		return null;
	}
	/**
	 * Does nothing in Idea
	 * @param title The title for the child.
	 * @param description The description for the child.
	 * @return true if the child was able to be created, false otherwise.
	 */
	@Override
	public Entity createChild(String title, String description) {
		return null;
	}

	/**
	 * Gets the save string.
	 * @param manager   the XML manager to use
     * @return          the save string of this object
     */
	@Override
	public String getSaveString(XMLManager manager) {
		return String.format("<idea title=\"%s\" description=\"%s\">\n", getTitle(), getDescription());
	}

    /**
     * Associates a relation between an idea and a reference.
     * @param r The relation to be associated.
     */
    @Override
    public void registerRelation(Relation r) {
        this.ideas.add(r);
    }

    /**
     * Disassociates a relation between an idea and a reference.
     * @param r The relation to be disassociated.
     */
    @Override
    public void removeRelation(Relation r) {
        this.ideas.removeIf(r::equals);
    }

	/**
	 * Returns a list of strings that will be displayed for the menu.
	 * @return A list of strings that will be displayed for the menu.
	 */
	public List<String> listOptions(){
        List<String> options = new ArrayList<>();
        options.add("Delete Idea");
        options.add("Edit Idea");
        options.add("Change Relation");
        options.add("View Directory");
        options.add("Move Up");
        options.add("Quit");
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
        funcs.add("change");
        funcs.add("view");
        funcs.add("move");
		funcs.add("quit");

        return funcs;
    }

    /**
     * Returns a list of attributes that contains the title and description of an idea.
     * @return a list of attributes that contains the title and description of an idea.
     */
    @Override
    public List<String> listAttributes(){
        List<String> attr = new ArrayList<>();
        attr.add(this.getTitle());
        attr.add(this.getDescription());

        return attr;
    }


    /**
     * Returns the list of Relations for this idea.
     * @return the List of Relations for this idea.
     */
    public List<Relation> getRelations(){
        return this.ideas;
    }
    public void registerRatedRelation(RatedRelation r){
        throw new UnsupportedOperationException();
    }

    /**
     * Ideas can't be rated at this time, so there is no need to associate it with a rating.
     * @param r Would be the RatedRelation, if we supported this operation.
     */
    public void removeRatedRelation(RatedRelation r){
        throw new UnsupportedOperationException();
    }
}
