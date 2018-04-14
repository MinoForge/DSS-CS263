package refmeister.entity.Interfaces;

import refmeister.XML.Saveable;

import java.util.List;

/**
 *
 */
public interface Entity extends Saveable{
    String getTitle();
    String getDescription();

    Entity getParent();

    void registerChild(Entity e);
    void removeChild(Entity e);

    List<Entity> getEntityChildren();
}
