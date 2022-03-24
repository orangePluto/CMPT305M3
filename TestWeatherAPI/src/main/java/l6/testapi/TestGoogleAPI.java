package l6.testapi;

import java.io.FileWriter;
import java.net.HttpURLConnection;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.net.URL;
import java.util.*;
import org.json.simple.JSONArray;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * This program utilizes the google map's directions API, which requires an
 * authentication key (placed at the end of url). Note: the outputted json file
 * is in tree format
 * 
 * 
 */

/**
 *
 * @author dantep
 */
public class TestGoogleAPI {

	public static void main(String args[]) {

		try {
			URL url = new URL("https://maps.googleapis.com/maps/api/directions/json?origin=Toronto&destination=Montreal&key=");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.connect();

			//Check if connect is made
			int responseCode = conn.getResponseCode();
			if (responseCode == 200) {
				StringBuilder directionMess = new StringBuilder();
				Scanner scanner = new Scanner(url.openStream());

				while (scanner.hasNext()) {
					directionMess.append(scanner.nextLine());
				}
				//Close the scanner
				scanner.close();

				//Lots of parsing of JSON objects to get duration of trip
				JSONParser parse = new JSONParser();
				JSONObject dataObject = (JSONObject) parse.parse(directionMess.toString());
				JSONArray jarr = (JSONArray) dataObject.get("routes");
				JSONObject dataObject2 = (JSONObject) jarr.get(0);
				JSONArray jarr2 = (JSONArray) dataObject2.get("legs");
				JSONObject dataObject3 = (JSONObject) jarr2.get(0);
				JSONObject duration = (JSONObject) dataObject3.get("duration");

				//Write duration object to a .json file
				FileWriter file = new FileWriter("src/main/java/resources/directions.json");
				ObjectMapper mapper = new ObjectMapper();
				String jsonStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(duration);
				file.write(jsonStr);
				file.flush();
				
				System.out.println(dataObject); //Top level JSON object printed
				System.out.println(duration);
				System.out.println("The drive from Toronto to Montreal is " +
						duration.get("text"));
			}
		} catch (Exception e) {
			System.out.println("Failed to connect, or some other error");
		}
	}
}
