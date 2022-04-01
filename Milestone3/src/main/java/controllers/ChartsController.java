package controllers;

import dataBase.CreateDatabase;
import dataBase.Entry;
import dataBase.Neighbourhood;
import dataBase.Property;
import java.io.IOException;
import java.util.HashMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import main.App;

/**
 * This is a controller class which is part of the MC for the Charts view of the Property Assessment App.
 * This controller sets up the logic for all of the javafx objects on the charts page. 
 * It also contains multiple helper methods to to simplify the implementation of the javaFx objects.
 * Lastly, this class creates a map of the neighbourhood data for all neighbourhoods within the Property Assessment CSV for faster access of data as the app runs
 * 
 * @author Brendan Gillespie
 */
public class ChartsController {
    
    @FXML  //Error Text
    private Text typeError;
    
    @FXML
    private Text valueError;
    
    @FXML
    private Text neigh1Error;
    
    @FXML
    private Text neigh2Error;
    
    @FXML
    private Text cFilterError;
    
    @FXML //Assessment Class ComboBox Text
    private Text classFilterText;
    
    @FXML  //Buttons
    private Button mainButton;
    private Button quitButton;
    private Button graphButton;
    private Button clearButton;
    
    @FXML  //PieCharts
    private PieChart pieChart1;
    
    @FXML
    private PieChart pieChart2;
    
    @FXML  //BarChart
    private BarChart barGraph;
    
    @FXML
    private CategoryAxis xAxis;
    
    @FXML
    private NumberAxis yAxis;
    
    @FXML //Input Fields
    private ComboBox<String> valueCBox;
    
    @FXML
    private ComboBox classFilterCB;
    
    @FXML
    private ComboBox typeCBox;
    
    @FXML
    private TextField neigh1Box;
    
    @FXML
    private TextField neigh2Box;
            
    Property database = CreateDatabase.createDB(getClass().getResource("/data/Property_Assessment_Data.csv"));
    private final HashMap<String, Neighbourhood> neighData = getNeighbourhoodData();  //Map of Neighbourhood Data for easy recall of data
    
    /**
     * This method is used by the main button. It returns the app back to the main page
     * 
     * @param event - click event
     * @throws IOException 
     */
    @FXML
    void mainPress(ActionEvent event) throws IOException {
		App.setNewScene("mainPage", 760, 537);
    }
    
    /**
     * This method is used by the quit button. It closes the app.
     * 
     * @param event - Click event
     * @throws IOException 
     */
    @FXML
    void quitPress(ActionEvent event) throws IOException{
        javafx.application.Platform.exit();
    }
    
    /**
     * This method is used by the graph button. This method is called when 'graph' is pressed.
     * It calls other methods to validate input and if valid input it will create and display the requested chart type.
     * 
     * @param event - Click event
     * @throws IOException 
     */
    @FXML
    void graphPress(ActionEvent event) throws IOException{
        clearErrors();
        if(!(verifyInputs())){return;}
        
        //If Selection 1; Build the BarChart 
        if(typeCBox.getSelectionModel().getSelectedIndex() == 0){
            pieChart1.setVisible(false);
            pieChart2.setVisible(false);
            barGraph.setVisible(true);
            createBarChart(valueCBox.getValue(), neigh1Box.getText().toUpperCase(), neigh2Box.getText().toUpperCase());
        }
        
        //If Selection 2; Build both Pie Charts
        if(typeCBox.getSelectionModel().getSelectedIndex() == 1){
            barGraph.setVisible(false);
            pieChart1.setVisible(true);
            pieChart2.setVisible(true);
           createPieChart(neigh1Box.getText().toUpperCase(), pieChart1);
           createPieChart(neigh2Box.getText().toUpperCase(), pieChart2);
        }
    }
    
    /**
     * This method is used by the clear button. It sets all input field back to default 
     * and removes any input error messages. 
     * 
     * @param event - Click event
     * @throws IOException 
     */
    @FXML
    void clearPress(ActionEvent event) throws IOException{
        valueCBox.setValue(null);
        typeCBox.setValue(null);
        neigh1Box.clear();
        neigh2Box.clear();
        clearErrors();
        classFilterCB.setVisible(false);
        classFilterCB.setValue(null);
        classFilterText.setVisible(false);
    }
    
    /**
     * This method is used as a listener for a selection made on the values or 1st ComboBox.
     * Upon selecting an option in this box this method will set the appropriate selection options for the 'type' ComboBox or the 2nd ComboBox.
     * 
     * @param event - Selection made event
     */
    @FXML
    void valueSelection(ActionEvent event){
        typeCBox.getItems().clear();
        classFilterCB.setVisible(false);
        classFilterCB.setValue(null);
        classFilterText.setVisible(false);
        
        //Selection - "Average Value of Properties"
        if(valueCBox.getSelectionModel().getSelectedIndex() == 0){
            typeCBox.getItems().addAll("Bar Chart");
        }
        //Selection - "Total Number of Properties"
        else if(valueCBox.getSelectionModel().getSelectedIndex() == 1){
            typeCBox.getItems().addAll("Bar Chart", "Pie Chart");
        }
    }
    
    /**
     * This method acts as a listener for the 'type' ComboBox and listens for a selection made within the ComboBox.
     * Upon selection this listener will display the additional filter field for filtering by Assessment Class if the BarChart is selected 
     * otherwise if PieChart is chosen then this additional filter is hidden.
     * 
     * @param event - Selection made event
     */
    @FXML
    void filterSelection(ActionEvent event){
        if(typeCBox.getSelectionModel().getSelectedIndex() == 0){
            classFilterCB.setVisible(true);
            classFilterText.setVisible(true);
        }
        else if(typeCBox.getSelectionModel().getSelectedIndex() == 1){
            classFilterCB.setVisible(false);
            classFilterCB.setValue(null);
            classFilterText.setVisible(false);
        }
    }
    
    /**
     * This is the initializer for the Charts page. This will setup the initial chart as well as the values for the ComboBoxes.
     * The initial chart is hard coded for 'Duggan' and 'Downtown' neighbourhoods simply as an example chart for the user upon opening the feature page.
     */
    public void initialize(){
        
        classFilterCB.getItems().addAll("All Assessment Classes", "Residential", "Other Residential", "Commercial", "Farmland");
        classFilterCB.setValue("All Assessment Classes");
        valueCBox.getItems().addAll("Average Value of Properties", "Total Number of Properties");
        createBarChart("Average Value of Properties", "DUGGAN", "DOWNTOWN");
        classFilterCB.setValue(null);
    }
    
    /**
     * This method is used to retrieve and add the neighbourhood data to a PieChart object in order to 
     * create a PieChart
     * 
     * @param neighbourhood - This is the name of the neighbourhood whose data you wish to create the chart with
     * @param chart - This is the javaFX chart object that you wish to put the data into 
     */
    public void createPieChart(String neighbourhood, PieChart chart){
        chart.getData().clear();
        
        chart.getData().add(new PieChart.Data(neighData.get(neighbourhood).residential.getAClass(),neighData.get(neighbourhood).residential.getCount()));
        chart.getData().add(new PieChart.Data(neighData.get(neighbourhood).otherResidential.getAClass(),neighData.get(neighbourhood).otherResidential.getCount()));
        chart.getData().add(new PieChart.Data(neighData.get(neighbourhood).commercial.getAClass(),neighData.get(neighbourhood).commercial.getCount()));
        chart.getData().add(new PieChart.Data(neighData.get(neighbourhood).farmland.getAClass(),neighData.get(neighbourhood).farmland.getCount()));
        
        chart.setTitle("Distribution of Assessment Classes in " + neighbourhood.toUpperCase());
    }
    
    /**
     * This method is used to retrieve data for two neighbourhoods and then input that data into a javaFX BarChart object to create a double bar
     * graph of the chosen data. Also allows for filtering of which assessment classes are displayed.
     * 
     * @param values - This is a string stating which values from the neighbourhood data you would like to create the graph with.
     * @param n1 - This is the name of the 1st neighbourhood
     * @param n2 - This is the name of the 2nd neighbourhood
     */
    public void createBarChart(String values, String n1, String n2){
        barGraph.getData().clear();
        XYChart.Series series1 = new XYChart.Series();
        XYChart.Series series2 = new XYChart.Series();
        series1.setName(n1);
        series2.setName(n2);
        yAxis.setLabel(values);
        
        if(classFilterCB.getSelectionModel().getSelectedIndex() == 0 || classFilterCB.getSelectionModel().getSelectedIndex() == 1){
            series1.getData().add(new XYChart.Data(neighData.get(n1).residential.getAClass(), neighData.get(n1).residential.getData(values)));
            series2.getData().add(new XYChart.Data(neighData.get(n2).residential.getAClass(), neighData.get(n2).residential.getData(values)));
        }
        
        if(classFilterCB.getSelectionModel().getSelectedIndex() == 0 || classFilterCB.getSelectionModel().getSelectedIndex() == 2){
            series1.getData().add(new XYChart.Data(neighData.get(n1).otherResidential.getAClass(), neighData.get(n1).otherResidential.getData(values)));
            series2.getData().add(new XYChart.Data(neighData.get(n2).otherResidential.getAClass(), neighData.get(n2).otherResidential.getData(values)));
        }
        
        if(classFilterCB.getSelectionModel().getSelectedIndex() == 0 || classFilterCB.getSelectionModel().getSelectedIndex() == 3){
            series1.getData().add(new XYChart.Data(neighData.get(n1).commercial.getAClass(), neighData.get(n1).commercial.getData(values)));
            series2.getData().add(new XYChart.Data(neighData.get(n2).commercial.getAClass(), neighData.get(n2).commercial.getData(values)));
        }
        
        if(classFilterCB.getSelectionModel().getSelectedIndex() == 0 || classFilterCB.getSelectionModel().getSelectedIndex() == 4){
            series1.getData().add(new XYChart.Data(neighData.get(n1).farmland.getAClass(), neighData.get(n1).farmland.getData(values)));
            series2.getData().add(new XYChart.Data(neighData.get(n2).farmland.getAClass(), neighData.get(n2).farmland.getData(values)));
        }
        barGraph.getData().addAll(series1, series2);
    }
    
    /**
     * This method is used to confirm whether or not all required input fields have received correct input from the user for
     * creating the desired charts.
     * 
     * @return boolean True if all inputs are validated.
     */
    public boolean verifyInputs(){
        boolean verification = true;
        if(valueCBox.getValue() == null){ 
            valueError.setText("Error! Please Select an Option");
            verification = false;}
        if(typeCBox.getValue() == null) { 
            typeError.setText("Error! Please Select an Option");
            verification = false;}
        if(classFilterCB.isVisible() && classFilterCB.getValue() == null) { 
            cFilterError.setText("Error! Please Select an Option");
            verification = false;}
        if("".equals(neigh1Box.getText())){ 
            neigh1Error.setText("Error! No Value Entered");
            verification = false;}
        if("".equals(neigh2Box.getText())){ 
            neigh2Error.setText("Error! No Value Entered");
            verification = false;}
        if(!(neighData.containsKey(neigh1Box.getText().toUpperCase()))){ 
            neigh1Error.setText("Neighbourhood not Found.\nEnter Valid Neighbourhood");
            verification = false;}
        if(!(neighData.containsKey(neigh2Box.getText().toUpperCase()))){ 
            neigh2Error.setText("Neighbourhood not Found.\nEnter Valid Neighbourhood");
            verification = false;}
        return verification;
    }
    
    /**
     * This is a simple helper method that removes all input validation errors from the display page.
     */
    public void clearErrors(){
        valueError.setText("");
        typeError.setText("");
        neigh1Error.setText("");
        neigh2Error.setText("");
        cFilterError.setText("");
    } 
    
    /**
     * This method is used by the controller to create a map containing neighbourhood names as keys with their respective data assigned to them.
     * The data is stored within the Neighbourhood.java class 
     * 
     * @return HashMap<String, Neighbourhood> which is the neighbourhood name and data stored within a HashMap collection
     */
    public HashMap<String, Neighbourhood> getNeighbourhoodData(){
            HashMap<String, Neighbourhood> data = new HashMap<>();
            for(Entry entry : database.getAllEntries()){
                String neigh = entry.getNeighbourhood();
                if(data.containsKey(neigh)){
                    Neighbourhood nData = data.get(neigh);
                    nData.updateData(entry);
                    data.put(neigh, nData);
                }
                else{
                    Neighbourhood nData = new Neighbourhood();
                    nData.updateData(entry);
                    data.put(neigh, nData);
                }
            }
            return data;
        }    
  
    

    

}
