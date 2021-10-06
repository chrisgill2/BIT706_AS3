import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * The Controller Class acts as the intermediary between the forms and the objects
 * that the forms require data from.
 * There are 2 main object types that the forms require data from:
 *  Customers, 
 *  Accounts
 *  @author Chris Gill
 *  @version 1.0
 */

public class Controller {
	private Customer customer = new Customer();
	private CustomerFile customerFile = new CustomerFile();
	private Transaction transaction;
	public String customerToEdit;
	public boolean isBankEmployee;
	public boolean accountAdded;
	public String selectedAccount;
	public Account account;
	public Account transferToAccount;
	
	/**
	 * Reads all customers from the serializable file.
	 * @return latest list of customers.
	 */
	public List<Customer> getCustomers() {
		Customer.customerList = customerFile.readFromFile();
		return Customer.customerList;
	}
	
	public List<String> getCustomerNames(){		
		List<String> customerNames = new ArrayList<String>();
		List<Customer> customers = getCustomers();
		for (Customer customer: customers) {
			customerNames.add(customer.getCustomerName());
		}
		return customerNames;
	}
	
	public void setCustomerAccount(Account account) {
		this.account = account;
	}
	
	public void setTransferToAccount(String accountName) {
		transferToAccount = customer.getCustomerAccountByName(customerToEdit, accountName);
	}

	public void deleteCustomer(int customerPositionInList) {
		customer.deleteCustomer(customerPositionInList);
		customerFile.writeToFile(Customer.customerList);
	}
	
	/**
	 * Add the customer to the list of customers and
	 * update the file with the latest list.
	 * @param customerDetails
	 * @param isBankEmployee
	 */
	public void addCustomerToList(HashMap<String, String> customerDetails, boolean isBankEmployee) {
		Customer.customerList = customerFile.readFromFile();
		customer.addCustomer(customerDetails, isBankEmployee);
		customerFile.writeToFile(Customer.customerList);
	}
	
	/**
	 * Update the customer with any changes and
	 * store in the customer file.
	 * @param customerDetails
	 */
	public void editCustomer(HashMap<String, String> customerDetails) {
		customer.setIsBankEmployee(isBankEmployee);
		customer.editCustomer(customerToEdit, customerDetails);
		customerFile.writeToFile(Customer.customerList);
	}
	
	/**
	 * Stores all customer details in a HashMap.
	 * @return customerDetails
	 */
	public HashMap<String, String> getCustomerEditDetails() {
		HashMap<String, String> customerDetails = customer.getCustomerDetailsByName(customerToEdit);
		isBankEmployee = customer.getIsBankEmployee();
		return customerDetails;
	}
	
	/**
	 * Gets a list of names of all accounts that are linked to
	 * the selected customer.
	 * @return list of account names
	 */
	public List <String> getCustomerAccountNames() {
		List<Account> customerAccounts =  customer.getCustomerAccounts(customerToEdit);
		List<String> accountNames = new ArrayList<String>();
		
		for (Account account:customerAccounts) {
			accountNames.add(account.getAccountName());
		}
		return accountNames;
	}
	
	
	/**
	 * Uses the string accountType to determine the type of account
	 * and adds to the list of accounts for the customer selected.
	 * @param accountType
	 */
	public void addCustomerAccount(String accountType) {
		Account account = createNewAccount(accountType);
		customer = customer.getCustomerByName(customerToEdit);
		if (getCustomerAccountNames().contains(account.getAccountName())) {
			accountAdded = false;
		} else {
			customer.addCustomerAccount(customerToEdit, account);
			customerFile.writeToFile(Customer.customerList);
			accountAdded = true;
		}
	}
	
	/**
	 * Uses the string accountType to determine the type of account
	 * and creates a new account linked to the customer.
	 * @param accountType
	 */
	public Account createNewAccount(String accountType) {
		customer = customer.getCustomerByName(customerToEdit);
		Account account;
		switch (accountType) {
		case "EveryDay":
			account = new EveryDayAccount(customer);
			break;
		case "Investment":
			account = new InvestmentAccount(customer);
			break;
		case "Omni":
			account = new OmniAccount(customer);
			break;
		default:
			return null;
		}
		return account;
	}
	
	public Account getCustomerAccountByName(String accountName) {
		return customer.getCustomerAccountByName(customerToEdit, accountName);
	}
	
	public Account getCustomerAccount() {
		List<Account> customerAccounts =  customer.getCustomerAccounts(customerToEdit);
		return customerAccounts.get(getPositionInList(customerAccounts));
	}
	
	/**
	 * Gets the account details for the selected customer.
	 * @return accountDetails
	 */
	public List <String> getCustomerAccountDetails(){
		List<Account> customerAccounts =  customer.getCustomerAccounts(customerToEdit);
		List<String> accountDetails = new ArrayList<String>();
		
		for (Account account:customerAccounts) {
			String details = account.getAccountID() + "  " + account.getAccountName() + "  $" + account.getBalance();
			accountDetails.add(details);
		}
		return accountDetails;
	}
	
	/**
	 * Sets the last transaction to be interest added if
	 * the interest calculation was successful.
	 * @return
	 */
	public String getInterestAddedToAccount() {
		transaction = new Transaction(account);
		transaction.addInterestToAccount();
		if (transaction.getinterestAddedSuccessIndicator()) {
			account.transactionList.add(account.lastTransaction);
			return account.lastTransaction;
		} else {
			return null;
		}
	}
	

	public String getLatestAccountDetails() {
		return account.getLatestAccountDetails();
	}
	
	public void recordDeposit(double amount) {
		Transaction transaction = new Transaction(account);
		transaction.recordDeposit(amount);
	}
	
	/**
	 * Performs a withdrawal if there are sufficient funds available.
	 * @param amount
	 * @throws FailedWithdrawalException if there insufficient funds in the account.
	 */
	public void performWithdrawal(double amount) throws FailedWithdrawalException {
		Transaction transaction = new Transaction(account);
		transaction.performWithdrawal(amount);
	}
	
	private int getPositionInList(List<Account> customerAccounts) {
		int pos = 0;
		String[] splitBySpace = selectedAccount.split("\\s+");
		selectedAccount = splitBySpace[1];
		for (Account account: customerAccounts) {
			if (selectedAccount.equals(account.getAccountName())){
				return pos;
			}
			pos++;
		}
		return -1;
	}
}