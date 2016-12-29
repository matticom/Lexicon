package windows;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import AssignmentWindowComponents.AssignmentTableModel;
import AssignmentWindowComponents.AssignmentTableRowObject;
import AssignmentWindowComponents.TechnicalTermJTable;
import model.TechnicalTerm;
import utilities.GridBagLayoutUtilities;
import utilities.WinUtil;

public class AssignTechnicalTermToSpecialtyWindow extends MyWindow {

	private final double JDIALOG_DISPLAY_RATIO = 0.4;
	private int panelWidth;
	private int panelHeight;
	
	List<TechnicalTerm> technicalTermList;
	
	private Container contentPane;
	private JScrollPane scrollPane;
	private Dimension displaySize;
	
	private JTable technicalTermTable;
	private AssignmentTableModel assignmentTableModel;
	private JButton changeButton;
		

	public AssignTechnicalTermToSpecialtyWindow(ResourceBundle languageBundle, List<TechnicalTerm> technicalTermList) {
		super(languageBundle);	
		this.technicalTermList = technicalTermList;
		contentPane = this.getContentPane();
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
		panelWidth = (int) (displaySize.getWidth() * JDIALOG_DISPLAY_RATIO);
		panelHeight = (int) (displaySize.getHeight() * JDIALOG_DISPLAY_RATIO);

		this.setMinimumSize(new Dimension(displaySize.width / 3, panelHeight));
		this.setMaximumSize(new Dimension(displaySize.width / 2, displaySize.height));

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
		
		changeButton = new JButton(languageBundle.getString("insertBtn"));
		WinUtil.configButton(changeButton, WinUtil.relW(180), WinUtil.relH(30), BorderFactory.createLineBorder(WinUtil.GRASS_GREEN),
				WinUtil.GRASS_GREEN, WinUtil.LIGHT_BLACK);
		GridBagLayoutUtilities.addGB(contentPane, changeButton, 1, 2, 1, 1, GridBagConstraints.NONE, 0, 1,
				new Insets(WinUtil.relH(30), 0, WinUtil.relH(30), 0), GridBagConstraints.SOUTH);
		
		
//		technicalTermTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
//		technicalTermTable.setGridColor(WinUtil.LIGHT_BLACK);
//		technicalTermTable.setIntercellSpacing(new Dimension(0, 0));
//		technicalTermTable.setShowGrid(false);
		// Listener nur Anzeige der aktuellen Zeilennummer
//		technicalTermTable.getSelectionModel().addListSelectionListener(this);
		// Zum Abfangen der Tasten POS1 und ENDE
//		technicalTermTable.addKeyListener(this);
//		// Für Doppelklick
//		technicalTermTable.addMouseListener(this);
		
//		technicalTermTable.setModel();
		
		scrollPane = new JScrollPane(technicalTermTable);
		GridBagLayoutUtilities.addGB(contentPane, scrollPane, 1, 1, 1, 1, GridBagConstraints.BOTH, 1, 1,
				new Insets(WinUtil.relH(10), WinUtil.relW(30), WinUtil.relH(30), WinUtil.relW(30)));
	}
	
	public void setChangeButtonActionListener(ActionListener l) {
		changeButton.addActionListener(l);
	}
	
	public AssignmentTableRowObject[] getTableRowObjects() {
		
		int[] selectedRows = technicalTermTable.getSelectedRows();
		AssignmentTableRowObject[] tableRowObjectArray = new AssignmentTableRowObject[selectedRows.length];
		
		for(int row = 0; row < selectedRows.length; row++) {
			int rowIndex = selectedRows[row];
			tableRowObjectArray[row] = assignmentTableModel.getAssignmentTableRowObjectAtRow(rowIndex);
		}
		return tableRowObjectArray;
	}
}
