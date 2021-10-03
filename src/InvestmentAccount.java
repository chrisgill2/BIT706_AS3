

public class InvestmentAccount extends Account {
	
	/*
	 * The account constant values are place holders for this
	 * iteration where there is only a single user.
	 */
	
	static final int ACCOUNT_ID = 2;

    public InvestmentAccount(Customer customer) {
    	super("Investment", ACCOUNT_ID, customer);
        this.failedTransactionFee = 4;
        this.overDraftAmount = 0;
        this.interestRate = 4;
    }

    public double getInterestRate() {
    	return this.interestRate;
    }
    
    public int getFailedTransactionFee() {
    	if (customer.getIsBankEmployee()) {
    		return failedTransactionFee / 2;
    	}
    	return failedTransactionFee;
    }
    
    @Override
    public void updateAccountDetails(String transaction, double amount) {
    	lastTransaction = transaction + "$" + amount;
    }


	@Override
	public String getLatestAccountDetails() {
		String details = accountName + accountID + "; " + "Interest Rate " + interestRate +  "%; " +
				 " ; Balance $" + balance;
		return details;
	}
}
