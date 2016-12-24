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

import interactElements.SpecialtyButton;
import interactElements.TechnicalTermButton;
import model.Specialty;
import model.TechnicalTerm;
import model.Translations;
import utilities.GridBagLayoutUtilities;
import utilities.WinUtil;

public class SearchResultPanelDynamic extends JPanel implements DynamicPanel {
	
	private JPanel germanPanel, spanishPanel;
	
	private ArrayList<SpecialtyButton> specialtyButtonsDE;
	private ArrayList<SpecialtyButton> specialtyButtonsES;
	private ArrayList<TechnicalTermButton> technicalTermButtonsDE;
	private ArrayList<TechnicalTermButton> technicalTermButtonsES;
	
	private final int GERMAN = 1;
	private final int SPANISH = 2;
	
	private String searchWord;
	private int dynamicPanelHeight;
	
	public SearchResultPanelDynamic(int mainFrameWidth, int mainFrameHeight, List<Translations> technicalTermTranslationList, String searchWord) {
		
		this.searchWord = distinguishBetweenLetterSearchAndNormalSearch(searchWord);
		specialtyButtonsDE = new ArrayList<SpecialtyButton>();
		specialtyButtonsES = new ArrayList<SpecialtyButton>();
		technicalTermButtonsDE = new ArrayList<TechnicalTermButton>();
		technicalTermButtonsES = new ArrayList<TechnicalTermButton>();
		buildUp(mainFrameWidth, mainFrameHeight, technicalTermTranslationList);
	}

	private String distinguishBetweenLetterSearchAndNormalSearch(String searchWord) {
		if (searchWord.charAt(0) == '.') {
			return String.valueOf(searchWord.charAt(1));
		} else {
			return searchWord;
		}
	}
	
	private void buildUp(int mainFrameWidth, int mainFrameHeight, List<Translations> technicalTermTranslationList) {
		this.setBackground(Color.RED);
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		germanPanel = new JPanel();
		germanPanel.setBackground(Color.CYAN);
		germanPanel.setLayout(new GridBagLayout());
		germanPanel.setPreferredSize(new Dimension(mainFrameWidth/2, 900));
		germanPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.LIGHT_GRAY));

		spanishPanel = new JPanel();
		spanishPanel.setBackground(Color.GRAY);
		spanishPanel.setLayout(new GridBagLayout());
		spanishPanel.setPreferredSize(new Dimension(mainFrameWidth/2, 900));
		spanishPanel.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.LIGHT_GRAY));

		this.add(germanPanel);
		this.add(spanishPanel);
				
		Dimension displaySize = Toolkit.getDefaultToolkit().getScreenSize();
		int buttonWidth = (int) (displaySize.getWidth() * 200/1920);
		int buttonHeight = (int) (displaySize.getHeight() * 30/1200);
		int inset = (int) (displaySize.getWidth() * 40/1920);

		splitAndCreateButtons(technicalTermTranslationList, buttonWidth, buttonHeight);
		calculateDynamicPanelHeight(buttonHeight, inset);
		arrangeButtons(inset);
		
		this.setPreferredSize(new Dimension(mainFrameWidth, dynamicPanelHeight));
		this.setMinimumSize(new Dimension(0, dynamicPanelHeight));
		this.setMaximumSize(new Dimension((int) displaySize.getWidth(), dynamicPanelHeight));
	}

	private void splitAndCreateButtons(List<Translations> technicalTermTranslationList, int buttonWidth, int buttonHeight) {
		
		for(Translations translation: technicalTermTranslationList) {
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
		
		Specialty specialty = ((TechnicalTerm)(translation.getTerm())).getSpecialty();
		Translations specialtyTranslation = specialty.getTranslationList().get(LanguageId-1);
		
		SpecialtyButton specialtyButton = new SpecialtyButton(specialty.getId(), specialtyTranslation.getId(), GERMAN, specialtyTranslation.getName());
		WinUtil.configButton(specialtyButton, buttonWidth, buttonHeight, BorderFactory.createLineBorder(buttonColor), buttonColor, WinUtil.LIGHT_BLACK);
		return specialtyButton;
	}
	
	private void calculateDynamicPanelHeight(int buttonHeight, int inset) {
		
		int numbersOfElements = Math.max(technicalTermButtonsDE.size(), technicalTermButtonsES.size());
		dynamicPanelHeight = numbersOfElements*buttonHeight + numbersOfElements*inset - inset/2;
	}
	
	private void arrangeButtons(int inset) {
		
		int numbersOfEntries = technicalTermButtonsDE.size();
		arrangeButtonsOfBothPanels(numbersOfEntries, inset, technicalTermButtonsDE, specialtyButtonsDE, germanPanel);
		
		numbersOfEntries = technicalTermButtonsES.size();
		arrangeButtonsOfBothPanels(numbersOfEntries, inset, technicalTermButtonsES, specialtyButtonsES, spanishPanel);
	}
	
	private void arrangeButtonsOfBothPanels(int numbersOfEntries, int inset, ArrayList<TechnicalTermButton> technicalTermButtons, ArrayList<SpecialtyButton> specialtyButtons, JPanel panel) {
		
		int posY = 1;
		int weightY = 0;
		int bottomInsert = inset;
		
		for (int i = 0; i < numbersOfEntries; i++) {
			
			GridBagLayoutUtilities.addGB(panel, technicalTermButtons.get(i), 1, posY, 1, weightY, GridBagConstraints.NORTH, new Insets(0, 0, bottomInsert, 0));
			GridBagLayoutUtilities.addGB(panel, specialtyButtons.get(i), 2, posY, 1, weightY, GridBagConstraints.NORTH, new Insets(0, 0, bottomInsert, 0));
			posY++;
			
			if (posY == numbersOfEntries) {
				weightY = 1;
				bottomInsert = 0;
			} else {
				weightY = 0;
			}
		}
	}
	
	public void setSpecialtiesButtonsActionListener(ActionListener l) {
		
		for (int i = 0; i < specialtyButtonsDE.size(); i++) {
			specialtyButtonsDE.get(i).addActionListener(l);
			specialtyButtonsES.get(i).addActionListener(l);
		}
		
		for (int i = 0; i < technicalTermButtonsDE.size(); i++) {
			technicalTermButtonsDE.get(i).addActionListener(l);
			technicalTermButtonsES.get(i).addActionListener(l);
		}
	}
	
	public String getSearchWord() {
		return searchWord;
	}

	public int getDynamicPanelHeight() {
		return dynamicPanelHeight;
	}
	
	
}
