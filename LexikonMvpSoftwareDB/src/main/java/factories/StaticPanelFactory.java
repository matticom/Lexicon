package factories;

import java.util.ResourceBundle;

import enums.StaticPanels;
import panels.TermPanelDynamic;
import panels.DynamicPanel;
import panels.StaticPanel;
import panels.SearchResultPanelDynamic;
import panels.SearchResultPanelStatic;
import panels.TechnicalTermPanelStatic;
import panels.SpecialtyPanelStatic;

public class StaticPanelFactory {
		
	private final String SEARCH_RESULT_TITLE = "resultsLbl";
	private final String LETTER_RESULT_TITLE = "resultsLblLetter";
	private final String TECHNICALTERM_RESULT_TITLE = "resultsLblSubject";
	private final int SEARCH_RESULT_HEIGHT = 200;
	private final int TECHNICALTERM_RESULT_HEIGHT = 170;
	
	public StaticPanel createPanel(StaticPanels panelType, ResourceBundle languageBundle, double MAINFRAME_DISPLAY_RATIO, DynamicPanel dynamicPanel) {
		
		StaticPanel staticPanel = panelFactory(panelType, languageBundle, MAINFRAME_DISPLAY_RATIO, dynamicPanel);
		return staticPanel;
	}
	
	protected StaticPanel panelFactory(StaticPanels panelType, ResourceBundle languageBundle, double MAINFRAME_DISPLAY_RATIO, DynamicPanel dynamicPanel) {
		
		StaticPanel staticPanel = null;
				
		if (panelType == StaticPanels.LETTER_RESULT_PANEL) {
			staticPanel = new SearchResultPanelStatic(languageBundle, MAINFRAME_DISPLAY_RATIO, (SearchResultPanelDynamic)dynamicPanel, LETTER_RESULT_TITLE, SEARCH_RESULT_HEIGHT, panelType);
		}
		if (panelType == StaticPanels.SEARCH_RESULT_PANEL) {
			staticPanel = new SearchResultPanelStatic(languageBundle, MAINFRAME_DISPLAY_RATIO, (SearchResultPanelDynamic)dynamicPanel, SEARCH_RESULT_TITLE, SEARCH_RESULT_HEIGHT, panelType);
		}
		if (panelType == StaticPanels.SPECIALTY_PANEL) {
			staticPanel = new SpecialtyPanelStatic(languageBundle, MAINFRAME_DISPLAY_RATIO, (TermPanelDynamic)dynamicPanel, panelType);
		}
		if (panelType == StaticPanels.TECHNICAL_TERM_PANEL) {
			staticPanel = new TechnicalTermPanelStatic(languageBundle, MAINFRAME_DISPLAY_RATIO, (TermPanelDynamic)dynamicPanel, TECHNICALTERM_RESULT_TITLE, TECHNICALTERM_RESULT_HEIGHT, panelType);
		}
		return staticPanel;
	}
}
