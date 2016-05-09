/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ese.BinaryBatAlgorithm;

import Ese.Dataset.LoadDataset;
import Ese.Evaluate.SVM;
import java.io.IOException;
import net.sf.javaml.core.Dataset;




/**
 *
 * @author erigha eseoghene dan
 */
public class JBBA {
    
    private BBA bba;
    private  BinaryBat bBat;
    private  final String trainPath = "C:\\Users\\erigha eseoghene dan\\Documents\\Trees\\Iris\\JavaML\\irisTrainTrain.txt";
    private  final String testPath = "C:\\Users\\erigha eseoghene dan\\Documents\\Trees\\Iris\\JavaML\\irisTest.txt";
    
    
    private void init_ba() throws IOException{
       int n = 10;
       int maxGen = 10;
       double loudness = 0.5;
       double pulse = 0.5;
       int dimension = 41;
       
       
       bba = new BBA(n,maxGen,loudness,pulse,dimension);
       bBat = bba.execute();
    }
    
    private void testPhase() throws IOException{
       
        LoadDataset load = new LoadDataset(trainPath, testPath);
        Dataset test = load.getTestData();
        Dataset train = load.getTrainingData();
        Dataset reducedTest = load.createReducedDataset(test, bBat);
        Dataset reducedTrain = load.createReducedDataset(train, bBat);
        
        SVM svm  = new SVM(reducedTrain,reducedTest,bBat.getCost(),bBat.getGamma());
        System.out.println("Acuracy: "+svm.getAccuracy()*100);
        
        System.out.println();
        System.out.println();
        
        
        System.out.println();
        int [] attrib = bBat.getAttributes();
        
        System.out.println("Optimal feature subset");
        for(int i = 0; i< attrib.length; i++){
            System.out.print(attrib[i] +" ");
        }
        
        System.out.println();
        System.out.println("COST: "+ bBat.getCost() +"\t"+"GAMMA: " +bBat.getGamma());
    }
    
    
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        
      JBBA jbba =  new JBBA();
      jbba.init_ba(); 
      jbba.testPhase();
        
    }
    
}
