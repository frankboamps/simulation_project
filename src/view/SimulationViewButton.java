package view;

import javafx.event.EventHandler;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.control.Button;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ResourceBundle;

/**
 * This class creates a general button for our simulation, but styled with hover components and such
 * @author Franklin Boampong
 */

public class SimulationViewButton extends Button
{
    private static final int BUTTON_HEIGHT = 45, BUTTON_WIDTH = 150;
    private static final String RESOURCES = "resources";
    private static final String DEFAULT_RESOURCE_PACKAGE = RESOURCES + ".";
    private ResourceBundle myResources;
    private String font;


    /**
     * Constructor method that lays out a button
     * @param words specifies text in button
     * @param language language specifies form of button to present
     */
    public SimulationViewButton(String words, String language) {
        myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "English");
        font = myResources.getString("FontStylePath");
        setText(words);
        setButtonTextFont();
        setPrefHeight(BUTTON_HEIGHT);//45
        setPrefWidth(BUTTON_WIDTH);//190
        setStyle(font);
        mouseUpdateListener();
    }

    private void setButtonTextFont() {
        try {
            setFont(javafx.scene.text.Font.loadFont(new FileInputStream(font), Double.parseDouble(myResources.getString("FSize"))));
        } catch (FileNotFoundException e) {
            setFont(Font.font("TimesRoman", 23));
        }
    }

    private void mouseUpdateListener() {
        setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setEffect(new DropShadow());
            }
        });

        setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setEffect(null);
            }
        });
    }

}
