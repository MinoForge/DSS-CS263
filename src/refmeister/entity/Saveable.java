package refmeister.entity;

import java.util.List;

public interface Saveable {

	List<Editable> getChildren();

	String getSaveString();

}