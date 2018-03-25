package refmeister.entity;

import java.security.InvalidParameterException;
import java.util.ArrayList;

public abstract class Editable implements Displayable, Saveable {

	private String title;
	private String description;
    private ArrayList<Editable> children;

    public String getAttribute(String attribute){
        if(attribute == "title"){
            return this.getTitle();
        }
        else if(attribute == "description"){
            return this.getDescription();
        }else{
            return null;
        }
    }
    public void setAttribute(String attribute, String contents){
        if(attribute == "title"){
            this.setTitle(contents);
        }
        else if(attribute == "description"){
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
}