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
		btnAddCustomer = createLargeButton("Add Customer", buttonXPosition, 110);
		openAddCustomerWindowOnClick();
		
		btnEditCustomer = createLargeButton("Edit Customer", buttonXPosition, 220);
		openEditCustomerWindowOnClick();
		
		btnDeleteCustomer = createLargeButton("Delete Customer", buttonXPosition, 330);
		deleteCustomerOnClick();
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
					controller.customerToEdit = customerListBox.getItems()[selectedCustomerPosition];
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
	
	private void displayCustomerList() {
		customerListBox.removeAll();
		java.util.List<Customer> customerList = controller.getCustomers();
		if (customerList != null) {
			if (customerList.size() > 0) {
				for(Customer customer:customerList) {
					customerListBox.add(customer.getCustomerName());
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
}
