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

	int counter = 0;
	
	public SearchResultPanelDynamic createPanel(DynamicPanels panel, DynamicPanel currentPanel, ActionListener actionListener, int mainFrameWidth, int mainFrameHeight,
			List<Translations> translationList, String searchWord) {
		
		if (currentPanel != null) {
			((SearchResultPanelDynamic) currentPanel).removeActionListenerFromSearchResultTermButtons(actionListener);
		}
		SearchResultPanelDynamic dynamicPanel = (SearchResultPanelDynamic) panelFactory(panel, actionListener, mainFrameWidth, mainFrameHeight, translationList,
				searchWord, null, null);
		dynamicPanel.setSearchResultTermsButtonsActionListener(actionListener);
		return dynamicPanel;
	}

	public TermPanelDynamic createPanel(DynamicPanels panel, DynamicPanel currentPanel, ActionListener actionListener, int mainFrameWidth, int mainFrameHeight,
			List<Specialty> specialtyList) {
		
//		if (currentPanel != null) {
//			((TermPanelDynamic) currentPanel).removeActionListenerFromTermButtons(actionListener);
//		}
		TermPanelDynamic dynamicPanel = (TermPanelDynamic) panelFactory(panel, actionListener, mainFrameWidth, mainFrameHeight, null, null, specialtyList, null);
//		dynamicPanel.setTermsButtonsActionListener(actionListener, counter++);
//		dynamicPanel.getOneTermButton().setText("blablub");
		return dynamicPanel;
	}

	public TermPanelDynamic createPanel(DynamicPanels panel, DynamicPanel currentPanel, ActionListener actionListener, int mainFrameWidth, int mainFrameHeight,
			String specialtyName, List<TechnicalTerm> technicalTermList) {
		
//		if (currentPanel != null) {
//			((TermPanelDynamic) currentPanel).removeActionListenerFromTermButtons(actionListener);
//		}
		TermPanelDynamic dynamicPanel = (TermPanelDynamic) panelFactory(panel, actionListener, mainFrameWidth, mainFrameHeight, null, specialtyName, null,
				technicalTermList);
//		dynamicPanel.setTermsButtonsActionListener(actionListener, counter);
		return dynamicPanel;
	}

	protected DynamicPanel panelFactory(DynamicPanels panel, ActionListener actionListener, int mainFrameWidth, int mainFrameHeight,
			List<Translations> translationList, String title, List<Specialty> specialtyList, List<TechnicalTerm> technicalTermList) {

		DynamicPanel dynamicPanel = null;

		if (panel == DynamicPanels.LetterResultPanel) {
			dynamicPanel = new SearchResultPanelDynamic(mainFrameWidth, mainFrameHeight, translationList, title);
		}
		if (panel == DynamicPanels.SearchResultPanel) {
			dynamicPanel = new SearchResultPanelDynamic(mainFrameWidth, mainFrameHeight, translationList, title);
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
