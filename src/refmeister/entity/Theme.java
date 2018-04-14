package refmeister.entity;

import refmeister.XML.Saveable;
import refmeister.XML.XMLManager;
import refmeister.entity.Interfaces.Entity;
import refmeister.entity.Interfaces.Editable;
import refmeister.entity.Interfaces.Relation;

import java.util.ArrayList;
import java.util.List;

/**
 * The Theme class models an Theme that would be listed in one Topic.
 * @author Caleb Dinehart
 * @version 25, 3, 2018
 */

public class Theme extends Editable {
	/**
	 * Constructor for an Theme that is given a specified title, description, and an ArrayList of
	 * RefIdeas.
	 * @param title The specified String to be set as this Theme's title.
	 * @param desc The specified String to be set as this Theme's description.
	 * @param parent The specified Topic that contains this theme
	 * @param refs The ArrayList of references that this theme holds.
	 */
	public Theme(String title, String desc, Entity parent,  List<Entity> refs) {
		this.setTitle(title);
		this.setDescription(desc);
		this.children = refs;
		this.parent = parent;
		parent.registerChild(this);
	}
	/**
	 * Constructor which sets an empty ArrayList of references, while still passing
	 * the others.
	 * @param title The specified String to be set as this Theme's title.
	 * @param desc The specified String to be set as this Theme's description.
	 * @param parent The Topic that contains this Theme.
	 */
	public Theme(String title, String desc, Topic parent){
		this(title, desc, parent, new ArrayList<Entity>());
	}
	/**
	 * Constructor which sets an empty ArrayList of references, and sets
	 * description to a default value while still passing the others.
	 * @param title The specified String to be set as this Theme's title.
	 * @param parent The Topic that contains this Theme.
	 */
	public Theme(String title, Topic parent){
		this(title, "Unset Description", parent, new ArrayList<Entity>());
	}

	/*
	 * This method will move a theme from one topic to another topic as long as
	 * the theme is not already in the topic and if the topic exists. This reeks of code smells
	 * needs to be altered
	 * @param topicTitle The title of the Topic the theme is being moved to.
	/*
	public void moveTheme(String topicTitle) throws InvalidParameterException {
		for(Entity t : this.parent.getParent().getEntityChildren()){
			if(t.getTitle().equals(topicTitle)){
				for(Entity i : t.getEntityChildren()){
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

	/**
	 * Add a Reference to the ArrayList of References.
	 * @param title the String representing the title of the reference
	 * @param desc the String representing the description of the reference
	 * @return return the newly added Reference
	 */
	public Reference addReference(String title, String desc) {
		for(Entity t : children) {
			if(t.getTitle().equals(getTitle())) {
				return null;
			}
		}
		return new Reference(title, desc, this);
	}

	/**
	 * Deletes the selected reference from the  ArrayList of References
	 * @param title String represeneting the title fo the Reference being removed
	 */
	public void deleteReference(String title) {
		children.removeIf(ed -> ed.getTitle().equals(title));
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
				", references=" + children +
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
		return new ArrayList<>(children);
	}
	/**
	 * Gets children of Theme.
	 * @return The list of this Entity's children.
	 */
	public List<Entity> getEntityChildren() {
		return children;
	}

	/**
	 * Creates a child for this Entity.
	 * @param title The title for the child.
	 * @param description The description for the child.
	 * @return true if the child was able to be created, false otherwise.
	 */
	@Override
	public Entity createChild(String title, String description) {
		return addReference(title, description);
	}
}