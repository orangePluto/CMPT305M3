package dataBase;

/**
 *
 * @author Brendan
 */
public class Neighbourhood {
    public NeighbourhoodData residential = new NeighbourhoodData("residential", 0, 0.0);
    public NeighbourhoodData otherResidential = new NeighbourhoodData("otherResidential", 0, 0.0);
    public NeighbourhoodData commercial = new NeighbourhoodData("commercial", 0, 0.0);
    public NeighbourhoodData farmland = new NeighbourhoodData("farmland", 0, 0.0);
    
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
