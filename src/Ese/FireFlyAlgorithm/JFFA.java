/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Ese.FireFlyAlgorithm;

/**
 *
 * @author erigha eseoghene dan
 */
public class JFFA {
    
   private FFA ffa;
   
   private void init_ffa(){
       
       /*
         For each optimization task
         1. Determine the dimension of the problem:
            This is similar to the number of genes in Genetic algorithm(GA)
         2. Set the lower and upper bounds of each gene in the FFA class (init_bounds)
       
          NOTE: 
          1. This algorithm is less computationally expensive than GA
          2. It yields better solutions compared to GA
       
          ENJOY THIS BEAUTIFUL ALGORITHM!!!!!!!!!
          courtesy of Xin Shen Yang,2010
          coded by @Ese
       */
       int n = 10;
       int maxGen = 20;
       double alpha = 0.25;
       double betamin = 0.20;
       double gamma = 1.0;
       int dimension = 2;
       
       ffa = new FFA(n,maxGen,alpha,betamin,gamma,dimension);
   }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
       new JFFA().init_ffa();
        
    }
}
