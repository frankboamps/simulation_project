package cellsociety;

import view.SimulationViewGUI;
import java.io.FileNotFoundException;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.stage.Stage;
import xml.SimulationException;


public class SimulationMain extends Application {
    public static final String RESOURCES = "resources";
    public static final String DEFAULT_RESOURCE_PACKAGE = RESOURCES + ".";
    public static final String LANGUAGE = "English";
    public static final String DEFAULT_RESOURCE_FOLDER = "/" + RESOURCES + "/";
    public static ResourceBundle SIMULATION_RESOURCE = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + LANGUAGE);

    private static final String TITLE = "Simulation";

    /**
     * Initialize what will be displayed and how it will be updated.
     */
    @Override
    public void start(Stage stage) throws FileNotFoundException, SimulationException {
        SimulationViewGUI display = new SimulationViewGUI(SIMULATION_RESOURCE.getString("English"));
        stage.setTitle(TITLE);
        stage = display.getSimulationViewStage();
        stage.show();
    }

    /**
     * Start of the program.
     */
    public static void main(String[] args) {
        launch(args);
    }
}


