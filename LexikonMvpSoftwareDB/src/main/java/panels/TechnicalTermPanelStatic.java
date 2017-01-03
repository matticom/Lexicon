package panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import eventHandling.ChosenLanguage;
import eventHandling.PanelEventTransferObject;
import utilities.GridBagLayoutUtilities;
import utilities.WinUtil;

public class TechnicalTermPanelStatic extends MyPanel {
	
	private JPanel contentPanel;
	protected JPanel staticElementsPanel;
	private JLabel resultLabel;
	protected JLabel germanLabel; 
	protected JLabel spanishLabel;
	private String labelTitle;
	
	protected int staticPanelHeight;

	private DynamicPanel dynamicPanel;
	
	
	public TechnicalTermPanelStatic(ResourceBundle languageBundle, double MAINFRAME_DISPLAY_RATIO, DynamicPanel dynamicPanel, String labelTitle, int staticPanelHeight) {

		super(languageBundle, MAINFRAME_DISPLAY_RATIO);
		this.labelTitle = labelTitle;
		this.dynamicPanel = dynamicPanel;
		this.staticPanelHeight = staticPanelHeight;
		initialize();
	}

	private void initialize() {
		
		initializePanels();
		createLabels();
		createLabelsExtends();
		
		contentPanel.add(staticElementsPanel);
		contentPanel.add((JPanel)dynamicPanel);

		this.getViewport().add(contentPanel);
	}
	
	private void initializePanels() {
		contentPanel = new JPanel();
		contentPanel.setBackground(WinUtil.LIGHT_BLACK);
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		contentPanel.setPreferredSize(new Dimension(mainFrameWidth, 500));

		staticElementsPanel = new JPanel();
		staticElementsPanel.setBackground(WinUtil.LIGHT_BLACK);
		staticElementsPanel.setLayout(new GridBagLayout());
		staticElementsPanel.setPreferredSize(new Dimension(mainFrameWidth, WinUtil.relH(staticPanelHeight)));
		staticElementsPanel.setMinimumSize(new Dimension(displayResolution.width, WinUtil.relH(staticPanelHeight)));
		staticElementsPanel.setMaximumSize(new Dimension(displayResolution.width, WinUtil.relH(staticPanelHeight)));
	}
	
	private void createLabels() {
		resultLabel = new JLabel(languageBundle.getString(labelTitle) + "  ' " + dynamicPanel.getSearchWord() + " '");
		WinUtil.configLabel(resultLabel, WinUtil.relW(1200),  WinUtil.relH(40), Color.WHITE, WinUtil.LIGHT_BLACK, 25, Font.BOLD);
		GridBagLayoutUtilities.addGB(staticElementsPanel, resultLabel, 1, 1, 4, 1, new Insets(WinUtil.relH(30), 0, WinUtil.relH(10), 0));
		
		JSeparator jSeparator = new JSeparator(SwingConstants.HORIZONTAL);
		WinUtil.configSeparator(jSeparator, WinUtil.relW(1000),  WinUtil.relH(5), Color.MAGENTA, WinUtil.LIGHT_BLACK);
		GridBagLayoutUtilities.addGB(staticElementsPanel, jSeparator, 1, 2, 4, 1);		
	}
	
	protected void createLabelsExtends() {
		
		germanLabel = new JLabel(languageBundle.getString("miDeutsch"));
		WinUtil.configLabel(germanLabel, WinUtil.relW(300),  WinUtil.relH(30), WinUtil.COOL_BLUE, WinUtil.LIGHT_BLACK, 18, Font.BOLD);
		GridBagLayoutUtilities.addGB(staticElementsPanel, germanLabel, 1, 3, 2, 1, GridBagConstraints.BOTH, 1, 1, new Insets(WinUtil.relH(20), 0, 0, 0));
		
		spanishLabel = new JLabel(languageBundle.getString("miSpanisch"));
		WinUtil.configLabel(spanishLabel, WinUtil.relW(300),  WinUtil.relH(30), WinUtil.STRONG_ORANGE, WinUtil.LIGHT_BLACK, 18, Font.BOLD);
		GridBagLayoutUtilities.addGB(staticElementsPanel, spanishLabel, 3, 3, 2, 1, GridBagConstraints.BOTH, 1, 1, new Insets(WinUtil.relH(20), 0, 0, 0));
	}
	
	@Override
	public void updatePanel(PanelEventTransferObject e) {

		if (e.getCurrentLanguage() == ChosenLanguage.German) {
			languageBundle = ResourceBundle.getBundle("languageBundles.lexikon", new Locale("de"));
		} else {
			languageBundle = ResourceBundle.getBundle("languageBundles.lexikon", new Locale("es"));
		}
		changeLanguage();
		changeLanguageExtends();
		resizePanelStructur((DynamicPanel)e.getDynamicPanel(), e.getMainFrameWidth());	
	}
	
	private void changeLanguage() {
		
		resultLabel.setText(languageBundle.getString(labelTitle) + "  ' " + dynamicPanel.getSearchWord() + " '");
		germanLabel.setText(languageBundle.getString("miDeutsch"));
		spanishLabel.setText(languageBundle.getString("miSpanisch"));
	}
	
	protected void changeLanguageExtends() {}
	
	private void resizePanelStructur(DynamicPanel newDynamicPanel, int mainFrameWidth) {
		
		contentPanel.remove(staticElementsPanel);
		contentPanel.remove((JPanel)dynamicPanel);
		
		dynamicPanel = newDynamicPanel;
		
		int staticHeight = WinUtil.relH(staticPanelHeight);
		int dynamicHeight = dynamicPanel.getDynamicPanelHeight();
		
		contentPanel.setPreferredSize(new Dimension(mainFrameWidth, staticHeight + dynamicHeight));
		staticElementsPanel.setPreferredSize(new Dimension(mainFrameWidth, staticHeight));
	
		contentPanel.add(staticElementsPanel);
		contentPanel.add((JPanel)dynamicPanel);
	}
}
