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
		
	public MyPanel createPanel(PanelUpdateObjects panel, ResourceBundle languageBundle, double MAINFRAME_DISPLAY_RATIO) {
		
		MyPanel myPanel = panelFactory(panel, languageBundle, MAINFRAME_DISPLAY_RATIO);
		myPanel.updatePanel(new PanelEventTransferObject());
		return myPanel;
	}
	
	protected MyPanel panelFactory(PanelUpdateObjects panel, ResourceBundle languageBundle, double MAINFRAME_DISPLAY_RATIO) {
		
		MyPanel myPanel = null;
		
		if (panel == PanelUpdateObjects.LetterResultPanel) {
			myPanel = new LetterResultPanel(languageBundle, MAINFRAME_DISPLAY_RATIO);
		}
		if (panel == PanelUpdateObjects.SearchResultPanel) {
			myPanel = new SearchResultPanel(languageBundle, MAINFRAME_DISPLAY_RATIO);
		}
		if (panel == PanelUpdateObjects.SpecialtyPanel) {
			myPanel = new SpecialtyPanel(languageBundle, MAINFRAME_DISPLAY_RATIO);
		}
		if (panel == PanelUpdateObjects.TechnicalTermPanel) {
			myPanel = new TechnicalTermPanel(languageBundle, MAINFRAME_DISPLAY_RATIO);
		}
		return myPanel;
	}
}
