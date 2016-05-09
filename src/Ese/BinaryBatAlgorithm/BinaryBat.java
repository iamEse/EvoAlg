/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ese.BinaryBatAlgorithm;

import java.io.Serializable;

/**
 *
 * @author erigha eseoghene dan
 */
public class BinaryBat implements Comparable<BinaryBat>,Serializable {
 
    protected int [] attributes;   
    protected double aFitness;
    protected double cost;
    protected double gamma;
      
    public BinaryBat(int [] aAttributes){
        attributes = aAttributes;
    }
     
     public BinaryBat(int dimension){
         attributes = new int[dimension];

     }
     
     public double getCost(){
         return cost;
     }
     
     public void setCost(double cost){
         this.cost = cost;
     }
     
     public double getGamma(){
         return gamma;
     }
     
     public void setGamma(double gamma){
         this.gamma = gamma;
     }
     
     public double getFitness(){
        return aFitness;
    }
    
    
    public void setFitness(double value){
        aFitness = value;
    }
     
     public void setValue(int key, int value){
        attributes[key] = value;
    }
    
    public int getValue(int pos) {
        return attributes[pos];
    }
    
    public int noAttributes() {
        return attributes.length;
    }
    
    public int [] getAttributes(){
       return attributes;
    }

   
    @Override
    public int compareTo(BinaryBat other) {
       if(aFitness < other.aFitness){
           return -1;
       }else if(aFitness > other.aFitness){
           return +1;
       }
       return 0;
    }
}
