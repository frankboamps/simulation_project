package cells;

import javafx.scene.shape.Polygon;

/**
 * Cell holds information about neighbors and has a Rectangle Shape. The status of the cell reflects the color of the cell
 * @author Lucy Gu
 */

public class TriCell extends Cell {

    private boolean down = false;
    /**
     * Constructor for the RectCell class
     * @param x is the x position on the scene
     * @param y is the y position on the scene
     * @param width is the width of the drawn cell
     * @param height is the height of the drawn cell
     * @param status is the current state of the cell
     */
    public TriCell(int x, int y, double width, double height, int status) {
        super(x, y, status);

        if((x+y)%2==0) down=true;
        cellImage = new Polygon();

        if(down){
            ((Polygon) cellImage).getPoints().addAll(
                    (double)y/2*width, (double)x*height,
                    ((double)y/2+1)*width, (double)x*height,
                    ((double)y/2+0.5)*width, (double)(x+1)*height);
        }
        else{
            ((Polygon) cellImage).getPoints().addAll(
                    ((double)y/2+0.5)*width, (double)x*height,
                ((double)y/2+1)*width, (double)(x+1)*height,
                ((double)y/2)*width, (double)(x+1)*height);
        }

        cellImage.setFill(ColorList[status]);

    }

    /**
     * Updates the color of the cell, which is also updates the state of the cell to the next state
     */
    public void updateColor(){
        updateState();
        cellImage.setFill(ColorList[getCurrentState()]);
    }

}
