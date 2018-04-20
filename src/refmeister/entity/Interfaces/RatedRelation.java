package refmeister.entity.Interfaces;

/**
 * Represents a relation that is weighted.
 */
public interface RatedRelation extends Relation {
    float getRating();
    void setRating(float rating);

}
