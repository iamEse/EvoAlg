/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ese.BinaryBatAlgorithm;
import Ese.BatAlgorithm.Bat;
import Ese.Dataset.LoadDataset;
import Ese.FitnessFunction.BinaryBatFitnessFunction;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import net.sf.javaml.core.Dataset;

/**
 *
 * @author erigha eseoghene dan
 */
public class BBA {
    
    private final Random rand;
    private final int n; //Population size
    private final int maxGen; //Number of Generations
    private final double a;  //Loudness
    private final double r; //pulse rate
    private final int dimension;  //dimension of solution
    private final int fMax = 2;  //maximum frequency
    private final int fMin = 0;  //minimum frequency
    private final double [] f;  //Frequencies for the bats
    private final double [][] v;  //Velocities for the bats
    protected  BinaryBat [] aBats;
    protected BinaryBat bestBat;
    protected double [] cg_curve;
    private double bestFitness; //Best fitness for each generation
    
    Dataset train;
    Dataset test;
    LoadDataset dataset;
    private static final String trainPath = "C:\\Users\\erigha eseoghene dan\\Documents\\PAPER\\NSLKDD\\Random\\KDDTRAINTRAIN.txt";
    private static final String testPath = "C:\\Users\\erigha eseoghene dan\\Documents\\PAPER\\NSLKDD\\Random\\KDDTRAINTEST.txt";

    
    public BBA(int n, int maxGen,double a, double r, int dimension) throws IOException{
        
        this.n = n;
        this.maxGen = maxGen;
        this.a = a;  //loudness
        this.r = r;  //pulse
        this.dimension = dimension;
        f = new double [n];
        v = new double [n][dimension];
        cg_curve = new double[maxGen];
        aBats = new BinaryBat[n];
        rand = new Random();
        dataset = new LoadDataset(trainPath, testPath);
        test = dataset.getTestData();
        train = dataset.getTrainingData();
    }
    
    
    //Initialize frequency and velocity
    private void init_arrays(){
        
        //Initialize the frequency , velocity and Binary bats
        for(int i = 0; i < n; i++){
            
            f[i] = 0.0;  //set frequency
            
            aBats[i] = new BinaryBat(dimension);
            
            for(int j = 0; j < dimension; j++){
                
                v[i][j] = 0.0;   //set velocity
                aBats[i].setValue(j,0);   //setValues for each bat agent  
            }
        }
      
    }
    
    //initialize cg_curve
    private void init_cg_curve(){
        for(int i = 0; i < maxGen; i++){
            
            cg_curve[i] = 0.0;
        }
    }
    
    
    //initialize populations/solutions
    public void init_population(){
        
        BinaryBatFitnessFunction bf = new BinaryBatFitnessFunction();
        
        System.out.println("INITIALIZATION");
        for(int i = 0; i < n; i++){
            
            for(int j = 0; j < dimension; j++){
                
                double rNum = rand.nextDouble();
                if(rNum <= 0.5){
                   aBats[i].setValue(j,0);   //setValues for each bat agent  
                }else{
                    aBats[i].setValue(j,1);   //setValues for each bat agent  
                }
            }
            
            double fitness = bf.getFitness(aBats[i],train,test);  
            aBats[i].setFitness(fitness);
            Bat bat = bf.getBat();
            aBats[i].setCost(bat.getValue(0));
            aBats[i].setGamma(bat.getValue(1));
            //printBat(aBats[i]);
            
        }
        
        
    }
    
    
    public void printBat(BinaryBat bat){
        
        //Get the attributes of the bat in binary
         int [] attributes = bat.getAttributes();
         
         
         //retrieve positions with 0 bit value
         for(int k = 0; k < attributes.length; k++){
              
             System.out.print(attributes[k] +" ");
             
         }
            System.out.println("\t"+ bat.getFitness());
            System.out.print("COST: "+bat.getCost() +"\t"+ "GAMMA: "+bat.getGamma());
            System.out.println();
            System.out.println();
    }
    
    //Format Number
    private double formatNumber(double num){
      DecimalFormat myFormat = new DecimalFormat("##.##");
      String outVar = myFormat.format(num);
      return Double.parseDouble(outVar);
    }
    
    
     //Display results for each generation   
    private void displayResult(int numGen,BinaryBat aBat){
        System.out.println("BINARY BAT GENERATION: "+numGen +"\t"+"Best Fitness: " +aBat.getFitness());
        System.out.println();
        System.out.println("Bat position");
        for(int i = 0; i < dimension; i++){
           
           System.out.print(aBat.getValue(i)+",");
        }
       
       System.out.print("COST: "+aBat.getCost() +"\t"+ "GAMMA: "+aBat.getGamma());
       System.out.println();
       System.out.println();
    }
    
    private void find_best_bat(){
        
        double highestFitness = getHighestFitness();
        ArrayList<BinaryBat> fittestBats = getBatsWithHighestFitness(highestFitness);
        bestBat = getBestBatWithreducedFeatureSet(fittestBats);
        bestFitness = bestBat.getFitness();
    }
    
    private BinaryBat getBestBatWithreducedFeatureSet(ArrayList<BinaryBat> fitBats){
        
        BinaryBat batBest = new BinaryBat(dimension);
        Set<Integer> indices = new HashSet<>();
        
        for(BinaryBat bat: fitBats){
            
            Set<Integer> batIndices = new HashSet<>();
            int [] attributes = bat.getAttributes();
            
            //retrieve positions with 0 bit value
            for(int i = 0; i < attributes.length; i++){
              
              if(attributes[i] == 0){
                  batIndices.add(i);
                }
            }
            
            if(batIndices.size() > indices.size()){
               
                indices = batIndices;
                batBest = bat;
            }
            
        }
        
        return batBest;
    }
    
    private ArrayList<BinaryBat> getBatsWithHighestFitness(double fitnessValue){
        ArrayList<BinaryBat> myBats = new ArrayList<>();
        
        for(BinaryBat bat : aBats){
            
            if(bat.getFitness() == fitnessValue){
                myBats.add(bat);
            }
        }
        
        return myBats;
    }
    
    private double getHighestFitness(){
        double highestFitness = 0.0;
        
        //get highest fitness value
        for(BinaryBat bat: aBats){
            if(bat.getFitness() > highestFitness){
                
                highestFitness = bat.getFitness();
            }
        }
        
        return highestFitness;
    }
    
    private boolean isReducedFeatures(BinaryBat current, BinaryBat best){
        boolean flag = false;
  
        int [] currentAttrib = current.getAttributes();
        Set<Integer> currentIndices = getIndices(currentAttrib);
        
        int [] bestAttrib = best.getAttributes();
        Set<Integer> bestIndices = getIndices(bestAttrib);
        
        if(currentIndices.size() > bestIndices.size()){
            flag = true;
        }
        
        return flag;
    }
    
    private Set<Integer> getIndices(int [] attrib){
        
        Set<Integer> currentIndices = new HashSet<>();
        //retrieve positions with 0 bit value
            for(int i = 0; i < attrib.length; i++){
              
             //System.out.print(attributes[i] +" ");
              if(attrib[i] == 0){
                  currentIndices.add(i);
                }
            }
            
            return currentIndices;
    }
    
    public BinaryBat execute(){
        
        init_arrays();
        init_population();
        init_cg_curve();
        
        
        //Find current best Solution
      
        find_best_bat();
        //System.out.println("BEST BAT");
        //printBat(bestBat);
      
         //Start the iteration-Binary Bat Algorithm
        int t = 0;
        
        while(t < maxGen){
            
           
            double [][] s = new double[n][dimension];
            
            //Loop over all bats/solutions
            int i,j;
            cg_curve[t] = bestFitness;
            
            for( i = 0; i < n; i++){
                
                for( j = 0; j < dimension; j++){
                    
                   f[i] = formatNumber(fMin +(fMax-fMin)*rand.nextDouble());
                    
                   v[i][j] = formatNumber(v[i][j] + (aBats[i].getValue(j)- bestBat.getValue(j)) * f[i]);
                         
                   double v_shaped_transfer = Math.abs((2/Math.PI) * Math.atan((Math.PI/2) * v[i][j]));
                         
                   double rNum = rand.nextDouble();
                         
                    if(rNum < v_shaped_transfer){
                             
                       //flip the attribute j
                       int k = aBats[i].getValue(j);
                            
                       if(k == 0){
                           aBats[i].setValue(j,1);
                        }else{
                          aBats[i].setValue(j,0);   
                        }         
                    }
                         
                    rNum = rand.nextDouble();
                    if(rNum > r){
                       aBats[i].setValue(j,bestBat.getValue(j));
                    }
                } //end for j
                
                //Evaluate new solutions
                BinaryBatFitnessFunction bf = new BinaryBatFitnessFunction();
                
                double fNew = bf.getFitness(aBats[i],train,test);
                
                //Update if the solution improves, or not too loud
                if((fNew >= aBats[i].getFitness()) && (rand.nextDouble() < a)){
                    aBats[i].setFitness(fNew);
                    Bat bat = bf.getBat();
                    aBats[i].setCost(bat.getValue(0));
                    aBats[i].setGamma(bat.getValue(1));
                    
                } //end if
               
            
                //Update the current best solution
                if(fNew > bestFitness ||((fNew == bestFitness) && isReducedFeatures(aBats[i],bestBat))){
                    
                    for(j = 0; j < dimension; j++){
                        bestBat.setValue(j, aBats[i].getValue(j));
                    }
                    bestFitness = fNew;
                    bestBat.setFitness(bestFitness);
                    bestBat.setCost(aBats[i].getCost());
                    bestBat.setGamma(aBats[i].getGamma());
                    
                }//end if
                
            } //end for i
            
            t++;
            displayResult(t,bestBat); 
            
        } //end while
        
        return bestBat;
    }
    
    
    }
