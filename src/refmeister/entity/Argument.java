package refmeister.entity;

import refmeister.XML.Saveable;
import refmeister.XML.XMLManager;
import refmeister.entity.Interfaces.Editable;
import refmeister.entity.Interfaces.Entity;
import refmeister.entity.Interfaces.Relation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The Argument class models an argument that would be associated with one or multiple References.
 * @author Brandon Townsend
 * @version 25, 3, 2018
 */
public class Argument extends Editable {

	/** ArrayList of RefArgs that show what this Argument instance is associated with. */
	private List<RefArg> arguments;

	/**
	 * Constructor for an Argument that is given a specified title, description and an ArrayList
	 * of RefArgs.
	 * @param title The specified String to be set as this Argument's title.
	 * @param desc The specified String to be set as this Argument's description.
	 * @param arguments The specified ArrayList to be set to arguments.
	 */
	public Argument(String title, String desc, List<RefArg> arguments) {
		setTitle(title);
		setDescription(desc);
		this.arguments = arguments;
	}

	/**
	 * Constructor which sets the ArrayList of RefArgs to a default value, while still passing
	 * the others.
	 * @param title The specified String to be set as this Argument's title.
	 * @param desc The speicified String to be set as this Argument's description.
	 */
	public Argument(String title, String desc) {
		this(title, desc, new ArrayList<RefArg>());
	}

	/**
	 * Constructor which sets the description to a default value, while still passing the others.
	 * @param title The specified String to be set as this Argument's title.
	 * @param args The specified ArrayList to be set to this Argument's title.
	 */
	public Argument(String title, List<RefArg> args) {
		this(title, "Unset Description", args);
	}

	/**
	 * Default Constructor that is passed the title. All others are set to default values.
	 * @param title The specified String to be set as this Argument's title.
	 */
	public Argument(String title) {
		this(title, "Unset Description", new ArrayList<RefArg>());
	}

	/**
	 * Calculates the average of the ratings of all the RefArgs in arguments.
	 * @return The average of the ratings of all the RefArgs in arguments.
	 */
	public float getArgAverage() {
		float average = 0;
		for(RefArg ra : arguments) {
			average += ra.getRating();
		}
		return average / arguments.size();
	}

	/**
	 * Disassociates all of this Argument's RefArgs from this argument.
	 */
	public void destroy() {
		for(RefArg ra : arguments) {
			ra.destroy();
		}
	}

	/**
	 * Arguments should have no children, so this method should return an empty list.
	 * @return An empty list, since Arguments do not have children.
	 */
	@Override
	public List<Saveable> getSaveableChildren() {
		return Collections.emptyList();
	}

	/**
	 * Arguments should have no children, so this method should return an empty list.
	 * @return An empty  list, since Argurments do not have children.
	 */
	@Override
	public List<Editable> getChildren() {
		return Collections.emptyList();
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
		return String.format("<argument title=\"%s\" description=\"%s\" />\n",
				getTitle(), getDescription());
	}

	/**
	 * Does nothing in Argument.
	 * @param title The title for the child.
	 * @param description The description for the child.
	 * @return true if the child was able to be created, false otherwise.
	 */
    @Override
    public boolean createChild(String title, String description) {
        return false;
    }

	/**
	 * This method associates a refArg to this Argument.
	 * @param refArg The refArg being associated.
	 */
	void registerRefArg(RefArg refArg) {
        this.arguments.add(refArg);
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