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

public class HeadBar extends JPanel implements Updatable {

	private JButton[] alphabetButtons = new JButton[26];
	private JLabel headDescriptionLabel, separatorLabel;
	private JPanel leftPanel, middlePanel, rightPanel, centerPanel; 
	

	private JPanel backgroundPanel;

	private JButton specialtyButton, deButton, esButton, newTechnicalTermButton, searchButton;

	private int panelWidth;
	private int panelHeight;
	private double widthRatio;
	private int sidePanelWidth;

	private final double HEADPANEL_MAINFRAME_RATIO = 0.125;

	private ResourceBundle languageBundle;

	public HeadBar(Dimension displaySize, int mainFrameWidth, ResourceBundle languageBundle) {

		
		panelWidth = mainFrameWidth;
		panelHeight = (int) (displaySize.getHeight() * 0.0833);
//		widthRatio = 
		this.languageBundle = languageBundle;

		initialize();
	}

	private void initialize() {

		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		this.setPreferredSize(new Dimension(panelWidth, panelHeight));
		this.setBackground(Color.DARK_GRAY);
		sidePanelWidth = panelWidth/4;
		
		leftPanel = new JPanel(null);
		leftPanel.setPreferredSize(new Dimension(sidePanelWidth, panelHeight));
		leftPanel.setBackground(Color.DARK_GRAY);
		centerPanel = new JPanel(null);
		centerPanel.setPreferredSize(new Dimension(sidePanelWidth*2, panelHeight));
		centerPanel.setBackground(Color.DARK_GRAY);
		rightPanel = new JPanel(null);
		rightPanel.setPreferredSize(new Dimension(sidePanelWidth, panelHeight));
		rightPanel.setBackground(Color.DARK_GRAY);

		// Such-Button
		searchButton = WinUtil.createButton(languageBundle.getString("searchBtn"), 20, 57, 260, 26, BorderFactory.createLineBorder(Color.BLACK),
				Color.DARK_GRAY, null, null, null, false, false, Color.WHITE);
		leftPanel.add(searchButton);//40, 22, 260, 25

		// Beschreibungslabel
		headDescriptionLabel = WinUtil.createLabel(languageBundle.getString("headDescriptionLabel"), panelWidth/4 - 250, 10, 500, 25, new EmptyBorder(0, 0, 0, 0),
				Color.DARK_GRAY, null, null, Color.WHITE);
		headDescriptionLabel.setFont(headDescriptionLabel.getFont().deriveFont(Font.BOLD, 13));
		headDescriptionLabel.setHorizontalAlignment(SwingConstants.CENTER);
		centerPanel.add(headDescriptionLabel);

		// Fachgebiete darstellen
		specialtyButton = WinUtil.createButton(languageBundle.getString("subjectsBtn"), panelWidth/4 - 100, 68, 200, 20,
				BorderFactory.createLineBorder(Color.GRAY), Color.DARK_GRAY, null, null, null, false, false, WinUtil.ULTRA_LIGHT_GRAY);
		centerPanel.add(specialtyButton);

		// HintergrundPanel
		backgroundPanel = new JPanel();
		backgroundPanel.setBounds(panelWidth/4 - 260, 36, 520, 28);
		backgroundPanel.setBackground(WinUtil.DARKER_GRAY);

		// Sprachauswahlbuttons
		deButton = WinUtil.createButton("DE", panelWidth/4-83, 10, 25, 25, new EmptyBorder(0, 0, 0, 0), Color.BLACK, null, "DE", null, false, false,
				Color.WHITE);
		rightPanel.add(deButton);

		separatorLabel = WinUtil.createLabel("/", panelWidth/4-53, 10, 5, 25, new EmptyBorder(0, 0, 0, 0), Color.DARK_GRAY, "separator", null, Color.WHITE);
		rightPanel.add(separatorLabel);

		esButton = WinUtil.createButton("ES", panelWidth/4-45, 10, 25, 25, new EmptyBorder(0, 0, 0, 0), Color.DARK_GRAY, null, "ES", null, false, false,
				Color.WHITE);
		rightPanel.add(esButton);

		// Neuer Eintrag Button
		newTechnicalTermButton = WinUtil.createButton(languageBundle.getString("newEntryBtn"), panelWidth/4-280, 50, 260, 30,
				BorderFactory.createLineBorder(Color.BLACK), Color.DARK_GRAY, null, null, null, false, false, Color.WHITE);
		rightPanel.add(newTechnicalTermButton);

		drawAlphabet(panelWidth, panelHeight);
		centerPanel.add(backgroundPanel);
		
		this.add(leftPanel);
		this.add(centerPanel);
		this.add(rightPanel);
		
	}

	private void drawAlphabet(int PANEL_WIDTH, int PANEL_HEIGHT) {

		for (char letter = 'A'; letter <= 'Z'; letter++) {
			int arrayPosition = letter - 65;
			int xLetterOffSet = (int) (arrayPosition * 18);
			
			alphabetButtons[arrayPosition] = WinUtil.createButton(String.valueOf(letter), (int) (panelWidth/4-234 + xLetterOffSet),
											 (int) (40), (int) (14), (int) (20), BorderFactory.createLineBorder(Color.GRAY),
											 Color.DARK_GRAY, null, String.valueOf(letter), String.valueOf(letter), false, false, WinUtil.ULTRA_LIGHT_GRAY);
			alphabetButtons[arrayPosition].setActionCommand(String.valueOf(letter) + "%");
			centerPanel.add(alphabetButtons[arrayPosition]);
		}
	}

	@Override
	public void updatePanel(PanelEventTransferObject e) {

		if (e.getCurrentLanguage() == ChosenLanguage.German) {
			deButton.setBorder(BorderFactory.createLineBorder(Color.GRAY));
			languageBundle = ResourceBundle.getBundle("languageBundles.lexikon", new Locale("de"));
		} else {
			esButton.setBorder(BorderFactory.createLineBorder(Color.GRAY));
			languageBundle = ResourceBundle.getBundle("languageBundles.lexikon", new Locale("es"));
		}
		int centerPanelHalfWidth = (e.getMainFrameWidth()-2*sidePanelWidth)/2;
		centerPanel.setPreferredSize(new Dimension(centerPanelHalfWidth*2, panelHeight));
		headDescriptionLabel.setBounds(centerPanelHalfWidth - 250, 10, 500, 25);
		specialtyButton.setBounds(centerPanelHalfWidth - 100, 68, 200, 20);
		backgroundPanel.setBounds(centerPanelHalfWidth - 260, 36, 520, 28);
		for (int arrayPosition = 0; arrayPosition < 26; arrayPosition++) {
			int xLetterOffSet = (int) (arrayPosition * 18);
			alphabetButtons[arrayPosition].setBounds((int) (centerPanelHalfWidth-234 + xLetterOffSet), (int) (40), (int) (14), (int) (20));
		}
//		changeComponentsSizeOnResize(e.getMainFrameWidth(), e.getMainFrameHeight());
		changeGuiTextLanguage();
		checkAvailabilityLetters(e.getAvailableLetters());
	}

	private void changeComponentsSizeOnResize(int mainFrameWidth, int mainFrameHeight) {

		panelWidth = mainFrameWidth;
		panelHeight = (int) (mainFrameHeight * HEADPANEL_MAINFRAME_RATIO);

		this.setPreferredSize(new Dimension(panelWidth, panelHeight));
		
		resizeOtherGuiElements();
		resizeAlphabet();
	}

	private void resizeOtherGuiElements() {

		int fontResize = (int) (0.14 * panelHeight - 14);

		searchButton.setFont(searchButton.getFont().deriveFont(Font.BOLD, 13 + fontResize));
		searchButton.setBounds((int) (0.031 * panelWidth), (int) (0.57 * panelHeight), (int) (0.2 * panelWidth), (int) (0.26 * panelHeight));

		headDescriptionLabel.setFont(headDescriptionLabel.getFont().deriveFont(Font.BOLD, 13 + fontResize));
		headDescriptionLabel.setBounds((int) (0.308 * panelWidth), (int) (0.1 * panelHeight), (int) (0.385 * panelWidth), (int) (0.25 * panelHeight));

		specialtyButton.setFont(specialtyButton.getFont().deriveFont(Font.BOLD, 13 + fontResize));
		specialtyButton.setBounds((int) (0.423 * panelWidth), (int) (0.71 * panelHeight), (int) (0.154 * panelWidth), (int) (0.2 * panelHeight));

		backgroundPanel.setBounds((int) (0.3 * panelWidth), (int) (0.36 * panelHeight), (int) (0.4 * panelWidth), (int) (0.28 * panelHeight));

		deButton.setFont(deButton.getFont().deriveFont(Font.BOLD, 13 + fontResize));
		deButton.setBounds((int) (0.923 * panelWidth), (int) (0.1 * panelHeight), (int) (0.019 * panelWidth), (int) (0.25 * panelHeight));

		separatorLabel.setFont(separatorLabel.getFont().deriveFont(Font.BOLD, 13 + fontResize));
		separatorLabel.setBounds((int) (0.946 * panelWidth), (int) (0.1 * panelHeight), (int) (0.004 * panelWidth), (int) (0.25 * panelHeight));

		esButton.setFont(esButton.getFont().deriveFont(Font.BOLD, 13 + fontResize));
		esButton.setBounds((int) (0.952 * panelWidth), (int) (0.1 * panelHeight), (int) (0.019 * panelWidth), (int) (0.25 * panelHeight));

		newTechnicalTermButton.setFont(newTechnicalTermButton.getFont().deriveFont(Font.BOLD, 13 + fontResize));
		newTechnicalTermButton.setBounds((int) (0.769 * panelWidth), (int) (0.5 * panelHeight), (int) (0.2 * panelWidth), (int) (0.3 * panelHeight));
	}

	private void resizeAlphabet() {

		int fontResize = (int) (0.14 * panelHeight - 14);

		for (int arrayPosition = 0; arrayPosition < 26; arrayPosition++) {
			int xLetterOffSet = (int) (arrayPosition * 0.014 * panelWidth);
			alphabetButtons[arrayPosition].setFont(alphabetButtons[arrayPosition].getFont().deriveFont(Font.BOLD, 11 + fontResize));
			alphabetButtons[arrayPosition].setBounds((int) (0.32 * panelWidth + xLetterOffSet), (int) (0.4 * panelHeight), (int) (0.013 * panelWidth),
					(int) (0.2 * panelHeight));
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
