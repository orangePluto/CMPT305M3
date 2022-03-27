package m3Modules;
import java.util.*;

/**
 *This module calculates various descriptive statistics from a dataset:
 *size of data set, min & max, range, mean, standard deviation, and median 
 *
 *@author Damion Dhillon Shillinglaw;ID#: 3069536
 */
public class Statistics{
    /**Computes the length of the dataset (list) 
     * 
     * @param values <-- dataset used to get size
     * @return size of values, otherwise -1 when empty
     */
    public static int n(List<Double> values){
        int len = 0;
        if(!(values.isEmpty())){
            for(int i=0;i<values.size();i++){
                len++;
            }
            return len;
        }else return -1;
    }
    /**Computes the minimum value of dataset 
     * 
     *@param values <-- dataset used to get min 
     *@return mn <-- smallest double value from dataset
    */
    public static double min(List<Double> values){
        double mn = values.get(0); //First value is assumed as min
        for(int i=1;i<values.size();i++){
            if(values.get(i)<mn) mn = values.get(i);
        }
        return mn;
    }
    /**Computes the maximum value of dataset
     * 
     * @param values <-- dataset (Link) used to get max value
     * @return mx <-- largest double value from dataset
     */
    public static double max(List<Double> values){
        double mx = values.get(0); //First value is assumed as max
        for(int i=1;i<values.size();i++){
            if(values.get(i)>mx) mx = values.get(i);
        }
        return mx;
    }
    /**Compute the range in dataset
     * 
     * @param values <-- dataset (List) used to get range
     * @return a value resulting from the difference between max and min 
     *         in values
     */
    public static double range(List<Double> values){
        double mn = min(values);
        double mx = max(values);
        return mx - mn;
    }
    /**Compute mean of dataset
     * 
     * @param values <-- dataset (List) used to get mean
     * @return mean of values (double)
     */
    public static double mean(List<Double> values){
        int len = values.size();
        double sum = 0.0;
        for(int i=0;i<len;i++){
            sum+=values.get(i);
        }
        return sum/(double) len;
    }
    /**Compute standard deviation of dataset
     * 
     * @param values <-- dataset used to get standard deviation
     * @return stdandard deviation of values where type is double
     */
    public static double stdev(List<Double> values){
        double sum = 0.0;
        double m = mean(values); 
        int len = values.size();
        double tempResult;

        for(int i=0;i<len;i++){
            tempResult = values.get(i) - m;

            //Handle absolute operation
            if(tempResult<0){ 
                tempResult = -1 * tempResult;
            }
            tempResult = tempResult * tempResult;
            sum += tempResult;
        }

        return Math.sqrt(sum/(double) len);
    }
    /**Computes the median of dataset
     * 
     * @param values <-- dataset (List) used to get median
     * @return median of values (double) 
     */
    public static double median(List<Double> values){
        int middle = values.size()/2;
        Collections.sort(values); //First sort list before get median
        if((values.size()%2) == 0){ //Check for even length
           return (values.get(middle)+values.get(middle-1))/2.0; 
        }else return values.get(middle); //odd length
    }
}