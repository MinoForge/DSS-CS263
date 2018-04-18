package refmeister.entity;

import refmeister.XML.Saveable;
import refmeister.XML.XMLManager;
import refmeister.entity.Interfaces.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The Argument class models an argument that would be associated with one or multiple References.
 * @author Brandon Townsend
 * @version 17, 4, 2018
 */
public class Argument extends Editable implements Relatable {

	/** ArrayList of RefArgs that show what this Argument instance is associated with. */
	private List<RatedRelation> refArgs;

	/**
	 * Constructor for an Argument that is given a specified title, description and an ArrayList
	 * of RefArgs.
	 * @param title The specified String to be set as this Argument's title.
	 * @param desc The specified String to be set as this Argument's description.
	 * @param arguments The specified ArrayList to be set to arguments.
	 */
	public Argument(String title, String desc, List<RatedRelation> arguments) {
		setTitle(title);
		setDescription(desc);
		this.refArgs = arguments;
	}

	/**
	 * Constructor which sets the ArrayList of RefArgs to a default value, while still passing
	 * the others.
	 * @param title The specified String to be set as this Argument's title.
	 * @param desc The specified String to be set as this Argument's description.
	 */
	public Argument(String title, String desc) {
		this(title, desc, new ArrayList<>());
	}

	/**
	 * Constructor which sets the description to a default value, while still passing the others.
	 * @param title The specified String to be set as this Argument's title.
	 * @param args The specified ArrayList to be set to this Argument's title.
	 */
	public Argument(String title, List<RatedRelation> args) {
		this(title, "Unset Description", args);
	}

	/**
	 * Default Constructor that is passed the title. All others are set to default values.
	 * @param title The specified String to be set as this Argument's title.
	 */
	public Argument(String title) {
		this(title, "Unset Description", new ArrayList<>());
	}

    /**
     * Default Constructor. At this time, we do not support creating an argument without
     * specifying at least a title.
     */
	public Argument() {
	    throw new UnsupportedOperationException("Must specify at least a title for this Argument.");
    }
	/**
	 * Calculates the average of the ratings of all the RefArgs in arguments.
	 * @return The average of the ratings of all the RefArgs in arguments.
	 */
	public float getArgAverage() {
		float average = 0;
		for(Relation ra : refArgs) {
		    if(ra instanceof RatedRelation)
			    average += ((RatedRelation) ra).getRating();
		}
		return average / refArgs.size();
	}


	public void delete() {
		destroy();
	}


	/**
	 * Disassociates all of this Argument's RefArgs from this argument.
	 */
	public void destroy() {
		for(RatedRelation ra : refArgs) {
			ra.destroy();
		}
	}

    /**
     * Checks equality between this argument and a passed in object.
     * @param o The object to have its equality is checked against this Argument.
     * @return true if they are equal, false otherwise.
     */
	@Override
	public boolean equals(Object o) {
	    if(this == o) {
	        return true;
        }
        if(o instanceof Argument) {
	        Argument temp = (Argument) o;
	        return this.getTitle().equals(temp.getTitle());
        }
        return false;
    }

	/**
	 * Arguments should have no children, so this method should return an empty list.
	 * @return An empty list, since Arguments do not have children.
	 */
	@Override
	public List<Saveable> getSaveableChildren() {
		return Collections.emptyList();
	}

	/**
	 * Arguments should have no children, so this method should return an empty list.
	 * @return An empty  list, since Argurments do not have children.
	 */
	public List<Entity> getEntityChildren() {
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
        return String.format("<argument title=\"%s\" description=\"%s\" />",
				getTitle(), getDescription());
	}

	/**
	 * Does nothing in Argument.
	 * @param title The title for the child.
	 * @param description The description for the child.
	 * @return true if the child was able to be created, false otherwise.
	 */
    @Override
    public Entity createChild(String title, String description) {
        return null;
    }

	/**
	 * This method associates a refArg to this Argument.
	 * @param refArg The refArg being associated.
	 */
	void registerRefArg(RefArg refArg) {
        this.refArgs.add(refArg);
	}

	public void registerRatedRelation(RatedRelation r){
        this.refArgs.add(r);
    }
    @Override
    public void registerRelation(Relation r) {
        throw new UnsupportedOperationException("no no no");
    }

    @Override
    public void removeRelation(Relation r) {
        throw new UnsupportedOperationException("nope not today");
    }

    public void removeRatedRelation(RatedRelation r){
        this.refArgs.removeIf(r::equals);
    }
    /**
     * Returns a list of strings that will be displayed for the menu.
     * @return A list of strings that will be displayed for the menu.
     */
    public List<String> listOptions(){
        List<String> options = new ArrayList<>();
        options.add("Delete Argument");
        options.add("Edit Argument");
        options.add("Change Relation");
        options.add("Change Rating");
        options.add("View Directory");
        options.add("Move Up");
        return options;
    }
    /**
     * Returns a list of attributes that contains the title and description of
     * the argument.
     * @return A list of attributes that contains the title and description of
     * the argument.
     */
    @Override
    public List<String> listAttributes(){
        List<String> attr = new ArrayList<>();
        attr.add(this.getTitle());
        attr.add(this.getDescription());

        return attr;
    }

    /**
     * Returns an Array List of the attribute labels
     * @return the Array List of Strings of the labels
     */
    @Override
    public List<String> listAttributeTitles(){
        List<String> labels = new ArrayList<>();
        labels.add("Title");
        labels.add("Description");
        labels.add("Rating");
        return labels;
    }

    /**
     * Returns the list of Relations for this argument.
     * @return the List of Relations for this argument.
     */
    public List<Relation> getRelations(){
        List<Relation> result = new ArrayList<>(this.refArgs);
        return result;
    }

    public void changeRating(String refTitle, float newRating){
        for(RatedRelation r : refArgs){
            if(r.getReference().getTitle().equals(refTitle)){
                r.setRating(newRating);
            }
            else{
                System.out.println("Reference Doesn't Exist.");
            }
        }
    }
}
