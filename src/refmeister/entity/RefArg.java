package refmeister.entity;

import refmeister.XML.Saveable;
import refmeister.XML.XMLManager;
import refmeister.entity.Interfaces.RatedRelation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This is an intermediate class modeling the links associating a Reference and an Argument.
 * @author Red Team/DevSquad Supreme
 * @version 25, 3, 2018
 */
class RefArg implements Saveable, RatedRelation {

	/** The Reference to which this object is associating an Argument to. */
	private Reference reference;
	/** The Argument which this object is associating a Reference to. */
	private Argument argument;
	/** The rating for a given argument. */
	private float rating;

	/**
	 * Constructor for a RefArg object. Links a specified Reference to a specified Argument and
	 * sets a rating for link.
	 * @param ref The specified Reference to be linked to an Argument.
	 * @param argument The specified Argument to be linked to a Reference.
	 * @param rating The rating for this specified argument link.
	 */
	public RefArg(Reference ref, Argument argument, float rating) {
		this.reference = ref;
		this.argument = argument;
		ref.registerRelation(this);
		argument.registerRefArg(this);
		this.rating = rating;
	}

	/**
	 * Retrieves the Reference that is associated with this RefArg's argument
	 * @return The Reference that is associated with this RefArg's argument.
	 */
	public Reference getReference() {
		return this.reference;
	}

	@Override
	public Argument getEntity() {
		return null;
	}

	/**
	 * Associates this RefArg's argument with a new specified reference.
	 * @param reference The new specified reference.
	 */
	public void setReference(Reference reference) {
		this.reference = reference;
	}

	/**
	 * Retrieves the Argument that is associated with this RefArg's reference.
	 * @return The Argument that is associated with this RefArg's reference.
	 */
	public Argument getArgument() {
		return this.argument;
	}

	/**
	 * Associates this RefArg's reference with a new specified argument.
	 * @param argument The new specified argument.
	 */
	public void setArgument(Argument argument) {
		this.argument = argument;
	}

	/**
	 * Retrieves the rating of this single RefArg.
	 * @return The rating of this single RefArg.
	 */
	public float getRating() {
		return this.rating;
	}

	/**
	 * Sets the rating of this RefArg to a new specified rating.
	 * @param rating The new specified rating.
	 */
	public void setRating(float rating) {
		this.rating = rating;
	}

	/**
	 * Looks in both the Reference and Argument that are linked to this RefArg. When it finds the
	 * RefArg that links them, it will remove the RefArg from that ArrayList.
	 */
	public void destroy() {
		reference.removeRelation(this);
		argument.removeRelation(this);
	}
	/**
	 * A list of all of this Saveable's saveable children. This method should <bold>NEVER</bold>
	 * return null.
	 * @return a list of all of this object's Saveable children.
	 */
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
	    String xml = String.format("<refarg reference=\"%s\" argument=\"%s\" rating=\"%f\" />\n",
                this.getReference().getTitle(), this.getArgument().getTitle(), this.getRating());

	    manager.addArgument(getArgument());
	    manager.addAssociation(xml);
	    return null;
    }
}