package refmeister.entity.Interfaces;

import refmeister.XML.Saveable;

/**
 * Relation interface that models an associations between two entities.
 * @author Wesley Rogers
 * @version 20 April 2018
 */
public interface Relation extends Saveable {
    /**
     * Returns the reference that this relation shares with another entity.
     * @return the reference that this relation shares with another entity.
     */
    Relatable getReference();

    /**
     * Returns the entity that shares a relation with a reference.
     * @return the entity that shares a relation with a reference.
     */
    Relatable getEntity();

    /**
     * Disassociates the relation between two entities.
     */
    void destroy();
}
