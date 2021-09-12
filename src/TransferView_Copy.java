import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.SWT;
import org.eclipse.wb.swt.SWTResourceManager;

public class TransferView_Copy {

	protected Shell shell;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			TransferView_Copy window = new TransferView_Copy();
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
		
		Combo combo = new Combo(shell, SWT.NONE);
		combo.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		combo.setBounds(120, 46, 170, 22);
		combo.add("EveryDay");
		combo.add("Omni");
		combo.add("Investment");

	}
}
