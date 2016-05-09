/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ese.BatAlgorithm;

/**
 *
 * @author erigha eseoghene dan
 */
public class JBA {
    
    private BA ba;  
    
     /*
         For each optimization task
         1. Determine the dimension of the problem:
            This is similar to the number of genes in Genetic algorithm(GA)
         2. Set the lower and upper bounds of each gene in the FFA class (init_bounds)
       
          NOTE: 
          1. This algorithm is less computationally expensive than GA
          2. It yields better solutions compared to FireFly,PSO,GA, and GSA
       
          ENJOY THIS BEAUTIFUL ALGORITHM!!!!!!!!!
          courtesy of Xin Shen Yang,2010
          coded by @Ese
       */
    
    private void init_ba(){
       int n = 10;
       int maxGen = 10;
       double loudness = 0.5;
       double pulse = 0.5;
       int dimension = 2;
       
       //ba = new BA(n,maxGen,loudness,pulse,dimension);
    }
    
    public static void main(String[] args) {
        // TODO code application logic here
        
       new JBA().init_ba();
        
    }
    
    
}
