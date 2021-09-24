

public class InvestmentAccount extends Account {
	
	/*
	 * The account constant values are place holders for this
	 * iteration where there is only a single user.
	 */
	
	static final int OVERDRAFT_AMOUNT = 0;
	static final int ACCOUNT_ID = 2;
	static final String ACCOUNT_NAME = "Investment";

    public InvestmentAccount(Customer customer) {
    	super(ACCOUNT_NAME, ACCOUNT_ID, customer);
        this.failedTransactionFee = 10;
        this.overDraftAmount = OVERDRAFT_AMOUNT;
        this.setInterestRate(10);
    }
    
    public void setInterestRate(int interestRate) {
    	this.interestRate = interestRate;
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
