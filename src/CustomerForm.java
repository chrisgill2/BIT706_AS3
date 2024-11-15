import org.eclipse.swt.widgets.Display;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.List;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Button;

public class CustomerView extends BaseForm{

	private List customerListBox;
	private Button btnAddCustomer;
	private Button btnEditCustomer;
	private Button btnDeleteCustomer;
	private Button btnManageAccounts;
	private int buttonXPosition = 289;
	private int selectedCustomerPosition;
	
	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		
		createBaseContents("Customers");
		
		// Customers list box
		customerListBox = new List(shell, SWT.BORDER);
		customerListBox.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		customerListBox.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		customerListBox.setBounds(20, 110, 224, 340);
		displayCustomerList();
		
		//Customer Buttons
		btnAddCustomer = createButton("Add Customer", buttonXPosition, 110);
		openAddCustomerWindowOnClick();
		
		btnEditCustomer = createButton("Edit Customer", buttonXPosition, 180);
		openEditCustomerWindowOnClick();
		
		btnDeleteCustomer = createButton("Delete Customer", buttonXPosition, 250);
		deleteCustomerOnClick();
		
		btnManageAccounts = createButton("Manage Accounts", buttonXPosition, 320);
		openManageCustomerAccountsWindowOnClick();
	}

	
	private void openAddCustomerWindowOnClick() {
		btnAddCustomer.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				shell.close();
				AddCustomerView addCustomerWindow = new AddCustomerView();
				addCustomerWindow.open();
			}
		});
	}
		
	private void openEditCustomerWindowOnClick() {
		btnEditCustomer.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				selectedCustomerPosition = customerListBox.getSelectionIndex();
				if (customerSelected()) {
					String selectedCustomer = customerListBox.getItems()[selectedCustomerPosition];
					controller.customerToEdit = getCustomerName(selectedCustomer);
					shell.close();
					EditCustomerView editCustomerWindow = new EditCustomerView();
					editCustomerWindow.open();
				}
			}
		});
	}
	
	private void deleteCustomerOnClick() {
		btnDeleteCustomer.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				if (customerSelected()) {
					controller.deleteCustomer(selectedCustomerPosition);
					displayCustomerList();
				}
			}
		});
	}
	
	private void openManageCustomerAccountsWindowOnClick() {
		btnManageAccounts.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				selectedCustomerPosition = customerListBox.getSelectionIndex();
				if (customerSelected()) {
					String selectedCustomer = customerListBox.getItems()[selectedCustomerPosition];
					controller.customerToEdit = getCustomerName(selectedCustomer);
					shell.close();
					CustomerAccountsView customerAccountsPage = new CustomerAccountsView();
					customerAccountsPage.open();
				}
			}
		});
	}
	
	private void displayCustomerList() {
		customerListBox.removeAll();
		java.util.List<Customer> customerList = controller.getCustomers();
		if (customerList != null) {
			if (customerList.size() > 0) {
				for(Customer customer:customerList) {
					customerListBox.add(customer.getCustomerID() + "   " + customer.getCustomerName());
				}
			} 
		}
	}
	
	/*
	 * Displays an error popup if no
	 * customer was selected.
	 */
	private boolean customerSelected() {
		if (customerListBox.getSelectionIndex() < 0) {
			displayMessage("No Customer Selected", "Please select a customer.");
			return false;
		}
		selectedCustomerPosition = customerListBox.getSelectionIndex();
		return true;
	}
	
	/*
	 * Get the name of the customer
	 * from the customer details displayed
	 * in the list.
	 */
	private String getCustomerName(String customerDetails) {
		String customerName = customerDetails.replaceAll(".+\\s", "");
		return customerName;
	}
}
