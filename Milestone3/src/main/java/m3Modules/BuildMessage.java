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
         * Builds a message from Entry's fields for a property
         * Pre: e MUST be a valid Entry
         * @param e is an Entry object containing data associated with account number
		 * @return message.toString() is the message built
         */
        public static String buildPropertyMessage(Entry e) {
			DecimalFormat formatter = new DecimalFormat("#,###");
			StringBuilder message = new StringBuilder();
			
			message.append("Assessed value: $");
			message.append(formatter.format(e.getAssessedV()));
			message.append("\n");
			message.append("Assessment class: ");
			message.append(e.getAssessmentClass());
			message.append("\n");
			message.append("Neighbourhood: "); 
			message.append(e.getNeighbourhood());
			message.append("\n");
			message.append("Geometry: (");
			message.append(e.getLatitude());
			message.append(",");
			message.append(e.getLongitude());
			message.append(")\n");
			
			return message.toString();
				
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
