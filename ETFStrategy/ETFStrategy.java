/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package etf.strategy;

import java.util.Scanner;

/**
 *
 * @author meehanb
 */
public class EtfStrategy {
        private static final Scanner scanner = new Scanner (System.in);

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int [] market_condition = {1,2,3,2,1,2,1,2,1,2,1,2,3,1,2,3,1,3,1,2,3,1,2,1,2,3};      



        double [] QQQ = {77.83,78.72,77.22,78.61,80.38,81.44,83.54,85.18,86.14,86.01,87.65,86.74,84.93,88.8,89.28,87.37,89.21,86.37, 91.00, 94.26, 94.59, 96.43, 98.52, 100.28, 99.05, 97.21};
        double [] TQQQ = {42.94,44.7,42.19,44.37,47.38,49.14,52.91,56.03,57.84,57.91,61.22,59.14,55.59,63.12,64.73,60.94,64.78,58.69, 67.67, 75.59, 75.91, 80.39, 85.43, 90.07, 87.43, 82.37};
        double [] SQQQ = {86.6,0,87.16,0,77.04,0,68,0,61.72,0,57.76,0,63.14,54.67,0,55.72,52.19,57.34, 47.83, 0, 41.70, 39.30, 0, 34.79, 0, 37.65};
        
        while(true){
        int strategy = 0;
        int upper_limit = market_condition.length;
        double cash = 10000;
        double num_shares = 0;
        double num_short = 0;
        double account_value = cash;
        
        System.out.println("Please enter 1,2,3 or 4 for a strategy to follow");
        strategy = scanner.nextInt();
        //strategy 1: QQQ
        //strategy 2: TQQQ
        //strategy 3: TQQQ/SQQQ fully short in corrections
        //strategy 4: TQQQ/SQQQ 50% short in corrections
        
        switch(strategy){
            case 1:
                for(int i = 0; i < upper_limit; i++){
                    switch(market_condition[i]){
                        case 1:
                            num_shares += cash/QQQ[i];
                            cash = 0;
                            break;
                        case 2:
                            num_shares *= 0.5; //sell half the shares
                            cash += (num_shares)*QQQ[i]; 
                            break;
                        case 3:
                            cash += (num_shares)*QQQ[i];
                            num_shares = 0;
                            break;
                        default:
                            break;
                    }
                    
                    account_value = cash + num_shares*QQQ[i]; 
                    System.out.println("account value:"+ account_value);
                }
                break;
            case 2:
                for(int i = 0; i < upper_limit; i++){
                    //System.out.println("num_shares: "+num_shares+" cash: "+cash + " TQQQ[i]: " + TQQQ[i]);
                    switch(market_condition[i]){
                        case 1:
                            num_shares += cash/TQQQ[i];
                            cash = 0;
                            break;
                        case 2:
                            num_shares *= 0.5; //sell half the shares
                            cash += (num_shares)*TQQQ[i]; 
                            break;
                        case 3:
                            cash += (num_shares)*TQQQ[i];
                            num_shares = 0;
                            break;
                        default:
                            break;
                    }
                    account_value = cash + num_shares*TQQQ[i];
                    System.out.println("account value:"+ account_value);
                }
                break;
            case 3:
                for(int i = 0; i < upper_limit; i++){
                    switch(market_condition[i]){
                        case 1:
                            cash +=(num_short)*SQQQ[i];
                            num_short = 0;
                            
                            num_shares += cash/TQQQ[i];
                            cash = 0;
                            break;
                        case 2:
                            num_shares *= 0.5; //sell half the shares
                            cash += (num_shares)*TQQQ[i]; 
                            break;
                        case 3:
                            cash += (num_shares)*TQQQ[i];
                            num_shares = 0;
                            
                            num_short += cash/SQQQ[i];
                            cash = 0;
                            break;
                        default:
                            break;
                    }
                    account_value = cash + num_shares*TQQQ[i] + num_short*SQQQ[i]; 
                    System.out.println("account value:"+ account_value);
                }
                break;
            case 4:
                for(int i = 0; i < upper_limit; i++){
                    switch(market_condition[i]){
                        case 1:
                            cash +=(num_short)*SQQQ[i];
                            num_short = 0;
                            
                            num_shares += cash/TQQQ[i];
                            cash = 0;
                            break;
                        case 2:
                            num_shares *= 0.5; //sell half the shares
                            cash += (num_shares)*TQQQ[i]; 
                            break;
                        case 3:
                            cash += (num_shares)*TQQQ[i];
                            num_shares = 0;
                            //System.out.println("cash: "+cash);
                            num_short += (0.5*cash)/SQQQ[i];
                            cash *= 0.5;
                            break;
                        default:
                            break;
                    }
                    //System.out.println("num_short: "+num_short);
                    account_value = cash + num_shares*TQQQ[i] + num_short*SQQQ[i];
                    System.out.println("account value:"+ account_value);
                }
                break;
            default:
                break;
        }

        
        System.out.printf("Your account value is: $%.2f\n", account_value);
    }
    }//end while loop
    
}
