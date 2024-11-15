import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;


public class HomeForm extends BaseForm{

	private Button btnEveryDay;
	

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			HomeForm window = new HomeForm();
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
		createBaseContents("Welcome");
		 
		// Manage Customers button
		btnEveryDay = createLargeButton("Manage Customers", 265, 250);
		openManageCustomersWindowOnClick();

	}
	
	/**
	 * Open the Manage customers window
	 * on button click.
	 */	
	private void openManageCustomersWindowOnClick() {
		btnEveryDay.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				shell.close();
				CustomerView customerPage = new CustomerView();
				customerPage.open();
			}
		});
	}
}
