package refmeister.entity;

import refmeister.XML.Saveable;
import refmeister.XML.XMLManager;

import java.util.*;

public class Reference extends Editable {

	private String[] refData;
	private List<RefIdea> ideas;
	private List<RefArg> arguments;
	private List<Note> notes;
	private Theme parent;

	public Reference(String title, String desc, String[] refData, List<RefIdea> ideas,
					 List<RefArg> arguments, List<Note> notes, Theme parent) {
		setTitle(title);
		setDescription(desc);
		this.refData = refData;
		this.ideas = ideas;
		this.arguments = arguments;
		this.notes = notes;
		this.parent = parent;
		Editable lib = getParent().getParent().getParent();
		if(lib instanceof Library) {
		    Library myLib = (Library)lib;
		    myLib.getRefs().add(this);
        }
		parent.register(this);
	}

	public Reference(String title, String desc, String[] refData, Theme parent) {
		this(title, desc, refData, new ArrayList<RefIdea>(), new ArrayList<RefArg>(),
				new ArrayList<Note>(), parent);
	}

	public Reference(String title, String[] refData, Theme parent) {
		this(title, "Unset Description", refData, new ArrayList<RefIdea>(), new ArrayList<RefArg>(),
				new ArrayList<Note>(), parent);
	}

	public Reference(String title, String description, Theme parent) {
		this(title, description, new String[12], new ArrayList<RefIdea>(), new ArrayList<RefArg>(),
				new ArrayList<Note>(), parent);
	}

	public String[] getRefData() {
		return refData;
	}

	public void setRefData(String[] refData) {
		this.refData = refData;
	}

	public List<RefIdea> getIdeas() {
		return ideas;
	}

	public void setIdeas(List<RefIdea> ideas) {
		this.ideas = ideas;
	}

	public List<RefArg> getArguments() {
		return arguments;
	}

	public void setArguments(List<RefArg> arguments) {
		this.arguments = arguments;
	}

	/**
	 * Adds a new note to this Reference's ArrayList of notes.
	 * @param title The title of the note to be added.
	 * @param desc The description of the note to be added.
	 * @return If the note is not already in the list, return the new note. Otherwise, return null.
	 */
	public Note addNote(String title, String desc) {
		for(Editable n : notes) {
			// If a note already has the same title as the one we are trying to add, don't add it.
			if(n.getTitle().equals(title)) {
				return null;
			}
		}
		Note newNote = new Note(title, desc, this);
		return newNote;
	}

	public List<Note> getNotes() {
		return notes;
	}

	public void setNotes(List<Note> notes) {
		this.notes = notes;
	}

	public void deleteNote(String title) {
		notes.removeIf(ed -> ed.getTitle().equals(title));
	}

	public Theme getParent() {
		return parent;
	}

	public void setParent(Theme parent) {
		this.parent = parent;
	}

	public String generateCite(String format) {
        return null;
	}

	public RefArg addArgument(Argument arg, float rating){
		RefArg newRefArg = new RefArg(this, arg, rating);
		return newRefArg;
	}

	public RefIdea addIdea(Idea idea){
		RefIdea ri = new RefIdea(this, idea);
		return ri;
	}

	@Override
	public String[] display() {
		String[] view = new String[notes.size() + ideas.size() +
                arguments.size() + 5];
		int i = 0;
		view[i] = getTitle();
		view[++i] = getDescription();
		view[++i] = "Notes:";
		for(Note n : notes) {
			view[++i] = n.getTitle();
		}

		view[++i] = "Ideas:";
        for(RefIdea idea : ideas) {
            view[++i] = idea.getIdea().getTitle();
        }

        view[++i] = "Arguments";
        for(RefArg a : arguments) {
            view[++i] = a.getArgument().getTitle();
        }

		return view;
	}
	/**
	 * A list of all of this Saveable's saveable children. This method should <bold>NEVER</bold>
	 * return null.
	 * @return a list of all of this object's Saveable children.
	 */
	@Override
	public List<Saveable> getSaveableChildren() {
        List<Saveable> out = new ArrayList<>(notes);
        out.addAll(arguments);
        out.addAll(ideas);
	    return out;
	}
	/**
	 * Retrieves the list of this Editable's children.
	 * @return The list of this Editable's children.
	 */
	public List<Editable> getChildren() {
		return null;
	}

    @Override
    public boolean createChild(String title, String description) {
        return false;
    }

    @Override
	public String getSaveString(XMLManager manager) {
		return super.getSaveString("reference", manager);
	}

    void registerNote(Note note) {
        this.notes.add(note);
	}

	void registerRefArg(RefArg ra){
	    this.arguments.add(ra);
    }

    void registerRefIdea(RefIdea ri){
	    this.ideas.add(ri);
    }
}