package grids;

import cells.RectCell;
import view.SimulationViewGUI;
import cells.Cell;
import java.util.*;

/**
 * grid of rectangle cells. have specific neighbouring positions, etc
 * @author LG
 */
public class RectGrid extends Grid {

    /**
     * Create grid with rectangle cells
     * @param row           row of grid
     * @param col           col of grid
     * @param allNeighbour  if true, we're looking for all 8 neighbours, else we're just looking for 4 immediate
     * @param wrap          if true, toroidal (affects all check neighbour methods
     */
    public RectGrid(int row, int col, boolean allNeighbour, boolean wrap){
        super(wrap, row, col);
        if(allNeighbour) numNeighbour = 8;
        else numNeighbour = 4;
        for(int i = 0; i<row; i++){
            grid.add(new ArrayList<>());
            for(int j=0; j<col; j++){
                Cell cell = new RectCell(i, j, (double)SimulationViewGUI.SIMULATION_VIEW_WIDTH/(col),
                        (double)SimulationViewGUI.SIMULATION_VIEW_HEIGHT/row, 0);
                grid.get(i).add(cell);
                display.setShape(new int[]{i,j}, cell.getCellImage());
            }
        }
    }


    /**
     * helper method. x+row[i], y+col[i] gives the position of a neighbour of cell at x,y
     * @return
     */
    @Override
    public int[] rowH(int[]index) {
        return new int[]{-1, 1, 0, 0, -1, 1, -1, 1};
    }

    /**
     * helper method. x+row[i], y+col[i] gives the position of a neighbour of cell at x,y
     * @return
     */
    @Override
    public int[] colH(int[]index) {
        return new int[]{0, 0, -1, 1, 1, -1, -1, 1};
    }


}
