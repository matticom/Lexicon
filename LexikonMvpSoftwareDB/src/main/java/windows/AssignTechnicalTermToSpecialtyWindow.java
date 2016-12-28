package windows;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.ResourceBundle;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

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
		
		technicalTermTable = new JTable();
		
		technicalTermTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
		// Listener nur Anzeige der aktuellen Zeilennummer
//		technicalTermTable.getSelectionModel().addListSelectionListener(this);
		// Zum Abfangen der Tasten POS1 und ENDE
//		technicalTermTable.addKeyListener(this);
//		// Für Doppelklick
//		technicalTermTable.addMouseListener(this);
		
		technicalTermTable.setModel(new AssignmentTableModel(technicalTermList, languageId));
		
		scrollPane = new JScrollPane(technicalTermTable);
		GridBagLayoutUtilities.addGB(contentPane, scrollPane, 1, 1, 1, 1, GridBagConstraints.BOTH, 1, 1,
				new Insets(WinUtil.relH(10), WinUtil.relW(30), WinUtil.relH(30), WinUtil.relW(30)));
	}
}
