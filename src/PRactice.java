import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.SWT;

public class PRactice {

	protected Shell shell;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			PRactice window = new PRactice();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

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
		shell = new Shell();
		shell.setSize(450, 300);
		shell.setText("SWT Application");
		
//		btnEveryDayAccount = new Button(shell, SWT.RADIO);
//		btnEveryDayAccount.setBounds(149, 35, 90, 18);
//		btnEveryDayAccount.setText("EveryDay Account");
//		
//		btnInvestmentAccount = new Button(shell, SWT.RADIO);
//		btnInvestmentAccount.setBounds(149, 90, 90, 18);
//		btnInvestmentAccount.setText("Investment Account");
//		
//		btnOmniAccount = new Button(shell, SWT.RADIO);
//		btnOmniAccount.setBounds(149, 144, 90, 18);
//		btnOmniAccount.setText("Omni Account");

	}
}
