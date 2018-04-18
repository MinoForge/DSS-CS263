package refmeister.entity;

import refmeister.XML.Saveable;
import refmeister.XML.XMLManager;
import refmeister.entity.Interfaces.Relatable;
import refmeister.entity.Interfaces.Relation;

import java.util.Collections;
import java.util.List;

/**
 * This is an intermediate class modeling the links associating a Reference and an Idea.
 * @author Red Team/DevSquad Supreme
 * @version 17, 4, 2018
 */
class RefIdea implements Saveable, Relation {

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
		this.reference.registerRelation(this);
		this.idea.registerRelation(this);
	}

	/**
	 * Retrieves the Reference that is associated with this RefIdea's idea.
	 * @return The Reference that is associated with this RefIdea's idea.
	 */
	public Relatable getReference() {
	    return this.reference;
    }

    /**
	 * Associates this RefIdea's idea with a new specified reference.
	 * @param reference The new specified reference.
	 */
	public void setReference(Reference reference) {
	    this.reference = reference;
	    this.reference.registerRelation(this);
	}

	/**
	 * Retrieves the Idea that is associated with this RefIdea's reference.
	 * @return The Idea that is associated with this RefIdea's reference.
	 */
	@Override
	public Relatable getEntity() {
	    return this.idea;
	}

	/**
	 * Associates this RefIdea's reference with a new specified idea.
	 * @param idea The new specified idea.
	 */
	public void setEntity(Idea idea) {
	    this.idea = idea;
	    this.idea.registerRelation(this);
	}

	/**
	 * Looks in both the Reference and Idea that are linked to this RefIdea. When it finds the
	 * RefIdea that links them, it will remove the RefIdea from that ArrayList.
	 */
	public void destroy() {
	    this.reference.removeRelation(this);
	    this.idea.removeRelation(this);
	}

	@Override
	public List<Saveable> getSaveableChildren() {
		return Collections.emptyList();
	}

	/**
	 * Gets the XML representation of this saveable object. Saveable objects that are association
	 * classes should register their XML output with the XMLManager, and Argument/Ideas should
	 * also register with the XMLManager.
	 * @param manager   The XMLManager that this traversal is being used with.
	 * @return          The XML representation of this Saveable, with appropriate associations
	 *                  registered with the XML Manager.
	 */
	@Override
	public String getSaveString(XMLManager manager) {
		String xml = String.format("<refarg reference=\"%s\" entity=\"%s\" />\n",
				this.reference.getTitle(), this.idea.getTitle());

		manager.addEntity(this.idea);
		manager.addAssociation(xml);
		return null;
	}
}