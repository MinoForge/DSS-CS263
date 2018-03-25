package refmeister.entity;

import java.util.ArrayList;

public interface Saveable {

	ArrayList<Saveable> getChildren();

	String getSaveString();

}