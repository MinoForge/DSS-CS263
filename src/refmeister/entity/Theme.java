package refmeister.entity;

import java.security.InvalidParameterException;
import java.util.ArrayList;

public class Theme extends Editable {
	private ArrayList<Editable> refs;
	private Topic parent;

	public Theme(String title, String desc, Topic parent,  ArrayList<Editable> refs) {
		this.setTitle(title);
		this.setDescription(desc);
		this.refs = refs;
		this.parent = parent;
	}

	public Theme(String title, String desc, Topic parent){
		this(title, desc, parent, new ArrayList<Editable>());
	}

	public Theme(String title, Topic parent){
		this(title, "Unset Description", parent, new ArrayList<Editable>());
	}

	public void moveTheme(String topicTitle) throws InvalidParameterException {
		for(Editable t : this.parent.getParent().getChildren()){
			if(t.getTitle().equals(topicTitle)){
				for(Editable i : t.getChildren()){
					if(i.getTitle().equals(this.getTitle())){
						throw new InvalidParameterException("Theme already exists in chosen topic");
					}
				}
				t.getChildren().add(this);
				this.setParent((Topic)t);
				this.parent.deleteTheme(this.getTitle());
				return;
			}
		}
		throw new InvalidParameterException("Topic does not exist");
	}

	public Reference addReference() {
		for(Editable t : refs) {
			if(t.getTitle().equals(getTitle())) {
				return null;
			}
		}
		Reference newRef = new Reference(title, desc, this);
		refs.add(newRef);
		return newRef;
	}

	public void deleteReference() {
		for(Editable t : refs) {
			if(t.getTitle().equals(getTitle())) {
				refs.remove(t);
			}
		}
	}

	public ArrayList<Editable> getRefs() {
		return refs;
	}

	public void setRefs(ArrayList<Editable> refs) {
		this.refs = refs;
	}

	public Topic getParent() {
		return parent;
	}

	public void setParent(Topic parent) {
		this.parent = parent;
	}

	@Override
	public java.lang.String toString() {
		return "Topic{" +
				"title='" + getTitle() + '\'' +
				", description='" + getDescription() + '\'' +
				", references=" + refs +
				'}';
	}


	@Override
	public String getSaveString() {
		return null;
	}

	@Override
	public ArrayList<Editable> getChildren() {
		return null;
	}

	public void edit(String[] edits) {
		setTitle(edits[0]);
		setDescription(edits[1]);
	}
}