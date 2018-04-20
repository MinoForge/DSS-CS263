package refmeister.entity.Interfaces;

import refmeister.XML.Saveable;

/**
 * Created by wesley on 4/4/2018
 */
public interface Relation extends Saveable {
    Relatable getReference();
    Relatable getEntity();

    void destroy();
}
