package windows;

import java.util.Locale;
import java.util.ResourceBundle;

import eventHandling.PanelEventTransferObject;

public class TechnicalTermCreationWindow extends MyWindow {

	private ResourceBundle 	languageBundle;
	private Locale 			currentLocale;
	
	public TechnicalTermCreationWindow(ResourceBundle languageBundle) {

		this.languageBundle = languageBundle;
	}

	@Override
	public void updatePanel(PanelEventTransferObject e) {
	
	}

}
