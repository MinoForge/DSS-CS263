package test;

import refmeister.XML.XMLParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Tests a bad XML file
 */
public class XMLBadLoadTest {
    public static void main(String[] args){
        String badXML = "";
        try (Scanner scanner = new Scanner(new File("bad_xml/test1.rl"))) {
            scanner.useDelimiter("\\Z");
            badXML = scanner.next();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
