
public class OmniAccount extends Account{
	
	/*
	 * The account constant values are place holders for this
	 * iteration where there is only a single user.
	 */
	static final int ACCOUNT_ID = 3;
	
    public OmniAccount(Customer customer){
    	super("Omni", ACCOUNT_ID, customer);
        this.failedTransactionFee = 10;
        this.interestRate = 4;
        this.overDraftAmount = 1000;
    }
	
	public double getInterestRate() {
		return this.interestRate;
	}
	
	public int getOverDraft() {
		return this.overDraftAmount;
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
				"Overdraft Limit $" + overDraftAmount + " ; Balance $" + balance;
		return details;
	}
}
