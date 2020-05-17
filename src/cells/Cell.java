package cells;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;

/** Cell class serves as a superclass
 *    class will be extended by other forms of grid cells
 *    Gets Info from Simulation to specify layout on the simulation screen
 *    @author LG
 **/
public abstract class Cell{
    private int index1, index2;
    private int currentState;
    private int nextState;
    protected Shape cellImage;
    protected Paint[] ColorList = {Color.WHITE, Color.BLACK, Color.BLUE, Color.RED, Color.WHITE, Color.GREEN, Color.YELLOW,
    Color.LIGHTPINK, Color.LIGHTCORAL, Color.RED, Color.DARKRED};

    public Cell (int x, int y, int status){
        index1 = x;
        index2 = y;
        currentState = status;
        nextState = status;
    }

    /**
     *
     * @return current state
     */
    public int getCurrentState(){
        return currentState;
    }
    /**
     *
     * @return next state
     */
    public int getNextState(){
        return nextState;
    }
    /**
     *
     * @return index1
     */
    public int getIndex1(){
        return index1;
    }
    /**
     *
     * @return index2
     */
    public int getIndex2(){
        return index2;
    }
    /**
     *
     * change next state to i
     */
    public void changeNext(int i){
        nextState = i;
    }
    /**
     *
     * update current state to next state
     */
    public void updateState(){
        currentState = nextState;
    }
    /**
     *
     * @return cell image
     */
    public Shape getCellImage(){
        return cellImage;
    }

    /**
     *
     * update color
     */
    public abstract void updateColor();


}

