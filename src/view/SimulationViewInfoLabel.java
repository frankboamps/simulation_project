package view;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.text.Font;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ResourceBundle;


/**
 * Class SimulationViewInfoLabel which extends the label class
 * Class extends the Label super class
 * @author Franklin Boampong
 */
public class SimulationViewInfoLabel extends Label {
    private static final String RESOURCES = "resources";
    public static final String DEFAULT_RESOURCE_PACKAGE = RESOURCES + ".";
    private ResourceBundle myResources;
    private int fontSize;

    /**
     * Constructor method for class SimulationViewInfoLabel
     *
     * @param description specifies label string
     * @param width       specifies the width of the label
     * @param height      specifies height of label
     */
    public SimulationViewInfoLabel(String description, int width, int height) {
        myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "English");
        setPrefWidth(width);
        setPrefHeight(height);
        setText(description);
        setWrapText(true);
        fontSize = Integer.parseInt(myResources.getString("FSize"));
        setInfoLabelFont();
        BackgroundImage labelBackground = new BackgroundImage(new Image(myResources.getString("LabelBackground"), width, height, false, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
        setBackground(new Background(labelBackground));
    }

    private void setInfoLabelFont() {
        try {
            setFont(Font.loadFont(new FileInputStream(new File(myResources.getString("FontStylePath"))), fontSize));
        } catch (FileNotFoundException e) {
            setFont(Font.font("TimesRoman", fontSize));
        }
    }

}
