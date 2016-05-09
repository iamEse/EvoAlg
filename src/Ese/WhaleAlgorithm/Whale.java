/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ese.WhaleAlgorithm;

import java.io.Serializable;

/**
 *
 * @author erigha eseoghene dan
 */
public class Whale implements Comparable<Whale>, Serializable{
    
    protected double [] attributes;
    protected double aFitness;
    
    public Whale(double [] aAttributes){
        attributes = aAttributes;
    }
     
     public Whale(int dimension){
         attributes = new double[dimension];

     }
     
     public double getFitness(){
        return aFitness;
    }
    
    
    public void setFitness(double value){
        aFitness = value;
    }
     
     public void setValue(int key, double value){
        attributes[key] = value;
    }
    
    public double getValue(int pos) {
        return attributes[pos];
    }
    
    public int noAttributes() {
        return attributes.length;
    }
    
    public double [] getAttributes(){
       return attributes;
    }
    
    @Override
    public int compareTo(Whale other) {
        if(aFitness < other.aFitness){
           return -1;
       }else if(aFitness > other.aFitness){
           return +1;
       }
       return 0;
    }
    
}
