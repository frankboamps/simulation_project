package xml;

import java.io.File;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 * This class opens up a file chooser to load in XML configuration files for the simulations
 * This class depends on JavaFx stages
 *
 * To use this class, you would instaniate it and then call it's openFile method to open the file chooser dialog
 * and choose the xml file.
 *
 * @author Michelle Tai
 * @author Robert Duvall
 */
public class SimulationXMLFileChooser {

  // kind of data files to look for
  public static final String DATA_FILE_EXTENSION = "*.xml";
  // NOTE: generally accepted behavior that the chooser remembers where user left it last
  public final static FileChooser FILE_CHOOSER = makeChooser(DATA_FILE_EXTENSION);
  private SimulationXML simXML;


  /**
   * Opens the file chooser and saves the parsed XML information into a SimulationXML object
   * @param primaryStage is the JavaFX Stage in which the filechooser will pop up
   * @throws Exception
   */
  public void openFile (Stage primaryStage) throws Exception {
    File dataFile = FILE_CHOOSER.showOpenDialog(primaryStage);
    while (dataFile != null) {
      try {
        Pair<String, SimulationXML> p = new Pair<>(dataFile.getName(), new XMLParser("media").getGame(dataFile));
        // do something "interesting" with the resulting data
        simXML = p.getSecond();
        System.out.println("got data");
//        showMessage(AlertType.INFORMATION, p.getFirst() + "\n" + p.getSecond().toString());
        break;
      }
      catch (SimulationException e) {
        // handle error of unexpected file format
        showMessage(AlertType.ERROR, e.getMessage());
      }
    }
  }

  /**
   * @return the information parsed from the XML file in the form of a SimulationXML object
   */
  public SimulationXML getSimulationXMLInfo(){
    return simXML;
  }


  // display given message to user using the given type of Alert dialog box
  private void showMessage (AlertType type, String message) {
    //new Alert(type, message).showAndWait();
    new Alert(type, message).show();

  }

  // set some sensible defaults when the FileChooser is created
  private static FileChooser makeChooser (String extensionAccepted) {
    FileChooser result = new FileChooser();
    result.setTitle("Open Data File");
    // pick a reasonable place to start searching for files
    result.setInitialDirectory(new File(System.getProperty("user.dir")));
    result.getExtensionFilters().setAll(new ExtensionFilter("Text Files", extensionAccepted));
    return result;
  }

}
