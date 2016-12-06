package viewFactory;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionListener;
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
	
	private final int  PANEL_WIDTH = 1300;
	private final int  PANEL_HEIGHT = 100;
	
	
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
		backgroundPanel.setBounds(390, 8, 520, 85);
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

	
	}

	private void drawAlphabet() {
		
		for (char letter = 'A'; letter <= 'Z'; letter++) {
			int arrayPosition = letter-65;
			int xLetterOffSet = (letter-65) * 18;

			alphabetButtons[arrayPosition] = WinUtil.createButton(String.valueOf(letter), 420 + xLetterOffSet, 40, 14, 20, BorderFactory.createLineBorder(Color.GRAY),
					Color.DARK_GRAY, null, String.valueOf(letter), String.valueOf(letter), false, false, WinUtil.ULTRA_LIGHT_GRAY);
			alphabetButtons[arrayPosition].setActionCommand(String.valueOf(letter) + "%");
			this.add(alphabetButtons[arrayPosition]);
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
			alphabetButtons[letter-65].addActionListener(l);
		}
		
	}
	

	@Override
	public void updateFrame(PanelEventTransferObject e) {
		
		// den jenigen Sprachbutton umrahmen der übereinstimmt mit aktueller
		// Sprache
		if (e.getCurrentLanguage() == ChosenLanguage.German)
			deButton.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		else
			esButton.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		
		drawAlphabet();
		checkAvailabilityLetters(e.getAvailableLetters());
		this.add(backgroundPanel);

	}

}
