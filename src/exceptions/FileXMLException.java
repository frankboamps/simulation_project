package exceptions;

/**
 * This class is used to throw exceptions so that the program can note when there's problems and not completely crash
 * @author Franklin Boampong
 */

public class FileXMLException extends RuntimeException {

    // for serialization
    private static final long serialVersionUID = 1L;


    /**
     * Create an exception based on an issue in our code.
     */
    public FileXMLException (String message, Object ... values) {
        super(String.format(message, values));
    }

    /**
     * Create an exception based on a caught exception with a different message.
     */
    public FileXMLException (Throwable cause, String message, Object ... values) {
        super(String.format(message, values), cause);
    }

    /**
     * Create an exception based on a caught exception, with no additional message.
     */
    public FileXMLException (Throwable cause) {
        super(cause);
    }

}
