package refmeister.display.elements.Interfaces;

import refmeister.display.elements.Interfaces.RefObserver;

/**
 * Models a Subject in the Observer Design pattern. Implements the adding of
 * observers to a list of observers, removing observers, and notifying the
 * list of observers.
 * @author Brandon Townsend
 * @version 28 April 2018
 */
public interface RefSubject<T extends RefObserver> {

    /**
     * Adds an observer to its list of observers.
     * @param ro The RefObserver object to be added.
     * @return true if the object was added, false otherwise.
     */
    boolean addObserver(RefObserver ro);

    /**
     * Removes an observer from its list of observers.
     * @param ro The RefObserver object to be removed.
     * @return true if the object was removed, false otherwise.
     */
    boolean removeObserver(RefObserver ro);

    /**
     * Notifies each observer in this subjects list of observers. This will
     * cause each observer to call their update method.
     */
    void notifyObservers(Object arg);
}
