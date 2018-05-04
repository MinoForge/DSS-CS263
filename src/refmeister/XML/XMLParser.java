package refmeister.XML;

import refmeister.entity.*;
import refmeister.entity.interfaces.Entity;
import refmeister.entity.interfaces.Relation;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * A class managing the loading and saving of an instance of refmeister using the XML file format.
 *
 * @author Wesley Rogers
 * @version 20 April, 2018
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

    private EntityFactory entityFactory;

    private RelationFactory relationFactory;

    /**
     * Creates a save system.
     */
    public XMLParser(){
        entities = new HashMap<>();
        entityFactory = this::makeEntity;
        relationFactory = this:: makeRelation;
    }

    public XMLParser(EntityFactory ef, RelationFactory rf){
        entities = new HashMap<>();
        entityFactory = ef;
        relationFactory = rf;
    }

    /**
     * Loads the given XML as a refmeister library.
     * @param xml   An XML string
     * @return      The library, fully loaded from string.
     */
    public Library loadLibrary(String xml){
        //Get every line as an array, trimmed.
        String[] lines = Stream.of(xml.split("\n")).map(String::trim).toArray(String[]::new);

        ArrayDeque<Entity> parents = new ArrayDeque<>();  //A stack to keep track of parents
        ArrayDeque<String> openedTags = new ArrayDeque<>(); // Another stack to make sure the tags
        boolean relations = false;
        for(String tag : lines){// are aligned
            if(!(tag.startsWith("<") && tag.endsWith(">")))
                throw new MalformedXMLException("Non-tag in parsed file");

            if(isOpeningTag(tag)){
                String tagType = getTagType(tag);
                if (tagType != null) {
                    if(tagType.equals("relation"))
                        relations = true;
                    openedTags.addLast(tagType);
                }
            }

            if(!relations) {
                Entity made = entityFactory.makeEntity(tag, parents.peekLast());
                if(made != null)
                    parents.add(made);
            }  else {
                //By the specification of relation, we don't need to keep it.
                relationFactory.makeRelation(tag);
            }

            if(isClosingTag(tag)) {
                String tagType = getTagType(tag);
                String removed = openedTags.removeLast();
                if(tagType != null && !tagType.equals(removed)){
                    throw new MalformedXMLException("Parent tag and closing tag do not match",
                            tagType, removed);
                }
                if(tag.matches("^</.*[^/]>") && !parents.isEmpty()){
                    parents.removeLast();
                }
            }
        }

        if (!openedTags.isEmpty()) {
            String[] unclosed = openedTags.toArray(new String[0]);
            throw new MalformedXMLException("Tag(s) not closed", unclosed);
        }

        return root;
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
            Pattern tagType = Pattern.compile("^</?(.*)>$");
            Matcher matcher = tagType.matcher(tag);
            if(matcher.find()){
                return matcher.group(1);
            }
        }

        throw new MalformedXMLException("Tag does not have a type", tag);
    }

    /**
     * Makes the requested object, and then returns it if it's a valid parent.
     * @param tag       the passed tag
     * @param parent    the parent of this tag, if it has one. Is null if no parent exists.
     * @return          the entity made
     */
    private Entity makeEntity(String tag, Entity parent){
        String type = getTagType(tag);
        Pair<String> pair = getTitleDescription(tag);
        if(pair == null)
            return null;
//            throw new MalformedXMLException("Entity tag does not have a title or description", tag);
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
                return new Topic(title, desc, parent);

            case "theme":
                return new Theme(title, desc,(Topic) parent);

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

    private Pair<String> getRelationStrings(String tag){
        Pattern titlePattern = Pattern.compile("^<.* reference=\"(.*?)\".*>$"); //actual black magic
        Pattern descriptionPattern = Pattern.compile("^<.* entity=\"(.*?)\".*>$");
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

    private float getRating(String tag){
        Pattern titlePattern = Pattern.compile("^<.* rating=\"(.*?)\".*>$"); //actual black magic
        Matcher titleMatcher = titlePattern.matcher(tag);
        if(titleMatcher.find()){
            return Float.parseFloat(titleMatcher.group(1));
        }
        return Float.NEGATIVE_INFINITY;
    }

    private Relation makeRelation(String tag){
        String type = getTagType(tag);
        Pair<String> pair = getRelationStrings(tag);

        if(pair == null)
            return null;

        Entity ref = this.entities.get(pair.one);
        Entity en = this.entities.get(pair.two);

        switch (type) {
            case "refarg":
                if (ref instanceof Reference && en instanceof Argument) {
                    try {
                        return ((Reference) ref).addArgument((Argument) en, getRating(tag));
                    } catch (NumberFormatException e){
                        throw new MalformedXMLException("Rating is not valid float!", tag);
                    }
                } else {
                    throw new MalformedXMLException("refarg tag does not have valid ref. " +
                            "or arg.", tag);
                }

            case "refidea":
                if (ref instanceof Reference && en instanceof Argument) {
                    try {
                        return ((Reference) ref).addArgument((Argument) en, getRating(tag));
                    } catch (NumberFormatException e){
                        throw new MalformedXMLException("Rating is not valid float!", tag);
                    }
                } else {
                    throw new MalformedXMLException("refarg tag does not have valid ref. " +
                            "or arg.", tag);
                }
        }
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
     * A simple pair class, used to pair a title and description from a tag.
     * @param <T> The pair type
     */
    private static class Pair<T>{
        T one;
        T two;
    }

    /**
     * Creates an entity given a tag and the entity created by this element's parent.
     */
    @FunctionalInterface
    public interface EntityFactory {
        /**
         * Creates an entity from the given tag and parent, or returns null if it failed.
         * @param tag       The tag being passed
         * @param parent    The parent of this entity
         * @return          The entity constructed, or null if the created entity is not a valid
         *                      parent.
         */
        Entity makeEntity(String tag, Entity parent);
    }

    /**
     * Creates a relation given a tag.
     */
    @FunctionalInterface
    public interface RelationFactory {
        /**
         * Makes a relation out of a tag.
         * @param tag   the tag being passed
         * @return      the relation
         */
        Relation makeRelation(String tag);
    }
}
