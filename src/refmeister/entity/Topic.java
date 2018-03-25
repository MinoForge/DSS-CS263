package refmeister.entity;

import java.util.ArrayList;

public class Topic extends Editable {
    private ArrayList<Editable> themes;
    private Editable parent;

    /**
     * Creates a topic.
     * @param title         The title of this topic
     * @param description   The description of this topic
     * @param parent        This topic's parent
     * @param themes        A list of this topic's themes
     */
    public Topic(String title, String description, Library parent, ArrayList<Editable> themes){
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
     * Gets a list of
     * @return
     */
    public ArrayList<Editable> getChildren() {
        return themes;
    }

    public void setThemes(ArrayList<Editable> themes) {
        this.themes = themes;
    }

    public Editable getParent() {
        return parent;
    }

    public void setParent(Editable parent) {
        this.parent = parent;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "Topic{" +
                "title='" + getTitle() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", themes=" + themes +
                '}';
    }

    @Override
    public String getSaveString() {
        return null;
    }


    @Override
    public String display() {
        return this.toString();
    }
}