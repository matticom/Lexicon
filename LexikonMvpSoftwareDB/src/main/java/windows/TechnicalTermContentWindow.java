package windows;

import java.awt.Color;
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
import java.awt.event.WindowListener;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;

import dto.TechnicalTermDTO;
import eventHandling.PanelEventTransferObject;
import interactElements.ChooseSpecialtyComboBox;
import interactElements.MyScrollBarUI;
import model.Specialty;
import model.TechnicalTerm;
import utilities.ExtendedListItem;
import utilities.GridBagLayoutUtilities;
import utilities.WinUtil;

public class TechnicalTermContentWindow extends MyWindow {

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

	private JButton editButton;
	private JButton abortEditButton;
	private JButton saveChangesButton;

	private JTextField germanTechnicalTermTextField;
	private JTextField spanishTechnicalTermTextField;
	private JTextField germanSpecialtyTextField;
	private JTextField spanishSpecialtyTextField;

	private JTextArea germanTextArea;
	private JTextArea spanishTextArea;

	private String tempGermanTextAreaText;
	private String tempSpanishTextAreaText;

	private JScrollPane germanScrollPane;
	private JScrollPane spanishScrollPane;

	private TechnicalTerm technicalTerm;
	private TechnicalTermDTO technicalTermDTO;

	private boolean editMode;
	private boolean textHasChangedInTextAreas;

	private int SIDE_WIDTH = 20;
	private int BOTTOM_HEIGHT = 20;

	public TechnicalTermContentWindow(ResourceBundle languageBundle, TechnicalTerm technicalTerm) {

		super(languageBundle);
		contentPane = this.getContentPane();
		this.technicalTerm = technicalTerm;
		technicalTermDTO = new TechnicalTermDTO(technicalTerm);
		editMode = false;
		textHasChangedInTextAreas = false;
		initialize();
	}

	private void initialize() {

		initializeJDialog();
		createLabels();
		createLine();
		createTextFields();
		createTextAreas();
		createButtons();
		fillFields();
		configElementsVisiblity();
	
		setModal(false);
		setLocationByPlatform(true);
		setVisible(true);
	}

	private void initializeJDialog() {

		setTitle(languageBundle.getString("newEntry"));
		contentPane.setBackground(WinUtil.LIGHT_BLACK);
		contentPane.setLayout(new GridBagLayout());

		displaySize = Toolkit.getDefaultToolkit().getScreenSize();
		panelWidth = (int) (displaySize.getWidth() * JDIALOG_DISPLAY_RATIO);
		panelHeight = (int) (displaySize.getHeight() * JDIALOG_DISPLAY_RATIO);

		setSize(panelWidth, panelHeight);

		setMinimumSize(new Dimension(panelWidth, panelHeight));
		setMaximumSize(new Dimension(displaySize.width, displaySize.height));

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setResizable(true);
	}

	private void createTextFields() {

		germanTechnicalTermTextField = new JTextField();
		WinUtil.configTextFieldExtended(germanTechnicalTermTextField, WinUtil.relW(350), WinUtil.relH(30), WinUtil.DARK_WHITE, WinUtil.LIGHT_BLACK,
				BorderFactory.createLineBorder(Color.BLACK), false);
		GridBagLayoutUtilities.addGB(contentPane, germanTechnicalTermTextField, 1, 4, 1, 1, GridBagConstraints.HORIZONTAL, 1, 0,
				new Insets(0, WinUtil.relW(SIDE_WIDTH), WinUtil.relH(10), WinUtil.relW(SIDE_WIDTH)));

		spanishTechnicalTermTextField = new JTextField();
		WinUtil.configTextFieldExtended(spanishTechnicalTermTextField, WinUtil.relW(350), WinUtil.relH(30), WinUtil.DARK_WHITE, WinUtil.LIGHT_BLACK,
				BorderFactory.createLineBorder(Color.BLACK), false);
		GridBagLayoutUtilities.addGB(contentPane, spanishTechnicalTermTextField, 3, 4, 1, 1, GridBagConstraints.HORIZONTAL, 1, 0,
				new Insets(0, WinUtil.relW(SIDE_WIDTH), WinUtil.relH(10), WinUtil.relW(SIDE_WIDTH)));

		germanSpecialtyTextField = new JTextField();
		WinUtil.configTextFieldExtended(germanSpecialtyTextField, WinUtil.relW(200), WinUtil.relH(30), WinUtil.DARK_WHITE, WinUtil.LIGHT_BLACK,
				BorderFactory.createLineBorder(Color.BLACK), false);
		GridBagLayoutUtilities.addGB(contentPane, germanSpecialtyTextField, 1, 5, 1, 1, GridBagConstraints.HORIZONTAL, 1, 0,
				new Insets(0, WinUtil.relW(SIDE_WIDTH), WinUtil.relH(10), WinUtil.relW(SIDE_WIDTH)));

		spanishSpecialtyTextField = new JTextField();
		WinUtil.configTextFieldExtended(spanishSpecialtyTextField, WinUtil.relW(200), WinUtil.relH(30), WinUtil.DARK_WHITE, WinUtil.LIGHT_BLACK,
				BorderFactory.createLineBorder(Color.BLACK), false);
		GridBagLayoutUtilities.addGB(contentPane, spanishSpecialtyTextField, 3, 5, 1, 1, GridBagConstraints.HORIZONTAL, 1, 0,
				new Insets(0, WinUtil.relW(SIDE_WIDTH), WinUtil.relH(10), WinUtil.relW(SIDE_WIDTH)));
	}

	private void createTextAreas() {

		germanTextArea = new JTextArea();
		germanTextArea.getDocument().addDocumentListener(new changeWatching());
		germanScrollPane = new JScrollPane(germanTextArea);
		germanScrollPane.getVerticalScrollBar().setUI(new MyScrollBarUI());
		WinUtil.configScrollPaneExtended(germanScrollPane, germanTextArea, WinUtil.relW(350), WinUtil.relH(360), WinUtil.DARK_WHITE, WinUtil.LIGHT_BLACK,
				BorderFactory.createLineBorder(Color.BLACK), false);
		GridBagLayoutUtilities.addGB(contentPane, germanScrollPane, 1, 6, 1, 3, GridBagConstraints.BOTH, 1, 1,
				new Insets(0, WinUtil.relW(SIDE_WIDTH), WinUtil.relH(BOTTOM_HEIGHT), WinUtil.relW(SIDE_WIDTH)));

		spanishTextArea = new JTextArea();
		spanishTextArea.getDocument().addDocumentListener(new changeWatching());
		spanishScrollPane = new JScrollPane(spanishTextArea);
		spanishScrollPane.getVerticalScrollBar().setUI(new MyScrollBarUI());
		WinUtil.configScrollPaneExtended(spanishScrollPane, spanishTextArea, WinUtil.relW(350), WinUtil.relH(360), WinUtil.DARK_WHITE, WinUtil.LIGHT_BLACK,
				BorderFactory.createLineBorder(Color.BLACK), false);
		GridBagLayoutUtilities.addGB(contentPane, spanishScrollPane, 3, 6, 1, 3, GridBagConstraints.BOTH, 1, 1,
				new Insets(0, WinUtil.relW(SIDE_WIDTH), WinUtil.relH(BOTTOM_HEIGHT), WinUtil.relW(SIDE_WIDTH)));
	}

	private void createButtons() {

		abortEditButton = new JButton(languageBundle.getString("abortEditMode"));
		abortEditButton.addActionListener(e -> abortEditMode());
		WinUtil.configButton(abortEditButton, WinUtil.relW(180), WinUtil.relH(30), BorderFactory.createLineBorder(WinUtil.HOT_RED), WinUtil.HOT_RED, WinUtil.LIGHT_BLACK);
		GridBagLayoutUtilities.addGB(contentPane, abortEditButton, 2, 7, 1, 1, GridBagConstraints.NONE, 0, 1, new Insets(WinUtil.relH(30), 0, WinUtil.relH(20), 0),
				GridBagConstraints.SOUTH);

		saveChangesButton = new JButton(languageBundle.getString("saveChanges"));
		WinUtil.configButton(saveChangesButton, WinUtil.relW(180), WinUtil.relH(30), BorderFactory.createLineBorder(WinUtil.GRASS_GREEN), WinUtil.GRASS_GREEN, WinUtil.LIGHT_BLACK);
		GridBagLayoutUtilities.addGB(contentPane, saveChangesButton, 2, 8, 1, 1, GridBagConstraints.NONE, 0, 0, new Insets(0, 0, WinUtil.relH(BOTTOM_HEIGHT + 30), 0),
				GridBagConstraints.SOUTH);

		editButton = new JButton(languageBundle.getString("termEditBtn"));
		editButton.addActionListener(e -> activateEditMode());
		WinUtil.configButton(editButton, WinUtil.relW(180), WinUtil.relH(70), BorderFactory.createLineBorder(WinUtil.GRASS_GREEN), WinUtil.GRASS_GREEN, WinUtil.LIGHT_BLACK);
		GridBagLayoutUtilities.addGB(contentPane, editButton, 2, 8, 1, 1, GridBagConstraints.NONE, 0, 0, new Insets(0, 0, WinUtil.relH(BOTTOM_HEIGHT + 30), 0),
				GridBagConstraints.SOUTH);
	}

	private void createLabels() {

		descriptionLabel = new JLabel(languageBundle.getString("TermWinDescriptionLabel"));
		WinUtil.configLabel(descriptionLabel, WinUtil.relW(600), WinUtil.relH(40), WinUtil.DARK_WHITE, WinUtil.LIGHT_BLACK, 25, Font.BOLD);
		GridBagLayoutUtilities.addGB(contentPane, descriptionLabel, 1, 1, 3, 1, GridBagConstraints.VERTICAL, 1, 0, new Insets(WinUtil.relH(30), 0, WinUtil.relH(10), 0));

		germanLabel = new JLabel(languageBundle.getString("miDeutsch"));
		WinUtil.configLabel(germanLabel, WinUtil.relW(200), WinUtil.relH(25), WinUtil.COOL_BLUE, WinUtil.LIGHT_BLACK, 18, Font.BOLD);
		GridBagLayoutUtilities.addGB(contentPane, germanLabel, 1, 3, 1, 1, GridBagConstraints.BOTH, 1, 0, new Insets(WinUtil.relH(25), 0, WinUtil.relH(25), 0));

		spanishLabel = new JLabel(languageBundle.getString("miSpanisch"));
		WinUtil.configLabel(spanishLabel, WinUtil.relW(200), WinUtil.relH(25), WinUtil.STRONG_ORANGE, WinUtil.LIGHT_BLACK, 18, Font.BOLD);
		GridBagLayoutUtilities.addGB(contentPane, spanishLabel, 3, 3, 1, 1, GridBagConstraints.BOTH, 1, 0, new Insets(WinUtil.relH(25), 0, WinUtil.relH(25), 0));

		technicalTermLabel = new JLabel(languageBundle.getString("dlgterm"));
		WinUtil.configLabel(technicalTermLabel, WinUtil.relW(180), WinUtil.relH(25), WinUtil.ULTRA_LIGHT_GRAY, WinUtil.LIGHT_BLACK, 18, Font.BOLD);
		GridBagLayoutUtilities.addGB(contentPane, technicalTermLabel, 2, 4, 1, 1, GridBagConstraints.BOTH, 0, 0, new Insets(0, 0, WinUtil.relH(10), 0));

		specialtyLabel = new JLabel(languageBundle.getString("dlgsubject"));
		WinUtil.configLabel(specialtyLabel, WinUtil.relW(180), WinUtil.relH(25), WinUtil.ULTRA_LIGHT_GRAY, WinUtil.LIGHT_BLACK, 18, Font.BOLD);
		GridBagLayoutUtilities.addGB(contentPane, specialtyLabel, 2, 5, 1, 1, GridBagConstraints.BOTH, 0, 0, new Insets(0, 0, WinUtil.relH(5), 0));

		contentLabel = new JLabel(languageBundle.getString("dlgText"));
		WinUtil.configLabel(contentLabel, WinUtil.relW(180), WinUtil.relH(25), WinUtil.ULTRA_LIGHT_GRAY, WinUtil.LIGHT_BLACK, 18, Font.BOLD);
		GridBagLayoutUtilities.addGB(contentPane, contentLabel, 2, 6, 1, 1, GridBagConstraints.BOTH, 0, 0, new Insets(0, 0, WinUtil.relH(50), 0));
	}

	private void createLine() {

		JSeparator jSeparator = new JSeparator(SwingConstants.HORIZONTAL);
		WinUtil.configSeparator(jSeparator, (int) (panelWidth * 0.8), WinUtil.relH(5), Color.MAGENTA, WinUtil.LIGHT_BLACK);
		GridBagLayoutUtilities.addGB(contentPane, jSeparator, 1, 2, 3, 1);
	}

	private void fillFields() {

		germanTechnicalTermTextField.setText(technicalTermDTO.getGermanName());
		spanishTechnicalTermTextField.setText(technicalTermDTO.getSpanishName());
		germanSpecialtyTextField.setText(technicalTermDTO.getGermanSpecialty());
		spanishSpecialtyTextField.setText(technicalTermDTO.getSpanishSpecialty());
		germanTextArea.setText(technicalTermDTO.getGermanDescription());
		spanishTextArea.setText(technicalTermDTO.getSpanishDescription());
	}

	private void configElementsVisiblity() {

		if (editMode) {
			abortEditButton.setVisible(true);
			saveChangesButton.setVisible(true);
			editButton.setVisible(false);
			germanTextArea.setEditable(true);
			germanTextArea.setBackground(WinUtil.DARK_WHITE);
			germanTextArea.setForeground(Color.BLACK);
			spanishTextArea.setEditable(true);
			spanishTextArea.setBackground(WinUtil.DARK_WHITE);
			spanishTextArea.setForeground(Color.BLACK);
		} else {
			abortEditButton.setVisible(false);
			saveChangesButton.setVisible(false);
			editButton.setVisible(true);
			germanTextArea.setEditable(false);
			germanTextArea.setBackground(WinUtil.LIGHT_BLACK);
			germanTextArea.setForeground(WinUtil.DARK_WHITE);
			spanishTextArea.setEditable(false);
			spanishTextArea.setBackground(WinUtil.LIGHT_BLACK);
			spanishTextArea.setForeground(WinUtil.DARK_WHITE);
		}
	}

	private void activateEditMode() {
		editMode = true;
		tempGermanTextAreaText = germanTextArea.getText();
		tempSpanishTextAreaText = spanishTextArea.getText();
		configElementsVisiblity();
	}

	public void saveChangesEditMode() {

		editMode = false;
		configElementsVisiblity();
		textHasChangedInTextAreas = false;
	}

	private void abortEditMode() {

		if (queryAbortEdit()) {
			editMode = false;
			germanTextArea.setText(tempGermanTextAreaText);
			spanishTextArea.setText(tempSpanishTextAreaText);
			configElementsVisiblity();
			textHasChangedInTextAreas = false;
		}
	}

	private boolean queryAbortEdit() {

		String[] options = { languageBundle.getString("close"), languageBundle.getString("abort") };

		if (!textHasChangedInTextAreas) {
			return true;
		}
		int retValue = JOptionPane.showOptionDialog(this, languageBundle.getString("queryEdit"), languageBundle.getString("queryExitTitle"), JOptionPane.YES_NO_OPTION,
				JOptionPane.WARNING_MESSAGE, null, options, options[1]);

		if (retValue == JOptionPane.YES_OPTION) {
			return true;
		}
		return false;
	}

	public void setSaveChangesButtonActionListener(ActionListener l) {
		saveChangesButton.addActionListener(l);
	}

	public boolean isTextHasChangedInTextAreas() {
		return textHasChangedInTextAreas;
	}

	public String getGermanTextAreaText() {
		return germanTextArea.getText();
	}

	public String getSpanishTextAreaText() {
		return spanishTextArea.getText();
	}
	
	public void setContentWindowListener(WindowListener l) {
		addWindowListener(l);
	}

	public class changeWatching implements DocumentListener {

		@Override
		public void changedUpdate(DocumentEvent e) {
			textHasChangedInTextAreas = true;
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			textHasChangedInTextAreas = true;
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			textHasChangedInTextAreas = true;
		}

	}
}
