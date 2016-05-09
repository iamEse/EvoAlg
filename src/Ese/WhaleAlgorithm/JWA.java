/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ese.WhaleAlgorithm;
import Ese.Dataset.LoadDataset;
import Ese.Evaluate.SVM;
import java.io.IOException;
import net.sf.javaml.core.Dataset;

/**
 *
 * @author erigha eseoghene dan
 */
public class JWA {
    
    private WOA wa;
    private static Whale whale;
    private static final String traintrainPath = "C:\\Users\\erigha eseoghene dan\\Documents\\PAPER\\NSLKDD\\Random\\KDDTRAINTRAIN.txt";
    private static final String traintestPath = "C:\\Users\\erigha eseoghene dan\\Documents\\PAPER\\NSLKDD\\Random\\KDDTRAINTEST.txt";
    private static final String testPath = "C:\\Users\\erigha eseoghene dan\\Documents\\PAPER\\NSLKDD\\Random\\KDDTEST.txt";
    LoadDataset load;
    Dataset train;
    
    
    private void init_ba() throws IOException{
       
       load = new LoadDataset(traintrainPath, traintestPath);
       train = load.getTrainingData();
       Dataset test = load.getTestData();
       
       int n = 10;
       int maxGen = 10;
       int dimension = 2;
       
       wa = new WOA(n,maxGen,dimension);
       whale = wa.execute();
    }
    
    private void testPhase() throws IOException{
       Dataset test =  load.readData(testPath);
       SVM svm  = new SVM(train,test,whale.getValue(0),whale.getValue(1));
       
       System.out.println("Best Bat Fitness After Training: "+ whale.getFitness());
       System.out.println();
       
       System.out.println("Acuracy during Testing: "+svm.getAccuracy()*100);
       System.out.println("COST: "+ whale.getValue(0) +"\t"+"GAMMA: " +whale.getValue(1));
    }
    
    
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        
      JWA jwa =  new JWA();
      
      jwa.init_ba(); 
      //jwa.testPhase();
        
    }
    
}
