package refmeister.entity.Interfaces;

/**
 *
 */
public interface Entity {
    String getTitle();
    String getDescription();

    Entity getParent();

    void registerRelation(Relation r);
    void registerChild(Entity e);

    void removeRelation(Relation r);
    void removeChild(Entity e);

    boolean equals(Object o);
}
