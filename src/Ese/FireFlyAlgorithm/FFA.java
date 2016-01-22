/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Ese.FireFlyAlgorithm;
import Ese.FitnessFunction.FireFlyFitnessFunction;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author erigha eseoghene dan
 */


public class FFA {
    
    private final Random rand;  //Random number generator
    private final int n; // number of fireflies
    private final int maxGen; // number of generations
    private double alpha; //alpha
    private final double betamin; //betamin
    private final double gamma; //Absorption coefficent
    private final int dimension; //problem dimension
    private final double [] lb;  //Array of upper bound
    private final double [] ub;  //Array of lower bound
    private final double [] u0;
    private double fbest; //best fitness value at current generation (Light Intensity)
    private FireFly bestFireFly; //best firefly//Solution
            
    protected FireFly [] aFireFlies;
    
    public FFA(int n, int maxGen,double alpha,double betamin,double gamma,int dimension ){
        this.n = n;
        this.maxGen = maxGen;
        this.betamin = betamin;
        this.gamma = gamma;
        this.alpha = alpha;
        this.dimension = dimension;
        aFireFlies = new FireFly[n];
        lb = new double[dimension];
        ub = new double [dimension];
        u0 = new double [dimension];
        rand = new Random();
        executeFFA();  //executes the firefly algorithm
    }
    
    //Set Limits
    private void findLimits(int i){
        int k;
        
        for(k = 0; k < dimension; k++){
            
            if(aFireFlies[i].getValue(k) < lb[k]){
                aFireFlies[i].setValue(k, lb[k]);
            }
            if(aFireFlies[i].getValue(k) > ub[k]){
                aFireFlies[i].setValue(k,ub[k]);
            }
        }
    }
    
    //Update alpha
    private void alpha_new(double aAlpha,int NGen){
        double delta;  //delta parameter
	delta = 1.0 - Math.pow((Math.pow(10.0, -4)/0.9),(1/(double)NGen));
	alpha = (1-delta)*aAlpha; 
    }
    
    //Initialize upper and lower bounds
    private void init_bounds(){
        
       for(int i = 0; i < dimension; i++){
		lb[i] = 1.0;
		ub[i] = 3.0;
	} 
    }
    
    //Format Number
    private double formatNumber(double num){
      DecimalFormat myFormat = new DecimalFormat("##.##");
      String outVar = myFormat.format(num);
      return Double.parseDouble(outVar);
    }
    
    
    //Sort fireflies according to fitness
    private void sort_ffa(){
      Arrays.sort(aFireFlies);  
    }
    
    
    //Initial Random guess
    private void initialRandomGuess(){
        for(int i = 0; i < dimension; i++){
           double rNum = rand.nextDouble();
           u0[i] = formatNumber(lb[i]+(ub[i]-lb[i])*rNum);
        } 
    }
    
    
    //Display results for each generation
    private void displayResults(int numGen,FireFly aFireFly){
       System.out.println("GENERATION: "+numGen +"\t"+"Best Fitness: " +aFireFly.getLightIntensity());
       System.out.println();
       System.out.println("FireFly position");
       for(int i = 0; i < dimension; i++){
           
           System.out.print(aFireFly.getValue(i)+",");
       }
       
       System.out.println();
       System.out.println();
    }
    
    
    //Initialize firefly
    private void init_ffa(){
        int i,j;
	double r;
        
        //initialize upper and lower bounds
	init_bounds();
	
        //initial Random guess
        initialRandomGuess();
        
	//Initial location of fireflies
        for(i =0; i < n; i++){
            aFireFlies[i] = new FireFly(dimension);
            
            for(j = 0; j < dimension; j++){
              
                r = formatNumber(rand.nextDouble());
              
                double value = formatNumber(r*(ub[j]-lb[j])+lb[j]);
                
                aFireFlies[i].setValue(j,value);   //setValues for each firefly agent's dimension
            }
            aFireFlies[i].setLightIntensity(1.0);  //initialize each firefly's light intensity to 1.0
        }
        
    }
    
    //Execute the algorithm
    private void executeFFA(){
        int t = 0;
        init_ffa();
        
        while(t < maxGen){
            
            //This line of reducing alpha gradually is optional
            //alpha_new(alpha,maxGen);
            
            
            //Evaluate new solutions
            for(int i =0; i < n; i++){
                
                double fitness = new FireFlyFitnessFunction().getFitness(aFireFlies[i]);
                aFireFlies[i].setLightIntensity(fitness);
            }
            
            
            //Rank Fireflies based on light intensity; the last is the best
            sort_ffa();
            
            //Select current best firefly
            bestFireFly = aFireFlies[n-1];  //the last firefly
            
            /*
            NOTE: OPTIMIZATION TASKS
            1. Maximization problems: The last firefly after sorting is the current best firefly
            2. Minimization problems: The first firefly after sorting is the current best firefly
            */
                        
            //display intermediate results
            displayResults(t+1,bestFireFly);
            
            //move all fireflies to the better locations
            move_ffa();
            
            t++;  //move to nextGeneration
        }
        
        
        
    }
    
    //Move fireflies to new locations
    private void move_ffa(){
      int i,j,k;
      double scale;
      double r,beta;
      
      FireFly [] copyFireFlies = new FireFly[aFireFlies.length];
      System.arraycopy(aFireFlies,0,copyFireFlies,0,aFireFlies.length);   //create a copy of the original firefly array
      
      for(i=0; i < n; i++){
          
          
          for(j=0; j<n; j++){
              
              
              r = 0.0;
             
              for(k = 0; k < dimension; k++){
                  
                   // Euclidean distance computation
                  r+=(aFireFlies[i].getValue(k) - aFireFlies[j].getValue(k))*
                      (aFireFlies[i].getValue(k) - aFireFlies[j].getValue(k));    
              }//end for k
              
              r = Math.sqrt(r);  //Square root to determine the euclidean distance
              
              
              //update firefly movement
              if(aFireFlies[j].getLightIntensity() > copyFireFlies[i].getLightIntensity()){  //Brighter and more attractive
                 double beta0 = 1.0;
                 beta = (beta0-betamin)* Math.exp((-gamma)*Math.pow(r, 2.0))+ betamin;
                 
                 for(k=0; k < dimension; k++){
                     
                     scale = Math.abs(ub[k] - lb[k]);
                     double rNum = rand.nextDouble();
                     double tmpf = alpha*(rNum-0.5)*scale;
                     double value = formatNumber(aFireFlies[i].getValue(k)*(1.0-beta)+ 
                             copyFireFlies[j].getValue(k)*beta+tmpf);
                     aFireFlies[i].setValue(k,value); //update value
                 }//end for k
                  
              }//end if
                
          } //end for j
      
          findLimits(i);
      } //end for i
      
    } //end move_ffa 
    
}
