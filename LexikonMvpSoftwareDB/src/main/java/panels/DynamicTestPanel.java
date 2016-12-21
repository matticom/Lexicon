package panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import interactElements.SpecialtyButton;
import model.Specialty;
import model.Translations;
import utilities.GridBagLayoutUtilities;



public class DynamicTestPanel extends JPanel {

	private JPanel germanSpecialtyPanel, spanishSpecialtyPanel;
	private int gridX, gridY;
	private ArrayList<SpecialtyButton> specialtyButtonsDE = new ArrayList<SpecialtyButton>();
	private ArrayList<SpecialtyButton> specialtyButtonsES = new ArrayList<SpecialtyButton>();

	public DynamicTestPanel(int mainFrameWidth, int mainFrameHeight, List<Specialty> specialtyList) {
	
		buildUp(mainFrameWidth, mainFrameHeight, specialtyList);
	}
	
	private void buildUp(int mainFrameWidth, int mainFrameHeight, List<Specialty> specialtyList) {
		
		this.setBackground(Color.GREEN);
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.setPreferredSize(new Dimension(mainFrameWidth, 900));

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
		
		int numbersOfSpecialties = specialtyList.size();
		
		createSpecialtyButtons(specialtyList);
		calculateGrid(numbersOfSpecialties, mainFrameWidth/2);
		resizeGuiElements();
	}
	
	private void createSpecialtyButtons(List<Specialty> specialtyList) {

		int GERMAN = 1;
		int SPANISH = 2;
		
		for (Specialty specialty : specialtyList) {
			for (Translations translation : specialty.getTranslationList()) {
				if (translation.getLanguages().getId() == GERMAN) {
					SpecialtyButton specialtyButton = new SpecialtyButton(specialty.getId(), translation.getId(), GERMAN, translation.getName());
					specialtyButton.setPreferredSize(new Dimension(100, 30));
					specialtyButtonsDE.add(specialtyButton);
				}
				if (translation.getLanguages().getId() == SPANISH) {
					SpecialtyButton specialtyButton = new SpecialtyButton(specialty.getId(), translation.getId(), SPANISH, translation.getName());
					specialtyButton.setPreferredSize(new Dimension(100, 30));
					specialtyButtonsES.add(specialtyButton);
				}
			}
		}
	}

	private void calculateGrid(int numbersOfButtons, int panelWidth) {
		int insetsX = 20;
		gridX = panelWidth / (100+2*insetsX) ;
		gridY = numbersOfButtons/gridX;
		if (numbersOfButtons % gridX != 0) {
			gridY++;
		}
	}

	private void resizeGuiElements() {
		resizeSpecialtyButton();

	}

	private void resizeSpecialtyButton() {
		
		int posX = 1;
		int posY = 1;
		int weightY = 0;
		for (SpecialtyButton specialtyButton : specialtyButtonsDE) {
			
			GridBagLayoutUtilities.addGB(germanSpecialtyPanel, specialtyButton, posX, posY, 0, weightY, GridBagConstraints.NORTH, new Insets(20, 20, 20, 20));
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
	

}
