package dataBase;

import java.text.*;
/**
 * Class contains an entry values from csv file for properties.
 *
 * @author Damion Dhillon Shillinglaw
 */
public class Entry {
    DecimalFormat formatter = new DecimalFormat("#,###");

    private Boolean exists;
    private int accountId;
    private String address;
    private double assessedValue;
    private String assessmentClass;
    private String ward;
    private String neighbourhood;
    private String latitude;
    private String longitude;

    /**
     * After finishing entering values for entry, set exists to true or false
     *
     * @param v is used to set exists attribute
     */
    public void setExists(Boolean v) {
        this.exists = v;
    }

    /**
     * Set accountId attribute
     *
     * @param id
     */
    public void setId(int id) {
        this.accountId = id;
    }

    /**
     * Set address attribute
     *
     * @param addr
     */
    public void setAddress(String addr) {
        this.address = addr;
    }

    /**
     * Set assessedValue attribute
     *
     * @param assessedV
     */
    public void setAssessedV(double assessedV) {
        this.assessedValue = assessedV;
    }

    /**
     * Set assessmentClass attribute
     *
     * @param assessmentV
     */
    public void setAssessmentV(String assessmentV) {
        this.assessmentClass = assessmentV;
    }

    /**
     * Set ward attribute
     *
     * @param ward
     */
    public void setWard(String ward) {
        this.ward = ward;
    }

    /**
     * Set neighbourhood attribute
     *
     * @param neigh
     */
    public void setNeigh(String neigh) {
        this.neighbourhood = neigh;
    }

    /**
     * Set location, i.e. latitude and longitude attributes
     *
     * @param lat and longt
     */
    public void setLocation(String lat, String longt) {
        this.latitude = lat;
        this.longitude = longt;
    }

    /**
     * Get exists value
     *
     * @return
     */
    public Boolean isEntry() {
        return exists;
    }

    /**
     * Get accountId's value (attribute)
     *
     * @return
     */
    public int getAccountId() {
        return accountId;
    }

    /**
     * Get address value (attribute)
     *
     * @return
     */
    public String getAddress() {
        return address;
    }

    public double getAssessedV() {
        return assessedValue;
    }
    
    /**
     * Get assessedValue's value (attribute)
     *
     * @return
     */
    public String getAssessedValue() {
        return "$"+ formatter.format(this.assessedValue);
    }

    /**
     * Get assessmentClass's value (attribute)
     *
     * @return
     */
    public String getAssessmentClass() {
        return assessmentClass;
    }

    /**
     * Get ward's value (attribute)
     *
     * @return
     */
    public String getWard() {
        return ward;
    }

    /**
     * Get neighbourhood's value (attribute)
     *
     * @return
     */
    public String getNeighbourhood() {
        return neighbourhood;
    }

    /**
     * Get latitude value (attribute)
     *
     * @return
     */
    public String getLatitude() {
        return latitude;
    }
    
    /**
     * Get longitude value (attribute)
     * @return 
     */
    public String getLongitude() {
        return longitude;
    }
}
