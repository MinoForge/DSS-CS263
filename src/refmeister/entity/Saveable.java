package refmeister.entity;

import java.util.List;

public interface Saveable {

	List<Saveable> getSaveableChildren();

	String getSaveString();

}