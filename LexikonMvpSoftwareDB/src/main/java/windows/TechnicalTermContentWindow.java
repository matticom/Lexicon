package windows;

import java.util.Locale;
import java.util.ResourceBundle;

import eventHandling.PanelEventTransferObject;

public class TechnicalTermContentWindow extends MyWindow {

	private ResourceBundle 	languageBundle;
	private Locale 			currentLocale;
	
	public TechnicalTermContentWindow(ResourceBundle languageBundle) {

		this.languageBundle = languageBundle;
	}

	@Override
	public void updateFrame(PanelEventTransferObject e) {
		
	}

}
