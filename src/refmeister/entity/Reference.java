package refmeister.entity;

import refmeister.XML.Saveable;
import refmeister.XML.XMLManager;
import refmeister.entity.Interfaces.Entity;
import refmeister.entity.Interfaces.Editable;
import refmeister.entity.Interfaces.Relatable;
import refmeister.entity.Interfaces.Relation;

import java.util.*;

/**
 * TODO
 * @author
 * @version
 */
public class Reference extends Editable implements Relatable {

    /**
     * The list containing all of reference's data, such as author, date of publication, etc. in
     * (currently) no order.
     */
	private String[][] refData;

    /**
     * A list containing associations to all ideas that this reference is associated with.
     */
	private List<Relation> relations;

    /**
     * The theme parent of this reference.
     */
	private Entity parent;

    /**
     * Creates a reference
     * @param title     the title of this reference
     * @param desc      the description of this reference
     * @param refData   the reference data of this reference
     * @param ideas     a list of associations to ideas that this reference is associated with
     * @param notes     a list of all notes with this reference as a parent
     * @param parent    the parent of this reference
     */
	private Reference(String title, String desc, String[][] refData, List<Relation> ideas,
					 List<Note> notes, Theme parent) {
		setTitle(title);
		setDescription(desc);
		this.refData = refData;
		this.relations = ideas;
		this.parent = parent;
		parent.registerChild(this);
	}

    /**
     * Creates a reference.
     * @param title     the title of this reference
     * @param desc      the description of this reference
     * @param refData   A string containing all reference data
     * @param parent    the parent of this reference
     */
	public Reference(String title, String desc, String[][] refData, Theme parent) {
		this(title, desc, refData, new ArrayList<Relation>(),
				new ArrayList<Note>(), parent);
	}

    /**
     * Creates a reference.
     * @param title     the title of this reference
     * @param refData   the reference data of this reference
     * @param parent    the parent theme of this parent
     */
	public Reference(String title, String[][] refData, Theme parent) {
		this(title, "Unset Description", refData, new ArrayList<Relation>(),
				new ArrayList<Note>(), parent);
	}

    /**
     * Creates a reference.
     * @param title         the title of this reference
     * @param description   the description of this reference
     * @param parent        the parent of this reference
     */
	public Reference(String title, String description, Theme parent) {
		this(title, description, new String[13][], new ArrayList<Relation>(),
				new ArrayList<Note>(), parent);
	}

    /**
     * Default Constructor. At this time, we do not support creating a Reference without at least
     * a title.
     */
	public Reference() {
	    throw new UnsupportedOperationException("Must specify at least a title for this Reference" +
                ".");
    }

    /**
     * Gets the reference data.
     * @return the refdata
     */
	public String[] getRefData() {
		return null;
	}

    /**
     * Sets the refdata
     * @param refData the refdata
     */
	public void setRefData(String[][] refData) {
		this.refData = refData;
	}


    /**
     * Generates a citation.
     * @param format the citation format
     * @return       the formatted output
     */
	public String generateCite(String format) {
	    String cite = "";
	    if(format.toLowerCase().equals("mla")) {
            cite = generateMLA();
        } else if(format.toLowerCase().equals("apa")) {
	        cite = generateAPA();
        }
        return cite;
	}

	/**
	 * Generates an MLA citation based on the information in refData.
	 * @return A String containing the MLA citation.
	 */
	public String generateMLA() {
	    StringBuilder cite = new StringBuilder();
	    for(int i = 0; i < refData[0].length; i++) {
	        cite.append(refData[0][i]);
	        cite.append(", ");
	        if(!refData[1][i].equals("")) {
	            cite.append(". ");
            }
            cite.append(refData[2][i]);
	        cite.append(". ");
        }
        cite.append(refData[3][0]);
	    cite.append(", \"");
	    cite.append(refData[4][0]);
	    cite.append("\". ");
	    cite.append(refData[5][0]);
	    for(int i = 6; i < refData.length; i++) {
	        if(!refData[i][0].equals("")) {
                cite.append(", ");
                cite.append(refData[i][0]);
            }
        }
        return cite.toString();
    }

    /**
     * Generates and APA citation based on the information in refData.
     * @return A String containing the APA citation.
     */
    public String generateAPA() {
	    StringBuilder cite = new StringBuilder();
        for(int i = 0; i < refData[0].length; i++) {
            cite.append(refData[0][i]);
            cite.append(", ");
            if(!refData[1][i].equals("")) {
                cite.append(". ");
            }
            cite.append(refData[2][i]);
            cite.append(". ");
        }
        cite.append("(");
        cite.append(refData[8][0]);
        cite.append(")");
        cite.append(refData[3][0]);
        cite.append(", ");
        cite.append(refData[4][0]);
        cite.append(". ");
        return cite.toString();
    }


    /**
     * Associates an argument with this reference
     * @param arg       the argument to associate
     * @param rating    the strength of the argument
     * @return          the RefArg created
     */
	public RefArg addArgument(Argument arg, float rating){
		return new RefArg(this, arg, rating);
	}

    /**
     * Associates an idea.
     * @param idea  the idea to associate
     * @return      the created RefIdea
     */
	public RefIdea addIdea(Idea idea){
		return new RefIdea(this, idea);
	}


	/**
	 * A list of all of this Saveable's saveable children. This method should <bold>NEVER</bold>
	 * return null.
	 * @return a list of all of this object's Saveable children.
	 */
	@Override
	public List<Saveable> getSaveableChildren() {
        List<Saveable> out = new ArrayList<>(children);
        out.addAll(relations);
	    return out;
	}

	/**
	 * Retrieves the list of this Entity's children.
	 * @return The list of this Entity's children.
	 */
	public List<Entity> getEntityChildren() {
		return children;
	}

	/**
	 * Creates a child, specifically a Note for this Entity.
	 * @param title The title for the child.
	 * @param description The description for the child.
	 * @return true if the child was able to be created, false otherwise.
	 */
    @Override
    public Entity createChild(String title, String description) {
        for(Entity t : children) {
            if(t.getTitle().equals(title)) {
                return t;
            }
        }
        return new Note(title, description, this);
    }

    public Entity createArgument(String title, String description){
        for(Entity t : children) {
            if (t.getTitle().equals(title)) {
                return t;
            }
        }

        return new Argument(title, description);
    }

    public Entity createIdea(String title, String description){
        for(Entity t : children){
            if (t.getTitle().equals(title)) {
                return t;
            }
        }
        return new Argument(title, description);
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
		return super.getSaveString("reference", manager);
	}

	//These are to prevent orphaned notes

	/**
	 * Registers a note with this reference.
	 * @param note the note to add to this reference's children
	 */
    void registerNote(Note note) {
        this.children.add(note);
	}

    /**
     * Checks the equality between this Library and a passed in object.
     * @param o object to be checked
     * @return boolean of
     */
    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        if(o instanceof Reference){
            Reference temp = (Reference) o;
            return this.getTitle().equals(temp.getTitle());
        }
        return false;

    }

    /**
     * TODO
     * @param r
     */
    @Override
    public void registerRelation(Relation r) {
        this.relations.add(r);
    }

    /**
     * TODO
     * @param r
     */
    @Override
    public void removeRelation(Relation r) {
        this.relations.removeIf(r::equals);
    }

    /**
     * Returns a list of strings that will be displayed for the menu.
     * @return A list of strings that will be displayed for the menu.
     */
    @Override
    public List<String> listOptions() {
        List<String> options = new ArrayList<>();
        options.add("Delete Reference");
        options.add("Edit Reference");
        options.add("Add Note");
        options.add("Add Idea");
        options.add("Add Argument");
        options.add("Generate Citation");
        options.add("View Directory");
        for (Entity e : getEntityChildren()) {
            options.add(e.getTitle());
        }
        return options;
    }

    /**
     * Sorts all of references children (Notes, Arguments and Ideas) in ascending or descending
     * order based on their titles.
     * @param order Specifies either ascending or descending order.
     */
    @Override
    public void sort(String order) {
         List<Entity> notes = new ArrayList<>();
         List<Entity> args  = new ArrayList<>();
         List<Entity> ideas = new ArrayList<>();
         for(Entity e : getEntityChildren()) {
             if(e instanceof Note)
                 notes.add(e);
             if(e instanceof Argument)
                 args.add(e);
             if(e instanceof Idea)
                 ideas.add(e);
         }
         if(order.toLowerCase().equals("a-z")) {
             notes.sort(Comparator.naturalOrder());
             args.sort(Comparator.naturalOrder());
             ideas.sort(Comparator.naturalOrder());
         } else if(order.toLowerCase().equals("z-a")) {
             notes.sort(Comparator.reverseOrder());
             args.sort(Comparator.reverseOrder());
             ideas.sort(Comparator.reverseOrder());
         }
    }
    /**
     * Returns a list of attributes that contains the title and description of
     * the reference.
     * @return A list of attributes that contains the title and description of
     * the reference.
     */
    public List<String> listAttributes() {
        List<String> attr = new ArrayList<>();
        attr.add(this.getTitle());
        attr.add(this.getDescription());

        return attr;
    }

}