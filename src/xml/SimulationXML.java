package xml;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Simple immutable value object representing simulation data
 *
 * This class is mainly used in the SimulationXMLFileChooser Class to store the tag data from the xml file
 *
 * @author Michelle Tai
 */
public class SimulationXML {
  // name in data file that will indicate it represents data for this type of object
  public static final String DATA_TYPE = "Simulation";
  // field names expected to appear in data file holding values for this object
  // NOTE: simple way to create an immutable list
  public static final List<String> DATA_FIELDS = List.of(
      "title",
      "author",
      "width",
      "height",
      "random",
      "shape",
      "initialConfig"
  );

  // specific data values for this instance
  private String myTitle;
  private String myAuthor;
  private int myWidth;
  private int myHeight;
  private String myInitialConfig;
  private String isRandom;
  private String shape;
  private List<List<Integer>> getGridConfig;
  // NOTE: keep just as an example for converting toString(), otherwise not used
  private Map<String, String> myDataValues;


  /**
   * Create game data from given data.
   */
  public SimulationXML(String title, String author, int width, int height, String random, String shapes, String initialConfig) {
    myTitle = title;
    myAuthor = author;
    myWidth = width;
    myHeight = height;
    myInitialConfig = initialConfig;
    isRandom = random;
    shape = shapes;
    // NOTE: this is useful so our code does not fail due to a NullPointerException
    myDataValues = new HashMap<>();
    getGridConfig = new ArrayList<>();
  }

  /**
   * Create game data from data structure of Strings.
   *
   * @param dataValues map of field names to their values
   */
  public SimulationXML(Map<String, String> dataValues) {
    this(dataValues.get(DATA_FIELDS.get(0)),
        dataValues.get(DATA_FIELDS.get(1)),
        Integer.parseInt(dataValues.get(DATA_FIELDS.get(2))),
        Integer.parseInt(dataValues.get(DATA_FIELDS.get(3))),
        dataValues.get(DATA_FIELDS.get(4)),
        dataValues.get(DATA_FIELDS.get(5)),
        dataValues.get(DATA_FIELDS.get(6)));
    myDataValues = dataValues;
  }

  /**
   * Returns title of this simulation.
   */
  public String getTitle () {
    return myTitle;
  }

  /**
   * Returns author of this file.
   */
  public String getAuthor() {
    return myAuthor;
  }

  /**
   * Returns the number of cells in a single row of the simulation grid.
   */
  public int getWidth() {
    return myWidth;
  }

  /**
   * Returns the number of cells in a single columns of the simulation grid.
   */
  public int getHeight() {
    return myHeight;
  }

  /**
   * If the string is exactly "false", false will be returned
   * else return true
   */
  public boolean isRandom(){
    return !isRandom.equals("false");
  }

  /**
   * Returns the shape
   */
  public String getShape() {
    return shape;
  }

  /**
   * Returns the initial configurations in the form of a grid
   */
  public List<List<Integer>> getInitialConfig() {
    String[] initialConfigArray = myInitialConfig.split("\n");
    List<List<Integer>> ret = new ArrayList<>();

    for(String rowStr : initialConfigArray){
      List<Integer> row = new ArrayList<>();
      String[] rowArr = rowStr.split(" ");
      for(int j = 0; j < rowArr.length; j++){
        row.add(Integer.parseInt(rowArr[j]));
      }
      ret.add(row);
    }
    return ret;
  }

  /**
   * @see Object#toString()
   */
  @Override
  public String toString () {
    StringBuilder result = new StringBuilder();
    result.append(DATA_TYPE + " = [\n");
    for (Map.Entry<String, String> e : myDataValues.entrySet()) {
      result.append("  ").append(e.getKey()).append(" = '").append(e.getValue()).append("',\n");
    }
    result.append("]\n");
    return result.toString();
  }
}
