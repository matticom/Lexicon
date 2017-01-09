package windows;

import java.util.ResourceBundle;

import javax.swing.JDialog;

import enums.DialogWindows;

public abstract class DialogWindow extends JDialog {

	protected ResourceBundle 	languageBundle;
	protected final int GERMAN = 1;
	protected final int SPANISH = 2;
	
	protected DialogWindows dialogWindowType;
		
	public DialogWindow(ResourceBundle languageBundle, DialogWindows dialogWindowType) {

		this.languageBundle = languageBundle;
		this.dialogWindowType = dialogWindowType;
	}

	public DialogWindows getDialogWindowType() {
		return dialogWindowType;
	}
}
