/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package retirementaccounts;


/**
 *
 * @author meehanb
 */

import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.Math;

public class RetirementAccounts {
private static final Scanner scanner = new Scanner (System.in);


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
        int accountIDCounter = 1;
        int answer = 0;
        
        do{
            String name;
            int empID;
            int age;
            String designation;
            
            //Employee emp1 = new Employee("Brett", 9288, 20, "Noob intern", 0);//initial salary doesn't matter since it is overwritten in the beginning of realTimeSimulation()
            System.out.println("Please enter your name:");
            name = scanner.nextLine();
            System.out.println("Please enter your employee ID:");
            empID = scanner.nextInt();
            System.out.println("Please enter your age:");
            age = scanner.nextInt();
            System.out.println("Please enter your designation:");
            designation = scanner.nextLine();//flush buffer (get rid of newline character)
            designation = scanner.nextLine();
            
            Employee emp1 = new Employee (name, empID, age, designation, 0);
            emp1.openInvestmentAccount(accountIDCounter++);
            emp1.open401k(accountIDCounter++, 0.6667, 0.06);
            emp1.openTraditionalIRA(accountIDCounter++);
            emp1.openRothIRA(accountIDCounter++);
            
            
//            System.out.println("Would you like to open a 401(k) account? Press 1 for YES or 0 for NO");
//            answer = scanner.nextInt();
//            if(answer == 1)
//                
//
//            System.out.println("Would you like to open a Traditional IRA account?");
//            answer = scanner.nextInt();
//            if(answer == 1)
//                
//
//            System.out.println("Would you like to open a Roth IRA?");
//            answer = scanner.nextInt();
//            if(answer == 1)
                

            System.out.println("Press 1 to start a Real Time Simulation. Press 2 to start a Projected Simulation. Press 3 to start an exportable Projected Simulation");
//            if(answer == 3)//need to flush buffer after case 3
//                answer = scanner.nextInt();
            answer = scanner.nextInt();
            switch(answer){
                case 1:  realTimeSimulation(emp1);//add checking/savings account?
                         break;
                case 2:  projectedSimulation(emp1, false);
                         break;
                case 3:  exportableProjectedSimulation(emp1);
                         break;
                default: System.out.println("Invalid number");
                         break;
            }
            System.out.println("Would you like to exit the program?\nPress 1 to exit, or 0 to restart");
            answer = scanner.nextInt();
            }while(answer == 0);



    }
    
    static public void realTimeSimulation(Employee emp){
        int year = 1;
        double taxableProfits = 0;
        
        
        
        
        
        int moveOn; //checks if user wants to continue or quit program (1/0 for true/false)
        do{
            System.out.print("\n\n\n\n\nPlease enter your current salary:\n$");
            double salary = scanner.nextDouble();
            emp.salary = salary;//update employee object
            System.out.print("Enter your monthly expenses:\n$");
            double expenses = 12*scanner.nextDouble();
            emp.expenses = expenses;//update employee object
            
            double contributableIncomeForTaxFreeAccounts = findMaxTaxFreeContributionAmount(emp, taxableProfits, true);//find optimal amount for a 401k contribution
            
            double contributableIncomeForTaxableAccounts = salary + taxableProfits - calculateTaxes(emp, taxableProfits) - expenses;
            
            System.out.println("Taxable: "+ salary + " + " + taxableProfits + " - " + calculateTaxes(emp, taxableProfits) + " - " + expenses);//////////////////////////////////////////////////
            System.out.printf("\nContributable Income for Tax Free Accounts: $%.2f\n", contributableIncomeForTaxFreeAccounts);
            System.out.printf("Contributable Income for Taxable Accounts: $%.2f\n", contributableIncomeForTaxableAccounts);
            System.out.println("salary: "+salary + "\ntaxableprofits: "+taxableProfits +"\ncalculatetaxes: "+ calculateTaxes(emp, taxableProfits) + "\nexpenses: "+expenses);///////////////////////////////////
            
            double totalIRAContributionsLeft = emp.rIRA.getContributionLimit();//can only contribute limit for all IRAs combined
            
            
            
            while(emp.kk401 != null){
                System.out.printf("\nHow much do you want to contribute to your 401(k)?($%.2f max)\n$", emp.kk401.getContributionLimit());
                emp.kk401Contribution = scanner.nextDouble();
                if(emp.kk401Contribution > emp.kk401.getContributionLimit() || emp.kk401Contribution > contributableIncomeForTaxFreeAccounts || emp.kk401Contribution > emp.salary)
                    System.out.println("Please enter an amount less than the contribution limit");
                else{
                    contributableIncomeForTaxFreeAccounts -= emp.kk401Contribution;
                    contributableIncomeForTaxableAccounts = salary + taxableProfits - emp.kk401Contribution - calculateTaxes(emp, taxableProfits) - expenses;//have to recalculate taxes to adjust for lower taxable income

                    System.out.println("Taxable: "+ salary + " + " + taxableProfits + " - " + calculateTaxes(emp, taxableProfits) + " - " + expenses);//////////////////////////////////////////////////
                    
                    System.out.printf("\nContributable Income for Tax Free Accounts: $%.2f\n", contributableIncomeForTaxFreeAccounts);
                    System.out.printf("Contributable Income for Taxable Accounts: $%.2f\n", contributableIncomeForTaxableAccounts); 
                    
                    break;
                }
            }
            checkAccountEligibility(emp);
            
            
            
            
            
            while(emp.tIRA != null && emp.calculateFederalAdjustedGrossIncome() <= emp.tIRA.INCOME_LIMIT){
                System.out.printf("\nHow much do you want to contribute to your Traditional IRA?($%.2f max for ALL IRAs combined)\n", emp.tIRA.getContributionLimit());
                System.out.printf("Total contributions left for all IRA accounts (combined): $%.2f\n$", totalIRAContributionsLeft);
                emp.tIRAContribution = scanner.nextDouble();
                System.out.println("emp.tIRAContribution: "+emp.tIRAContribution + " totalIRAContributionsLeft: "+totalIRAContributionsLeft+" contributableIncomeForTaxFreeAccounts: "+contributableIncomeForTaxFreeAccounts);
                if(emp.tIRAContribution > emp.tIRA.getContributionLimit() + 0.009 || emp.tIRAContribution > totalIRAContributionsLeft + 0.009 || emp.tIRAContribution > contributableIncomeForTaxFreeAccounts + 0.009){
                    System.out.println("Please enter an amount less than the contribution limit");
                    
//                    if(emp.tIRAContribution > emp.tIRA.getContributionLimit())
//                        //System.out.println("Problem 1");////////////////////////////
//                    if(emp.tIRAContribution > totalIRAContributionsLeft)
//                        //System.out.println("Problem 2");////////////////////////////
//                    if(emp.tIRAContribution > contributableIncomeForTaxFreeAccounts){
//                       // System.out.println("Problem 3");/////////////////////////////
//                        System.out.println(emp.tIRAContribution);
//                        System.out.println(contributableIncomeForTaxFreeAccounts);
//                    }
                }
                else{
                    //contributableIncomeForTaxFreeAccounts -= emp.tIRAContribution;
                    //System.out.println(salary +"-"+ emp.kk401Contribution +"-"+emp.tIRAContribution+"+"+taxableProfits+"-"+calculateTaxes(emp, taxableProfits)+"-" +expenses );
                    contributableIncomeForTaxableAccounts = salary + taxableProfits - emp.kk401Contribution- emp.tIRAContribution - calculateTaxes(emp, taxableProfits) - expenses;//have to recalculate taxes to adjust for lower taxable income
                    //contributableIncomeForTaxableAccounts = Math.round(contributableIncomeForTaxableAccounts * 100) / 100;
                    totalIRAContributionsLeft -= emp.tIRAContribution;
                    
                    //System.out.printf("\nContributable Income for Tax Free Accounts: $%.2f\n", contributableIncomeForTaxFreeAccounts);
                    System.out.printf("Contributable Income for Taxable Accounts: $%f\n", contributableIncomeForTaxableAccounts);
                    checkAccountEligibility(emp);
                    break;
                }
            }
            
            
            
   
            
            while(emp.rIRA != null && emp.calculateFederalAdjustedGrossIncome() <= emp.rIRA.INCOME_LIMIT){//takes into account income limit
                System.out.printf("\nHow much do you want to contribute to your Roth IRA?($%.2f max for ALL IRAs combined)\n", emp.rIRA.getContributionLimit());
                System.out.printf("Total contributions left for all IRA accounts (combined): $%.2f\n$", totalIRAContributionsLeft);
                emp.rIRAContribution = scanner.nextDouble();
                if(emp.rIRAContribution > emp.rIRA.getContributionLimit() + 0.009 || emp.rIRAContribution > totalIRAContributionsLeft + 0.009 || emp.rIRAContribution > contributableIncomeForTaxableAccounts + 0.009){
                    System.out.println("Please enter an amount less than the contribution limit");
                }
                else{
                    //contributableIncomeForTaxFreeAccounts -= emp.rIRAContribution;
                    //System.out.println(salary +"-"+ emp.kk401Contribution +"-"+emp.tIRAContribution+"+"+taxableProfits+"-"+calculateTaxes(emp, taxableProfits)+"-" +expenses );
                    contributableIncomeForTaxableAccounts -= emp.rIRAContribution;
                    //contributableIncomeForTaxableAccounts = Math.round(contributableIncomeForTaxableAccounts * 100) / 100;
                    totalIRAContributionsLeft -= emp.rIRAContribution;
                    
                    System.out.printf("Contributable Income for Taxable Accounts: $%.2f\n", contributableIncomeForTaxableAccounts);
                    break;
                }
            }
            
            contributableIncomeForTaxableAccounts -= taxableProfits;//prevent double counting of taxable investment profits (the money is already in the taxable investment account)
            
            //create deposit function for different accounts maybe
            if(contributableIncomeForTaxableAccounts > 0)//WHY IS IT PRINTING 0?_maybe rounding error
                System.out.printf("\nYour $%.2f leftover will be deposited into your investment account\n\n", contributableIncomeForTaxableAccounts);
            else if(contributableIncomeForTaxableAccounts < 0)//assuming balance is greater than 0
                System.out.println("\n$" + contributableIncomeForTaxableAccounts + " will be taken from your investment account to pay your bills\n\n");
            
            emp.mainAccountContribution = contributableIncomeForTaxableAccounts;
                
            

            taxableProfits = nextYear(emp, salary);
            System.out.printf("\nTaxable Profits: $%.2f\n", taxableProfits);
            //System.out.println("401kcont = "+ emp.kk401Contribution);
            
            System.out.println("End of year "+ year++ +"\n");
            System.out.println("Would you like to continue?\nPress 1 for yes or 0 for no");
            
            moveOn = scanner.nextInt();
        }while(moveOn == 1);
        
       
    }
    
    
    
    static public void projectedSimulation(Employee emp, Boolean export) throws FileNotFoundException, UnsupportedEncodingException{
        //
        int year = 0;
        double salaryIncrease = 0;
        Boolean salaryIncreaseIsPercent = true, expensesIncreaseIsPercent = true;
        double salary = 0;
        double initSalary = 0;
        double expenses = 0;
        double initExpenses = 0;
        double expensesIncrease = 0;
        double taxableProfits = 0;
        int max401k = 0;
        int maxrIRA = 0, maxtIRA = 0;//cannot both be true
        
        int restart = 0;
        int flag = 0;//for exported simulations-checks if info has been entered
        int permutation = -1;//set to -1 to prevent the do-while loop from continuing unless export==true and the program needs to run through all 6 permutations
        String permutationString = "";
        PrintWriter writer = new PrintWriter("dummy_file.json", "UTF-8");//open new json file to store year and net worth;
        
        if(export == true){
            writer = new PrintWriter("net_worth_data.json", "UTF-8");//open new json file to store year and net worth
            writer.printf("[");//starts at 0,0
            permutation = 1;//start testing different permutations
        }

        
        do{
            if(flag == 0 || export == false){
                System.out.print("\n\n\n\n\nPlease enter your current salary:\n$");
                salary = scanner.nextDouble();
                initSalary = salary;
                emp.salary = salary;//update employee object
                
                System.out.println("Enter your estimated yearly raise (% or $ eg. 0.05 or 1000):");
                salaryIncrease = scanner.nextDouble();
                if(salaryIncrease < 1)
                    salaryIncreaseIsPercent = true;
                else
                    salaryIncreaseIsPercent = false;
                
                System.out.print("Enter your monthly expenses:\n$");
                expenses = 12*scanner.nextDouble();
                initExpenses = expenses;
                emp.expenses = expenses;//update employee object
                
                System.out.println("Enter your estimated YEARLY increase in expenses (% or $ eg. 0.05 or 500):");
                expensesIncrease = scanner.nextDouble();
                if(expensesIncrease < 1)
                    expensesIncreaseIsPercent = true;
                else
                    expensesIncreaseIsPercent = false;
                
                if(export == false){
                    System.out.println("Do you want to max out your 401(k)? Press 1 for yes or 0 for no");
                    max401k = scanner.nextInt();
                    System.out.println("Do you want to max out your Roth IRA? Press 1 for yes or 0 for no");
                    maxrIRA = scanner.nextInt();
                    if(maxrIRA == 0){
                        System.out.println("Do you want to max out your Traditional IRA? Press 1 for yes or 0 for no");
                        maxtIRA = scanner.nextInt();
                    }
                    else
                        maxtIRA = 0;
                }


                System.out.println("Please enter the number of years you want to run this simulation for:");
                year = scanner.nextInt();
                
                flag = 1;//info has been entered
            }
            System.out.println("Begin");
            if(export == true){
                
                switch(permutation){
                    case 1:  max401k = 0;
                             maxrIRA = 0;
                             maxtIRA = 0;
                             permutationString = "None";
                             break;
                    case 2:  max401k = 0;
                             maxrIRA = 1;
                             maxtIRA = 0;
                             permutationString = "rIRA";
                             break;
                    case 3:  max401k = 0;//if AGI is over 59000, this will be the same as case 1
                             maxrIRA = 0;
                             maxtIRA = 1;
                             permutationString = "tIRA";
                             break;
                    case 4:  max401k = 1;
                             maxrIRA = 0;
                             maxtIRA = 0;
                             permutationString = "401k";
                             break;
                    case 5:  max401k = 1;
                             maxrIRA = 1;
                             maxtIRA = 0;
                             permutationString = "401k; rIRA";
                             break;
                    case 6:  max401k = 1;
                             maxrIRA = 0;
                             maxtIRA = 1;
                             permutationString = "401k; tIRA";
                             break;
                    default: System.out.println("Error choosing permutation");
                             break;
                }
                System.out.println("permutation: "+permutation);
                System.out.println("permutationString: " + permutationString);
                permutation++;
            }
            else
                System.out.println("Export apparently false");
            System.out.println("End");
            
            if(export == true)
                writer.printf("{\"seriesName\":\"%s\",\"pidPoints\":[[0,0],", permutationString);//starts at 0,0
            for(int i = 0; i < year; i++){


                double contributableIncomeForTaxFreeAccounts = findMaxTaxFreeContributionAmount(emp, taxableProfits, true);//find optimal amount for a 401k contribution


                double contributableIncomeForTaxableAccounts = salary + taxableProfits - calculateTaxes(emp, taxableProfits) - expenses;


                double totalIRAContributionsLeft = emp.rIRA.getContributionLimit();//can only contribute limit for all IRAs combined




                if(max401k == 1){//set the contribution as the minimum of: the 401(k) contribution limit, income left for tax-free accounts, and salary
                    double compare = Math.min(emp.salary, contributableIncomeForTaxFreeAccounts);
                    emp.kk401Contribution = Math.min(compare, emp.kk401.getContributionLimit());
                }
                else
                    emp.kk401Contribution = 0;

                contributableIncomeForTaxFreeAccounts -= emp.kk401Contribution;
                contributableIncomeForTaxableAccounts = salary + taxableProfits - emp.kk401Contribution - calculateTaxes(emp, taxableProfits) - expenses;//have to recalculate taxes to adjust for lower taxable income








                if(maxtIRA == 1 && emp.calculateFederalAdjustedGrossIncome() <= emp.tIRA.INCOME_LIMIT){//set the contribution as the minimum of: the Traditional IRA contribution limit, income left for tax-free accounts, and total contributions left to all IRA accounts combined
                    double compare = Math.min(emp.tIRA.getContributionLimit(), totalIRAContributionsLeft);
                    emp.tIRAContribution = Math.min(compare, contributableIncomeForTaxFreeAccounts);
                }
                else
                    emp.tIRAContribution = 0;

                contributableIncomeForTaxableAccounts = salary + taxableProfits - emp.kk401Contribution- emp.tIRAContribution - calculateTaxes(emp, taxableProfits) - expenses;//have to recalculate taxes to adjust for lower taxable income
                totalIRAContributionsLeft -= emp.tIRAContribution;






                if(maxrIRA == 1 && emp.calculateFederalAdjustedGrossIncome() <= emp.rIRA.INCOME_LIMIT){//set the contribution as the minimum of: the Roth IRA contribution limit, income left for tax-free accounts, and total contributions left to all IRA accounts combined
                    double compare = Math.min(emp.rIRA.getContributionLimit(), totalIRAContributionsLeft);
                    emp.rIRAContribution = Math.min(compare, contributableIncomeForTaxableAccounts);
                }
                else
                    emp.rIRAContribution = 0;

                contributableIncomeForTaxableAccounts -= emp.rIRAContribution;
                totalIRAContributionsLeft -= emp.rIRAContribution;


                double depositInInvestmentAccount = contributableIncomeForTaxableAccounts - taxableProfits;//prevent double counting of taxable investment profits since the money is already in the investment account
                emp.mainAccountContribution = depositInInvestmentAccount;

                //System.out.printf("Gross income: $%.2f/n", salary + taxableProfits);
                taxableProfits = nextYear(emp, salary);

                if(i < year - 1 && salary < 180000){//does not update salary after the last year; 180,000 salary cap
                    if(salaryIncreaseIsPercent == true)
                        salary = salary*(1 + salaryIncrease);//update salary for next year
                    else
                        salary = salary + salaryIncrease;
                    emp.salary = salary;
                    
                    if(expensesIncreaseIsPercent == true)
                        expenses = expenses*(1 + expensesIncrease);//update expenses for next year
                    else
                        expenses = expenses + expensesIncrease;
                    emp.expenses = expenses;
                }
                
                if(export == true){
                    writer.printf("[%d,%.2f]", i + 1, emp.netWorth);//saves year and net worth info
                    
                    if(i < year - 1){
                        writer.printf(",");
                    }
                }
                
                
                System.out.println("End of year "+ (i + 1) +"\n");
            }
            
            if(export == true){
                writer.printf("]}");
                if(permutation >= 1 && permutation <= 6)
                    writer.printf(",");
            }
            
            
            
            
            
            
            
            if(export == false){
                System.out.println("Would you like to restart the simulation?\nPress 1 for yes or 0 for no");
                restart = scanner.nextInt();
            }
            else if(permutation >= 1 && permutation <= 6)
                restart = 1;
            else
                restart = 0;
            
            //System.out.println("permutation: "+permutation);
            //System.out.println("restart: "+restart);
            if(restart == 1){//reset values
                int accountIDCounter = 1;

                Employee oldEmp = emp;
                emp = new Employee (oldEmp.name, oldEmp.employeeID, oldEmp.age - year, oldEmp.designation, 0);
                if(export == true){
                    emp.setSalary(initSalary);///////////////////////////CONFUSING-why am i using salary AND emp.salary??????????????????????????????????????????????????????????????????????
                    salary = initSalary;
                    emp.setExpenses(initExpenses);
                    expenses = initExpenses;
                }
                
                emp.openInvestmentAccount(accountIDCounter++);
                emp.open401k(accountIDCounter++, 0.6667, 0.06);
                emp.openTraditionalIRA(accountIDCounter++);
                emp.openRothIRA(accountIDCounter++);
                
                taxableProfits = 0;
            }
            //int wait = scanner.nextInt();
        }while(restart == 1);
        
        writer.printf("]");
        writer.close();//close JSON file
    }
    
    
    static public double nextYear(Employee emp, double salary){
        emp.setSalary(salary);
        
        double taxableProfits, throwaway;//do I need throwaway?
        throwaway = emp.kk401.calculateReturn(emp.kk401Contribution, salary);
        throwaway = emp.rIRA.calculateReturn(emp.rIRAContribution);
        throwaway = emp.tIRA.calculateReturn(emp.tIRAContribution);
        taxableProfits = emp.mainAccount.calculateReturn(emp.mainAccountContribution);
        taxableProfits = 0;//if investing in ETFs long-term
        double taxesDue = calculateTaxes(emp, taxableProfits);//stateTaxes + federalTaxes;
        emp.taxesDue = taxesDue;
        emp.totalTaxesPaid += taxesDue;
        emp.calculateEffectiveTaxRate();

        
        emp.updateNetWorth();
        emp.updateNetIncome();
        emp.printEmployee();
        
        //reset contributions for next year
        emp.kk401Contribution = 0;
        emp.rIRAContribution = 0;
        emp.tIRAContribution = 0;
        emp.mainAccountContribution = 0;
        
        emp.age++;
        
        return taxableProfits;
    }
    
    static public void exportableProjectedSimulation(Employee emp) throws FileNotFoundException, UnsupportedEncodingException{
        projectedSimulation(emp, true);
    }
    
    static public double calculateTaxes(Employee emp, double taxableProfits){//used to project future taxes
        emp.updateGrossIncome(taxableProfits);//assuming roughly the same returns as last year
        double projectedStateTaxes = emp.calculateStateTaxes("California");//emp.state??????
        //System.out.println("statetaxes: "+projectedStateTaxes);////////////////////////////////////////////////////////
        double projectedFederalTaxes = emp.calculateFederalTaxes(projectedStateTaxes);
        //System.out.println("federaltaxes: "+projectedFederalTaxes);//////////////////////
        double projectedTaxesDue = projectedStateTaxes + projectedFederalTaxes;
        
        return projectedTaxesDue;
    }
            

    static public void checkAccountEligibility(Employee emp){//lets user know ahead of time if he/she cannot contribute to an account
        double federalAGI = emp.calculateFederalAdjustedGrossIncome();
        if (federalAGI > emp.tIRA.INCOME_LIMIT)
            System.out.printf("\nYou can no longer contribute to a Traditional IRA due to having an adjusted gross income over $%.2f\n", emp.tIRA.INCOME_LIMIT);
        if(federalAGI > emp.rIRA.INCOME_LIMIT)
            System.out.printf("\nYou can no longer contribute to a Roth IRA due to having an adjusted gross income over $%.2f\n", emp.rIRA.INCOME_LIMIT);
    }//ADD MORE WARNINGS TO THIS METHOD
    
//    static public void printContributableIncome(double contributableIncomeForTaxFreeAccounts, double contributableIncomeForTaxableAccounts){
////        if(contributableIncomeForTaxFreeAccounts < 0)
////            contributableIncomeForTaxFreeAccounts = 0;
////        if (contributableIncomeForTaxableAccounts < 0)
////            contributableIncomeForTaxableAccounts = 0;
//        System.out.printf("\nContributable Income for Tax Free Accounts: $%.2f\n", contributableIncomeForTaxFreeAccounts);
//        System.out.printf("Contributable Income for Taxable Accounts: $%.2f\n", contributableIncomeForTaxableAccounts);                  
//    }
    
    /**
     * finds the Maximum Tax-free Contribution numerically
     * @param emp
     * @param taxableProfits
     * @param k401orTIRA
     * @return 
     */
    static public double findMaxTaxFreeContributionAmount(Employee emp, double taxableProfits, Boolean k401orTIRA){//true if testing 401k, false if testing Traditional IRA
        int index = 0;
        double [] stepValues = new double []{100000, 10000, 1000, 100, 10, 1, 0.1, 0.01, 0};//the closer to 0.01, the more accurate but less efficient//0 is at the end to prevent out of bounds error
        double step = stepValues[index];
        double afterTaxIncome = emp.salary + taxableProfits - emp.kk401Contribution - emp.tIRAContribution - calculateTaxes(emp, taxableProfits);
        double contribution = 0;
        double savekk401Contribution = emp.kk401Contribution;
        double savetIRAContribution = emp.tIRAContribution;
        
        if(afterTaxIncome < emp.expenses){
            System.out.println("Your expenses are greater than your after tax income, dummy!");
            System.out.println("AfterTaxIncome: $"+ afterTaxIncome);
            System.out.println("Expenses: $" + emp.expenses);
        }
        
        while(true){
            if(k401orTIRA == true){
                emp.kk401Contribution += step;
            }
            else{
                emp.tIRAContribution += step;
            }
            afterTaxIncome = emp.salary + taxableProfits - emp.kk401Contribution - emp.tIRAContribution - calculateTaxes(emp, taxableProfits);
            //System.out.println("contribution: "+emp.kk401Contribution + " afterTaxIncome: " + afterTaxIncome + " step: "+step);    
            if(afterTaxIncome - emp.expenses < 0.01 && afterTaxIncome - emp.expenses >= 0)//found optimal contribution with error <0.01
                break;
            if(afterTaxIncome < emp.expenses){//move back a step and adjust step to more precise amount
                if(k401orTIRA == true){
                emp.kk401Contribution -= step;
                }
                else{
                    emp.tIRAContribution -= step;
                }
                
                step = stepValues[++index];
            }
            
        }
        
        
        
        if(k401orTIRA == true){
                contribution = emp.kk401Contribution;
                //System.out.println("TaxFree: "+ emp.salary + " + " + taxableProfits + " - " +emp.kk401Contribution + " - " + emp.tIRAContribution + " - " + calculateTaxes(emp, taxableProfits) + " : " + emp.expenses);////////////////////////

                emp.kk401Contribution = savekk401Contribution;
            }
            else{
                contribution = emp.tIRAContribution;
                //System.out.println("TaxFree: "+ emp.salary + " + " + taxableProfits + " - " +emp.kk401Contribution + " - " + emp.tIRAContribution + " - " + calculateTaxes(emp, taxableProfits) + " : " + emp.expenses);////////////////////////                
                
                emp.tIRAContribution = savetIRAContribution;
            }
        
        
        return contribution;
    }
}
