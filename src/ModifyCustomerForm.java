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

public class ModifyCustomerForm extends BaseForm{
	Button btnCancel;
	Text textName;
	Text textEmail;
	Text textAddress;
	String customerName;
	boolean fieldEntryError;
	HashMap<String, String> customerDetails = new HashMap<String, String>();
	
	Button btnCheckButton;
	
	private final int textBoxXPosition = 200;

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
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
	protected void createContents(String pageHeader) {
		createBaseContents(pageHeader);
		
		// Name
		createTextBoxLabel("Customer Name", 117);
		textName = createText(textBoxXPosition, 112);
		
		// Email
		createTextBoxLabel("Customer Email", 151);
		textEmail = createText(textBoxXPosition, 148);
		
		// Address
		createTextBoxLabel("Customer Address", 191);
		textAddress = createText(textBoxXPosition, 184);
		
		// Cancel Button
		btnCancel = createLargeButton("Cancel", 291, 344);
		cancelOnClick();
		
		// Bank Employee checkbox
		btnCheckButton = new Button(shell, SWT.CHECK);
		btnCheckButton.setBounds(240, 220, 130, 18);
		btnCheckButton.setText("Bank Employee");
	}
	
	/**
	 * Cancel and return to customer screen
	 * on button click.
	 */	
	private void cancelOnClick() {
		btnCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				shell.close();
				CustomerView customerView = new CustomerView();
				customerView.open();
			}
		});
	}
	
	public String readNameFromTextBox() {
		String customerName = textName.getText();
		validateEntry(customerName, "name");
		return customerName;
	}
	
	public String readEmailFromTextBox() {
		String readEmailFromTextBox = textEmail.getText();
		validateEntry(readEmailFromTextBox, "email");
		return readEmailFromTextBox;
	}
	
	public String readAddressFromTextBox() {
		String customerAddress = textAddress.getText();
		validateEntry(customerAddress, "address");
		return customerAddress;
	}
	
	public boolean employeeCheckboxSelected() {
		if (btnCheckButton.getSelection()) {
			return true;
		}
		return false;
	}
	
	private void validateEntry(String entry, String textBoxName) {
		if (entry.equals("")) {
			fieldEntryError = true;
			displayMessage("Invalid Entry", "Please enter a valid " + textBoxName);
		}
		else {
			fieldEntryError = false;
		}
	}
	
	public HashMap<String, String> readCustomerDetails() {
		HashMap<String, String> customerDetails = new HashMap<String, String>();
		customerDetails.put("name", readNameFromTextBox());
		customerDetails.put("email", readEmailFromTextBox());
		customerDetails.put("address", readAddressFromTextBox());
		return customerDetails;
	}

	public void createTextBoxLabel(String text, int yPosition) {
		Label label = new Label(shell, SWT.NONE);
		label.setBounds(70, yPosition, 100, 15);
		label.setText(text);
	}
}
