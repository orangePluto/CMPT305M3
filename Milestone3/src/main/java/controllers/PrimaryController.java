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
    private MenuItem placesItem;

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
	
	/**Go to Damion's feature**/
    @FXML
    void switchToPlaces(ActionEvent event) throws IOException {
		App.setRoot("/fxmls/damion");
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
		App.setNewScene("/fxmls/Charts", 1037, 640);
    }

}
