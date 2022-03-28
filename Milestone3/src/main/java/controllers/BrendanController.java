package controllers;

import dataBase.CreateDatabase;
import dataBase.Entry;
import dataBase.Neighbourhood;
import dataBase.Property;
import java.io.IOException;
import java.util.HashMap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import main.App;

public class BrendanController {
    
    @FXML
    private Text typeError;
    
    @FXML
    private Text valueError;
    
    @FXML
    private Text neigh1Error;
    
    @FXML
    private Text neigh2Error;
    
    @FXML  //Buttons
    private Button mainButton;
    private Button quitButton;
    private Button graphButton;
    private Button clearButton;
    
    @FXML  //BarChart
    private BarChart barGraph;
    
    @FXML
    private CategoryAxis xAxis;
    
    @FXML
    private NumberAxis yAxis;
    
    @FXML //Input Fields
    private ComboBox<String> valueCBox;
    
    @FXML
    private ComboBox typeCBox;
    
    @FXML
    private TextField neigh1Box;
    
    @FXML
    private TextField neigh2Box;
            
    Property database = CreateDatabase.createDB(getClass().getResource("/data/Property_Assessment_Data.csv"));
    private HashMap<String, Neighbourhood> neighData = getNeighbourhoodData();
    
    @FXML
    void mainPress(ActionEvent event) throws IOException {
		App.setNewScene("mainPage", 760, 537);
    }
    
    @FXML
    void quitPress(ActionEvent event) throws IOException{
        javafx.application.Platform.exit();
    }
    
    @FXML
    void graphPress(ActionEvent event) throws IOException{
        clearErrors();
        boolean valid = verifyInputs();
        if(!(valid)){return;}
        
        if(typeCBox.getSelectionModel().getSelectedIndex() == 0){
            barGraph.setVisible(true);
            createBarChart(valueCBox.getValue(), neigh1Box.getText(), neigh2Box.getText());
        }
        if(typeCBox.getSelectionModel().getSelectedIndex() == 1){
            createBarChart("value", "BELMONT", "DUGGAN");
        }
    }
    
    @FXML
    void clearPress(ActionEvent event) throws IOException{
        System.out.println("Test");
    }
    
    @FXML
    void valueSelection(ActionEvent event){
        typeCBox.getItems().clear();
        if(valueCBox.getSelectionModel().getSelectedIndex() == 0){
            typeCBox.getItems().addAll("Bar Chart");
        }
        else if(valueCBox.getSelectionModel().getSelectedIndex() == 1){
            typeCBox.getItems().addAll("Bar Chart", "Pie Chart");
        }
    }
    
    public void initialize(){
        
        valueCBox.getItems().addAll("Average Value of Properties", "Number of Total Properties");
        createBarChart("Average Value of Properties", "DUGGAN", "DOWNTOWN");
    }
    
    public void createBarChart(String values, String n1, String n2){
        barGraph.getData().clear();
        XYChart.Series series1 = new XYChart.Series();
        XYChart.Series series2 = new XYChart.Series();
        series1.setName(n1);
        series2.setName(n2);
        
        series1.getData().add(new XYChart.Data(neighData.get(n1).residential.getAClass(), neighData.get(n1).residential.getData(values)));
        series2.getData().add(new XYChart.Data(neighData.get(n2).residential.getAClass(), neighData.get(n2).residential.getData(values)));
        
        series1.getData().add(new XYChart.Data(neighData.get(n1).otherResidential.getAClass(), neighData.get(n1).otherResidential.getData(values)));
        series2.getData().add(new XYChart.Data(neighData.get(n2).otherResidential.getAClass(), neighData.get(n2).otherResidential.getData(values)));
        
        series1.getData().add(new XYChart.Data(neighData.get(n1).commercial.getAClass(), neighData.get(n1).commercial.getData(values)));
        series2.getData().add(new XYChart.Data(neighData.get(n2).commercial.getAClass(), neighData.get(n2).commercial.getData(values)));
        
        series1.getData().add(new XYChart.Data(neighData.get(n1).farmland.getAClass(), neighData.get(n1).farmland.getData(values)));
        series2.getData().add(new XYChart.Data(neighData.get(n2).farmland.getAClass(), neighData.get(n2).farmland.getData(values)));
        
        barGraph.getData().addAll(series1, series2);
    }
 
    public boolean verifyInputs(){
        boolean verification = true;
        if(valueCBox.getValue() == null){ 
            valueError.setText("Error! Please Select an Option");
            verification = false;}
        if(typeCBox.getValue() == null) { 
            typeError.setText("Error! Please Select an Option");
           verification = false;}
        if("".equals(neigh1Box.getText())){ 
            neigh1Error.setText("Error! No Value Entered");
            verification = false;}
        if("".equals(neigh2Box.getText())){ 
            neigh2Error.setText("Error! No Value Entered");
            verification = false;}
        if(!(neighData.containsKey(neigh1Box.getText()))){ 
            neigh1Error.setText("Neighbourhood not Found.\nEnter Valid Neighbourhood");
            verification = false;}
        if(!(neighData.containsKey(neigh2Box.getText()))){ 
            neigh2Error.setText("Neighbourhood not Found.\nEnter Valid Neighbourhood");
            verification = false;}
        return verification;
    }
    
    public void clearErrors(){
        valueError.setText("");
        typeError.setText("");
        neigh1Error.setText("");
        neigh2Error.setText("");
    } 
    
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
