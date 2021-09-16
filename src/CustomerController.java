import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CustomerController {
	private Customer customer = new Customer();
	public String customerToEdit;
	
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
}