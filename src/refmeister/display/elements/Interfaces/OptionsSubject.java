package refmeister.display.elements.Interfaces;

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
