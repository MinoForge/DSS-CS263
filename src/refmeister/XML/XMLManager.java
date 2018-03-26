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

    public String getXML() {
        StringBuilder out = new StringBuilder("<refmeister>\n");
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

        return out.append("</refmeister>").toString();
    }

}
