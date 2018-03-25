package refmeister.entity;

import java.util.ArrayList;

/**
 * The Library models a library which contains topics. It must have a
 * specified title.
 * @author Red Team/DevSquad Supreme
 * @version 25, 3, 2018
 */
public class Library extends Editable {

    /** The title of this library. Must be specified*/
    private String title;
    /** The description of this library. */
    private String description;
    /** The Topics which are children of this Library. */
    private ArrayList<Editable> topics;


    /**
     * Constructor for a Library object. Takes a String to be specified as the title, a String to
     * be specified as the description, and an ArrayList to be this Libraries topics.
     * @param title The String to be specified as this Library's title.
     * @param description The String to be specified as this Library's description.
     * @param topics The ArrayList which is the list of this Library's topics.
     */
    public Library(String title, String description, ArrayList<Editable> topics) {
        this.title = title;
        this.description = description;
        this.topics = topics;
    }

    /**
     * A constructor for a Library object with a specified title and description, but with no
     * specified list of Topics.
     * @param title The String to be specified as this Library's title.
     * @param description The String to be specified as this Library's
     *                    description.
     */
    public Library(String title, String description) {
        this(title, description, new ArrayList<Editable>());
    }

    /**
     * A default constructor where the user only specifies that they want a title for their
     * Library. The description and list of Topics will be set to default values.
     * @param title The String to be specified as this Library's title.
     */
    public Library(String title) {
        this(title, "Unset Description", new ArrayList<Editable>());
    }

	/**
	 * Adds a new topic to this Library's ArrayList of topics.
	 * @param title The title of the topic to be added.
	 * @param desc The description of the topic to be added.
	 */
	public Topic addTopic(String title, String desc) {
		// TODO - implement Library.addTopic
        for(Editable t : topics) {
            // If a topic already has the same title of the one we are trying to add, don't add it.
            if(t.getTitle().equals(title)) {
                return null;
            }
        }
        Topic newTopic = new Topic(title, desc, this);
        topics.add(newTopic);
        return newTopic;
		//throw new UnsupportedOperationException();
	}

    /**
     * Deletes a topic for this Library's ArrayList of topics.
     * @param title The title of the topic to be removed.
     */
	public void deleteTopic(String title) {
	    for(Editable t : topics) {
	        // If the title of the topic is found, removed the topic from the list.
	        if(t.getTitle().equals(title)) {
	            topics.remove(t);
            }
        }
	}

    public String display() {
        return null;
    }

    public ArrayList<Editable> getChildren() {
        return topics;
    }

    public String getSaveString() {
        throw new UnsupportedOperationException();
        //TODO: Implement this with XML stuff.
    }

}