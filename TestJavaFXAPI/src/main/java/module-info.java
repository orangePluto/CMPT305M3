module main.testjavafxapi {
    requires javafx.controls;
    requires javafx.fxml;
	requires com.fasterxml.jackson.databind;
	requires com.fasterxml.jackson.core;

    opens main.testjavafxapi to javafx.fxml;
    exports main.testjavafxapi;
}
