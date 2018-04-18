package refmeister.entity.Interfaces;

import java.util.List;

/**
 * Created by Caleb on 4/14/2018.
 */
public interface Relatable extends Entity {
    void registerRelation(Relation r);
    void removeRelation(Relation r);

    List<Relation> getRelations();

}
