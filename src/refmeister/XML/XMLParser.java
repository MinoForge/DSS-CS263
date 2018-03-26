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
public class XMLParser { //This is the second parser I've had to write this semester.

    /**
     * The library root for the system.
     */
    private Library root;

    /**
     * A map of titles and their associated references
     */
    private HashMap<String, Reference> references;

    /**
     * A map of argument titles and their associated arguments
     */
    private HashMap<String, Argument> args;

    /**
     * A map of idea titles and their associated idea
     */
    private HashMap<String, Idea> ideas;

    /**
     * Creates a save system.
     */
    private XMLParser(){
        references = new HashMap<>();
        args = new HashMap<>();
        ideas = new HashMap<>();
    }

    /**
     * Loads the given XML as a refmeister library.
     * @param xml   An XML string
     * @return      The library, fully loaded from string.
     */
    public static Library loadLibrary(String xml){
        //Get every line as an array, trimmed.
        String[] lines = Stream.of(xml.split("\n")).map(String::trim).toArray(String[]::new);
        XMLParser sys = new XMLParser();

        ArrayDeque<Editable> parents = new ArrayDeque<>(); //A stack to keep track of parents
        ArrayDeque<String> openedTags = new ArrayDeque<>();
        for(String tag : lines){
            // "<?" is the start of an XML version tag, which is not useful
            if(sys.isOpeningTag(tag)){
                String tagType = sys.getTagType(tag);
                if (tagType != null) {
                    openedTags.addLast(tagType);
                }
            }

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

            if(sys.isClosingTag(tag)) {
                String tagType = sys.getTagType(tag);
                String removed = openedTags.removeLast();
                if(tagType != null && !tagType.equals(removed)){
                    throw new MalformedXMLException("Parent tag and closing tag do not match",
                            tagType, removed);
                }
            }
        }

        if (!openedTags.isEmpty()) {
            String[] unclosed = openedTags.toArray(new String[0]);
            throw new MalformedXMLException("Tag(s) not closed", unclosed);
        }

        return sys.root;
    }

    private boolean isOpeningTag(String s){
        return s.matches("^<[^/].*>$");
    }

    private boolean isClosingTag(String s){
        return s.startsWith("</") || s.endsWith(" />");
    }

    /**
     * Returns true if this tag is a refarg.
     * @param tag the tag to check
     * @return    true if this is a refarg tag, false otherwise
     */
    private boolean isRefArg(String tag){
        Pattern tagType = Pattern.compile("^<refarg reference=\".*?\" argument=\".*?\" rating=\".*?\" />$");
        Matcher matcher = tagType.matcher(tag);
        return matcher.find();
    }

    /**
     * Returns true if this taf is a refidea
     * @param tag the tag to check
     * @return    true if this tag is a refidea, false otherwise
     */
    private boolean isRefIdea(String tag){
        Pattern tagType = Pattern.compile("^<refidea reference=\".*?\" idea=\".*?\" />$");
        Matcher matcher = tagType.matcher(tag);
        return matcher.find();
    }

    /**
     * Makes a refarg from a tag
     * @param tag the tag to make a ref arg from
     */
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

    /**
     * the tag to make a refidea from
     * @param tag the tag to make from
     */
    private void makeRefIdea(String tag){
        Pattern tagType = Pattern.compile("^<refidea reference=\"(.*?)\" idea=\"(.*?)\" />$");
        Matcher matcher = tagType.matcher(tag);
        if(matcher.find()){
            Reference ref = references.get(matcher.group(1));
            Idea idea = ideas.get(matcher.group(2));
            ref.addIdea(idea);
        }
    }

    /**
     * Gets the tag type
     * @param tag the tag
     * @return    the name of the tag
     */
    private String getTagType(String tag){
        if(tag.contains(" ")) {
            Pattern tagType = Pattern.compile("^<?(.*?) .*>$");
            Matcher matcher = tagType.matcher(tag);
            if (matcher.find()) {
                return matcher.group(1);
            }
        } else { //This is probably a closing tag then
            Pattern tagType = Pattern.compile("^<\\/?(.*)>$");
            Matcher matcher = tagType.matcher(tag);
            if(matcher.find()){
                return matcher.group(1);
            }
        }

        return null;
    }

    /**
     * Returns true if this is an editable tag
     * @param tag the tag
     * @return    true if this tag is an editable
     */
    private boolean isEditableTag(String tag){
        Pattern pat = Pattern.compile("^<.*title=\"(.*?)\" description=\"(.*?)\".*>$");
        //actual black magic
        Matcher matcher = pat.matcher(tag);
        return matcher.find();
    }

    /**
     * Makes the requested object, and then returns it if it's a valid parent.
     * @param type      The type of the tag
     * @param title     the tag title
     * @param desc      the editable's description
     * @param parent    the parent of this editable
     * @return          the new parent object, or null if the made object is not a valid parent
     */
    private Editable make(String type, String title, String desc, Editable parent){
        switch(type) {
            case "library":
                root = new Library(title, desc);
                return root;

            case "topic":
                if(!(parent instanceof Library))
                    throw new MalformedXMLException("topic parent not library");
                return new Topic(title, desc, (Library) parent);

            case "theme":
                if(!(parent instanceof Topic))
                    throw new MalformedXMLException("theme parent not topic");
                return new Theme(title, desc, (Topic) parent);

            case "reference":
                if(!(parent instanceof Theme))
                    throw new MalformedXMLException("reference parent not theme");
                Reference ref = new Reference(title, desc, (Theme) parent);
                references.put(title, ref);
                return ref;

            case "note":
                if(!(parent instanceof Reference))
                    throw new MalformedXMLException("note parent not reference");
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
                throw new MalformedXMLException("Unknown tag type: " + type);
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
