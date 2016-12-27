package viewFactory;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import utilities.WinUtil;
import eventHandling.ChosenLanguage;
import eventHandling.PanelEventTransferObject;
import eventHandling.Updatable;
import interactElements.SearchComboBox;

public class HeadBar extends JPanel implements Updatable {

	private JButton[] alphabetButtons = new JButton[26];
	private JLabel headDescriptionLabel, separatorLabel;
	private JPanel leftPanel, rightPanel, centerPanel;
	private JPanel alphabetBackgroundPanel;
	private JButton specialtyButton, deButton, esButton, newTechnicalTermButton, searchButton;
	private SearchComboBox searchComboBox;

	private int panelWidth;
	private int panelHeight;
	private int sidePanelWidth;
	private Dimension displaySize;

	private ResourceBundle languageBundle;
	

	public HeadBar(Dimension displaySize, int mainFrameWidth, ResourceBundle languageBundle, SearchComboBox searchComboBox) {

		this.displaySize = displaySize;
		panelWidth = mainFrameWidth;
		panelHeight = (int) (displaySize.getHeight() * 0.0833);
		this.languageBundle = languageBundle;
		sidePanelWidth = panelWidth/4;
		this.searchComboBox = searchComboBox;

		initialize();
	}

	private void initialize() {

		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.setPreferredSize(new Dimension(panelWidth, panelHeight));
		this.setBackground(Color.DARK_GRAY);
		
		createPanelArchitecture();
		createGuiElementsOfLeftPanel();
		createGuiElementsOfCenterPanel();
		createGuiElementsOfRightPanel();
		leftPanel.add(searchComboBox);
		
		this.add(leftPanel);
		this.add(centerPanel);
		this.add(rightPanel);
		
	}
	
	private void createPanelArchitecture() {
		
		leftPanel = new JPanel(null);
		leftPanel.setPreferredSize(new Dimension(sidePanelWidth, panelHeight));
		leftPanel.setBackground(Color.DARK_GRAY);
		centerPanel = new JPanel(null);
		centerPanel.setPreferredSize(new Dimension(sidePanelWidth*2, panelHeight));
		centerPanel.setBackground(Color.DARK_GRAY);
		rightPanel = new JPanel(null);
		rightPanel.setPreferredSize(new Dimension(sidePanelWidth, panelHeight));
		rightPanel.setBackground(Color.DARK_GRAY);
		
	}
	
	private void createGuiElementsOfLeftPanel() {
		
		searchButton = new JButton(languageBundle.getString("searchBtn"));
		WinUtil.configStaticButton(searchButton, WinUtil.relW(20), WinUtil.relH(57), WinUtil.relW(260), WinUtil.relH(26), 
									BorderFactory.createLineBorder(Color.BLACK), Color.WHITE, Color.DARK_GRAY);
		leftPanel.add(searchButton);
	}
	
	private void createGuiElementsOfCenterPanel() {

		headDescriptionLabel = new JLabel(languageBundle.getString("headDescriptionLabel"));
		WinUtil.configStaticLabel(headDescriptionLabel, (int)(sidePanelWidth - displaySize.width*0.1302),
				 (int)(displaySize.height*0.0083), (int)(displaySize.width*0.2604), (int)(displaySize.height*0.0208), Color.WHITE, Color.DARK_GRAY, 13, Font.BOLD);
		centerPanel.add(headDescriptionLabel);
		
		drawAlphabet(panelWidth, panelHeight);
		alphabetBackgroundPanel = new JPanel();
		alphabetBackgroundPanel.setBounds((int)(sidePanelWidth - displaySize.width*0.1354), (int)(displaySize.height*0.0300), (int)(displaySize.width*0.2708), (int)(displaySize.height*0.0233));
		alphabetBackgroundPanel.setBackground(WinUtil.DARKER_GRAY);
		centerPanel.add(alphabetBackgroundPanel);
		
		specialtyButton = new JButton(languageBundle.getString("subjectsBtn"));
		WinUtil.configStaticButton(specialtyButton, (int)(sidePanelWidth - displaySize.width*0.0521), (int)(displaySize.height*0.0600), 
									(int)(displaySize.width*0.1042), (int)(displaySize.height*0.0167), BorderFactory.createLineBorder(Color.GRAY), WinUtil.ULTRA_LIGHT_GRAY, Color.DARK_GRAY);
		centerPanel.add(specialtyButton);
	}
	
	private void createGuiElementsOfRightPanel() {

		deButton = new JButton("DE");
		WinUtil.configStaticButton(deButton, (int)(sidePanelWidth - displaySize.width*0.0432), (int)(displaySize.height*0.0083), (int)(displaySize.width*0.0130), (int)(displaySize.height*0.0208), 
									new EmptyBorder(0, 0, 0, 0), Color.WHITE, Color.DARK_GRAY);
		rightPanel.add(deButton);

		separatorLabel = new JLabel("/");
		WinUtil.configStaticLabel(separatorLabel, (int)(sidePanelWidth - displaySize.width*0.0276), (int)(displaySize.height*0.0083), (int)(displaySize.width*0.0026), (int)(displaySize.height*0.0208),
									Color.WHITE, Color.DARK_GRAY, 12, Font.PLAIN);
		rightPanel.add(separatorLabel);

		esButton = new JButton("ES");
		WinUtil.configStaticButton(esButton, (int)(sidePanelWidth - displaySize.width*0.0234), (int)(displaySize.height*0.0083), (int)(displaySize.width*0.0130), (int)(displaySize.height*0.0208), 
				new EmptyBorder(0, 0, 0, 0), Color.WHITE, Color.DARK_GRAY);
		rightPanel.add(esButton);
		
		newTechnicalTermButton = new JButton(languageBundle.getString("newEntryBtn"));
		WinUtil.configStaticButton(newTechnicalTermButton, (int)(sidePanelWidth - displaySize.width*0.1458), (int)(displaySize.height*0.0417),
									(int)(displaySize.width*0.1354), (int)(displaySize.height*0.0250), BorderFactory.createLineBorder(Color.BLACK), Color.WHITE, Color.DARK_GRAY);
		rightPanel.add(newTechnicalTermButton);
	}
	
	private void drawAlphabet(int PANEL_WIDTH, int PANEL_HEIGHT) {

		for (char letter = 'A'; letter <= 'Z'; letter++) {
			int arrayPosition = letter - 65;
			int xLetterOffSet = (int) (arrayPosition * 0.0094 * displaySize.width);
			
			alphabetButtons[arrayPosition] = new JButton(String.valueOf(letter));
			WinUtil.configStaticButton(alphabetButtons[arrayPosition], (int)(sidePanelWidth - displaySize.width*0.1219 + xLetterOffSet), (int)(displaySize.height*0.0333), 
										(int)(displaySize.width*0.0073), (int)(displaySize.height*0.0167), BorderFactory.createLineBorder(Color.GRAY), WinUtil.ULTRA_LIGHT_GRAY, WinUtil.DARKER_GRAY);
			alphabetButtons[arrayPosition].setActionCommand(String.valueOf(letter) + "%");
			centerPanel.add(alphabetButtons[arrayPosition]);
		}
	}

	@Override
	public void updatePanel(PanelEventTransferObject e) {
		
		setLanguage(e.getCurrentLanguage());
		changeComponentsSizeOnResize(e.getMainFrameWidth(), e.getMainFrameHeight());
		changeGuiTextLanguage();
		checkAvailabilityLetters(e.getAvailableLetters());
	}

	private void setLanguage(ChosenLanguage currentLanguage) {
		
		if (currentLanguage == ChosenLanguage.German) {
			deButton.setBorder(BorderFactory.createLineBorder(Color.GRAY));
			esButton.setBorder( new EmptyBorder(0, 0, 0, 0));
			languageBundle = ResourceBundle.getBundle("languageBundles.lexikon", new Locale("de"));
		} else {
			esButton.setBorder(BorderFactory.createLineBorder(Color.GRAY));
			deButton.setBorder( new EmptyBorder(0, 0, 0, 0));
			languageBundle = ResourceBundle.getBundle("languageBundles.lexikon", new Locale("es"));
		}
	}
	
	private void changeComponentsSizeOnResize(int mainFrameWidth, int mainFrameHeight) {

		int centerPanelHalfWidth = (mainFrameWidth - 2*sidePanelWidth) / 2;
		centerPanel.setPreferredSize(new Dimension(centerPanelHalfWidth*2, panelHeight));
		
		headDescriptionLabel.setBounds((int)(centerPanelHalfWidth - displaySize.width*0.1302),
				 (int) (displaySize.height*0.0083), (int) (displaySize.width*0.2604), (int) (displaySize.height*0.0208));
		specialtyButton.setBounds((int) (centerPanelHalfWidth - displaySize.width*0.0521),
				 (int) (displaySize.height*0.0600), (int) (displaySize.width*0.1042), (int) (displaySize.height*0.0167));
		alphabetBackgroundPanel.setBounds((int)(centerPanelHalfWidth - displaySize.width*0.1354),
				 (int) (displaySize.height*0.0300), (int) (displaySize.width*0.2708), (int) (displaySize.height*0.0233));
		
		for (int arrayPosition = 0; arrayPosition < 26; arrayPosition++) {
			int xLetterOffSet = (int) (arrayPosition * 0.0094 * displaySize.width);
			alphabetButtons[arrayPosition].setBounds((int) (centerPanelHalfWidth - displaySize.width*0.1219 + xLetterOffSet), 
					(int) (displaySize.height*0.0338), (int) (displaySize.width*0.0073), (int) (displaySize.height*0.0167));
		}
	}

	private void changeGuiTextLanguage() {

		searchButton.setText(languageBundle.getString("searchBtn"));
		headDescriptionLabel.setText(languageBundle.getString("headDescriptionLabel"));
		specialtyButton.setText(languageBundle.getString("subjectsBtn"));
		newTechnicalTermButton.setText(languageBundle.getString("newEntryBtn"));
	}

	private void checkAvailabilityLetters(boolean[] alphabet) {
		// Checkt ob es Begriffseinträge mit entsprechenden Anfangsbuchstaben
		// gibt -> wenn ja: Buchstaben umrahmen
		for (int arrayPosition = 0; arrayPosition < 26; arrayPosition++) {

			if (alphabet[arrayPosition]) {
				alphabetButtons[arrayPosition].setEnabled(true);
				alphabetButtons[arrayPosition].setBorder(BorderFactory.createLineBorder(Color.GRAY));
				alphabetButtons[arrayPosition].setFocusable(true);
			} else {
				alphabetButtons[arrayPosition].setEnabled(false);
				alphabetButtons[arrayPosition].setBorder(new EmptyBorder(0, 0, 0, 0));
				alphabetButtons[arrayPosition].setFocusable(false);
			}
		}
	}

	public void setSearchButtonActionListener(ActionListener l) {
		searchButton.addActionListener(l);
	}

	public void setSpecialtyButtonActionListener(ActionListener l) {
		specialtyButton.addActionListener(l);
	}

	public void setDeButtonActionListener(ActionListener l) {
		deButton.addActionListener(l);
	}

	public void setEsButtonActionListener(ActionListener l) {
		esButton.addActionListener(l);
	}

	public void setNewTechnicalTermButtonActionListener(ActionListener l) {
		newTechnicalTermButton.addActionListener(l);
	}

	public void setAlphabetButtonActionListener(ActionListener l) {
		for (char letter = 'A'; letter <= 'Z'; letter++) {
			alphabetButtons[letter - 65].addActionListener(l);
		}

	}

}
