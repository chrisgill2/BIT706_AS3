

import java.util.ArrayList;
import java.util.List;

// Singleton
public class EveryDayAccountDetails{
	
    public static double balance;
    public String lastTransaction;
    public static List <String> transactionList;

    private static EveryDayAccountDetails accountInstance = null;
    
    private EveryDayAccountDetails() {}
    
    public static void setInstance(EveryDayAccount everyDayAccount){
    	accountInstance = new EveryDayAccountDetails();
    	transactionList = new ArrayList<String>();
    	accountInstance.lastTransaction = everyDayAccount.lastTransaction;
        if (accountInstance == null){
            accountInstance = new EveryDayAccountDetails();
            EveryDayAccountDetails.balance = everyDayAccount.balance;
        }
    }

    public static EveryDayAccountDetails getInstance(){
        return accountInstance;
    }
}