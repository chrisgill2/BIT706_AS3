

public class OmniAccount extends Account{
	
	/*
	 * The account constant values are place holders for this
	 * iteration where there is only a single user.
	 */
	static final int ACCOUNT_ID = 3;
	static final String ACCOUNT_NAME = "Omni";
	
    public OmniAccount(Customer customer){
    	super(ACCOUNT_NAME, ACCOUNT_ID, customer);
        this.failedTransactionFee = 10;
        this.interestRate = 10;
    }
	
	public void setInterestRate(int interestRate) {
			this.interestRate = interestRate;
	}
	
	public double getInterestRate() {
		return this.interestRate;
	}
	
	public void setOverDraft(int overDraftAmount) {
		this.overDraftAmount = overDraftAmount;
	}
	
	public int getOverDraft() {
		return this.overDraftAmount;
	}
	
    public int getFailedTransactionFee() {
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
