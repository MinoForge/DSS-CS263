package refmeister.XML;

import refmeister.entity.*;
import refmeister.entity.Interfaces.Editable;
import refmeister.entity.Interfaces.Entity;
import refmeister.entity.Interfaces.Relation;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    private HashMap<String, Entity> entities;

    private List<String> entityNames; //note topic theme

    private EntityFactory entityFactory;

    private RelationFactory relationFactory;

    /**
     * Creates a save system.
     */
    private XMLParser(){
        entities = new HashMap<>();
        entityNames = new ArrayList<>();
        entityFactory = this::makeEntity;
        relationFactory = this:: makeRelation;
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

        ArrayDeque<Entity> parents = new ArrayDeque<>();  //A stack to keep track of parents
        ArrayDeque<String> openedTags = new ArrayDeque<>(); // Another stack to make sure the tags
        boolean relations = false;
        for(String tag : lines){// are aligned
            if(!(tag.startsWith("<") && tag.endsWith(">")))
                throw new MalformedXMLException("Non-tag in parsed file");

            if(sys.isOpeningTag(tag)){
                String tagType = sys.getTagType(tag);
                if (tagType != null) {
                    if(tagType.equals("relations"))
                        relations = true;
                    openedTags.addLast(tagType);
                }
            }

            if(!relations) {
                Entity made = sys.entityFactory.makeEntity(tag, parents.peekLast());
                if(made != null)
                    parents.add(made);
            }  else {
                //By the specification of relation, we don't need to keep it.
                sys.relationFactory.makeRelation(tag);
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
     * Gets the tag type
     * @param tag the tag
     * @return    the name of the tag
     */
    private String getTagType(String tag){
        if(tag.contains(" ")) {
            Pattern tagType = Pattern.compile("^<(.*?) .*>$");
            Matcher matcher = tagType.matcher(tag);
            if (matcher.find()) {
                return matcher.group(1);
            }
        } else { //This is probably a closing tag or a section tag
            Pattern tagType = Pattern.compile("^<\\/?(.*)>$");
            Matcher matcher = tagType.matcher(tag);
            if(matcher.find()){
                return matcher.group(1);
            }
        }

        throw new MalformedXMLException("Tag does not have a type", tag);
    }

    /**
     * Makes the requested object, and then returns it if it's a valid parent.
     * @param tag the passed tag
     * @param parent the parent of this tag, if it has one. Is null if no parent exists.
     */
    private Entity makeEntity(String tag, Entity parent){
        String type = getTagType(tag);
        Pair<String> pair = getTitleDescription(tag);
        if(pair == null)
            throw new MalformedXMLException("Entity tag does not have a title or description");
        String title = pair.one;
        String desc = pair.two;
        switch(type) {
            case "library":
                if(root == null) {
                    root = new Library(title, desc);
                    return root;
                } else {
                    throw new MalformedXMLException("Multiple Library XML Tags.");
                }

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
                if(entities.get(title) == null){
                    Reference ref = new Reference(title, desc, (Theme) parent);
                    entities.put(title, ref);
                    return ref;
                } else {
                    throw new MalformedXMLException("Reference with same name already exists.");
                }

            case "note":
                if(!(parent instanceof Reference))
                    throw new MalformedXMLException("note parent not reference");
                Note note = new Note(title, desc, (Reference) parent);
                return null;

            case "argument":
                if(entities.get(title) == null) {
                    Argument arg = new Argument(title, desc);
                    entities.put(title, arg);
                    return null;
                } else {
                    throw new MalformedXMLException("Argumment with given name already exists");
                }

            case "idea":
                if(entities.get(title) == null) {
                    Idea idea = new Idea(title, desc);
                    entities.put(title, idea);
                    return null;
                } else {
                    throw new MalformedXMLException("Idea with given name already exists.");
                }

            default:
                throw new MalformedXMLException("Unknown tag type: " + type);
        }
    }

    public Relation makeRelation(String tag){

        return null;
    }

    /**
     * Gets the title and description of a given XML tag.
     * @param tag a string representing an XML opening tag of an editable.
     * @return    A pair containing the title and description.
     */
    private Pair<String> getTitleDescription(String tag){
        Pattern titlePattern = Pattern.compile("^<.* title=\"(.*?)\".*>$"); //actual black magic
        Pattern descriptionPattern = Pattern.compile("^<.* description=\"(.*?)\".*>$");
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

    @FunctionalInterface
    public interface EntityFactory {
        /**
         * Creates an entity from the given tag and parent, or returns null if it failed.
         * @param tag
         * @param parent
         * @return
         */
        Entity makeEntity(String tag, Entity parent);
    }

    @FunctionalInterface
    public interface RelationFactory {
        Relation makeRelation(String tag);
    }
}
