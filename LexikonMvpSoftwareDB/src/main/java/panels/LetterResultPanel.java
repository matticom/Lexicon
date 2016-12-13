package panels;

import java.util.Locale;
import java.util.ResourceBundle;

import eventHandling.PanelEventTransferObject;

public class LetterResultPanel extends MyPanel {

	private ResourceBundle 	languageBundle;
	private Locale 			currentLocale;
	
	public LetterResultPanel(ResourceBundle languageBundle) {

		this.languageBundle = languageBundle;
	}

	@Override
	public void updatePanel(PanelEventTransferObject e) {
	
	}

}
