package refmeister.entity.Interfaces;

/**
 * This interface defines methods which must be implemented for the display of the program.
 * Currently only gets a String array for printing to the command line.
 */

public interface Displayable {

	/**
	 * Gets a String array representing the object for printing to the command line
	 * @return The String array.
	 */

	String getAttribute(String attribute);
	void setAttribute(String attribute, String contents);

	}