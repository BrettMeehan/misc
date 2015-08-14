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
public class Account {
    private String owner;
    private int ownerID;
    private int accountID;
    private String accountType;
    private double balance = 0;
    private double totalDeposits = 0;
    private Boolean taxDeductible = false;
    private double maxContribution = -1;//-1 means no max contribution
    //private double currentYearContribution = 0;
    private double growthRate;
    
    
    // This is the constructor of the class Employee
    public Account(String owner, int ownerID, int accountID, double growthRate){
       this.owner = owner;
       this.ownerID = ownerID;
       this.accountID = accountID;
       this.growthRate = growthRate;
       
    }
    
    /*
    public void setAccountID (int value){
        accountID = value;
    }*/
    
    public int getAccountID (){
        return accountID;
    }
    
    
    public void setAccountType(String type){
        accountType = type;
    }
    public String getAccountType(){
        return accountType;
    }
    
    
    public void setBalance(double amount){
        balance = amount;
    }
    public double getBalance(){
        return balance;
    }
    
    public void addToTotalDeposits(double amount){
        totalDeposits += amount;
    }
    public double getTotalDeposits(){
        return totalDeposits;
    }
    
    
    public void setTaxDeductible(Boolean value){
        taxDeductible = value;
    }
    public Boolean isTaxDeductible(){
        return taxDeductible;
    }
    

    public double getContributionLimit(){
        return maxContribution;
    }
    
    public void setContributionLimit(double amount){
        maxContribution = amount;
    }

    
    public void setGrowthRate(double amount){
        growthRate = amount;
    }
    public double getGrowthRate(){
        return growthRate;
    }
    
    
    public void printAccountInfo(){
        System.out.println("Account Type: " + accountType);
        System.out.println("Account Owner: " + owner);
        System.out.println("Owner ID#: " + ownerID);
        System.out.println("Account ID#: " + accountID);
        System.out.printf("Account Balance: $%.2f\n", balance);
        System.out.printf("Total deposits: $%.2f\n", totalDeposits);
        System.out.println("Tax deductible: " + taxDeductible);
        System.out.println("Maximum Contribution Amount (-1 is unlimited): $" + maxContribution);
    }
    
    public double calculateReturn(double currentYearContribution){
        assert(currentYearContribution <= maxContribution || maxContribution == -1);
        addToTotalDeposits(currentYearContribution);
        
        double monthlyContribution = currentYearContribution/12;
        double monthlyGrowthRate = growthRate/12;
        double startingBalance = balance;
        for (int i = 0; i < 12; i++){
            balance *= (1 + monthlyGrowthRate);
            balance += monthlyContribution;
        }
        
        
        return balance - (startingBalance + currentYearContribution);
    }
}
