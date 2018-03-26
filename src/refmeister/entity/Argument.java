package refmeister.entity;

import java.util.ArrayList;
import java.util.List;

public class Argument extends Editable {

	/** ArrayList of RefArgs that show what this Argument instance is associated with. */
	private List<RefArg> arguments;

	/**
	 * TODO
	 * @param title
	 * @param desc
	 * @param arguments
	 */
	public Argument(String title, String desc, List<RefArg> arguments) {
		setTitle(title);
		setDescription(desc);
		this.arguments = arguments;
	}

	/**
	 * TODO
	 * @param title
	 * @param desc
	 */
	public Argument(String title, String desc) {
		this(title, desc, new ArrayList<RefArg>());
	}

	public Argument(String title, List<RefIdea> ideas) {
		this(title)
	}

	/**
	 * TODO
	 */
	public void destroy() {
		for(RefArg ra : arguments) {
			ra.destroy();
		}
	}

	@Override
	public List<Editable> getChildren() {
		return null;
	}

	@Override
	public String getSaveString() {
		return null;
	}

	public void edit(String[] edits) {
		setTitle(edits[0]);
		setDescription(edits[1]);
	}
}