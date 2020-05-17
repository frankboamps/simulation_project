package individual_simulations;

import java.util.*;

/**
 * This class holds all the logic behind the fire simulation so that the fire simulation can be displayed.
 * We assume that an empty spot will always be a value of 6, a tree spot with a value of 5, and a burning spot with a value of 3
 *
 * To create a Fire simulation that would have a width and height of 100, a triangular cell shape, and a probability of catching on fire of 0.25 while also
 * checking all neighbors would require a call like this:
 *
 * new Fire(100, 100, true, Triangle, 0.25);
 *
 * @author Franklin Boampong
 */
public class Fire extends Simulation {

    private int EMPTY = 6;
    private int TREE = 5;
    private int BURNING = 3;
    private double probCatch;

    //private Grid grid;

    /**
     * Create a fire simulation; initialize grid and probability of catching on fire
     * @param row               number of rows
     * @param col               number of columns
     * @param neighbourNumber   true = all neighbours, false = only immediate
     * @param prob              probability of catching on fire
     * @param shape             the shape of the cell
     */
    public Fire(int row, int col, boolean neighbourNumber, String shape, double prob) {
        super( row,  col,  neighbourNumber, false, shape);
        grid.iniState(new int[]{TREE});
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                indices.add(new int[]{i, j});
                if(i==0 || j==0 || i==row-1 || j==col-1) grid.changeNext(new int[]{i,j}, EMPTY);
            }
        }
        boolean findFire = true;
        while(findFire) {
            Collections.shuffle(indices);
            int i = (indices.get(0))[0];
            int j = (indices.get(0))[1];
            if(grid.getCell(new int[] {i,j})!=EMPTY){
                grid.changeNext(new int[]{i,j}, BURNING);
                findFire = false;
            }
        }

        grid.updateAll();
        probCatch = prob;
    }


    /**
     * ask grid to find the number of each of TREE, BURNING, and EMPTY cell
     * @return hashmap with information
     */
    public HashMap<String, Integer> frequency() {
        HashMap<String, Integer>ret = new HashMap<>();
        ret.put("TREE", grid.getFreq(TREE));
        ret.put("BURNING", grid.getFreq(BURNING));
        ret.put("EMPTY", grid.getFreq(EMPTY));
        return ret;
    }



    /**
     * if the current cell is burning, its next state will be empty
     * if the current cell is empty, it remains empty
     * if the current cell is tree, get neighbours who are burning
     * for each neighbour that is burning, the current cell has a probcatch chance of being affected (burned)
     * @param curCell       state of current cell (at the index we are checking
     * @param neighbours    list of status of neighbours
     * @return              return the nextstate of the current cell
     */
    public int checkAndReact(int curCell,  List<Integer> neighbours){
        if(curCell==BURNING) return EMPTY;
        if(curCell==EMPTY) return EMPTY;
        int numBurn = 0;
        for(int n:neighbours){
            if(n==BURNING) numBurn++;
        }
        if(numBurn==0) return TREE;
        int status = TREE;
        for(int i = 0; i<numBurn; i++){
            status = (Math.random() <= probCatch) ? BURNING : status;
        }
        return status;
    }

}

