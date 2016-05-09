/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ese.FitnessFunction;
import Ese.BatAlgorithm.Bat;
import Ese.Evaluate.EvaluateSolution;
import java.text.DecimalFormat;
import net.sf.javaml.core.Dataset;
import weka.core.Instances;


/**
 *
 * @author erigha eseoghene dan
 */
public class BinaryBatFitnessFunction implements AbstractFitnessFunction{

    private Bat bat;
    
    @Override
    public double getFitness(Object o, Dataset etrain, Dataset etest) {
        
      Dataset dtrain = etrain.copy();
      Dataset dtest = etest.copy();
      
      EvaluateSolution eval = new EvaluateSolution(o,dtrain,dtest); 
      Bat myBat =  eval.getAccuracy();
      bat = myBat;
      return myBat.getFitness();  
    }
    
    public Bat getBat(){
        return bat;
    }
    
    private double formatNumber(double num){
      DecimalFormat myFormat = new DecimalFormat("##.##");
      String outVar = myFormat.format(num);
      return Double.parseDouble(outVar);
    }

    @Override
    public double getFitness(Object o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double getFitness(Object o, Instances train, Instances test) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
