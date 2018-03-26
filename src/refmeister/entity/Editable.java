package refmeister.entity;

import refmeister.XML.Saveable;
import refmeister.XML.XMLManager;

import java.security.InvalidParameterException;
import java.util.List;

/**
 * @author
 * @version
 */
public abstract class Editable implements Displayable, Saveable {

    /**  */
	private String title;
	/**  */
	private String description;
	/**  */
	private Editable parent;

    /**
     *
     * @param attribute
     * @return
     */
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

    /**
     *
     * @param attribute
     * @param contents
     */
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

    /**
     *
     * @return
     */
    public Editable getParent() {
        return parent;
    }

    /**
     *
     * @param parent
     */
    public void setParent(Editable parent) {
        this.parent = parent;
    }

    /**
     *
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Creates an XML tag for an editable object, given it's tag name.
     * @param tagName   The tag name of this tag.
     * @param manager
     * @return          A formatted XML String.
     */
    protected String getSaveString(String tagName, XMLManager manager){
        StringBuilder out = new StringBuilder();
        out.append(String.format("<%s title=\"%s\" description=\"%s\">\n",
                tagName, this.getTitle(), this.getDescription()));

        for(Saveable child : this.getSaveableChildren()){
            String childString = child.getSaveString(manager);
            if(childString != null){
                out.append(childString);
            }
        }

        out.append("</");
        out.append(tagName);
        out.append(">\n");
        return out.toString();
    }

    /**
     *
     * @param edits
     */
    public void edit(String[] edits) {
        setTitle(edits[0]);
        setDescription(edits[1]);
    }

    /**
     * Gets an array of elements that this object has, with title being at
     * index 0, description at index 1, and each child as a sub index.
     * @return
     */
    public String[] display() {
        String[] display = new String[7 + getChildren().size()];
        int i = 0;
        display[i++] = getTitle();
        display[i++] = getDescription();
        display[i++] = "e: Edit this Object";
        display[i++] = "u: Select Parent";
        display[i++] = "c: Create a New Child";
        display[i++] = "q: Quit RefMeister";
        display[i++] = null;
        for(Editable e : getChildren()) {
            display[i++] = e.getTitle();
        }
        return display;
    }

    /**
     *
     * @param title
     * @param description
     * @return
     */
    public abstract boolean createChild(String title, String description);

    /**
     *
     * @return
     */
    public abstract List<Editable> getChildren();
}