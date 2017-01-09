package viewFactory;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
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

import enums.ChosenLanguage;
import utilities.WinUtil;
import eventHandling.PanelEventTransferObject;
import eventHandling.Updatable;
import interactElements.SearchComboBox;

public class HeadBar extends JPanel implements Updatable {

	private JButton[] alphabetButtons = new JButton[26];
	private JLabel headDescriptionLabel, separatorLabel;
	private JPanel leftPanel, rightPanel, centerPanel;
	private JPanel alphabetBackgroundPanel;
	private JButton specialtyButton, newTechnicalTermButton, searchButton;
	private JButton deButton, esButton;
	private SearchComboBox searchComboBox;

	private int panelWidth;
	private int panelHeight;
	private int sidePanelWidth;
	boolean[] alphabet;

	private ResourceBundle languageBundle;
	

	public HeadBar(double MAINFRAME_DISPLAY_RATIO, ResourceBundle languageBundle, SearchComboBox searchComboBox, boolean[] alphabet) {

		Dimension displayResolution = Toolkit.getDefaultToolkit().getScreenSize();
		panelWidth = (int) (displayResolution.getWidth() * MAINFRAME_DISPLAY_RATIO);
		panelHeight = (int) (WinUtil.relH(100));
		sidePanelWidth = panelWidth/4;
		this.languageBundle = languageBundle;
		this.searchComboBox = searchComboBox;
		this.alphabet = alphabet;

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
		WinUtil.configStaticLabel(headDescriptionLabel, sidePanelWidth - WinUtil.relW(250), WinUtil.relH(10), WinUtil.relW(500), WinUtil.relH(25), Color.WHITE, Color.DARK_GRAY, 13, Font.BOLD);
		centerPanel.add(headDescriptionLabel);
		
		drawAlphabet(panelWidth, panelHeight);
		alphabetBackgroundPanel = new JPanel();
		alphabetBackgroundPanel.setBounds(sidePanelWidth - WinUtil.relW(260), WinUtil.relH(36), WinUtil.relW(520), WinUtil.relH(28));
		alphabetBackgroundPanel.setBackground(WinUtil.DARKER_GRAY);
		centerPanel.add(alphabetBackgroundPanel);
		
		specialtyButton = new JButton(languageBundle.getString("subjectsBtn"));
		WinUtil.configStaticButton(specialtyButton, sidePanelWidth - WinUtil.relW(100), WinUtil.relH(72), WinUtil.relW(200), WinUtil.relH(20), BorderFactory.createLineBorder(Color.GRAY), WinUtil.ULTRA_LIGHT_GRAY, Color.DARK_GRAY);
		centerPanel.add(specialtyButton);
	}
	
	private void createGuiElementsOfRightPanel() {

		deButton = new JButton("DE");
		WinUtil.configStaticButton(deButton, sidePanelWidth - WinUtil.relW(83), WinUtil.relH(10), WinUtil.relW(25), WinUtil.relH(25), new EmptyBorder(0, 0, 0, 0), Color.WHITE, Color.DARK_GRAY);
		rightPanel.add(deButton);

		separatorLabel = new JLabel("/");
		WinUtil.configStaticLabel(separatorLabel, sidePanelWidth - WinUtil.relW(53), WinUtil.relH(10), WinUtil.relW(5), WinUtil.relH(25), Color.WHITE, Color.DARK_GRAY, 12, Font.PLAIN);
		rightPanel.add(separatorLabel);
		
		esButton = new JButton("ES");
		WinUtil.configStaticButton(esButton, sidePanelWidth - WinUtil.relW(45), WinUtil.relH(10), WinUtil.relW(25), WinUtil.relH(25), new EmptyBorder(0, 0, 0, 0), Color.WHITE, Color.DARK_GRAY);
		rightPanel.add(esButton);
		
		newTechnicalTermButton = new JButton(languageBundle.getString("newEntryBtn"));
		WinUtil.configStaticButton(newTechnicalTermButton, sidePanelWidth - WinUtil.relW(280), WinUtil.relH(50), WinUtil.relW(260), WinUtil.relH(30), BorderFactory.createLineBorder(Color.BLACK), Color.WHITE, Color.DARK_GRAY);
		rightPanel.add(newTechnicalTermButton);

	}
	
	private void drawAlphabet(int PANEL_WIDTH, int PANEL_HEIGHT) {

		for (char letter = 'A'; letter <= 'Z'; letter++) {
			int arrayPosition = letter - 65;
			int xLetterOffSet = (int) (arrayPosition * WinUtil.relW(18));
			
			alphabetButtons[arrayPosition] = new JButton(String.valueOf(letter));
			WinUtil.configStaticButton(alphabetButtons[arrayPosition], sidePanelWidth - WinUtil.relW(234) + xLetterOffSet, WinUtil.relH(40), WinUtil.relW(14), WinUtil.relH(20), BorderFactory.createLineBorder(Color.GRAY), WinUtil.ULTRA_LIGHT_GRAY, WinUtil.DARKER_GRAY);
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
		repaint();
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
		
		headDescriptionLabel.setBounds(centerPanelHalfWidth - WinUtil.relW(250), WinUtil.relH(10), WinUtil.relW(500), WinUtil.relH(25));
		specialtyButton.setBounds(centerPanelHalfWidth - WinUtil.relW(100), WinUtil.relH(72), WinUtil.relW(200), WinUtil.relH(20));
		alphabetBackgroundPanel.setBounds(centerPanelHalfWidth - WinUtil.relW(260), WinUtil.relH(36), WinUtil.relW(520), WinUtil.relH(28));
		
		for (int arrayPosition = 0; arrayPosition < 26; arrayPosition++) {
			int xLetterOffSet = arrayPosition * WinUtil.relW(18);
			alphabetButtons[arrayPosition].setBounds(centerPanelHalfWidth - WinUtil.relW(234) + xLetterOffSet, WinUtil.relH(40), WinUtil.relW(14), WinUtil.relH(20));
		}
	}

	private void changeGuiTextLanguage() {

		searchButton.setText(languageBundle.getString("searchBtn"));
		headDescriptionLabel.setText(languageBundle.getString("headDescriptionLabel"));
		specialtyButton.setText(languageBundle.getString("subjectsBtn"));
		newTechnicalTermButton.setText(languageBundle.getString("newEntryBtn"));
	}

	private void checkAvailabilityLetters(boolean[] alphabet) {
	
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
