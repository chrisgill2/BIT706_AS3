import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.wb.swt.SWTResourceManager;

public class TransferView extends BaseForm{

	protected Shell shlAbcBank = super.shell;
	private CustomerView customerView = new CustomerView();
	private Text textBoxAmount;
	private Button btnCancel;
	private Button btnTransfer;
	private Account toAccount;
	private Account fromAccount;
	private Combo transferToAccount;
	private double transferAmount;
	private String toAccountSelection;
	
	private final int buttonYPosition = 300;
	protected String transactionType;
	
	private static final String INVALID_AMOUNT_ERROR = "Please enter a valid amount.";
	private static final String INVALID_AMOUNT = "Invalid amount.";


	public TransferView(Account fromAccount) {
		this.fromAccount = fromAccount;
	}
	/**
	 * Open the window.
	 * @wbp.parser.entryPoint
	 */
	public void open() {
		Display display = Display.getDefault();
		createTransactionFormContents();
		shlAbcBank.open();
		shlAbcBank.layout();
		while (!shlAbcBank.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createTransactionFormContents() {
		createBaseContents("Transfer Money");
		
		// Transfer to account label
		Label lblAccountTransferFrom = new Label(shlAbcBank, SWT.NONE);
		lblAccountTransferFrom.setBounds(140, 150, 200, 20);
		lblAccountTransferFrom.setText("Transfer From " + fromAccount.getAccountName() + " Account");

		// Transfer from account label
		Label lblAccountTransferTo = new Label(shlAbcBank, SWT.NONE);
		lblAccountTransferTo.setBounds(140, 200, 140, 14);
		lblAccountTransferTo.setText("Transfer To Account");
		
		// Account for transfer to
		transferToAccount = createTransferAccountCombo(195);

		
		// Amount to deposit text box
		textBoxAmount = createText(300, 250);
		
		// Amount label
		Label lblAmount = new Label(shlAbcBank, SWT.NONE);
		lblAmount.setBounds(140, 250, 59, 14);
		lblAmount.setText("Amount");


		btnTransfer = createButton("Transfer", 150, buttonYPosition);
		performTransferOnClick();
		

		// Cancel button
		btnCancel = createButton("Cancel", 350, buttonYPosition);
		cancelTransactionOnClick();

	}
	
	/**
	 * Cancel the transaction
	 * on button click.
	 */	
	protected void cancelTransactionOnClick() {
		btnCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				shlAbcBank.close();
				customerView.open();
			}
		});
	}
	
	private int readAmountFromTextBox() {
		// Error if an invalid amount is entered
		try {
			String transactionAmount = textBoxAmount.getText();
			int amount = Integer.valueOf(transactionAmount);
			if (amount <= 0) {
				displayMessage(INVALID_AMOUNT, INVALID_AMOUNT_ERROR);
				textBoxAmount.setText("");
				return 0;
			} 
			else {
				textBoxAmount.setText("");
				return Integer.valueOf(transactionAmount);
			}
		} 
		catch (Exception e) {
			displayMessage(INVALID_AMOUNT, INVALID_AMOUNT_ERROR);
			return 0;
		}
	}
	
	/**
	 * Record the transaction
	 * on button click.
	 */	
	private void performTransferOnClick() {
		btnTransfer.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				// Exit method if amount is invalid
				transferAmount = readAmountFromTextBox();
				if (transferAmount == 0) {
					textBoxAmount.setText("");
					return;
					};
				if (validateAccountSelection()) {
					performTransfer();
				};
			}
		});
	}
	
	private void performTransfer() {
		toAccount = controller.getCustomerAccountByName(toAccountSelection);
		Transaction transaction = new Transaction(fromAccount);
		transaction.transferBetweenAccounts(toAccount, transferAmount);
		
		
		
		if (!transaction.getTransferSuccessIndicator()){
			displayMessage("Insufficient Funds", "Insufficient funds for transfer.");
		} else {
			toAccount.deposit(transferAmount);
			displayMessage("Transaction successful", "Transfer was successful.");
		};
	}
	
	private Combo createTransferAccountCombo(int yPosition) {
		Combo transferAccount = new Combo(shell, SWT.DROP_DOWN);
		transferAccount.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		transferAccount.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		transferAccount.setBounds(300, yPosition, 220, 35);
		
		// Get the list of available accounts to transfer to
		for(String accountName: controller.getCustomerAccountNames()){
			if (!accountName.equals(fromAccount.getAccountName()))
				transferAccount.add(accountName);
		}
	
		return transferAccount;
	}
	
	private boolean validateAccountSelection() {
		boolean validAccountSelection = true;
		toAccountSelection = transferToAccount.getText();
		
		if (transferToAccount.getText().equals("")) {
			displayMessage("Account not selected.", "Please select an account to transfer to.");
			validAccountSelection = false;
		} else {
			controller.setTransferToAccount(toAccountSelection);
		}

		return validAccountSelection;
	}
}
