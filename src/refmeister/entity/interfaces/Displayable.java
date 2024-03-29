package refmeister.entity.interfaces;


import java.util.List;

/**
 * This interface defines methods which must be implemented for the display of the program.
 * Currently only gets a String array for printing to the command line.
 * @author DevSquad
 * @version 20 April, 2018
 */

public interface Displayable {

	/**
	 * Gets a String array representing the object for printing to the command line
     * @param attribute the attribute to get
	 * @return          The String representing the attribute.
	 */
	String getAttribute(String attribute);

    /**
     * Sets the attribute to the given value.
     * @param attribute the attribute to set
     * @param contents  the new value of the attribute
     */
	void setAttribute(String attribute, String contents);

    /**
     * Lists the options that this Displayable can do, such as edit, display, etc.
     * @return  a list of all options.
     */
	List<String> listOptions();

	/**
	 * Returns the list of functions the displayable object can perform.
	 * @return List of Strings of the functions the displayable object can perform.
	 */
	List<String> getFunc();
}