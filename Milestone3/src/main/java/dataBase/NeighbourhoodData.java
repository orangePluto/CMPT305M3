package dataBase;

/**
 * This is a data class to hold information for all properties 
 * of a specific assessment class within a neighbourhood
 * 
 * @author Brendan Gillespie
 */
public class NeighbourhoodData {
    
    /**
     *  aClass - String: which represents the assessment class of the data
     *  count - Integer: This is the total number of properties within a neighbourhood with assessment class aClass
     *  totalVal - Double: This is the total value of properties within a neighbourhood with assessment class aClass
     */
    private String aClass;
    private Integer count;
    private Double totalVal;
    
    /** Main Constructor
     * 
     * @param aClass - This is the assessment class of the object
     * 
     *  count is defaulted to 0
     *  totalVal is defaulted to 0.0
     */
    public NeighbourhoodData(String aClass){
        this.aClass = aClass;
        this.count = 0;
        this.totalVal = 0.0;
    }
    
    /**
     * This method is update to increment the count by 1
     */
    public void updateCount(){
        count ++;
    }
    
    /**
     * This method is used to add another property value onto the running totalVal
     * 
     * @param val - This is the value of a property assessment
     */
    public void updateTotalVal(Double val){
        totalVal += val;
    }
    
    /**
     * This method is used to get the assessment class 
     * 
     * @return - aClass value
     */
    public String getAClass(){
        return aClass;
    }
    
    /**
     * This method is used to get the count 
     * 
     * @return - count value
     */
    public Integer getCount(){
        return count;
    }
    
    /**
     * This method is used to get the neighbourhood data from the class.
     * This method has a filter so the user can retrieve either the average property value or count
     * 
     * @param filter - This is a String message that is used to filter the type of data you want.
     * @return - (Double)count or totalVal/count = average or null if not valid filter
     */
    public Double getData(String filter){
        if("Average Value of Properties".equals(filter)){
            if(count == 0){return 0.0;}
            else{return (totalVal/count);}   
        }
        else if ("Total Number of Properties".equals(filter)){
            Double d = new Double(count);
            return d;
        } 
        else{ return null;}
    }
}
