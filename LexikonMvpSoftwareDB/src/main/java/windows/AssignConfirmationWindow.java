package windows;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import AssignmentWindowComponents.AssignmentTableModel;
import AssignmentWindowComponents.AssignmentTableRowObject;
import model.TechnicalTerm;
import utilities.GridBagLayoutUtilities;
import utilities.WinUtil;

public class AssignConfirmationWindow extends MyWindow {

	private final double JDIALOG_DISPLAY_RATIO = 0.4;
	private int panelWidth;
	private int panelHeight;
	
	List<TechnicalTerm> technicalTermList;
	
	private Container contentPane;
	private JScrollPane scrollPane;
	private Dimension displaySize;
	
	private JTable technicalTermTable;
	private AssignmentTableModel assignmentTableModel;
	private AssignmentTableRowObject[] tableRowObjectArray;
	
	private JLabel testLabel;
	private JLabel testLabel2;
	private JButton changeButton;
		

	public AssignConfirmationWindow(ResourceBundle languageBundle, AssignmentTableRowObject[] tableRowObjectArray) {
		super(languageBundle);	
		this.tableRowObjectArray = tableRowObjectArray;
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
				AssignConfirmationWindow.this.dispose();
			}
		});
	}

	
		
	private void createTable() {
		testLabel = new JLabel(tableRowObjectArray[0].toString());
		WinUtil.configLabel(testLabel, WinUtil.relW(500),  WinUtil.relH(30), Color.WHITE, WinUtil.LIGHT_BLACK, 11, Font.PLAIN);
		GridBagLayoutUtilities.addGB(contentPane, testLabel, 1, 1, 1, 1, GridBagConstraints.BOTH, 1, 1, new Insets(0, 0, 0, 0));
		testLabel2 = new JLabel(tableRowObjectArray[1].toString());
		WinUtil.configLabel(testLabel2, WinUtil.relW(500),  WinUtil.relH(30), Color.WHITE, WinUtil.LIGHT_BLACK, 11, Font.PLAIN);
		GridBagLayoutUtilities.addGB(contentPane, testLabel2, 1, 2, 1, 1, GridBagConstraints.BOTH, 1, 1, new Insets(0, 0, 0, 0));
		
	}
}
