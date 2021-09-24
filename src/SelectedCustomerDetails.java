

import java.util.ArrayList;
import java.util.List;

// Singleton
public class SelectedCustomerDetails{
	
    public static double balance;
    public double interestRate;
    public double failedTransactionFee;
    public String lastTransaction;
    public static List <Account> customerAccounts;
    
    private SelectedCustomerDetails() {}

    private static SelectedCustomerDetails customerInstance = null;

    public static void setInstance(Customer customer){
        if (customerInstance == null){
            customerInstance = new SelectedCustomerDetails();

        }
    }
    
    public static SelectedCustomerDetails getInstance(){
        return customerInstance;
    }

}
