/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package spyreturns;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author meehanb
 */
public class SPYReturns {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
//        BufferedReader br = new BufferedReader(new FileReader("C:/Users/meehanb/Downloads/HistoricalQuotes.csv"));
//        String line;
//        ArrayList<Double> MonthlyCloses = new ArrayList<>();
//        
//        String lastMonth = "", currentMonth = "";
//        
//        
//        
//        
//        
//        line = br.readLine();//skip first title line
//        
//        while((line = br.readLine()) != null){
//            String[] tokenizedLine = line.split(",");
//            currentMonth = tokenizedLine[0].replaceFirst("/.*", "");
//            if(!currentMonth.equals(lastMonth))
//                MonthlyCloses.add(Double.parseDouble(tokenizedLine[1]));
//            
//            lastMonth = currentMonth;
//        }
//        Collections.reverse(MonthlyCloses);//arrange monthly closes in correct chronological order
//        
//        System.in.read();
//        double account = 0;
//        double monthlyContribution = 0, totalContributions = 0;
//        double[] yearCloses = new double[12];
//        double ftwHigh;
//        double percentOffHigh;
//        int index = 0;
//        
//        for(int i = 1; i < MonthlyCloses.size(); i++){
//            account *= (MonthlyCloses.get(i)/MonthlyCloses.get(i - 1) + 0.02);//capital appreciation + 2% dividend
//            
//            System.out.println("\n" + account);
//            System.out.println(totalContributions);
//            System.out.println(MonthlyCloses.get(i)/MonthlyCloses.get(i - 1) + 0.02 - 1);
//            
//            yearCloses[(index++)%12] = MonthlyCloses.get(i);
//            ftwHigh = arrayMax(yearCloses);
//            
//            percentOffHigh = (ftwHigh - MonthlyCloses.get(i))/ftwHigh;
////            System.out.println(percentOffHigh);
//            if(percentOffHigh < 0.05)
//                monthlyContribution = 1600;
//            else if(percentOffHigh >= 0.05 && percentOffHigh < 0.1)
//                monthlyContribution = 3200;
//            else if(percentOffHigh >= 0.1 && percentOffHigh < 0.2)
//                monthlyContribution = 6400;
//            else if(percentOffHigh >= 0.2 && percentOffHigh < 0.3)
//                monthlyContribution = 12800;
//            else if(percentOffHigh >= 0.4)
//                monthlyContribution = 25600;
//            
//            account += monthlyContribution;
//            totalContributions += monthlyContribution;
//            
//        }
//        
//        System.out.println("Account value: " + account);
//        System.out.println("Total Contributions: " + totalContributions);
//        System.out.println("Annualized total return: " + (Math.pow(account/totalContributions, 12.0/MonthlyCloses.size()) - 1));
//        System.out.println("S&P 500 total return: " + (Math.pow((Math.pow(1.02, MonthlyCloses.size()/12.0) + MonthlyCloses.get(MonthlyCloses.size() - 1)/MonthlyCloses.get(0) - 1), 12.0/MonthlyCloses.size()) - 1));
//        
        
        
        
        
        
        
        
        
        
        
        
        
        
        BufferedReader br = new BufferedReader(new FileReader("C:/Users/meehanb/Downloads/HistoricalQuotes.csv"));
        String line;
        ArrayList<Double> DailyCloses = new ArrayList<>();
        
        String lastMonth = "", currentMonth = "";
        
        
        
        
        
        line = br.readLine();//skip first title line
        
        while((line = br.readLine()) != null){
            String[] tokenizedLine = line.split(",");
            DailyCloses.add(Double.parseDouble(tokenizedLine[1]));
        }
        Collections.reverse(DailyCloses);//arrange monthly closes in correct chronological order
        
        
        double shares = 0;
        double monthlyContribution = 0, totalContributions = 0;
        double[] yearCloses = new double[252];
        double ftwHigh;
        double percentOffHigh;
        int index = 0;
        int flag1 = 0, flag2 = 0, flag3 = 0, flag4 = 0;
        double lastMonthValue = 1;
        
        for(int i = 0; i < DailyCloses.size(); i++){
            yearCloses[(index++)%252] = DailyCloses.get(i);
            ftwHigh = arrayMax(yearCloses);

            percentOffHigh = (ftwHigh - DailyCloses.get(i))/ftwHigh;
//            System.out.println(percentOffHigh);
            
//            //every day
//            if(percentOffHigh >= 0.05 && percentOffHigh < 0.1 && flag1 == 0){
//                flag1 = 1;
//                shares += 3200/DailyCloses.get(i);
//                totalContributions += 3200;
//            }
//            else if(percentOffHigh >= 0.1 && percentOffHigh < 0.2 && flag2 == 0){
//                flag2 = 1;
//                shares += 6400/DailyCloses.get(i);
//                totalContributions += 6400;
//            }
//            else if(percentOffHigh >= 0.2 && percentOffHigh < 0.3 && flag3 == 0){
//                flag3 = 1;
//                shares += 12800/DailyCloses.get(i);
//                totalContributions += 12800;
//            }
//            else if(percentOffHigh >= 0.4 && flag4 == 0){
//                flag4 = 1;
//                shares += 25600/DailyCloses.get(i);
//                totalContributions += 25600;
//            }
                        
                        
            if(i % 20 == 0){//every month
                //if(flag1 == 0 && flag2 == 0 && flag3 == 0 && flag4 == 0){
                    if(percentOffHigh < 0.05)
                        monthlyContribution = 1600;
                    else if(percentOffHigh >= 0.05 && percentOffHigh < 0.1)
                        monthlyContribution = 3200;
                    else if(percentOffHigh >= 0.1 && percentOffHigh < 0.2)
                        monthlyContribution = 6400;
                    else if(percentOffHigh >= 0.2 && percentOffHigh < 0.3)
                        monthlyContribution = 12800;
                    else if(percentOffHigh >= 0.4)
                        monthlyContribution = 25600;
                    shares += monthlyContribution/DailyCloses.get(i);
                    totalContributions += monthlyContribution;
                //}
                
                //reset flags
                flag1 = 0;
                flag2 = 0;
                flag3 = 0;
                flag4 = 0;
                
                System.out.println("\n" + shares*DailyCloses.get(i));
                System.out.println(totalContributions);
                System.out.println(monthlyContribution);
                System.out.println((DailyCloses.get(i)/lastMonthValue - 1) + "\n");
                
                lastMonthValue = DailyCloses.get(i);
            }
            
            
            if(i%252 == 0){//every year
                shares *= (1.02);//dividend
            }
            
        }
        double account = shares*DailyCloses.get(DailyCloses.size() - 1);
        
        System.out.println("Account value: " + account);
        System.out.println("Total Contributions: " + totalContributions);
        System.out.println("Annualized total return: " + (Math.pow(account/totalContributions, 252.0/DailyCloses.size()) - 1));
        System.out.println("S&P 500 total return: " + (Math.pow((Math.pow(1.02, DailyCloses.size()/252.0) + DailyCloses.get(DailyCloses.size() - 1)/DailyCloses.get(0) - 1), 252.0/DailyCloses.size()) - 1));
        
    
    
    
    }
    
    public static double arrayMax(double[] array){
        double ans = array[0];
        for(int i = 1; i < array.length; i++){
            ans = Math.max(ans, array[i]);
        }
        return ans;
    }
    
}
