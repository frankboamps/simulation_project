package individual_simulations;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * This class deals with all the logic for the rock paper scissor simulation
 *
 * To create a Rock Paper Scissor simulation of width 100 and height 150, checks only immediate neighbors instead
 * of all neighbors, has cells in the shapes of rectangles, and has a threshold of winning equal to 2, you would call:
 *
 * Simulation mySimulation = new RockPaperScissor(150, 100, false, Rectangle, 2);
 *
 * @author Franklin Boampong
 * @author Lucy Gu
 */
public class RockPaperScissor extends Simulation{
    private int ROCK = 2;
    private int PAPER = 3;
    private int SCISSOR = 5;
    private int threshold;
    //private Grid grid;

    /**
     * Create new rock paper scissor grid
     * @param row               number of rows
     * @param col               number of columns
     * @param neighbourNumber   true = all neighbours, false = only immediate
     * @param shape             String that tells if should be triangle or rectangle
     * @param thresh            threshhold for change cell state
     */
    public RockPaperScissor(int row, int col, boolean neighbourNumber, String shape, int thresh){
        super(row, col, neighbourNumber, false, shape);
        grid.iniState(new int[]{ROCK, PAPER, SCISSOR});
        createIndices(row, col);
        threshold = thresh;
    }


    /**
     * ask grid to find the number of rock, paper, and scissor
     * @return      hashmap with the information
     */
    public HashMap<String, Integer> frequency() {
        HashMap<String, Integer>ret = new HashMap<>();
        ret.put("ROCK", grid.getFreq(ROCK));
        ret.put("PAPER", grid.getFreq(PAPER));
        ret.put("SCISSOR", grid.getFreq(SCISSOR));

        return ret;
    }


    /**
     * check if the current cell needs to change state. If there are a certain number or more of its neighbouring cell
     * that is able to beat current cell, change to the status of the neighbouring cell that beats it
     * @param curCellStatus status of current cell
     * @param neighbours    list of status of neighbour current cell
     * @return              status of current cell's next state
     */

    public int checkAndReact(int curCellStatus, List<Integer> neighbours){
        int rock = 0;
        int paper = 0;
        int scissor = 0;

        for(int i : neighbours){
            if(i==ROCK) rock++;
            if(i==PAPER) paper++;
            if(i==SCISSOR) scissor++;
        }

        int thresh = threshold +  + (new Random()).nextInt(2);
        if(curCellStatus==ROCK && paper>thresh) return PAPER;
        if(curCellStatus==SCISSOR && rock>thresh) return ROCK;
        if(curCellStatus==PAPER && scissor>thresh) return SCISSOR;
        return curCellStatus;
    }

}
