/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ese.WhaleAlgorithm;

import Ese.FitnessFunction.WhaleFitnessFunction;
import java.util.Random;
import net.sf.javaml.core.Dataset;

/**
 *
 * @author erigha eseoghene dan
 */
public class WOA {
    
    private final Random rand;
    private final int n; //Population size
    private final int maxGen; //Number of Generations
    private final int dimension;  //dimension of solution
    private final double [] lb;
    private final double [] ub;
    private final  Whale [] aWhales;
    private double bestFitness;
    private Whale bestWhale;
    private final double [] cg_curve;
    
    //Dataset train;
    //Dataset test;
    
    public WOA(int n, int maxGen, int dimension){
        
         //this.train = train;
        //this.test = test;
        this.n = n;
        this.maxGen = maxGen;
        this.dimension = dimension;
        aWhales = new Whale[n];
        bestWhale = new Whale(dimension);
        rand = new Random();
        lb = new double[dimension];
        ub = new double [dimension];
        cg_curve = new double[maxGen];
    }
    
    
    //Initialize the lower and upper bounds
    private void init_bounds(){
         for(int j = 0; j < dimension; j++){
             
             if(j == 0){
                //lb[j] = 1.0;
		//ub[j] = 3500.0; 
                
                lb[j] = 1.0;
		ub[j] = 3.0; 
             }
             
             if(j == 1){
                //lb[j] = 0.001;
		//ub[j] = 50.0; 
                
                lb[j] = 1.0;
		ub[j] = 3.0;
             }
		
	}   
    }
    
    
    //initialize position vector and score for the leader
    private void init_best(){
        
        for(int j = 0; j < dimension; j++){
            bestWhale.setValue(j,0);
        }
        
        bestFitness = Double.NEGATIVE_INFINITY;
    }
    
    //Initialize the positions of search agents
    private void init_whales(){
        
        for(int i = 0; i < n; i++){
            
            aWhales[i] = new Whale(dimension);
            double rNum = rand.nextDouble();
            for(int j = 0; j < dimension; j++){
                
                double val = rNum*(ub[j]-lb[j])+lb[j];
                aWhales[i].setValue(j, val);
            }
        }
    }
    
    private void init_cg_curve(){
       
        for(int i = 0; i < maxGen; i++){
            cg_curve[i] = 0;
        }
    }
    
    private double getRandomized(){
        double num = (double) 1 + rand.nextInt(2);
        double dec = rand.nextDouble();
        
        double total = num + dec;
        return total;
    }
    
    private void setBounds(int i){
        
      
        for(int j =0; j < dimension; j++){
              
              double val = aWhales[i].getValue(j);
              if(val < lb[j]){
                  val = getRandomized();
                  aWhales[i].setValue(j, val);
               }
              
              if(val > ub[j]){
                   val = getRandomized();
                  aWhales[i].setValue(j, val);
               }
        } 
        
    }
    
    //Display results for each generation   
    private void displayResult(int numGen){
       System.out.println("GENERATION: "+numGen +"\t"+"Best Fitness: " +bestWhale.getFitness());
       System.out.println();
       System.out.println("Whale position");
       for(int j = 0; j < dimension; j++){
           
           System.out.print(bestWhale.getValue(j)+",");
       }
       
       System.out.println();
       System.out.println();
    }
    
    private void setBestWhale(int i){
        for(int j = 0; j <  dimension; j++){
            
            bestWhale.setValue(j,aWhales[i].getValue(j));
        }
        bestWhale.setFitness(bestFitness);
    }
    
    
    public Whale execute(){
        
        init_bounds();
        init_best();
        init_cg_curve();
        init_whales();
        int  t = 0;
        
        
        //Main loop
        while(t < maxGen){
            
            
            for(int i = 0; i < n; i++){
                
                //Normalize to upper and lower bounds if necessary
                setBounds(i);
                
                // Calculate objective function for each search agent
                double fitness = new WhaleFitnessFunction().getFitness(aWhales[i]);
                System.out.println("FITNESS: " +fitness +"\t" +aWhales[i].getValue(0)+" "+aWhales[i].getValue(1));
                
                // Update the best
                if(fitness > bestFitness){
                    
                    bestFitness = fitness;
                    setBestWhale(i);
                }
            }
            
            double a = 2-t *((2)/maxGen); // a decreases linearly fron 2 to 0 in Eq. (2.3)
            
            // a2 linearly decreases from -1 to -2 to calculate t in Eq. (3.12)
            double a2= -1+t*((-1)/maxGen);
            
            //Update the Position of search agents 
            for(int i = 0; i < n; i++){
                
                double rNum1 = rand.nextDouble();
                double rNum2 = rand.nextDouble();
                
                double A= 2*a*rNum1-a;  //Eq. (2.3) in the paper
                double C=2*rNum2;      // Eq. (2.4) in the paper
        
                double b=1;            //parameters in Eq. (2.5)
                double l= (a2-1)*rand.nextDouble()+1; // parameters in Eq. (2.5)
        
                double p = rand.nextDouble();        // p in Eq. (2.6)
                
                
                for(int j = 0; j < dimension; j++){
                    
                    if(p < 0.5){
                        
                        if(Math.abs(A) >= 1){
                            
                            int rand_leader_index = (int) Math.floor(n*rand.nextDouble()+1);
                            if(rand_leader_index == n){
                                rand_leader_index = n-1;
                            }
                            Whale x_rand = aWhales[rand_leader_index];
                            double d_x_rand= Math.abs(C*x_rand.getValue(j)-aWhales[i].getValue(j)); // Eq. (2.7)
                            double val = x_rand.getValue(j) - A * d_x_rand;  //Eq. (2.8)
                            aWhales[i].setValue(j, val);
                            
                        }
                        else if(Math.abs(A) < 1){
                            double d_leader = Math.abs(C*bestWhale.getValue(j) - aWhales[i].getValue(j));  // Eq. (2.1)
                            double val = bestWhale.getValue(j) - A*d_leader; //Eq. (2.2)
                            aWhales[i].setValue(j, val);   
                        }
                        
                    }
                    else if(p >=0.5){
                        
                        double distance_to_leader = Math.abs(bestWhale.getValue(j) - aWhales[i].getValue(j));   //Eq. (2.5)
                        double val = distance_to_leader*Math.exp(b*l)*Math.cos(l*2*Math.PI)+bestWhale.getValue(j);
                        aWhales[i].setValue(j, val);  
                    }
                    
                }    
            }
            cg_curve[t] = bestFitness;
            displayResult(t+1);
            t+=1;
            
        }//end while
       
       return bestWhale; 
    }
    
    
}
