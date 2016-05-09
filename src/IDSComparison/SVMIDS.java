/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IDSComparison;
import java.util.Map;
import net.sf.javaml.classification.evaluation.EvaluateDataset;
import net.sf.javaml.classification.evaluation.PerformanceMeasure;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.tools.weka.WekaClassifier;
import weka.classifiers.functions.LibSVM;
import static weka.classifiers.functions.LibSVM.TAGS_KERNELTYPE;
import static weka.classifiers.functions.LibSVM.TAGS_SVMTYPE;
import weka.core.SelectedTag;
import Ese.Dataset.LoadDataset;
import java.io.IOException;
import net.sf.javaml.classification.evaluation.CrossValidation;

/**
 *
 * @author erigha eseoghene dan
 */
public class SVMIDS {
    
    
    public static void main (String []args) throws IOException{
        
        String trainPath = "C:\\Users\\erigha eseoghene dan\\Documents\\PAPER\\NSLKDD\\Random\\KDDTRAINTRAIN.txt";
        String testPath = "C:\\Users\\erigha eseoghene dan\\Documents\\PAPER\\NSLKDD\\Random\\KDDTEST.txt";
        
        LoadDataset load = new LoadDataset(trainPath, testPath);
        
        Dataset train = load.getTrainingData();
        Dataset test = load.getTestData();
        
        LibSVM libSVM = new LibSVM();
        libSVM.setKernelType(new SelectedTag(2,TAGS_KERNELTYPE));
        libSVM.setSVMType(new SelectedTag(0,TAGS_SVMTYPE));
        libSVM.setNormalize(true);
        libSVM.setShrinking(false);
        
        
        net.sf.javaml.classification.Classifier svm = new WekaClassifier((weka.classifiers.Classifier)libSVM);
        
        try{
            
            svm.buildClassifier(train);
            Map<Object,PerformanceMeasure> pm = EvaluateDataset.testDataset(svm, test);
           
            //Performance on test data
           
           for(Object o: pm.keySet()){
               
               String s = (String)o;
               
               if(s.equals("anomaly")){
                   System.out.println("TEST DATA");
                   System.out.println(s);
                   System.out.println("TPRate: "+pm.get(o).getTPRate()*100);
                   System.out.println("False Alarm Rate: "+pm.get(o).getFPRate()*100);
                   System.out.println("Accuracy: "+pm.get(o).getAccuracy()*100);
               }
           }
           
        }catch(Exception err){
            System.out.println(err.getMessage());
        }
        
        
    }
    
}
