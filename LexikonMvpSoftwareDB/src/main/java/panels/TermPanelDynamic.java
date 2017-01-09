package panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import enums.DynamicPanels;
import interactElements.TermButton;
import model.Term;
import model.Translations;
import utilities.GridBagLayoutUtilities;
import utilities.WinUtil;

public class TermPanelDynamic extends DynamicPanel {

	private int gridX, gridY;
	private ArrayList<TermButton> TermButtonsDE;
	private ArrayList<TermButton> TermButtonsES;
	private int numbersOfSpecialties;


	public TermPanelDynamic(int mainFrameWidth, int mainFrameHeight, List<? extends Term> termList, ActionListener actionListener, DynamicPanels dynamicPanel) {

		super(dynamicPanel);
		searchWord = null;
		TermButtonsDE = new ArrayList<TermButton>();
		TermButtonsES = new ArrayList<TermButton>();
		buildUp(mainFrameWidth, mainFrameHeight, termList);
		setTermsButtonsActionListener(actionListener);
	}

	public TermPanelDynamic(int mainFrameWidth, int mainFrameHeight, List<? extends Term> termList, String searchWord, ActionListener actionListener, DynamicPanels dynamicPanel) {

		this(mainFrameWidth, mainFrameHeight, termList, actionListener, dynamicPanel);
		this.searchWord = searchWord;
	}

	private void buildUp(int mainFrameWidth, int mainFrameHeight, List<? extends Term> termList) {

		setBackground(WinUtil.LIGHT_BLACK);
		setLayout(new GridLayout(1, 2));

		germanPanel = new JPanel();
		germanPanel.setBackground(WinUtil.LIGHT_BLACK);
		germanPanel.setLayout(new GridBagLayout());
		germanPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.LIGHT_GRAY));

		spanishPanel = new JPanel();
		spanishPanel.setBackground(WinUtil.LIGHT_BLACK);
		spanishPanel.setLayout(new GridBagLayout());
		spanishPanel.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.LIGHT_GRAY));

		add(germanPanel);
		add(spanishPanel);

		Dimension displaySize = Toolkit.getDefaultToolkit().getScreenSize();
		numbersOfSpecialties = termList.size();
		int buttonWidth = WinUtil.relW(200);
		int buttonHeight = WinUtil.relH(30);
		int inset = WinUtil.relW(20);

		createTermButtons(termList, buttonWidth, buttonHeight);
		calculateGrid(numbersOfSpecialties, mainFrameWidth / 2, inset, buttonWidth, buttonHeight);
		arrangeTermButton(numbersOfSpecialties, inset);

		setPreferredSize(new Dimension(mainFrameWidth, dynamicPanelHeight));
		setMinimumSize(new Dimension(0, dynamicPanelHeight));
		setMaximumSize(new Dimension((int) displaySize.getWidth(), dynamicPanelHeight));
	}

	private void createTermButtons(List<? extends Term> termList, int buttonWidth, int buttonHeight) {

		int GERMAN = 1;
		int SPANISH = 2;

		for (Term term : termList) {
			for (Translations translation : term.getTranslationList()) {
				if (translation.getLanguages().getId() == GERMAN) {
					TermButton termButton = new TermButton(term.getId(), translation.getId(), GERMAN, translation.getName());
					WinUtil.configButton(termButton, buttonWidth, buttonHeight, BorderFactory.createLineBorder(WinUtil.COOL_BLUE), WinUtil.COOL_BLUE, WinUtil.LIGHT_BLACK);
					TermButtonsDE.add(termButton);
				}
				if (translation.getLanguages().getId() == SPANISH) {
					TermButton termButton = new TermButton(term.getId(), translation.getId(), SPANISH, translation.getName());
					WinUtil.configButton(termButton, buttonWidth, buttonHeight, BorderFactory.createLineBorder(WinUtil.STRONG_ORANGE), WinUtil.STRONG_ORANGE, WinUtil.LIGHT_BLACK);
					TermButtonsES.add(termButton);
				}
			}
		}
	}

	private void calculateGrid(int numbersOfButtons, int panelWidth, int inset, int buttonWidth, int buttonHeight) {

		gridX = panelWidth / (buttonWidth + 2 * inset);
		gridY = numbersOfButtons / gridX;

		if (numbersOfButtons % gridX != 0) {
			gridY++;
		}
		dynamicPanelHeight = gridY * buttonHeight + gridY * 2 * inset;
	}

	private void arrangeTermButton(int numbersOfSpecialties, int inset) {

		int posX = 1;
		int posY = 1;
		int weightY = 0;

		for (int i = 0; i < numbersOfSpecialties; i++) {

			if (gridY == 1) {
				weightY = 1;
			}

			GridBagLayoutUtilities.addGB(germanPanel, TermButtonsDE.get(i), posX, posY, 0, weightY, GridBagConstraints.NORTH, new Insets(inset, inset, inset, inset));
			GridBagLayoutUtilities.addGB(spanishPanel, TermButtonsES.get(i), posX, posY, 0, weightY, GridBagConstraints.NORTH, new Insets(inset, inset, inset, inset));
			posX++;

			if (posX > gridX) {
				posX = 1;
				posY++;
			}

			if (posY == gridY) {
				weightY = 1;
			} else {
				weightY = 0;
			}
		}
	}

	public TermButton getOneTermButton() {
		return TermButtonsDE.get(0);
	}
	
	public void setTermsButtonsActionListener(ActionListener l) {

		for (int i = 0; i < numbersOfSpecialties; i++) {
			TermButtonsDE.get(i).addActionListener(l);
			TermButtonsES.get(i).addActionListener(l);
		}
	}

	@Override
	public String getSearchWord() {
		return searchWord;
	}

	@Override
	public int getDynamicPanelHeight() {
		return dynamicPanelHeight;
	}
}
