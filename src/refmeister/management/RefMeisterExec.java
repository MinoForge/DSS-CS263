package refmeister.management;

import refmeister.entity.Controller;
import refmeister.entity.WorkingDirectory;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Files;


/**
 * The main executable for the RefMeister application. Creates a controller and begins running.
 *
 * @author Peter Gardner
 * @version 26, 3, 2018
 *
 */
public class RefMeisterExec {

    public static final String DEFAULT_DIRECTORY = "refmeister-wd";

    public static void main(String[] args) {
        File directory = new File(DEFAULT_DIRECTORY);
        boolean goodDir = true;
        if(!Files.exists(FileSystems.getDefault().getPath("./" + DEFAULT_DIRECTORY))) {
            goodDir = directory.mkdir();
        }
        Controller control = new Controller(null);
        if(goodDir) {
            WorkingDirectory workingDir = new WorkingDirectory(directory);
            control.setWorkingDir(workingDir);
            System.out.println("Directory Good. WorkingDirectory Set.");
        }

        control.startUp();
        while(true) {
            control.displayMenu();
        }
    }
}
