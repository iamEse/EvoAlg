/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ese.Dataset;
import Ese.BinaryBatAlgorithm.BinaryBat;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.DefaultDataset;
import net.sf.javaml.core.Instance;
import net.sf.javaml.tools.data.FileHandler;

/**
 *
 * @author erigha eseoghene dan
 */
public class LoadDataset {
    
    Dataset train;
    Dataset test;
    
    public LoadDataset(String trainPath, String testPath) throws IOException{
        train = readData(trainPath);
        test = readData(testPath);
    }
    
    public Dataset getTrainingData(){
        return train;
    }
    
    public Dataset getTestData(){
        return test;
    }
    
    public Dataset readData(String filePath) throws FileNotFoundException, IOException{
         
        Dataset dataset = FileHandler.loadDataset(new File(filePath),41,",");  
        return dataset;
    }
    
    public Dataset createReducedDataset(Dataset myDataset, BinaryBat bat){
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
    
}
