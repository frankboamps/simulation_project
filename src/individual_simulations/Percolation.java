package individual_simulations;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Percolation class holds the rules and update behavior for the percolation simulation
 *
 * To create a Percolation simulation that is 100 in width and height, checks all neighbors for it's percolation condition, and
 * is in a rectangular cell shape/grid shape, you would make a call like:
 *
 * mySimulation = new Percolation(100, 100, true, Rectangle);
 *
 * @author Michelle Tai
 * @author Lucy Gu
 **/

public class Percolation extends Simulation {
  private int OPEN = 0;
  private int CLOSED = 1;
  private int PERCOLATED = 2;
  private boolean percolate = false;
  private int col = 0;

    /**
     * Create new percolation grid
     *
     * @param row             number of rows
     * @param col             number of columns
     * @param neighbourNumber true = all neighbours, false = only immediate
     */

  public Percolation(int row, int col, boolean neighbourNumber, String shape){
      super(row, col, neighbourNumber, false, shape);
      this.col = row;
      grid.iniState(new int[]{OPEN, CLOSED});

      createIndices(row, col);

      boolean findWater = true;
      while(findWater) {
          Collections.shuffle(indices);
          int i = (indices.get(0))[0];
          int j = (indices.get(0))[1];
          if(!(j==col-1)){
              grid.changeNext(new int[]{i,j}, PERCOLATED);
              findWater = false;
          }
      }
        grid.updateAll();
    }

    /**
     * get a hashmap that tells us how many open, percolated, and closed cells there are
     *
     * @return
     */
    public HashMap<String, Integer> frequency() {
        HashMap<String, Integer> ret = new HashMap<>();
        ret.put("OPEN", grid.getFreq(OPEN));
        ret.put("PERCOLATED", grid.getFreq(PERCOLATED));
        ret.put("CLOSED", grid.getFreq(CLOSED));
        return ret;
    }

    @Override
    public void updateGrid() {
        for (int[] index : indices) {
            List<Integer> neighbours = grid.neighbourStatus(index);
            int next = checkAndReact(grid.getCell(index), neighbours);
            grid.changeNext(index, next);
        }
        grid.updateAll();
        boolean leftpercolate = false;
        boolean rightpercolate = false;
        for (int[] index : indices) {
            if (index[0] == 0 && grid.getCell(index) == PERCOLATED) leftpercolate = true;
            if (index[0] == col - 1 && grid.getCell(index) == PERCOLATED) rightpercolate = true;
        }
        if (leftpercolate && rightpercolate) percolate = true;
    }

    /**
     * if current cell is percolated or closed, don't change
     * if current cell is open, check if there is neighbour who is percolated. If so, percolate
     *
     * @param curCell    current cell's status
     * @param neighbours list of integers denoted to the neighbour's status
     * @return the state the current cell should change to
     */
    public int checkAndReact(int curCell, List<Integer> neighbours) {
        if (!percolate) {
            if (curCell == PERCOLATED || curCell == CLOSED) return curCell;
            for (int neighbour : neighbours) {
                if (neighbour == PERCOLATED) return PERCOLATED;
            }
        }
        return curCell;
    }

}
