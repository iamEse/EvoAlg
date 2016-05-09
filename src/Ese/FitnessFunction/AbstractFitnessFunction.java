/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ese.FitnessFunction;

import java.io.Serializable;
import net.sf.javaml.core.Dataset;
import weka.core.Instances;

/**
 *
 * @author erigha eseoghene dan
 */
public interface AbstractFitnessFunction extends Serializable{
    
    public double getFitness(Object o);
    
    public double getFitness(Object o, Instances train, Instances test);
    
    public double getFitness(Object o, Dataset train, Dataset test);
    
    
}
