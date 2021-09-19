import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract  class Account implements Serializable{

	protected int accountID;
    protected double balance;
    protected double interestRate;
    protected int failedTransactionFee;
    protected int overDraftAmount;
    protected String accountName;
    protected String lastTransaction;
    protected List<String> transactionList = new ArrayList<String>();
    protected Customer customer;
	
    
    public Account(String accountName, int accountID, Customer customer) {
    	this.accountName = accountName;
    	this.accountID = accountID;
    	this.customer = customer;
    	this.balance = 0;
    	if (customer.getIsBankEmployee()) {
    		this.failedTransactionFee = failedTransactionFee / 2;
    	}
    }
    
    public int getAccountID() {
    	return accountID;
    }
    
    public double getBalance(){
        return balance;
    }
    
    public String getAccountName() {
    	return accountName;
    }
    
    public String getLastTransaction() {
    	return lastTransaction;
    }

    /*
     * Return false if there are not 
     * sufficient funds.
     */
    public boolean withdrawalPerformed(double amount){
        if (amountAvailable(amount)){
            balance -= amount;
            return true;
        }
        return false;
    }

    public void deposit(double amount){
        balance += amount;
    }
    
    public void addInterest(double amount) {
    	balance += amount;
    }
    
    public abstract String getLatestAccountDetails();
    
    public abstract void updateAccountDetails(String transaction, double amount);
    
    protected String getLatestTransactionDetails(String lastTransaction) {
    	String editedTransaction = lastTransaction.replace("\t", " ");
    	String details = accountName + ";  " + editedTransaction + ";  " + "balance $" + balance;
    	return details;
    }
    
    // Check if the requested amount is available
    private boolean amountAvailable(double withdrawalAmount){
        double limit = balance + overDraftAmount;

        // Apply fee if the withdrawal exceeds available funds
        if (withdrawalAmount > limit){
            balance -= failedTransactionFee;
            return false;
        }
        return true;
    }
}
