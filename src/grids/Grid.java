package grids;

import cells.Cell;
import grids.DisplayGrid;
import java.util.*;

/**
 * Grid hold a list of list of cell, a display grid, and other specifications for checking neighbour purposes
 * @author Lucy Gu
 */
public abstract class Grid{

    protected int numNeighbour;
    protected boolean wrapped;
    protected ArrayList<ArrayList<Cell>> grid;
    protected DisplayGrid display;

    public Grid (boolean wrap, int row, int col){
        display = new DisplayGrid(row, col);
        wrapped = wrap;
        grid = new ArrayList<>();
    }

    /**
     * randomly initialize state to the elements contained in possible types
     * @param type possible types
     */
    public void iniState(int[]type){
        for(List<Cell> rows: grid){
            for(Cell cell:rows){
                int status = type[(new Random()).nextInt(type.length)];
                cell.changeNext(status);
                cell.updateColor();
            }
        }
    }

    /**
     * initialize state with an actual configuration
     * @param state configuration
     */
    public void iniState(List<List<Integer>> state){
        for(List<Cell> rows: grid){
            for(Cell cell:rows){
                cell.changeNext(state.get(cell.getIndex1()).get(cell.getIndex2()));
                cell.updateColor();
            }
        }
    }

    /**
     * get the cell at index
     * @param index index of cell
     * @return the cell's status
     */
    public int getCell(int[] index){
        return grid.get(index[0]).get(index[1]).getCurrentState();
    }

    /**
     * get the cell's next at index
     * @param index index of cell
     * @return the cell's next status
     */
    public int getCellNext(int[] index){
        return grid.get(index[0]).get(index[1]).getNextState();
    }

    /**
     * change the cell at index's next state to next
     * @param index tells us which cell
     * @param next the cell't next state
     */
    public void changeNext(int[]index, int next){
        Cell cell = grid.get(index[0]).get(index[1]);
        cell.changeNext(next);
    }

    /**
     * update all cells colors
     */
    public void updateAll(){
        for(List<Cell> rows: grid){
            for(Cell cell:rows){
                cell.updateColor();
            }
        }
    }

    /**
     * gets the frequency of a status in the grid
     * @param status the state whose frequency/number you want
     * @return how many cells inside grid has this status
     */
    public int getFreq(int status){
        int ret = 0;
        for(List<Cell> rows: grid){
            for(Cell cell:rows){
                if(cell.getCurrentState()==status) ret++;
            }
        }
        return ret;
    }

    /**
     * give a list of status of neighbours
     * @param index cell center
     * @return list of status
     */
    public List<Integer> neighbourStatus(int[] index){
        List<int[]> neighbours = neighbourIndex(index);
        ArrayList<Integer> ret = new ArrayList<>();
        for(int[] indice:neighbours){
            ret.add(grid.get(indice[0]).get(indice[1]).getCurrentState());
        }

        return ret;
    }

    /**
     * return the index of a list of neighbours satisfying requirement (namely, its current and next are both equal to status)
     * @param index index of center cell
     * @param status neighbour need to satisfy requirement
     * @return a list of index of such neighbour
     */
    public List<int[]> neighbourIndexSatisfyingRequirement(int[]index, int status){
        List<int[]> neighbours = neighbourIndex(index);
        List<int[]> ret = new ArrayList<>();
        for(int[] indice:neighbours){
            if(grid.get(indice[0]).get(indice[1]).getCurrentState()==status
                    && grid.get(indice[0]).get(indice[1]).getNextState()==status) ret.add(indice);
        }
        return ret;
    }


    /**
     * return a list of index of neighbour according to other specifications
     * @param index center cell
     * @return list of index
     */
    private List<int[]> neighbourIndex(int[]index){
        ArrayList<int[]> ret = new ArrayList<>();
        int[] rowDelta = rowH(index);
        int[] colDelta = colH(index);

        for(int k=0; k < numNeighbour; k++){
            int x = index[0]+rowDelta[k];
            int y = index[1]+colDelta[k];
            checkInRangeAndAddToRet(ret, x, y);
        }
        return ret;
    }

    /**
     * get the display grid related to this grid
     * @return
     */
    public DisplayGrid getDisplay(){
        return display;
    }


    public abstract int[] rowH(int[]index);

    public abstract int[] colH(int[]index);

    /**
     * for sugarscape: find a list of index of neighbour within vision away from center and are not notStatus
     * @param index     index of center cell
     * @param vision    how far we search from the center cell
     * @param notStatus cell at returned indexes can't be this state
     * @return          list of index of neighbours satisfying requirement
     */
    public List<int[]> depthNeighbour(int[] index, int vision, int notStatus){
        ArrayList<int[]> rowcol = new ArrayList<>();
        for(int i = 1; i<=vision; i++){
            ArrayList<int[]> temp = new ArrayList<>();
            temp.add(new int[] {0, i});
            temp.add(new int[] {0, -i});
            temp.add(new int[] {i, 0});
            temp.add(new int[] {-i, 0});
            Collections.shuffle(temp);
            rowcol.addAll(temp);
        }

        ArrayList<int[]> ret = new ArrayList<>();
        for(int k=0; k < vision*4; k++){
            int x = index[0]+rowcol.get(k)[0];
            int y = index[1]+rowcol.get(k)[1];
            checkInRangeAndAddToRet(ret, x, y);
        }
        ArrayList<int[]> temp = new ArrayList<>();
        for(int[]in:ret){
            if(getCell(in)==notStatus || getCellNext(in)==notStatus) temp.add(in);
        }
        ret.removeAll(temp);
        return ret;

    }

    private void checkInRangeAndAddToRet(ArrayList<int[]> ret, int x, int y) {
        if (x>-1 && x < grid.size() && y > -1 && y < grid.get(0).size()){
            ret.add(new int[]{x,y});
        }
        else if(wrapped){
            if(x<0) x = grid.size()-1;
            if(y<0) y = grid.size()-1;
            if(x>=grid.size()) x =0;
            if(y>=grid.size()) y = 0;
            ret.add(new int[]{x,y});
        }
    }
}
