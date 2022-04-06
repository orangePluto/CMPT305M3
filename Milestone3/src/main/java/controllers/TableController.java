package controllers;

import javafx.fxml.FXML;
import javafx.collections.*;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.collections.ObservableList;
import dataBase.*;
import java.io.IOException;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.*;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import java.util.*;
import m3Modules.BuildMessage;
import main.App;

/**
 * This controller handles logic and display for tableView object
 * and interface for performing queries on tableView (which contains
 * data base).
 * 
 * "table.fxml" uses this class as it's controller.
 */
/*
 * All fields/attributes of controller are translated from 
 *fxml file called "table.fxml"
 * 
 * @author Damion Dhillon Shillinglaw
 */
public class TableController {
	
	private Property db; //data base object
	ObservableList<Entry> tableList; //Used for tableview
	
	@FXML
    private Button mainButton;
	
	@FXML
    private Button quitButton;
	
	@FXML 
	Button resetButton;
	
	@FXML
    private TextField addyField;
	@FXML
    private TextField accountField;
	@FXML
    private TextField neighField;
	@FXML
    private ComboBox<String> dropBox;
	
	@FXML
    private TextArea statTextArea;

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
	
	/***
	 * When mainButton is clicked then go back to main page
	 * 
	 * @param event isn't used
	 * @throws IOException if mainPage isn't found
	 */
	@FXML
    void mainPress(ActionEvent event) throws IOException {
		App.setNewScene("mainPage", 760, 537);
    }
	
	/**
	 * When quitButton is clicked then close application
	 * 
	 * @param event isn't used
	 */
	@FXML
    void quitPress(ActionEvent event) {
		javafx.application.Platform.exit();
    }
	
	/**
	 * Method handles search button press which deals 
	 * with a users query.
	 * 
	 * @param event interrupt by user (i.e. pressing search button) 
	 */
	@FXML
    void searchPress(ActionEvent event) {
		
		if (!(accountField.getText().isEmpty())) { //Query by account id
			filterByAccId(accountField.getText());
		} else if (!(addyField.getText().isEmpty())) { //Query by address
			filterByAddress(addyField.getText());
		} else if (!(neighField.getText().isEmpty())) { //Query by neigh 
			if (dropBox.getValue() != null)	// potentially with assessment class
				filterByNeighAssem(neighField.getText(),dropBox.getValue());
			else filterByNeighAssem(neighField.getText(),null);
		} else if (dropBox.getValue() != null) { //Query by assessment class
			filterByNeighAssem(null,dropBox.getValue());
		} else System.out.println("Dont search anything");
	
    }
	
	/***
	 * Method handles reset button press which clears all fields
	 * and returns to default view.
	 * @param event interrupt by user (i.e. pressing reset button)
	 */
	@FXML
    void resetPress(ActionEvent event) {
		dropBox.setValue(null);
		accountField.clear();
		addyField.clear();
		neighField.clear();
		statTextArea.clear();
		updateTable(db.getAllEntries());
		displayStatArea(db.getAllPrices());
    }

    /**
	 * Initialize drop box items, data base, and display original/start
	 * view
	 * 
	 */
    public void initialize() {
        URL url = getClass().getResource("/data/Property_Assessment_Data.csv");
		
		dropBox.getItems().removeAll(dropBox.getItems());
		dropBox.getItems().addAll("NON RESIDENTIAL", "RESIDENTIAL", 
				"COMMERCIAL","OTHER RESIDENTIAL");
		
		
        db = CreateDatabase.createDB(url);
		updateTable(db.getAllEntries());
		
		statTextArea.setEditable(false);
		displayStatArea(db.getAllPrices());
        
    }
	
	/**
	 * Update tableview object with data base entries
	 * 
	 * @param entries is an Entry object containing data for each property
	 */
	private void updateTable(List<Entry> entries) {
		if (entries != null) {
		this.tableList = FXCollections.observableArrayList(entries);
        account.setCellValueFactory(new PropertyValueFactory<>("accountId"));
        address.setCellValueFactory(new PropertyValueFactory<>("address"));
        assessed.setCellValueFactory(new PropertyValueFactory<>("assessedValue"));
        assessment.setCellValueFactory(new PropertyValueFactory<>("assessmentClass"));
        latitude.setCellValueFactory(new PropertyValueFactory<>("latitude"));
        longitude.setCellValueFactory(new PropertyValueFactory<>("longitude"));
        neigh.setCellValueFactory(new PropertyValueFactory<>("neighbourhood"));
        
        this.table.setItems(tableList);
		} else this.table.getItems().clear();
	}
	
	/**
	 * Handle query by account id: update table view and text area 
	 * if id is valid
	 * 
	 * @param id is housing id
	 */
	private void filterByAccId(String id) {
		int accID;
		Entry entry;
	
		try { //process id and search for it in data base
			accID = Integer.parseInt(id);
			entry = db.getEntry(accID);
			
			if (entry != null) {
				List<Entry> singleton = new ArrayList<>();
				singleton.add(entry);
				updateTable(singleton);
			} else updateTable(null);				
		} catch (NumberFormatException e) {
			System.out.println("Must enter numbers for Id.");
			updateTable(null);
		}
		
		statTextArea.clear();	
	}
	
	/**
	 * Handle query by address: update table view and text area
	 * if address is valid
	 * 
	 * @param address is a string containing #suite number, street/ave & direction 
	 */
	private void filterByAddress(String address) {
		Entry e = db.getEntry(address);
		
		if (e != null) {
			List<Entry> singleton = new ArrayList<>();
			singleton.add(e);
			updateTable(singleton);
		} else { //Invalid address
			updateTable(null);
		}
		
		statTextArea.clear();
	}
	
	/**
	 * Handle query by neighbourhood, assessment class, or both:
	 * if both are valid then update table view and text area, otherwise
	 * display nothing.
	 * Pre: pass parameters as null for ignoring for search
	 * @param neigh is a string containing #suite number, street/ave & direction
	 * @param assem is a string for assessment class
	 */
	private void filterByNeighAssem(String neigh, String assem) {
		ArrayList<Double> arrPrices = new ArrayList<>();
		
		if (assem != null && neigh != null) { //Query by neighbourhood 
			                                 //and assessment class
			arrPrices = db.byAssessClass(assem, neigh);
			if (!arrPrices.isEmpty()) {
				displayStatArea(arrPrices);
				updateTable(db.dynamicEntries);
			} else {
				statTextArea.clear();
				updateTable(null);
			}
		} else if (neigh == null && assem != null) {  //Query by assessment class
			arrPrices = db.byAssessClass(assem, neigh);
			if (!arrPrices.isEmpty()) {
				displayStatArea(arrPrices);
				updateTable(db.dynamicEntries);
			} else {
				statTextArea.clear();
				updateTable(null);
			}
		}else { //Only query by neighbourhood
			arrPrices = db.byNeighbourhood(neigh);
			if (!arrPrices.isEmpty()) {
				displayStatArea(arrPrices);
				updateTable(db.dynamicEntries);
			} else {
				statTextArea.clear();
				updateTable(null);
			}
		}
	}
	
	/**
	 * Update statTextArea from valid query otherwise clear it.
	 * This method is called by the filter methods
	 * @param housePrices a filtered array depending on the query
	 */
	private void displayStatArea(ArrayList<Double> housePrices) {
		String statMessage = BuildMessage.buildStatData(housePrices);
		statTextArea.setText(statMessage);
	}
	
}