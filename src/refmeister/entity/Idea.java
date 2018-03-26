package refmeister.entity;

import java.util.*;

public class Idea extends Editable {

	/** ArrayList of RefIdeas that show what this Idea instance is associated with. */
	private List<RefIdea> ideas;

	/**
	 * Constructor for an Idea that is given a specified title, description, and an ArrayList of
	 * RefIdeas.
	 * @param title The specified String to be set as this Idea's title.
	 * @param desc The specified String to be set as this Idea's description.
	 * @param ideas The specified ArrayList to be set to this Idea's ideas.
	 */
	public Idea(String title, String desc, List<RefIdea> ideas) {
		setTitle(title);
		setDescription(desc);
		this.ideas = ideas;
	}

	/**
	 * Constructor which sets the ArrayList of RefIdeas to a default value, while still passing
	 * the others.
	 * @param title The specified String to be set as this Idea's title.
	 * @param desc The specified String to be set as this Idea's description.
	 */
	public Idea(String title, String desc) {
		this(title, desc, new ArrayList<RefIdea>());
	}

	/**
	 * Constructor which sets the description to a default value, while still passing the others.
	 * @param title The specified String to be set as this Idea's title.
	 * @param ideas The specified ArrayList to be set to this Idea's ideas.
	 */
	public Idea(String title, List<RefIdea> ideas) {
		this(title, "Unset Description", ideas);
	}

	/**
	 * Default Constructor that is passed the title. All others are set to default values.
	 * @param title The specified String to be set as this Idea's title.
	 */
	public Idea(String title) {
		this(title, "Unset Description", new ArrayList<RefIdea>());
	}

	/**
	 * TODO
	 * @return
	 */
	public List<RefIdea> getRefIdea() {
		return ideas;
	}

	/**
	 * TODO
	 * @param ideas
	 */
	public void setRefIdea(List<RefIdea> ideas) {
		this.ideas = ideas;
	}

	/**
	 * TODO
	 */
	public void destroy() {
		for(RefIdea ri : ideas) {
			ri.destroy();
		}
	}

	@Override
	public List<Editable> getChildren() {
		return null;
	}

	@Override
	public String getSaveString() {
		return super.getSaveString("idea");
	}
}