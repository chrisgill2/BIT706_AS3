

public class OmniAccount extends Account{
	
	/*
	 * The account constant values are place holders for this
	 * iteration where there is only a single user.
	 */
	static final int FAILED_TRANSACTION_FEE = 10;
	static final int ACCOUNT_ID = 3;
	static final String ACCOUNT_NAME = "Omni";
	
    public OmniAccount(Customer customer){
    	super(ACCOUNT_NAME, ACCOUNT_ID, customer);
        this.failedTransactionFee = FAILED_TRANSACTION_FEE;
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
    
    @Override
    public void updateAccountDetails(String transaction, double amount) {
    	// Reduce the overdraft if the failed transaction fee exceeds the balance
		OmniAccountDetails omniAccountDetails = OmniAccountDetails.getInstance();
		OmniAccountDetails.balance = this.balance;
		omniAccountDetails.overDraftAmount = this.overDraftAmount;
		omniAccountDetails.lastTransaction = transaction + "$" + amount;
    }

	@Override
	public String getLatestAccountDetails() {
		String details = accountName + accountID + "; " + "Interest Rate " + interestRate +  "%; " +
				"Overdraft Limit $" + overDraftAmount + " ; Balance $" + OmniAccountDetails.balance;
		return details;
	}
}
