/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ese.Evaluate;

import net.sf.javaml.core.Dataset;
import Ese.BatAlgorithm.BA;
import Ese.BatAlgorithm.Bat;

/**
 *
 * @author erigha eseoghene dan
 */
public class EvaluateBinaryBat {
    
       int n = 10;
       int maxGen = 10;
       double loudness = 0.5;
       double pulse = 0.5;
       int dimension = 2;
       Dataset train;
       Dataset test;
       
       public EvaluateBinaryBat(Dataset train, Dataset test){
        this.train = train;
        this.test = test;
       }
       
       public Bat runBBAoptimization(){
           
           BA ba = new BA(n,maxGen,loudness,pulse,dimension,train,test);
           return ba.executeBA();
       }
}
