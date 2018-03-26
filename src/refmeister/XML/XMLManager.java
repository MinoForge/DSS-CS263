package refmeister.XML;

import refmeister.entity.Argument;
import refmeister.entity.Idea;
import refmeister.entity.Library;

import java.util.*;

/**
 * Created by wesle on 3/25/2018.
 */
public class XMLManager {
    private Library lib;
    private Collection<Argument> args;
    private Collection<Idea> ideas;
    private Collection<String> associations;

    public XMLManager(Library lib){
        this.lib = lib;
        args = new HashSet<>();
        ideas = new HashSet<>();
        associations = new HashSet<>();
    }

    public void addArgument(Argument arg){
        args.add(arg);
    }

    public void addIdea(Idea idea){
        ideas.add(idea);
    }

    public void addAssociation(String xml){
        associations.add(xml);
    }

    private String getXMLUnformatted() {
        StringBuilder out = new StringBuilder();
        out.append(lib.getSaveString(this));

        out.append("<arguments>\n");
        for(Argument arg : args){
            out.append(arg.getSaveString(this));
        }
        out.append("</arguments>\n");

        out.append("<ideas>\n");
        for(Idea idea : ideas){
            out.append(idea.getSaveString(this));
        }
        out.append("</ideas>\n");

        out.append("<associations>\n");
        for(String assoc : associations){
            out.append(assoc);
        }
        out.append("</associations>\n");

        return out.toString();
    }

    public String getXML(){
        String[] unformatted = getXMLUnformatted().split("\n");
        StringBuilder builder = new StringBuilder("<?xml version=\"1.0\" " +
                "encoding=\"UTF-8\"?>\n<refmeister>\n");
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

            if (line.matches("^<[^\\/].*[^\\/]>$")){ // if line matches open
                indent++;
            }
        }

        builder.append("</refmeister>");
        return builder.toString();
    }

}
