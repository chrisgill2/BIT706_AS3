

import java.text.DecimalFormat;
import org.eclipse.swt.widgets.List;

public class Transaction {
	
	Account account;
	private static DecimalFormat df2 = new DecimalFormat("#.##");
	private boolean withdrawalSuccessIndicator = false;
	private boolean interestAddedSuccessIndicator = false;
	private double balance;
	private double interestEarned = 0.0;
	
	
	public Transaction(Account account){
		this.account = account;
	}
	
//	public boolean getWithdrawalSuccessIndicator() {
//		return withdrawalSuccessIndicator;
//	}
	
	public boolean getinterestAddedSuccessIndicator() {
		return interestAddedSuccessIndicator;
	}
	
	public void recordDeposit(double depositAmount) {
		// Don't record the transaction if there was an invalid amount
		if (depositAmount == 0) {
			return;
		}
		String depositAmountString = df2.format(depositAmount);
		account.deposit(depositAmount);
		String transaction = "Deposit       $" + depositAmountString;
		
		// Update the applicable account details instance
		switch (account.accountName) {
			case "EveryDay":
				((EveryDayAccount)(account)).updateAccountDetails("Deposit", depositAmount);
				EveryDayAccountDetails.transactionList.add(transaction);
				break;
			case "Investment":
				((InvestmentAccount)(account)).updateAccountDetails("Deposit", depositAmount);
				InvestmentAccountDetails.transactionList.add(transaction);
				break;
			case "Omni":
				((OmniAccount)(account)).updateAccountDetails("Deposit", depositAmount);
				OmniAccountDetails.transactionList.add(transaction);
				break;
		}
	}
	
	
	public void performWithdrawal(double withdrawalAmount) throws FailedWithdrawalException {
		double transactionAmount = withdrawalAmount;
		String transaction = "Withdrawal       $" + withdrawalAmount;
		String failedWithdrawalMessage = "Insufficient funds in " + account.accountName + " account.";
		
		// Update the account details if withdrawal was successful
		boolean sufficientFunds = account.withdrawalPerformed(withdrawalAmount);
		if (sufficientFunds) {		
			// Update the applicable account details instance
			switch (account.accountName) {
				case "EveryDay":
					if (!sufficientFunds) {
						transaction = "Failed Transaction    $" + EveryDayAccount.FAILED_TRANSACTION_FEE;
						((EveryDayAccount) account).updateAccountDetails("Failed Transaction", EveryDayAccount.FAILED_TRANSACTION_FEE);
					} else {
					((EveryDayAccount)(account)).updateAccountDetails("Withdrawal", transactionAmount);
					}
					EveryDayAccountDetails.transactionList.add(transaction);
					break;
				case "Investment":
					if (!sufficientFunds) {
						transactionAmount = InvestmentAccount.FAILED_TRANSACTION_FEE * -1;
						transaction = "Failed Transaction    $" + InvestmentAccount.FAILED_TRANSACTION_FEE;
						((InvestmentAccount) account).updateAccountDetails("Failed Transaction", InvestmentAccount.FAILED_TRANSACTION_FEE);
					} else {
						((InvestmentAccount)(account)).updateAccountDetails("Withdrawal", transactionAmount);
					}
					InvestmentAccountDetails.transactionList.add(transaction);
					break;
				case "Omni":
					if (!sufficientFunds) {
						transactionAmount = OmniAccount.FAILED_TRANSACTION_FEE * -1;
						transaction = "Failed Transaction    $" + OmniAccount.FAILED_TRANSACTION_FEE;
						((OmniAccount) account).updateAccountDetails("Failed Transaction", OmniAccount.FAILED_TRANSACTION_FEE);
					} else {
						((OmniAccount)(account)).updateAccountDetails("Withdrawal", transactionAmount);
					}
					OmniAccountDetails.transactionList.add(transaction);
					break;
			}
		} 
		else {
			throw new FailedWithdrawalException(failedWithdrawalMessage);
		}
	}
	

	public void addInterestToAccount() {
		double interestAmount;
		String interestAmountString;

		// Omni Account interest calculation
		if (account.accountName.equals("Omni")) {
			interestAmount = calculateOmniInterest();
			interestAmountString = df2.format(interestAmount);
			if (interestAmount > 0) {
				((OmniAccount) account).addInterest(interestAmount);
				((OmniAccount) account).updateAccountDetails("Interest", interestAmount);
				String transaction = "Interest       $" + interestAmountString;
				OmniAccountDetails.transactionList.add(transaction);
				interestAddedSuccessIndicator = true;
				return;
			}
			return;
		}

		// Investment Account interest calculation
		interestAmount = calculateInvestmentInterest();
		interestAmountString = df2.format(interestAmount);
		if (interestAmount > 0) {
			((InvestmentAccount) account).addInterest(interestAmount);
			((InvestmentAccount) account).updateAccountDetails("Interest", interestAmount);
			String transaction = "Interest       $" + interestAmountString;
			InvestmentAccountDetails.transactionList.add(transaction);
			interestAddedSuccessIndicator =  true;
		} 
	}
	
    /**
	 *  Calculates interest for
	 *  the investment account.
     */
    protected double calculateInvestmentInterest(){
    	balance = ((InvestmentAccount) account).balance;
    	if (balance > 0) {
	        interestEarned = balance * ((InvestmentAccount) account).interestRate / 100;
	        balance += interestEarned;
    	}
        return interestEarned;
}
	
    /**
	 *  Interest will be calculated for the Omni account if
	 *  the balance exceeds $1000.
     */
    protected double calculateOmniInterest(){
        // Add interest if balance exceeds 1000
    	balance = ((OmniAccount) account).balance;
        if (balance > 1000){
            interestEarned = balance * ((OmniAccount) account).interestRate / 100;
            balance += interestEarned;
        }
        return interestEarned;
    }
	
	
	/*
	 * Adds each transaction in the list
	 * to the account singleton for future reference.
	 */
	public List addTransactionToList(List transactionList) {
		// Get the the transactions for the applicable account
		switch (account.accountName) {
		case "EveryDay":
			for (String transaction :EveryDayAccountDetails.transactionList) {
				transactionList.add(transaction);
			}
			break;
		case "Omni":
			for (String transaction :OmniAccountDetails.transactionList) {
				transactionList.add(transaction);
			}
			break;
		case "Investment":
			for (String transaction :InvestmentAccountDetails.transactionList) {
				transactionList.add(transaction);
			}
			break;
		}
		return transactionList;
	}
}
