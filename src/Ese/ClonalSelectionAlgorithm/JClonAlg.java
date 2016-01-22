/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ese.ClonalSelectionAlgorithm;

/**
 *
 * @author erigha eseoghene dan
 */
public class JClonAlg {
    
     private CLONALGOpt clonalg;
   
   private void exec_clonalg(){
       
       /*
         For each optimization task
         1. Determine the dimension of the problem:
            This is similar to the number of genes in Genetic algorithm(GA)
         2. Set the lower and upper bounds of each subcell in the CLONALGOpt class (init_bounds)
       
          NOTE: 
          1. This algorithm is similar to GA except without the utilization of crossover
          2. It yields better solutions compared to GA in some optimization problems
       
          ENJOY THIS BEAUTIFUL ALGORITHM!!!!!!!!!
          courtesy of De Castro L.N., Von Zuben F.J.: 
                      “Learning and optimization using the clonalselection principle”. 
                       IEEE Trans. on Evolutionary Computation, 
                       vol 6, no 3, pp.239-251, (2002).
          coded by @Ese
       */
       double clonalFactor = 0.1;
       int antibodypoolSize = 10;
       int selectionPoolSize = 5;
       int replacementSize = 3;
       int numGenerations = 20;
       int dimension = 2;
       
       clonalg = new CLONALGOpt(clonalFactor,antibodypoolSize,selectionPoolSize,
                      replacementSize,numGenerations,dimension);
       
       
   }
   
   public static void main(String[] args) {
        // TODO code application logic here
        
       new JClonAlg().exec_clonalg();
        
    }
    
}
