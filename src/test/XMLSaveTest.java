package test;

import refmeister.XML.MalformedXMLException;
import refmeister.XML.SaveSystem;
import refmeister.XML.XMLParser;
import refmeister.entity.*;
import refmeister.entity.Interfaces.Entity;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Test class to ensure that saving to XML works right
 */
public class XMLSaveTest {
    public static void main(String[] args){
        Library lib = new Library("GenericLibrary", "A demo library");
        Topic top = lib.addTopic("Topic1", "Description");
        Theme t1 = (Theme) top.createChild("Theme1", "Desc");
        Theme t2 = (Theme) top.createChild("Theme2", "desc2");
        Reference r1 = (Reference) t1.createChild("Green Eggs and Ham", "Dr. Seuss");
        Reference r2 = (Reference) t2.createChild("1984", "George Orwell");

        Argument arg = new Argument("Ham is good");
        Argument arg2 = new Argument("Arg2");

        r1.addArgument(arg, 5f);
        r1.addArgument(arg2, 0f);
        r2.addArgument(arg2, 2.5f);
        r1.addNote("Ham is delicious", "Like really eat it all the time");

        SaveSystem.FILE_SYSTEM.start();
        SaveSystem.FILE_SYSTEM.setFileName("ssTest");
        SaveSystem.FILE_SYSTEM.save(lib);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        SaveSystem.FILE_SYSTEM.stop();

        try (Scanner s = new Scanner(new File("refmeister-wd/ssTest.rl"))){
            s.useDelimiter("\\Z");
            XMLParser sys = new XMLParser();
            Library loaded = sys.loadLibrary(s.next());
            System.out.println(XMLParser.saveLibrary(loaded));
        } catch (MalformedXMLException e){
            System.out.println("Error: " + e.getMessage());
            System.out.println("Erroring tags: ");
            if(e.getTags() != null)
                for(String tag : e.getTags()){
                    System.out.println("\t" + tag);
                }
        } catch (IOException e) {
            System.out.println("File failed");
        }
    }
}
