package view;

import cells.Cell;
import javafx.scene.chart.*;

import java.util.HashMap;

/**
 * SimulationLineChart has the code to display simulation's data in the form of a line chart.
 * Although this was not used in the rest of our code, it helps with future extensions if line charts are needed to be used somewhere else
 * @author Frankling Boampong
 */

public class SimulationLineChart {

    private CategoryAxis simulationXValues = new CategoryAxis();
    private NumberAxis simulationYValues = new NumberAxis();
    protected LineChart lineChart;//=new LineChart();

    HashMap<Integer, Integer> hash = new HashMap<>();
    HashMap<Integer, XYChart.Series> seriesMap = new HashMap<>();
    int time = 0;

    /**
     * Constructor for the class that sets the labels of the line chart
     */
    public SimulationLineChart() {
        //super();
        simulationXValues.setLabel("Time");
        simulationYValues.setLabel("Frequency of State");
        //updateLineChartData();
        //getLine();

    }

    /**
     * Creates the lines in the line chart
     * @return the line chart
     */
    public LineChart getLine() {
        //NumberAxis xAxis = new NumberAxis();
        //NumberAxis yAxis = new NumberAxis();
        time += 1;
        lineChart = new LineChart(simulationXValues, simulationYValues);

        lineChart.setLayoutX(850);
        lineChart.setLayoutY(470);
        lineChart.setPrefWidth(600);
        lineChart.setPrefHeight(250);

        for (Integer key : hash.keySet()) {
            seriesMap.get(key).getData().add(new XYChart.Data(time, hash.get(key)));
            lineChart.getData().add(seriesMap.get(key));
        }
        return lineChart;
    }

    /**
     * updates the lines in the line chart
     * @param myCell the cell so its state can be added in updating the line chart
     */

    public void updateLineChartData(Cell myCell) {
        if (!hash.containsKey(myCell.getCurrentState())) {
            hash.put(myCell.getCurrentState(), 1);
            seriesMap.put(myCell.getCurrentState(), new XYChart.Series());
        } else {
            hash.put(myCell.getCurrentState(), hash.get(myCell.getCurrentState()) + 1);
        }
    }

}
