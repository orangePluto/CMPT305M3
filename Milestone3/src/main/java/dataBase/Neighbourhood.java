package dataBase;

/**
 * This class uses 4 instances of the NeighbourhoodData class to store the count and value
 * data for an entire neighbourhood
 * 
 * @author Brendan Gillespie
 */
public class Neighbourhood {
    
    /**
     * 4 NeighbourhoodData classes are created with assessment class value of
     * ('Residential', 'Other Residential', 'Commercial', 'Farmland')
     */
    public NeighbourhoodData residential = new NeighbourhoodData("Residential");
    public NeighbourhoodData otherResidential = new NeighbourhoodData("Other Residential");
    public NeighbourhoodData commercial = new NeighbourhoodData("Commercial");
    public NeighbourhoodData farmland = new NeighbourhoodData("Farmland");
    
    /**
     * This method is used to add the data from a property Entry into the neighbourhood.
     * 
     * @param entry - This is an Entry object that contains the data from a property assessment csv
     */
    public void updateData(Entry entry){
        String aClass = entry.getAssessmentClass();
        if(null != aClass) switch (aClass) {
            case "RESIDENTIAL":
                residential.updateCount();
                residential.updateTotalVal(entry.getAssessedV());
                break;
            case "OTHER RESIDENTIAL":
                otherResidential.updateCount();
                otherResidential.updateTotalVal(entry.getAssessedV());
                break;
            case "COMMERCIAL":
                commercial.updateCount();
                commercial.updateTotalVal(entry.getAssessedV());
                break;
            case "FARMLAND":
                farmland.updateCount();
                farmland.updateTotalVal(entry.getAssessedV());
                break;
            default:
                break;
        }
    }
}
