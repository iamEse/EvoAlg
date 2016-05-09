/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evoalg;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import weka.classifiers.Evaluation;
import weka.classifiers.functions.LibSVM;
import static weka.classifiers.functions.LibSVM.TAGS_KERNELTYPE;
import static weka.classifiers.functions.LibSVM.TAGS_SVMTYPE;
import weka.core.Instances;
import weka.core.SelectedTag;

/**
 *
 * @author erigha eseoghene dan
 */
public class EvoAlg {

    private static Instances readData(String filePath) throws FileNotFoundException, IOException{
        
         Instances dataset;
         
        try (BufferedReader reader = new BufferedReader(
                new FileReader(filePath))) {
            
            dataset = new Instances(reader);
            
            reader.close();
        }   
        return dataset;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
          
        LibSVM svm = new LibSVM();
        svm.setKernelType(new SelectedTag(0,TAGS_KERNELTYPE));
        svm.setSVMType(new SelectedTag(0,TAGS_SVMTYPE));
        
        String trainPath = "C:\\Users\\erigha eseoghene dan\\Documents\\Trees\\Iris\\irisTrain.txt";
        String testPath = "C:\\Users\\erigha eseoghene dan\\Documents\\Trees\\Iris\\irisTest.txt";
        
        try{
            
            Instances train = readData(trainPath);
            train.setClassIndex(train.numAttributes()-1);
            
            svm.buildClassifier(train);
            svm.setNormalize(true);
            
            Instances test = readData(testPath);
            test.setClassIndex(test.numAttributes()-1);
            
            Evaluation eval = new Evaluation(train);
            eval.evaluateModel(svm, test);
            System.out.println(eval.pctCorrect());
            System.out.println(eval.toSummaryString("\nResults\n======\n", false)); 
            
        }catch(Exception err){
            System.out.println(err);
        }
    }
    
}
