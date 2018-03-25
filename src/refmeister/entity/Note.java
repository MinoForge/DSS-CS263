package refmeister.entity;

import java.util.*;

/**
 * The Note class models a Reference's note. It must have a title and a
 * parent Reference, but does not need a description.
 */
public class Note extends Editable {

	/** This Note's parent, which is a Reference. */
	Reference parent;
	/** This Note's title; Must be specified. */
	private String title;
	/** This Note's description; Does not have to be specified. */
	private String description;

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
		this.title = title;
		this.description = description;
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
	 * Retrieves this Note's title.
	 * @return This Note's title.
	 */
	@Override
	public String getTitle() {
		return this.title;
	}

	/**
	 * Sets this Note's parent to the specified String.
	 * @param title The String to be set to this Note's title.
	 */
	@Override
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Retrieves this Note's description.
	 * @return This Note's description.
	 */
	@Override
	public String getDescription() {
		return this.description;
	}

	/**
	 * Sets this Note's description to the specified String.
	 * @param description The String to bes est to this Note's title.
	 */
	@Override
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Returns a String that contains the title and description of this Note.
	 * @return A String that contains the title and description of this Note.
	 */
	@Override
	public String display() {
		return "Title: " + this.title +
				"Description: " + this.description;
	}

	/**
	 * This Note does not have children, but it still needs to Override this
	 * method.
	 * @return null, since Note's have no children.
	 */
	@Override
	public ArrayList<Saveable> getChildren() {
		return null;
	}

	/**
	 * TODO Not exactly sure as of yet what this one will do.
	 * @return
	 */
	@Override
	public String getSaveString() {
		return null;
	}
}