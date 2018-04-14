package refmeister.entity;

import refmeister.XML.Saveable;
import refmeister.XML.XMLManager;
import refmeister.entity.Interfaces.Editable;
import refmeister.entity.Interfaces.Entity;
import refmeister.entity.Interfaces.Relation;

import java.util.ArrayList;
import java.util.List;

/**
 * The Theme class models an Theme that would be listed in one Topic.
 * @author Caleb Dinehart
 * @version 25, 3, 2018
 */

public class Theme extends Editable {
	/** List of references that are associated with this theme. */
	private List<Editable> refs;
	/** The Topic that contains this theme. */
	private Topic parent;
	/**
	 * Constructor for an Theme that is given a specified title, description, and an ArrayList of
	 * RefIdeas.
	 * @param title The specified String to be set as this Theme's title.
	 * @param desc The specified String to be set as this Theme's description.
	 * @param parent The specified Topic that contains this theme
	 * @param refs The ArrayList of references that this theme holds.
	 */
	public Theme(String title, String desc, Topic parent,  List<Editable> refs) {
		this.setTitle(title);
		this.setDescription(desc);
		this.refs = refs;
		this.parent = parent;
		parent.register(this);
	}
	/**
	 * Constructor which sets an empty ArrayList of references, while still passing
	 * the others.
	 * @param title The specified String to be set as this Theme's title.
	 * @param desc The specified String to be set as this Theme's description.
	 * @param parent The Topic that contains this Theme.
	 */
	public Theme(String title, String desc, Topic parent){
		this(title, desc, parent, new ArrayList<Editable>());
	}
	/**
	 * Constructor which sets an empty ArrayList of references, and sets
	 * description to a default value while still passing the others.
	 * @param title The specified String to be set as this Theme's title.
	 * @param parent The Topic that contains this Theme.
	 */
	public Theme(String title, Topic parent){
		this(title, "Unset Description", parent, new ArrayList<Editable>());
	}

    /**
     * Default Constructor. At this time, you must specify at least a title.
     */
	public Theme() {
	    throw new UnsupportedOperationException("Must specify at least a title for this Theme.");
    }

	/*
	 * This method will move a theme from one topic to another topic as long as
	 * the theme is not already in the topic and if the topic exists. This reeks of code smells
	 * needs to be altered
	 * @param topicTitle The title of the Topic the theme is being moved to.
	/*
	public void moveTheme(String topicTitle) throws InvalidParameterException {
		for(Editable t : this.parent.getParent().getChildren()){
			if(t.getTitle().equals(topicTitle)){
				for(Editable i : t.getChildren()){
					if(i.getTitle().equals(this.getTitle())){
						throw new InvalidParameterException("Theme already exists in chosen topic");
					}
				}
				t.getSaveableChildren().add(this);
				this.setParent((Topic)t);
				this.parent.deleteTheme(this.getTitle());
				return;
			}
		}
		throw new InvalidParameterException("Topic does not exist");
	}*/

	void register(Reference ref){
		this.refs.add(ref);
	}

	/**
	 * Add a Reference to the ArrayList of References.
	 * @param title the String representing the title of the reference
	 * @param desc the String representing the description of the reference
	 * @return return the newly added Reference
	 */
	public Reference addReference(String title, String desc) {
		for(Editable t : refs) {
			if(t.getTitle().equals(getTitle())) {
				return null;
			}
		}
		Reference newRef = new Reference(title, desc, this);
		return newRef;
	}

	/**
	 * Deletes the selected reference from the  ArrayList of References
	 * @param title String represeneting the title fo the Reference being removed
	 */
	public void deleteReference(String title) {
		refs.removeIf(ed -> ed.getTitle().equals(title));
	}

	/**
	 * Getter for the list of references
	 * @return returns the List of References
	 */
	public List<Editable> getRefs() {
		return refs;
	}

	/**
	 * Setter for the list of references
	 * @param refs the list of references being set
	 */
	public void setRefs(List<Editable> refs) {
		this.refs = refs;
	}

	/**
	 * Getter to retrieve the parent
	 * @return the Topic that is the Theme's parent
	 */
	public Topic getParent() {
		return parent;
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

	/**
	 * Setter to set the parent
	 * @param parent the Topic that will become the new parent of this theme
	 */
	public void setParent(Topic parent) {
		this.parent = parent;
	}

	/**
	 * Returns a String representation of this Class
	 * @return the String representation of this Class
	 */
	@Override
	public java.lang.String toString() {
		return "Theme{" +
				"title='" + getTitle() + '\'' +
				", description='" + getDescription() + '\'' +
				", references=" + refs +
				'}';
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
		if(o instanceof Theme){
			Theme temp = (Theme) o;
			return this.getTitle().equals(temp.getTitle());
		}
		return false;

	}
	 /** Gets the XML String of this theme, with all references as it's children.
	 * @return The formatted XML save string.
	 * @param manager The XMLManager that handles the XML formatting and parsing.
	 */
	@Override
	public String getSaveString(XMLManager manager) {
		return super.getSaveString("theme", manager);
	}

	/**
	 * Returns a list of this Themes's XML children.
 	* @return A list of all of this Themes's children
 	*/
	@Override
	public List<Saveable> getSaveableChildren() {
		return new ArrayList<>(refs);
	}
	/**
	 * Gets children of Theme.
	 * @return The list of this Editable's children.
	 */
	public List<Editable> getChildren() {
		return refs;
	}

	/**
	 * Creates a child for this Editable.
	 * @param title The title for the child.
	 * @param description The description for the child.
	 * @return true if the child was able to be created, false otherwise.
	 */
	@Override
	public boolean createChild(String title, String description) {
		return (addReference(title, description) != null);
	}
}