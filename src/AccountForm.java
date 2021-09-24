
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.List;
import org.eclipse.wb.swt.SWTResourceManager;

public class AccountForm extends BaseForm {

	public List transactionList;
	private String accountType;
	private Account account;
	private Transaction transaction;
	private int transactionPosition;
	private String lastTransaction = "";

	private Button btnHome;
	private Button btnDepositMoney;
	private Button btnWithdrawMoney;
	private Button btnTransferMoney;
	private Button btnCalculateInterest;
	private Button btnAccountInformation;

	private static final String ACCOUNT_INFORMATION = "Account Information";
	private static final String ACCOUNT_DETAILS = "Account Details";
	private static final String HOME = "Home";
	private static final String CALCULATE_INTEREST = "Calculate Interest";
	private static final String INTEREST_ADDED = "Interest was added.";
	private static final String NO_INTEREST_ADDED = "No interest was added.";
	private static final String ERROR = "Error";
	private static final String SUCCESSFUL_TRANSACTION = "Successful Transaction";
	private static final String DEPOSIT = "Deposit";
	private static final String WITHDRAW = "Withdraw";

	private final int buttonXPosition = 43;
	HomeForm homeForm = new HomeForm();

	public AccountForm(Account account, String accountType) {
		this.accountType = accountType;
		this.account = account;
		this.transaction = new Transaction(account);
	}

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
		createBaseContents(accountType);

		// Transactions list box
		transactionList = new List(shell, SWT.BORDER);
		transactionList.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
		transactionList.setBounds(324, 116, 224, 315);

		// Display all transactions for the account
		transaction.addTransactionToList(transactionList);

		// Account information button
		btnAccountInformation = createButton(ACCOUNT_INFORMATION, buttonXPosition, 116);
		displayAccountInformationOnClick();

		// Withdraw money button
		btnWithdrawMoney = createButton(WITHDRAW, buttonXPosition, 184);
		openWithdrawMoneyWindowOnClick();

		// Deposit money button
		btnDepositMoney = createButton(DEPOSIT, buttonXPosition, 252);
		openDepositMoneyWindowOnClick();
		
		// Transfer money button -- only display if there are at least 2 accounts
		if (controller.getCustomerAccountNames().size() > 1) {
			btnTransferMoney = createButton("Transfer", buttonXPosition, 321);
			openTransferMoneyWindowOnClick();
		}
		
		// Calculate interest button -- hide for Every Day account
		if (!accountType.toLowerCase().equals("everyday")) {
			btnCalculateInterest = createButton(CALCULATE_INTEREST, buttonXPosition, 390);
			calculateInterestOnClick();
		}

		// Home page button
		btnHome = createButton(HOME, buttonXPosition, 459);
		openHomePageOnClick();
	}

	/**
	 * Open the home page on click.
	 */
	private void openHomePageOnClick() {
		btnHome.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				shell.close();
				HomeForm homePage = new HomeForm();
				homePage.open();
			}
		});
	}

	/**
	 * Display the account information on button click.
	 */
	private void displayAccountInformationOnClick() {
		btnAccountInformation.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				displayAccountDetails();
			}
		});
	}

	/**
	 * Open the deposit window on button click.
	 */
	private void openDepositMoneyWindowOnClick() {
		btnDepositMoney.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				shell.close();
				TransactionForm depositForm = new TransactionForm(account, DEPOSIT);
				depositForm.open();
			}
		});
	}

	/**
	 * Open the withdraw window on button click.
	 */
	private void openWithdrawMoneyWindowOnClick() {
		btnWithdrawMoney.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				shell.close();
				TransactionForm withdrawMoneyForm = new TransactionForm(account, WITHDRAW);
				withdrawMoneyForm.open();
			}
		});
	}
	
	/**
	 * Open the transfer window on button click.
	 */
	private void openTransferMoneyWindowOnClick() {
		btnTransferMoney.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				controller.setTransferFromAccount(account);
				shell.close();
				TransferView transferForm = new TransferView(account);
				transferForm.open();
			}
		});
	}

	/**
	 * Calculate interest and add to balance on button click.
	 */
	private void calculateInterestOnClick() {
		btnCalculateInterest.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				transaction.addInterestToAccount();
				if (transaction.getinterestAddedSuccessIndicator()) {
					account.transactionList.add(account.lastTransaction);
					displayMessage(SUCCESSFUL_TRANSACTION, INTEREST_ADDED);
					transactionList.add(account.lastTransaction);
				} else {
					displayMessage(ERROR, NO_INTEREST_ADDED);
				}
			}
		});
	}

	/*
	 * Reads the account details from a singleton and displays on screen.
	 */
	private void displayAccountDetails() {
		// Check for the latest transaction -- not displayed in this iteration
		lastTransaction = account.getLastTransaction();
		String details = account.getLatestAccountDetails();
		displayMessage(ACCOUNT_DETAILS, details);
	}
}
