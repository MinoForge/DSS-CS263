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

	/**
	 *
	 * @param title
	 * @param args
	 */
	public Argument(String title, List<RefArg> args) {
		this(title, "Unset Description", args);
	}

	/**
	 *
	 * @param title
	 */
	public Argument(String title) {
		this(title, "Unset Description", new ArrayList<RefArg>());
	}

	/**
	 *
	 * @return
	 */
	public List<RefArg> getRefArg() {
		return arguments;
	}

	/**
	 *
	 * @param args
	 */
	public void setRefArg(List<RefArg> args) {
		this.arguments = args;
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
	public List<Saveable> getSaveableChildren() {
		return null;
	}

	@Override
	public String getSaveString() {
		return super.getSaveString("argument");
	}
}