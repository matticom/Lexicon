package viewFactory;

import java.util.ResourceBundle;
import eventHandling.StaticPanels;
import panels.TermPanelDynamic;
import panels.DynamicPanel;
import panels.MyPanel;
import panels.SearchResultPanelDynamic;
import panels.SearchResultPanelStatic;
import panels.TechnicalTermPanelStatic;
import panels.SpecialtyPanelStatic;

public class StaticPanelCreator {
		
	private final String SEARCH_RESULT_TITLE = "resultsLbl";
	private final String LETTER_RESULT_TITLE = "resultsLblLetter";
	private final String TECHNICALTERM_RESULT_TITLE = "resultsLblSubject";
	private final int SEARCH_RESULT_HEIGHT = 200;
	private final int TECHNICALTERM_RESULT_HEIGHT = 170;
	
	public MyPanel createPanel(StaticPanels panel, ResourceBundle languageBundle, double MAINFRAME_DISPLAY_RATIO, DynamicPanel dynamicPanel) {
		
		MyPanel myPanel = panelFactory(panel, languageBundle, MAINFRAME_DISPLAY_RATIO, dynamicPanel);
		return myPanel;
	}
	
	protected MyPanel panelFactory(StaticPanels panel, ResourceBundle languageBundle, double MAINFRAME_DISPLAY_RATIO, DynamicPanel dynamicPanel) {
		
		MyPanel myPanel = null;
				
		if (panel == StaticPanels.LetterResultPanel) {
			myPanel = new SearchResultPanelStatic(languageBundle, MAINFRAME_DISPLAY_RATIO, (SearchResultPanelDynamic)dynamicPanel, LETTER_RESULT_TITLE, SEARCH_RESULT_HEIGHT);
		}
		if (panel == StaticPanels.SearchResultPanel) {
			myPanel = new SearchResultPanelStatic(languageBundle, MAINFRAME_DISPLAY_RATIO, (SearchResultPanelDynamic)dynamicPanel, SEARCH_RESULT_TITLE, SEARCH_RESULT_HEIGHT);
		}
		if (panel == StaticPanels.SpecialtyPanel) {
			myPanel = new SpecialtyPanelStatic(languageBundle, MAINFRAME_DISPLAY_RATIO, (TermPanelDynamic)dynamicPanel);
		}
		if (panel == StaticPanels.TechnicalTermPanel) {
			myPanel = new TechnicalTermPanelStatic(languageBundle, MAINFRAME_DISPLAY_RATIO, (TermPanelDynamic)dynamicPanel, TECHNICALTERM_RESULT_TITLE, TECHNICALTERM_RESULT_HEIGHT);
		}
		return myPanel;
	}
}
