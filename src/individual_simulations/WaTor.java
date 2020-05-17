package individual_simulations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * WaTor holds all the logic needed for the WaTor simulation
 *
 * To create a WaTor simulation that has a height of 150, a width of 100, rectangular cells, and only immediate neighbor checking,
 * as well as fishes having a reproduction time of 2 chronons and being worth 2 energy and sharks having a reproduction time of 10 chronons,
 * you would have a call:
 *
 * Simulation mySimulation = new WaTor(150, 100, false, Rectangle, 2, 10, 2);
 * @author Lucy Gu
 */
public class WaTor extends Simulation {
  private int FISH = 5;
  private int SHARK = 1;
  private int BLANK = 4;

  private int sharkthreshold;
  private int fishthreshold;
  private int initenergy;

  private List<List<int[]>> status = new ArrayList<>();
  //private Grid grid;


  /**
   * Create new wator grid
   * @param row               number of rows
   * @param col               number of columns
   * @param neighbourNumber   true = all neighbours, false = only immediate
   * @param energy            initial energy of shark
   * @param sharkR            shark reproduction cycle
   * @param fishR             fish reproduction cycle
   */

  public WaTor(int row, int col, boolean neighbourNumber, String shape, int energy, int sharkR, int fishR) {
    super(row, col, neighbourNumber, true, shape);
    initenergy = energy;
    sharkthreshold = sharkR;
    fishthreshold = fishR;

    grid.iniState(new int[]{FISH, BLANK, SHARK});

    createIndices(row, col);
    for (int i = 0; i < row; i++) {
      status.add(new ArrayList<>());
      for (int j = 0; j < col; j++) {
        if(grid.getCell(new int[]{i,j})==SHARK) status.get(i).add(new int[]{0, initenergy});
        else status.get(i).add(new int[]{0, 0});
      }
    }

  }

  /**
   * makes a map that contains number of fish, shark, and blank cells
   * @return hashmap with information needed
   */

  public HashMap<String, Integer> frequency() {
    HashMap<String, Integer>ret = new HashMap<>();
    ret.put("FISH", grid.getFreq(FISH));
    ret.put("SHARK", grid.getFreq(SHARK));
    ret.put("BLANK", grid.getFreq(BLANK));

    return ret;
  }

  /**
   * update grid by
   * collecting index of cells whose current state is shark
   * update these cell's next
   *
   * collecting index of cells whose current AND next state is fish (which means it's not eaten by shark)
   * update there cell's next
   *
   * update grid
   */
  @Override
  public void updateGrid(){
    //checkAllShark
    ArrayList<int[]> sharkInd = new ArrayList<>();
    for(int[] index:indices){
      if(grid.getCell(index)==BLANK) grid.changeNext(index, BLANK);
      if(grid.getCell(index)==SHARK) sharkInd.add(index);
    }
    Collections.shuffle(sharkInd);
    for(int[] shark:sharkInd) checkSharkUpdate(shark);


    ArrayList<int[]> fishInd = new ArrayList<>();
    for(int[] index:indices){
      if(grid.getCell(index)==FISH && grid.getCellNext(index)==FISH) fishInd.add(index);
    }
    Collections.shuffle(fishInd);
    for(int[] fish:fishInd) checkFishUpdate(fish);

    grid.updateAll();

  }


  /**
   * It's just here because it's declared in the super class
   * @param curCell
   * @param neighbour
   * @return
   */
  public int checkAndReact(int curCell, List<Integer> neighbour) {
    return 0;
  }


  private void checkSharkUpdate(int[] index){
    //round++

    int[] roundEnergy = status.get(index[0]).get(index[1]);
    roundEnergy[0]++;

    List<int[]> fishNeighbour = grid.neighbourIndexSatisfyingRequirement(index, FISH);
    List<int[]> emptyNeighbour = grid.neighbourIndexSatisfyingRequirement(index, BLANK);

    if(fishNeighbour.size()>0){
      roundEnergy[1]++;
      status.get(index[0]).set(index[1], roundEnergy);

      Collections.shuffle(fishNeighbour);
      int[] fish = fishNeighbour.get(0);
      boolean reproduced = reproduce(index, fish, true);
      if(!reproduced) moveToEmpty(index, fish, true);
      return;
    }

    roundEnergy[1]--;
    status.get(index[0]).set(index[1], roundEnergy);
    if(roundEnergy[1]<=0){
      sharkDeath(index);
      return;
    }
    if(emptyNeighbour.size()==0){
      return;
    }

    Collections.shuffle(emptyNeighbour);
    int[] space = emptyNeighbour.get(0);
    boolean reproduced = reproduce(index, space, true);
    if(!reproduced) moveToEmpty(index, space, true);

  }

  private void sharkDeath(int[] index) {
    grid.changeNext(index, BLANK);
    status.get(index[0]).set(index[1], new int[]{0,0});
  }

  private void moveToEmpty(int[] index, int[] space, boolean isShark) {
    if(isShark){
      moveTo(index, space, BLANK, SHARK);
      return;
    }
    moveTo(index, space, BLANK, FISH);
  }

  private boolean reproduce(int[] creature, int[] moveSpace, boolean isShark){
    int[] state = status.get(creature[0]).get(creature[1]);
    if(isShark && status.get(creature[0]).get(creature[1])[0]>=sharkthreshold){
      grid.changeNext(creature, SHARK);
      grid.changeNext(moveSpace, SHARK);
      status.get(creature[0]).set(creature[1], new int[]{0,initenergy});
      status.get(moveSpace[0]).set(moveSpace[1], new int[]{1, state[1]});
      return true;
    }
    else if(!isShark && status.get(creature[0]).get(creature[1])[0]>=fishthreshold){
      moveTo(creature, moveSpace, FISH, FISH);
      status.get(moveSpace[0]).set(moveSpace[1], new int[]{1,0});
      return true;
    }
    return false;
  }

  private void moveTo(int[] creature, int[] moveSpace, int fish, int fish2) {
    grid.changeNext(creature, fish);
    grid.changeNext(moveSpace, fish2);
    status.get(moveSpace[0]).set(moveSpace[1], status.get(creature[0]).get(creature[1]));
    status.get(creature[0]).set(creature[1], new int[]{0,0});
  }


  private void checkFishUpdate(int[] index){
    int rount = status.get(index[0]).get(index[1])[0];
    rount++;
    status.get(index[0]).set(index[1], new int[]{rount,0});

    List<int[]> emptyNeighbour = grid.neighbourIndexSatisfyingRequirement(index, BLANK);

    if(emptyNeighbour.size()==0) return;

    Collections.shuffle(emptyNeighbour);
    int[] space = emptyNeighbour.get(0);
    boolean reproduced = reproduce(index, space, false);
    if(!reproduced) moveToEmpty(index,space,false);

  }

}
