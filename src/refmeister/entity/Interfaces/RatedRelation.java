package refmeister.entity.Interfaces;

/**
 * Represents a relation that is weighted.
 * @author Wesley Rogers
 * @version 20 April 2018
 */
public interface RatedRelation extends Relation {
    /**
     * Returns the float that contains the rating of this Relation.
     * @return the float that contains the rating of this Relation.
     */
    float getRating();

    /**
     * Sets the rating to a specified float.
     * @param rating the specified float to be set to the rating.
     */
    void setRating(float rating);

}
