package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
	private static Stage stage;

    @Override
    public void start(Stage stage) throws IOException {
		App.stage = stage;
		stage.setTitle("Edmonton Properties Assessment App");
        scene = new Scene(loadFXML("mainPage"),760,537);
        stage.setScene(scene);
        stage.show();
    }
	
	/**
	 *
	 * @param fxml
	 * @throws IOException
	 */
	public static void setRoot(String fxml) throws IOException {
		scene.setRoot(loadFXML(fxml));
    }
	
	/**
	 * Change the scene for application
	 * @param fxml is the fxml file to load
	 * @param width of scene
	 * @param height of scene
	 * @throws IOException if failed to load/find fxml file
	 */
	public static void setNewScene(String fxml,double width, double height) 
			throws IOException {
		scene = new Scene(loadFXML(fxml),width,height);
		stage.setScene(scene);
		stage.show();
	}
	
    private  static Parent loadFXML(String fxml) throws IOException {
       FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
       return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}