

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;

public class TransactionForm extends BaseForm{

	protected Shell shlAbcBank = super.shell;
	private AccountForm accountForm;
	protected Label lblMessage;
	protected Text textBoxAmount;
	protected Button btnCancel;
	protected Button btnTransaction;
	
	private Transaction transaction;
	protected final int buttonYPosition = 221;
	protected String transactionType;
	
	private static final String INVALID_AMOUNT_ERROR = "Please enter a valid amount.";
	private static final String INVALID_AMOUNT = "Invalid amount.";
	private static final String SUCCESSFUL_TRANSACTION = " was successful";
	private static final String FAILED_TRANSACTION = "Failed Transaction";
	private static final String SUCCESS = "Successful Transaction";
	private static final String DEPOSIT = "Deposit";
	private static final String WITHDRAW = "Withdraw";
	private static final String CANCEL = "Cancel";
	
	/**
	 * @wbp.parser.entryPoint
	 */
	public TransactionForm(Account account, String transactionType) {
		this.transactionType = transactionType;
		transaction = new Transaction(account);
		accountForm = new AccountForm(account, account.accountName);
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
		createBaseContents(transactionType + " Money");
		
		// Amount to deposit text box
		textBoxAmount = new Text(shlAbcBank, SWT.BORDER);
		textBoxAmount.setBounds(263, 145, 137, 19);
		
		// Amount label
		Label lblAmount = new Label(shlAbcBank, SWT.NONE);
		lblAmount.setBounds(167, 148, 59, 14);
		lblAmount.setText("Amount");

		// Transaction button
		if (transactionType.equals(DEPOSIT)) {
			btnTransaction = createButton(DEPOSIT, 150, buttonYPosition);
			performTransactionOnClick();
		} else {
			btnTransaction = createButton(WITHDRAW, 150, buttonYPosition);
			performTransactionOnClick();
		}
		
		// Cancel button
		btnCancel = createButton(CANCEL, 350, buttonYPosition);
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
				accountForm.open();
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
	private void performTransactionOnClick() {
		btnTransaction.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				// Exit method if amount is invalid
				double amount = readAmountFromTextBox();
				if (amount == 0) {
					textBoxAmount.setText("");
					return;
					};
				
				if (transactionType.equals("Deposit")) {
					controller.recordDeposit(amount);
					displayMessage(SUCCESS, transactionType + SUCCESSFUL_TRANSACTION);
				} else {
					try {
						controller.performWithdrawal(amount);
						displayMessage(SUCCESS, transactionType + SUCCESSFUL_TRANSACTION);
					} catch (FailedWithdrawalException e1) {
						displayMessage(FAILED_TRANSACTION, e1.getMessage());
					}
				}
				shlAbcBank.close();
				accountForm.open();
			}
		});
	}
}
