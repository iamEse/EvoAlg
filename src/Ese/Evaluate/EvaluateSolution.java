/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ese.Evaluate;


import Ese.BatAlgorithm.Bat;
import Ese.BinaryBatAlgorithm.BinaryBat;
import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Set;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.DefaultDataset;
import net.sf.javaml.core.Instance;
/**
 *
 * @author erigha eseoghene dan
 */
public class EvaluateSolution {
     
    Dataset train;
    Dataset test;
            
    public EvaluateSolution(Object o, Dataset etrain, Dataset etest){
        
        selectFeatures(o,etrain, etest); 
    }
    
    
    private void selectFeatures(Object o, Dataset etrain, Dataset etest){
        
        BinaryBat bat = (BinaryBat)o;
       
        train = extract(etrain,bat);
        test =  extract(etest,bat);
    }
    
    private Dataset extract(Dataset myDataset, BinaryBat bat){
        
        Dataset dataset = new DefaultDataset();
        Set<Integer> indices = new HashSet<>();
        
        
        //Get the attributes of the bat in binary
         int [] attributes = bat.getAttributes();
         
         
         //retrieve positions with 0 bit value
         for(int i = 0; i < attributes.length; i++){
              
             //System.out.print(attributes[i] +" ");
             if(attributes[i] == 0){
                 indices.add(i);
             }
         }
         
        
         for(int i = 0; i < myDataset.size(); i++){
             Instance inst = myDataset.get(i);
             inst.removeAttributes(indices);
             
             dataset.add(i, inst);
         }
         
         
         return dataset;
    }
    
    private double formatNumber(double num){
      DecimalFormat myFormat = new DecimalFormat("##.##");
      String outVar = myFormat.format(num);
      return Double.parseDouble(outVar);
    }
    
    public Bat getAccuracy(){
        
        double accuracy = 0.0;
        EvaluateBinaryBat evalBat = new EvaluateBinaryBat(train,test);
       return evalBat.runBBAoptimization();
    }
}
