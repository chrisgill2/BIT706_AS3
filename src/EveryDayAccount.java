
public class EveryDayAccount extends Account {
	
//	final int failedTransactionFee = 0;
	static final int INTEREST_RATE = 0;
	static final int OVERDRAFT_AMOUNT = 0;
	static final int ACCOUNT_ID = 1;
	static final String ACCOUNT_NAME = "EveryDay";
	String lastTransaction;
	
    public EveryDayAccount(Customer customer) {
    	super (ACCOUNT_NAME, ACCOUNT_ID, customer);
        this.interestRate = INTEREST_RATE;
        this.failedTransactionFee = 0;
        this.overDraftAmount = OVERDRAFT_AMOUNT;
        this.lastTransaction = "";
    }
    
    public int getFailedTransactionFee() {
    	return failedTransactionFee;
    }
    
    @Override
    public void updateAccountDetails(String transaction, double amount) {
		lastTransaction = transaction+ "$" + amount;
    }

	@Override
	public String getLatestAccountDetails() {
		String details = accountName + " " + accountID;
		details += "; Balance $" + balance;
		return details;
	}
}
