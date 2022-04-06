package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App: Edmonton Properties Assessment App.
 * Contains five features which are described in controllers directory.
 * User is presented with a main page which provides them with a menu button,
 * allowing user to access other features/pages.
 * 
 * @authors Damion Dhillon Shillinglaw, Brendan Gillespie, Nathan Koop,
 *          Jamie Czelenski
 */
public class App extends Application {

    private static Scene scene;
	private static Stage stage;

	/**
	 * Set scene and stage objects for app
	 * 
	 * @param stage is main stage
	 * @throws IOException raised if mainPage isn't found
	 */
    @Override
    public void start(Stage stage) throws IOException {
		App.stage = stage;
		stage.setTitle("Edmonton Properties Assessment App");
        scene = new Scene(loadFXML("mainPage"),760,537);
        stage.setScene(scene);
        stage.show();
    }
	
	/**
	 *Change scene for app
	 * @param fxml name of fxml file
	 * @throws IOException is raised if fxml file isn't found
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
	
	/**
	 * Utility method used to find fxml files (inside resources directory)
	 * 
	 * @param fxml name of fxml file
	 * @return loadFXML is Parent object used in scene
	 * @throws IOException is raised if fxml file isn't found 
	 */
    private  static Parent loadFXML(String fxml) throws IOException {
       FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
       return fxmlLoader.load();
    }
	
	/**
	 * Run application
	 * @param args isn't used
	 */
    public static void main(String[] args) {
        launch();
    }
}