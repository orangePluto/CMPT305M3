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

public class houseViewController {

	private Property db;
	
	private final String streetBaseStr = "https://maps.googleapis."+ 
			"com/maps/api/streetview?";
	private final String streetSize = "size=555x502";
	private final String APIKEY = "&key=AIzaSyA1i5WBge"
			+ "XiI6xFq7E2lLl4ghZa1zj7M74";
	
	@FXML
    private TextField accountField;

    @FXML
    private TextField addressField;

    @FXML
    private Button displayButton;

    @FXML
    private Label houseLabel;

    @FXML
    private ImageView houseView;

    @FXML
    private Button mainButton;

    @FXML
    private TextArea propertyTextArea;

    @FXML
    private Button quitButton;

    @FXML
    private Button resetButton;
	
	@FXML
    void resetPress(ActionEvent event) {
		accountField.clear();
		addressField.clear();
		propertyTextArea.clear();
		houseView.setImage(null);
		houseLabel.setVisible(false);
    }
	
	
	@FXML
	void mainPress(ActionEvent event) throws IOException {
		App.setNewScene("mainPage", 760, 537);

	}
	
	public void initialize() {
		URL url = getClass().getResource("/data/Property_Assessment_Data.csv");
		db = CreateDatabase.createDB(url);
		
		if (db == null) {
			System.out.println("Failed to load database");
		}
		houseLabel.setVisible(false);
		propertyTextArea.setEditable(false);
		
	}

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

    @FXML
    void quitPress(ActionEvent event) {
		javafx.application.Platform.exit();
    }

	
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
