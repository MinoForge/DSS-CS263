package refmeister.XML;

import java.util.List;

public interface Saveable {

	List<Saveable> getSaveableChildren();

	String getSaveString(XMLManager manager);

}