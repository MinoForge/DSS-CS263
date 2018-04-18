package test;

import refmeister.XML.FileManager;
import refmeister.XML.XMLParser;
import refmeister.entity.Library;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Tests a bad XML file
 */
public class XMLBadLoadTest {
    public static void main(String[] args){
        FileManager m = FileManager.getInstance();
        m.start();
        try{
            Thread.sleep(1000);
            m.log(FileManager.Severity.DEBUG, "Finished sleeping");
            new ArrayList<String>().remove(0);

        } catch (Exception e){
            m.log(FileManager.Severity.MAJOR_ERROR, e);
        }

        m.log(FileManager.Severity.DEBUG, "Exited try catch");

        m.stop();
    }
}
