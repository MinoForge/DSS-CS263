package refmeister.display.elements;

/**
 * Models an Observer in the Observer design pattern. Updates when a subject
 * notifies it's observer(s).
 * @author Brandon Townsend
 * @version 28 April 2018
 */
public interface RefObserver {

    /**
     * This observer is updated whenever it is notified from its subject.
     */
    void update();
}
