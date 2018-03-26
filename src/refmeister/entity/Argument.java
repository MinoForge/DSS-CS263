package refmeister.entity;

import java.util.ArrayList;
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
	 * Retrieves the ArrayList of RefArgs.
	 * @return The ArrayList of RefArgs.
	 */
	public List<RefArg> getRefArg() {
		return arguments;
	}

	/**
	 * Sets the ArrayList of RefArgs to a specified List of RefArgs.
	 * @param args The specified List of RefArgs.
	 */
	public void setRefArg(List<RefArg> args) {
		this.arguments = args;
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
	 * Arguments should have no children, so this method should return null.
	 * @return null, since Arguments do not have children.
	 */
	@Override
	public List<Editable> getChildren() {
		return null;
	}

	/**
	 * TODO Will edit this later
	 * @return
	 */
	@Override
	public String getSaveString() {
		return super.getSaveString("argument");
	}
}