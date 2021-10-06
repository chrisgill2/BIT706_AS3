
public class EveryDayAccount extends Account {
	
	static final int ACCOUNT_ID = 1;
	String lastTransaction;
	
    public EveryDayAccount(Customer customer) {
    	super ("EveryDay", ACCOUNT_ID, customer);
        this.interestRate = 0;
        this.failedTransactionFee = 0;
        this.overDraftAmount = 0;
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
