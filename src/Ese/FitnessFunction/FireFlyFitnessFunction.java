/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Ese.FitnessFunction;

import java.text.DecimalFormat;
import Ese.FireFlyAlgorithm.FireFly;

/**
 *
 * @author erigha eseoghene dan
 */
public class FireFlyFitnessFunction implements AbstractFitnessFunction{
 
    private double quadraticSolver(double x, double y){
        double val1 = x*x;
        return formatNumber(val1+y);
    }
    
    private double formatNumber(double num){
      DecimalFormat myFormat = new DecimalFormat("##.##");
      String outVar = myFormat.format(num);
      return Double.parseDouble(outVar);
    }

    @Override
    public double getFitness(Object o) {
        FireFly aFireFly = (FireFly)o;
        double x = formatNumber(aFireFly.getValue(0));
        double y = formatNumber(aFireFly.getValue(1));
        return quadraticSolver(x,y);
    }
}
