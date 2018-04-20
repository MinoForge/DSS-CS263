package refmeister.entity.Interfaces;

import java.util.List;

/**
 * Represents an object that can be related to with a relation.
 */
public interface Relatable extends Entity {
    void registerRelation(Relation r);
    void removeRelation(Relation r);
    void registerRatedRelation(RatedRelation r);
    void removeRatedRelation(RatedRelation r);
    List<Relation> getRelations();

}
