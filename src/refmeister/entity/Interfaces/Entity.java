package refmeister.entity.Interfaces;

import refmeister.XML.Saveable;

import java.util.List;

/**
 *
 */
public interface Entity extends Saveable, Comparable<Entity>{
    String getTitle();
    String getDescription();

    Entity getParent();
    void registerChild(Entity e);
    Entity removeChild(Entity e);
    List<Entity> getEntityChildren();
    void setParent(Entity e);
    void delete();

    void sort(String order);
    @Override
    int compareTo(Entity o);
}
