package m3Modules;

import java.util.*;
import dataBase.*;
import java.text.DecimalFormat;
/** 
 * Module helps create messages related to statistical values based on housing
 * properties.
 *
 * @author Damion Dhillon Shillinglaw  
 */
public class BuildMessage {
        /**
         *Builds a message (string) pertaining to statistical values associated
		 * with housing properties from data set.
         * 
         * @param dataset contains each property value
		 *@return statText is the statistical message built
         */
        public static String buildStatData(List<Double> dataset){
			DecimalFormat formatter = new DecimalFormat("#,###");
			StringBuilder statText = new StringBuilder("");
			
			statText.append("Statistics of Assessed Values:\n\n");
            statText.append("Number of Properties: ");
			statText.append(formatter.format(Statistics.n(dataset)));
			statText.append("\n");
            statText.append("Min: $" );
			statText.append(formatter.format(
					Math.round(Statistics.min(dataset))));
			statText.append("\n");
            statText.append("Max: $");
			statText.append(formatter.format(
					Math.round(Statistics.max(dataset))));
			statText.append("\n");
            statText.append("Range: $");
			statText.append(formatter.format(
					Statistics.range(dataset)));
			statText.append("\n");
            statText.append("Mean: $"); 
			statText.append(formatter.format(
					Math.round(Statistics.mean(dataset))));
			statText.append("\n");
			statText.append("Median: $");
			statText.append(formatter.format(
					Math.round(Statistics.median(dataset))));
			statText.append("\n");
            statText.append("Standard deviation: $");
			statText.append(formatter.format(
					Math.round(Statistics.stdev(dataset))));
			
			return statText.toString();
        }

        /**
         * Displays statistical values associated with account number (unique value)
         * 
         * @param e is an Entry object containing data associated with account number
         */
        public static void propertyOutput(Entry e) {
            if (e.isEntry()) { //check if e is a valid entry 
                System.out.println("Address = " + e.getAddress());
                System.out.println("Assessed value = " + Math.round(e.getAssessedV()));
                System.out.println("Assessment class = " + e.getAssessmentClass());
                System.out.println("Neighbourhood = " + e.getNeighbourhood()
                    + " (" + e.getWard() + ")");
                //System.out.println("Location = " + e.getLocation());
            } else { //Not an identifiable account id
                System.out.println("Invalid account ID: " + e.getAccountId());
            }
            System.out.println("\n\n");
        }

        /**
         *Method checks if user's input contains an integer > 0 
         * 
         * @param line is the line of user input (String)
         * @return true if line contains an integer; otherwise false
         */
        public static Boolean isNumeric(String line) {
            if (line == null) {
                return false;
            }
            try {
                Integer.parseInt(line);
            } catch (NumberFormatException e) {
                return false;
            }
            return true;
        }
    
}
