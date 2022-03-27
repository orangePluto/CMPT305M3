package controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import main.App;

public class DamionController {

    @FXML
    private Button mainButton;

    @FXML
    void mainPress(ActionEvent event) throws IOException {
		App.setRoot("mainPage");
    }

}
