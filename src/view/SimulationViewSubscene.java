package view;

import individual_simulations.*;
import individual_simulations.Simulation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.SubScene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.util.Duration;
import xml.SimulationException;
import xml.SimulationXML;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * This class contains the information needed to display the simulation and graph
 * There are dependencies on the XML package since it passes information about the simulation
 * We assume that the size of the simulation and the size of the graph will always be the same and nonresizable
 * We also assume the initial step speed with be based on our factor of 10
 * @author Franklin Boampong
 * @author Michelle Tai
 */

public class SimulationViewSubscene extends SubScene {

    private static final int FRAMES_PER_SECOND = 60;
    private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    private static final int SUBSCENE_LAYOUT_X = 25, SUBSCENE_LAYOUT_Y = 124;
    private static final int LINE_GRAPH_X = 550, LINE_GRAPH_Y = 600, LINE_GRAPH_X_LAYOUT = 800, LINE_GRAPH_Y_LAYOUT = 0;
    private static final String RESOURCES = "resources";
    private static final String[] allTitle = {"Game of Life", "Segregation", "Fire", "Percolation", "WaTor", "Rock Paper Scissor", "Sugar Scape"};
    public static final String DEFAULT_RESOURCE_PACKAGE = RESOURCES + ".";
    private ResourceBundle myResources;

    private Simulation mySimulation;
    private Timeline animation;
    private AnchorPane mySubscenePane;
    private SimulationXML simXMLInfo;
    private int factor = 10;
    private int time = 0;

    final CategoryAxis xAxis = new CategoryAxis();
    final NumberAxis yAxis = new NumberAxis();

    private LineChart<String, Number> lineChart = new LineChart<String, Number>(xAxis, yAxis);
    private HashMap<String, XYChart.Series> seriesMap = new HashMap<>();
    private ArrayList<XYChart.Series> timeSeriesArrayList = new ArrayList<>();

    /**
     * The constructor initializes the all the subparts of this subscene, such as the background, the properties file, the simulation, etc
     * @param width is the width of the subscene
     * @param height is the height of the subscene
     */
    public SimulationViewSubscene(int width, int height) {
        super(new AnchorPane(), width, height);
        myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "English");
        prefWidth(width);
        prefHeight(height);
        setBackground();
        subsceneLayout();
        beginAnimation();
        mySubscenePane.getStylesheets().addAll("resources/default.css");
    }

    /**
     * Public method to enable easy acces to the subcene pane
     *
     * @return the pane of the subscene
     */
    public AnchorPane getPane() {
        return (AnchorPane) this.getRoot();
    }

    /**
     * Void public method to be used for step button
     * just updates the grid by calling updateGrid
     */
    public void stepb() {
        try {
            mySimulation.updateGrid();
        }
        catch (java.lang.Exception e){
            throw new SimulationException(myResources.getString("FileError"));
        }
    }

    /**
     * Void method is used to update the factor of animation speed
     * @param i to serve as the factor speed of animation
     */

    public void factorChange(int i) {
        factor = i;
    }

    /**
     * Method provides the current animation
     * @return animation to be able to make the animation dynamic
     */
    public Timeline getAnimation() {
        return animation;
    }

    /**
     * Method starts the simulation process
     * @param simInfo gets the info from xml file
     * @throws FileNotFoundException to catch exception when needed
     */


    public void start(SimulationXML simInfo) throws FileNotFoundException {
        try {
            this.simXMLInfo = simInfo;
            if(mySimulation!=null) mySimulation.getDisplay().removeFromPane(mySubscenePane);
            makeNewSim();
            mySimulation.getDisplay().addToPane(mySubscenePane);
            if(mySubscenePane.getChildren().contains(lineChart)) mySubscenePane.getChildren().remove(lineChart);
            createTimeSeries();
            displayLineChart();
            animation.play();
        } catch (java.lang.Exception e) {
            showError(myResources.getString("FileError"));
        }
    }

    private void subsceneLayout() {
        setLayoutX(SUBSCENE_LAYOUT_X);
        setLayoutY(SUBSCENE_LAYOUT_Y);
    }

    private void setBackground() {
        Image backgroundImage = new Image(myResources.getString("SubImage"), false);
        BackgroundImage subsceneViewBackground;
        subsceneViewBackground = new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
        mySubscenePane = (AnchorPane) this.getRoot();
        mySubscenePane.setBackground(new Background(subsceneViewBackground));
    }

    private void step(double secondDelay) {
        try {
            mySimulation.update(secondDelay, factor);
            if(time%factor==0) {
                updateSeries();
            }
            time += 1;
        }
        catch (java.lang.Exception e){
            animation.stop();
        }
    }

    private void makeNewSim () throws FileNotFoundException {
        String title = "";
        String shape = "";
        try {
            title = simXMLInfo.getTitle();
            shape = simXMLInfo.getShape();
            if (title.equals(allTitle[0])) {
                mySimulation = new GameOfLife(simXMLInfo.getHeight(), simXMLInfo.getWidth(),
                        true, shape);
            } else if (title.equals(allTitle[1])) {
                mySimulation = new Segregation(simXMLInfo.getHeight(), simXMLInfo.getWidth(),
                        true, shape, 0.75);
            } else if (title.equals(allTitle[2])) {
                mySimulation = new Fire(simXMLInfo.getHeight(), simXMLInfo.getWidth(),
                        true, shape, 0.25);
            } else if (title.equals(allTitle[3])) {
                mySimulation = new Percolation(simXMLInfo.getHeight(), simXMLInfo.getWidth(), true, shape);
            } else if (title.equals(allTitle[4])) {
                mySimulation = new WaTor(simXMLInfo.getHeight(), simXMLInfo.getWidth(),
                        false, shape, 2, 10, 2);
            } else if (title.equals(allTitle[5])) {
                mySimulation = new RockPaperScissor(simXMLInfo.getHeight(), simXMLInfo.getWidth(), true, shape, 2);
            } else if (title.equals(allTitle[6])) {
                mySimulation = new Sugarscape(simXMLInfo.getHeight(), simXMLInfo.getWidth(), false, shape, 500);
            }
            if (!simXMLInfo.isRandom()) {
                mySimulation.setData(simXMLInfo.getInitialConfig());
            }
        } catch (java.lang.Exception e) {
            throw new SimulationException(myResources.getString("FileError"));
        }
    }

    private void beginAnimation() {
        try {
            KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY));
            animation = new Timeline();
            animation.setCycleCount(Timeline.INDEFINITE);
            animation.getKeyFrames().add(frame);
        }
        catch (java.lang.Exception e){
            throw new SimulationException(myResources.getString("FileError"));
        }
    }

    private void displayLineChart() {
        lineChart.setPrefHeight(LINE_GRAPH_Y);
        lineChart.setPrefWidth(LINE_GRAPH_X);
        lineChart.setLayoutX(LINE_GRAPH_X_LAYOUT);
        lineChart.setLayoutY(LINE_GRAPH_Y_LAYOUT);
        lineChart.setCreateSymbols(false);
        if(mySubscenePane.getChildren().contains(lineChart)) mySubscenePane.getChildren().remove(lineChart);
        mySubscenePane.getChildren().add(lineChart);
    }

    private void createTimeSeries(){
        time = 0;
        Map<String, Integer> map = mySimulation.frequency();
        int total = 0;
        for(String i: map.keySet()){
            total+=map.get(i);
        }
        yAxis.setMaxHeight(total);
        lineChart = new LineChart<String,Number>(xAxis,yAxis);
        for (String i : map.keySet()){
            XYChart.Series series = new XYChart.Series();
            series.getData().add(new XYChart.Data(time + "", map.get(i)));
            series.setName(i);
            timeSeriesArrayList.add(series);
            lineChart.getData().addAll(series);
        }
    }

    private void updateSeries(){
        Map<String, Integer> map = mySimulation.frequency();
        for (XYChart.Series series: timeSeriesArrayList){
            if(time>1000) series.getData().remove(0);
            series.getData().add(new XYChart.Data(time+"", map.get(series.getName())));
        }
        mySubscenePane.getChildren().remove(lineChart);
        mySubscenePane.getChildren().add(lineChart);
    }

    private void showError(String message) {
        try {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(myResources.getString("ErrorTitle"));
            alert.setContentText(message);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                throw new SimulationException(myResources.getString("TitleError"));
            }
        }
        catch (java.lang.IllegalStateException e){
            animation.stop();
        }
    }
}
