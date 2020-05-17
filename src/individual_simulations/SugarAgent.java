package individual_simulations;

/**
 * SugarAgent object that holds information for each object in the SugarScape simulation
 * @author Fanklin Boampong
 * @author Lucy Gu
 */
public class SugarAgent {
    private int sugar;
    private int metabolism;
    private int vision;

    private int[] index;

    /**
     * SugarAgent object that holds information on the sugar it has, the sugar it needs to consume each round,
     * how far it can see, and its current position
     * initial sugar, metabolism, and vision are randomly chosen
     * @param i position of SugarAgent
     */
    public SugarAgent(int[] i){
        sugar = (int)((Math.random()*5) + 5);
        metabolism = (int)((Math.random()*8) + 1);
        vision = (int)((Math.random()*3) + 1);

        index = i;
    }

    /**
     * @return vision
     */
    public int getVision(){
        return vision;
    }

    /**
     * @return position
     */
    public int[] getIndex(){
        return index;
    }
    /**
     * @return sugar SugarAgent has
     */
    public int getSugar(){
        return sugar;
    }
    /**
     * @return how much sugar SugarAgent eat each round
     */
    public int getMetabolism(){
        return metabolism;
    }

    /**
     * update SugarAgent information (sugar currently have, and new position in index)
     * @param sugarc new sugar count
     * @param position new index
     */
    public void update(int sugarc, int[]position){
        index = position;
        sugar = sugarc;
    }

}
