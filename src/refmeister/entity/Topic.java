package refmeister.entity;

import java.util.ArrayList;

public class Topic extends Editable {
    private ArrayList<Theme> themes;
    private Library parent;

    public Topic(String title, String description, Library parent, ArrayList<Theme> themes){
        this.setTitle(title);
        this.setDescription(description);
        this.parent = parent;
        this.themes = themes;
    }

    public Topic(String title, String description, Library parent){
        this(title, description, parent, new ArrayList<Theme>());
    }

    public Topic(String title, Library parent){
        this(title, "Unset Description", parent, new ArrayList<Theme>());
    }
	/**
	 * 
	 * @param title
	 * @param desc
	 */
	public Theme addTheme(String title, String desc) {
		// TODO - implement Topic.addTheme
        for(Theme t : themes) {
            if(t.getTitle().equals(title)) {
                return null;
            }
        }
        Theme newTheme = new Theme(title, desc, this);
        themes.add(newTheme);
        return newTheme;
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param theme
	 */
	public void deleteTheme(String theme) {
		// TODO - implement Topic.deleteTheme
        for(Theme t : themes) {
            if(t.getTitle().equals(getTitle())) {
                themes.remove(t);
            }
        }
		throw new UnsupportedOperationException();
	}


    public ArrayList<Theme> getThemes() {
        return themes;
    }

    public void setThemes(ArrayList<Theme> themes) {
        this.themes = themes;
    }

    public Library getParent() {
        return parent;
    }

    public void setParent(Library parent) {
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