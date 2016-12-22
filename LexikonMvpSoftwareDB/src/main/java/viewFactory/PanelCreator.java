package viewFactory;

import java.util.ResourceBundle;

import javax.swing.JPanel;

import eventHandling.PanelEventTransferObject;
import eventHandling.PanelUpdateObjects;
import panels.DynamicTestPanel;
import panels.LetterResultPanel;
import panels.MyPanel;
import panels.SearchResultPanel;
import panels.SpecialtyPanel;
import panels.TechnicalTermPanel;


public class PanelCreator {
		
	public MyPanel createPanel(PanelUpdateObjects panel, ResourceBundle languageBundle, double MAINFRAME_DISPLAY_RATIO, DynamicTestPanel dynamicTestPanel) {
		
		MyPanel myPanel = panelFactory(panel, languageBundle, MAINFRAME_DISPLAY_RATIO, dynamicTestPanel);
		myPanel.updatePanel(new PanelEventTransferObject());
		return myPanel;
	}
	
	protected MyPanel panelFactory(PanelUpdateObjects panel, ResourceBundle languageBundle, double MAINFRAME_DISPLAY_RATIO, DynamicTestPanel dynamicTestPanel) {
		
		MyPanel myPanel = null;
		
		if (panel == PanelUpdateObjects.LetterResultPanel) {
			myPanel = new LetterResultPanel(languageBundle, MAINFRAME_DISPLAY_RATIO);
		}
		if (panel == PanelUpdateObjects.SearchResultPanel) {
			myPanel = new SearchResultPanel(languageBundle, MAINFRAME_DISPLAY_RATIO);
		}
		if (panel == PanelUpdateObjects.SpecialtyPanel) {
			myPanel = new SpecialtyPanel(languageBundle, MAINFRAME_DISPLAY_RATIO, dynamicTestPanel);
		}
		if (panel == PanelUpdateObjects.TechnicalTermPanel) {
			myPanel = new TechnicalTermPanel(languageBundle, MAINFRAME_DISPLAY_RATIO);
		}
		return myPanel;
	}
}
