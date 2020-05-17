package grids;

import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.List;

/**
 * Display grid just stores shape, passing information from the models to views
 * @author LG
 */
public class DisplayGrid{

    private ArrayList<ArrayList<Shape>> grid = new ArrayList<>();

    /**
     * a display grid that holds shapes
     * @param row height of grid
     * @param col width of grid
     */
    public DisplayGrid(int row, int col){
        for(int i = 0; i<row; i++){
            grid.add(new ArrayList<>());
            for(int j=0; j<col; j++){
                grid.get(i).add(new Rectangle());
            }
        }
    }

    /**
     * store shape/image at index
     * @param index index of shape
     * @param shape shape being stored
     */
    public void setShape(int[]index, Shape shape){
        grid.get(index[0]).set(index[1],shape);
    }

    /**
     * add everything that is inside this grid to pane for display
     * @param pane shapes are added to this pane
     */
    public void addToPane(AnchorPane pane){
        for(List<Shape> rows: grid){
            for(Shape cell:rows){
                pane.getChildren().add(cell);
            }
        }
    }

    /**
     * remove everything in the grid from pane
     * @param pane shapes are being removed from this pane
     */
    public void removeFromPane(AnchorPane pane){
        for(List<Shape> rows: grid){
            for(Shape cell:rows){
                pane.getChildren().remove(cell);
            }
        }
    }

}
