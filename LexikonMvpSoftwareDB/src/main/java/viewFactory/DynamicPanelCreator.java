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
	
	public SearchResultPanelDynamic createPanel(DynamicPanels panelType, DynamicPanel currentPanel, ActionListener actionListener, int mainFrameWidth, int mainFrameHeight,
			List<Translations> translationList, String searchWord) {
		
		SearchResultPanelDynamic dynamicPanel = (SearchResultPanelDynamic) panelFactory(panelType, actionListener, mainFrameWidth, mainFrameHeight, translationList,
				searchWord, null, null);
		return dynamicPanel;
	}

	public TermPanelDynamic createPanel(DynamicPanels panelType, DynamicPanel currentPanel, ActionListener actionListener, int mainFrameWidth, int mainFrameHeight,
			List<Specialty> specialtyList) {
		
		TermPanelDynamic dynamicPanel = (TermPanelDynamic) panelFactory(panelType, actionListener, mainFrameWidth, mainFrameHeight, null, null, specialtyList, null);
		return dynamicPanel;
	}

	public TermPanelDynamic createPanel(DynamicPanels panelType, DynamicPanel currentPanel, ActionListener actionListener, int mainFrameWidth, int mainFrameHeight,
			String specialtyName, List<TechnicalTerm> technicalTermList) {
		
		TermPanelDynamic dynamicPanel = (TermPanelDynamic) panelFactory(panelType, actionListener, mainFrameWidth, mainFrameHeight, null, specialtyName, null,
				technicalTermList);
		return dynamicPanel;
	}

	protected DynamicPanel panelFactory(DynamicPanels panelType, ActionListener actionListener, int mainFrameWidth, int mainFrameHeight,
			List<Translations> translationList, String title, List<Specialty> specialtyList, List<TechnicalTerm> technicalTermList) {

		DynamicPanel dynamicPanel = null;

		if (panelType == DynamicPanels.LetterResultPanel) {
			dynamicPanel = new SearchResultPanelDynamic(mainFrameWidth, mainFrameHeight, translationList, title, actionListener, panelType);
		}
		if (panelType == DynamicPanels.SearchResultPanel) {
			dynamicPanel = new SearchResultPanelDynamic(mainFrameWidth, mainFrameHeight, translationList, title, actionListener, panelType);
		}
		if (panelType == DynamicPanels.SpecialtyPanel) {
			dynamicPanel = new TermPanelDynamic(mainFrameWidth, mainFrameHeight, specialtyList, actionListener, panelType);
		}
		if (panelType == DynamicPanels.TechnicalTermPanel) {
			dynamicPanel = new TermPanelDynamic(mainFrameWidth, mainFrameHeight, technicalTermList, title, actionListener, panelType);
		}
		return dynamicPanel;
	}
}
