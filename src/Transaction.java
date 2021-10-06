

import java.text.DecimalFormat;
import java.util.ArrayList;

import org.eclipse.swt.widgets.List;

public class Transaction {
	
	Account account;
	private static DecimalFormat df2 = new DecimalFormat("#.##");
	private boolean interestAddedSuccessIndicator = false;
	private boolean transferSuccessIndicator = false;
	private double interestEarned = 0.0;
	CustomerFile customerFile = new CustomerFile();
	
	
	public Transaction(Account account){
		this.account = account;
		if (account.transactionList == null) {
			account.transactionList = new ArrayList<String>();
		}
	}

	
	public boolean getinterestAddedSuccessIndicator() {
		return interestAddedSuccessIndicator;
	}
	
	public boolean getTransferSuccessIndicator() {
		return transferSuccessIndicator;
	}
	
	public void recordDeposit(double depositAmount) {
		// Don't record the transaction if there was an invalid amount
		if (depositAmount == 0) {
			return;
		}
		
		String depositAmountString = df2.format(depositAmount);
		account.deposit(depositAmount);
		
		String transaction = "Deposit       $" + depositAmountString;
		account.transactionList.add(transaction);
		customerFile.writeToFile(Customer.customerList);
		
	}
	
	public void performWithdrawal(double withdrawalAmount) throws FailedWithdrawalException {
		double transactionAmount = withdrawalAmount;
		String transaction = "Withdrawal       $" + withdrawalAmount;
		String failedWithdrawalMessage = "Insufficient funds in " + account.accountName + " account.";
		
		// Update the account details if withdrawal was successful
		boolean sufficientFunds = account.withdrawalPerformed(withdrawalAmount);

		if (!sufficientFunds) {
			transaction = "Failed Transaction    $" + account.getFailedTransactionFee();
			account.updateAccountDetails("Failed Transaction	", account.getFailedTransactionFee());
			account.transactionList.add(account.lastTransaction);
			customerFile.writeToFile(Customer.customerList);
			throw new FailedWithdrawalException(failedWithdrawalMessage);
		} else {
			account.updateAccountDetails("Withdrawal", transactionAmount);
		}
		account.transactionList.add(transaction);
		customerFile.writeToFile(Customer.customerList);
	}
	
	public void addInterestToAccount() {
		double interestAmount;
		String interestAmountString;

		// Omni Account interest calculation
		if (account.accountName.equals("Omni")) {
			interestAmount = calculateOmniInterest();
			interestAmountString = df2.format(interestAmount);
			if (interestAmount > 0) {
				account.addInterest(interestAmount);
				account.updateAccountDetails("Interest       ", interestAmount);
				String transaction = "Interest       $" + interestAmountString;
				account.transactionList.add(transaction);
				customerFile.writeToFile(Customer.customerList);
				interestAddedSuccessIndicator = true;
				return;
			}
			return;
		}

		// Investment Account interest calculation
		interestAmount = calculateInvestmentInterest();
		interestAmountString = df2.format(interestAmount);
		
		if (interestAmount > 0) {
			account.addInterest(interestAmount);
			account.updateAccountDetails("Interest       ", interestAmount);
			String transaction = "Interest       $" + interestAmountString;
			account.transactionList.add(transaction);
			customerFile.writeToFile(Customer.customerList);
			interestAddedSuccessIndicator =  true;
		} 
	}
	
	public void transferBetweenAccounts(Account toAccount, double transferAmount) {
		try {
			// Withdraw money from the account being transferred from
			performWithdrawal(transferAmount);
			
			// Deposit money in the account being transferred to
			this.account = toAccount;
			recordDeposit(transferAmount);
			transferSuccessIndicator = true;
		} catch (FailedWithdrawalException e) {
			transferSuccessIndicator = false;
		}
	}
	
    /**
	 *  Calculates interest for
	 *  the investment account.
     */
    private double calculateInvestmentInterest(){
    	if (account.balance > 0) {
	        interestEarned = account.balance * account.interestRate / 100;
    	}
        return interestEarned;
}
	
    /**
	 *  Interest will be calculated for the Omni account if
	 *  the balance exceeds $1000.
     */
    private double calculateOmniInterest(){
        // Add interest if balance exceeds 1000
        if (account.balance > 1000){
            interestEarned = account.balance * account.interestRate / 100;
        }
        return interestEarned;
    }
	
	public List addTransactionToList(List transactionList) {
		if(account.transactionList != null && account.transactionList.size() > 0) {
			for (String transaction :account.transactionList) {
				if (transaction != null){
					transactionList.add(transaction);
				}
			}
		}
		return transactionList;
	}
}
