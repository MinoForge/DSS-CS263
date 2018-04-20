package refmeister.management;


import refmeister.XML.FileManager;
import refmeister.controllers.Controller;
import refmeister.controllers.SingleLibraryController;
import refmeister.display.CLIDisplay;
import refmeister.display.Displayer;
//import refmeister.display.GUIDisplay;
import refmeister.entity.WorkingDirectory;

import java.io.File;
import java.nio.file.*;


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
        Displayer display = null;
        WorkingDirectory wDir = null;

        int argIndex = 0;
        while(argIndex < args.length) {
            if(args[argIndex].equals("-dir")) {
                directory = args[++argIndex];
            }

            argIndex++;
        }

        File dir = new File(DEFAULT_DIRECTORY);

        dir.mkdir();

        try {
            wDir = new WorkingDirectory(dir);
        } catch (AccessDeniedException e) {
            FileManager.getInstance().log(FileManager.Severity.MAJOR_ERROR, e);
        }

        Controller control = new SingleLibraryController(wDir);
        display = new CLIDisplay(control);

        FileManager.getInstance().start(false);
        FileManager.getInstance().log(FileManager.Severity.DEBUG, "Application Started");

        try {
            boolean quit = false;
            while (!quit) {
                display.displayCurrent();
                quit = display.pickOption();
//                System.out.println(quit);
            }
        } catch (Throwable e){
            System.out.println("Catastrophic error!");
            FileManager.getInstance().log(FileManager.Severity.MAJOR_ERROR, "Catastrophic error!");
            FileManager.getInstance().log(FileManager.Severity.MAJOR_ERROR, e);
        }
        FileManager.getInstance().log(FileManager.Severity.DEBUG, "Application Stop");
        FileManager.getInstance().stop();
    }
}
