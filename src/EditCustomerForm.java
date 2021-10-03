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

public class EditCustomerView extends ModifyCustomerForm{
	Button btnEditCustomer;

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents("Edit Customer");
		
		// Edit Customer Button
		btnEditCustomer = createLargeButton("Edit", 91, 344);
		editOnClick();
		
		shell.open();
		shell.layout();
		displayCustomerDetails();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
	
	/**
	 * Open the Edit Customer window
	 * on button click.
	 */	
	private void editOnClick() {
		btnEditCustomer.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				editCustomer();
				if (!fieldEntryError) {
					shell.close();
					CustomerView customerView = new CustomerView();
					customerView.open();
				}
			}
		});
	}
	
	/*
	 * Reads in the customer details from the inputs
	 * and passes them to controller's edit method.
	 */
	private void editCustomer() {	
		controller.editCustomer(readCustomerDetails());
	}
	
	private void displayCustomerDetails() {
		if (controller.getCustomerNames().size() > 0) {
			HashMap<String, String> customerDetails = controller.getCustomerEditDetails();
			textName.setText(customerDetails.get("name"));
			textEmail.setText(customerDetails.get("email"));
			textAddress.setText(customerDetails.get("address"));
			btnIsEmployee.setSelection(controller.isBankEmployee);
		}
	}
}
