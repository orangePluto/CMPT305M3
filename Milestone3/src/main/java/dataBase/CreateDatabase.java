package dataBase;

import java.util.*;
import java.nio.file.Paths;
import java.io.IOException;
import java.net.*;
import javafx.scene.shape.Path;

/**
 *Module for creating database for properties. 
 *
 *@author Damion Dhillon Shillinglaw 
 */
public class CreateDatabase {
    /**
     * Initialize database if possible.
     * @param infile is the file name use to create database
     * @return db is the Property object for representing database; otherwise,
     *         return null if invalid infile
     */
    public static Property createDB(URL infile) {
		
        //String infile = "Property_Assessment_Data.csv";

        //check if file can be opened & build database
        try{ 
            Property db = new Property();
            Scanner dataSetFile = new Scanner(Paths.get(infile.toURI()));
            dataSetFile.nextLine();
            Entry tempE; 
            while(dataSetFile.hasNextLine()){
                tempE = processEntry(dataSetFile.nextLine());
                db.addEntry(tempE);
            }
            dataSetFile.close();
            return db;

        }catch (IOException e){
            System.out.println("Could not open file: "+infile);
            return null;
        }catch (URISyntaxException e) {
            System.out.println(e.toString());
            return null;
        }
    }

/**
 *Grab column values and pack them into an Entry object. 
 * 
 * @param line is the line being parsed for values
 * @return e is the Entry object storing column values.
 */
    private static Entry processEntry(String line){
        Entry e = new Entry();
        Scanner parser = new Scanner(line);
        parser.useDelimiter(",");

        if(parser.hasNext()){ //get & set account id
            e.setId(Integer.parseInt(parser.next()));
        }

        if(parser.hasNext()){ //get & set address
            e.setAddress((parser.next()+" "+parser.next() +" "+ parser.next())
			.trim());
        }

        parser.next(); parser.next();
        if(parser.hasNext()){ //get & set neighbourhood
            e.setNeigh(parser.next());
        }

        if(parser.hasNext()){ //get & set ward
            e.setWard(parser.next());
        }

        if(parser.hasNext()){ //get & set assessedValue
            e.setAssessedV(Double.parseDouble(parser.next()));
        }

        if(parser.hasNext()){ //get & set location
            e.setLocation(parser.next(), parser.next());
        }

        for(int i=0;i<4;i++) parser.next();
        if(parser.hasNext()){ //get & set assessed class
            e.setAssessmentV(parser.next());
        }
        
        parser.close();
        return e;
    }
    
}
