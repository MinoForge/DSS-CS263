package refmeister.entity;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

public class Theme extends Editable {
	private List<Editable> refs;
	private Topic parent;

	public Theme(String title, String desc, Topic parent,  List<Editable> refs) {
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
		for(Editable t : this.parent.getParent().getSaveableChildren()){
			if(t.getTitle().equals(topicTitle)){
				for(Editable i : t.getSaveableChildren()){
					if(i.getTitle().equals(this.getTitle())){
						throw new InvalidParameterException("Theme already exists in chosen topic");
					}
				}
				t.getSaveableChildren().add(this);
				this.setParent((Topic)t);
				this.parent.deleteTheme(this.getTitle());
				return;
			}
		}
		throw new InvalidParameterException("Topic does not exist");
	}

	public Reference addReference(String title, String desc) {
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

	public List<Editable> getRefs() {
		return refs;
	}

	public void setRefs(List<Editable> refs) {
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
	public List<Saveable> getSaveableChildren() {
		return new ArrayList<>(refs);
	}

}