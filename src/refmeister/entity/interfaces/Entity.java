package refmeister.entity.interfaces;

import refmeister.XML.Saveable;

import java.util.List;

/**
 * Models an Entity class.
 * @author Caleb Dinehart
 * @version 20 April 2018
 */
public interface Entity extends Saveable, Comparable<Entity>{
    /**
     * Returns the title of this entity.
     * @return the title of this entity.
     */
    String getTitle();

    /**
     * Returns the description of this entity.
     * @return the description of this entity.
     */
    String getDescription();

    /**
     * Returns the parent of this entity.
     * @return the parent of this entity.
     */
    Entity getParent();

    /**
     * Registers a passed Entity as a child of the current entity.
     * @param e the Entity to be set as a child.
     */
    void registerChild(Entity e);

    /**
     * Removes a passed Entity from the current entity's list of children.
     * @param e the passed Entity to be removed.
     * @return The Entity that was removed.
     */
    Entity removeChild(Entity e);

    /**
     * Returns the list of children that are entities.
     * @return the list of children that are entities.
     */
    List<Entity> getEntityChildren();

    /**
     * Set's a current entities parent to the Entity passed in.
     * @param e The entity to be set to the parent.
     */
    void setParent(Entity e);

    /**
     * Removes an entity from our data structure.
     */
    void delete();

    /**
     * Sorts a list of entities in the order specified.
     * @param order The order in which to sort.
     */
    void sort(String order);

    /**
     * Compares this Entity to another entity that is passed in.
     * @param o The entity to be compared against.
     * @return an integer based on whether o is &lt;, &gt; or == this entity.
     */
    @Override
    int compareTo(Entity o);
}
