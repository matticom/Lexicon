package windows;

import java.util.Locale;
import java.util.ResourceBundle;

import eventHandling.PanelEventTransferObject;


public class AssignTechnicalTermToSpecialtyWindow extends MyWindow {

	private ResourceBundle 	languageBundle;
	private Locale 			currentLocale;

	public AssignTechnicalTermToSpecialtyWindow(ResourceBundle languageBundle) {
		
		this.languageBundle = languageBundle;
	}

	@Override
	public void updatePanel(PanelEventTransferObject e) {
	
	}

}
