package refmeister.display.elements.Interfaces;

/**
 * Models a subject in the Observer Design pattern. Implements the adding of
 * observers to a list of observers, removing observers, and notifying the
 * list of observers.
 * @author DevSquadSupreme (Red Team)
 * @version 3 May 2018
 */
public interface OptionsSubject<T extends OptionsObserver> {

    /**
     * Adds an observer to its list of observers.
     * @param oo The OptionsObserver object to be added.
     * @return true if the object was added, false otherwise.
     */
    boolean addObserver(T oo);

    /**
     * Removes an observer from its list of observers.
     * @param oo The OptionsObserver object to be removed.
     * @return true if the object was removed, false otherwise.
     */
    boolean removeObserver(T oo);

    /**
     * Notifies each observer in this subjects list of observers. This will
     * cause each observer to call their selectOption method.
     */
    void notifyObservers(String option, Object... args);
}
