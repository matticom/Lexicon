package panels;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JSeparator;
import javax.swing.JViewport;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import interactElements.MyScrollBarUI;
import eventHandling.ChosenLanguage;
import eventHandling.PanelEventTransferObject;
import interactElements.SpecialtyButton;
import model.Specialty;
import model.Translations;
import utilities.GridBagLayoutUtilities;
import utilities.WinUtil;

public class SpecialtyPanel extends MyPanel {

	private JPanel 							contentPanel, staticElementsPanel, dynamicElementsPanel;
	private JLabel 							welcomeLabel, introductionLabel, specialtyLabelDE, specialtyLabelES;
	
	private ArrayList<SpecialtyButton>		specialtyButtonsDE = new ArrayList<SpecialtyButton>();
	private ArrayList<SpecialtyButton>		specialtyButtonsES = new ArrayList<SpecialtyButton>();
	private int 							lengthSite;
	private JScrollBar 						scrollbar;
	private int 							scrollBarSize = ((Integer) UIManager.get("ScrollBar.width")).intValue();
	
	private JViewport						scrollPaneViewPort;
	private final int						BAR_POSITION = 125;
	private int								barYPosition = BAR_POSITION;

	
	public SpecialtyPanel(ResourceBundle languageBundle, double MAINFRAME_DISPLAY_RATIO) {

		super(languageBundle, MAINFRAME_DISPLAY_RATIO);
		initialize();
	}
	
	
	private void initialize()
	{
		this.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.setBorder(new EmptyBorder(1, 0, 0, 0));
		
		
		contentPanel = new JPanel();
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		contentPanel.setPreferredSize(new Dimension((int)(displaySize.width*0.6), 1000));
		
		staticElementsPanel = new JPanel();
		staticElementsPanel.setBackground(Color.DARK_GRAY);
		staticElementsPanel.setLayout(new GridBagLayout());
		staticElementsPanel.setPreferredSize(new Dimension((int)(displaySize.width*0.6), 265));
		
		dynamicElementsPanel = new JPanel();
		dynamicElementsPanel.setBackground(Color.GREEN);
		dynamicElementsPanel.setLayout(new GridBagLayout());
		dynamicElementsPanel.setPreferredSize(new Dimension((int)(displaySize.width*0.6), 900));
		
		
		welcomeLabel = new JLabel();
		welcomeLabel.setText(languageBundle.getString("welcomeLbl"));
		welcomeLabel.setBackground(Color.ORANGE);
		welcomeLabel.setForeground(Color.WHITE);
		welcomeLabel.setFont(welcomeLabel.getFont().deriveFont(Font.BOLD, 30));
		welcomeLabel.setOpaque(true);
		welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		welcomeLabel.setPreferredSize(new Dimension(800, 39));
		GridBagLayoutUtilities.addGB(staticElementsPanel, welcomeLabel, 1, 1, 2, 1, new Insets(30, 0, 10, 0));
		
		JSeparator jSeparator = new JSeparator(SwingConstants.HORIZONTAL);
		jSeparator.setForeground(Color.MAGENTA);
		jSeparator.setBackground(Color.DARK_GRAY);
		jSeparator.setPreferredSize(new Dimension(1000, 5));
		jSeparator.setOpaque(true);
		GridBagLayoutUtilities.addGB(staticElementsPanel, jSeparator, 1, 2, 2, 1);
		
		introductionLabel = new JLabel();
		introductionLabel.setPreferredSize(new Dimension(1100, 100));
		introductionLabel.setText(languageBundle.getString("introductionLbl"));
		introductionLabel.setBackground(WinUtil.COOL_BLUE);
		introductionLabel.setForeground(WinUtil.ULTRA_LIGHT_GRAY);
		introductionLabel.setFont(introductionLabel.getFont().deriveFont(Font.PLAIN,18));
		introductionLabel.setOpaque(true);
	
		
		GridBagLayoutUtilities.addGB(staticElementsPanel, introductionLabel, 1, 3, 2, 1, new Insets(10, 0, 20, 0));
	
		specialtyLabelDE = new JLabel();
		specialtyLabelDE.setText(languageBundle.getString("subjectLbl"));
		specialtyLabelDE.setBackground(Color.BLUE);
		specialtyLabelDE.setForeground(Color.WHITE);
		specialtyLabelDE.setFont(specialtyLabelDE.getFont().deriveFont(Font.PLAIN,18));
		specialtyLabelDE.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagLayoutUtilities.addGB(staticElementsPanel, specialtyLabelDE, 1, 4, 1, 1, GridBagConstraints.BOTH, 1, 1, new Insets(0, 0, 20, 0));
		specialtyLabelDE.setOpaque(true);
		
		specialtyLabelES = new JLabel();
		specialtyLabelES.setText(languageBundle.getString("subjectLbl"));
		specialtyLabelES.setBackground(Color.CYAN);
		specialtyLabelES.setForeground(Color.WHITE);
		specialtyLabelES.setFont(specialtyLabelES.getFont().deriveFont(Font.PLAIN,18));
		specialtyLabelES.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagLayoutUtilities.addGB(staticElementsPanel, specialtyLabelES, 2, 4, 1, 1, GridBagConstraints.BOTH, 1, 1, new Insets(0, 0, 20, 0));
		specialtyLabelES.setOpaque(true);
	
		
		// Auflistung der Fachbegebiete
//		createSubjects();
		
		scrollbar = new JScrollBar(ScrollBar.VERTICAL);
		scrollbar.setUI(new MyScrollBarUI());
		this.setVerticalScrollBar(scrollbar);
		
		// Größe des AnzeigePanels entsprechend der Anzahl der Begriffseinträge einstellen (ScrollPane richtet sich nach PreferredSize) 			
//		contentPanel.setPreferredSize(new Dimension(panelWidth, lengthSite));
	
		contentPanel.add(staticElementsPanel);
		contentPanel.add(dynamicElementsPanel);
		
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
//		g.setColor(Color.MAGENTA);
//		g.drawLine(150, barYPosition, 1150, barYPosition);
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

//		contentPanel.setPreferredSize(new Dimension(panelWidth, lengthSite));
		
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
