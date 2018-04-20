package refmeister.display;

import refmeister.controllers.Controller;
import refmeister.entity.Interfaces.Displayable;

public interface Displayer {

    /**
     * This method displays all current information and options for the currently selected Entity.
     */
    void displayCurrent();

    String[] editMenu();


    /**
     * The method used to pick an option out of the choices available.
     * @return true if "quit" is chosen. False otherwise.
     */
    boolean pickOption();

}
