package refmeister.entity.Interfaces;

import refmeister.XML.Saveable;

/**
 *
 */
public interface Entity extends Saveable{
    String getTitle();
    String getDescription();

    Entity getParent();

    void registerRelation(Relation r);
    void registerChild(Entity e);

    void removeRelation(Relation r);
    void removeChild(Entity e);

    boolean equals(Object o);
}
