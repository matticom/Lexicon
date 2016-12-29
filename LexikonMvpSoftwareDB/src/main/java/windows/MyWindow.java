package windows;

import java.util.ResourceBundle;

import javax.swing.JDialog;

public abstract class MyWindow extends JDialog {

	protected ResourceBundle 	languageBundle;
		
	public MyWindow(ResourceBundle languageBundle) {

		this.languageBundle = languageBundle;
	}
}
