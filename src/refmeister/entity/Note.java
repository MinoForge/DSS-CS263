package refmeister.entity;

import java.util.*;

/**
 * The Note class models a Reference's note. It must have a title and a
 * parent Reference, but does not need a description.
 * @author Red Team/DevSquad Supreme
 * @version 25, 3, 2018
 */
public class Note extends Editable {

	/** This Note's parent, which is a Reference. */
	private Reference parent;

	/**
	 * Constructor for the Note class. Takes a Reference object to be
	 * specified as the parent, a String to be the title, and a String to be
	 * the description.
	 * @param parent The Reference to be set as this Note's parent.
	 * @param title	The String to be specified as the title.
	 * @param description The String to be specified as the description.
	 */
	public Note(Reference parent, String title, String description) {
		this.parent = parent;
		setTitle(title);
		setDescription(description);
	}

	/**
	 * A more default constructor where the user does not specify a description.
	 * @param parent The Reference to be set as this Note's parent.
	 * @param title The String to be specified as the title.
	 */
	public Note(Reference parent, String title) {
		this(parent, title, "Unset Description");
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
	public String display() {
		return 	"Title: " + getTitle() +
				"Description: " + getDescription();
	}

	/**
	 * This Note does not have children, but it still needs to Override this
	 * method.
	 * @return null, since Note's have no children.
	 */
	@Override
	public ArrayList<Editable> getChildren() {
		return null;
	}

	/**
	 * TODO Add code to this at a later day
	 * @return
	 */
	@Override
	public String getSaveString() {
		return null;
	}
}