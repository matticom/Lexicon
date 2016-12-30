package windows;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.JTextComponent;

import eventHandling.PanelEventTransferObject;
import interactElements.ChooseSpecialtyComboBox;
import utilities.ExtendedListItem;
import utilities.GridBagLayoutUtilities;
import utilities.WinUtil;

public class TechnicalTermCreationWindow extends MyWindow {

	private final double JDIALOG_DISPLAY_RATIO = 0.6;
	private int panelWidth;
	private int panelHeight;
	private Container contentPane;
	private Dimension displaySize;

	private JLabel descriptionLabel;
	private JLabel germanLabel;
	private JLabel spanishLabel;
	private JLabel technicalTermLabel;
	private JLabel specialtyLabel;
	private JLabel contentLabel;

	private JButton insertButton;

	private JTextField germanTextField;
	private JTextField spanishTextField;
	private JTextField germanSpecialtyInput;
	private JTextField spanishSpecialtyInput;

	private JTextArea germanTextArea;
	private JTextArea spanishTextArea;

	private JScrollPane germanScrollPane;
	private JScrollPane spanishScrollPane;

	private ChooseSpecialtyComboBox germanSpecialtyComboBox;
	private ChooseSpecialtyComboBox spanishSpecialtyComboBox;

	private boolean newSpecialtySelected = false;

	private JCheckBox newSpecialtyCheckBox;

	private int SIDE_WIDTH = 20;
	private int BOTTOM_HEIGHT = 20;

	public TechnicalTermCreationWindow(ResourceBundle languageBundle, ChooseSpecialtyComboBox germanSpecialtyComboBox,
			ChooseSpecialtyComboBox spanishSpecialtyComboBox) {

		super(languageBundle);
		contentPane = this.getContentPane();
		this.germanSpecialtyComboBox = germanSpecialtyComboBox;
		this.spanishSpecialtyComboBox = spanishSpecialtyComboBox;
		initialize();
	}

	private void initialize() {

		initializeJDialog();

		createLabels();
		createLine();
		createTextFields();
		createCheckBox();
		createTextAreas();
		createInsertButton();
		arrangeComboBoxes();

//		insertButton.addActionListener(e -> leseTtext()); // Beispielimplementierung

		configElementsVisiblityAtStart(WinUtil.getLanguageId(languageBundle));

		this.setModal(false);
		this.setLocationByPlatform(true);
		this.setVisible(true);
	}

	private void leseTtext() {
		System.out.println(((JTextComponent) germanSpecialtyComboBox.getEditor().getEditorComponent()).getText());

		try {
			System.out.println(germanSpecialtyComboBox.getSelectedListItem().getValueMember());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Es wurde kein Element ausgewählt: " + e.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void initializeJDialog() {

		this.setTitle(languageBundle.getString("newEntry"));
		contentPane.setBackground(WinUtil.LIGHT_BLACK);
		contentPane.setLayout(new GridBagLayout());

		displaySize = Toolkit.getDefaultToolkit().getScreenSize();
		panelWidth = (int) (displaySize.getWidth() * JDIALOG_DISPLAY_RATIO);
		panelHeight = (int) (displaySize.getHeight() * JDIALOG_DISPLAY_RATIO);

		this.setSize(panelWidth, panelHeight);

		this.setMinimumSize(new Dimension(panelWidth, panelHeight));
		this.setMaximumSize(new Dimension(displaySize.width, displaySize.height));

		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setResizable(true);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				TechnicalTermCreationWindow.this.dispose();
			}
		});

	}

	private void arrangeComboBoxes() {

		germanSpecialtyComboBox.addItemListener(e -> copySpecialtyToTextField(WinUtil.getLanguageId(languageBundle)));
		GridBagLayoutUtilities.addGB(contentPane, germanSpecialtyComboBox, 1, 5, 1, 2, GridBagConstraints.HORIZONTAL, 1, 0,
				new Insets(0, WinUtil.relW(SIDE_WIDTH), WinUtil.relH(10), WinUtil.relW(SIDE_WIDTH)));
		spanishSpecialtyComboBox.addItemListener(e -> copySpecialtyToTextField(WinUtil.getLanguageId(languageBundle)));
		GridBagLayoutUtilities.addGB(contentPane, spanishSpecialtyComboBox, 3, 5, 1, 2, GridBagConstraints.HORIZONTAL, 1, 0,
				new Insets(0, WinUtil.relW(SIDE_WIDTH), WinUtil.relH(10), WinUtil.relW(SIDE_WIDTH)));
	}

	private void createTextFields() {

		germanTextField = new JTextField();
		germanTextField.setBackground(WinUtil.DARK_WHITE);
		germanTextField.setPreferredSize(new Dimension(WinUtil.relW(350), WinUtil.relH(30)));
		germanTextField.setMargin(new Insets(0, 3, 0, 0));
		GridBagLayoutUtilities.addGB(contentPane, germanTextField, 1, 4, 1, 1, GridBagConstraints.HORIZONTAL, 1, 0,
				new Insets(0, WinUtil.relW(SIDE_WIDTH), WinUtil.relH(10), WinUtil.relW(SIDE_WIDTH)));

		spanishTextField = new JTextField();
		spanishTextField.setBackground(WinUtil.DARK_WHITE);
		spanishTextField.setPreferredSize(new Dimension(WinUtil.relW(350), WinUtil.relH(30)));
		spanishTextField.setMargin(new Insets(0, 3, 0, 0));
		GridBagLayoutUtilities.addGB(contentPane, spanishTextField, 3, 4, 1, 1, GridBagConstraints.HORIZONTAL, 1, 0,
				new Insets(0, WinUtil.relW(SIDE_WIDTH), WinUtil.relH(10), WinUtil.relW(SIDE_WIDTH)));

		germanSpecialtyInput = new JTextField();
		germanSpecialtyInput.setBackground(WinUtil.DARK_WHITE);
		germanSpecialtyInput.setPreferredSize(new Dimension(WinUtil.relW(200), WinUtil.relH(30)));
		germanSpecialtyInput.setMargin(new Insets(0, 3, 0, 0));
		// germanSpecialtyInput.setVisible(false);
		GridBagLayoutUtilities.addGB(contentPane, germanSpecialtyInput, 1, 5, 1, 2, GridBagConstraints.HORIZONTAL, 1, 0,
				new Insets(0, WinUtil.relW(SIDE_WIDTH), WinUtil.relH(10), WinUtil.relW(SIDE_WIDTH)));

		spanishSpecialtyInput = new JTextField();
		spanishSpecialtyInput.setBackground(WinUtil.DARK_WHITE);
		spanishSpecialtyInput.setPreferredSize(new Dimension(WinUtil.relW(200), WinUtil.relH(30)));
		spanishSpecialtyInput.setMargin(new Insets(0, 3, 0, 0));
		// spanishSpecialtyInput.setVisible(false);
		GridBagLayoutUtilities.addGB(contentPane, spanishSpecialtyInput, 3, 5, 1, 2, GridBagConstraints.HORIZONTAL, 1, 0,
				new Insets(0, WinUtil.relW(SIDE_WIDTH), WinUtil.relH(10), WinUtil.relW(SIDE_WIDTH)));
	}

	private void createTextAreas() {

		germanTextArea = new JTextArea();
		germanTextArea.setBackground(WinUtil.DARK_WHITE);
		germanScrollPane = new JScrollPane(germanTextArea);
		WinUtil.configScrollPane(germanScrollPane, germanTextArea, WinUtil.relW(350), WinUtil.relH(360));
		GridBagLayoutUtilities.addGB(contentPane, germanScrollPane, 1, 7, 1, 2, GridBagConstraints.BOTH, 1, 1,
				new Insets(0, WinUtil.relW(SIDE_WIDTH), WinUtil.relH(BOTTOM_HEIGHT), WinUtil.relW(SIDE_WIDTH)));

		spanishTextArea = new JTextArea();
		spanishTextArea.setBackground(WinUtil.DARK_WHITE);
		spanishScrollPane = new JScrollPane(spanishTextArea);
		WinUtil.configScrollPane(spanishScrollPane, spanishTextArea, WinUtil.relW(350), WinUtil.relH(360));
		GridBagLayoutUtilities.addGB(contentPane, spanishScrollPane, 3, 7, 1, 2, GridBagConstraints.BOTH, 1, 1,
				new Insets(0, WinUtil.relW(SIDE_WIDTH), WinUtil.relH(BOTTOM_HEIGHT), WinUtil.relW(SIDE_WIDTH)));
	}

	private void createInsertButton() {

		insertButton = new JButton(languageBundle.getString("insertBtn"));
		WinUtil.configButton(insertButton, WinUtil.relW(180), WinUtil.relH(30), BorderFactory.createLineBorder(WinUtil.GRASS_GREEN),
				WinUtil.GRASS_GREEN, WinUtil.LIGHT_BLACK);
		GridBagLayoutUtilities.addGB(contentPane, insertButton, 2, 8, 1, 1, GridBagConstraints.NONE, 0, 1,
				new Insets(WinUtil.relH(30), 0, WinUtil.relH(BOTTOM_HEIGHT + 30), 0), GridBagConstraints.SOUTH);
	}

	private void createLabels() {

		descriptionLabel = new JLabel(languageBundle.getString("dlgDescriptionLabel"));
		WinUtil.configLabel(descriptionLabel, WinUtil.relW(600), WinUtil.relH(40), WinUtil.DARK_WHITE, WinUtil.LIGHT_BLACK, 25, Font.BOLD);
		GridBagLayoutUtilities.addGB(contentPane, descriptionLabel, 1, 1, 3, 1, GridBagConstraints.VERTICAL, 1, 0,
				new Insets(WinUtil.relH(30), 0, WinUtil.relH(10), 0));

		germanLabel = new JLabel(languageBundle.getString("miDeutsch"));
		WinUtil.configLabel(germanLabel, WinUtil.relW(200), WinUtil.relH(25), WinUtil.COOL_BLUE, WinUtil.LIGHT_BLACK, 18, Font.BOLD);
		GridBagLayoutUtilities.addGB(contentPane, germanLabel, 1, 3, 1, 1, GridBagConstraints.BOTH, 1, 0,
				new Insets(WinUtil.relH(25), 0, WinUtil.relH(25), 0));

		spanishLabel = new JLabel(languageBundle.getString("miSpanisch"));
		WinUtil.configLabel(spanishLabel, WinUtil.relW(200), WinUtil.relH(25), WinUtil.STRONG_ORANGE, WinUtil.LIGHT_BLACK, 18, Font.BOLD);
		GridBagLayoutUtilities.addGB(contentPane, spanishLabel, 3, 3, 1, 1, GridBagConstraints.BOTH, 1, 0,
				new Insets(WinUtil.relH(25), 0, WinUtil.relH(25), 0));

		technicalTermLabel = new JLabel(languageBundle.getString("dlgterm"));
		WinUtil.configLabel(technicalTermLabel, WinUtil.relW(180), WinUtil.relH(25), Color.WHITE, WinUtil.LIGHT_BLACK, 18, Font.BOLD);
		GridBagLayoutUtilities.addGB(contentPane, technicalTermLabel, 2, 4, 1, 1, GridBagConstraints.BOTH, 0, 0,
				new Insets(0, 0, WinUtil.relH(10), 0));

		specialtyLabel = new JLabel(languageBundle.getString("dlgsubject"));
		WinUtil.configLabel(specialtyLabel, WinUtil.relW(180), WinUtil.relH(25), Color.WHITE, WinUtil.LIGHT_BLACK, 18, Font.BOLD);
		GridBagLayoutUtilities.addGB(contentPane, specialtyLabel, 2, 5, 1, 1, GridBagConstraints.BOTH, 0, 0, new Insets(0, 0, WinUtil.relH(5), 0));

		contentLabel = new JLabel(languageBundle.getString("dlgText"));
		WinUtil.configLabel(contentLabel, WinUtil.relW(180), WinUtil.relH(25), Color.WHITE, WinUtil.LIGHT_BLACK, 18, Font.BOLD);
		GridBagLayoutUtilities.addGB(contentPane, contentLabel, 2, 7, 1, 1, GridBagConstraints.BOTH, 0, 0, new Insets(0, 0, WinUtil.relH(50), 0));
	}

	private void createLine() {

		JSeparator jSeparator = new JSeparator(SwingConstants.HORIZONTAL);
		WinUtil.configSeparator(jSeparator, (int) (panelWidth * 0.8), WinUtil.relH(5), Color.MAGENTA, WinUtil.LIGHT_BLACK);
		GridBagLayoutUtilities.addGB(contentPane, jSeparator, 1, 2, 3, 1);
	}

	private void createCheckBox() {

		newSpecialtyCheckBox = new JCheckBox(languageBundle.getString("newSpecialty"));
		WinUtil.configCheckBox(newSpecialtyCheckBox, 180, 20, WinUtil.ULTRA_LIGHT_GRAY,
				e -> configElementsVisiblity(WinUtil.getLanguageId(languageBundle), e), SwingConstants.CENTER);
		GridBagLayoutUtilities.addGB(contentPane, newSpecialtyCheckBox, 2, 6, 1, 1, GridBagConstraints.BOTH, 0, 0,
				new Insets(0, 0, WinUtil.relH(10), 0), GridBagConstraints.CENTER);
	}

	private void configElementsVisiblity(int languageId, ItemEvent e) {

		if (e.getStateChange() == ItemEvent.SELECTED) {

			newSpecialtySelected = true;
			germanSpecialtyInput.setVisible(true);
			germanSpecialtyInput.setEditable(true);
			germanSpecialtyInput.setText("");
			spanishSpecialtyInput.setVisible(true);
			spanishSpecialtyInput.setEditable(true);
			spanishSpecialtyInput.setText("");
			germanSpecialtyComboBox.setVisible(false);
			spanishSpecialtyComboBox.setVisible(false);
		}
		if (e.getStateChange() == ItemEvent.DESELECTED) {
			newSpecialtySelected = false;
			configElementsVisiblityAtStart(languageId);
		}
	}

	private void configElementsVisiblityAtStart(int languageId) {
		if (languageId == GERMAN) {
			germanSpecialtyInput.setVisible(false);
			spanishSpecialtyInput.setVisible(true);
			spanishSpecialtyInput.setEditable(false);
			germanSpecialtyComboBox.setVisible(true);
			spanishSpecialtyInput.setText(((ExtendedListItem) (germanSpecialtyComboBox.getSelectedListItem().getDisplayMember())).getOtherLanguage());
			spanishSpecialtyComboBox.setVisible(false);
		}
		if (languageId == SPANISH) {
			germanSpecialtyInput.setVisible(true);
			germanSpecialtyInput.setEditable(false);
			spanishSpecialtyInput.setVisible(false);
			germanSpecialtyComboBox.setVisible(false);
			germanSpecialtyInput.setText(((ExtendedListItem) (spanishSpecialtyComboBox.getSelectedListItem().getDisplayMember())).getOtherLanguage());
			spanishSpecialtyComboBox.setVisible(true);
		}
	}

	private void copySpecialtyToTextField(int languageId) {
		if (languageId == GERMAN) {
			spanishSpecialtyInput.setText(((ExtendedListItem) (germanSpecialtyComboBox.getSelectedListItem().getDisplayMember())).getOtherLanguage());
		}

		if (languageId == SPANISH) {
			germanSpecialtyInput.setText(((ExtendedListItem) (spanishSpecialtyComboBox.getSelectedListItem().getDisplayMember())).getOtherLanguage());
		}
	}

	public void setInsertButtonsActionListener(ActionListener l) {
		insertButton.addActionListener(l);
	}

	public void setTextFieldListener(KeyListener l) {
		germanTextField.addKeyListener(l);
		spanishTextField.addKeyListener(l);
		germanSpecialtyInput.addKeyListener(l);
		spanishSpecialtyInput.addKeyListener(l);
	}

	public JTextField getGermanTextField() {
		return germanTextField;
	}

	public JTextField getSpanishTextField() {
		return spanishTextField;
	}

	public JTextField getGermanSpecialtyInput() {
		return germanSpecialtyInput;
	}

	public JTextField getSpanishSpecialtyInput() {
		return spanishSpecialtyInput;
	}

	public boolean isNewSpecialtySelected() {
		return newSpecialtySelected;
	}

}
