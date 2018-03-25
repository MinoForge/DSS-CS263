package refmeister.entity;

import java.util.ArrayList;

public class Topic extends Editable {
    private ArrayList<Editable> themes;
    private Editable parent;

    public Topic(String title, String description, Library parent, ArrayList<Editable> themes){
        this.setTitle(title);
        this.setDescription(description);
        this.parent = parent;
        this.themes = themes;
    }

    public Topic(String title, String description, Library parent){
        this(title, description, parent, new ArrayList<Editable>());
    }

    public Topic(String title, Library parent){
        this(title, "Unset Description", parent, new ArrayList<Editable>());
    }
	/**
	 * 
	 * @param title
	 * @param desc
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
	 * 
	 * @param theme
	 */
	public void deleteTheme(String theme) {
        for(Editable t : themes) {
            if(t.getTitle().equals(getTitle())) {
                themes.remove(t);
            }
        }
	}

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