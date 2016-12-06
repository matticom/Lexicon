package panels;

import java.util.Locale;
import java.util.ResourceBundle;

import eventHandling.PanelEventTransferObject;

public class SpecialtyPanel extends MyPanel {

	private ResourceBundle 	languageBundle;
	private Locale 			currentLocale;
	
	public SpecialtyPanel(ResourceBundle languageBundle) {

		this.languageBundle = languageBundle;
	}

	@Override
	public void updateFrame(PanelEventTransferObject e) {
		
	}

}
