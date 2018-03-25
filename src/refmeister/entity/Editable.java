package refmeister.entity;

import java.util.ArrayList;

public abstract class Editable implements Displayable, Saveable {

	private String title;
	private String description;
    private ArrayList<Editable> children;

	public String getTitle() {
		// TODO - implement Editable.getTitle
		throw new UnsupportedOperationException();
	}

	public String getDescription() {
		// TODO - implement Editable.getDescription
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param newTitle
	 */
	public void setTitle(String newTitle) {
		// TODO - implement Editable.setTitle
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param newDesc
	 */
	public void setDescription(String newDesc) {
		// TODO - implement Editable.setDescription
		throw new UnsupportedOperationException();
	}

}