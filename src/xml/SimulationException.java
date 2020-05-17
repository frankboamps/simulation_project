package xml;

import cellsociety.SimulationMain;

import java.util.ResourceBundle;


/**
 * This class is used to throw exceptions so that the program can note when there's problems and not completely crash
 * @author Franklin Boampong
 */
public class SimulationException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private ResourceBundle myResources = SimulationMain.SIMULATION_RESOURCE;

    /**
     * Create an exception based on an issue in our code.
     */
    public SimulationException(String message, Object... values) {
        super(String.format(message, values));
    }

    /**
     * Create an exception based on a caught exception with a different message.
     */
    public SimulationException(Throwable cause, String message, Object... values) {
        super(String.format(message, values), cause);
    }

    /**
     * Create an exception based on a caught exception, with no additional message.
     */
    public SimulationException(Throwable cause) {
        super(cause);
    }

}
