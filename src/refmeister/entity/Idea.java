package refmeister.entity;

import java.util.*;

public class Idea extends Editable {

	Collection<RefIdea> refIdea;

	public void destroy() {
		// TODO - implement Idea.destroy
		throw new UnsupportedOperationException();
	}

	@Override
	public String[] display() {
		return new String[0];
	}

	@Override
	public List<Editable> getChildren() {
		return null;
	}

	@Override
	public String getSaveString() {
		return null;
	}
}