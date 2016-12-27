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
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import eventHandling.PanelEventTransferObject;
import interactElements.ChooseSpecialtyComboBox;
import utilities.GridBagLayoutUtilities;
import utilities.WinUtil;

public class AssignTechnicalTermToSpecialtyWindow extends MyWindow {

	private final double JDIALOG_DISPLAY_RATIO = 0.4;
	private int panelWidth;
	private int panelHeight;
	private Container contentPane;
	private Dimension displaySize;
	
	private JLabel descriptionLabel;
	private JLabel germanLabel;
	private JLabel spanishLabel;
	private JLabel technicalTermLabel;
	private JLabel specialtyLabel;
	
	private JTextField germanTextField;
	private JTextField spanishTextField;
	
	private ChooseSpecialtyComboBox germanSpecialtyComboBox;
	private ChooseSpecialtyComboBox spanishSpecialtyComboBox;

	public AssignTechnicalTermToSpecialtyWindow(ResourceBundle languageBundle) {
		super(languageBundle);
		contentPane = this.getContentPane();
		initialize();
	}

	private void initialize() {

		initializeJDialog();
		createLabels();
		arrangeComboBoxes();

		this.setLocationByPlatform(true);
		this.setVisible(true);
	}

	private void initializeJDialog() {

		this.setTitle(languageBundle.getString("newEntry"));
		contentPane.setBackground(Color.DARK_GRAY);
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

	private void arrangeComboBoxes() {
	}
	
	private void createLabels() {
		
		
	}
}
