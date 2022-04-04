package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dataBase.CreateDatabase;
import dataBase.Entry;
import dataBase.Property;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
//import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import main.App;

public class JamieController {
    
    //private Property db;
    
    private final String APIKEY = "&key=AIzaSyA1i5WBgeXiI6xFq7E2lLl4ghZa1zj7M74";
    
    private String address = "15308 82 Avenue NW Edmonton";
    private String origin = address.replace(" ", "+");
    //private String destination = "West+Edmonton+Mall";
    
    private String locationLatitude;
    private String locationLongitude;
    
    private String[] locations = {"West Edmonton Mall", "Royal Alberta Museum",
        "Fort Edmonton Park", 
        "Muttart Conservatory", "Alberta Legislature", "Art Gallery of Alberta", 
        "Edmonton Expo Centre", "Edmonton Valley Zoo", "Telus World of Science", 
        "University of Alberta", "Rogers Place", "William Hawrelak Park", 
        "Old Strathcona Farmer's Market", "Kingsway Mall", 
        "Commonwealth Stadium", "Citadel Theatre"};
    
    private URL path = getClass().getResource("/data/Property_Assessment_Data.csv");
    private Property db = CreateDatabase.createDB(path);
    

    @FXML
    private Button mainButton;
    @FXML
    private Button byAccountButton;
    @FXML
    private TextField accountTextField;
    @FXML
    private Button byAddressButton;
    @FXML
    private TextField addressTextField;
    @FXML
    private Button searchButton;
    @FXML
    private Button resetButton;
    @FXML
    private ComboBox notableLocationsComboBox;
    @FXML
    private TextField distanceTextField;
    @FXML
    private TextField timeTextField;
    @FXML
    private Text inputErrorText;
    @FXML
    private ImageView notableLocationImageView;
    @FXML
    private Text notableLocationTitle;


    @FXML
    void mainPress(ActionEvent event) throws IOException {
		App.setNewScene("mainPage", 760, 537);
    }
    public void initialize() {
        //URL path = getClass().getResource("/data/Property_Assessment_Data.csv");
        //db = CreateDatabase.createDB(path);
        
        notableLocationsComboBox.getItems().clear();
        notableLocationsComboBox.getItems().addAll("Alberta Legislature",
                "Art Gallery of Alberta","Citadel Theatre","Commonwealth Stadium",
                "Edmonton Expo Centre","Edmonton Valley Zoo","Fort Edmonton Park",
                "Kingsway Mall","Macewan University","Muttart Conservatory",
                "Old Strathcona Farmer\'s Market",
                "Rogers Place","Royal Alberta Museum","Telus World of Science",
                "University of Alberta","West Edmonton Mall","William Hawrelak Park");
        
        
        addressTextField.setEditable(false);
        inputErrorText.setVisible(false);
        notableLocationTitle.setText("");
        addressTextField.setText("");
        accountTextField.setText("");
        timeTextField.setText("");
        distanceTextField.setText("");
        notableLocationImageView.setImage(null);
        
    
        if (db == null) {
                System.out.println("Failed to load database");
                return;
        }
    }
    @FXML
    void byAccountNumberButton(){
        addressTextField.setText("");
        addressTextField.setEditable(false);
        accountTextField.setEditable(true);
    }
    @FXML
    void byAdressActionButton(){
        accountTextField.setText("");
        accountTextField.setEditable(false);
        addressTextField.setEditable(true);
    }
    @FXML
    void resetButtonAction(){
        initialize();
    }
    @FXML
    void searchButtonPress(){
        String notableLocation = (String) notableLocationsComboBox.getValue();
        if(notableLocation == null){
            inputErrorText.setVisible(true);
            return;
        }
        if(!accountTextField.getText().equals("")){
            try{
                int accountNumber = Integer.parseInt(accountTextField.getText());
                
                byAccountNumber(accountNumber);
                //setImage(notableLocation);
                
                //inputErrorText.setVisible(false);
                return;
            }
            catch(Exception e){
                inputErrorText.setVisible(true);
                timeTextField.setText("");
                distanceTextField.setText("");
                notableLocationTitle.setText("");
                notableLocationImageView.setImage(null);
                return;
                
            }
        }
        if(!addressTextField.getText().equals("")){
            try{
                String address = addressTextField.getText();
                
                byAddress(address.toUpperCase());
                //setImage(notableLocation);
                
                return;
            }
            catch(Exception e){
                inputErrorText.setVisible(true);
                timeTextField.setText("");
                distanceTextField.setText("");
                notableLocationTitle.setText("");
                notableLocationImageView.setImage(null);
                return;
                
            }
        }
        inputErrorText.setVisible(true);
        timeTextField.setText("");
        distanceTextField.setText("");
        notableLocationTitle.setText("");
        notableLocationImageView.setImage(null);
        
        
    }
    public void byAccountNumber(int accountNumber){
        Entry thisAccount = db.getEntry(accountNumber);
        if(thisAccount != null){ // change to != after testing ui
            //String address = thisAccount.getAddress();
            String coordinates = thisAccount.getLatitude()+ ","+thisAccount.getLongitude();
            
            String notableLocation = (String) notableLocationsComboBox.getValue();
            String destination = notableLocation.replace(" ", "+");
            destination = destination + "+Edmonton+Alberta";
        
            //System.out.println(address);
            try {
                URL url = 
                        new URL("https://maps.googleapis.com/maps/api/directions/"
                                        + "json?origin=" + coordinates + "&destination=" + 
                                        destination + APIKEY);  
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();

                if (conn.getResponseCode() == 200) {
                    StringBuilder directionMess = new StringBuilder();
                    Scanner scanner = new Scanner(url.openStream());

                    while (scanner.hasNext()) {
                            directionMess.append(scanner.nextLine());
                    }
                    //Close the scanner
                    scanner.close();

                    //Parse json string, sent from google's direction api
                    ObjectMapper mapper = new ObjectMapper();
                    JsonNode rootJsonNode = mapper.readTree(directionMess.toString());
                    JsonNode jarr = rootJsonNode.get("routes");
                    JsonNode jsonNode1 = jarr.get(0);
                    JsonNode jarr2 = jsonNode1.get("legs");
                    JsonNode jsonNode2 = jarr2.get(0);
                    JsonNode duration = jsonNode2.get("duration");
                    JsonNode distance = jsonNode2.get("distance");

                    
                    timeTextField.setText(duration.get("text").toString().replace("\"", ""));
                    distanceTextField.setText(distance.get("text").toString().replace("\"", ""));
                    inputErrorText.setVisible(false);
                    notableLocationTitle.setText(notableLocation);
                    setImage(notableLocation);

                } else {
                    distanceTextField.setText("Failed connection!");
                    timeTextField.setText("Failed connection!");
                }
            } catch (Exception e) {
                        System.out.println("Failed to connect, or some other error");
        }
        }
        else{
            //System.out.println("Something went wrong");
            inputErrorText.setVisible(true);
            timeTextField.setText("");
            distanceTextField.setText("");
            notableLocationTitle.setText("");
            notableLocationImageView.setImage(null);
        }	
    }
    public void byAddress(String address){
        Entry thisAccount = db.getEntry(address);
        if(thisAccount != null){ // change to != after testing ui
            //String address = thisAccount.getAddress();
            String coordinates = thisAccount.getLatitude()+ ","+thisAccount.getLongitude();
            
            String notableLocation = (String) notableLocationsComboBox.getValue();
            String destination = notableLocation.replace(" ", "+");
            destination = destination + "+Edmonton+Alberta";
        
            //System.out.println(address);
            try {
                URL url = 
                        new URL("https://maps.googleapis.com/maps/api/directions/"
                                        + "json?origin=" + coordinates + "&destination=" + 
                                        destination + APIKEY);  
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();

                if (conn.getResponseCode() == 200) {
                    StringBuilder directionMess = new StringBuilder();
                    Scanner scanner = new Scanner(url.openStream());

                    while (scanner.hasNext()) {
                            directionMess.append(scanner.nextLine());
                    }
                    //Close the scanner
                    scanner.close();

                    //Parse json string, sent from google's direction api
                    ObjectMapper mapper = new ObjectMapper();
                    JsonNode rootJsonNode = mapper.readTree(directionMess.toString());
                    JsonNode jarr = rootJsonNode.get("routes");
                    JsonNode jsonNode1 = jarr.get(0);
                    JsonNode jarr2 = jsonNode1.get("legs");
                    JsonNode jsonNode2 = jarr2.get(0);
                    JsonNode duration = jsonNode2.get("duration");
                    JsonNode distance = jsonNode2.get("distance");

                    
                    timeTextField.setText(duration.get("text").toString().replace("\"", ""));
                    distanceTextField.setText(distance.get("text").toString().replace("\"", ""));
                    inputErrorText.setVisible(false);
                    notableLocationTitle.setText(notableLocation);
                    setImage(notableLocation);

                } else {
                    distanceTextField.setText("Failed connection!");
                    timeTextField.setText("Failed connection!");
                }
            } catch (Exception e) {
                        System.out.println("Failed to connect, or some other error");
        }
        }
        else{
            inputErrorText.setVisible(true);
            timeTextField.setText("");
            distanceTextField.setText("");
            notableLocationTitle.setText("");
            notableLocationImageView.setImage(null);
        }   
    }
    public void setImage(String notableLocation){
        try{
            String formattedName = notableLocation.replace(" ","_");
            formattedName.replace("'","");
            formattedName = formattedName + ".jpg";
            
            Image image = new Image(new FileInputStream("src/main/resources/images/"+formattedName));

            notableLocationImageView.setImage(image);
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
}
