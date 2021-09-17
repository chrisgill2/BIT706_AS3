

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
		btnEveryDayAccount = new Button(shell, SWT.RADIO);
		btnEveryDayAccount.setBounds(250, 135, 150, 18);
		btnEveryDayAccount.setText("EveryDay Account");
		
		// Investment account radio button
		btnInvestmentAccount = new Button(shell, SWT.RADIO);
		btnInvestmentAccount.setBounds(250, 190, 150, 18);
		btnInvestmentAccount.setText("Investment Account");
		
		// Omni account radio button
		btnOmniAccount = new Button(shell, SWT.RADIO);
		btnOmniAccount.setBounds(250, 244, 150, 18);
		btnOmniAccount.setText("Omni Account");
		
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
				CustomerView customerView = new CustomerView();
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
					homeForm.open();
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
