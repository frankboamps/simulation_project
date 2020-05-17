package view;

import cellsociety.SimulationMain;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import xml.SimulationException;
import xml.SimulationXMLFileChooser;
import xml.SimulationXML;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ResourceBundle;

/**
 * This class holds the whole simulation's "view", mainly controlling the buttons view and the placement of the buttons and
 * where the simulation will be displayed
 * @author Franklin Boampong
 */

public class SimulationViewGUI {

    public static final double WIDTH = 2000;
    public static final double HEIGHT = 1024;
    public final static int SUBSCENE_WIDTH = 1350;//800
    public final static int SUBSCENE_HEIGHT = 600;//600
    public final static int SIMULATION_VIEW_WIDTH = 800, SIMULATION_VIEW_HEIGHT = 600;
    private final static int LABEL_LAYOUT = 268, LABEL_HEIGHT = 50;
    private final static int BUTTON_LAYOUT = 10;
    private final static int SLIDER_LAYOUT_X = 270, SLIDER_LAYOUT_Y = 80, SLIDER_MAX = 1000, SLIDER_LENGTH = 450, STEP = 10;//270, 80
    public final static int SLIDE_LABEL_X = 250, SLIDE_LABEL_Y = 25, SLIDE_LABEL_LAYOUT_X = 10, SLIDE_LABEL_LAYOUT_Y = 78;
    private final static int DEFAULT_INT = 0;

    private ResourceBundle myResources = SimulationMain.SIMULATION_RESOURCE;
    private Stage simulationViewStage;
    private Scene simulationViewScene;
    private AnchorPane simulationViewPane;
    private SimulationViewSubscene mySubscene;
    private Slider simulationViewSlider;
    private String font;
    private SimulationViewInfoLabel simulationViewLabel;
    private SimulationViewButton mySimulationStartButton;
    private SimulationViewButton mySimulationStopButton;
    private SimulationViewButton mySimulationStepButton, mySimulationContinueButton;
    private SimulationViewButton mySimulationLoadNewFileButton;
    private SimulationViewButton mySimulationRunDifferentSimulation;
    private boolean stepboolean = false;
    private int slideVal;
    private SimulationXML simulationXMLInfo;
    private String language = myResources.getString("English");

    /**
     * Create a view of the given model of a web browser with prompts in the given language.
     */
    public SimulationViewGUI(String language) throws FileNotFoundException, SimulationException {
        font = myResources.getString("FontStylePath");
        setGameScene();
        createBackgroundImage();
        createSubScene();
        createSimulationPane();
        makeSlider(DEFAULT_INT, SLIDER_MAX, STEP);
        makeSliderMessageLabel();
        simulationViewPane.getStylesheets().add("resources/default.css");
    }

    /**
     * method to obtain the stage of the view
     *
     * @return baseStage
     */
    public Stage getSimulationViewStage() {
        return simulationViewStage;
    }

    /**
     * public method to asses the pane of main view
     *
     * @return the pane of current view
     */
    public AnchorPane getSimulationViewPane() {
        return simulationViewPane;
    }

    private void makeTopButtons() {
        HBox boxWIthButtons = new HBox(BUTTON_LAYOUT);
        boxWIthButtons.setPrefWidth(WIDTH);
        boxWIthButtons.setPrefHeight(LABEL_HEIGHT);
        boxWIthButtons.setLayoutY(BUTTON_LAYOUT);
        boxWIthButtons.setLayoutY(BUTTON_LAYOUT);
        mySimulationStartButton = makeButton("StartCommand", event -> {
            try {
                startSimulation();
            } catch (FileNotFoundException e) {
                showError(myResources.getString("FileError"));
            }
        });
        boxWIthButtons.getChildren().add(mySimulationStartButton);
        mySimulationStopButton = makeButton("StopCommand", event -> stopSimulation());
        boxWIthButtons.getChildren().add(mySimulationStopButton);
        mySimulationContinueButton = makeButton("ContinueCommand", event -> {
            try {
                continueSimulation();
            } catch (java.lang.Exception e) {
                throw new SimulationException(myResources.getString("TitleError"));
            }
        });
        boxWIthButtons.getChildren().add(mySimulationContinueButton);
        mySimulationStepButton = makeButton("StepCommand", event -> {
            try {
                stepThroughSimulation();
            } catch (java.lang.Exception e) {
                showError(myResources.getString("FileError"));
            }
        });
        boxWIthButtons.getChildren().add(mySimulationStepButton);
        mySimulationLoadNewFileButton = makeButton("LoadFileCommand", event -> {
            try {
                loadFile();
            } catch (java.lang.Exception e) {
                throw new SimulationException(myResources.getString("LoadFile"));
            }
        });
        boxWIthButtons.getChildren().add(mySimulationLoadNewFileButton);
        simulationViewPane.getChildren().add(boxWIthButtons);
        mySimulationRunDifferentSimulation = makeButton("NewSimCommand", event -> {
            try {
                startSecondSimulation();
            } catch (java.lang.Exception e) {
                throw new SimulationException(e.getMessage(), myResources.getString("DifferentSimulationError"));
            }
        });
        boxWIthButtons.getChildren().add(mySimulationRunDifferentSimulation);
    }

    private void startSecondSimulation() throws java.lang.Exception {
        Stage secondSimulation = new Stage();
        Application app2 = new Application() {
            @Override
            public void start(Stage primaryStage) throws FileNotFoundException {
                SimulationViewGUI mysecondSim = new SimulationViewGUI(language);
                primaryStage = mysecondSim.getSimulationViewStage();
                primaryStage.show();
            }
        };
        app2.start(secondSimulation);
    }

    private void makeBottomLabelScene() throws FileNotFoundException {
        HBox labelAtBottom = new HBox();
        labelAtBottom.setPrefHeight(LABEL_HEIGHT);
        labelAtBottom.setPrefWidth(WIDTH);
        labelAtBottom.setLayoutY(HEIGHT - LABEL_LAYOUT);
        labelAtBottom.setLayoutX(DEFAULT_INT);
        simulationViewLabel = new SimulationViewInfoLabel(myResources.getString("WelcomeMessage"), (int) WIDTH, LABEL_HEIGHT);
        simulationViewLabel.setFont((Font.loadFont(new FileInputStream(new File(font)), Double.parseDouble(myResources.getString("FSizeLabel")))));
        labelAtBottom.setAlignment(Pos.BOTTOM_LEFT);
        labelAtBottom.getChildren().addAll(simulationViewLabel);
        labelAtBottom.setAlignment(Pos.CENTER_LEFT);
        simulationViewPane.getChildren().add(labelAtBottom);
    }

    private void startSimulation() throws FileNotFoundException {
        mySubscene.start(simulationXMLInfo);
    }

    private void stopSimulation() {
        mySubscene.getAnimation().stop();
    }

    private void continueSimulation() {
        mySubscene.getAnimation().play();
    }

    private void stepThroughSimulation() {
        try {
            if (!stepboolean) {
                mySubscene.getAnimation().stop();
                stepboolean = true;
            } else {
                mySubscene.getAnimation().stop();
                mySubscene.stepb();
            }
        } catch (java.lang.Exception e) {
            throw new SimulationException(myResources.getString("FileError"));
        }
    }

    private void loadFile() throws java.lang.Exception {
        SimulationXMLFileChooser fileChooser = new SimulationXMLFileChooser();
        fileChooser.openFile(simulationViewStage);
        simulationXMLInfo = fileChooser.getSimulationXMLInfo();
        simulationViewLabel.setText(myResources.getString("SimulationTitle") + " " + simulationXMLInfo.getTitle());
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(myResources.getString("ErrorTitle"));
        alert.setContentText(message);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            simulationViewStage.close();
        }
    }

    private SimulationViewButton makeButton(String property, EventHandler<ActionEvent> handler) {
        SimulationViewButton result = new SimulationViewButton(myResources.getString(property), SimulationMain.LANGUAGE);
        String label = myResources.getString(property);
        final String IMAGEFILE_SUFFIXES = String.format(".*\\.(%s)", String.join("|", ImageIO.getReaderFileSuffixes()));
        if (label.matches(IMAGEFILE_SUFFIXES)) {
            result.setGraphic(new ImageView(new Image(getClass().getResourceAsStream(SimulationMain.DEFAULT_RESOURCE_FOLDER + label))));
        } else {
            result.setText(label);
        }
        result.setOnAction(handler);
        return result;
    }

    private void setGameScene() throws FileNotFoundException {
        simulationViewPane = new AnchorPane();
        simulationViewScene = new Scene(simulationViewPane, WIDTH, HEIGHT);
        simulationViewStage = new Stage();
        simulationViewStage.setScene(simulationViewScene);
    }

    private void createBackgroundImage() {
        Image backgroundImage = new Image(myResources.getString("SimulationBackground"), false);
        BackgroundImage simulationViewBackground;
        simulationViewBackground = new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
        simulationViewPane.setBackground(new Background(simulationViewBackground));
    }

    private void createSimulationPane() throws FileNotFoundException, SimulationException {
        makeBottomLabelScene();
        makeTopButtons();
    }

    private void makeSlider(int min, int max, int step) {
        simulationViewSlider = new Slider();
        simulationViewSlider.setMax(max);
        simulationViewSlider.setMin(min);
        simulationViewSlider.setShowTickLabels(true);
        simulationViewSlider.setShowTickMarks(true);
        simulationViewSlider.setMajorTickUnit(SLIDER_MAX);
        simulationViewSlider.setBlockIncrement(step);
        simulationViewSlider.setPrefWidth(SLIDER_LENGTH);
        simulationViewSlider.setMaxWidth(SLIDER_MAX);
        simulationViewSlider.setLayoutX(SLIDER_LAYOUT_X);
        simulationViewSlider.setLayoutY(SLIDER_LAYOUT_Y);
        simulationViewSlider.setValueChanging(true);
        simulationViewSlider.valueProperty().addListener((observable, oldvalue, newvalue) -> {
            slideVal = newvalue.intValue();
            mySubscene.factorChange(STEP / (slideVal / 100 + 1) + 1);
        });
        simulationViewPane.getChildren().add(simulationViewSlider);
    }

    private void makeSliderMessageLabel() {
        SimulationViewInfoLabel sliderMessage = new SimulationViewInfoLabel(myResources.getString("SliderString"), SLIDE_LABEL_X, SLIDE_LABEL_Y);
        sliderMessage.setLayoutX(SLIDE_LABEL_LAYOUT_X);
        sliderMessage.setLayoutY(SLIDE_LABEL_LAYOUT_Y);
        getSimulationViewPane().getChildren().add(sliderMessage);
    }

    private void createSubScene() {
        mySubscene = new SimulationViewSubscene(SUBSCENE_WIDTH, SUBSCENE_HEIGHT);
        simulationViewPane.getChildren().add(mySubscene);
    }

}



