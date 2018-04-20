package refmeister.display;

/**
 * Interface to model the different types of displays.
 * @author DevSquadSupreme (Red Team)
 * @version 20 April 2018
 */
public interface Displayer {

    /**
     * This method displays all current information and options for the currently selected Entity.
     */
    void displayCurrent();

    /**
     * This method is used to edit an array of variables which are passed to it.
     * @return The new values after editing takes place.
     */
    String[] editMenu();


    /**
     * The method used to pick an option out of the choices available.
     * @return true if "quit" is chosen. False otherwise.
     */
    boolean pickOption();

}
