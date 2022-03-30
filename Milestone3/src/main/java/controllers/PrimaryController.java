package controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import main.App;


public class PrimaryController {
	@FXML
    private MenuItem jamieOption;
	
	@FXML
    private MenuItem nathansOption;
	
    @FXML
    private MenuItem quitButton;
	
	@FXML
    private MenuItem tableOption;

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
	
	//Go to Brendan's feature
	@FXML
    void switchToBrendan(ActionEvent event) throws IOException {
		App.setRoot("/fxmls/brendan");
    }

}
