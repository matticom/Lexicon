package viewFactory;

import java.util.ResourceBundle;

import eventHandling.PanelEventTransferObject;
import eventHandling.PanelUpdateObjects;
import panels.LetterResultPanel;
import panels.MyPanel;
import panels.SearchResultPanel;
import panels.SpecialtyPanel;
import panels.TechnicalTermPanel;


public class PanelCreator {
		
	public MyPanel createPanel(PanelUpdateObjects panel, ResourceBundle languageBundle) {
		
		MyPanel myPanel = panelFactory(panel, languageBundle);
		myPanel.updateFrame(new PanelEventTransferObject());
		return myPanel;
	}
	
	protected MyPanel panelFactory(PanelUpdateObjects panel, ResourceBundle languageBundle) {
		
		MyPanel myPanel = null;
		
		if (panel == PanelUpdateObjects.LetterResultPanel) {
			myPanel = new LetterResultPanel(languageBundle);
		}
		if (panel == PanelUpdateObjects.SearchResultPanel) {
			myPanel = new SearchResultPanel(languageBundle);
		}
		if (panel == PanelUpdateObjects.SpecialtyPanel) {
			myPanel = new SpecialtyPanel(languageBundle);
		}
		if (panel == PanelUpdateObjects.TechnicalTermPanel) {
			myPanel = new TechnicalTermPanel(languageBundle);
		}
		return myPanel;
	}
}
