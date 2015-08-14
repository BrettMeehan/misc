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
public class RothIra extends Account{
    double INCOME_LIMIT = 114000;
    
    public RothIra(String owner, int ownerID, int accountID, double growthRate) {
        super(owner, ownerID, accountID, growthRate);
        //this.setTaxDeductible(false);
        this.setAccountType("Roth IRA");
        this.setContributionLimit(5500);
    }
    
}

