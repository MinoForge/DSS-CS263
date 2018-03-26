package refmeister.XML;

import refmeister.entity.Library;

/**
 * Created by wesle on 3/25/2018.
 */
public class SaveSystem {
    private static XMLManager manager;

    public Library loadLibrary(String xml){
        return null;
    }

    public String saveLibrary(Library lib){
        return new XMLManager(lib).getXML();
    }
}
