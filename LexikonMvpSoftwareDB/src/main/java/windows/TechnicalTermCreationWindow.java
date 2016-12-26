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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import eventHandling.PanelEventTransferObject;
import interactElements.ChooseSpecialtyComboBox;
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
	
	private JTextField germanTextField;
	private JTextField spanishTextField;
	
	private ChooseSpecialtyComboBox germanSpecialtyComboBox;
	private ChooseSpecialtyComboBox spanishSpecialtyComboBox;

	public TechnicalTermCreationWindow(ResourceBundle languageBundle, ChooseSpecialtyComboBox germanSpecialtyComboBox, ChooseSpecialtyComboBox spanishSpecialtyComboBox) {
		super(languageBundle);
		contentPane = this.getContentPane();
		this.germanSpecialtyComboBox = germanSpecialtyComboBox;
		this.spanishSpecialtyComboBox = spanishSpecialtyComboBox;
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

		this.setSize(panelWidth, panelHeight);
		
//		this.setMinimumSize(new Dimension(displaySize.width / 3, panelHeight));
//		this.setMaximumSize(new Dimension(displaySize.width / 2, displaySize.height));

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
		
		GridBagLayoutUtilities.addGB(contentPane, germanSpecialtyComboBox, 1, 4, 1, 1, GridBagConstraints.NONE, 1, 0, new Insets(0, WinUtil.relW(50), WinUtil.relH(30), 0));
		GridBagLayoutUtilities.addGB(contentPane, spanishSpecialtyComboBox, 3, 4, 1, 1, GridBagConstraints.NONE, 1, 0, new Insets(0, 0, WinUtil.relH(30), WinUtil.relW(50)));
	}
	
	private void createLabels() {
		
		descriptionLabel = new JLabel(languageBundle.getString("dlgDescriptionLabel"));
		WinUtil.configLabel(descriptionLabel, WinUtil.relW(500), WinUtil.relH(30), Color.GRAY, Color.CYAN, 20, Font.BOLD);
		GridBagLayoutUtilities.addGB(contentPane, descriptionLabel, 1, 1, 3, 1, GridBagConstraints.BOTH, 1, 0, new Insets(WinUtil.relH(30), 0, WinUtil.relH(50), 0));		
		
		germanLabel = new JLabel(languageBundle.getString("miDeutsch"));
		WinUtil.configLabel(germanLabel, WinUtil.relW(200), WinUtil.relH(25), WinUtil.COOL_BLUE, Color.BLUE, 18, Font.BOLD);
		GridBagLayoutUtilities.addGB(contentPane, germanLabel, 1, 2, 1, 1, GridBagConstraints.BOTH, 1, 0, new Insets(0, 0, WinUtil.relH(50), 0));
		
		spanishLabel = new JLabel(languageBundle.getString("miSpanisch"));
		WinUtil.configLabel(spanishLabel, WinUtil.relW(200), WinUtil.relH(25), WinUtil.COOL_BLUE, Color.YELLOW, 18, Font.BOLD);
		GridBagLayoutUtilities.addGB(contentPane, spanishLabel, 3, 2, 1, 1, GridBagConstraints.BOTH, 1, 0, new Insets(0, 0, WinUtil.relH(50), 0));
		
		germanTextField = new JTextField();
		germanTextField.setPreferredSize(new Dimension(WinUtil.relW(350), WinUtil.relH(25)));
		GridBagLayoutUtilities.addGB(contentPane, germanTextField, 1, 3, 1, 1, GridBagConstraints.BOTH, 1, 0, new Insets(0, WinUtil.relW(50), WinUtil.relH(30), 0));		
		
		technicalTermLabel = new JLabel(languageBundle.getString("dlgterm"));
		WinUtil.configLabel(technicalTermLabel, WinUtil.relW(300), WinUtil.relH(25), WinUtil.COOL_BLUE, Color.BLACK, 18, Font.BOLD);
		GridBagLayoutUtilities.addGB(contentPane, spanishLabel, 2, 3, 1, 1, GridBagConstraints.BOTH, 0, 0, new Insets(0, 0, WinUtil.relH(50), 0));
		
		spanishTextField = new JTextField();
		spanishTextField.setPreferredSize(new Dimension(WinUtil.relW(350), WinUtil.relH(25)));
		GridBagLayoutUtilities.addGB(contentPane, germanTextField, 3, 3, 1, 1, GridBagConstraints.BOTH, 1, 0, new Insets(0, 0, WinUtil.relH(30), WinUtil.relW(50)));
		
		specialtyLabel = new JLabel(languageBundle.getString("dlgsubject"));
		WinUtil.configLabel(specialtyLabel, WinUtil.relW(300), WinUtil.relH(25), WinUtil.COOL_BLUE, Color.BLACK, 18, Font.BOLD);
		GridBagLayoutUtilities.addGB(contentPane, spanishLabel, 2, 4, 1, 1, GridBagConstraints.BOTH, 0, 0, new Insets(0, 0, WinUtil.relH(50), 0));
		
//	
//				
////				// deutsche Fachgebietstextfeld
////				tfSubjectDE = new JTextField();
////				tfSubjectDE.setBounds(50, 230, 350, 25);
////				tfSubjectDE.addKeyListener(this);
////				tfSubjectDE.addFocusListener(this);
////				this.getContentPane().add(tfSubjectDE);
//
//				// Fachgebiet Label
//				dlgsubject = WinUtil.createLabel(	Lang.getString("dlgsubject"), 450, 230, 300, 25, new EmptyBorder(0, 0, 0, 0),
//													WinUtil.createColor(0, 178, 238), null, null, Color.WHITE);
//				dlgsubject.setFont(dlgterm.getFont().deriveFont(Font.BOLD, 18));
//				dlgsubject.setHorizontalAlignment(SwingConstants.CENTER);
//				this.getContentPane().add(dlgsubject);
//
//				
//				cboModelES = new DefaultComboBoxModel<ListItem>();
//				cboSubjectES = new JComboBox<>(cboModelES);
//				cboSubjectES.setBounds(800, 230, 350, 25);
//				cboSubjectES.addKeyListener(this);
//				cboSubjectES.addFocusListener(this);
//				this.getContentPane().add(cboSubjectES);
//				
//				
////				// spanische Fachgebietstextfeld
////				tfSubjectES = new JTextField();
////				tfSubjectES.setBounds(800, 230, 350, 25);
////				tfSubjectES.addKeyListener(this);
////				tfSubjectES.addFocusListener(this);
////				this.getContentPane().add(tfSubjectES);
//
//				// deutsche Inhaltstextarea
//				taTextDE = new JTextArea();
//				taTextDE.setMargin(new Insets(3, 3, 3, 3));
//				taTextDE.setLineWrap(true);
//				taTextDE.setWrapStyleWord(true);
//
//				// deutsche ScrollPane für deutsche Inhaltstextarea
//				spDE = new JScrollPane(taTextDE);
//				spDE.setBounds(50, 280, 350, 360);
//				this.getContentPane().add(spDE);
//
//				// Beschreibung Label
//				dlgText = WinUtil.createLabel(Lang.getString("dlgText"), 450, 280, 300, 25, new EmptyBorder(0, 0, 0, 0),
//						WinUtil.createColor(0, 178, 238), null, null, Color.WHITE);
//				dlgText.setFont(dlgText.getFont().deriveFont(Font.BOLD, 18));
//				dlgText.setHorizontalAlignment(SwingConstants.CENTER);
//				this.getContentPane().add(dlgText);
//
//				// spanische Inhaltstextarea
//				taTextES = new JTextArea();
//				taTextES.setMargin(new Insets(3, 3, 3, 3));
//				taTextES.setLineWrap(true);
//				taTextES.setWrapStyleWord(true);
//
//				// spanische ScrollPane für spanische Inhaltstextarea
//				spES = new JScrollPane(taTextES);
//				spES.setBounds(800, 280, 350, 360);
//				this.getContentPane().add(spES);
//
//				// Info Label unter Beschreibungslabel
//
//				dlgInfo = WinUtil.createLabel(Lang.getString("Info"), 425, 450, 350, 100, new EmptyBorder(0, 0, 0, 0),
//						WinUtil.createColor(0, 178, 238), null, null, Color.GRAY);
//				dlgInfo.setFont(dlgInfo.getFont().deriveFont(Font.PLAIN, 14));
//				dlgInfo.setHorizontalAlignment(SwingConstants.CENTER);
//				this.getContentPane().add(dlgInfo);
//
//				
//				// Insertbutton
//				insertBtn = WinUtil.createButton(Lang.getString("insertBtn"), 450, 400, 300, 30,
//						BorderFactory.createLineBorder(WinUtil.createColor(0, 178, 238)), Color.DARK_GRAY, this,
//						null, null, false, false, WinUtil.createColor(0, 178, 238));
//				this.getContentPane().add(insertBtn);
//				
//				// Comboboxen füllen
//		
//		
//
//		JSeparator jSeparator = new JSeparator(SwingConstants.HORIZONTAL);
//		WinUtil.configSeparator(jSeparator, (int) (displaySize.getWidth() * 1000 / 1920), (int) (displaySize.getHeight() * 5 / 1200), Color.MAGENTA, Color.DARK_GRAY);
//		GridBagLayoutUtilities.addGB(staticElementsPanel, jSeparator, 1, 2, 4, 1);
	}

	@Override
	public void updatePanel(PanelEventTransferObject e) {
		// TODO Auto-generated method stub
		
	}

}
