package refmeister.entity;

import java.util.ArrayList;

public interface Saveable {

	ArrayList<Editable> getChildren();

	String getSaveString();

}