

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.List;
import org.eclipse.wb.swt.SWTResourceManager;

public class AddCustomerAccountForm extends BaseForm {

	private Button btnEveryDayAccount;
	private Button btnInvestmentAccount;
	private Button btnOmniAccount;
	private Button btnCancel;
	private Button btnAddAccount;
	HomeForm homeForm = new HomeForm();
	CustomerView customerView = new CustomerView();

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createAccountContents();
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
	protected void createAccountContents() {
		createBaseContents("Add New Account");

		// Everyday account radio button
		btnEveryDayAccount = createRadioButton("EveryDay Account", 250, 135);
		
		// Investment account radio button
		btnInvestmentAccount = createRadioButton("Investment Account", 250, 190);
		
		// Omni account radio button
		btnOmniAccount = createRadioButton("Omni Account", 250, 244);
		
		// Add Account Button
		btnAddAccount = createLargeButton("Add", 91, 344);
		addOnClick();

		// Cancel Button
		btnCancel = createLargeButton("Cancel", 400, 344);
		cancelOnClick();
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
				customerView.open();
			}
		});
	}
	
	/**
	 * Open the Add Customer window
	 * on button click.
	 */	
	private void addOnClick() {
		btnAddAccount.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				String accountType = getAccountSelection();
				controller.addCustomerAccount(accountType);
				
				if (!controller.accountAdded) {
					displayMessage("Invalid Selection.", "Account already exists.");
				} else {
					displayMessage("Account Added.", "Account was created.");
					shell.close();
					customerView.open();
				}
			}
		});
	}
	
	private String getAccountSelection() {
		String accountType = null;
		if (btnEveryDayAccount.getSelection()) {
			accountType = "EveryDay";
		} else if (btnInvestmentAccount.getSelection()) {
			accountType = "Investment";
		} else if (btnOmniAccount.getSelection()) {
			accountType = "Omni";
		} else {
			displayMessage("No Account Selected.", "Please select an account.");
		}
		return accountType;
	}
}
