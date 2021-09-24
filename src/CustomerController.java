import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CustomerController {
	private Customer customer = new Customer();
	private CustomerFile customerFile = new CustomerFile();
	public String customerToEdit;
	public boolean isBankEmployee;
	public boolean accountAdded;
	public String selectedAccount;
	public Account transferFromAccount;
	public Account transferToAccount;
	
	public List<Customer> getCustomers() {
		Customer.customerList = customerFile.readFromFile();
		return Customer.customerList;
	}
	
	public List<String> getCustomerNames(){		
		Customer.customerList = customerFile.readFromFile();
		List<String> customerNames = new ArrayList<String>();
		List<Customer> customers = Customer.customerList;
		for (Customer customer: customers) {
			customerNames.add(customer.getCustomerName());
		}
		return customerNames;
	}
	
	public void setTransferFromAccount(Account account) {
		transferFromAccount = account;
	}
	
	public void setTransferToAccount(String accountName) {
		transferToAccount = customer.getCustomerAccountByName(customerToEdit, accountName);
	}

	public void deleteCustomer(int customerPositionInList) {
		customer.deleteCustomer(customerPositionInList);
		customerFile.writeToFile(Customer.customerList);
	}
	
	public void addCustomerToList(HashMap<String, String> customerDetails, boolean isBankEmployee) {
		Customer.customerList = customerFile.readFromFile();
		customer.addCustomer(customerDetails, isBankEmployee);
		customerFile.writeToFile(Customer.customerList);
	}
	
	public void editCustomer(HashMap<String, String> customerDetails) {
		customer.setIsBankEmployee(isBankEmployee);
		customer.editCustomer(customerToEdit, customerDetails);
		customerFile.writeToFile(Customer.customerList);
	}
	
	public HashMap<String, String> getCustomerEditDetails() {
		HashMap<String, String> customerDetails = customer.getCustomerDetailsByName(customerToEdit);
		isBankEmployee = customer.getIsBankEmployee();
		return customerDetails;
	}
	
	public List <String> getCustomerAccountNames() {
		List<Account> customerAccounts =  customer.getCustomerAccounts(customerToEdit);
		List<String> accountNames = new ArrayList<String>();
		
		for (Account account:customerAccounts) {
			accountNames.add(account.getAccountName());
		}
		return accountNames;
	}
	
	public void addCustomerAccount(String accountType) {
		Account account = createNewAccount(accountType);
		customer = customer.getCustomerByName(customerToEdit);
		if (getCustomerAccountNames().contains(account.getAccountName())) {
			accountAdded = false;
		} else {
			customer.addCustomerAccount(customerToEdit, account);
			// thing
			customerFile.writeToFile(Customer.customerList);
			accountAdded = true;
		}
	}
	
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
		return customerAccounts.get(getCustomerPositionInList(customerAccounts));
	}
	
	private int getCustomerPositionInList(List<Account> customerAccounts) {
		int pos = 0;
		for (Account account: customerAccounts) {
			if (selectedAccount.equals(account.getAccountName())){
				return pos;
			}
			pos++;
		}
		return -1;
	}
}