
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import java.io.*;


/**
 *This program is the main client of project
 * which makes a request to TableController to get table of housing
 * properties. Also, the controller provides interface for querying properties.
 * 
 * @author Damion Dhillon Shillinglaw
 */
public class MainFX extends Application {
    
	/**
	 * Starting point of application.
	 * 
	 * @param primaryStage main container for controller.
	 */
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Edmonton Property Assessments");
        Parent tableRoot;
        
        try {
            tableRoot = FXMLLoader.load(getClass().getResource("resources/table.fxml"));
        } catch (IOException e) {
            System.out.println("Failed to open fxml file.");
            return;
        }
        
        Scene table = new Scene(tableRoot);
        primaryStage.setScene(table);
        primaryStage.show();		
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
