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
	private Button btnDeposit;
	private Button btnWithdraw;
	private Button btnTransfer;
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
		
		btnDeposit = createButton("Deposit", buttonXPosition, 180);
		openDepositWindowOnClick();
		
		btnWithdraw = createButton("Withdraw", buttonXPosition, 250);
		openWithdrawWindowOnClick();
		
		btnTransfer = createButton("Transfer", buttonXPosition, 320);
		openTransferWindowOnClick();
	}

	
	private void openAddAccountWindowOnClick() {
		btnAddAccount.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				shell.close();
//				AddCustomerView addCustomerWindow = new AddCustomerView();
//				addCustomerWindow.open();
			}
		});
	}
		
	private void openDepositWindowOnClick() {
		btnDeposit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				selectedCustomerPosition = customerAccountsListBox.getSelectionIndex();
				if (customerAccountSelected()) {
//					controller.customerToEdit = customerAccountsListBox.getItems()[selectedCustomerPosition];
//					shell.close();
//					EditCustomerView editCustomerWindow = new EditCustomerView();
//					editCustomerWindow.open();
				}
			}
		});
	}
	
	private void openWithdrawWindowOnClick() {
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
	
	private void displayCustomerAccountList() {
		customerAccountsListBox.removeAll();
//		java.util.List<Customer> customerList = controller.getCustomers();
//		if (customerList != null) {
//			if (customerList.size() > 0) {
//				for(Customer customer:customerList) {
//					customerAccountsListBox.add(customer.getCustomerName());
//				}
//			} 
//		}
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
		selectedCustomerPosition = customerAccountsListBox.getSelectionIndex();
		return true;
	}

}
