package refmeister.management;


import refmeister.entity.WorkingDirectory;

import java.io.File;
import java.nio.file.AccessDeniedException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Paths;


/**
 * The main executable for the RefMeister application. Creates a controller and begins running.
 *
 * @author Peter Gardner
 * @version 26, 3, 2018
 *
 */
public class RefMeisterExec {

    /** The default directory, which is created relative to this class file when run. */
    public static final String DEFAULT_DIRECTORY = "refmeister_saved";

    /**
     * The main function, which starts and runs the Controller class.
     * @param args Any commandline arguments which might be implemented.
     */
    public static void main(String[] args) {
        String directory = DEFAULT_DIRECTORY;
        //Displayer display = null;
        WorkingDirectory wDir = null;

        int argIndex = 0;
        while(argIndex < args.length) {
            if(args[argIndex].equals("-dir")) {
                directory = args[++argIndex];
            }

            argIndex++;
        }

        try {
            wDir = new WorkingDirectory(Paths.get(directory).toFile());
        } catch (AccessDeniedException ade) {
            System.out.println(ade.getMessage());
        }

        //display = new CLIDisplay(wDir);

        boolean quit = false;
        while(!quit) {
        //    display.displayCurrent();
        //    quit = display.pickOption();
        }
    }
}
