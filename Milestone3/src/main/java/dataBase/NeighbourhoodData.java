package dataBase;

/**
 *
 * @author Brendan
 */
public class NeighbourhoodData {
    
    private String aClass;
    private Integer count;
    private Double totalVal;
    
    public NeighbourhoodData(String aClass, Integer count, Double totalVal){
        this.aClass = aClass;
        this.count = count;
        this.totalVal = totalVal;
    }
    
    public void updateCount(){
        count ++;
    }
    
    public void updateTotalVal(Double val){
        totalVal += val;
    }
    
    public String getAClass(){
        return aClass;
    }
    
    public Integer getCount(){
        return count;
    }
    
    public Double getData(String filter){
        if("Average Value of Properties".equals(filter)){
            if(count == 0){return 0.0;}
            else{return (totalVal/count);}   
        }
        else if ("Number of Total Properties".equals(filter)){
            Double d = new Double(count);
            return d;
        } 
        else{ return null;}
    }
}
