package viewFactory;

import java.util.ResourceBundle;

import javax.swing.JPanel;
import eventHandling.PanelUpdateObjects;
import panels.SpecialtyPanelDynamic;
import panels.MyPanel;
import panels.SearchResultPanelDynamic;
import panels.SearchResultPanelStatic;
import panels.SpecialtyPanelStatic;
import panels.TechnicalTermPanel;


public class PanelCreator {
		
	private final String SEARCH_RESULT_TITLE = "resultsLbl";
	private final String LETTER_RESULT_TITLE = "resultsLblLetter";
	
	public MyPanel createPanel(PanelUpdateObjects panel, ResourceBundle languageBundle, double MAINFRAME_DISPLAY_RATIO, JPanel dynamicPanel) {
		
		MyPanel myPanel = panelFactory(panel, languageBundle, MAINFRAME_DISPLAY_RATIO, dynamicPanel);
		return myPanel;
	}
	
	protected MyPanel panelFactory(PanelUpdateObjects panel, ResourceBundle languageBundle, double MAINFRAME_DISPLAY_RATIO, JPanel dynamicPanel) {
		
		MyPanel myPanel = null;
				
		if (panel == PanelUpdateObjects.LetterResultPanel) {
			myPanel = new SearchResultPanelStatic(languageBundle, MAINFRAME_DISPLAY_RATIO, (SearchResultPanelDynamic)dynamicPanel, LETTER_RESULT_TITLE);
		}
		if (panel == PanelUpdateObjects.SearchResultPanel) {
			myPanel = new SearchResultPanelStatic(languageBundle, MAINFRAME_DISPLAY_RATIO, (SearchResultPanelDynamic)dynamicPanel, SEARCH_RESULT_TITLE);
		}
		if (panel == PanelUpdateObjects.SpecialtyPanel) {
			myPanel = new SpecialtyPanelStatic(languageBundle, MAINFRAME_DISPLAY_RATIO, (SpecialtyPanelDynamic)dynamicPanel);
		}
		if (panel == PanelUpdateObjects.TechnicalTermPanel) {
			myPanel = new TechnicalTermPanel(languageBundle, MAINFRAME_DISPLAY_RATIO);
		}
		return myPanel;
	}
}
