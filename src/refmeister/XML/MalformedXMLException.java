package refmeister.XML;

/**
 * An exception designed to be thrown when an error is found while parsing XML.
 *
 * @author Wesley Rogers
 */
public class MalformedXMLException extends RuntimeException {
    private String[] tags;

    public MalformedXMLException(String msg){
        super(msg);
    }

    public MalformedXMLException(String msg, String... tags){
        super(msg);
        this.tags = tags;
    }

    public String[] getTags(){
        return tags;
    }
}
