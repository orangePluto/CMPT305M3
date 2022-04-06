package controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import main.App;

/**
 * This is the controller for the main menu of our Property Assessment App.
 * This controller facilitates the change from one feature/page to another feature/page
 * within the app. It creates a dropdown that lets the user move between each feature
 * by selecting the feature in the dropdown menu. 
 * 
 * @authors Damion Dhillon Shillinglaw, Brendan Gillespie, Nathan Koop,
 *         Jamie Czelenski
 */
public class PrimaryController {	
    @FXML
    private MenuItem quitButton;
	
    @FXML
    private MenuItem tableOption;
        
    /**
     * This is the method called when the 'quit' button is pressed. 
     * This method terminates the app.
     * 
     * @param event - button clicked
     */
    @FXML
    void quitPress(ActionEvent event) {
		javafx.application.Platform.exit();
    }
	
	/**
	 *Go table view page: user performs queries on data base of housing
	 * properties.
	 * 
	 *@param event isn't used
	 *@throws IOException thrown when fxml file (tableView.fxml) isn't found
	 *@author feature/page by Damion Dhillon Shillinglaw
	 */
    @FXML
    void switchToTable(ActionEvent event) throws IOException {
		App.setNewScene("/fxmls/table", 1227, 609);
    }
	
	/**
	 * This page, called House View, provides the user with a 
	 * street view of the property.
	 * 
	 * @param event isn't used
	 * @throws IOException thrown when fxml file (houseView.fxml) isn't found
	 * @author feature/page by Damion Dhillon Shillinglaw
	 */
    @FXML
    void switchToHouseView(ActionEvent event) throws IOException {
		App.setNewScene("/fxmls/houseView",895,582);
    }
	
	/*
	 *Switch to LocationsController: allows user to check what properties are
	  found in a specified location and filtered by a price range.
	 *
	 *@param event isn't used
	 *@throws IOException thrown when fxml file (locations.fxml) isn't found
	 *@author feature/page by Nathan Koop
	*/
	@FXML
    void switchToLocations(ActionEvent event) throws IOException {
		App.setRoot("/fxmls/location");
    }
	
	/**
         * Switches to the notable location view, so the user can use this feature
         * 
         * @param event click event
         * @throws IOException thrown when fxml file (notable.fxml) isn't found
         * @author feature/page Jamie Czelenski
         */
	@FXML
    void switchToNotable(ActionEvent event) throws IOException {
		App.setNewScene("/fxmls/notable", 1031,768);
    }
	
	/**
         * This method switches the app to the Charts page. The Charts page, allows
         * the user to look at and compare two neighbourhood's and their data using 
         * charts such as a Pie Chart and Bar Chart,
         * 
         * @param event isn't used
         * @throws IOException thrown when fxml file (Charts.fxml) isn't found
         * @author feature/page by Brendan Gillespie
         */
	@FXML
    void switchToCharts(ActionEvent event) throws IOException {
		App.setNewScene("/fxmls/Charts", 1037, 640);
    }

}
