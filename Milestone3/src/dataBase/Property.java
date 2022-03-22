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
	public List<Entry> dynamicEntries = new ArrayList<>(); 
	
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
	 * get entry by address
	 * 
	 * @param address is searched for
	 * @return null if not found otherwise return entry
	 */
	public Entry getAddress(String address) {
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
     *Query by assessment class 
     * 
     * @param className is assessment class of interest (String)
     * @return arrClass contains house prices relevant to className
     */
    public ArrayList<Double> byAssessClass(String className, String neigh){
        ArrayList<Double> arrClass = new ArrayList<>();
        Entry tempEntry = new Entry();
		dynamicEntries = new ArrayList<>();

        for(int i=0;i < accIDs.size();i++){
            tempEntry = mainTable.get(accIDs.get(i));
            if (((className.compareTo(tempEntry.getAssessmentClass())) == 0) && 
					(neigh.compareTo(tempEntry.getNeighbourhood()) == 0)) {
                arrClass.add(tempEntry.getAssessedV());
				dynamicEntries.add(tempEntry);
            }
        }
        return arrClass;
    }

    /**
     *Query by account id 
     * 
     * @param accountId is a unique value in database (int)
     * @return use accountId to find Entry object in mainTable, if
     *         doesn't exist return a null Entry, i.e. e.SetExists
	 *         field becomes false
     */
    public Entry getAccount(int accountId) {
            Entry e = new Entry();
        if (mainTable.containsKey(accountId)) {
            e = mainTable.get(accountId);
        } else { 
            e.setExists(false);
            e.setId(accountId);
        }

        return e;
    }
    
    /**
     * Get all entries from database
     * @return 
     */
    public List<Entry> getAllEntries() {
		List<Entry> copy = new ArrayList<>(entries);
        return copy;
    }
	
	
	/**
	 * This method is used when building data base (i.e. a Property object)
	 * @param newEntry data entry added to database
	 */
	public void addEntry(Entry newEntry) {
		mainTable.put(newEntry.getAccountId(),newEntry);
		entries.add(newEntry);
		accIDs.add(newEntry.getAccountId());
	}
	
}