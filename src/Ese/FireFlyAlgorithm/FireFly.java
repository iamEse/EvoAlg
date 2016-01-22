/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Ese.FireFlyAlgorithm;

import java.io.Serializable;

/**
 *
 * @author erigha eseoghene dan
 */
public class FireFly implements Comparable<FireFly>, Serializable{
        protected double [] attributes;
       
        protected double aLightIntensity;
    
     public FireFly(double [] aAttributes){
        attributes = aAttributes;
    }
     
     public FireFly(int dimension){
         attributes = new double[dimension];
     }
     
     
    @Override
    public int compareTo(FireFly other) {
       if(aLightIntensity < other.aLightIntensity){
           return -1;
       }else if(aLightIntensity > other.aLightIntensity){
           return +1;
       }
       return 0;
    }
    
    
    public double getLightIntensity(){
        return aLightIntensity;
    }
    
    
    public void setLightIntensity(double value){
        aLightIntensity = value;
    }
    
    public void setValue (int key, double value){
        attributes[key] = value;
    }
    
    public double getValue(int pos) {
        return attributes[pos];
    }
    
    public int noAttributes() {
        return attributes.length;
    }
    
    public double [] getAttributes(){
//       double [] attribs = new double [attributes.length];
//       System.arraycopy(attributes,0,attribs,0,attributes.length);
       return attributes;
    }
    
 
}
