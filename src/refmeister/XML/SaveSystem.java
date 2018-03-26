package refmeister.XML;

import refmeister.entity.Library;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wesle on 3/25/2018.
 */
public class SaveSystem {

    public static Library loadLibrary(String xml){
        return null;
    }

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
     * @param lib
     * @return
     */
    public static String saveLibrary(Library lib){
        return new XMLManager(lib).getXML();
    }

    private static class Pair<T>{
        T one;
        T two;
    }
}
