package dataBase;

import java.text.*;
/**
 * Class contains an entry values from csv file for properties.
 *
 * @author Damion Dhillon Shillinglaw
 */
public class Entry {
    DecimalFormat formatter = new DecimalFormat("#,###");
    private int accountId;
    private String address;
    private double assessedValue;
    private String assessmentClass;
    private String ward;
    private String neighbourhood;
    private String latitude;
    private String longitude;

    /**
     * Set accountId attribute
     *
     * @param id used to set new id 
     */
    public void setId(int id) {
        this.accountId = id;
    }

    /**
     * Set address attribute
     *
     * @param addr used to set new address
     */
    public void setAddress(String addr) {
        this.address = addr;
    }

    /**
     * Set assessedValue attribute
     *
     * @param assessedV used to set new assessed value
     */
    public void setAssessedV(double assessedV) {
        this.assessedValue = assessedV;
    }

    /**
     * Set assessmentClass attribute
     *
     * @param assessmentV used to set new assessment
     */
    public void setAssessmentV(String assessmentV) {
        this.assessmentClass = assessmentV;
    }

    /**
     * Set ward attribute
     *
     * @param ward used to set new ward
     */
    public void setWard(String ward) {
        this.ward = ward;
    }

    /**
     * Set neighbourhood attribute
     *
     * @param neigh used to set new neighborhoud
     */
    public void setNeigh(String neigh) {
        this.neighbourhood = neigh;
    }

    /**
     * Set location, i.e. latitude and longitude attributes
     *
     * @param lat and longt used to set new longitude and latitude  
     */
    public void setLocation(String lat, String longt) {
        this.latitude = lat;
        this.longitude = longt;
    }

    /**
     * Get accountId's value (attribute)
     *
     * @return accountId of property
     */
    public int getAccountId() {
        return accountId;
    }

    /**
     * Get address value (attribute)
     *
     * @return address of property 
     */
    public String getAddress() {
        return address;
    }
	
	/**
	 * Get assessed value
	 * 
	 * @return assessedValue of property
	 */
    public double getAssessedV() {
        return assessedValue;
    }
    
    /**
     * Get assessedValue's value (attribute)
     *
     * @return clean format for assessedValue
     */
    public String getAssessedValue() {
        return "$"+ formatter.format(this.assessedValue);
    }

    /**
     * Get assessmentClass's value (attribute)
     *
     * @return assessmentClass of property
     */
    public String getAssessmentClass() {
        return assessmentClass;
    }

    /**
     * Get ward's value (attribute)
     *
     * @return ward of property
     */
    public String getWard() {
        return ward;
    }

    /**
     * Get neighbourhood's value (attribute)
     *
     * @return neighbourhood of property
     */
    public String getNeighbourhood() {
        return neighbourhood;
    }

    /**
     * Get latitude value (attribute)
     *
     * @return latitude of property 
     */
    public String getLatitude() {
        return latitude;
    }
    
    /**
     * Get longitude value (attribute)
     * @return longitude of property
     */
    public String getLongitude() {
        return longitude;
    }
}
