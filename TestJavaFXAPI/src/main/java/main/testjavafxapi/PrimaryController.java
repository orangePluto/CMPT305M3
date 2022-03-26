package main.testjavafxapi;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.util.Scanner;

public class PrimaryController {
	private final String APIKEY = "AIzaSyA1i5WBgeXiI6xFq7E2lLl4ghZa1zj7M74";
	
	@FXML
    private TextField destinField;

    @FXML
    private Button distButton;

    @FXML
    private TextArea distTextArea;

    @FXML
    private Button resetButton;

    @FXML
    private TextField sourceField;
	
	/**
	 * After user presses distance button, if they provided two valid cities
	 * then the duration of the trip is displayed in distTextArea
	 * @param event 
	 */
    @FXML
    void distPress(ActionEvent event) {
		if (!destinField.getText().isEmpty() && 
				!sourceField.getText().isEmpty()) {
			String origin = sourceField.getText();
			String destination = destinField.getText();
			try {
				URL url = 
					new URL("https://maps.googleapis.com/maps/api/directions/"
							+ "json?origin=" + origin + "&destination=" + 
							destination + "&key=" + APIKEY);  
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				conn.connect();

				if (conn.getResponseCode() == 200) {
						StringBuilder directionMess = new StringBuilder();
						Scanner scanner = new Scanner(url.openStream());

						while (scanner.hasNext()) {
							directionMess.append(scanner.nextLine());
						}
						//Close the scanner
						scanner.close();
						
						//Parse json string, sent from google's direction api
						ObjectMapper mapper = new ObjectMapper();
						JsonNode rootJsonNode = mapper.readTree(directionMess.toString());
						JsonNode jarr = rootJsonNode.get("routes");
						JsonNode jsonNode1 = jarr.get(0);
						JsonNode jarr2 = jsonNode1.get("legs");
						JsonNode jsonNode2 = jarr2.get(0);
						JsonNode duration = jsonNode2.get("duration");
						
						//Write json tree to duration.json
						ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
						File outFile = new File("src/main/java/duration.json");
						writer.writeValue(outFile,duration);
						
						System.out.println(rootJsonNode);
						System.out.println(duration);
						distTextArea.setText(duration.get("text").toString());

					} else distTextArea.setText("Failed connection!");
			} catch (Exception e) {
					System.out.println("Failed to connect, or some other error");
			}
		} 
	}

    @FXML
    void resetPress(ActionEvent event) {
		destinField.clear();
		distTextArea.clear();
		sourceField.clear();
    }
	
	public void initialize() {
		distTextArea.setEditable(false);
	}

}
