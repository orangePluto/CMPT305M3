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
 * @author Damion Dhillon Shillinglaw, Brendan Gillespie
 */
public class PrimaryController {
    @FXML
    private MenuItem jamieOption;
	
    @FXML
    private MenuItem nathansOption;
	
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
	 * @throws IOException thrown when fxml file (viewHouse.fxml) isn't found
	 * @author feature/page by Damion Dhillon Shillinglaw
	 */
    @FXML
    void switchToHouseView(ActionEvent event) throws IOException {
		App.setNewScene("/fxmls/houseView",895,582);
    }
	
	//Go to Nathan's feature
	@FXML
    void switchToNathans(ActionEvent event) throws IOException {
		App.setRoot("/fxmls/nathan");
    }
	
	//Go to Jamie's feature
	@FXML
    void switchToJamie(ActionEvent event) throws IOException {
		App.setRoot("/fxmls/jamie");
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
    void switchToBrendan(ActionEvent event) throws IOException {
		App.setNewScene("/fxmls/Charts", 1037, 640);
    }

}
