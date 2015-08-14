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
public class Employee{
   double RATE_OF_RETURN = 0.04;
   double AGGRESSIVE_RATE_OF_RETURN = 0.04;
    
   String name;
   int employeeID;
   int age;
   String designation;
   double salary;
   double grossIncome;
   double netIncome;//create functions
   double expenses;
   double taxesDue = 0;
   double totalTaxesPaid = 0;
   double effectiveTaxRate = 0;
   double netWorth;
   
   RothIra rIRA = null;
   double rIRAContribution = 0;
   
   TraditionalIra tIRA = null;
   double tIRAContribution = 0;
   
   k401 kk401 = null;
   double kk401Contribution = 0;
   
   InvestmentAccount mainAccount = null;
   double mainAccountContribution = 0;
	
   // This is the constructor of the class Employee
   public Employee(String name, int employeeID, int age, String designation, double salary){
      assert(salary >= 0 && age >= 0);
      this.name = name;
      this.employeeID = employeeID;
      this.age = age;
      this.designation = designation;
      this.salary = salary;
   }
   
//   //Copy constructor
//   public Employee(Employee emp){
//      assert(salary >= 0 && age >= 0);
//      this.name = emp.name;
//      this.employeeID = emp.employeeID;
//      this.age = emp.age;
//      this.designation = emp.designation;
//      this.salary = emp.salary;
//      this.grossIncome = emp.grossIncome;
//      this.netIncome = emp.netIncome;//create functions
//      this.expenses = emp.expenses;
//      this.totalTaxesPaid = 0;
//      double netWorth;
//
//      RothIra rIRA = null;
//      double rIRAContribution = 0;
//
//      TraditionalIra tIRA = null;
//      double tIRAContribution = 0;
//
//      k401 kk401 = null;
//      double kk401Contribution = 0;
//
//      InvestmentAccount mainAccount = null;
//      double mainAccountContribution = 0;
//   }
   // Assign the age of the Employee  to the variable age.
   public void setAge(int empAge){
       assert(empAge >= 0);
      age =  empAge;
   }
  
   /* Assign the designation to the variable designation.*/
   public void setDesignation(String empDesig){
      designation = empDesig;
   }
   /* Assign the salary to the variable	salary.*/
   public void setSalary(double empSalary){
       assert(salary >= 0);
       salary = empSalary;
   }
   
   public void setExpenses(double amount){
       expenses = amount;
   }
   /* Print the Employee details */
   public void printEmployee(){
      System.out.println("Name:"+ name );
      System.out.println("Employee ID: " + employeeID);
      System.out.println("Age:" + age );
      System.out.println("Designation:" + designation );
      System.out.printf("Salary: $%.2f\n", salary);
      System.out.printf("Expenses: $%.2f\n", expenses);
      System.out.printf("Gross Income: $%.2f\n", grossIncome);
      System.out.printf("Net Income: $%.2f\n", netIncome);
      System.out.println("");
      mainAccount.printAccountInfo();
      System.out.println("");
      rIRA.printAccountInfo();
      System.out.println("");
      tIRA.printAccountInfo();
      System.out.println("");
      kk401.k401printAccountInfo();
      System.out.println("");
      System.out.printf("\nYou owe: $%.2f in total taxes\n\n", taxesDue);
      System.out.printf("Effective tax rate: %.2f %%\n\n", effectiveTaxRate);
      System.out.printf("NET WORTH: $%.2f\n", netWorth);
      System.out.printf("Total taxes paid: $%.2f\n\n\n", totalTaxesPaid);
   }
   public void openInvestmentAccount(int idGenerator){
       if (mainAccount == null)
        mainAccount = new InvestmentAccount(name, employeeID, idGenerator, AGGRESSIVE_RATE_OF_RETURN);
       else
           System.out.println("You already have a taxable investment account open");
   }
   public void openRothIRA(int idGenerator){
       if (rIRA == null)
        rIRA = new RothIra(name, employeeID, idGenerator, AGGRESSIVE_RATE_OF_RETURN);
       else
           System.out.println("You already have a Roth IRA open");
   }
   
   public void openTraditionalIRA(int idGenerator){
       if (tIRA == null)
        tIRA = new TraditionalIra(name, employeeID, idGenerator, AGGRESSIVE_RATE_OF_RETURN);
       else
           System.out.println("You already have a Traditional IRA open");
   }
   
   public void open401k(int idGenerator, double employerMatch, double employerLimit){
       if (kk401 == null)
           kk401 = new k401(name, employeeID, idGenerator, RATE_OF_RETURN, employerMatch, employerLimit);
       else
           System.out.println("You already have a 401(k) open");
   }
   public void updateGrossIncome(double mainAccountProfits){
       grossIncome = salary + mainAccountProfits;
   }
   public double getGrossIncome(){
       return grossIncome;
   }
   
   public void updateNetIncome(){
       netIncome = grossIncome - taxesDue - expenses;//should I exclude 401k & tIRA contributions?
   }
   public double getNetIncome(){
       return netIncome;
   }
   
   /**
    * Make sure taxesDue and grossIncome are up to date before calculating
    */
   public void calculateEffectiveTaxRate(){
       effectiveTaxRate = 100*taxesDue/grossIncome;
   }
   
   public void updateNetWorth(){
       netWorth = mainAccount.getBalance() + rIRA.getBalance() + tIRA.getBalance() + kk401.getBalance();
   }
   
   public double getNetWorth(){
       return netWorth;
   }
   
   public double calculateStateTaxes(String state){
       //for CALIFORNIA
       double STANDARD_STATE_DEDUCTION = 3906;//1000 if dependent
       int NUM_BRACKETS = 9;
       double [][] taxBrackets = new double[][]{{7582, 0.01}, {17976, 0.02}, {28371, 0.04}, {39384, 0.06}, {49774, 0.08}, {254250, 0.093}, {305100, 0.103}, {508500, 0.113}, {-1, 0.123}};
       //double gIncome = salary;//+investments
       double adjustedGrossIncomeState = calculateFederalAdjustedGrossIncome() - STANDARD_STATE_DEDUCTION;
       if(adjustedGrossIncomeState < 0)//prevent negative taxes
           adjustedGrossIncomeState = 0;
       //System.out.println("test calculation: " + calculateFederalAdjustedGrossIncome());//////////////////////
       double totalStateTaxes = 0;
      // System.out.println("totalStateTaxes: "+totalStateTaxes);////////////////////////////////////
       double lowerLimit = 0;
       for (int i = 0; i < NUM_BRACKETS; i++){
           if(adjustedGrossIncomeState <= taxBrackets[i][0] || taxBrackets[i][0] == -1){//accounts for highest bracket (no upper limit)
               totalStateTaxes += (adjustedGrossIncomeState-lowerLimit)*taxBrackets[i][1];
               //System.out.println("totalStateTaxes: "+totalStateTaxes);////////////////////////////////////
               //System.out.println("adjustedGrossIncomeState: "+adjustedGrossIncomeState);////////////////////////////////////
               break;
           }
           else
               totalStateTaxes += (taxBrackets[i][0]-lowerLimit)*taxBrackets[i][1];
           //System.out.println("Total state taxes ("+i+"): $" + totalStateTaxes);
           lowerLimit = taxBrackets[i][0];//update the lower bracket limit
       }
       //System.out.println("HELLOOOOOOOOOOOOOOOOOOOOOO");///////////////////////////////////////////////
       return totalStateTaxes;
   }
   
   public double calculateFederalTaxes(double stateTaxes){
       //for singles
       double STANDARD_FEDERAL_DEDUCTION = 6200;
       int NUM_BRACKETS = 7;
       double [][] taxBrackets = new double[][]{{9075, 0.1},{36900, 0.15},{89350, 0.25}, {186350, 0.28}, {405100, 0.33}, {406750, 0.35}, {-1, 0.396}};//-1 means no upper limit
       //double income = salary;//+investments
       double adjustedGrossIncome = calculateFederalAdjustedGrossIncome();
       
       if(stateTaxes > STANDARD_FEDERAL_DEDUCTION)
           adjustedGrossIncome -= stateTaxes;//deduct state taxes
       else
           adjustedGrossIncome -= STANDARD_FEDERAL_DEDUCTION;//otherwise, just take standard deduction
       
       if(adjustedGrossIncome < 0)//prevents negative taxes
           adjustedGrossIncome = 0;
       
       double totalFederalTaxes = 0;
       double lowerLimit = 0;
       for (int i = 0; i < NUM_BRACKETS; i++){
           if(adjustedGrossIncome <= taxBrackets[i][0] || taxBrackets[i][0] == -1){//accounts for highest bracket (no upper limit)
               totalFederalTaxes += (adjustedGrossIncome-lowerLimit)*taxBrackets[i][1];
               break;
           }
           else
               totalFederalTaxes += (taxBrackets[i][0]-lowerLimit)*taxBrackets[i][1];
           lowerLimit = taxBrackets[i][0];//update the lower bracket limit
       }
       
       return totalFederalTaxes;
   }
   
   public double calculateFederalAdjustedGrossIncome(){
       //System.out.println("gross Income: "+grossIncome);//////////////////////////////////////////////
       //System.out.println("401kContribution: "+kk401Contribution);////////////////////////////////////////////
       //System.out.println("tIRA Contribution: "+tIRAContribution);/////////////////////////////////////////
       return grossIncome - kk401Contribution - tIRAContribution;
   }
}
