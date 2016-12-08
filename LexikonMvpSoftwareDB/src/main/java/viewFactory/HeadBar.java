package viewFactory;

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

public class HeadBar extends JMenuBar implements Updatable {

	private JComboBox<ListItem> searchComboBox;
	private DefaultComboBoxModel<ListItem> searchComboBoxDefaultModel;

	private JButton[] alphabetButtons = new JButton[26];
	private JLabel headDescriptionLabel, separatorLabel;
	private JPanel backgroundPanel;

	private JButton specialtyButton, deButton, esButton, newTechnicalTermButton, searchButton;

	private int PANEL_WIDTH = 1300;
	private int PANEL_HEIGHT = 100;
	private int counter = 0;

	private ResourceBundle languageBundle;
	private Locale currentLocale;

	public HeadBar(ResourceBundle languageBundle) {
		this.languageBundle = languageBundle;
		initialize();
	}

	private void initialize() {

		this.setLayout(null);
		this.setPreferredSize(new Dimension(PANEL_WIDTH, 100));
		this.setBackground(Color.DARK_GRAY);

		// Such-Button
		searchButton = WinUtil.createButton(languageBundle.getString("searchBtn"), 40, 57, 260, 26, BorderFactory.createLineBorder(Color.BLACK),
				Color.DARK_GRAY, null, null, null, false, false, Color.WHITE);
		this.add(searchButton);

		// Beschreibungslabel
		headDescriptionLabel = WinUtil.createLabel(languageBundle.getString("headDescriptionLabel"), 401, 10, 500, 25, new EmptyBorder(0, 0, 0, 0),
				Color.DARK_GRAY, null, null, Color.WHITE);
		headDescriptionLabel.setFont(headDescriptionLabel.getFont().deriveFont(Font.BOLD, 13));
		headDescriptionLabel.setHorizontalAlignment(SwingConstants.CENTER);
		this.add(headDescriptionLabel);

		// Fachgebiete darstellen
		specialtyButton = WinUtil.createButton(languageBundle.getString("subjectsBtn"), PANEL_WIDTH / 2 - 100, 68, 200, 20,
				BorderFactory.createLineBorder(Color.GRAY), Color.DARK_GRAY, null, null, null, false, false, WinUtil.ULTRA_LIGHT_GRAY);
		this.add(specialtyButton);

		// HintergrundPanel
		backgroundPanel = new JPanel();
		backgroundPanel.setBounds(390, 36, 520, 28);
		backgroundPanel.setBackground(WinUtil.DARKER_GRAY);
		

		// Sprachauswahlbuttons
		deButton = WinUtil.createButton("DE", 1200, 10, 25, 25, new EmptyBorder(0, 0, 0, 0), Color.BLACK, null, "DE", null, false, false,
				Color.WHITE);
		this.add(deButton);

		separatorLabel = WinUtil.createLabel("/", 1230, 10, 5, 25, new EmptyBorder(0, 0, 0, 0), Color.DARK_GRAY, "separator", null, Color.WHITE);
		this.add(separatorLabel);

		esButton = WinUtil.createButton("ES", 1238, 10, 25, 25, new EmptyBorder(0, 0, 0, 0), Color.DARK_GRAY, null, "ES", null, false, false,
				Color.WHITE);
		this.add(esButton);

		// Neuer Eintrag Button
		newTechnicalTermButton = WinUtil.createButton(languageBundle.getString("newEntryBtn"), 1000, 50, 260, 30,
				BorderFactory.createLineBorder(Color.BLACK), Color.DARK_GRAY, null, null, null, false, false, Color.WHITE);
		this.add(newTechnicalTermButton);
		
		drawAlphabet(PANEL_WIDTH, PANEL_HEIGHT);
		this.add(backgroundPanel);
	}

	private void changeComponentsSizeOnResize(int height, int width) {
		
		PANEL_WIDTH = width;
		PANEL_HEIGHT = height / 8;
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		System.out.println("Auflösung meines Bildschirms: " + dim.getWidth() + " x " + dim.getHeight());
		// int x = (int)(0.03*PANEL_WIDTH);
		// int y = (int)(0.57*PANEL_HEIGHT);
		// int bWidth = (int)(0.2*PANEL_WIDTH);
		// int bHeight = (int)(0.26*PANEL_HEIGHT);
		int fontResize = (int) (0.14 * PANEL_HEIGHT - 14);
		this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		searchButton.setFont(searchButton.getFont().deriveFont(Font.BOLD, 13 + fontResize));
		searchButton.setBounds((int) (0.031 * PANEL_WIDTH), (int) (0.57 * PANEL_HEIGHT), (int) (0.2 * PANEL_WIDTH), (int) (0.26 * PANEL_HEIGHT));

		headDescriptionLabel.setFont(headDescriptionLabel.getFont().deriveFont(Font.BOLD, 13 + fontResize));
		headDescriptionLabel.setBounds((int) (0.308 * PANEL_WIDTH), (int) (0.1 * PANEL_HEIGHT), (int) (0.385 * PANEL_WIDTH),
				(int) (0.25 * PANEL_HEIGHT));

		specialtyButton.setFont(specialtyButton.getFont().deriveFont(Font.BOLD, 13 + fontResize));
		specialtyButton.setBounds((int) (0.423 * PANEL_WIDTH), (int) (0.71 * PANEL_HEIGHT), (int) (0.154 * PANEL_WIDTH), (int) (0.2 * PANEL_HEIGHT));

		backgroundPanel.setBounds((int) (0.3 * PANEL_WIDTH), (int) (0.36 * PANEL_HEIGHT), (int) (0.4 * PANEL_WIDTH), (int) (0.28 * PANEL_HEIGHT));

		deButton.setFont(deButton.getFont().deriveFont(Font.BOLD, 13 + fontResize));
		deButton.setBounds((int) (0.923 * PANEL_WIDTH), (int) (0.1 * PANEL_HEIGHT), (int) (0.019 * PANEL_WIDTH), (int) (0.25 * PANEL_HEIGHT));

		separatorLabel.setFont(separatorLabel.getFont().deriveFont(Font.BOLD, 13 + fontResize));
		separatorLabel.setBounds((int) (0.946 * PANEL_WIDTH), (int) (0.1 * PANEL_HEIGHT), (int) (0.004 * PANEL_WIDTH), (int) (0.25 * PANEL_HEIGHT));

		esButton.setFont(esButton.getFont().deriveFont(Font.BOLD, 13 + fontResize));
		esButton.setBounds((int) (0.952 * PANEL_WIDTH), (int) (0.1 * PANEL_HEIGHT), (int) (0.019 * PANEL_WIDTH), (int) (0.25 * PANEL_HEIGHT));

		newTechnicalTermButton.setFont(newTechnicalTermButton.getFont().deriveFont(Font.BOLD, 13 + fontResize));
		newTechnicalTermButton.setBounds((int) (0.769 * PANEL_WIDTH), (int) (0.5 * PANEL_HEIGHT), (int) (0.2 * PANEL_WIDTH),
				(int) (0.3 * PANEL_HEIGHT));

		resizeAlphabet(PANEL_HEIGHT, PANEL_WIDTH);

		// for (int arrayPosition = 0; arrayPosition < 26; arrayPosition++) {
		// int xLetterOffSet = (int)(arrayPosition * 0.014 * PANEL_WIDTH);
		// alphabetButtons[arrayPosition].setBounds((int)(0.323*PANEL_WIDTH +
		// xLetterOffSet), (int)(0.4*PANEL_HEIGHT), (int)(0.011*PANEL_WIDTH),
		// (int)(0.2*PANEL_HEIGHT));
		// alphabetButtons[arrayPosition].setFont(alphabetButtons[arrayPosition].getFont().deriveFont(Font.BOLD,
		// 13 + fontResize));
		// }

	}

	private void resizeAlphabet(int height, int width) {
		
		int fontResize = (int) (0.14 * PANEL_HEIGHT - 14);
		
		for (int arrayPosition = 0; arrayPosition < 26; arrayPosition++) {
			int xLetterOffSet = (int) (arrayPosition * 0.014 * PANEL_WIDTH);
			alphabetButtons[arrayPosition].setFont(alphabetButtons[arrayPosition].getFont().deriveFont(Font.BOLD, 11 + fontResize));
			alphabetButtons[arrayPosition].setBounds((int) (0.32 * PANEL_WIDTH + xLetterOffSet), (int) (0.4 * PANEL_HEIGHT), (int) (0.013 * PANEL_WIDTH), (int) (0.2 * PANEL_HEIGHT));
		}
	}

	private void drawAlphabet(int PANEL_WIDTH, int PANEL_HEIGHT) {
	
		for (char letter = 'A'; letter <= 'Z'; letter++) {
			int arrayPosition = letter - 65;
			int xLetterOffSet = (int) (arrayPosition * 0.015 * PANEL_WIDTH);
//			420+j , 40, 14, 20
			alphabetButtons[arrayPosition] = WinUtil.createButton(String.valueOf(letter), (int) (0.3 * PANEL_WIDTH + xLetterOffSet),
					(int) (0.4 * PANEL_HEIGHT), (int) (0.013 * PANEL_WIDTH), (int) (0.2 * PANEL_HEIGHT), BorderFactory.createLineBorder(Color.GRAY),
					Color.DARK_GRAY, null, String.valueOf(letter), String.valueOf(letter), false, false, WinUtil.ULTRA_LIGHT_GRAY);
			alphabetButtons[arrayPosition].setActionCommand(String.valueOf(letter) + "%");
			this.add(alphabetButtons[arrayPosition]);
//			(390, 36, 520, 28)
		}
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

	@Override
	public void updateFrame(PanelEventTransferObject e) {

		// den jenigen Sprachbutton umrahmen der übereinstimmt mit aktueller
		// Sprache
		if (e.getCurrentLanguage() == ChosenLanguage.German) {
			deButton.setBorder(BorderFactory.createLineBorder(Color.GRAY));
			ResourceBundle.getBundle("languageBundles.lexikon", new Locale("de"));
		} else {
			esButton.setBorder(BorderFactory.createLineBorder(Color.GRAY));
			ResourceBundle.getBundle("languageBundles.lexikon", new Locale("es"));
		}

		// this.add(backgroundPanel);
		changeComponentsSizeOnResize(e.getMainframeHeight(), e.getMainframeWidth());

		// checkAvailabilityLetters(e.getAvailableLetters());

	}

}
