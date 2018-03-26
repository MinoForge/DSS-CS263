package refmeister.management;

import refmeister.entity.Controller;
import refmeister.entity.WorkingDirectory;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Files;

public class RefMeisterExec {

    public static final String DEFAULT_DIRECTORY = "refmeister-wd";

    public static void main(String[] args) {
        File directory = new File(DEFAULT_DIRECTORY);
        boolean goodDir = true;
        if(!Files.exists(FileSystems.getDefault().getPath(DEFAULT_DIRECTORY))) {
            goodDir = directory.mkdir();
        }
        if(goodDir) {
            WorkingDirectory workingDir = new WorkingDirectory(directory);
            Controller control = new Controller(workingDir);
        }
    }
}
