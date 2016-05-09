/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ese.FitnessFunction;

import Ese.WhaleAlgorithm.Whale;
import java.text.DecimalFormat;
import net.sf.javaml.core.Dataset;
import weka.core.Instances;

/**
 *
 * @author erigha eseoghene dan
 */
public class WhaleFitnessFunction implements AbstractFitnessFunction{

    @Override
    public double getFitness(Object o) {
        Whale whale = (Whale)o;
        double x = whale.getValue(0);
        double y = whale.getValue(1);
        
        return formatNumber(fitness(x,y));
    }
    
    private double fitness(double x, double y){
       return x*x + y*y; 
    }
    
    private double formatNumber(double val){
        DecimalFormat myFormat = new DecimalFormat("#.#");
        return Double.parseDouble(myFormat.format(val));
    }

    @Override
    public double getFitness(Object o, Instances train, Instances test) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double getFitness(Object o, Dataset train, Dataset test) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
