package refmeister.display.elements.Interfaces;

/**
 * Models an Observer in the Observer design pattern. Selects and options
 * when a subject notifies its observer(s).
 * @author DevSquadSupreme (Red Team)
 * @version 3 May 2018
 */
public interface OptionsObserver {

    /**
     * Updates the observer with the specified option.
     * @param option The option of the observer to update.
     * @param args The list of the observers to update.
     */
    void selectOption(String option, Object... args);
}
