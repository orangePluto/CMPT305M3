package controllers;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import main.App;
import java.io.InputStream;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import dataBase.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import m3Modules.BuildMessage;

/**
 * This class is the controller for the houseView.fxml.
 * In this feature/page the user is provided with a street view for a
 * property which is ascertained by address or account Id.
 * 
 * @author Damion Dhillon Shillinglaw
 */
public class houseViewController {
	private Property db;//Represents data base for Edmonton properties
	//These strings are used in http request to google map's street view API
	private final String streetBaseStr = "https://maps.googleapis."+ 
			"com/maps/api/streetview?"; 
	private final String streetSize = "size=555x502";
	private final String APIKEY = "&key=AIzaSyA1i5WBge"
			+ "XiI6xFq7E2lLl4ghZa1zj7M74";
	
	@FXML
    private TextField accountField; //Contains input for account id 

    @FXML
    private TextField addressField; //Contains input for address

    @FXML
    private Button displayButton;

    @FXML
    private Label houseLabel;

    @FXML
    private ImageView houseView; //Object in which static image is loaded

    @FXML
    private Button mainButton;

    @FXML
    private TextArea propertyTextArea; //Outputs two types of messages:
	                                   // 1) Error message: user entered erroneous input
	                                  // 2) Property data: user entered valid input

    @FXML
    private Button quitButton;

    @FXML
    private Button resetButton;
	
	/**
	 * When user presses resetButton then all fields and houseView are cleared.
	 * 
	 * @param event isn't used
	 */
	@FXML
    void resetPress(ActionEvent event) {
		accountField.clear();
		addressField.clear();
		propertyTextArea.clear();
		houseView.setImage(null);
		houseLabel.setVisible(false);
    }
	
	/**
	 * When user presses mainButton then switch to main page.
	 * 
	 * @param event isn't used
	 * @throws IOException thrown when fxml file (main.fxml) isn't found
	 */
	@FXML
	void mainPress(ActionEvent event) throws IOException {
		App.setNewScene("mainPage", 760, 537);
	}
	
	/**
	 * Method initializes data base (db) and sets attributes for
	 * GUI objects. In the event data base isn't loaded then an error message
	 * is sent to stdout.
	 * 
	 */
	public void initialize() {
		URL url = getClass().getResource("/data/Property_Assessment_Data.csv");
		db = CreateDatabase.createDB(url);
		
		if (db == null) {
			System.out.println("Failed to load database");
		}
		houseLabel.setVisible(false);
		propertyTextArea.setEditable(false);
		
	}

	/**
	 * When user presses displayButton then one of two changes to the
	 * page happens: 1) In the event user enters erroneous input then 
	 *                  only propertyTextArea will display an err message
	 *               2) Conversely, valid input provides user with a static image
	 *                  of a street view of property (which exist in db)
	 * @param event isn't used
	 */
	@FXML
	void displayPress(ActionEvent event) {
		Entry entry;
		
		if (!addressField.getText().isEmpty()) {
			entry = db.getEntry(addressField.getText());
			if (entry != null) {
				displayImage(entry);
				updatePropertyTextArea(entry,0); //first line is account id
			} else propertyTextArea.setText("Not a valid Property!");
		} else if (!accountField.getText().isEmpty()) {
			//Check if value is by account id
			if (accountField.getText().matches("[0-9]+")) { 	
				int accountId = Integer.parseInt(accountField.getText());
				entry = db.getEntry(accountId);
				if (entry != null) {
					displayImage(entry);
					updatePropertyTextArea(entry,1); //first line is address
				}
				else propertyTextArea.setText("Not a valid Property!");
			} else propertyTextArea.setText("Not a valid Property!");
		}
		else propertyTextArea.setText("Not a valid Property!");
	}
	
	/**
	 * When user presses quitButton then App is closed.
	 * 
	 * @param event isn't used
	 */
    @FXML
    void quitPress(ActionEvent event) {
		javafx.application.Platform.exit();
    }

	/**
	 * The main functionality of this controller comes from this method:
	 * if user input is valid then, first, a http request is made out to 
	 * google map's API street view; and, if successful then relevant GUI objects
	 * on the page are updated. Conversely, an unsuccessful request results in
	 * an error message sent out to stdout.
	 * 
	 * @param entry is the object representing a property in db
	 */
	private void displayImage(Entry entry) {
		//Build location parameter for http request
		StringBuilder latNlongStr = new StringBuilder("");
		latNlongStr.append("&location=");
		latNlongStr.append(entry.getLatitude());
		latNlongStr.append(",");
		latNlongStr.append(entry.getLongitude());

		try { //Attempt to connect to google's street api
			URL url = new URL(streetBaseStr + streetSize + 
					latNlongStr.toString() + APIKEY);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.connect();

			if (conn.getResponseCode() == 200) { //check response
				                                //set image & update text area
												//display address label
				InputStream imageStream = url.openStream();
				houseView.setImage(new Image(imageStream));
				houseLabel.setText(entry.getAddress());
				houseLabel.setVisible(true);
			}
			
		} catch (Exception e) {
			System.out.println("Failed to connect to street view static api");
		}
	}
	
	/**
	 * Utility method for displayMethod which, simply, upates propertyTextArea
	 * according to what user inputted
	 * 
	 * @param e is the object representing a property in db
	 * @param whom (int) identifies the message needed to update 
	 *        propertyTextArea where 0 pertains to a search done by address
	 *        and 1 by account id
	 */
	private void updatePropertyTextArea(Entry e, int whom) {
		String message = BuildMessage.buildPropertyMessage(e);
		if (whom == 0) { //upate based on address search
			            //therefore provide account id
			propertyTextArea.setText("Account Id: "+ e.getAccountId() + "\n"
					+ message);
		} else { //conversely, update by account id, thus, provide address
			propertyTextArea.setText("Address: " + e.getAddress() + "\n" +
					message);
		}
	}

}
