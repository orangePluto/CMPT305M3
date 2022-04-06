package dataBase;

import java.util.*;

/*Class contains Entry objects from csv file (table of house properties).
 *Also, contains methods for returning list of property values, depending 
 *on the query.
 *  
 *@author Damion Dhillon Shillinglaw
*/
public class Property{
    private Hashtable<Integer, Entry> mainTable = new Hashtable<>(); //Contains Entry objects 
                                                             //and keys(account ids)
    private ArrayList<Integer> accIDs = new ArrayList<>(); //All account ids;keys
    private List<Entry> entries = new ArrayList<>(); //All Entry objects in data base
	public List<Entry> dynamicEntries = new ArrayList<>(); //Filled with most
	                                                      //properties from most
	                                                     //recent query
	
    /**
     *Query done by neighbourhood 
     * 
     * @param nHood is the neighbourhod of interest (String)
     * @return nArr is an ArrayList containing Doubles (prices) associated 
     *         to nHood
     */
    public ArrayList<Double> byNeighbourhood(String nHood){
        ArrayList<Double> nArr = new ArrayList<>(); 
        Entry tempEntry  = new Entry();
		dynamicEntries = new ArrayList<>();

        for (int i=0;i < accIDs.size();i++){ //Build nArr with relevant prices
            tempEntry = mainTable.get(accIDs.get(i));
            if (nHood.compareTo(tempEntry.getNeighbourhood()) == 0) {
                nArr.add(tempEntry.getAssessedV());
				dynamicEntries.add(tempEntry);
            }
        }
        return nArr;
    }

    /**
     *Query by all properties 
     *  
     * @return allArr is an ArrayList containing all houses prices
     */
    public ArrayList<Double> getAllPrices(){
        ArrayList<Double> allArr = new ArrayList<>();
        Entry tempEntry = new Entry();
		

        for(int i=0;i < accIDs.size();i++){
            tempEntry = mainTable.get(accIDs.get(i));
            allArr.add(tempEntry.getAssessedV());
        }
        return allArr;
    }

    /**
     *Query by only assessment class or by assessment class and neighborhoud 
     * Pre: pass neigh as null for assessment query  
     * @param className is assessment class of interest (String)
	 * @param neigh neigh is neighbourhood of interest
     * @return arrClass contains house prices relevant to className
     */
    public ArrayList<Double> byAssessClass(String className, String neigh){
        ArrayList<Double> arrClass = new ArrayList<>();
        Entry tempEntry = new Entry();
		Boolean isClass; //true means only by assessment class otherwise by
		                 //neighborhood and assessment
		dynamicEntries = new ArrayList<>();

		
		isClass = neigh == null;
		
        for(int i=0;i < accIDs.size();i++){
            tempEntry = mainTable.get(accIDs.get(i));
			if (!isClass) {
				if (((className.compareTo(tempEntry.getAssessmentClass())) == 0) && 
					(neigh.compareTo(tempEntry.getNeighbourhood()) == 0)) {
					arrClass.add(tempEntry.getAssessedV());
					dynamicEntries.add(tempEntry);
				} 
			} else { //Query by assessment class only
				if (className.compareTo(tempEntry.getAssessmentClass()) 
					== 0) {
					arrClass.add(tempEntry.getAssessedV());
					dynamicEntries.add(tempEntry);
				}
			}
        }
        return arrClass;
    }
    
	/**
	 * Used when initializing data base and, after, adding entries
	 * @param newEntry data entry added to database
	 */
	public void addEntry(Entry newEntry) {
		mainTable.put(newEntry.getAccountId(),newEntry);
		entries.add(newEntry);
		accIDs.add(newEntry.getAccountId());
	}
	
	/**
	 * Get entry by account id
	 * 
	 * @param accId an int value used to find entry in data base
	 * @return e is a found entry otherwise null
	 */
	public Entry getEntry(int accId) {
		Entry e = new Entry();
		
		if (mainTable.containsKey(accId)) {
			e = mainTable.get(accId);
		} else e = null;
		
		return e;
	}
	
	/**
	 * Get entry by address
	 * 
	 * @param address is a string used to find entry in data base
	 * @return e is a found entry otherwise null
	 */
	public Entry getEntry(String address) {
		Entry tempEntry = new Entry();
		
		for (int i=0;i < accIDs.size(); i++) {
			tempEntry = mainTable.get(accIDs.get(i));
			if ((address.compareTo(tempEntry.getAddress())) == 0) {
				return tempEntry;
			}
		}
		
		return null;
	}
	
	 /**
     * Get all entries from database
     * @return 
     */
    public List<Entry> getAllEntries() {
		List<Entry> copy = new ArrayList<>(entries);
        return copy;
    }
	
}