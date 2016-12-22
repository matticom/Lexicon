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

	private JPanel contentPanel, staticElementsPanel, germanSpecialtyPanel, spanishSpecialtyPanel;
	private JLabel welcomeLabel, introductionLabel, specialtyLabelDE, specialtyLabelES;

	private DynamicTestPanel dynamicTestPanel;

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

	public SpecialtyPanel(ResourceBundle languageBundle, double MAINFRAME_DISPLAY_RATIO, DynamicTestPanel dynamicTestPanel) {

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
		contentPanel.setPreferredSize(new Dimension((int) (displaySize.width * MAINFRAME_DISPLAY_RATIO), 500));

		staticElementsPanel = new JPanel();
		staticElementsPanel.setBackground(Color.DARK_GRAY);
		staticElementsPanel.setLayout(new GridBagLayout());
		staticElementsPanel.setPreferredSize(new Dimension((int) (displaySize.width * MAINFRAME_DISPLAY_RATIO), (int) (displaySize.getHeight() * 270/1200)));
		staticElementsPanel.setMinimumSize(new Dimension(400, (int) (displaySize.getHeight() * 270/1200)));
		staticElementsPanel.setMaximumSize(new Dimension(displaySize.width, (int) (displaySize.getHeight() * 270/1200)));
	
		welcomeLabel = new JLabel();
		welcomeLabel.setText(languageBundle.getString("welcomeLbl"));
		welcomeLabel.setBackground(Color.ORANGE);
		welcomeLabel.setForeground(Color.WHITE);
		welcomeLabel.setFont(welcomeLabel.getFont().deriveFont(Font.BOLD, 30));
		welcomeLabel.setOpaque(true);
		welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		welcomeLabel.setPreferredSize(new Dimension((int) (displaySize.getWidth() * 800/1920), (int) (displaySize.getHeight() * 40/1200)));
		GridBagLayoutUtilities.addGB(staticElementsPanel, welcomeLabel, 1, 1, 2, 1, new Insets((int) (displaySize.getHeight() * 30/1200), 0, (int) (displaySize.getHeight() * 10/1200), 0));

		JSeparator jSeparator = new JSeparator(SwingConstants.HORIZONTAL);
		jSeparator.setForeground(Color.MAGENTA);
		jSeparator.setBackground(Color.DARK_GRAY);
		jSeparator.setPreferredSize(new Dimension((int) (displaySize.getWidth() * 1000/1920), (int) (displaySize.getHeight() * 5/1200)));
		jSeparator.setOpaque(true);
		GridBagLayoutUtilities.addGB(staticElementsPanel, jSeparator, 1, 2, 2, 1);

		introductionLabel = new JLabel();
		introductionLabel.setPreferredSize(new Dimension((int) (displaySize.getWidth() * 1100/1920), (int) (displaySize.getHeight() * 100/1200)));
		introductionLabel.setText(languageBundle.getString("introductionLbl"));
		introductionLabel.setBackground(WinUtil.COOL_BLUE);
		introductionLabel.setForeground(WinUtil.ULTRA_LIGHT_GRAY);
		introductionLabel.setFont(introductionLabel.getFont().deriveFont(Font.PLAIN, 18));
		introductionLabel.setOpaque(true);

		GridBagLayoutUtilities.addGB(staticElementsPanel, introductionLabel, 1, 3, 2, 1, new Insets((int) (displaySize.getHeight() * 10/1200), 0, (int) (displaySize.getHeight() * 20/1200), 0));

		specialtyLabelDE = new JLabel();
		specialtyLabelDE.setText(languageBundle.getString("subjectLblDE"));
		specialtyLabelDE.setBackground(Color.BLUE);
		specialtyLabelDE.setForeground(Color.WHITE);
		specialtyLabelDE.setFont(specialtyLabelDE.getFont().deriveFont(Font.PLAIN, 18));
		specialtyLabelDE.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagLayoutUtilities.addGB(staticElementsPanel, specialtyLabelDE, 1, 4, 1, 1, GridBagConstraints.BOTH, 1, 1, new Insets(0, 0, 0, 0));
		specialtyLabelDE.setOpaque(true);

		specialtyLabelES = new JLabel();
		specialtyLabelES.setText(languageBundle.getString("subjectLblES"));
		specialtyLabelES.setBackground(Color.CYAN);
		specialtyLabelES.setForeground(Color.WHITE);
		specialtyLabelES.setFont(specialtyLabelES.getFont().deriveFont(Font.PLAIN, 18));
		specialtyLabelES.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagLayoutUtilities.addGB(staticElementsPanel, specialtyLabelES, 2, 4, 1, 1, GridBagConstraints.BOTH, 1, 1, new Insets(0, 0, 0, 0));
		specialtyLabelES.setOpaque(true);

		scrollbar = new JScrollBar(ScrollBar.VERTICAL);
		scrollbar.setUI(new MyScrollBarUI());
		this.setVerticalScrollBar(scrollbar);

		contentPanel.add(staticElementsPanel);
		contentPanel.add(dynamicTestPanel);

		this.getViewport().add(contentPanel);
	}

	
	@Override
	public void updatePanel(PanelEventTransferObject e) {

		if (e.getCurrentLanguage() == ChosenLanguage.German) {
			languageBundle = ResourceBundle.getBundle("languageBundles.lexikon", new Locale("de"));
		} else {
			languageBundle = ResourceBundle.getBundle("languageBundles.lexikon", new Locale("es"));
		}
		
		changeLanguage();
		resizePanelStructur(e.getDynamicTestPanel(), e.getMainFrameWidth());
			
	}
	
	private void changeLanguage() {
		
		welcomeLabel.setText(languageBundle.getString("welcomeLbl"));
		introductionLabel.setText(languageBundle.getString("introductionLbl"));
		specialtyLabelDE.setText(languageBundle.getString("subjectLblDE"));
		specialtyLabelES.setText(languageBundle.getString("subjectLblES"));
	}
	
	private void resizePanelStructur(DynamicTestPanel newDynamicTestPanel, int mainFrameWidth) {
		
		contentPanel.remove(staticElementsPanel);
		contentPanel.remove(dynamicTestPanel);
		
		dynamicTestPanel = newDynamicTestPanel;
		
		int staticHeight = (int) (displaySize.getHeight() * 270/1200);
		int dynamicHeight = dynamicTestPanel.getDynamicPanelHeight();
		
		contentPanel.setPreferredSize(new Dimension(mainFrameWidth, staticHeight + dynamicHeight));
		staticElementsPanel.setPreferredSize(new Dimension(mainFrameWidth, (int) (displaySize.getHeight() * 270/1200)));
	
		contentPanel.add(staticElementsPanel);
		contentPanel.add(dynamicTestPanel);
	}
}
