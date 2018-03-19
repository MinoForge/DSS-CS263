package refmeister.entity;

public interface Saveable {

	Saveable[] getChildren();

	string getSaveString();

}