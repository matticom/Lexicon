package panels;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JViewport;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import interactElements.MyScrollBarUI;
import eventHandling.ChosenLanguage;
import eventHandling.PanelEventTransferObject;
import interactElements.SpecialtyButton;
import model.Specialty;
import model.Translations;
import utilities.WinUtil;

public class SpecialtyPanel extends MyPanel {

	private JPanel 							contentPanel, startPagePanel, lineStartPanel, lineEndPanel;
	private JLabel 							welcomeLabel, introductionLabel, specialtyLabelDE, specialtyLabelES;
	
	private ArrayList<SpecialtyButton>		specialtyButtonsDE = new ArrayList<SpecialtyButton>();
	private ArrayList<SpecialtyButton>		specialtyButtonsES = new ArrayList<SpecialtyButton>();
	private int 							lengthSite;
	private JScrollBar 						scrollbar;
	private int 							scrollBarSize = ((Integer) UIManager.get("ScrollBar.width")).intValue();
	
	private JViewport						scrollPaneViewPort;
	private final int						BAR_POSITION = 125;
	private int								barYPosition = BAR_POSITION;
	
	private int panelWidth = 0;
	private int panelHeight = 0;
	
	private ResourceBundle 	languageBundle;
	
	
	public SpecialtyPanel(ResourceBundle languageBundle) {

		this.languageBundle = languageBundle;
		initialize();
	}
	
	
	private void initialize()
	{
		contentPanel = new JPanel();		
		contentPanel.setLayout(new BorderLayout());
		contentPanel.setBackground(WinUtil.LIGHT_BLACK);
		
		startPagePanel = new JPanel(); 
		startPagePanel.setPreferredSize(new Dimension(1300, 200));
		startPagePanel.setBackground(WinUtil.LIGHT_BLACK);
		contentPanel.add(startPagePanel, BorderLayout.PAGE_START);
		
		lineStartPanel = new JPanel(); 
		lineStartPanel.setPreferredSize(new Dimension(650, 600));
		lineStartPanel.setBackground(WinUtil.LIGHT_BLACK);
		contentPanel.add(lineStartPanel, BorderLayout.LINE_START);
		
		lineEndPanel = new JPanel(); 
		lineEndPanel.setPreferredSize(new Dimension(650, 600));
		lineEndPanel.setBackground(WinUtil.LIGHT_BLACK);
		contentPanel.add(lineEndPanel, BorderLayout.LINE_END);
		
		welcomeLabel = WinUtil.createLabel(languageBundle.getString("welcomeLbl"), 150, 30, 1000, 100, new EmptyBorder(0, 0, 0, 0), Color.DARK_GRAY, null, null, Color.WHITE);
		welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		welcomeLabel.setFont(welcomeLabel.getFont().deriveFont(Font.BOLD, 30));
		startPagePanel.add(welcomeLabel);
		
		introductionLabel = WinUtil.createLabel(languageBundle.getString("introductionLbl"), 100, 150, 1100, 100, new EmptyBorder(0, 18, 0, 5), 
												WinUtil.DARKER_GRAY, null, null, WinUtil.ULTRA_LIGHT_GRAY);
		introductionLabel.setOpaque(true);
		introductionLabel.setFont(introductionLabel.getFont().deriveFont(Font.PLAIN,18));
		startPagePanel.add(introductionLabel);
		
		specialtyLabelDE = WinUtil.createLabel(languageBundle.getString("subjectLbl"), 650/2-115, 300, 200, 25, new EmptyBorder(0, 0, 0, 0), Color.DARK_GRAY, null, null, Color.WHITE);
		specialtyLabelDE.setFont(introductionLabel.getFont().deriveFont(Font.BOLD,18));
		specialtyLabelDE.setHorizontalAlignment(SwingConstants.CENTER);
		lineStartPanel.add(specialtyLabelDE);
		
		specialtyLabelES = WinUtil.createLabel(languageBundle.getString("subjectLbl"), 650/2-115, 300, 200, 25, new EmptyBorder(0, 0, 0, 0), Color.DARK_GRAY, null, null, Color.WHITE);
		specialtyLabelES.setFont(introductionLabel.getFont().deriveFont(Font.BOLD,18));
		specialtyLabelES.setHorizontalAlignment(SwingConstants.CENTER);
		lineEndPanel.add(specialtyLabelES);
		
		// Auflistung der Fachbegebiete
//		createSubjects();
		
		scrollbar = new JScrollBar(ScrollBar.VERTICAL);
		scrollbar.setUI(new MyScrollBarUI());
		this.setVerticalScrollBar(scrollbar);
		
		// Größe des AnzeigePanels entsprechend der Anzahl der Begriffseinträge einstellen (ScrollPane richtet sich nach PreferredSize) 			
//		contentPanel.setPreferredSize(new Dimension(panelWidth, lengthSite));
		
		// AnzeigePanels der ScrollPane hinzufügen
		this.getViewport().add(contentPanel);

	}
	
	private void repaintBar()
	{
		int actualYPositon = (int)scrollPaneViewPort.getViewPosition().getY();
		if (actualYPositon < BAR_POSITION)
		{
			barYPosition = BAR_POSITION - actualYPositon;
			repaint();
		}
	}
	
	@Override
	public void paint(Graphics g)
	{
		super.paint(g);
		g.setColor(Color.MAGENTA);
		g.drawLine(150, barYPosition, 1150, barYPosition);
	}
	
	public void switchLang()
	{
//		// Alle Beschriftungen werden geändert
//		Lang = mainFrame.getLang();
//		welcomeLabel.setText(Lang.getString("welcomeLbl"));
//		introductionLabel.setText(Lang.getString("introductionLbl"));
//		specialtyLabel.setText(Lang.getString("subjectLbl"));
//		
//		// Buttons entfernen
//		for (int i = 0; i < zeilenanzahl; i++)
//		{
//			startPanel.remove(specialtyButtons.get(i));
//		}
//		specialtyButtons = null;
//		
//		// Buttons und Labels neu erstellen
//		createSubjects();
//		startPanel.setPreferredSize(new Dimension(1100, lengthSite));
	}
		
	@Override
	public void updatePanel(PanelEventTransferObject e) {
		
		if (e.getCurrentLanguage() == ChosenLanguage.German) {
			languageBundle = ResourceBundle.getBundle("languageBundles.lexikon", new Locale("de"));
		} else {
			languageBundle = ResourceBundle.getBundle("languageBundles.lexikon", new Locale("es"));
		}
		
		changeComponentsSizeOnResize(e.getMainFrameWidth(), e.getMainFrameHeight(), e.getSpecialtyList());
	}
	
	private void changeComponentsSizeOnResize(int mainFrameWidth, int mainFrameHeight, List<Specialty> specialtyList) {

		panelWidth = mainFrameWidth;
		panelHeight = mainFrameHeight;

		contentPanel.setPreferredSize(new Dimension(panelWidth, lengthSite));
		
		createSpecialtyButtons(specialtyList);
		calculateNeededSpace(10, panelWidth, lengthSite);
		resizeGuiElements();
		
		scrollPaneViewPort = this.getViewport();
		scrollPaneViewPort.addChangeListener(e -> repaintBar());
	}
	
	private void createSpecialtyButtons(List<Specialty> specialtyList) {
		
		int GERMAN = 1;
		int SPANISH = 2;
		
		for (Specialty specialty : specialtyList) {
			for(Translations translation : specialty.getTranslationList()) {
				if (translation.getLanguages().getId() == GERMAN) {
					specialtyButtonsDE.add(new SpecialtyButton(specialty.getId(), translation.getId(), GERMAN, translation.getName()));
				}
				if (translation.getLanguages().getId() == SPANISH) {
					specialtyButtonsES.add(new SpecialtyButton(specialty.getId(), translation.getId(), SPANISH, translation.getName()));
				}
			}
		}
	}
	
	private void calculateNeededSpace(int numbersOfButtons, int mainFrameWidth, int mainFrameHeight) {
		int i = 0;
		lengthSite = 360+i*50;
	}
	
	private void resizeGuiElements() {
		resizeSpecialtyButton();
		
	}
	
	private void resizeSpecialtyButton() {
//		120+j*270 - scrollBarSize, 360+i*50, 250, 25, BorderFactory.createLineBorder(WinUtil.COOL_BLUE), 
//		WinUtil.LIGHT_BLACK,  null, null, false, false, WinUtil.COOL_BLUE));
	}

}
