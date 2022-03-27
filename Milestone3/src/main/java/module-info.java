module main {
    requires javafx.controls;
    requires javafx.fxml;

    opens main to javafx.fxml;
	opens controllers to javafx.fxml;
	opens dataBase to javafx.base;
    exports main;
	exports controllers;
	exports dataBase;
}
