package refmeister.entity;

import java.lang.reflect.Array;
import java.sql.Ref;
import java.util.*;

public class Reference extends Editable {

	private String[] refData;
	private ArrayList<RefIdea> ideas;
	private ArrayList<RefArg> arguments;
	private ArrayList<Note> notes;
	private Theme parent;

	public Reference(String title, String desc, String[] refData, ArrayList<RefIdea> ideas,
					 ArrayList<RefArg> arguments, ArrayList<Note> notes, Theme parent) {
		setTitle(title);
		setDescription(desc);
		this.refData = refData;
		this.ideas = ideas;
		this.arguments = arguments;
		this.notes = notes;
		this.parent = parent;
	}

	public Reference(String title, String desc, String[] refData, Theme parent) {
		this(title, desc, refData, new ArrayList<RefIdea>(), new ArrayList<RefArg>(),
				new ArrayList<Note>(), parent);
	}

	public Reference(String title, String[] refData, Theme parent) {
		this(title, "Unset Description", refData, new ArrayList<RefIdea>(), new ArrayList<RefArg>(),
				new ArrayList<Note>(), parent);
	}

	public String[] getRefData() {
		return refData;
	}

	public void setRefData(String[] refData) {
		this.refData = refData;
	}

	public ArrayList<RefIdea> getIdeas() {
		return ideas;
	}

	public void setIdeas(ArrayList<RefIdea> ideas) {
		this.ideas = ideas;
	}

	public ArrayList<RefArg> getArguments() {
		return arguments;
	}

	public void setArguments(ArrayList<RefArg> arguments) {
		this.arguments = arguments;
	}

	public ArrayList<Note> getNotes() {
		return notes;
	}

	public void setNotes(ArrayList<Note> notes) {
		this.notes = notes;
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

	@Override
	public ArrayList<Editable> getChildren() {
		return null;
	}

	@Override
	public String getSaveString() {
		return null;
	}
}