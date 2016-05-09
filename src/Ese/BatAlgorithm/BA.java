/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ese.BatAlgorithm;
import Ese.FitnessFunction.BatFitnessFunction;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Random;
import net.sf.javaml.core.Dataset;

/**
 *
 * @author erigha eseoghene dan
 */
public class BA{
    
    private final Random rand;
    private final int n; //Population size
    private final int maxGen; //Number of Generations
    private final double a;  //Loudness
    private final double r; //pulse rate
    private final int dimension;  //dimension of solution
    private final int fMax = 2;  //maximum frequency
    private final int fMin = 0;  //minimum frequency
    private final double [] lb;
    private final double [] ub;
    private final double [] f;  //Frequencies for the bats
    private final double [][] v;  //Velocities for the bats
    protected  Bat [] aBats;
    
    private Bat bestBat;   //Best Bat/Solution
    private double bestFitness; //Best fitness for each generation
    Dataset train;
    Dataset test;
    
    public BA(int n, int maxGen,double a, double r, int dimension, Dataset train, Dataset test){
        this.n = n;
        this.maxGen = maxGen;
        this.a = a;
        this.r = r;
        this.dimension = dimension;
        lb = new double[dimension];
        ub = new double [dimension];
        f = new double [n];
        v = new double [n][dimension];
        aBats = new Bat[n];
        rand = new Random();
        this.train = train;
        this.test = test;
        
       
    }
    
    //Initialize the lower and upper bounds
    private void init_bounds(){
         for(int i = 0; i < dimension; i++){
             
             if(i == 0){
                lb[i] = 1.0;
		ub[i] = 3500.0; 
             }
             
             if(i == 1){
                lb[i] = 0.001;
		ub[i] = 50.0;  
             }
		
	} 
        
    }
    
    //Initialize frequency and velocity
    private void init_arrays(){
        
        //Initialize the frequencies
        for(int i = 0; i < n; i++){
            f[i] = 0.0;
        }
        
        //Initialize the velocity
        for(int i = 0; i < n; i++){
            
            for(int j = 0; j < dimension; j++){
                
                v[i][j] = 0.0;
            }
        }    
    }
   
    //Initialize the populations and solutions
    private void init_population(){
        
        for(int i = 0; i < n; i++){
            
            aBats[i] = new Bat(dimension);
            for(int j = 0; j < dimension; j++){
                
                double rNum = rand.nextDouble();
                double value = formatNumber(lb[j]+(ub[j]-lb[j])*rNum);
                aBats[i].setValue(j,value);   //setValues for each bat agent   
            }
            
             //double fitness = new BatFitnessFunction().getFitness(aBats[i]);  
             double fitness = new BatFitnessFunction().getFitness(aBats[i], train, test);
             aBats[i].setFitness(fitness);
        }
        
    }
    
    //Format Number
    private double formatNumber(double num){
      DecimalFormat myFormat = new DecimalFormat("##.##");
      String outVar = myFormat.format(num);
      return Double.parseDouble(outVar);
    }
    
    //Limit values to lower and upper bound
    private double [][] simpleBounds(double [][] arr,int i){
      
          for(int j =0; j < dimension; j++){
              
              if(arr[i][j] < lb[j]){
                  arr[i][j] = lb[j];
               }
              
              if(arr[i][j] > ub[j]){
                  arr[i][j] = ub[j];
               }
            }
        
      
      return arr;  
    }
    
    //Display results for each generation   
    private void displayResult(int numGen,Bat aBat){
        System.out.println("GENERATION: "+numGen +"\t"+"Best Fitness: " +aBat.getFitness());
       System.out.println();
       System.out.println("Bat position");
       for(int i = 0; i < dimension; i++){
           
           System.out.print(aBat.getValue(i)+",");
       }
       
       System.out.println();
       System.out.println();
    }
    
    public Bat executeBA(){
        
        //initialize bounds
        init_bounds();
        
        //initialize arrays
        init_arrays();
        
        //initialize population
        init_population();
        
        //Find initial Best Solution
        Arrays.sort(aBats);
        bestBat = aBats[n-1];  //Best is determined by the objective function
        bestFitness = aBats[n-1].getFitness();   //Best fitness as produced by the best bat
         
        int t = 0;
        
        
        //BEGIN PROCESSING
        while(t < maxGen){
          
            //initialize s(i,:)
            double [][] s = new double[n][dimension];
            
            int i,j;
            //loop over all bats/solutions
            for(i = 0; i< n; i++){
                f[i] = formatNumber(fMin +(fMax-fMin)*rand.nextDouble());
                
                for(j = 0; j < dimension; j++){
                  v[i][j] = formatNumber(v[i][j] + (aBats[i].getValue(j)- bestBat.getValue(j)) * f[i]);
                  s[i][j] = formatNumber(aBats[i].getValue(j)+ v[i][j]);
                } //end for j
                 
                //Pulse rate
                if(rand.nextDouble() > r){
                    // The factor 0.001 limits the step sizes of random walks 
                    for(j=0; j< dimension; j++){
                        double rNum = rand.nextGaussian();
                        
                        s[i][j] = formatNumber(bestBat.getValue(j)+0.001*1-2*rNum);
                    }
                } //end if
                
                //Apply simplebounds/limit
                s = simpleBounds(s,i);
                
                Bat bat = new Bat(s[i]);
                
                //Evaluate new solutions
                //double fNew = new BatFitnessFunction().getFitness(bat);
                double fNew = new BatFitnessFunction().getFitness(bat, train, test);
                
                //Update if the solution improves, or not too loud
                if((fNew >= aBats[i].getFitness()) && (rand.nextDouble() < a)){
                   
                    for(j = 0; j < dimension; j++){
                       aBats[i].setValue(j, s[i][j]); // Sol(i,:)=S(i,:);
                    }
                    aBats[i].setFitness(fNew);
                } //end if
                
                
                //Update the current best solution
                if(fNew >= bestFitness){
                    
                    for(j = 0; j < dimension; j++){
                        bestBat.setValue(j, s[i][j]);
                    }
                    bestFitness = fNew;
                    bestBat.setFitness(bestFitness);
                }//end if
               
            }// end for ith bat
            
            t++;
            
            displayResult(t,bestBat); 
        }//end while
    
       return bestBat; 
    }
}
