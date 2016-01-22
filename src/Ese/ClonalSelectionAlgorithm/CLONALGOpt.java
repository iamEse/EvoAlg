/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ese.ClonalSelectionAlgorithm;
import java.util.Random;
import Ese.FitnessFunction.AffinityFunction;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author erigha eseoghene dan
 */

public class CLONALGOpt {
    
    protected  double clonalFactor; // beta    
    protected  int antibodyPoolSize; // N    
    protected  int selectionPoolSize; // n    
    protected  int replacementSize; // d    
    protected  int maxGen; // Ngen    
    protected Antibody [] aAntibodyPool;
    protected Random rand = new Random();
    protected int dimension;
    private final double [] lb;  //Array of upper bound
    private final double [] ub;  //Array of lower bound
    
    
    public CLONALGOpt(double clonalFactor, int antibodyPoolSize,int selectionPoolSize, 
            int replacementSize,int numGenerations,int dimension){
        
        // set defaults
            lb = new double[dimension];
            ub = new double [dimension];
	    this.clonalFactor = clonalFactor; // beta
	    this.antibodyPoolSize = antibodyPoolSize; // N
	    this.selectionPoolSize = selectionPoolSize; // n
	    this.replacementSize = replacementSize; // d
	    this.maxGen = numGenerations;
            aAntibodyPool = new Antibody[antibodyPoolSize];
            this.dimension = dimension;
            
        //Execute the algorithm
         executeCLONALG();
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
    
    //Initialize Antibody pool
    private void initializeAntibodyPool(){
        
        init_bounds();
        
        double r;
        for(int i = 0; i < antibodyPoolSize; i++){
            
            //Initialize Antibody
            aAntibodyPool[i] = new Antibody(dimension);
            
            for(int j = 0; j < dimension; j++){
               r = formatNumber(rand.nextDouble());
               double value = formatNumber(r*(ub[j]-lb[j])+lb[j]); 
               aAntibodyPool[i].setValue(j,value);   //setValues for this Antibody dimension 
            }
        }
        
    }
    
    //Calculate Affinity for each Antibody   Note: Move this into the executeCLONALG method
    private Antibody [] calculateAffinity(Antibody [] aAntibody){
        
        for(Antibody antibody: aAntibody){
            
            double affinity = new AffinityFunction().getFitness(antibody);
            antibody.setAffinity(affinity);
            
        }
        
        return aAntibody;
    }
    
    //Select the set n of best Antibody
    private Antibody [] selectBestAntibodySet(Antibody [] aAntibodies){
        Antibody [] bestSet = new Antibody [selectionPoolSize];
        
        Arrays.sort(aAntibodies);  //Sort the antibodies in ascending order
        
        //Assign last antibody in aAntibodies to index 0 in the bestSet and continue in that order
        int lower = 0;
        for(int i = selectionPoolSize-1; i>=0; i--){
           bestSet[lower] = aAntibodies[i];
           lower++;
        }
        
       return bestSet; 
    }
    
    //Perform cloning and mutation
    private Antibody[] prepareCloneSet(Antibody [] aBestSet){
        
        //Number of clones is the same for optimization task
        int numOfClones = (int) Math.round(clonalFactor * antibodyPoolSize);
        
        ArrayList<Antibody> clones = new ArrayList<>(); //will contain clones
        
        for(int i = 0; i < aBestSet.length; i++){
            
            double mutationRate = (double)i+1 / aBestSet.length;
            
            for(int j = 0; j < numOfClones; j++){
                
                Antibody current = new Antibody(aBestSet[i]);  //create clone
                Antibody mutatedClone = mutateClone(current,mutationRate);
                clones.add(mutatedClone);
            }
              
        }
        
        return clones.toArray(new Antibody[clones.size()]); //Convert arraylist to array
    }
    
    //Mutation process
    private Antibody mutateClone(Antibody aClone,double mutationRate){
        double [] data = aClone.getAttributes();
        for(int i = 0; i < data.length; i++){
            
            double range = ub[i] - lb[i];
            range = (range*mutationRate);
            
            double min = Math.max(data[i]-(range/2.0), lb[i]);
            double max = Math.min(data[i] - (range/2.0), ub[i]);
            
            data[i] = formatNumber(min +(rand.nextDouble()* (max-min)));  //Actual mutation   
        }
        return new Antibody(data);
    }
    
    //Display results for each generation   
    private void displayResult(int numGen,Antibody aAntibody){
        System.out.println("GENERATION: "+numGen +"\t"+"Best Fitness: " +aAntibody.getAffinity());
       System.out.println();
       System.out.println("Antibody position");
       for(int i = 0; i < dimension; i++){
           
           System.out.print(aAntibody.getValue(i)+",");
       }
       
       System.out.println();
       System.out.println();
    }
    
    private void executeCLONALG(){
        initializeAntibodyPool();  //Randomly initialize antibody pool
        
        for(int t = 0; t < maxGen; t++){
            
           //calculate Affinity for all antibodies and return the updated array
            aAntibodyPool = calculateAffinity(aAntibodyPool);
            
            //Select best n Antibodies from the antibody pool;  note: aAntibody already sorted due to Arrays.sort() in selectBestAntibodySet
            Antibody [] bestSet = selectBestAntibodySet(aAntibodyPool);
            
            //Perform cloning and hypermaturation on the selected best antibodies
            Antibody [] cloneSet = prepareCloneSet(bestSet);
            
            //Calculate Affinities for the cloned set
            cloneSet = calculateAffinity(cloneSet);
            
     
            //Select n best clones; bestClones already in descending order
            Antibody [] bestClones = selectBestAntibodySet(cloneSet);
            
            //For optimization tasks only---
            //replace n anitbodies in the antibody pool with the bestCloned set
            replaceAntibodyPool(bestClones);
            
            //replace the d lowest antibodies in the antibody pool with random antibodies
            replaceLowestAntibodyPool();  
            
            //displayResults for each generation
            displayResult(t+1,aAntibodyPool[antibodyPoolSize-1]);
        }
        
    }
    
    //replace d lowest antibodies in the antibody pool
    private void replaceLowestAntibodyPool(){
       Arrays.sort(aAntibodyPool);
       double r;
       for(int i = 0; i < replacementSize; i++){
         
           for(int j = 0; j < dimension; j++){
               r = formatNumber(rand.nextDouble());
               double value = formatNumber(r*(ub[j]-lb[j])+lb[j]); 
               aAntibodyPool[i].setValue(j,value);   //setValues for this Antibody dimension    
           }   
       }  
    }
    
    //replaces n Antibody in the Antibody pool 
    private void replaceAntibodyPool(Antibody [] aBestClones){
        
        //Note: the best clones are in descending order
        //Note: antibody in sorted order
        int i,j;
        int upper = aAntibodyPool.length-1;
        
        for(i = 0; i < aBestClones.length; i++ ){
            
            //iterate from behind to compare the bestClones with best antibodies in antibody pool
            //Since the best antibody starts from the last antibody
            for(j = upper; j >= 0; j--){
                
                if(aAntibodyPool[j].getAffinity()< aBestClones[i].getAffinity()){
                    
                    aAntibodyPool[j] = aBestClones[i];
                    upper--;
                    break;
                }
                upper--;
            }
         
            if((upper+1) <= 0){
                break;
            }
        }    
    }
    
    /**
     * @return Returns the antibodyPoolSize.
     */
    public int getAntibodyPoolSize(){
        return antibodyPoolSize;
    }
    /**
     * @param antibodyPoolSize The antibodyPoolSize to set.
     */
    public void setAntibodyPoolSize(int antibodyPoolSize){
        this.antibodyPoolSize = antibodyPoolSize;
    }
    /**
     * @return Returns the clonalFactor.
     */
    public double getClonalFactor(){
        return clonalFactor;
    }
    /**
     * @param clonalFactor The clonalFactor to set.
     */
    public void setClonalFactor(double clonalFactor){
        this.clonalFactor = clonalFactor;
    }
    /**
     * @return Returns the numGenerations.
     */
    public int getNumGenerations(){
        return maxGen;
    }
    /**
     * @param numGenerations The numGenerations to set.
     */
    public void setNumGenerations(int numGenerations){
        this.maxGen = numGenerations;
    }
    
     public int getDimension(){
        return dimension;
    }
    /**
     * @param dim The number of dimensions (length of each B cell).
     */
    public void setDimension(int dim){
        this.dimension = dim;
    }
    
    /**
     * @return Returns the selectionPoolSize.
     */
    public int getSelectionPoolSize(){
        return selectionPoolSize;
    }
    /**
     * @param selectionPoolSize The selectionPoolSize to set.
     */
    public void setSelectionPoolSize(int selectionPoolSize){
        this.selectionPoolSize = selectionPoolSize;
    }
    /**
     * @return Returns the totalReplacement.
     */
    public int getReplacementSize(){
        return replacementSize;
    }
    
    public void setReplacementSize(int value){
        this.replacementSize = value;
    }
    
    
    
}
