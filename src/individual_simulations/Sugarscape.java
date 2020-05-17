package individual_simulations;

import java.util.*;

/**
 * Sugarscape holds all the logic needed for the Sugar Scape simulation.
 * @author Lucy Gu
 * @author Franklin Boampong
 */

public class Sugarscape extends Simulation{
    private int ZERO = 0;
    private int ONE = 7;
    private int TWO = 8;
    private int THREE= 9;
    private int FOUR = 10;
    private int numAgent = 0;
    private int AGENT = 1;
    private ArrayList<SugarAgent> SugarAgents = new ArrayList<>();
    private ArrayList<SugarAgent> remove = new ArrayList<>();
    private List<Integer> list = new ArrayList<>(Arrays. asList(ZERO, ONE, TWO, THREE, FOUR));
    //private Grid grid;

    /**
     * Create new game of life grid
     * @param row               row number of cell
     * @param col               column number of cell
     * @param neighbourNumber   true = all neighbours, false = only immediate
     */
    public Sugarscape(int row, int col, boolean neighbourNumber, String shape, int agent){
        super(row, col, neighbourNumber, true, shape);
        numAgent = agent;
        grid.iniState(new int[]{FOUR});
        createIndices(row, col);
        Collections.shuffle(indices);
        for(int i = 0; i<numAgent;){
            if(grid.getCellNext(indices.get(i))!=AGENT) {
                grid.changeNext(indices.get(i), AGENT);
                SugarAgents.add(new SugarAgent(indices.get(i)));
                i++;
            }
        }
        grid.updateAll();
    }


    /**
     * get how many SugarAgents are left
     * @return      hashmap with the information
     */
    public HashMap<String, Integer> frequency() {
        HashMap<String, Integer>ret = new HashMap<>();
        ret.put("AGENT", SugarAgents.size());

        return ret;
    }

    /**
     * get all the SugarAgent first, move the SugarAgent
     * general update all cells
     */
    @Override
    public void updateGrid() {
        remove = new ArrayList<>();
        Collections.shuffle(SugarAgents);
        for(SugarAgent SugarAgent : SugarAgents){
            agentReact(SugarAgent);
        }
        SugarAgents.removeAll(remove);
        for(int[] index : indices){
            changeNext(index);
        }
        grid.updateAll();
    }

    /**
     * for particular SugarAgent, grid.depthneighbour gives a list of neighbours who are currently and nextly empty
     * if the size of such neighbour is 0, SugarAgent will not move and its sugar just decrease by metabolism
     * if size is larger than 0, find the closest cell with max sugar, move there, take the sugar
     * @param a SugarAgent being updated
     */
    private void agentReact(SugarAgent a){
        int sugar = a.getSugar();
        int metabolism = a.getMetabolism();
        int[] index = a.getIndex();
        int vision = a.getVision();

        List<int[]> neighbour = grid.depthNeighbour(index, vision, AGENT);
        if(neighbour.size()>0) {
            int[] where = new int[]{0,0};
            int sugarval = 0;
            for (int[] ind : neighbour) {
                if (list.indexOf(grid.getCell(ind)) > sugarval) {
                    where = ind;
                    sugarval = list.indexOf(grid.getCell(ind));
                }
            }

            if (sugar + sugarval > metabolism) {
                a.update(sugar + sugarval - metabolism, where);
                grid.changeNext(where, AGENT);
                grid.changeNext(index, ZERO);
            } else {
                grid.changeNext(index, ZERO);
                remove.add(a);
            }
            return;
        }

        a.update(sugar - metabolism, index);
    }


    /**
     * General update
     * If the cell's next state isn't SugarAgent (hasn't been eaten) and its current state isn't SugarAgent
     * (since above two are handled inside checking SugarAgent)
     * if its current state isn't max, increase by 1; else don't change
     * @param index index of cell being checked
     */
    private void changeNext(int[]index){
        int i = grid.getCell(index);
        if(grid.getCell(index)!=AGENT && grid.getCellNext(index)!=AGENT){
            int j = list.indexOf(i);
            if(j==list.size()-1) return;
            else{
                i = list.get(j+1);
                grid.changeNext(index, i);
            }
        }
    }


    /**
     * this is just here because it's in the super class
     * @param curCellStatus
     * @param neighbours
     * @return
     */
    public int checkAndReact(int curCellStatus, List<Integer> neighbours){
          return 0;
    }

}
