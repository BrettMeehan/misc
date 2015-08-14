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
public class TraditionalIra extends Account{
    double INCOME_LIMIT = 59000;

    public TraditionalIra(String owner, int ownerID, int accountID, double growthRate) {
        super(owner, ownerID, accountID, growthRate);
        this.setTaxDeductible(true);
        this.setAccountType("Traditional IRA");
        this.setContributionLimit(5500);
    }
    
}
