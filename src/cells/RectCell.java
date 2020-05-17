package cells;

import javafx.scene.shape.Rectangle;

/**
 * Cell holds information about neighbors and has a Rectangle Shape. The status of the cell reflects the color of the cell
 * @author Lucy Gu
 */

public class RectCell extends Cell {

    /**
     * Constructor for the RectCell class
     * @param x is the x position on the scene
     * @param y is the y position on the scene
     * @param width is the width of the drawn cell
     * @param height is the height of the drawn cell
     * @param status is the current state of the cell
     */
    public RectCell(int x, int y, double width, double height, int status) {
        super(x, y, status);
        cellImage = new Rectangle();
        ((Rectangle) cellImage).setWidth(width);
        ((Rectangle) cellImage).setHeight(height);
        ((Rectangle) cellImage).setX((x)*((Rectangle) cellImage).getWidth());
        ((Rectangle) cellImage).setY((y)*((Rectangle) cellImage).getHeight());
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
