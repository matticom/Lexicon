package panels;

import java.util.Locale;
import java.util.ResourceBundle;

import eventHandling.PanelEventTransferObject;

public class TechnicalTermPanel extends MyPanel {
	
	private ResourceBundle 	languageBundle;
	private Locale 			currentLocale;
	
	public TechnicalTermPanel(ResourceBundle languageBundle) {

		this.languageBundle = languageBundle;
	}

	@Override
	public void updatePanel(PanelEventTransferObject e) {
		
	}

}
