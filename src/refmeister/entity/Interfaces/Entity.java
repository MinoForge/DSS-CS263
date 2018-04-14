package refmeister.entity.Interfaces;

/**
 *
 */
public interface Entity {
    String getTitle();
    String getDescription();

    Entity getParent();

    void registerChild(Entity e);
    void removeChild(Entity e);

}
