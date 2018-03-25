package refmeister.entity;

import java.security.InvalidParameterException;
import java.util.ArrayList;

public abstract class Editable implements Displayable, Saveable {

	private String title;
	private String description;

    public String getAttribute(String attribute){
        if(attribute.equals("title")){
            return this.getTitle();
        }
        else if(attribute.equals("description")){
            return this.getDescription();
        }else{
            return null;
        }
    }
    public void setAttribute(String attribute, String contents){
        if(attribute.equals("title")){
            this.setTitle(contents);
        }
        else if(attribute.equals("description")){
            this.setDescription(contents);
        }else{
            throw new InvalidParameterException();
        }
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Creates an XML tag for an editable object, given it's tag name.
     * @param tagName   The tag name of this tag.
     * @return          A formatted XML String.
     */
    protected String getSaveString(String tagName){
        StringBuilder out = new StringBuilder();
        out.append(String.format("<%s title=\"%s\" description=\"%s\">\n",
                tagName, this.getTitle(), this.getDescription()));

        for(Editable child : this.getChildren()){
            String childString = child.getSaveString();
            if(childString != null){
                out.append("\t");
                out.append(childString);
            }
        }

        out.append("</");
        out.append(tagName);
        out.append(">\n");
        return out.toString();
    }
}