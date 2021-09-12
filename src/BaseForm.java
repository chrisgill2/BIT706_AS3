import java.text.DecimalFormat;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

public class BaseForm {

	protected Shell shell = new Shell();
	protected static DecimalFormat df2 = new DecimalFormat("#.##");
	protected MessageBox messageBox = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
	protected static CustomerController controller = new CustomerController();
	
	private static final String BANK_NAME = "ABC Bank";
	private final int texboxLength =220;
	private final int texboxHeight =20;
	

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
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
	public void createBaseContents(String headerText) {
		shell.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY));
		shell.setSize(599, 516);
		shell.setText(headerText);
		
		Label lblHeader = new Label(shell, SWT.CENTER);
		lblHeader.setBounds(222, 25, 129, 14);
		lblHeader.setText(BANK_NAME);

	}
	
	/**
	 * Create a new text box.
	 */
	protected Text createText(int xPosition, int yPosition){
		Text text = new Text(shell, SWT.BORDER);
		text.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		text.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		text.setBounds(xPosition, yPosition, texboxLength, texboxHeight);
		return text;
	}
	
	/**
	 * Create a new button.
	 */
	protected Button createButton(String buttonText, int xPosition, int yPosition) {
		Button button = new Button(shell, SWT.NONE);
		button.setText(buttonText);
		button.setBounds(xPosition, yPosition, 145, 41);
		return button;
	}
	
	/**
	 * Create a new large button.
	 */
	protected Button createLargeButton(String buttonText, int xPosition, int yPosition) {
		Button button = new Button(shell, SWT.NONE);
		button.setText(buttonText);
		button.setBounds(xPosition, yPosition, 180, 90);
		return button;
	}
	
	/**
	 * Displays a message box with a title
	 * and message.
	 */
	protected void displayMessage(String title, String message) {
		messageBox.setText(title);
		messageBox.setMessage(message);
		messageBox.open();
	}
}