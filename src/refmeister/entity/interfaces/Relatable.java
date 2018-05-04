package refmeister.entity.interfaces;

import java.util.List;

/**
 * Represents an object that can be related to with a relation.
 * @author Wesley Rogers
 * @version 20 April 2018
 */
public interface Relatable extends Entity {
    /**
     * Associates a Relation between specified Entities.
     * @param r The Relation to associate.
     */
    void registerRelation(Relation r);

    /**
     * Disassociates a Relation between specified Entities.
     * @param r The Relation to disassociate.
     */
    void removeRelation(Relation r);

    /**
     * Associates a RatedRelation between specified Entities.
     * @param r The RatedRelation to associate.
     */
    void registerRatedRelation(RatedRelation r);

    /**
     * Disassociates a RatedRelation between specified Entities.
     * @param r The RatedRelation to disassociate.
     */
    void removeRatedRelation(RatedRelation r);

    /**
     * Returns a list of Relations between Entities.
     * @return a list of Relations between Entities.
     */
    List<Relation> getRelations();

}
