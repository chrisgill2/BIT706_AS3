import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Customer {
	private String name;
	private String customerID;
	private String email;
	private String address;
	private boolean isBankEmployee;
	static List <Customer> customerList = new ArrayList<Customer>();
	private List <Account> customerAccounts;
	
	public Customer() {
		customerAccounts = new ArrayList<Account>();
	}
	
	public String getCustomerName() {
		return name;
	}
	
	public String getCustomerID() {
		return customerID;
	}
	
	public String getCustomerEmail() {
		return email;
	}
	
	public String getCustomerAddress() {
		return address;
	}
	
	public boolean getIsBankEmployee() {
		return isBankEmployee;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}
	
	public void setCustomerEmail(String email) {
		this.email = email;
	}
	
	public void setCustomerAddress(String address) {
		this.address = address;
	}
	
	public void addCustomer(HashMap<String, String> customerDetails, boolean isBankEmployee) {
		Customer customer = new Customer();
		customer.name = customerDetails.get("name");
		customer.email = customerDetails.get("email");
		customer.address = customerDetails.get("address");
		customer.isBankEmployee = isBankEmployee;
		customer.customerID = getNewCustomerID();
		customerList.add(customer);
	}
	
	/* 
	 * Finds the customer in the customer list
	 * and returns the details stored in a hashmap.
	 */
	public HashMap<String, String> getCustomerDetailsByName(String customerName) {
		Customer customer = customerList.get(getCustomerPositionInList(customerName));
		HashMap<String, String> customerDetails = new HashMap<String, String>();
		customerDetails.put("name", customer.getCustomerName());
		customerDetails.put("email", customer.getCustomerEmail());
		customerDetails.put("address", customer.getCustomerAddress());
		return customerDetails;
	}
	
	/*
	 * Gets the position of the customer to edit in
	 * the customer list and edits the customer.
	*/
	public void editCustomer(String currentCustomerName, HashMap<String, String> customerDetails) {
		Customer customer = customerList.get(getCustomerPositionInList(currentCustomerName));
		
		customer.name = customerDetails.get("name");
		customer.email = customerDetails.get("email");
		customer.address = customerDetails.get("address");
	}
	
	public void deleteCustomer(int customerPositionInList) {
		customerList.remove(customerPositionInList);
	}
	
	private int getCustomerPositionInList(String name) {
		int pos = 0;
		for (Customer customer: customerList) {
			if (name.equals(customer.name)){
				return pos;
			}
			pos++;
		}
		return -1;
	}
	
	/*
	 * Gets the latest customer ID and 
	 * increments that ID to generate
	 * the new ID.
	 */
	private String getNewCustomerID() {
		List <Integer> customerIDList = new ArrayList<Integer>();
		if (customerList.size() > 0) {
			// Create a list of customer IDs
			for (Customer customer: customerList) {
				int customerID = Integer.valueOf(customer.getCustomerID());
				customerIDList.add(customerID);
			}
		}
		else {
			// Return 1 if it's the first customer
			return "1";
			};
		
		// Increment the latest customer ID and return
		int latestCustomerID = Collections.max(customerIDList) + 1;
		return Integer.toString(latestCustomerID);
	}
	
	public List <Account> getCustomerAccounts(String currentCustomerName) {
		Customer customer = customerList.get(getCustomerPositionInList(currentCustomerName));
//		Customer customer = new Customer();
//		EveryDayAccount ed = new EveryDayAccount(customer);
//		OmniAccount omni = new OmniAccount(customer);
//		customer.customerAccounts.add(ed);
//		customer.customerAccounts.add(omni);
		return customer.customerAccounts;
		
	}

	
	public void addCustomerAccount(String currentCustomerName, Account account) {
		Customer customer = customerList.get(getCustomerPositionInList(currentCustomerName));
		customer.customerAccounts.add(account);
	}
}
