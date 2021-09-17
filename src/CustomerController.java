import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CustomerController {
	private Customer customer = new Customer();
	public String customerToEdit;
	public boolean accountAdded;
	
	public List<Customer> getCustomers() {
		return Customer.customerList;
	}
	
	public List<String> getCustomerNames(){
		List<String> customerNames = new ArrayList<String>();
		List<Customer> customers = Customer.customerList;
		for (Customer customer: customers) {
			customerNames.add(customer.getCustomerName());
		}
		return customerNames;
	}

	public void deleteCustomer(int customerPositionInList) {
		customer.deleteCustomer(customerPositionInList);
	}
	
	public void addCustomerToList(HashMap<String, String> customerDetails, boolean isBankEmployee) {
		customer.addCustomer(customerDetails, isBankEmployee);
	}
	
	public void editCustomer(HashMap<String, String> customerDetails) {
		customer.editCustomer(customerToEdit, customerDetails);
	}
	
	public HashMap<String, String> getCustomerEditDetails() {
		return customer.getCustomerDetailsByName(customerToEdit);
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
		if (getCustomerAccountNames().contains(account.getAccountName())) {
			accountAdded = false;
		} else {
			customer.addCustomerAccount(customerToEdit, account);
			accountAdded = true;
		}
	}
	
	private Account createNewAccount(String accountType) {
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
}