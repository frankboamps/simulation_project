package individual_simulations;

import java.util.*;

/**
 * This class contains all the logic needed for the Segregation simulation.
 *
 * To create a Segregation simulation object that has a height of 150, a width of 100, rectangular-shaped cells, and a neighbor
 * satisfaction rate of 0.75 while looking at
 * all neighbors instead of just the most immediate ones, you would call:
 *
 *
 * mySimulation = new Segregation(150, 100, true, Rectangle, 0.75);
 *
 * @author Lucy Gu
 *
 */

public class Segregation extends Simulation {
    private int BLUE = 2;
    private int RED = 3;
    private int BLANK = 4;
    ArrayList<int[]> unsatisfied = new ArrayList<>();
    ArrayList<Integer> unusedCell = new ArrayList<>();
    //private Grid grid;

    private double satisfyRate;

    /**
     * Create new segregation grid
     * @param row               number of rows
     * @param col               number of columns
     * @param neighbourNumber   true = all neighbours, false = only immediate
     * @param shape             String that tells if should be triangle or rectangle
     * @param satisfied         the satisfaction rate
     */
    public Segregation(int row, int col, boolean neighbourNumber, String shape, double satisfied) {
        super(row, col, neighbourNumber, false, shape);
        grid.iniState(new int[]{BLUE, BLANK, RED});
        createIndices(row, col);

        setSatisfyRate(satisfied);
    }


    private void setSatisfyRate(double satisfyRate) {
        this.satisfyRate = satisfyRate;
    }


    /**
     * get the red, blue, and blank cell numbers (supposedly they will be the same every time)
     * @return hashmap with the information
     */
    public HashMap<String, Integer> frequency() {
        HashMap<String, Integer>ret = new HashMap<>();
        ret.put("RED", grid.getFreq(RED));
        ret.put("BLUE", grid.getFreq(BLUE));
        ret.put("BLANK", grid.getFreq(BLANK));
        return ret;
    }

    /**
     * check all the cells and see if they are satisfied; if not, collect index
     * for all unsatisfied cells do random placement
     */
    @Override
    public void updateGrid() {
        unsatisfied = new ArrayList<>();
        for (int[] index : indices) {
            List<Integer> neighbours = grid.neighbourStatus(index);
            int next = checkSatisfy(grid.getCell(index), neighbours);
            if (next==100) unsatisfied.add(index);
            grid.changeNext(index, next);
        }
        //Collections.shuffle(unsatisfied);
        Collections.shuffle(unusedCell);
        for (int[] index2 : unsatisfied) {
            randPlace(index2);
        }
        grid.updateAll();
    }


    /**
     * just here because declared in super class
     * @param curCell
     * @param neighbour
     * @return 0
     */
    public int checkAndReact(int curCell, List<Integer> neighbour) {
        return 0;
    }


    private int checkSatisfy(int curCell, List<Integer> neighbours) {
        if (curCell == BLANK) {
            unusedCell.add(BLANK);
            return 100;
        }
        int same = 0;
        int nonBlank = 0;
        for (int neighbour : neighbours) {
            if (neighbour == curCell) same++;
            if (neighbour != BLANK) nonBlank++;
        }
        double satisfiedRate = 0;
        if (nonBlank > 0) satisfiedRate = (double) same / nonBlank;
        if (satisfiedRate >= satisfyRate) return curCell;

        unusedCell.add(curCell);
        return 100;

    }


    private void randPlace(int[] index) {
        grid.changeNext(index, unusedCell.get(0));
        unusedCell.remove(0);
    }


}
