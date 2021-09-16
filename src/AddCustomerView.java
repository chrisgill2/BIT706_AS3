import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;

import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Button;

public class AddCustomerView extends ModifyCustomerForm{
	Button btnAddCustomer;

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents("Add Customer");
		btnAddCustomer = createLargeButton("Add", 91, 344);
		addOnClick();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
	
	/**
	 * Open the Add Customer window
	 * on button click.
	 */	
	private void addOnClick() {
		btnAddCustomer.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				addCustomer();
				if (!fieldEntryError) {
					shell.close();
					CustomerView customerView = new CustomerView();
					customerView.open();
				}
			}
		});
	}
	
	/*
	 * Read the customer details from the
	 * text boxes and use the controller
	 * to add the new customer.
	 */
	private void addCustomer() {
		customerDetails.put("name", readNameFromTextBox());
		customerDetails.put("email", readEmailFromTextBox());
		customerDetails.put("address", readAddressFromTextBox());
		if (!fieldEntryError) {
			controller.addCustomerToList(customerDetails, employeeCheckboxSelected());
		}
	}
}
