

public class InvestmentAccount extends Account {
	
	/*
	 * The account constant values are place holders for this
	 * iteration where there is only a single user.
	 */
	static final int FAILED_TRANSACTION_FEE = 10;
	static final int OVERDRAFT_AMOUNT = 0;
	static final int ACCOUNT_ID = 2;
	static final String ACCOUNT_NAME = "Investment";

    public InvestmentAccount(Customer customer) {
    	super(ACCOUNT_NAME, ACCOUNT_ID, customer);
        this.failedTransactionFee = FAILED_TRANSACTION_FEE;
        this.overDraftAmount = OVERDRAFT_AMOUNT;
    }
    
    public void setInterestRate(int interestRate) {
    	this.interestRate = interestRate;
    }

    public double getInterestRate() {
    	return this.interestRate;
    }
    
    @Override
    public void updateAccountDetails(String transaction, double amount) {
		InvestmentAccountDetails investmentAccountDetails = InvestmentAccountDetails.getInstance();
		InvestmentAccountDetails.balance = this.balance;
		investmentAccountDetails.lastTransaction = transaction + "$" + amount;
    }


	@Override
	public String getLatestAccountDetails() {
		String details = accountName + accountID + "; " + "Interest Rate " + interestRate +  "%; " +
				 " ; Balance $" + InvestmentAccountDetails.balance;
		return details;
	}
}
