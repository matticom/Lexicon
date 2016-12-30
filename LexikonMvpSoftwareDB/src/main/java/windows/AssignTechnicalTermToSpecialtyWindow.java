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
import java.awt.event.ItemListener;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollBar;

import AssignmentWindowComponents.AssignmentTableModel;
import AssignmentWindowComponents.AssignmentTableRowObject;
import AssignmentWindowComponents.TechnicalTermJTable;
import interactElements.ChooseSpecialtyComboBox;
import interactElements.MyScrollBarUI;
import model.TechnicalTerm;
import utilities.GridBagLayoutUtilities;
import utilities.WinUtil;

public class AssignTechnicalTermToSpecialtyWindow extends MyWindow implements SpecialtyTextFieldsCheckable {

	private final double JDIALOG_DISPLAY_RATIO_WIDTH = 0.3;
	private final double JDIALOG_DISPLAY_RATIO_HEIGHT = 0.7;
	private int panelWidth;
	private int panelHeight;

	List<TechnicalTerm> technicalTermList;

	private Container contentPane;
	private JScrollPane scrollPane;
	private Dimension displaySize;

	private JLabel instructionLabel;
	private JTable technicalTermTable;
	private AssignmentTableModel assignmentTableModel;
	private JButton changeButton;

	private JLabel chooseSpecialtyLabel;
	private JLabel newSpecialtyLabel;
	private JCheckBox newSpecialtyCheckBox;

	private JLabel newGermanSpecialtyLabel;
	private JLabel newSpanishSpecialtyLabel;

	private JTextField germanSpecialtyInput;
	private JTextField spanishSpecialtyInput;

	ChooseSpecialtyComboBox specialtyComboBox;
	
	private boolean newSpecialtySelected = false;

	public AssignTechnicalTermToSpecialtyWindow(ResourceBundle languageBundle, List<TechnicalTerm> technicalTermList,
			ChooseSpecialtyComboBox specialtyComboBox) {
		super(languageBundle);
		this.technicalTermList = technicalTermList;
		contentPane = this.getContentPane();
		this.specialtyComboBox = specialtyComboBox;
		initialize();
	}

	private void initialize() {

		initializeJDialog();
		createTable();

		this.setLocationByPlatform(true);
		this.setVisible(true);
	}

	private void initializeJDialog() {

		this.setTitle(languageBundle.getString("newEntry"));
		contentPane.setBackground(WinUtil.LIGHT_BLACK);
		contentPane.setLayout(new GridBagLayout());

		displaySize = Toolkit.getDefaultToolkit().getScreenSize();
		panelWidth = (int) (displaySize.getWidth() * JDIALOG_DISPLAY_RATIO_WIDTH);
		panelHeight = (int) (displaySize.getHeight() * JDIALOG_DISPLAY_RATIO_HEIGHT);

		this.setMinimumSize(new Dimension(panelWidth, panelHeight));
		this.setMaximumSize(new Dimension(displaySize.width, displaySize.height));

		this.setPreferredSize(new Dimension(panelWidth, panelHeight));

		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setResizable(true);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				AssignTechnicalTermToSpecialtyWindow.this.dispose();
			}
		});
	}

	private void createTable() {

		assignmentTableModel = new AssignmentTableModel(languageBundle, technicalTermList);
		technicalTermTable = new TechnicalTermJTable(assignmentTableModel);

		instructionLabel = new JLabel(languageBundle.getString("instructionLabel"));
		WinUtil.configLabel(instructionLabel, WinUtil.relW((int) (panelWidth * 0.9)), WinUtil.relH(30), WinUtil.ULTRA_LIGHT_GRAY, Color.DARK_GRAY, 13,
				Font.PLAIN);
		GridBagLayoutUtilities.addGB(contentPane, instructionLabel, 1, 1, 2, 1, new Insets(WinUtil.relH(10), 0, WinUtil.relH(20), 0));
		instructionLabel.setBorder(new EmptyBorder(5, 5, 5, 5));

		scrollPane = new JScrollPane(technicalTermTable);
		scrollPane.getViewport().setBackground(Color.GRAY);
		scrollPane.getViewport().setPreferredSize(new Dimension(100, 100));
		GridBagLayoutUtilities.addGB(contentPane, scrollPane, 1, 2, 2, 1, GridBagConstraints.BOTH, 1, 1,
				new Insets(WinUtil.relH(10), WinUtil.relW(30), WinUtil.relH(10), WinUtil.relW(30)));
		JScrollBar scrollbar = new JScrollBar(JScrollBar.VERTICAL);
		scrollbar.setUI(new MyScrollBarUI());
		scrollPane.setVerticalScrollBar(scrollbar);

		GridBagLayoutUtilities.addGB(contentPane, specialtyComboBox, 1, 4, 2, 1, GridBagConstraints.HORIZONTAL, 1, 0,
				new Insets(0, WinUtil.relW(30), WinUtil.relH(10), WinUtil.relW(30)));

		newSpecialtyCheckBox = new JCheckBox(languageBundle.getString("newSpecialty"));
		WinUtil.configCheckBox(newSpecialtyCheckBox, 400, 20, WinUtil.DARK_WHITE, e -> itemStateChanged(e), SwingConstants.LEFT);
		GridBagLayoutUtilities.addGB(contentPane, newSpecialtyCheckBox, 1, 3, 2, 1, GridBagConstraints.NONE, 0, 0,
				new Insets(WinUtil.relH(10), WinUtil.relW(27), WinUtil.relH(10), WinUtil.relW(30)), GridBagConstraints.WEST);

		newGermanSpecialtyLabel = new JLabel(languageBundle.getString("germanSpecialty"));
		newGermanSpecialtyLabel.setVisible(false);
		WinUtil.configLabel(newGermanSpecialtyLabel, WinUtil.relW(200), WinUtil.relH(30), WinUtil.ULTRA_LIGHT_GRAY, WinUtil.LIGHT_BLACK, 13,
				Font.PLAIN);
		GridBagLayoutUtilities.addGB(contentPane, newGermanSpecialtyLabel, 1, 4, 1, 1, new Insets(WinUtil.relH(0), WinUtil.relW(30), 0, WinUtil.relW(10)));

		newSpanishSpecialtyLabel = new JLabel(languageBundle.getString("spanishSpecialty"));
		newSpanishSpecialtyLabel.setVisible(false);
		WinUtil.configLabel(newSpanishSpecialtyLabel, WinUtil.relW(200), WinUtil.relH(30), WinUtil.ULTRA_LIGHT_GRAY, WinUtil.LIGHT_BLACK, 13,
				Font.PLAIN);
		GridBagLayoutUtilities.addGB(contentPane, newSpanishSpecialtyLabel, 2, 4, 1, 1, new Insets(WinUtil.relH(0), WinUtil.relW(10), 0, WinUtil.relW(30)));

		germanSpecialtyInput = new JTextField();
		germanSpecialtyInput.setBackground(WinUtil.DARK_WHITE);
		germanSpecialtyInput.setPreferredSize(new Dimension(WinUtil.relW(200), WinUtil.relH(30)));
		germanSpecialtyInput.setMargin(new Insets(0, 3, 0, 0));
		germanSpecialtyInput.setVisible(false);
		GridBagLayoutUtilities.addGB(contentPane, germanSpecialtyInput, 1, 5, 1, 1, GridBagConstraints.HORIZONTAL, 1, 0,
				new Insets(0, WinUtil.relW(30), WinUtil.relH(10), WinUtil.relW(10)));

		spanishSpecialtyInput = new JTextField();
		spanishSpecialtyInput.setBackground(WinUtil.DARK_WHITE);
		spanishSpecialtyInput.setPreferredSize(new Dimension(WinUtil.relW(200), WinUtil.relH(30)));
		spanishSpecialtyInput.setMargin(new Insets(0, 3, 0, 0));
		spanishSpecialtyInput.setVisible(false);
		GridBagLayoutUtilities.addGB(contentPane, spanishSpecialtyInput, 2, 5, 1, 1, GridBagConstraints.HORIZONTAL, 1, 0,
				new Insets(0, WinUtil.relW(10), WinUtil.relH(10), WinUtil.relW(30)));

		changeButton = new JButton(languageBundle.getString("changeButton"));
		WinUtil.configButton(changeButton, WinUtil.relW(150), WinUtil.relH(30), BorderFactory.createLineBorder(WinUtil.GRASS_GREEN),
				WinUtil.GRASS_GREEN, WinUtil.LIGHT_BLACK);
		GridBagLayoutUtilities.addGB(contentPane, changeButton, 1, 6, 2, 1, GridBagConstraints.NONE, 0, 0,
				new Insets(WinUtil.relH(30), 0, WinUtil.relH(30), 0), GridBagConstraints.SOUTH);
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

	
	public void itemStateChanged(ItemEvent e) {

		if (e.getStateChange() == ItemEvent.SELECTED) {

			newSpecialtySelected = true;
			specialtyComboBox.setVisible(false);
			newGermanSpecialtyLabel.setVisible(true);
			newSpanishSpecialtyLabel.setVisible(true);
			germanSpecialtyInput.setVisible(true);
			spanishSpecialtyInput.setVisible(true);
		}
		if (e.getStateChange() == ItemEvent.DESELECTED) {

			newSpecialtySelected = false;
			specialtyComboBox.setVisible(true);
			newGermanSpecialtyLabel.setVisible(false);
			newSpanishSpecialtyLabel.setVisible(false);
			germanSpecialtyInput.setVisible(false);
			spanishSpecialtyInput.setVisible(false);
		}
	}

	public void setTextFieldListener(KeyListener l) {
		germanSpecialtyInput.addKeyListener(l);
		spanishSpecialtyInput.addKeyListener(l);
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
