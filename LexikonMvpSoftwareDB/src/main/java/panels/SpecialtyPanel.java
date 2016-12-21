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

	private JPanel contentPanel, staticElementsPanel, dynamicElementsPanel, germanSpecialtyPanel, spanishSpecialtyPanel;
	private JLabel welcomeLabel, introductionLabel, specialtyLabelDE, specialtyLabelES;
	
	private JPanel dynamicTestPanel;

	private ArrayList<SpecialtyButton> specialtyButtonsDE = new ArrayList<SpecialtyButton>();
	private ArrayList<SpecialtyButton> specialtyButtonsES = new ArrayList<SpecialtyButton>();
	
	private int gridX;
	private int gridY;
	
	private JScrollBar scrollbar;
	private int scrollBarSize = ((Integer) UIManager.get("ScrollBar.width")).intValue();

	private JViewport scrollPaneViewPort;
	private final int BAR_POSITION = 125;
	private int barYPosition = BAR_POSITION;
	
	double MAINFRAME_DISPLAY_RATIO = 0;

	public SpecialtyPanel(ResourceBundle languageBundle, double MAINFRAME_DISPLAY_RATIO, JPanel dynamicTestPanel) {

		super(languageBundle, MAINFRAME_DISPLAY_RATIO);
		this.MAINFRAME_DISPLAY_RATIO = MAINFRAME_DISPLAY_RATIO;
		this.dynamicTestPanel = dynamicTestPanel;
		initialize();
	}

	private void initialize() {
		this.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.setBorder(new EmptyBorder(1, 0, 0, 0));

		contentPanel = new JPanel();
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		contentPanel.setPreferredSize(new Dimension((int) (displaySize.width * MAINFRAME_DISPLAY_RATIO), 1000));

		staticElementsPanel = new JPanel();
		staticElementsPanel.setBackground(Color.DARK_GRAY);
		staticElementsPanel.setLayout(new GridBagLayout());
		staticElementsPanel.setPreferredSize(new Dimension((int) (displaySize.width * MAINFRAME_DISPLAY_RATIO), 265));

//		dynamicElementsPanel = new JPanel();
//		dynamicElementsPanel.setBackground(Color.GREEN);
//		dynamicElementsPanel.setLayout(new BoxLayout(dynamicElementsPanel, BoxLayout.X_AXIS));
//		dynamicElementsPanel.setPreferredSize(new Dimension((int) (displaySize.width * 0.6), 900));
//
//		germanSpecialtyPanel = new JPanel();
//		germanSpecialtyPanel.setBackground(Color.WHITE);
//		germanSpecialtyPanel.setLayout(new GridBagLayout());
//		germanSpecialtyPanel.setPreferredSize(new Dimension((int) (displaySize.width * 0.3), 900));
//
//		spanishSpecialtyPanel = new JPanel();
//		spanishSpecialtyPanel.setBackground(Color.GRAY);
//		spanishSpecialtyPanel.setLayout(new GridBagLayout());
//		spanishSpecialtyPanel.setPreferredSize(new Dimension((int) (displaySize.width * 0.3), 900));
//
//		dynamicElementsPanel.add(germanSpecialtyPanel);
//		dynamicElementsPanel.add(spanishSpecialtyPanel);

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
		introductionLabel.setFont(introductionLabel.getFont().deriveFont(Font.PLAIN, 18));
		introductionLabel.setOpaque(true);

		GridBagLayoutUtilities.addGB(staticElementsPanel, introductionLabel, 1, 3, 2, 1, new Insets(10, 0, 20, 0));

		specialtyLabelDE = new JLabel();
		specialtyLabelDE.setText(languageBundle.getString("subjectLbl"));
		specialtyLabelDE.setBackground(Color.BLUE);
		specialtyLabelDE.setForeground(Color.WHITE);
		specialtyLabelDE.setFont(specialtyLabelDE.getFont().deriveFont(Font.PLAIN, 18));
		specialtyLabelDE.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagLayoutUtilities.addGB(staticElementsPanel, specialtyLabelDE, 1, 4, 1, 1, GridBagConstraints.BOTH, 1, 1, new Insets(0, 0, 0, 0));
		specialtyLabelDE.setOpaque(true);

		specialtyLabelES = new JLabel();
		specialtyLabelES.setText(languageBundle.getString("subjectLbl"));
		specialtyLabelES.setBackground(Color.CYAN);
		specialtyLabelES.setForeground(Color.WHITE);
		specialtyLabelES.setFont(specialtyLabelES.getFont().deriveFont(Font.PLAIN, 18));
		specialtyLabelES.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagLayoutUtilities.addGB(staticElementsPanel, specialtyLabelES, 2, 4, 1, 1, GridBagConstraints.BOTH, 1, 1, new Insets(0, 0, 0, 0));
		specialtyLabelES.setOpaque(true);

		// Auflistung der Fachbegebiete
		// createSubjects();

		scrollbar = new JScrollBar(ScrollBar.VERTICAL);
		scrollbar.setUI(new MyScrollBarUI());
		this.setVerticalScrollBar(scrollbar);

		// Größe des AnzeigePanels entsprechend der Anzahl der Begriffseinträge
		// einstellen (ScrollPane richtet sich nach PreferredSize)
		// contentPanel.setPreferredSize(new Dimension(panelWidth, lengthSite));

		contentPanel.add(staticElementsPanel);
		contentPanel.add(dynamicTestPanel);

		// AnzeigePanels der ScrollPane hinzufügen
		this.getViewport().add(contentPanel);

	}

//	private void repaintBar() {
//		int actualYPositon = (int) scrollPaneViewPort.getViewPosition().getY();
//		if (actualYPositon < BAR_POSITION) {
//			barYPosition = BAR_POSITION - actualYPositon;
//			repaint();
//		}
//	}
//
//	@Override
//	public void paint(Graphics g) {
//		super.paint(g);
//		// g.setColor(Color.MAGENTA);
//		// g.drawLine(150, barYPosition, 1150, barYPosition);
//	}

	public void switchLang() {
		// // Alle Beschriftungen werden geändert
		// Lang = mainFrame.getLang();
		// welcomeLabel.setText(Lang.getString("welcomeLbl"));
		// introductionLabel.setText(Lang.getString("introductionLbl"));
		// specialtyLabel.setText(Lang.getString("subjectLbl"));
		//
		// // Buttons entfernen
		// for (int i = 0; i < zeilenanzahl; i++)
		// {
		// startPanel.remove(specialtyButtons.get(i));
		// }
		// specialtyButtons = null;
		//
		// // Buttons und Labels neu erstellen
		// createSubjects();
		// startPanel.setPreferredSize(new Dimension(1100, lengthSite));
	}

	@Override
	public void updatePanel(PanelEventTransferObject e) {

		if (e.getCurrentLanguage() == ChosenLanguage.German) {
			languageBundle = ResourceBundle.getBundle("languageBundles.lexikon", new Locale("de"));
		} else {
			languageBundle = ResourceBundle.getBundle("languageBundles.lexikon", new Locale("es"));
		}
		
		contentPanel.remove(dynamicTestPanel);
		contentPanel.add(e.getDynamicTestPanel());
		this.validate();
		// wegen remove muss repaint passieren
		this.repaint();
//		dynamicElementsPanel.remove(germanSpecialtyPanel);
//		dynamicElementsPanel.remove(spanishSpecialtyPanel);
//		
//		germanSpecialtyPanel = new JPanel();
//		germanSpecialtyPanel.setBackground(Color.WHITE);
//		germanSpecialtyPanel.setLayout(new GridBagLayout());
//		germanSpecialtyPanel.setPreferredSize(new Dimension((int) (e.getMainFrameWidth() * 0.45), 900));
//			
//		spanishSpecialtyPanel = new JPanel();
//		spanishSpecialtyPanel.setBackground(Color.GRAY);
//		spanishSpecialtyPanel.setLayout(new GridBagLayout());
//		spanishSpecialtyPanel.setPreferredSize(new Dimension((int) (e.getMainFrameWidth() * 0.45), 900));
//		
//		
//		dynamicElementsPanel.add(germanSpecialtyPanel);
//		dynamicElementsPanel.add(spanishSpecialtyPanel);
		
//		changeComponentsSizeOnResize(e.getMainFrameWidth(), e.getMainFrameHeight(), e.getSpecialtyList());
		


	}

	private void changeComponentsSizeOnResize(int mainFrameWidth, int mainFrameHeight, List<Specialty> specialtyList) {

		panelWidth = mainFrameWidth / 2;
		panelHeight = mainFrameHeight;
		int numbersOfSpecialties = specialtyList.size();

		// contentPanel.setPreferredSize(new Dimension(panelWidth, lengthSite));

		createSpecialtyButtons(specialtyList);
		calculateGrid(numbersOfSpecialties, panelWidth);
		resizeGuiElements();

//		scrollPaneViewPort = this.getViewport();
//		scrollPaneViewPort.addChangeListener(e -> repaintBar());
	}

	private void createSpecialtyButtons(List<Specialty> specialtyList) {

		int GERMAN = 1;
		int SPANISH = 2;
		
		for (Specialty specialty : specialtyList) {
			for (Translations translation : specialty.getTranslationList()) {
				if (translation.getLanguages().getId() == GERMAN) {
					SpecialtyButton specialtyButton = new SpecialtyButton(specialty.getId(), translation.getId(), GERMAN, translation.getName());
					specialtyButton.setPreferredSize(new Dimension(100, 30));
					specialtyButtonsDE.add(specialtyButton);
				}
				if (translation.getLanguages().getId() == SPANISH) {
					SpecialtyButton specialtyButton = new SpecialtyButton(specialty.getId(), translation.getId(), SPANISH, translation.getName());
					specialtyButton.setPreferredSize(new Dimension(100, 30));
					specialtyButtonsES.add(specialtyButton);
				}
			}
		}
	}

	private void calculateGrid(int numbersOfButtons, int panelWidth) {
		int insetsX = 20;
		gridX = panelWidth / (100+2*insetsX) ;
		gridY = numbersOfButtons/gridX;
		if (numbersOfButtons % gridX != 0) {
			gridY++;
		}
	}

	private void resizeGuiElements() {
		resizeSpecialtyButton();

	}

	private void resizeSpecialtyButton() {
		
		int posX = 1;
		int posY = 1;
		int weightY = 0;
		for (SpecialtyButton specialtyButton : specialtyButtonsDE) {
			
			GridBagLayoutUtilities.addGB(germanSpecialtyPanel, specialtyButton, posX, posY, 0, weightY, GridBagConstraints.NORTH, new Insets(20, 20, 20, 20));
			posX++;
			
			if (posX > gridX) {
				posX = 1;
				posY++;
			}
			
			if (posY == gridY) {
				weightY = 1;
			} else {
				weightY = 0;
			}
		}
	}

}
