package panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import enums.Language;
import enums.StaticPanels;
import eventHandling.PanelEventTransferObject;
import utilities.GridBagLayoutUtilities;
import utilities.WinUtil;

public class SpecialtyPanelStatic extends StaticPanel {

	private JPanel contentPanel;
	private JPanel staticElementsPanel;
	private JLabel welcomeLabel, introductionLabel, specialtyLabelDE, specialtyLabelES;

	private TermPanelDynamic dynamicPanel;

	public SpecialtyPanelStatic(ResourceBundle languageBundle, double MAINFRAME_DISPLAY_RATIO, TermPanelDynamic dynamicPanel, StaticPanels staticPanel) {

		super(languageBundle, MAINFRAME_DISPLAY_RATIO, staticPanel);
		this.dynamicPanel = dynamicPanel;
		initialize();
	}

	private void initialize() {
		
		initializePanels();
		createLabels();		
		
		contentPanel.add(staticElementsPanel);
		contentPanel.add(dynamicPanel);

		getViewport().add(contentPanel);
	}
	
	private void initializePanels() {
		contentPanel = new JPanel();
		contentPanel.setBackground(WinUtil.LIGHT_BLACK);
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		contentPanel.setPreferredSize(new Dimension((int) (mainFrameWidth), 500));

		staticElementsPanel = new JPanel();
		staticElementsPanel.setBackground(WinUtil.LIGHT_BLACK);
		staticElementsPanel.setLayout(new GridBagLayout());
		staticElementsPanel.setPreferredSize(new Dimension((int) (mainFrameWidth), WinUtil.relH(270)));
		staticElementsPanel.setMinimumSize(new Dimension(displayResolution.width, WinUtil.relH(270)));
		staticElementsPanel.setMaximumSize(new Dimension(displayResolution.width, WinUtil.relH(270)));
	}
	
	private void createLabels() {
		welcomeLabel = new JLabel(languageBundle.getString("welcomeLbl"));
		WinUtil.configLabel(welcomeLabel,  WinUtil.relW(800),  WinUtil.relH(40), Color.WHITE, WinUtil.LIGHT_BLACK, 30, Font.BOLD);
		GridBagLayoutUtilities.addGB(staticElementsPanel, welcomeLabel, 1, 1, 2, 1, new Insets(WinUtil.relH(30), 0, WinUtil.relH(10), 0));

		JSeparator jSeparator = new JSeparator(SwingConstants.HORIZONTAL);
		WinUtil.configSeparator(jSeparator, WinUtil.relW(1000),  WinUtil.relH(5), Color.MAGENTA, WinUtil.LIGHT_BLACK);
		GridBagLayoutUtilities.addGB(staticElementsPanel, jSeparator, 1, 2, 2, 1);

		introductionLabel = new JLabel(languageBundle.getString("introductionLbl"));
		WinUtil.configLabel(introductionLabel, WinUtil.relW(1200),  WinUtil.relH(100), WinUtil.ULTRA_LIGHT_GRAY, Color.DARK_GRAY, 20, Font.PLAIN);
		GridBagLayoutUtilities.addGB(staticElementsPanel, introductionLabel, 1, 3, 2, 1, new Insets(WinUtil.relH(10), 0, WinUtil.relH(20), 0));
		introductionLabel.setBorder(new EmptyBorder(5, 40, 5, 40));
		
		specialtyLabelDE = new JLabel(languageBundle.getString("subjectLblDE"));
		WinUtil.configLabel(specialtyLabelDE, WinUtil.relW(200),  WinUtil.relH(30), Color.WHITE, WinUtil.LIGHT_BLACK, 18, Font.PLAIN);
		GridBagLayoutUtilities.addGB(staticElementsPanel, specialtyLabelDE, 1, 4, 1, 1, GridBagConstraints.BOTH, 1, 1, new Insets(0, 0, 0, 0));

		specialtyLabelES = new JLabel(languageBundle.getString("subjectLblES"));
		WinUtil.configLabel(specialtyLabelES, WinUtil.relW(200),  WinUtil.relH(30), Color.WHITE, WinUtil.LIGHT_BLACK, 18, Font.PLAIN);
		GridBagLayoutUtilities.addGB(staticElementsPanel, specialtyLabelES, 2, 4, 1, 1, GridBagConstraints.BOTH, 1, 1, new Insets(0, 0, 0, 0));
	}

	
	@Override
	public void updatePanel(PanelEventTransferObject e) {

		if (e.getCurrentLanguage() == Language.GERMAN) {
			languageBundle = ResourceBundle.getBundle("languageBundles.lexikon", new Locale("de"));
		} else {
			languageBundle = ResourceBundle.getBundle("languageBundles.lexikon", new Locale("es"));
		}
		changeLanguage();
		resizePanelStructur((TermPanelDynamic)e.getDynamicPanel(), e.getMainFrameWidth());	
	}
	
	private void changeLanguage() {
		
		welcomeLabel.setText(languageBundle.getString("welcomeLbl"));
		introductionLabel.setText(languageBundle.getString("introductionLbl"));
		specialtyLabelDE.setText(languageBundle.getString("subjectLblDE"));
		specialtyLabelES.setText(languageBundle.getString("subjectLblES"));
	}
	
	private void resizePanelStructur(TermPanelDynamic newDynamicPanel, int mainFrameWidth) {
		
		contentPanel.remove(staticElementsPanel);
		contentPanel.remove(dynamicPanel);
		
		dynamicPanel = newDynamicPanel;
		
		int staticHeight = WinUtil.relH(270);
		int dynamicHeight = dynamicPanel.getDynamicPanelHeight();
		
		contentPanel.setPreferredSize(new Dimension(mainFrameWidth, staticHeight + dynamicHeight));
		staticElementsPanel.setPreferredSize(new Dimension(mainFrameWidth, staticHeight));
	
		contentPanel.add(staticElementsPanel);
		contentPanel.add(dynamicPanel);
		validate();
		repaint();
	}
}
