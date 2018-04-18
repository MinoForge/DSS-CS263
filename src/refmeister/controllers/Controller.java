package refmeister.controllers;

import refmeister.entity.Interfaces.Displayable;
import refmeister.entity.Interfaces.Editable;

import java.io.File;
import java.util.List;

public interface Controller {

    List<String> displaySelected();

    void saveLibrary();

    void loadLibrary(String title);

    void loadLibrary(File file);

    boolean functionality(String choice);

    String[] getAttributeTitles();

    String[] getAttributes();

    void editAttribute(String attrTitle, String attrValue);

}
