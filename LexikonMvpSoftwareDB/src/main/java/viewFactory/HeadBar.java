package viewFactory;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.FocusListener;
import java.awt.event.KeyListener;
import java.sql.ResultSet;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.text.JTextComponent;

import utilities.ListItem;
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
	SearchComboBox searchComboBox;

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
		
		searchButton = WinUtil.createButton(languageBundle.getString("searchBtn"), (int)(displaySize.width*0.0104),
				 (int) (displaySize.height*0.0475), (int) (displaySize.width*0.1354), (int) (displaySize.height*0.0217), BorderFactory.createLineBorder(Color.BLACK),
				Color.DARK_GRAY, null, null, null, false, false, Color.WHITE);
		leftPanel.add(searchButton);
	}
	
	private void createGuiElementsOfCenterPanel() {

		headDescriptionLabel = WinUtil.createLabel(languageBundle.getString("headDescriptionLabel"), (int)(sidePanelWidth - displaySize.width*0.1302),
				 (int) (displaySize.height*0.0083), (int) (displaySize.width*0.2604), (int) (displaySize.height*0.0208), new EmptyBorder(0, 0, 0, 0),
				Color.DARK_GRAY, null, null, Color.WHITE);
		headDescriptionLabel.setFont(headDescriptionLabel.getFont().deriveFont(Font.BOLD, 13));
		headDescriptionLabel.setHorizontalAlignment(SwingConstants.CENTER);
		centerPanel.add(headDescriptionLabel);
		
		drawAlphabet(panelWidth, panelHeight);
		alphabetBackgroundPanel = new JPanel();
		alphabetBackgroundPanel.setBounds((int)(sidePanelWidth - displaySize.width*0.1354),
				 (int) (displaySize.height*0.0300), (int) (displaySize.width*0.2708), (int) (displaySize.height*0.0233));
		alphabetBackgroundPanel.setBackground(WinUtil.DARKER_GRAY);
		centerPanel.add(alphabetBackgroundPanel);
		
		specialtyButton = WinUtil.createButton(languageBundle.getString("subjectsBtn"), (int) (sidePanelWidth - displaySize.width*0.0521),
				 (int) (displaySize.height*0.0600), (int) (displaySize.width*0.1042), (int) (displaySize.height*0.0167),
				BorderFactory.createLineBorder(Color.GRAY), Color.DARK_GRAY, null, null, null, false, false, WinUtil.ULTRA_LIGHT_GRAY);
		centerPanel.add(specialtyButton);
	}
	
	private void createGuiElementsOfRightPanel() {

		deButton = WinUtil.createButton("DE", (int) (sidePanelWidth - displaySize.width*0.0432),
				 (int) (displaySize.height*0.0083), (int) (displaySize.width*0.0130), (int) (displaySize.height*0.0208), new EmptyBorder(0, 0, 0, 0), Color.BLACK, null, "DE", null, false, false, Color.WHITE);
		rightPanel.add(deButton);

		separatorLabel = WinUtil.createLabel("/", (int) (sidePanelWidth - displaySize.width*0.0276),
				 (int) (displaySize.height*0.0083), (int) (displaySize.width*0.0026), (int) (displaySize.height*0.0208), new EmptyBorder(0, 0, 0, 0), Color.DARK_GRAY, "separator", null, Color.WHITE);
		rightPanel.add(separatorLabel);

		esButton = WinUtil.createButton("ES", (int) (sidePanelWidth - displaySize.width*0.0234), (int) (displaySize.height*0.0083), (int) (displaySize.width*0.0130), (int) (displaySize.height*0.0208), new EmptyBorder(0, 0, 0, 0), Color.DARK_GRAY, null, "ES", null, false, false, Color.WHITE);
		rightPanel.add(esButton);
		
		newTechnicalTermButton = WinUtil.createButton(languageBundle.getString("newEntryBtn"), (int) (sidePanelWidth - displaySize.width*0.1458),
				 (int) (displaySize.height*0.0417), (int) (displaySize.width*0.1354), (int) (displaySize.height*0.0250),
				BorderFactory.createLineBorder(Color.BLACK), Color.DARK_GRAY, null, null, null, false, false, Color.WHITE);
		rightPanel.add(newTechnicalTermButton);
	}
	
	private void drawAlphabet(int PANEL_WIDTH, int PANEL_HEIGHT) {

		for (char letter = 'A'; letter <= 'Z'; letter++) {
			int arrayPosition = letter - 65;
			int xLetterOffSet = (int) (arrayPosition * 0.0094 * displaySize.width);
			
			alphabetButtons[arrayPosition] = WinUtil.createButton(String.valueOf(letter), (int) (sidePanelWidth - displaySize.width*0.1219 + xLetterOffSet),
											 (int) (displaySize.height*0.0333), (int) (displaySize.width*0.0073), (int) (displaySize.height*0.0167), BorderFactory.createLineBorder(Color.GRAY),
											 Color.DARK_GRAY, null, String.valueOf(letter), String.valueOf(letter), false, false, WinUtil.ULTRA_LIGHT_GRAY);
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

	public JPanel getLeftPanel() {
		return leftPanel;
	}
}
