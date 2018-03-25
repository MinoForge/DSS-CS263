package refmeister.entity;

import java.util.ArrayList;
import java.util.List;

public class Argument extends Editable {

	List<RefArg> arguments;

	/**
	 * TODO
	 */
	public void destroy() {
		for(RefArg ra : arguments) {
			ra.destroy();
		}
	}

	/**
	 * TODO
	 * @return
	 */
	@Override
	public String[] display() {
		String[] results = new String[arguments.size() + 3];
		results[0] = getTitle();
		results[1] = getDescription();
		int i = 3;
		float avg = 0;
		for(RefArg ra : arguments) {
			avg += ra.getRating();
			results[i++] = ra.getReference().getTitle();
		}
		results[3] = "" + (avg / arguments.size());
		return results;
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