package refmeister.entity;

import refmeister.XML.Saveable;
import refmeister.XML.XMLManager;
import refmeister.entity.Interfaces.Entity;
import refmeister.entity.Interfaces.Editable;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

/**
 * The Theme class models an Theme that would be listed in one Topic.
 * @author Caleb Dinehart
 * @version 25, 3, 2018
 */


public class Theme extends Editable {

	/** A field to hold the library parent that this theme belongs to. */
    private Entity library;

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
		this.setParent(parent);
		parent.registerChild(this);
		library = parent.getParent();
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

    /**
     * Default Constructor. At this time, you must specify at least a title.
     */
	public Theme() {
	    throw new UnsupportedOperationException("Must specify at least a title for this Theme.");
    }

	/**
	 * This method will move a theme from one topic to another topic as long as
	 * the theme is not already in the topic and if the topic exists.
	 * @param topicTitle The title of the Topic the theme is being moved to.
	*/
	public void moveTheme(String topicTitle) throws InvalidParameterException {
		for(Entity t : this.library.getEntityChildren()){
			if(t.getTitle().equals(topicTitle)){
				for(Entity i : t.getEntityChildren()){
					if(i.getTitle().equals(this.getTitle())){
						throw new InvalidParameterException("Theme already exists in chosen topic.");
					}
				}
				t.getEntityChildren().add(this);
				this.setParent(t);
				this.parent.removeChild(this);
				return;
			}
		}
		throw new InvalidParameterException("Topic does not exist.");
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
	 * Creates a child for this Entity.
	 * @param title The title for the child.
	 * @param description The description for the child.
	 * @return true if the child was able to be created, false otherwise.
	 */
	@Override
	public Entity createChild(String title, String description) {
		for(Entity t : children) {
            if(t.getTitle().equals(title)) {
                return t;
            }
        }
        return new Reference(title, description, this);
	}

    /**
     * Returns a list of strings that will be displayed for the menu.
     * @return A list of strings that will be displayed for the menu.
     */
    public List<String> listOptions(){
        List<String> options = new ArrayList<>();
        options.add("Delete Theme");
        options.add("Edit Theme");
        options.add("Add Reference");
        options.add("View Directory");
        options.add("Sort References A-Z");
        options.add("Sort References Z-A");
        options.add("Move Up");
        for(Entity child : children){
            options.add(child.getTitle());
        }
        return options;
    }

    /**
     * Returns a list of attributes that contains the title and description of a theme.
     * @return A list of attributes that contains the title and description of a theme.
     */
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