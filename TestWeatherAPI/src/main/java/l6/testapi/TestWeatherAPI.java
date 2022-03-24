/*This program demonstrates how to use a simple public API (public means you
don't have to create an account or go thru other authentication steps to use the
API). Note: the outputted json file is in a non-tree format.
*/

package l6.testapi;
		
import java.io.FileWriter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
/**
 *
 * @author dantep
 */
public class TestWeatherAPI {

public static void main(String[] args) {
	try {
            //Public API:
            //https://www.metaweather.com/api/location/search/?query=<CITY>
            //https://www.metaweather.com/api/location/44418/

        URL url = new URL("https://www.metaweather.com/api/location/search/?query=Edmonton");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            //Check if connect is made
            int responseCode = conn.getResponseCode();

            // 200 OK
            if (responseCode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            } else {

                StringBuilder informationString = new StringBuilder();
                Scanner scanner = new Scanner(url.openStream());

                while (scanner.hasNext()) {
                    informationString.append(scanner.nextLine());
                }
                //Close the scanner
                scanner.close();

                System.out.println(informationString);


                //JSON simple library Setup with Maven is used to convert strings to JSON
                JSONParser parse = new JSONParser();
                JSONArray dataObject = (JSONArray) parse.parse(informationString.toString());
				FileWriter file = new FileWriter("src/main/java/resources/weather.json");
				file.write(dataObject.toJSONString());
				file.flush();

                //Get the first JSON object in the JSON array
                System.out.println(dataObject.get(0));

                JSONObject countryData = (JSONObject) dataObject.get(0);

                //System.out.println(countryData.get(""));
				System.out.println(countryData.get("title"));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

