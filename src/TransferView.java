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
	protected Label lblMessage;
	protected Text textBoxAmount;
	protected Button btnCancel;
	protected Button btnTransaction;
	
	protected final int buttonYPosition = 221;
	protected String transactionType;
	
	private static final String INVALID_AMOUNT_ERROR = "Please enter a valid amount.";
	private static final String INVALID_AMOUNT = "Invalid amount.";
	private static final String SUCCESSFUL_TRANSACTION = " was successful";
	private static final String INSUFFICIENT_FUNDS = "Insufficient funds for withdrawal.";
	private static final String FAILED_TRANSACTION = "Failed Transaction";
	private static final String SUCCESS = "Successful Transaction";


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


		btnTransaction = createButton("Transfer", 150, buttonYPosition);
		performTransactionOnClick();
		

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
//				accountForm.open();
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
				
//				if (transactionType.equals("Deposit")) {
//					transaction.recordDeposit(amount);
//					displayMessage(SUCCESS, transactionType + SUCCESSFUL_TRANSACTION);
//				} else {
//					transaction.performWithdrawal(amount);
//					if (!transaction.getWithdrawalSuccessIndicator()) {
//						displayMessage(FAILED_TRANSACTION, INSUFFICIENT_FUNDS);
//					} else {
//						displayMessage(SUCCESS, transactionType + SUCCESSFUL_TRANSACTION);
//					}
//				}
//				shlAbcBank.close();
//				accountForm.open();
			}
		});
	}
}
