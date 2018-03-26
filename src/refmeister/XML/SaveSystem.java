package refmeister.XML;

import refmeister.entity.*;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * A class managing the loading and saving of an instance of refmeister using the XML file format.
 *
 * @author Wesley Rogers
 */
public class SaveSystem { //This is the second parser I've had to write this semester.

    private Library root;
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
        //Get every line as an array, trimmed.
        String[] lines = Stream.of(xml.split("\n")).map(String::trim).toArray(String[]::new);
        SaveSystem sys = new SaveSystem();

        ArrayDeque<Editable> parents = new ArrayDeque<>(); //A stack to keep track of parents
        for(String tag : lines){
            if(sys.isEditableTag(tag)) {
                String type = sys.getTagType(tag);
                Pair<String> pair = sys.getTitleDescription(tag);
                if(pair != null){
                    Editable made = sys.make(type, pair.one, pair.two, parents.peekLast());
                    if(made != null)
                        parents.addLast(made);
                }
            } else if (!parents.isEmpty()){
                parents.removeLast();
            } else if (sys.isRefArg(tag)){
                sys.makeRefArg(tag);
            } else if (sys.isRefIdea(tag)){
                sys.makeRefIdea(tag);
            }
        }

        return sys.root;
    }

    private boolean isRefArg(String tag){
        Pattern tagType = Pattern.compile("^<refarg reference=\".*?\" argument=\".*?\" rating=\".*?\" />$");
        Matcher matcher = tagType.matcher(tag);
        return matcher.find();
    }

    private boolean isRefIdea(String tag){
        Pattern tagType = Pattern.compile("^<refidea reference=\".*?\" idea=\".*?\" />$");
        Matcher matcher = tagType.matcher(tag);
        return matcher.find();
    }

    private void makeRefArg(String tag){
        Pattern tagType = Pattern.compile("^<refarg reference=\"(.*?)\" argument=\"(.*?)\" rating=\"(.*?)\" />$");
        Matcher matcher = tagType.matcher(tag);
        if(matcher.find()){
            Reference ref = references.get(matcher.group(1));
            Argument arg = args.get(matcher.group(2));
            float weight = Float.parseFloat(matcher.group(3));
            ref.addArgument(arg, weight);
        }
    }

    private void makeRefIdea(String tag){
        Pattern tagType = Pattern.compile("^<refidea reference=\"(.*?)\" idea=\"(.*?)\" />$");
        Matcher matcher = tagType.matcher(tag);
        if(matcher.find()){
            Reference ref = references.get(matcher.group(1));
            Idea idea = ideas.get(matcher.group(2));
            ref.addIdea(idea);
        }
    }

    private String getTagType(String tag){
        Pattern tagType = Pattern.compile("^<(.*?) .*>$");
        Matcher matcher = tagType.matcher(tag);
        if(matcher.find()){
            return matcher.group(1);
        }

        return null;
    }

    private boolean isEditableTag(String tag){
        Pattern pat = Pattern.compile("^<.*title=\"(.*?)\" description=\"(.*?)\".*>$");
        //actual black magic
        Matcher matcher = pat.matcher(tag);
        return matcher.find();
    }

    /**
     * Makes the requested object, and then returns it if it's a valid parent.
     * @param type
     * @param title
     * @param desc
     * @param parent
     * @return
     */
    private Editable make(String type, String title, String desc, Editable parent){
        switch(type) {
            case "library":
                root = new Library(title, desc);
                return root;

            case "topic":
                if(!(parent instanceof Library))
                    throw new RuntimeException("Malformed XML Document: topic parent not library");
                Topic top = new Topic(title, desc, (Library) parent);
                topics.put(title, top);
                return top;

            case "theme":
                if(!(parent instanceof Topic))
                    throw new RuntimeException("Malformed XML Document: theme parent not topic");
                Theme the = new Theme(title, desc, (Topic) parent);
                themes.put(title, the);
                return the;

            case "reference":
                if(!(parent instanceof Theme))
                    throw new RuntimeException("Malformed XML Document: reference parent not theme");
                Reference ref = new Reference(title, desc, (Theme) parent);
                references.put(title, ref);
                return ref;

            case "note":
                if(!(parent instanceof Reference))
                    throw new RuntimeException("Malformed XML Document: note parent not reference");
                Note note = new Note(title, desc, (Reference)parent);
                return null;

            case "argument":
                Argument arg = new Argument(title, desc);
                args.put(title, arg);
                return null;

            case "idea":
                Idea idea = new Idea(title, desc);
                ideas.put(title, idea);
                return null;

            default:
                return null;
        }
    }

    /**
     * Gets the title and description of a given XML tag.
     * @param tag a string representing an XML opening tag of an editable.
     * @return    A pair containing the title and description.
     */
    private Pair<String> getTitleDescription(String tag){
        Pattern titlePattern = Pattern.compile("^<.*title=\"(.*?)\".*>$"); //actual black magic
        Pattern descriptionPattern = Pattern.compile("^<.*description=\"(.*?)\".*>$");
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

    private String getTagName(String tag){
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
