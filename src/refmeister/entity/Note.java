package refmeister.entity;

import refmeister.XML.Saveable;
import refmeister.XML.XMLManager;

import java.util.*;

/**
 * The Note class models a Reference's note. It must have a title and a parent Reference, but
 * does not need a description.
 * @author Brandon Townsend
 * @version 25, 3, 2018
 */
public class Note extends Editable {

	/** This Note's parent, which is a Reference. */
	private Reference parent;

	/**
	 * Constructor for the Note class. Takes a Reference object to be specified as the parent, a
	 * String to be the title, and a String to be the description.
	 * @param title    The String to be specified as the title.
	 * @param description The String to be specified as the description.
	 * @param parent The Reference to be set as this Note's parent.
	 */
	public Note(String title, String description, Reference parent) {
		this.parent = parent;
		parent.registerNote(this);
		setTitle(title);
		setDescription(description);
	}

	/**
	 * A more default constructor where the user does not specify a description.
	 * @param title The String to be specified as the title.
	 * @param parent The Reference to be set as this Note's parent.
	 */
	public Note(String title, Reference parent) {
		this(title, "Unset Description", parent);
	}

	/**
	 * Retrieves this Note's parent.
	 * @return This Note's parent.
	 */
	public Reference getReference() {
		return this.parent;
	}

	/**
	 * Sets this Note's parent to the specified reference.
	 * @param parent The Reference to be set to this Note's parent.
	 */
	public void setReference(Reference parent) {
		this.parent = parent;
	}

	/**
	 * Returns a String that contains the title and description of this Note.
	 * @return A String that contains the title and description of this Note.
	 */
	@Override
	public String[] display() {
		return new String[] {getTitle(), getDescription(), null};
	}

	/**
	 * This Note does not have children, but it still needs to Override this
	 * method.
	 * @return An empty list.
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
	 *
	 * @return
	 * @param manager
	 */
	@Override
	public String getSaveString(XMLManager manager) {
		return String.format("<note title=\"%s\" description=\"%s\" />\n",
				getTitle(), getDescription());
	}

	@Override
	public boolean createChild(String title, String description) {
		return false;
	}
}