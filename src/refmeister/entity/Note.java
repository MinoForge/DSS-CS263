package refmeister.entity;

import refmeister.XML.Saveable;
import refmeister.XML.XMLManager;
import refmeister.entity.Interfaces.Entity;
import refmeister.entity.Interfaces.Editable;

import java.util.*;

/**
 * The Note class models a Reference's note. It must have a title and a parent Reference, but
 * does not need a description.
 * @author Brandon Townsend
 * @version 25, 3, 2018
 */
public class Note extends Editable {

	/**
	 * Constructor for the Note class. Takes a Reference object to be specified as the parent, a
	 * String to be the title, and a String to be the description.
	 * @param title    The String to be specified as the title.
	 * @param description The String to be specified as the description.
	 * @param parent The Reference to be set as this Note's parent.
	 */
	public Note(String title, String description, Reference parent) {
		this.setParent(parent);
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
     * Default constructor. As of right now, we do not support creating Note's without at least a
     * title.
     */
    public Note() {
        throw new UnsupportedOperationException("Must specify at least a title for a Note.");
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

    @Override
    public Entity createChild(String title, String description) {
        return null;
    }

    @Override
    public List<Entity> getEntityChildren(){
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
	 * Checks the equality between this note and a passed in object
	 * @param o object to be checked
	 * @return boolean of whether
	 */
	public boolean equals(Object o){
		if(this == o){
			return true;
		}
		if(o instanceof Note){
			Note temp = (Note) o;
			return this.getTitle().equals(temp.getTitle());
		}
		return false;
	}

	/**
	 * Returns a list of strings that will be displayed for the menu.
	 * @return A list of strings that will be displayed for the menu.
	 */
    public List<String> listOptions(){
        List<String> options = new ArrayList<>();
        options.add("Delete Note");
        options.add("Edit Note");
        options.add("View Directory");
        options.add("Move Up");
        return options;
    }

    /**
     * Returns a list of attributes that contains the title and description of a note.
     * @return A list of attributes that contains the title and description of a note.
     */
    @Override
    public List<String> listAttributes(){
        List<String> attr = new ArrayList<>();
        attr.add(this.getTitle());
        attr.add(this.getDescription());

        return attr;
    }

    public void delete() {
    	//TODO
	}
}