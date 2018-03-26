package refmeister.entity;

import refmeister.XML.Saveable;
import refmeister.XML.XMLManager;

import java.util.ArrayList;
import java.util.List;

public class Topic extends Editable {
    private List<Editable> themes;
    private Editable parent;

    /**
     * Creates a topic.
     * @param title         The title of this topic
     * @param description   The description of this topic
     * @param parent        This topic's parent
     * @param themes        A list of this topic's themes
     */
    public Topic(String title, String description, Library parent, List<Editable> themes){
        this.setTitle(title);
        this.setDescription(description);
        this.parent = parent;
        this.themes = themes;
    }

    /**
     * Creates a topic.
     * @param title         The title of this topic
     * @param description   The description of this topic
     * @param parent        This topic's parent
     */
    public Topic(String title, String description, Library parent){
        this(title, description, parent, new ArrayList<Editable>());
    }

    /**
     * Creates a topic.
     * @param title     The title of this topic
     * @param parent    The parent library of this topic
     */
    public Topic(String title, Library parent){
        this(title, "<<Unset Description>>", parent, new ArrayList<Editable>());
    }
	/**
	 * Adds a theme to this topic.
	 * @param title The title of the topic
	 * @param desc  The description of the topic
	 */
	public Theme addTheme(String title, String desc) {
		// TODO - implement Topic.addTheme
        for(Editable t : themes) {
            if(t.getTitle().equals(title)) {
                return null;
            }
        }
        Theme newTheme = new Theme(title, desc, this);
        themes.add(newTheme);
        return newTheme;
	}

	/**
	 * Deletes a theme with the given title.
	 * @param theme The title of the theme to remove.
	 */
	public void deleteTheme(String theme) {
        themes.removeIf(ed -> ed.getTitle().equals(theme));
	}

    /**
     * Sets the themes of this topic.
     * @param themes A list of all themes that this topic should be
     *               associated with.
     */
    public void setThemes(List<Editable> themes) {
        this.themes = themes;
    }

    /**
     * Gets this topic's parent.
     * @return the topic's parent.
     */
    public Editable getParent() {
        return parent;
    }

    /**
     * Sets this editable's parent.
     * @param parent The topic's new parent.
     */
    public void setParent(Editable parent) {
        this.parent = parent;
    }

    @Override
    public List<Editable> getChildren() {
        return themes;
    }

    @Override
    public String toString() {
        return "Topic{" +
                "title='" + getTitle() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", themes=" + themes +
                '}';
    }

    @Override
    public String getSaveString(XMLManager manager) {
        return super.getSaveString("topic", manager);
    }

    /**
     * Gets a list of this topic's XML children
     * @return A list of this topic's children.
     */
    public List<Saveable> getSaveableChildren() {
        return new ArrayList<>(themes);
    }
}