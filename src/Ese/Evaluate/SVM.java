/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ese.Evaluate;

import java.util.Map;
import net.sf.javaml.classification.evaluation.EvaluateDataset;
import net.sf.javaml.classification.evaluation.PerformanceMeasure;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.tools.weka.WekaClassifier;
import weka.classifiers.functions.LibSVM;
import static weka.classifiers.functions.LibSVM.TAGS_KERNELTYPE;
import static weka.classifiers.functions.LibSVM.TAGS_SVMTYPE;
import weka.core.SelectedTag;

/**
 *
 * @author erigha eseoghene dan
 */
public class SVM {
    
    Dataset train;
    Dataset test;
    double cost;
    double gamma;
    
    public SVM(Dataset train, Dataset test, double cost, double gamma){
        this.train = train;
        this.test = test;
        this.cost = cost;
        this.gamma = gamma;
    }
    
    
    public double getAccuracy(){
        double accuracy = 0.0;
        
        LibSVM libSVM = new LibSVM();
        libSVM.setKernelType(new SelectedTag(2,TAGS_KERNELTYPE));
        libSVM.setSVMType(new SelectedTag(0,TAGS_SVMTYPE));
        libSVM.setNormalize(true);
        libSVM.setShrinking(false);
        libSVM.setCost(cost);
        libSVM.setGamma(gamma);
        
        net.sf.javaml.classification.Classifier svm = new WekaClassifier((weka.classifiers.Classifier)libSVM);
        
        try{
            svm.buildClassifier(train);
            
            Map<Object,PerformanceMeasure> pm = EvaluateDataset.testDataset(svm, test);
           
           
           for(Object o: pm.keySet()){
               String s = (String)o;
               
               if(s.equals("anomaly")){
                   System.out.println(s);
                   System.out.println("TPRate: "+pm.get(o).getTPRate()*100);
                   System.out.println("False Alarm Rate: "+pm.get(o).getFPRate()*100);
                   System.out.println("Accuracy: "+pm.get(o).getAccuracy()*100);
                   accuracy =  pm.get(o).getAccuracy();
               }
             
           }
           
        }catch(Exception err){
            System.out.println(err.getMessage());
        }
        
       return accuracy;
    }
    
}
