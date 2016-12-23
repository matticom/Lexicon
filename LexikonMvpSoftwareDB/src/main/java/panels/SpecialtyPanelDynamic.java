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

import interactElements.SpecialtyButton;
import model.Specialty;
import model.Translations;
import utilities.GridBagLayoutUtilities;
import utilities.WinUtil;

public class SpecialtyPanelDynamic extends JPanel {

	private JPanel germanSpecialtyPanel, spanishSpecialtyPanel;
	private int gridX, gridY;
	private ArrayList<SpecialtyButton> specialtyButtonsDE;
	private ArrayList<SpecialtyButton> specialtyButtonsES;
	private int numbersOfSpecialties;
	private int dynamicPanelHeight;

	public SpecialtyPanelDynamic(int mainFrameWidth, int mainFrameHeight, List<Specialty> specialtyList) {
	
		specialtyButtonsDE = new ArrayList<SpecialtyButton>();
		specialtyButtonsES = new ArrayList<SpecialtyButton>();
		buildUp(mainFrameWidth, mainFrameHeight, specialtyList);
	}
	
	private void buildUp(int mainFrameWidth, int mainFrameHeight, List<Specialty> specialtyList) {
		
		this.setBackground(Color.GREEN);
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		germanSpecialtyPanel = new JPanel();
		germanSpecialtyPanel.setBackground(Color.WHITE);
		germanSpecialtyPanel.setLayout(new GridBagLayout());
		germanSpecialtyPanel.setPreferredSize(new Dimension(mainFrameWidth/2, 900));

		spanishSpecialtyPanel = new JPanel();
		spanishSpecialtyPanel.setBackground(Color.GRAY);
		spanishSpecialtyPanel.setLayout(new GridBagLayout());
		spanishSpecialtyPanel.setPreferredSize(new Dimension(mainFrameWidth/2, 900));

		this.add(germanSpecialtyPanel);
		this.add(spanishSpecialtyPanel);
		
		Dimension displaySize = Toolkit.getDefaultToolkit().getScreenSize();
		numbersOfSpecialties = specialtyList.size();
		int buttonWidth = (int) (displaySize.getWidth() * 200/1920);
		int buttonHeight = (int) (displaySize.getHeight() * 30/1200);
		int inset = (int) (displaySize.getWidth() * 20/1920);
		
		createSpecialtyButtons(specialtyList, buttonWidth, buttonHeight);
		calculateGrid(numbersOfSpecialties, mainFrameWidth/2, inset, buttonWidth, buttonHeight);
		arrangeSpecialtyButton(numbersOfSpecialties, inset);
		
		this.setPreferredSize(new Dimension(mainFrameWidth, dynamicPanelHeight));
		this.setMinimumSize(new Dimension(0, dynamicPanelHeight));
		this.setMaximumSize(new Dimension((int) displaySize.getWidth(), dynamicPanelHeight));
	}
	
	private void createSpecialtyButtons(List<Specialty> specialtyList, int buttonWidth, int buttonHeight) {

		int GERMAN = 1;
		int SPANISH = 2;
		
		for (Specialty specialty : specialtyList) {
			for (Translations translation : specialty.getTranslationList()) {
				if (translation.getLanguages().getId() == GERMAN) {
					SpecialtyButton specialtyButton = new SpecialtyButton(specialty.getId(), translation.getId(), GERMAN, translation.getName());
					WinUtil.configButton(specialtyButton, buttonWidth, buttonHeight, BorderFactory.createLineBorder(WinUtil.COOL_BLUE), WinUtil.COOL_BLUE, WinUtil.LIGHT_BLACK);
					specialtyButtonsDE.add(specialtyButton);
				}
				if (translation.getLanguages().getId() == SPANISH) {
					SpecialtyButton specialtyButton = new SpecialtyButton(specialty.getId(), translation.getId(), SPANISH, translation.getName());
					WinUtil.configButton(specialtyButton, buttonWidth, buttonHeight, BorderFactory.createLineBorder(WinUtil.STRONG_ORANGE), WinUtil.STRONG_ORANGE, WinUtil.LIGHT_BLACK);
					specialtyButtonsES.add(specialtyButton);
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

	private void arrangeSpecialtyButton(int numbersOfSpecialties, int inset) {
		
		int posX = 1;
		int posY = 1;
		int weightY = 0;
		
		for (int i = 0; i < numbersOfSpecialties; i++) {
			
			GridBagLayoutUtilities.addGB(germanSpecialtyPanel, specialtyButtonsDE.get(i), posX, posY, 0, weightY, GridBagConstraints.NORTH, new Insets(inset, inset, inset, inset));
			GridBagLayoutUtilities.addGB(spanishSpecialtyPanel, specialtyButtonsES.get(i), posX, posY, 0, weightY, GridBagConstraints.NORTH, new Insets(inset, inset, inset, inset));
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
			specialtyButtonsDE.get(i).addActionListener(l);
			specialtyButtonsES.get(i).addActionListener(l);
		}
	}
	
	public int getDynamicPanelHeight() {
		return dynamicPanelHeight;
	}

}
