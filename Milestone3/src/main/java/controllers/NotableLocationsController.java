package controllers;

// relevant imports needed
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dataBase.CreateDatabase;
import dataBase.Entry;
import dataBase.Property;
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
import javafx.scene.text.Text;
import main.App;

/**
 * NotableLocationsController is a class which controllers the view of
 * notableLocations.fxml using information from a csv
 * 
 * @author Jamie Czelenski
 */
public class NotableLocationsController {
    
    // APIKEY is the key needed to use the google maps directions api
    private final String APIKEY = "&key=AIzaSyA1i5WBgeXiI6xFq7E2lLl4ghZa1zj7M74";
    // get file path and create database of properties for use
    private URL path = getClass().getResource("/data/Property_Assessment_Data.csv");
    private Property db = CreateDatabase.createDB(path);
    
    // initialize FXML objects for use
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

    /**
     * mainPress sends the user back to the main page
     * 
     * @param event click event for the "main" button
     * @throws IOException 
     */
    @FXML
    void mainPress(ActionEvent event) throws IOException {
		App.setNewScene("mainPage", 760, 537);
    }
    /**
     * initialize sets up the fxml file with the relevant information and settings
     * 
     */
    public void initialize() {
        // set up comboBox with the notable locations
        notableLocationsComboBox.getItems().clear();
        notableLocationsComboBox.getItems().addAll("Alberta Legislature",
                "Art Gallery of Alberta","Citadel Theatre","Commonwealth Stadium",
                "Edmonton Expo Centre","Edmonton Valley Zoo","Fort Edmonton Park",
                "Kingsway Mall","Macewan University","Muttart Conservatory",
                "Old Strathcona Farmer\'s Market",
                "Rogers Place","Royal Alberta Museum","Telus World of Science",
                "University of Alberta","West Edmonton Mall","William Hawrelak Park");
        
        // set other fxml objects to desired values and settings
        addressTextField.setEditable(false);
        inputErrorText.setVisible(false);
        notableLocationTitle.setText("");
        addressTextField.setText("");
        accountTextField.setText("");
        timeTextField.setText("");
        distanceTextField.setText("");
        notableLocationImageView.setImage(null);
        timeTextField.setEditable(false);
        distanceTextField.setEditable(false);
        
        // check if the the Property object was not created correctly
        if (db == null) {
                System.out.println("Failed to load database");
                return;
        }
    }
    /**
     * byAccountNumberButton gets the account number field ready for input
     * 
     */
    @FXML
    void byAccountNumberButton(){
        addressTextField.setText("");
        addressTextField.setEditable(false);
        accountTextField.setEditable(true);
    }
    /**
     * byAdressActionButton gets the address field ready for input
     * 
     */
    @FXML
    void byAdressActionButton(){
        accountTextField.setText("");
        accountTextField.setEditable(false);
        addressTextField.setEditable(true);
    }
    /**
     * resetButtonAction resets the view
     * 
     */
    @FXML
    void resetButtonAction(){
        initialize();
    }
    /**
     * searchButtonPress executes code to search and set values according to
     * the input given within the view
     * 
     */
    @FXML
    void searchButtonPress(){
        // get the value from the comboBox to check for null values
        String notableLocation = (String) notableLocationsComboBox.getValue();
        // alert user if notable location has not been selected
        if(notableLocation == null){
            inputErrorText.setVisible(true);
            return;
        }
        // check if there is input in the account TextField
        if(!accountTextField.getText().equals("")){
            try{
                // error checks for if the values given is not a number
                // and gets the account number from the view
                int accountNumber = Integer.parseInt(accountTextField.getText());
                // calls the method to set the values to all the fields in the view
                byAccountNumber(accountNumber);
                return;
            }
            catch(Exception e){
                // if parsing the Integer doesnt work alert user of the mistake
                inputErrorText.setVisible(true);
                timeTextField.setText("");
                distanceTextField.setText("");
                notableLocationTitle.setText("");
                notableLocationImageView.setImage(null);
                return;
                
            }
        }
        // check if there is text in the address TextField
        if(!addressTextField.getText().equals("")){
            try{
                // gets the address from the view
                String address = addressTextField.getText();
                // calls the method to set the values to all the fields in the view
                byAddress(address.toUpperCase());
                return;
            }
            catch(Exception e){
                // if try fails reset the relevant values to "" and alert user
                // of error
                inputErrorText.setVisible(true);
                timeTextField.setText("");
                distanceTextField.setText("");
                notableLocationTitle.setText("");
                notableLocationImageView.setImage(null);
                return;
                
            }
        }
        // if fails for other reason reset the relevant values to "" and alert user
        // of error
        inputErrorText.setVisible(true);
        timeTextField.setText("");
        distanceTextField.setText("");
        notableLocationTitle.setText("");
        notableLocationImageView.setImage(null);
        
        
    }
    /**
     * byAccountNumber is a method that sets the values in the view and gets
     * information from the google maps directions api using an account number
     * 
     * @param accountNumber is the account number given by the user
     */
    public void byAccountNumber(int accountNumber){
        // get the specific account from the database
        Entry thisAccount = db.getEntry(accountNumber);
        // if the account exists execute the code below
        if(thisAccount != null){
            // get coordinates from our Entry
            String coordinates = thisAccount.getLatitude()+ ","+thisAccount.getLongitude();
            // get the notableLocation from the comboBox for api use and ImageView
            String notableLocation = (String) notableLocationsComboBox.getValue();
            // format the destination of the notable location for the api
            String destination = notableLocation.replace(" ", "+");
            destination = destination + "+Edmonton+Alberta";
        
            // try to connect to the api
            try {
                URL url = 
                        new URL("https://maps.googleapis.com/maps/api/directions/"
                                        + "json?origin=" + coordinates + "&destination=" + 
                                        destination + APIKEY);  
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();
                // if connection was a success
                if (conn.getResponseCode() == 200) {
                    StringBuilder directionMess = new StringBuilder();
                    Scanner scanner = new Scanner(url.openStream());
                    // get information from the api response
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
                    // the below nodes are the nodes we want from the response
                    JsonNode duration = jsonNode2.get("duration");
                    JsonNode distance = jsonNode2.get("distance");

                    // set the values and ImageView to the relevant image and/or values
                    timeTextField.setText(duration.get("text").toString().replace("\"", ""));
                    distanceTextField.setText(distance.get("text").toString().replace("\"", ""));
                    inputErrorText.setVisible(false);
                    notableLocationTitle.setText(notableLocation);
                    setImage(notableLocation);

                } else {
                    // if fails alert user
                    distanceTextField.setText("Failed connection!");
                    timeTextField.setText("Failed connection!");
                }
            } catch (Exception e) {
                        System.out.println("Failed to connect, or some other error");
        }
        }
        else{
            // if the account doesnt exists alert user
            inputErrorText.setVisible(true);
            timeTextField.setText("");
            distanceTextField.setText("");
            notableLocationTitle.setText("");
            notableLocationImageView.setImage(null);
        }	
    }
    /**
     * byAddress is a method that sets the values in the view and gets
     * information from the google maps directions api using an address
     * 
     * @param address is an address given by the user
     */
    public void byAddress(String address){
        // get entry
        Entry thisAccount = db.getEntry(address);
        if(thisAccount != null){
            // get coordinates of the entry
            String coordinates = thisAccount.getLatitude()+ ","+thisAccount.getLongitude();
            // get the notable location from the comboBox
            String notableLocation = (String) notableLocationsComboBox.getValue();
            // format the destination for google's api
            String destination = notableLocation.replace(" ", "+");
            destination = destination + "+Edmonton+Alberta";
        
            // try to connect to api
            try {
                URL url = 
                        new URL("https://maps.googleapis.com/maps/api/directions/"
                                        + "json?origin=" + coordinates + "&destination=" + 
                                        destination + APIKEY);  
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();
                // if connection was a success
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
                    // these nodes below are relevant from the api response
                    JsonNode duration = jsonNode2.get("duration");
                    JsonNode distance = jsonNode2.get("distance");

                    // set the values and ImageView to relevant information
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
        // if account doesnt exists from the address given by user alert the user
        else{
            inputErrorText.setVisible(true);
            timeTextField.setText("");
            distanceTextField.setText("");
            notableLocationTitle.setText("");
            notableLocationImageView.setImage(null);
        }   
    }
    /**
     * setImage sets the ImageView to the image corresponding to the notable location
     * 
     * @param notableLocation is the string representation of the name of the
     * notable location
     */
    public void setImage(String notableLocation){
        try{
            // format name so we can get the image
            String formattedName = notableLocation.replace(" ","_");
            formattedName.replace("'","");
            formattedName = formattedName + ".jpg";
            // get the image
            Image image = new Image(new FileInputStream("src/main/resources/images/"+formattedName));
            // set the image
            notableLocationImageView.setImage(image);
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
}
