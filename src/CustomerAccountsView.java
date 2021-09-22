import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;

public class CustomerAccountsView extends BaseForm{

	private List customerAccountsListBox;
	private Button btnAddAccount;
	private Button btnWithdraw;
	private Button btnTransfer;
	private Button btnCustomers;
	private Button btnManageAccounts;
	private int buttonXPosition = 289;
	private int selectedCustomerAccountPosition;
	
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
		
		createBaseContents("Customer Accounts");
		
		// Customer accounts list box
		customerAccountsListBox = new List(shell, SWT.BORDER);
		customerAccountsListBox.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		customerAccountsListBox.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		customerAccountsListBox.setBounds(20, 110, 224, 340);
		displayCustomerAccountList();
		
		//Customer Account Buttons
		btnAddAccount = createButton("Add New Account", buttonXPosition, 110);
		openAddAccountWindowOnClick();
		
		btnManageAccounts = createButton("Manage Account", buttonXPosition, 180);
		openAccountWindowOnClick();
				
		btnCustomers = createButton("Customers", buttonXPosition, 250);
		openManageCustomersWindowOnClick();
	}

	
	private void openAddAccountWindowOnClick() {
		btnAddAccount.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				shell.close();
				AddCustomerAccountForm customerAccountForm = new AddCustomerAccountForm();
				customerAccountForm.open();
			}
		});
	}
	
	private void openAccountWindowOnClick() {
		btnManageAccounts.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				selectedCustomerAccountPosition = customerAccountsListBox.getSelectionIndex();
				if (customerAccountSelected()) {
					controller.selectedAccount = customerAccountsListBox.getItems()[selectedCustomerAccountPosition];
					shell.close();
					Account account = controller.getCustomerAccount();
					AccountForm accountForm = new AccountForm(account, account.getAccountName());
					accountForm.open();
				}
			}
		});
	}
	
	private void openTransferWindowOnClick() {
		btnWithdraw.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
//				if (customerAccountSelected()) {
//					controller.deleteCustomer(selectedCustomerPosition);
//					displayCustomerList();
//				}
			}
		});
	}
	
	/**
	 * Open the Manage customers window
	 * on button click.
	 */	
	private void openManageCustomersWindowOnClick() {
		btnCustomers.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				shell.close();
				CustomerView customerPage = new CustomerView();
				customerPage.open();
			}
		});
	}
	
	private void displayCustomerAccountList() {
		customerAccountsListBox.removeAll();
		java.util.List<String> customerAccountList = controller.getCustomerAccountNames();
		if (customerAccountList != null) {
			if (customerAccountList.size() > 0) {
				for(String accountName:customerAccountList) {
					customerAccountsListBox.add(accountName);
				}
			} 
		}
	}
	
	/*
	 * Displays an error popup if no
	 * account was selected.
	 */
	private boolean customerAccountSelected() {
		if (customerAccountsListBox.getSelectionIndex() < 0) {
			displayMessage("No Customer Account Selected", "Please select an account.");
			return false;
		}
		selectedCustomerAccountPosition = customerAccountsListBox.getSelectionIndex();
		return true;
	}

}
