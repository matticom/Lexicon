package dialogs;

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
import java.awt.event.KeyAdapter;
import java.awt.event.WindowAdapter;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import assignmentDialogComponents.AssignmentTableModel;
import assignmentDialogComponents.TechnicalTermJTable;
import enums.Dialogs;
import inputChecker.SpecialtyTextFieldsCheckable;
import interactElements.ChooseSpecialtyComboBox;
import interactElements.MyScrollBarUI;
import interactElements.SourceButton;
import model.TechnicalTerm;
import utilities.GridBagLayoutUtilities;
import utilities.WinUtil;

public class AssignmentDialog extends Dialog implements SpecialtyTextFieldsCheckable {

	private final double JDIALOG_DISPLAY_RATIO_WIDTH = 0.3;
	private final double JDIALOG_DISPLAY_RATIO_HEIGHT = 0.7;
	private int panelWidth;
	private int panelHeight;

	private List<TechnicalTerm> technicalTermList;

	private Container contentPane;
	private JScrollPane scrollPane;
	private Dimension displaySize;

	private JTable technicalTermTable;
	private SourceButton changeButton;
	private JCheckBox newSpecialtyCheckBox;

	private JLabel instructionLabel;
	private JLabel newGermanSpecialtyLabel;
	private JLabel newSpanishSpecialtyLabel;

	private JTextField germanSpecialtyInput;
	private JTextField spanishSpecialtyInput;

	private ChooseSpecialtyComboBox specialtyComboBox;
	private AssignmentTableModel assignmentTableModel;

	private boolean newSpecialtySelected = false;
	
	private ActionListener actionListener;
	private KeyAdapter keyAdapter;
	private WindowAdapter windowAdapter;
	 

	public AssignmentDialog(ResourceBundle languageBundle, List<TechnicalTerm> technicalTermList, ChooseSpecialtyComboBox specialtyComboBox, 
			Dialogs dialogWindowType, WindowAdapter windowAdapter, ActionListener actionListener, KeyAdapter keyAdapter) {
		
		super(languageBundle, dialogWindowType);
		this.technicalTermList = technicalTermList;
		contentPane = this.getContentPane();
		this.specialtyComboBox = specialtyComboBox;
		this.windowAdapter = windowAdapter;
		this.actionListener = actionListener;
		this.keyAdapter = keyAdapter;
		initialize();
	}

	private void initialize() {

		initializeJDialog();
		createTable();
		createLabels();
		createTextFields();
		createButtons();
		createScrollPane();
		arrangeComboBoxes();
		
		newSpecialtyDeselected();
		setModal(true);
		setLocationByPlatform(true);
		setVisible(true);
	}

	private void initializeJDialog() {

		setTitle(languageBundle.getString("changeButton"));
		contentPane.setBackground(WinUtil.LIGHT_BLACK);
		contentPane.setLayout(new GridBagLayout());

		displaySize = Toolkit.getDefaultToolkit().getScreenSize();
		panelWidth = (int) (displaySize.getWidth() * JDIALOG_DISPLAY_RATIO_WIDTH);
		panelHeight = (int) (displaySize.getHeight() * JDIALOG_DISPLAY_RATIO_HEIGHT);

		setMinimumSize(new Dimension(panelWidth, panelHeight));
		setMaximumSize(new Dimension(displaySize.width, displaySize.height));

		setPreferredSize(new Dimension(panelWidth, panelHeight));

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setResizable(true);
		
		addWindowListener(windowAdapter);
	}

	private void createTable() {

		assignmentTableModel = new AssignmentTableModel(languageBundle, technicalTermList);
		technicalTermTable = new TechnicalTermJTable(assignmentTableModel);
	}

	private void createLabels() {
		
		instructionLabel = new JLabel(languageBundle.getString("instructionLabel"));
		WinUtil.configLabel(instructionLabel, (int) (panelWidth * 0.9), WinUtil.relH(30), WinUtil.ULTRA_LIGHT_GRAY, Color.DARK_GRAY, 13, Font.PLAIN);
		GridBagLayoutUtilities.addGB(contentPane, instructionLabel, 1, 1, 2, 1, new Insets(WinUtil.relH(10), 0, WinUtil.relH(20), 0));
		instructionLabel.setBorder(new EmptyBorder(5, 5, 5, 5));

		newGermanSpecialtyLabel = new JLabel(languageBundle.getString("germanSpecialty"));
		newGermanSpecialtyLabel.setVisible(false);
		WinUtil.configLabel(newGermanSpecialtyLabel, WinUtil.relW(200), WinUtil.relH(30), WinUtil.ULTRA_LIGHT_GRAY, WinUtil.LIGHT_BLACK, 13, Font.PLAIN);
		GridBagLayoutUtilities.addGB(contentPane, newGermanSpecialtyLabel, 1, 4, 1, 1, new Insets(WinUtil.relH(0), WinUtil.relW(30), 0, WinUtil.relW(10)));

		newSpanishSpecialtyLabel = new JLabel(languageBundle.getString("spanishSpecialty"));
		newSpanishSpecialtyLabel.setVisible(false);
		WinUtil.configLabel(newSpanishSpecialtyLabel, WinUtil.relW(200), WinUtil.relH(30), WinUtil.ULTRA_LIGHT_GRAY, WinUtil.LIGHT_BLACK, 13, Font.PLAIN);
		GridBagLayoutUtilities.addGB(contentPane, newSpanishSpecialtyLabel, 2, 4, 1, 1, new Insets(WinUtil.relH(0), WinUtil.relW(10), 0, WinUtil.relW(30)));
	}

	private void createButtons() {
		
		changeButton = new SourceButton(languageBundle.getString("changeButton"), this, actionListener);
		WinUtil.configButton(changeButton, WinUtil.relW(150), WinUtil.relH(30), BorderFactory.createLineBorder(WinUtil.GRASS_GREEN), WinUtil.GRASS_GREEN, WinUtil.LIGHT_BLACK);
		GridBagLayoutUtilities.addGB(contentPane, changeButton, 1, 6, 2, 1, GridBagConstraints.NONE, 0, 0, new Insets(WinUtil.relH(30), 0, WinUtil.relH(30), 0),
				GridBagConstraints.SOUTH);
		
		newSpecialtyCheckBox = new JCheckBox(languageBundle.getString("newSpecialty"));
		WinUtil.configCheckBox(newSpecialtyCheckBox,  WinUtil.relW(400), WinUtil.relH(20), WinUtil.DARK_WHITE, e -> itemStateChanged(e), SwingConstants.LEFT);
		GridBagLayoutUtilities.addGB(contentPane, newSpecialtyCheckBox, 1, 3, 2, 1, GridBagConstraints.NONE, 0, 0,
				new Insets(WinUtil.relH(10), WinUtil.relW(27), WinUtil.relH(10), WinUtil.relW(30)), GridBagConstraints.WEST);
	}	
	
	private void createScrollPane() {
		
		scrollPane = new JScrollPane(technicalTermTable);
		scrollPane.getViewport().setBackground(Color.GRAY);
		scrollPane.getViewport().setPreferredSize(new Dimension(WinUtil.relW(100), WinUtil.relH(100)));
		scrollPane.getVerticalScrollBar().setUI(new MyScrollBarUI());
		GridBagLayoutUtilities.addGB(contentPane, scrollPane, 1, 2, 2, 1, GridBagConstraints.BOTH, 1, 1,
				new Insets(WinUtil.relH(10), WinUtil.relW(30), WinUtil.relH(10), WinUtil.relW(30)));
	}
	
	private void arrangeComboBoxes() {
		
		GridBagLayoutUtilities.addGB(contentPane, specialtyComboBox, 1, 4, 2, 1, GridBagConstraints.HORIZONTAL, 1, 0,
				new Insets(0, WinUtil.relW(30), WinUtil.relH(10), WinUtil.relW(30)));
	}
	
	private void createTextFields() {
	
		germanSpecialtyInput = new JTextField();
		WinUtil.configTextField(germanSpecialtyInput, WinUtil.relW(200), WinUtil.relH(30), WinUtil.DARK_WHITE, true);
		germanSpecialtyInput.setVisible(false);
		GridBagLayoutUtilities.addGB(contentPane, germanSpecialtyInput, 1, 5, 1, 1, GridBagConstraints.HORIZONTAL, 1, 0,
				new Insets(0, WinUtil.relW(30), WinUtil.relH(10), WinUtil.relW(10)));
		germanSpecialtyInput.addKeyListener(keyAdapter);

		spanishSpecialtyInput = new JTextField();
		WinUtil.configTextField(spanishSpecialtyInput, WinUtil.relW(200), WinUtil.relH(30), WinUtil.DARK_WHITE, true);
		spanishSpecialtyInput.setVisible(false);
		GridBagLayoutUtilities.addGB(contentPane, spanishSpecialtyInput, 2, 5, 1, 1, GridBagConstraints.HORIZONTAL, 1, 0,
				new Insets(0, WinUtil.relW(10), WinUtil.relH(10), WinUtil.relW(30)));
		spanishSpecialtyInput.addKeyListener(keyAdapter);
	}

	public void setChangeButtonActionListener(ActionListener l) {
		changeButton.addActionListener(l);
	}

	public int[] getTechnicalTermIds() {

		int[] selectedRows = technicalTermTable.getSelectedRows();
		int[] technicalTermIdArray = new int[selectedRows.length];

		for (int row = 0; row < selectedRows.length; row++) {
			int rowIndex = selectedRows[row];
			technicalTermIdArray[row] = assignmentTableModel.getAssignmentTableRowObjectAtRow(rowIndex).getTechnicalTermId();
		}
		return technicalTermIdArray;
	}

	private void itemStateChanged(ItemEvent e) {

		if (e.getStateChange() == ItemEvent.SELECTED) {
			newSpecialtySelected();
		}
		if (e.getStateChange() == ItemEvent.DESELECTED) {
			newSpecialtyDeselected();
		}
	}
	
	private void newSpecialtySelected() {
		newSpecialtySelected = true;
		specialtyComboBox.setVisible(false);
		newGermanSpecialtyLabel.setVisible(true);
		newSpanishSpecialtyLabel.setVisible(true);
		germanSpecialtyInput.setVisible(true);
		spanishSpecialtyInput.setVisible(true);
	}
	
	private void newSpecialtyDeselected() {
		newSpecialtySelected = false;
		specialtyComboBox.setVisible(true);
		newGermanSpecialtyLabel.setVisible(false);
		newSpanishSpecialtyLabel.setVisible(false);
		germanSpecialtyInput.setVisible(false);
		spanishSpecialtyInput.setVisible(false);
	}

	@Override
	public JTextField getGermanSpecialtyInput() {
		return germanSpecialtyInput;
	}

	@Override
	public JTextField getSpanishSpecialtyInput() {
		return spanishSpecialtyInput;
	}

	@Override
	public boolean isNewSpecialtySelected() {
		return newSpecialtySelected;
	}

	public void refreshAssignmentTableModel(List<TechnicalTerm> technicalTermList) {

		assignmentTableModel = new AssignmentTableModel(languageBundle, technicalTermList);
		technicalTermTable.setModel(assignmentTableModel);
	}
}
