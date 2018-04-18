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

    String[] getAttributeTitles();

    String[] getAttributes();

    void createLibrary();

    void createLibrary(String title, String description);

    void deleteRoot();

    void traverseUp();

    void delete();

    void editAttribute(String attrTitle, String attrValue);

    void sendFunc(String funcName, String... params);

    void viewDir();



}
