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
public class k401 extends Account{
    double employerMatch = 0;// decimal percent
    double employerLimit = 0;// decimal percent

    public k401(String owner, int ownerID, int accountID, double growthRate, double employerMatch, double employerLimit) {
        super(owner, ownerID, accountID, growthRate);
        this.setTaxDeductible(true);
        this.setAccountType("401(k)");
        this.setContributionLimit(17500);
        this.employerMatch = employerMatch;
        this.employerLimit = employerLimit;
    }
    /**
     * Documentation for the function below
     */
    public void k401printAccountInfo(){
        printAccountInfo();
        System.out.println("Employer Match: " + employerMatch);
    }
    
    public double calculateReturn(double currentYearContribution, double salary){
        //assert(currentYearContribution <= this.getContributionLimit() || this.getContributionLimit() == -1);
        addToTotalDeposits(currentYearContribution);
        
        double monthlyContribution;//make this more accurate-see SSL HR site
        if(currentYearContribution <= salary*employerLimit)
            monthlyContribution = (currentYearContribution + currentYearContribution*employerMatch)/12;
        else
            monthlyContribution = (currentYearContribution + salary*employerLimit*employerMatch)/12;
        
        
        double monthlyGrowthRate = this.getGrowthRate()/12;
        double startingBalance = this.getBalance();
        for (int i = 0; i < 12; i++){
            this.setBalance(this.getBalance()*(1 + monthlyGrowthRate) + monthlyContribution );
        }
        
        
        return this.getBalance() - (startingBalance + currentYearContribution);//returns profit
    }
    
}
