package refmeister.XML;

import java.util.List;

/**
 * The basis for saving the contents of an instance of Refmeister. This class works by tree
 * traversal: Each element manages the XML of it's children, ending with references.
 * Additionally, Arguments, Ideas, and RefArgs/RefIdeas register with the XMLManager during
 * traversal to ensure that all elements are properly accounted for in the XML output.
 */
public interface Saveable {

	/**
	 * A list of all of this Saveable's saveable children. This method should <bold>NEVER</bold>
     * return null.
	 * @return a list of all of this object's Saveable children.
	 */
	List<Saveable> getSaveableChildren();

    /**
     * Gets the XML representation of this saveable object. Saveable objects that are association
     * classes should register their XML output with the XMLManager, and Argument/Ideas should
     * also register with the XMLManager.
     * @param manager   The XMLManager that this traversal is being used with.
     * @return          The XML representation of this Saveable, with appropriate associations
     *                  registered with the XML Manager.
     */
	String getSaveString(XMLManager manager);

}