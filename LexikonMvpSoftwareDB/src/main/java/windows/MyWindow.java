package windows;

import java.util.ResourceBundle;

import javax.swing.JDialog;

public abstract class MyWindow extends JDialog {

	protected ResourceBundle 	languageBundle;
	protected final int GERMAN = 1;
	protected final int SPANISH = 2;
		
	public MyWindow(ResourceBundle languageBundle) {

		this.languageBundle = languageBundle;
	}
}
