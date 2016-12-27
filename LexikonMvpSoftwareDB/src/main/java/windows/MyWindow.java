package windows;

import java.util.ResourceBundle;

import javax.swing.JDialog;

import eventHandling.PanelEventTransferObject;
import eventHandling.Updatable;

public abstract class MyWindow extends JDialog {

	protected ResourceBundle 	languageBundle;
	
	
	public MyWindow(ResourceBundle languageBundle) {

		this.languageBundle = languageBundle;
	}

}
