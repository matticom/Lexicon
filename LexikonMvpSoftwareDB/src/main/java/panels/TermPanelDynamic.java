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

import interactElements.TermButton;
import model.Term;
import model.Translations;
import utilities.GridBagLayoutUtilities;
import utilities.WinUtil;

public class TermPanelDynamic extends JPanel implements DynamicPanel {

	private JPanel germanTermPanel, spanishTermPanel;
	private int gridX, gridY;
	private ArrayList<TermButton> TermButtonsDE;
	private ArrayList<TermButton> TermButtonsES;
	private int numbersOfSpecialties;
	
	private int dynamicPanelHeight;
	private String searchWord;

	public TermPanelDynamic(int mainFrameWidth, int mainFrameHeight, List<? extends Term> termList) {
	
		TermButtonsDE = new ArrayList<TermButton>();
		TermButtonsES = new ArrayList<TermButton>();
		searchWord = null;
		buildUp(mainFrameWidth, mainFrameHeight, termList);
	}
	
	public TermPanelDynamic(int mainFrameWidth, int mainFrameHeight, List<? extends Term> termList, String searchWord) {
		
		this(mainFrameWidth, mainFrameHeight, termList);
		this.searchWord = searchWord; 
	}
	
	private void buildUp(int mainFrameWidth, int mainFrameHeight, List<? extends Term> termList) {
		
		this.setBackground(WinUtil.LIGHT_BLACK);
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		germanTermPanel = new JPanel();
		germanTermPanel.setBackground(WinUtil.LIGHT_BLACK);
		germanTermPanel.setLayout(new GridBagLayout());
		germanTermPanel.setPreferredSize(new Dimension(mainFrameWidth/2, 900));
		germanTermPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.LIGHT_GRAY));

		spanishTermPanel = new JPanel();
		spanishTermPanel.setBackground(WinUtil.LIGHT_BLACK);
		spanishTermPanel.setLayout(new GridBagLayout());
		spanishTermPanel.setPreferredSize(new Dimension(mainFrameWidth/2, 900));
		spanishTermPanel.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.LIGHT_GRAY));

		this.add(germanTermPanel);
		this.add(spanishTermPanel);
		
		Dimension displaySize = Toolkit.getDefaultToolkit().getScreenSize();
		numbersOfSpecialties = termList.size();
		int buttonWidth = (int) (displaySize.getWidth() * 200/1920);
		int buttonHeight = (int) (displaySize.getHeight() * 30/1200);
		int inset = (int) (displaySize.getWidth() * 20/1920);
		
		createTermButtons(termList, buttonWidth, buttonHeight);
		calculateGrid(numbersOfSpecialties, mainFrameWidth/2, inset, buttonWidth, buttonHeight);
		arrangeTermButton(numbersOfSpecialties, inset);
		
		this.setPreferredSize(new Dimension(mainFrameWidth, dynamicPanelHeight));
		this.setMinimumSize(new Dimension(0, dynamicPanelHeight));
		this.setMaximumSize(new Dimension((int) displaySize.getWidth(), dynamicPanelHeight));
	}
	
	private void createTermButtons(List<? extends Term> termList, int buttonWidth, int buttonHeight) {

		int GERMAN = 1;
		int SPANISH = 2;
		
		for (Term term : termList) {
			for (Translations translation : term.getTranslationList()) {
				if (translation.getLanguages().getId() == GERMAN) {
					TermButton TermButton = new TermButton(term.getId(), translation.getId(), GERMAN, translation.getName());
					WinUtil.configButton(TermButton, buttonWidth, buttonHeight, BorderFactory.createLineBorder(WinUtil.COOL_BLUE), WinUtil.COOL_BLUE, WinUtil.LIGHT_BLACK);
					TermButtonsDE.add(TermButton);
				}
				if (translation.getLanguages().getId() == SPANISH) {
					TermButton TermButton = new TermButton(term.getId(), translation.getId(), SPANISH, translation.getName());
					WinUtil.configButton(TermButton, buttonWidth, buttonHeight, BorderFactory.createLineBorder(WinUtil.STRONG_ORANGE), WinUtil.STRONG_ORANGE, WinUtil.LIGHT_BLACK);
					TermButtonsES.add(TermButton);
				}
			}
		}
	}

	private void calculateGrid(int numbersOfButtons, int panelWidth, int inset, int buttonWidth, int buttonHeight) {
		
		gridX = panelWidth / (buttonWidth + 2*inset) ;
		gridY = numbersOfButtons / gridX;
		
		if (numbersOfButtons % gridX != 0) {
			gridY++;
		}
		dynamicPanelHeight = gridY*buttonHeight + gridY*2*inset;
	}

	private void arrangeTermButton(int numbersOfSpecialties, int inset) {
		
		int posX = 1;
		int posY = 1;
		int weightY = 0;
		
		for (int i = 0; i < numbersOfSpecialties; i++) {
			
			GridBagLayoutUtilities.addGB(germanTermPanel, TermButtonsDE.get(i), posX, posY, 0, weightY, GridBagConstraints.NORTH, new Insets(inset, inset, inset, inset));
			GridBagLayoutUtilities.addGB(spanishTermPanel, TermButtonsES.get(i), posX, posY, 0, weightY, GridBagConstraints.NORTH, new Insets(inset, inset, inset, inset));
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
	
	public void setSpecialtiesButtonsActionListener(ActionListener l) {
		
		for (int i = 0; i < numbersOfSpecialties; i++) {
			TermButtonsDE.get(i).addActionListener(l);
			TermButtonsES.get(i).addActionListener(l);
		}
	}
	
	public int getDynamicPanelHeight() {
		return dynamicPanelHeight;
	}

	public String getSearchWord() {
		return searchWord;
	}
}
