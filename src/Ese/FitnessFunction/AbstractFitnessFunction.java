/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ese.FitnessFunction;

import java.io.Serializable;

/**
 *
 * @author erigha eseoghene dan
 */
public interface AbstractFitnessFunction extends Serializable{
    
    public double getFitness(Object o);
    
    
}
