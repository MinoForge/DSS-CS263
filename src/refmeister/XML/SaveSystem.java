package refmeister.XML;

import refmeister.entity.Library;

/**
 * Created by wesle on 3/25/2018.
 */
public class SaveSystem {

    public static Library loadLibrary(String xml){
        return null;
    }

    /**
     * Saves the given library into an XML file.
     * @param lib
     * @return
     */
    public static String saveLibrary(Library lib){
        return new XMLManager(lib).getXML();
    }
}
