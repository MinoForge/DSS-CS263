package test;

import refmeister.XML.MalformedXMLException;
import refmeister.XML.XMLParser;
import refmeister.entity.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Test class to ensure that saving to XML works right
 */
public class XMLSaveTest {
    public static void main(String[] args){
        Library lib = new Library("GenericLibrary", "A demo library");
        Topic top = lib.addTopic("Topic1", "Description");
        Theme t1 = top.addTheme("Theme1", "Desc");
        Theme t2 = top.addTheme("Theme2", "desc2");
        Reference r1 = t1.addReference("Green Eggs and Ham", "Dr. Seuss");
        Reference r2 = t2.addReference("1984", "George Orwell");

        Argument arg = new Argument("Ham is good");
        Argument arg2 = new Argument("Arg2");

        r1.addArgument(arg, 5f);
        r1.addArgument(arg2, 0f);
        r2.addArgument(arg2, 2.5f);
        r1.addNote("Ham is delicious", "Like really eat it all the time");

        String xml = XMLParser.saveLibrary(lib);

        try {
            FileWriter writer = new FileWriter(new File("refmeister-wd/demo.rl"));
            writer.write(xml);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Library loaded = XMLParser.loadLibrary(xml);
            System.out.println(XMLParser.saveLibrary(loaded));
        } catch (MalformedXMLException e){
            System.out.println("Erroring tags: ");
            for(String tag : e.getTags()){
                System.out.println("\t" + tag);
            }
        }
    }
}
