package refmeister.XML;

/**
 * An exception designed to be thrown when an error is found while parsing XML.
 *
 * @author Wesley Rogers
 * @version 20 April, 2018
 */

public class MalformedXMLException extends RuntimeException {
    /**
     * A list of all offending tags.
     */
    private String[] tags;

    /**
     * Creates a MalformedXMLException.
     * @param msg description of what went wrong
     */
    public MalformedXMLException(String msg){
        super(msg);
    }

    /**
     * Creates a MalformedXMLException.
     * @param msg   description of what went wrong
     * @param tags  a list of tags or tag titles where the exception occured.
     */
    public MalformedXMLException(String msg, String... tags){
        super(msg);
        this.tags = tags;
    }

    /**
     * Gets the offending tags/tag titles.
     * @return an array of the offending tag/tag titles.
     */
    public String[] getTags(){
        return tags;
    }
}
