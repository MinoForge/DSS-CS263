package refmeister.controllers;

import refmeister.entity.Interfaces.Displayable;
import refmeister.entity.Interfaces.Editable;

import java.io.File;

public interface Controller {

    public String[] displaySelected();

    public void saveLibrary();

    public void loadLibrary(String title);

    public void loadLibrary(File file);

    public boolean choose(String choice);


}
