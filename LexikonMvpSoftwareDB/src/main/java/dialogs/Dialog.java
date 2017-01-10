package dialogs;

import java.util.ResourceBundle;

import javax.swing.JDialog;

import enums.Dialogs;

public abstract class Dialog extends JDialog {

	protected ResourceBundle 	languageBundle;
	protected final int GERMAN = 1;
	protected final int SPANISH = 2;
	
	protected Dialogs dialogWindowType;
		
	public Dialog(ResourceBundle languageBundle, Dialogs dialogWindowType) {

		this.languageBundle = languageBundle;
		this.dialogWindowType = dialogWindowType;
	}

	public Dialogs getDialogWindowType() {
		return dialogWindowType;
	}
}
