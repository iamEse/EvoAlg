/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ese.ClonalSelectionAlgorithm;

import java.io.Serializable;

/**
 *
 * @author erigha eseoghene dan
 */
public class Antibody implements Comparable<Antibody>, Serializable{
    
    private final double [] attributes;
    
    private double affinity;
    
    public Antibody(Antibody aParent){
        double [] copy = new double[aParent.attributes.length];
        System.arraycopy(aParent.attributes, 0, copy, 0, copy.length);
        attributes = copy;
    }
    public void setValue(int key, double value){
        attributes[key] = value;
    }
    
    public double getValue(int pos) {
        return attributes[pos];
    }
    
    public Antibody(double [] aAttributes){
        attributes = aAttributes;
    }
    
    public Antibody(int dimension){
         attributes = new double[dimension];
     }
    
    /**
     * @return Returns the attributes.
     */
    public double[] getAttributes(){
        return attributes;
    }
    
    /**
     * @return Returns the affinity.
     */
    public double getAffinity(){
        return affinity;
    }
    /**
     * @param affinity The affinity to set.
     */
    public void setAffinity(double affinity){
        this.affinity = affinity;
    }

    @Override
    public int compareTo(Antibody other) {
        if(affinity < other.affinity){
            return -1;
        }
        else if(affinity > other.affinity){
            return +1;
        }
        
        return 0;
    }
    
}
