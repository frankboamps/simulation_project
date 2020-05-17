package grids;

import cells.TriCell;
import view.SimulationViewGUI;
import cells.Cell;

import java.util.*;
/**
 * grid of triangle cells. have specific neighbouring positions, etc
 * @author LG
 */
public class TriGrid extends Grid {

    /**
     * create grid with triangle cells
     * @param row           row of grid
     * @param col           column of grid
     * @param allNeighbour  12 neighbours or only 3 immediate
     * @param wrap          toroidal or not
     */
    public TriGrid(int row, int col, boolean allNeighbour, boolean wrap){
        super(wrap, row, col);
        if(allNeighbour) numNeighbour = 12;
        else numNeighbour = 3;
        for(int i = 0; i<row; i++){
            grid.add(new ArrayList<>());
            for(int j=0; j<col; j++){
                Cell cell = new TriCell(i, j, (double)SimulationViewGUI.SIMULATION_VIEW_WIDTH*2/(col+1),
                        (double)SimulationViewGUI.SIMULATION_VIEW_HEIGHT/row, 0);
                grid.get(i).add(cell);
                display.setShape(new int[]{i,j}, cell.getCellImage());
            }
        }
    }

    /**
     * helper method. x+row[i], y+col[i] gives the position of a neighbour of cell at x,y
     * index is needed to determine this because if the triangle is downward its neighbour are different from if triangle is upward
     * @return
     */
    @Override
    public int[] rowH(int[]index) {
        boolean down = false;
        if ((index[0] + index[1]) % 2 == 0) down = true;
        if (down) {
            return new int[]{0, 0, -1, 1, 0, 0, -1, -1, 1, 1, -1, -1};
        }
        return new int[]{0, 0, 1, -1, 0, 0, -1, -1, 1, 1, 1, 1};
    }

    /**
     * helper method. x+row[i], y+col[i] gives the position of a neighbour of cell at x,y
     * @return
     */
    @Override
    public int[] colH(int[]index) {
        return new int[]{-1, 1, 0, 0, -2, 2, -1, 1, -1, 1, -2, 2};
    }

}
