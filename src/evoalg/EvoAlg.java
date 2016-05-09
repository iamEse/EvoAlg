/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evoalg;

import java.util.Arrays;

/**
 *
 * @author erigha eseoghene dan
 */
public class EvoAlg {

    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        boolean flag = false;
        int j;
        int upper = 9;
     do{
        for(j = upper; j >= 0; j--){
            
            if(j == 8){
                upper--;
                System.out.println(upper+" UPPER HERE BREAKING OUT");
                flag =true;
                break;
            }
            upper--;
            System.out.println(upper+"UPPER DIDNT BREAK OUT YET");
        }
        
        System.out.println(upper+"HERE: INSIDE DO LOOP");
        
     }while(flag = false);   
    }
    
}
