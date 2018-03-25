package refmeister.entity;

import java.util.List;

public class Argument extends Editable {

	public void destroy() {
		// TODO - implement Argument.destroy
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