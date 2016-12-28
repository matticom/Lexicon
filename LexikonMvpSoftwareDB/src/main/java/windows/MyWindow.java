package windows;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JDialog;

import eventHandling.PanelEventTransferObject;
import eventHandling.Updatable;

public abstract class MyWindow extends JDialog {

	protected ResourceBundle 	languageBundle;
	protected int languageId;
	protected final int GERMAN = 1;
	protected final int SPANISH = 2;
	
	
	public MyWindow(ResourceBundle languageBundle) {

		this.languageBundle = languageBundle;
		setLanguageId(languageBundle);
	}

	private void setLanguageId(ResourceBundle languageBundle) {
		
		if (languageBundle.getLocale().equals(new Locale("de"))) {
			languageId = GERMAN;
		} else {
			languageId = SPANISH;
		}
	}
}
