package controllers;

import dataBase.CreateDatabase;
import dataBase.Entry;
import dataBase.Property;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import main.App;
import java.util.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

/**
 * This class handles all the functions that relate to
 * the Location and Price Range feature of the application.
 * @author Nathan Koop
 */
public class LocationController {
    
    ObservableList<Entry> tableList; //Used for tableview
    List<Entry> entries = new ArrayList<Entry>();

    @FXML
    private Button mainButton;
    @FXML
    private TableColumn<Entry, Integer> account;
    @FXML
    private TableColumn<Entry, String> address;
    @FXML
    private TableColumn<Entry, String> assessed;
    @FXML
    private TableColumn<Entry, String> assessment;
    @FXML
    private TableColumn<Entry, String> latitude;
    @FXML
    private TableColumn<Entry, String> longitude;
    @FXML
    private TableColumn<Entry, String> neigh;
    @FXML
    private TableView<Entry> table;
    @FXML
    private TextField lat;
    @FXML
    private TextField lon;
    @FXML
    private TextField dist;
    @FXML
    private TextField high;
    @FXML
    private TextField low;
    @FXML
    private Text eLocation;
   @FXML
   private Text eRange;
   @FXML
   private ImageView mapDisplay;
   @FXML
   private AnchorPane mapParent;
   
   private final String apiKey = "AIzaSyA1i5WBgeXiI6xFq7E2lLl4ghZa1zj7M74";

    
    /**
     * Gets a list of all entries within distance of a specific point
     * @param data list of all entries included in the search
     * @param Lat Latitude of the point 
     * @param Lon Longitude of the point
     * @param distance distance Entries can be from the point
     * @return list of all entries in data that are at most distance away from
     *         the specified point.
     */
    List<Entry> GetEntriesByDistance(List<Entry>data, Double Lat, Double Lon, Double distance){
        List<Entry> returnList = new ArrayList<Entry>();
        for(int i = 0; i<data.size(); i++){
            Entry currentEntry = data.get(i);
            Double deltaLat = Lat - Double.parseDouble(currentEntry.getLatitude());
            Double deltaLon = Lon - Double.parseDouble(currentEntry.getLongitude());
            Double dist = Math.sqrt((deltaLat*deltaLat)+(deltaLon*deltaLon));//get distance in degrees
            dist = dist*111195/1000;//convert dist to km
            if(dist <= distance){
                returnList.add(currentEntry);
            }
            
        }
        return returnList;
    }
    
    /**
     * creates parts of a url that relates to drawing a circular path on the map.
     * it returns this url fragment as a string
     * @param numberOfPoints number of points to define the circle
     * @param centerLat latitude of the center of the circle
     * @param centerLon longitude of the center of the circle
     * @param radius radius of the circle (in km)
     * @return string representing the url fragment in relation
     */
    String getCircularPoints(int numberOfPoints, Double centerLat, Double centerLon, Double radius){
        String returnValue = "&path=fillcolor:0x0000ff80|";
        Double toDegrees = 1/(111.195);//Conversion factor of km to degrees
        for(Double theta = 0.0; theta < 2*Math.PI; theta+=((2*Math.PI)/numberOfPoints)){
            Double deltaLat = radius*Math.cos(theta)*toDegrees;
            Double deltaLon = radius*Math.sin(theta)*toDegrees;
            Double pointLat = deltaLat+centerLat;
            Double pointLon = deltaLon+centerLon;
            returnValue = returnValue+pointLat.toString()+","+pointLon.toString();
            if(theta+((2*Math.PI)/numberOfPoints) < 2*Math.PI){
                returnValue+="|";
            }
        }
        return returnValue;
    }
    
    /**
     * Gets a list of entries that are within a specific price range
     * @param data list of all entries included in the search
     * @param minimum the minimum price of an entry
     * @param maximum the maximum price of an entry
     * @return list of all entries in data that have an assessment value
     *         between the minimum and maximum.
     */
    List<Entry> GetEntriesByPriceRange(List<Entry>data, Double minimum, Double maximum){
        List<Entry> returnList = new ArrayList<Entry>();
        for(int i = 0; i<data.size(); i++){
            Entry currentEntry = data.get(i);
            Double value = currentEntry.getAssessedV();
            if(value >= minimum && value <= maximum){
                returnList.add(currentEntry);
            }
        }
        return returnList;
    }
    
    /**
     * calls google static map api to get an image of the location
     * the user has provided. It draws a blue circle encompassing the region
     * that the properties will be in
     * @param Lat latitude of the chosen point
     * @param Lon longitude of the chosen point
     * @param distance radius (in km) the properties must be within
     */
    void updateMapImage(Double Lat, Double Lon, Double distance){
        try{
            int width = (int) Math.floor(mapDisplay.getFitWidth());
            int height = (int) Math.floor(mapDisplay.getFitHeight());
            String circleParam = getCircularPoints(20, Lat, Lon,distance);
            URL url = new URL("https://maps.googleapis.com/maps/api/staticmap?center={"+Lat.toString()+","+Lon.toString()+"}&zoom=7&size="+width+"x"+height+circleParam+"&maptype=roadmap&markers=color:red&key="+apiKey);
            InputStream imageStream = url.openStream();
            mapDisplay.setImage(new Image(imageStream));
        }catch (Exception e){
            System.out.println(e);
        }
    }
    
    /**
     * this function is called when the scene is loaded.
     * it initializes the table and reads in all entries from the 
     * property assessment file. 
     */
    public void initialize(){
        URL url = getClass().getResource("/data/Property_Assessment_Data.csv");
        Property db = CreateDatabase.createDB(url);
        entries = db.getAllEntries();
        this.tableList = FXCollections.observableArrayList(entries);
        account.setCellValueFactory(new PropertyValueFactory<>("accountId"));
        address.setCellValueFactory(new PropertyValueFactory<>("address"));
        assessed.setCellValueFactory(new PropertyValueFactory<>("assessedValue"));
        assessment.setCellValueFactory(new PropertyValueFactory<>("assessmentClass"));
        latitude.setCellValueFactory(new PropertyValueFactory<>("latitude"));
        longitude.setCellValueFactory(new PropertyValueFactory<>("longitude"));
        neigh.setCellValueFactory(new PropertyValueFactory<>("neighbourhood"));
        
        this.table.setItems(tableList);
        mapDisplay.fitWidthProperty().bind(mapParent.widthProperty());
        mapDisplay.fitHeightProperty().bind(mapParent.heightProperty());
        System.out.println(getCircularPoints(20, 53.0, -113.0, 600.0));
    }
    
    /**
     * called when the return to main menu button is pressed.
     * this function returns the user to the main menu.
     * @param event The ActionEvent passed when the button is pressed.
     */
    @FXML
    void mainPress(ActionEvent event) throws IOException {
	App.setNewScene("mainPage", 760, 537);
    }
    
    /**
     * Called when the search button is pressed.
     * Gets and displays a list of all entries that match the users
     * specific search requirements.
     * @param event The ActionEvent passed when the button is pressed.
     */
    @FXML
    void SearchPress(ActionEvent event){
        eRange.setText("");
        eLocation.setText("");
        List<Entry> currentItems = entries;
        //Search by distance
        String lats = lat.getText();
        String lons = lon.getText();
        String dists = dist.getText();
        if(!((lats.equals("")||lats.equals(" "))&&(lons.equals("")||lons.equals(" "))&& (dists.equals("")||dists.equals(" ")))){
            try{
                Double Lat = Double.parseDouble(lats);
                Double Lon = Double.parseDouble(lons);
                Double Dist = Double.parseDouble(dists);
                currentItems = GetEntriesByDistance(currentItems, Lat, Lon, Dist);
                updateMapImage(Lat,Lon, Dist);
            }catch(Exception e){
                ErrorLocation();
            }
        }else{
            ErrorLocation();
        }
        //Search by price range
        String highs = high.getText();
        String lows = low.getText();
        if(!((highs.equals("")||highs.equals(" "))&&(lows.equals("")||lows.equals(" ")))){
            Double High = Double.parseDouble(highs);
            Double Low = Double.parseDouble(lows);
            if(Low <= High){
                currentItems = GetEntriesByPriceRange(currentItems, Low, High);
            }else{
                ErrorRange();
            }
        }else{
            ErrorRange();
        }
        table.getItems().setAll(currentItems);
    }
    /**
     * this function is called when the data given that
     * relates to location is incorrect.
     * This clears the input fields and displays the error message
     */
    void ErrorLocation(){
        lat.setText("");
        lon.setText("");
        dist.setText("");
        eLocation.setText("Invalid Location Input");
        mapDisplay.setImage(null);
    }
    
    /**
     * this function is called when the data given that
     * relates to price range is incorrect.
     * This clears the input fields and displays the error message
     */
    void ErrorRange(){
        high.setText("");
        low.setText("");
        eRange.setText("Invalid Price Range");
    }
    
    /**
     * function called when the reset button is pressed.
     * resets all input fields to blank and repopulates the table view
     * with all entries. 
     * @param event The ActionEvent passed when the button is pressed.
     */
    @FXML
    void Reset(ActionEvent event){
        high.setText("");
        low.setText("");
        lat.setText("");
        lon.setText("");
        dist.setText("");
        table.getItems().setAll(entries);
    }

}
