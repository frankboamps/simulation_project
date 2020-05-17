package individual_simulations;

import grids.DisplayGrid;
import grids.Grid;
import grids.RectGrid;
import grids.TriGrid;

import java.util.*;

/**
 * Simulation holds the logic that tells the cells what to do. It holds a grid item, where data of
 * all cells are stored, and a list of indices for accessing these cells
 *
 * This is mainly used as a superclass for all the simulations so that all simulations will have a way to update and keep track of
 * the states of cells.
 * @author Lucy Gu
 */
public abstract class Simulation{
    private double time = 0;
    //private SimulationViewGUI myGUI=new SimulationViewGUI("English");
    //private SimulationLineChart lineChart = new SimulationLineChart();;

    protected ArrayList<int[]> indices = new ArrayList<>();
    protected Grid grid;

    /**
     *
     * @param row               number of rows in the grid
     * @param col               number of columns in the grid
     * @param neighbourNumber   looking only at immediate neighbors or at all
     * @param wrap              if toroidal or not
     * @param shape             default to rectangle, which grid to call
     */
    public Simulation(int row, int col, boolean neighbourNumber, boolean wrap, String shape){
        if(shape.equals("Rectangle")) grid = new RectGrid(row, col, neighbourNumber, wrap);
        else if (shape.equals("Triangle")) grid = new TriGrid(row, col, neighbourNumber, wrap);
        else grid = new RectGrid(row, col, neighbourNumber, wrap);
    }

    /**
     * Call updateGrid (which updates the cells shown on screen) only if a certain amount
     * of time has passed
     * @param elapsedTime       time passed
     * @param factor            update every factor round
     */
    public void update(double elapsedTime, int factor){
        time+=elapsedTime;
        if(time>elapsedTime*factor){
            updateGrid();
            time = 0;
        }
    }

    /**
     * Initialize grid data with the data from XML file (state will be a list of list of status for each cell)
     * @param state     list of integer status
     */
    public void setData(List<List<Integer>> state){
        grid.iniState(state);
    }

    /**
     * Create a list of indices of all cells
     * @param row row of grid
     * @param col col of grid
     */
    public void createIndices(int row, int col){
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                indices.add(new int[]{i, j});
            }
        }
    }

    /**
     * find the frequency of each cell states and put them in a hashmap
     * @return hashmap with the information
     */
    public abstract Map<String, Integer> frequency();


    /**
     * go through index, find neighbour, update according to react, update all
     */
    public void updateGrid(){
        for(int[]index:indices) {
            List<Integer> neighbours = grid.neighbourStatus(index);
            int next = checkAndReact(grid.getCell(index), neighbours);
            grid.changeNext(index, next);
        }
        grid.updateAll();
    }

    /**
     * get the display grid, which contains all the views for the grid
     * @return displaygrid
     */
    public DisplayGrid getDisplay(){
        return grid.getDisplay();
    }

    /**
     * react according to the neighbour's status
     * @param curCell the cell being checked
     * @param neighbour the list of neighbour status
     * @return the current cell's supposed next state
     */
    public  abstract int checkAndReact(int curCell, List<Integer> neighbour);

}

