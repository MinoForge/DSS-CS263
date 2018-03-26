package refmeister.XML;

import refmeister.entity.*;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * A class managing the loading and saving of an instance of refmeister using the XML file format.
 */
public class SaveSystem { //This is the second parser I've had to write this semester.

    private HashMap<String, Topic> topics;
    private HashMap<String, Theme> themes;
    private HashMap<String, Reference> references;
    private HashMap<String, Argument> args;
    private HashMap<String, Idea> ideas;

    private SaveSystem(){
        topics = new HashMap<>();
        themes = new HashMap<>();
        references = new HashMap<>();
        args = new HashMap<>();
        ideas = new HashMap<>();
    }

    public static Library loadLibrary(String xml){
        String[] lines = Stream.of(xml.split("\n")).map(String::trim).toArray(String[]::new);
        return null;
    }



    /**
     * Gets the title and description of a given XML tag.
     * @param tag a string representing an XML opening tag of an editable.
     * @return    A pair containing the title and description.
     */
    private static Pair<String> getTitleDescription(String tag){
        Pattern titlePattern = Pattern.compile("<.*title=\"(.*?)\".*>");
        Pattern descriptionPattern = Pattern.compile("<.*description=\"(.*?)\".*>");
        Matcher titleMatcher = titlePattern.matcher(tag);
        Matcher descriptionMatcher = descriptionPattern.matcher(tag);
        if(titleMatcher.find() && descriptionMatcher.find()){
            Pair<String> pair = new Pair<>();
            pair.one = titleMatcher.group(1);
            pair.two = descriptionMatcher.group(1);
            return pair;
        }
        return null;
    }

    /**
     * Saves the given library into an XML file.
     * @param lib the library to save to an XML file.
     * @return    A string representing the given XML file.
     */
    public static String saveLibrary(Library lib){
        return new XMLManager(lib).getXML();
    }

    /**
     * A simple pair class, used to pair a title and description from a tag.
     * @param <T> The pair type
     */
    private static class Pair<T>{
        T one;
        T two;
    }
}
