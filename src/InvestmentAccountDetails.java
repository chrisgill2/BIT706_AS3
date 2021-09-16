

import java.util.ArrayList;
import java.util.List;

// Singleton
public class InvestmentAccountDetails{
	
    public static double balance;
    public double interestRate;
    public double failedTransactionFee;
    public String lastTransaction;
    public static List <String> transactionList;
    
    private InvestmentAccountDetails() {}

    private static InvestmentAccountDetails accountInstance = null;

    public static void setInstance(InvestmentAccount investmentAccount){
        if (accountInstance == null){
            accountInstance = new InvestmentAccountDetails();
            transactionList = new ArrayList<String>();
            InvestmentAccountDetails.balance = investmentAccount.getBalance();
            accountInstance.interestRate = investmentAccount.interestRate;
            accountInstance.failedTransactionFee = investmentAccount.failedTransactionFee;
        }
    }
    
    public static InvestmentAccountDetails getInstance(){
        return accountInstance;
    }

}
