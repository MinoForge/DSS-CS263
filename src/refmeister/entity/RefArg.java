package refmeister.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * This is an intermediate class modeling the links associating a Reference and an Argument.
 * @author Red Team/DevSquad Supreme
 * @version 25, 3, 2018
 */
class RefArg implements Saveable {

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
		this.rating = rating;
	}

	/**
	 * Retrieves the Reference that is associated with this RefArg's argument
	 * @return The Reference that is associated with this RefArg's argument.
	 */
	public Reference getReference() {
		return this.reference;
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
		RefArg temp = new RefArg(null, null, 0);
		for(RefArg ra : reference.getArguments()) {
			if(this == ra) {
				temp = ra;
			}
		}
		reference.getArguments().remove(temp);

		for(RefArg ra : argument.getRefArg()) {
			if(this == ra) {
				temp = ra;
			}
		}
		reference.getArguments().remove(temp);
	}

    @Override
    public List<Saveable> getSaveableChildren() {
        return new ArrayList<>();
    }

    @Override
    public String getSaveString() {
	    return String.format("<refarg reference=\"%s\" argument=\"%s\" rating=\"%f\" />",
                this.getReference().getTitle(), this.getArgument().getTitle(), this.getRating());
    }
}