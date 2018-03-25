package refmeister.entity;

import java.util.ArrayList;

public class Library extends Editable {

    private String title;
    private String description;
    private ArrayList<Topic> topics;


    public Library(String title, String description, ArrayList<Topic> topics) {
        this.title = title;
        this.description = description;
        this.topics = topics;
    }

    public Library(String title, String description) {
        this(title, description, new ArrayList<Topic>());
    }

    public Library(String title) {
        this(title, "Unset Description", new ArrayList<Topic>());
    }

	/**
	 * 
	 * @param title
	 * @param desc
	 */
	public Topic addTopic(String title, String desc) {
		// TODO - implement Library.addTopic
        for(Topic t : topics) {
            if(t.getTitle().equals(title)) {
                return null;
            }
        }
        Topic newTopic = new Topic(title, desc);
        topics.add(newTopic);
        return newTopic;
		//throw new UnsupportedOperationException();
	}

	public void deleteTopic(String title) {
	    for(Topic t : topics) {
	        if(t.getTitle().equals(title)) {
	            topics.remove(t);
            }
        }
	}

    public String getTitle() {
	    return this.title;
    }

    public String getDescription() {
	    return this.description;
    }

    /**
     *
     * @param newTitle
     */
    public void setTitle(String newTitle) {
        this.title = newTitle;
    }

    /**
     *
     * @param newDesc
     */
    public void setDescription(String newDesc) {
        this.description = newDesc;
    }

    public String display() {

    }

    public ArrayList<Saveable> getChildren() {
        return topics;
    }

    public String getSaveString() {
        //TODO: Implement this.
    }

}