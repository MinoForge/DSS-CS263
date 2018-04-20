package refmeister.XML;

import refmeister.entity.Interfaces.Entity;
import refmeister.entity.Library;

import java.util.*;

/**
 * A Class designed to assist in turning a library system into an XML file. The XMLManager has
 * four parts: A library, a list of arguments found in the library, a list of ideas found in the
 * library, and a list of association tags found in the library. Using the four of these, the
 * XMLManager wraps and formats a library into a nice XML output.
 *
 * @author Wesley Rogers
 * @version 20 April, 2018
 */
public class XMLManager {
    /**
     * The library this XMLManager is wrapping.
     */
    private Library lib;

    /**
     * A set of all entities that request late embedding encountered when traversing the library.
     */
    private Collection<Entity> entities;


    /**
     * A set of all ideas encountered while traversing the library.
     */
    private Collection<String> associations;

    /**
     * Creates an XMLManager bound to the given library.
     * @param lib The library to wrap into XML.
     */
    public XMLManager(Library lib){
        this.lib = lib;
        entities = new HashSet<>();
        associations = new HashSet<>();
    }

    /**
     * Adds an entity to this Refmeister XML tag.
     * @param ent The entity to add
     */
    public void addEntity(Entity ent){
        entities.add(ent);
    }

    /**
     * Adds an association XML tag.
     * @param xml The XML association tag (RefArg or RefIdea) to add to the associations section.
     */
    public void addAssociation(String xml){
        associations.add(xml);
    }

    /**
     * Gets the unformatted (unindented) XML code.
     * @return The unformatted XML code.
     */
    private String getXMLUnformatted() {
        StringBuilder out = new StringBuilder();
        out.append(lib.getSaveString(this));

        out.append("<entity>\n");
        for(Entity e : entities){
            out.append(e.getSaveString(this));
            out.append("\n");
        }
        out.append("</entity>\n");

        out.append("<relation>\n");
        for(String association : associations){
            out.append(association);
        }
        out.append("</relation>\n");

        return out.toString();
    }

    /**
     * Gets this XMLManager formatted as a XML 1.0 formatted string.
     * @return The library wrapped by this XMLManager as an XML Document.
     */
    public String getXML(){
        String[] unformatted = getXMLUnformatted().split("\n");
        StringBuilder builder = new StringBuilder("<refmeister>\n");
        int indent = 1;
        for(String line : unformatted){
            if(line.matches("^</.*>$")){ //if line matches a close
                indent--;
            }

            for(int i = 0; i < indent; i++){
                builder.append("\t");
            }
            builder.append(line);
            builder.append("\n");

            if (line.matches("^<[^/].*[^/]>$")){ // if line matches open
                indent++;
            }
        }

        builder.append("</refmeister>");
        return builder.toString();
    }

}
