

import java.util.ArrayList;
import java.util.List;

// Singleton
public class OmniAccountDetails{
	
    public static double balance;
    public double overDraftAmount;
    public double interestRate;
    public double failedTransactionFee;
    public String lastTransaction;
    public static List <String> transactionList;

    
    private OmniAccountDetails() {}
    
    private static OmniAccountDetails accountInstance = null;

    public static void setInstance(OmniAccount omniAccount){
        if (accountInstance == null){
            accountInstance = new OmniAccountDetails();
            transactionList = new ArrayList<String>();
            OmniAccountDetails.balance = omniAccount.balance;
            accountInstance.overDraftAmount = omniAccount.overDraftAmount;
            accountInstance.interestRate = omniAccount.interestRate;
            accountInstance.failedTransactionFee = omniAccount.failedTransactionFee;
            
        }
    }
    
    public static OmniAccountDetails getInstance(){
        return accountInstance;
    }
}
