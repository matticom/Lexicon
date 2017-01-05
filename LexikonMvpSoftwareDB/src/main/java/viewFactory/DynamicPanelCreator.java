package viewFactory;

import java.awt.event.ActionListener;
import java.util.List;

import eventHandling.DynamicPanels;
import model.Specialty;
import model.TechnicalTerm;
import model.Translations;
import panels.TermPanelDynamic;
import panels.DynamicPanel;
import panels.SearchResultPanelDynamic;

public class DynamicPanelCreator {
	
	public SearchResultPanelDynamic createPanel(DynamicPanels panel, DynamicPanel currentPanel, ActionListener actionListener, int mainFrameWidth, int mainFrameHeight,
			List<Translations> translationList, String searchWord) {
		
		SearchResultPanelDynamic dynamicPanel = (SearchResultPanelDynamic) panelFactory(panel, actionListener, mainFrameWidth, mainFrameHeight, translationList,
				searchWord, null, null);
		return dynamicPanel;
	}

	public TermPanelDynamic createPanel(DynamicPanels panel, DynamicPanel currentPanel, ActionListener actionListener, int mainFrameWidth, int mainFrameHeight,
			List<Specialty> specialtyList) {
		
		TermPanelDynamic dynamicPanel = (TermPanelDynamic) panelFactory(panel, actionListener, mainFrameWidth, mainFrameHeight, null, null, specialtyList, null);
		return dynamicPanel;
	}

	public TermPanelDynamic createPanel(DynamicPanels panel, DynamicPanel currentPanel, ActionListener actionListener, int mainFrameWidth, int mainFrameHeight,
			String specialtyName, List<TechnicalTerm> technicalTermList) {
		
		TermPanelDynamic dynamicPanel = (TermPanelDynamic) panelFactory(panel, actionListener, mainFrameWidth, mainFrameHeight, null, specialtyName, null,
				technicalTermList);
		return dynamicPanel;
	}

	protected DynamicPanel panelFactory(DynamicPanels panel, ActionListener actionListener, int mainFrameWidth, int mainFrameHeight,
			List<Translations> translationList, String title, List<Specialty> specialtyList, List<TechnicalTerm> technicalTermList) {

		DynamicPanel dynamicPanel = null;

		if (panel == DynamicPanels.LetterResultPanel) {
			dynamicPanel = new SearchResultPanelDynamic(mainFrameWidth, mainFrameHeight, translationList, title, actionListener);
		}
		if (panel == DynamicPanels.SearchResultPanel) {
			dynamicPanel = new SearchResultPanelDynamic(mainFrameWidth, mainFrameHeight, translationList, title, actionListener);
		}
		if (panel == DynamicPanels.SpecialtyPanel) {
			dynamicPanel = new TermPanelDynamic(mainFrameWidth, mainFrameHeight, specialtyList, actionListener);
		}
		if (panel == DynamicPanels.TechnicalTermPanel) {
			dynamicPanel = new TermPanelDynamic(mainFrameWidth, mainFrameHeight, technicalTermList, title, actionListener);
		}
		return dynamicPanel;
	}
}
