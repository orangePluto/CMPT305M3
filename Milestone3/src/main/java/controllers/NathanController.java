package controllers;

import dataBase.CreateDatabase;
import dataBase.Entry;
import dataBase.Property;
import java.io.IOException;
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

public class NathanController {
    
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
    }
    
    /**
     * called when the return to main menu button is pressed.
     * this function returns the user to the main menu.
     * @param event The ActionEvent passed when the button is pressed.
     */
    @FXML
    void mainPress(ActionEvent event) throws IOException {
	App.setRoot("mainPage");
    }
    
    /**
     * Called when the search button is pressed.
     * Gets and displays a list of all entries that match the users
     * specific search requirements.
     * @param event The ActionEvent passed when the button is pressed.
     */
    @FXML
    void SearchPress(ActionEvent event){
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
            }catch(Exception e){
                lat.setText("");
                lon.setText("");
                dist.setText("");
            }
        }else{
            lat.setText("");
            lon.setText("");
            dist.setText("");
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
                high.setText("");
                low.setText("");
            }
        }else{
            high.setText("");
            low.setText("");
        }
        table.getItems().setAll(currentItems);
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
