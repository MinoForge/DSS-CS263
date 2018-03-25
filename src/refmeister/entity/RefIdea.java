package refmeister.entity;

/**
 * This is an intermediate class modeling the links associating a Reference and an Idea.
 * @author Red Team/DevSquad Supreme
 * @version 25, 3, 2018
 */
class RefIdea {

	/** The Reference to which this object is associating an Idea to. */
	private Reference reference;
	/** The Idea which this object is associating a Reference to. */
	private Idea idea;

	/**
	 * Constructor for a RefIdea object. Links a specified Reference to a specified Idea.
	 * @param ref The specified Reference to be linked to an Idea.
	 * @param idea The specified Idea to be linked to a Reference.
	 */
	public RefIdea(Reference ref, Idea idea) {
		this.reference = ref;
		this.idea = idea;
	}

	/**
	 * Retrieves the Reference that is associated with this RefIdea's idea.
	 * @return The Reference that is associated with this RefIdea's idea.
	 */
	public Reference getReference() {
		return reference;
	}

	/**
	 * Associates this RefIdea's idea with a new specified reference.
	 * @param reference The new specified reference.
	 */
	public void setReference(Reference reference) {
		this.reference = reference;
	}

	/**
	 * Retrieves the Idea that is associated with this RefIdea's reference.
	 * @return The Idea that is associated with this RefIdea's reference.
	 */
	public Idea getIdea() {
		return idea;
	}

	/**
	 * Associates this RefIdea's reference with a new specified idea.
	 * @param idea The new specified idea.
	 */
	public void setIdea(Idea idea) {
		this.idea = idea;
	}

	/**
	 * Looks in both the Reference and Idea that are linked to this RefIdea. When it finds the
	 * RefIdea that links them, it will remove the RefIdea from that ArrayList.
	 */
	public void destroy() {
		for(RefIdea ri : reference.getIdeas()) {
			if(this == ri) {
				reference.getIdeas().remove(this);
			}
		}
		for(RefIdea ri : idea.getRefs()) {
			if(this == ri) {
				idea.getRefs().remove(this);
			}
		}
	}

}