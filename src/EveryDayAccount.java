

public class EveryDayAccount extends Account {
	
	static final int FAILED_TRANSACTION_FEE = 0;
	static final int INTEREST_RATE = 0;
	static final int OVERDRAFT_AMOUNT = 0;
	static final int ACCOUNT_ID = 1;
	static final String ACCOUNT_NAME = "EveryDay";
	
    public EveryDayAccount(Customer customer) {
    	super (ACCOUNT_NAME, ACCOUNT_ID, customer);
        this.interestRate = INTEREST_RATE;
        this.failedTransactionFee = FAILED_TRANSACTION_FEE;
        this.overDraftAmount = OVERDRAFT_AMOUNT;
    }
    
    @Override
    public void updateAccountDetails(String transaction, double amount) {
		EveryDayAccountDetails everyDayAccountDetails = EveryDayAccountDetails.getInstance();
		EveryDayAccountDetails.balance = this.balance;
		everyDayAccountDetails.lastTransaction = transaction+ "$" + amount;
		String t = "t";
    }

	@Override
	public String getLatestAccountDetails() {
		String details = accountName + " " + accountID;
		details += "; Balance $" + EveryDayAccountDetails.balance;
		return details;
	}
}
