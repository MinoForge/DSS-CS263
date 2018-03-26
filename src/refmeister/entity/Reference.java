package refmeister.entity;

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

	public List<Note> getNotes() {
		return notes;
	}

	public void setNotes(List<Note> notes) {
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
	public List<Saveable> getSaveableChildren() {
		return null;
	}

	public List<Editable> getChildren() {
		return null;
	}

	@Override
	public String getSaveString() {
		return super.getSaveString("reference");
	}
}