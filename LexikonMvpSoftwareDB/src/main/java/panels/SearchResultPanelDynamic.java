package panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import eventHandling.DynamicPanels;
import interactElements.SpecialtyButton;
import interactElements.TechnicalTermButton;
import model.Specialty;
import model.TechnicalTerm;
import model.Translations;
import utilities.GridBagLayoutUtilities;
import utilities.WinUtil;

public class SearchResultPanelDynamic extends DynamicPanel {

	private ArrayList<SpecialtyButton> specialtyButtonsDE;
	private ArrayList<SpecialtyButton> specialtyButtonsES;
	private ArrayList<TechnicalTermButton> technicalTermButtonsDE;
	private ArrayList<TechnicalTermButton> technicalTermButtonsES;

	private boolean isLetterResult;

	public SearchResultPanelDynamic(int mainFrameWidth, int mainFrameHeight, List<Translations> technicalTermTranslationList, String searchWord, ActionListener actionListener, DynamicPanels dynamicPanel) {

		super(dynamicPanel);
		distinguishBetweenLetterSearchAndNormalSearch(searchWord);
		specialtyButtonsDE = new ArrayList<SpecialtyButton>();
		specialtyButtonsES = new ArrayList<SpecialtyButton>();
		technicalTermButtonsDE = new ArrayList<TechnicalTermButton>();
		technicalTermButtonsES = new ArrayList<TechnicalTermButton>();
		buildUp(mainFrameWidth, mainFrameHeight, technicalTermTranslationList);
		setSearchResultTermsButtonsActionListener(actionListener);
	}

	private String distinguishBetweenLetterSearchAndNormalSearch(String searchWord) {
		if (searchWord.charAt(0) == '.') {
			isLetterResult = true;
			return String.valueOf(searchWord.charAt(1));
		} else {
			isLetterResult = false;
			return searchWord;
		}
	}

	private void buildUp(int mainFrameWidth, int mainFrameHeight, List<Translations> technicalTermTranslationList) {
		setBackground(WinUtil.LIGHT_BLACK);
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		germanPanel = new JPanel();
		germanPanel.setBackground(WinUtil.LIGHT_BLACK);
		germanPanel.setLayout(new GridBagLayout());
		germanPanel.setPreferredSize(new Dimension(mainFrameWidth / 2, 900));
		germanPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.LIGHT_GRAY));

		spanishPanel = new JPanel();
		spanishPanel.setBackground(WinUtil.LIGHT_BLACK);
		spanishPanel.setLayout(new GridBagLayout());
		spanishPanel.setPreferredSize(new Dimension(mainFrameWidth / 2, 900));
		spanishPanel.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.LIGHT_GRAY));

		add(germanPanel);
		add(spanishPanel);

		Dimension displaySize = Toolkit.getDefaultToolkit().getScreenSize();
		int buttonWidth = WinUtil.relW(200);
		int buttonHeight = WinUtil.relH(30);
		int inset = WinUtil.relW(40);

		splitAndCreateButtons(technicalTermTranslationList, buttonWidth, buttonHeight);
		calculateDynamicPanelHeight(buttonHeight, inset);
		arrangeButtons(inset);

		setPreferredSize(new Dimension(mainFrameWidth, dynamicPanelHeight));
		setMinimumSize(new Dimension(0, dynamicPanelHeight));
		setMaximumSize(new Dimension((int) displaySize.getWidth(), dynamicPanelHeight));
	}

	private void splitAndCreateButtons(List<Translations> technicalTermTranslationList, int buttonWidth, int buttonHeight) {

		for (Translations translation : technicalTermTranslationList) {
			int languageId = translation.getLanguages().getId();
			if (languageId == GERMAN) {
				Color buttonColor = WinUtil.COOL_BLUE;
				technicalTermButtonsDE.add(createTechnicalTermButton(translation, GERMAN, buttonWidth, buttonHeight, buttonColor));
				specialtyButtonsDE.add(createSpecialtyButton(translation, GERMAN, buttonWidth, buttonHeight, buttonColor));
			}
			if (languageId == SPANISH) {
				Color buttonColor = WinUtil.STRONG_ORANGE;
				technicalTermButtonsES.add(createTechnicalTermButton(translation, SPANISH, buttonWidth, buttonHeight, buttonColor));
				specialtyButtonsES.add(createSpecialtyButton(translation, SPANISH, buttonWidth, buttonHeight, buttonColor));
			}
		}
	}

	private TechnicalTermButton createTechnicalTermButton(Translations translation, int LanguageId, int buttonWidth, int buttonHeight, Color buttonColor) {

		TechnicalTermButton technicalTermButton = new TechnicalTermButton(translation.getTerm().getId(), translation.getId(), LanguageId, translation.getName());
		WinUtil.configButton(technicalTermButton, buttonWidth, buttonHeight, BorderFactory.createLineBorder(buttonColor), buttonColor, WinUtil.LIGHT_BLACK);
		return technicalTermButton;
	}

	private SpecialtyButton createSpecialtyButton(Translations translation, int LanguageId, int buttonWidth, int buttonHeight, Color buttonColor) {

		Specialty specialty = ((TechnicalTerm) (translation.getTerm())).getSpecialty();
		Translations specialtyTranslation = specialty.getTranslationList().get(LanguageId - 1);

		SpecialtyButton specialtyButton = new SpecialtyButton(specialty.getId(), specialtyTranslation.getId(), GERMAN, specialtyTranslation.getName());
		WinUtil.configButton(specialtyButton, buttonWidth, buttonHeight, BorderFactory.createLineBorder(buttonColor), buttonColor, WinUtil.LIGHT_BLACK);
		return specialtyButton;
	}

	private void calculateDynamicPanelHeight(int buttonHeight, int inset) {

		int numbersOfElements = Math.max(technicalTermButtonsDE.size(), technicalTermButtonsES.size());
		dynamicPanelHeight = numbersOfElements * buttonHeight + numbersOfElements * inset;
	}

	private void arrangeButtons(int inset) {

		int numbersOfEntries = technicalTermButtonsDE.size();
		arrangeButtonsOfBothPanels(numbersOfEntries, inset, technicalTermButtonsDE, specialtyButtonsDE, germanPanel);

		numbersOfEntries = technicalTermButtonsES.size();
		arrangeButtonsOfBothPanels(numbersOfEntries, inset, technicalTermButtonsES, specialtyButtonsES, spanishPanel);
	}

	private void arrangeButtonsOfBothPanels(int numbersOfEntries, int inset, ArrayList<TechnicalTermButton> technicalTermButtons, ArrayList<SpecialtyButton> specialtyButtons,
			JPanel panel) {

		int posY = 1;
		int weightY = 0;
		int bottomInsert = inset;
		int topInset = 0;

		for (int i = 0; i < numbersOfEntries; i++) {

			if (i == 0) {
				topInset = inset / 2;
			} else {
				topInset = 0;
			}

			if (numbersOfEntries == 1) {
				weightY = 1;
				bottomInsert = 0;
			}

			GridBagLayoutUtilities.addGB(panel, technicalTermButtons.get(i), 1, posY, 1, weightY, GridBagConstraints.NORTH, new Insets(topInset, 0, bottomInsert, 0));
			GridBagLayoutUtilities.addGB(panel, specialtyButtons.get(i), 2, posY, 1, weightY, GridBagConstraints.NORTH, new Insets(topInset, 0, bottomInsert, 0));
			posY++;

			if (posY == numbersOfEntries) {
				weightY = 1;
				bottomInsert = 0;
			} else {
				weightY = 0;
			}
		}
	}

	public void setSearchResultTermsButtonsActionListener(ActionListener l) {

		for (int i = 0; i < technicalTermButtonsDE.size(); i++) {
			technicalTermButtonsDE.get(i).addActionListener(l);
			specialtyButtonsDE.get(i).addActionListener(l);
		}

		for (int i = 0; i < technicalTermButtonsES.size(); i++) {
			technicalTermButtonsES.get(i).addActionListener(l);
			specialtyButtonsES.get(i).addActionListener(l);
		}
	}

	@Override
	public String getSearchWord() {
		return searchWord;
	}

	@Override
	public boolean isLetterResult() {
		return isLetterResult;
	}

	@Override
	public int getDynamicPanelHeight() {
		return dynamicPanelHeight;
	}
}
