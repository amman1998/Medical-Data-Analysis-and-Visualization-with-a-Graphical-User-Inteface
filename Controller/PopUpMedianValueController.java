/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.ModelForExperiments;
import Model.UserInputForBeadPlate;
import Model.bead;
import Model.probeTableData;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author feiping
 */
public class PopUpMedianValueController implements Initializable {

    @FXML
    private GridPane medianValueGridPane;
    @FXML
    private Text analyteName;

    /**
     * Initializes the controller class.
     */
    private bead curAnalyte;
    private int curSamplePos = 0;
    private int plateIndex = 0;
    private int probeIndex = 0;
    private int numberOfExperiments = 0;
    private int numberOfSamples =0;
    private String[] sampleNames;
    private List<Integer> experimentsToShow = new ArrayList<>(); // experiments that contains cur sample and will be showed in the table 
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        curAnalyte = ModelForExperiments.getInstance().getCurAnalyte();
        curSamplePos = ModelForExperiments.getInstance().getCurSample();
        plateIndex = ModelForExperiments.getInstance().getCurPlate();
        probeIndex = ModelForExperiments.getInstance().getCurProbe();
        numberOfExperiments = ModelForExperiments.getInstance().getNumberOfExperiments();
        numberOfSamples = ModelForExperiments.getInstance().getNumberOfSamples();
        sampleNames = ModelForExperiments.getInstance().getSampleNames();
       
        // calculate how many experiment contains that sample 
        HashMap<Integer, List<UserInputForBeadPlate>> userInputsForBeadPlateMap = ModelForExperiments.getInstance().getUserInputsForBeadPlateMap();
        for( int i = 1; i <=numberOfExperiments; i++)
        {
            List<UserInputForBeadPlate> inputList = userInputsForBeadPlateMap.get(i);
            for(UserInputForBeadPlate input :userInputsForBeadPlateMap.get(i) )
            {
                if(input.getNumOfSamples()>= curSamplePos)
                {
                    experimentsToShow.add( i );
                    break;
                }
            }  
        }
        
        tableCol(experimentsToShow.size());
        tableRow( sampleNames );    

        
        analyteName.setText(curAnalyte.getAnalyte() + "(" +curAnalyte.getRegionNumber()+")" );
    }    

    public  GridPane tableRow(String[] sampleNames ){
        int rows = sampleNames.length;
        for(int i=1; i<=rows; i++){
            //set width for cells of the cols
            ColumnConstraints column = new ColumnConstraints(30);
            medianValueGridPane.getColumnConstraints().add(column);
            Label label = new Label();
            String s = "  " + sampleNames[i-1];
            label.setText(s);    
            label.autosize();
            medianValueGridPane.add(label,0,i);
            
         }
        return medianValueGridPane;
        }


    public  GridPane tableCol( int col){
       for(int i=1; i<=col; i++){
        //set width for cells of the cols100
        ColumnConstraints column = new ColumnConstraints(100);
        medianValueGridPane.getColumnConstraints().add(column);
        Label label = new Label();
        String s = "Experiment " + experimentsToShow.get(i-1);
        label.setText(s);    
        //textField.setAlignment(Pos.CENTER);
        label.autosize();
        medianValueGridPane.add(label,i,0);
        showMedianValue(experimentsToShow.get(i-1), i);
     }
    return medianValueGridPane;
    }

    //experiment starts from 1
    private void showMedianValue(int experiment, int col) {
        int analyteRegionNumber = curAnalyte.getRegionNumber();
        double medianValue =0;
        for(int i = 0; i<sampleNames.length; i++)
        {
            Label label = new Label();
            if(ModelForExperiments.getInstance().getMedianValueMatrix().get(experiment).get(plateIndex).size() <= i ||
                ModelForExperiments.getInstance().getMedianValueMatrix().get(experiment).get(plateIndex).get(i).get(probeIndex)==null    )
            {
                break;         
            }
            else 
            {
                medianValue = ModelForExperiments.getInstance().getMedianValueMatrix().get(experiment).get(plateIndex).get(i).get(probeIndex).get(analyteRegionNumber);
                label.setText("   " +Double.toString(medianValue));    
         
            }
            label.autosize();
            medianValueGridPane.add(label,col, i+1);       

        }       
    }


}