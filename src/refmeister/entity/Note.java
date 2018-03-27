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
	 * Gets the XML representation of this saveable object. Saveable objects that are association
	 * classes should register their XML output with the XMLManager, and Argument/Ideas should
	 * also register with the XMLManager.
	 * @param manager   The XMLManager that this traversal is being used with.
	 * @return          The XML representation of this Saveable, with appropriate associations
	 *                  registered with the XML Manager.
	 */
	@Override
	public String getSaveString(XMLManager manager) {
		return String.format("<note title=\"%s\" description=\"%s\" />\n",
				getTitle(), getDescription());
	}
	/**
	 * Creates a child for this Editable.
	 * @param title The title for the child.
	 * @param description The description for the child.
	 * @return true if the child was able to be created, false otherwise.
	 */
	@Override
	public boolean createChild(String title, String description) {
		return false;
	}
}