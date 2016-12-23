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

import eventHandling.ChosenLanguage;
import eventHandling.PanelEventTransferObject;
import utilities.GridBagLayoutUtilities;
import utilities.WinUtil;

	

public class SearchResultPanelStatic extends MyPanel {
	
	private JPanel contentPanel;
	private JPanel staticElementsPanel;
	private JLabel resultLabel, germanLabel, spanishLabel, germanResultTechnicalTerm, germanResultSpecialty, spanishResultTechnicalTerm, spanishResultSpecialty;

	private SearchResultPanelDynamic dynamicPanel;
	
	
	public SearchResultPanelStatic(ResourceBundle languageBundle, double MAINFRAME_DISPLAY_RATIO, SearchResultPanelDynamic dynamicPanel) {

		super(languageBundle, MAINFRAME_DISPLAY_RATIO);
		this.dynamicPanel = dynamicPanel;
		initialize();
	}

	private void initialize() {
		
		initializePanels();
		createLabels();
		
		contentPanel.add(staticElementsPanel);
		contentPanel.add(dynamicPanel);

		this.getViewport().add(contentPanel);
		
	}
	
	private void initializePanels() {
		contentPanel = new JPanel();
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		contentPanel.setPreferredSize(new Dimension((int)(mainFrameWidth), 500));

		staticElementsPanel = new JPanel();
		staticElementsPanel.setBackground(Color.DARK_GRAY);
		staticElementsPanel.setLayout(new GridBagLayout());
		staticElementsPanel.setPreferredSize(new Dimension((int)(mainFrameWidth), (int)(displaySize.getHeight() * 200/1200)));
		staticElementsPanel.setMinimumSize(new Dimension(displaySize.width, (int)(displaySize.getHeight() * 200/1200)));
		staticElementsPanel.setMaximumSize(new Dimension(displaySize.width, (int)(displaySize.getHeight() * 200/1200)));
	}
	
	private void createLabels() {
		resultLabel = new JLabel(languageBundle.getString("resultsLbl") + "  ' " + dynamicPanel.getSearchWord() + " '");
		WinUtil.configLabel(resultLabel, (int)(displaySize.getWidth() * 1200/1920), (int)(displaySize.getHeight() * 40/1200), Color.WHITE, Color.DARK_GRAY, 25, Font.BOLD);
		GridBagLayoutUtilities.addGB(staticElementsPanel, resultLabel, 1, 1, 4, 1, new Insets((int)(displaySize.getHeight() * 30/1200), 0, (int)(displaySize.getHeight() * 10/1200), 0));
		
		JSeparator jSeparator = new JSeparator(SwingConstants.HORIZONTAL);
		WinUtil.configSeparator(jSeparator, (int)(displaySize.getWidth() * 1000/1920), (int)(displaySize.getHeight() * 5/1200), Color.MAGENTA, Color.DARK_GRAY);
		GridBagLayoutUtilities.addGB(staticElementsPanel, jSeparator, 1, 2, 4, 1);
		
		germanLabel = new JLabel(languageBundle.getString("miDeutsch"));
		WinUtil.configLabel(germanLabel, (int)(displaySize.getWidth() * 300/1920), (int)(displaySize.getHeight() * 30/1200), WinUtil.COOL_BLUE, Color.MAGENTA, 18, Font.BOLD);
		GridBagLayoutUtilities.addGB(staticElementsPanel, germanLabel, 1, 3, 2, 1, GridBagConstraints.BOTH, 1, 0, new Insets((int)(displaySize.getHeight() * 20/1200), 0, 0, 0));
		
		spanishLabel = new JLabel(languageBundle.getString("miSpanisch"));
		WinUtil.configLabel(spanishLabel, (int)(displaySize.getWidth() * 300/1920), (int)(displaySize.getHeight() * 30/1200), WinUtil.STRONG_ORANGE, Color.YELLOW, 18, Font.BOLD);
		GridBagLayoutUtilities.addGB(staticElementsPanel, spanishLabel, 3, 3, 2, 1, GridBagConstraints.BOTH, 1, 0, new Insets((int)(displaySize.getHeight() * 20/1200), 0, 0, 0));

		germanResultTechnicalTerm = new JLabel(languageBundle.getString("resultsTerm"));
		WinUtil.configLabel(germanResultTechnicalTerm, (int)(displaySize.getWidth() * 200/1920), (int)(displaySize.getHeight() * 30/1200), Color.WHITE, Color.BLACK, 15, Font.BOLD);
		GridBagLayoutUtilities.addGB(staticElementsPanel, germanResultTechnicalTerm, 1, 4, 1, 1, GridBagConstraints.BOTH, 1, 1, new Insets(0, 0, 0, 0));
		
		germanResultSpecialty = new JLabel(languageBundle.getString("resultsSubject"));
		WinUtil.configLabel(germanResultSpecialty, (int)(displaySize.getWidth() * 200/1920), (int)(displaySize.getHeight() * 30/1200), Color.WHITE, Color.GREEN, 15, Font.BOLD);
		GridBagLayoutUtilities.addGB(staticElementsPanel, germanResultSpecialty, 2, 4, 1, 1, GridBagConstraints.BOTH, 1, 1, new Insets(0, 0, 0, 0));
		
		spanishResultTechnicalTerm = new JLabel(languageBundle.getString("resultsTerm"));
		WinUtil.configLabel(spanishResultTechnicalTerm, (int)(displaySize.getWidth() * 200/1920), (int)(displaySize.getHeight() * 30/1200), Color.WHITE, Color.BLUE, 15, Font.BOLD);
		GridBagLayoutUtilities.addGB(staticElementsPanel, spanishResultTechnicalTerm, 3, 4, 1, 1, GridBagConstraints.BOTH, 1, 1, new Insets(0, 0, 0, 0));
		
		spanishResultSpecialty = new JLabel(languageBundle.getString("resultsSubject"));
		WinUtil.configLabel(spanishResultSpecialty, (int)(displaySize.getWidth() * 200/1920), (int)(displaySize.getHeight() * 30/1200), Color.WHITE, Color.CYAN, 15, Font.BOLD);
		GridBagLayoutUtilities.addGB(staticElementsPanel, spanishResultSpecialty, 4, 4, 1, 1, GridBagConstraints.NONE, 1, 1, new Insets(0, 0, 0, 0));		
	}
	
	@Override
	public void updatePanel(PanelEventTransferObject e) {

		if (e.getCurrentLanguage() == ChosenLanguage.German) {
			languageBundle = ResourceBundle.getBundle("languageBundles.lexikon", new Locale("de"));
		} else {
			languageBundle = ResourceBundle.getBundle("languageBundles.lexikon", new Locale("es"));
		}
		changeLanguage();
		resizePanelStructur((SearchResultPanelDynamic)e.getDynamicPanel(), e.getMainFrameWidth());	
	}
	
	private void changeLanguage() {
		
		resultLabel.setText(languageBundle.getString("resultsLbl") + "  ' " + dynamicPanel.getSearchWord() + " '");
		germanLabel.setText(languageBundle.getString("miDeutsch"));
		spanishLabel.setText(languageBundle.getString("miSpanisch"));
		germanResultTechnicalTerm.setText(languageBundle.getString("resultsTerm"));
		germanResultSpecialty.setText(languageBundle.getString("resultsSubject"));
		spanishResultTechnicalTerm.setText(languageBundle.getString("resultsTerm"));
		spanishResultSpecialty.setText(languageBundle.getString("resultsSubject"));
	}
	
	private void resizePanelStructur(SearchResultPanelDynamic newDynamicPanel, int mainFrameWidth) {
		
		contentPanel.remove(staticElementsPanel);
		contentPanel.remove(dynamicPanel);
		
		dynamicPanel = newDynamicPanel;
		
		int staticHeight = (int) (displaySize.getHeight() * 200/1200);
		int dynamicHeight = dynamicPanel.getDynamicPanelHeight();
		
		contentPanel.setPreferredSize(new Dimension(mainFrameWidth, staticHeight + dynamicHeight));
		staticElementsPanel.setPreferredSize(new Dimension(mainFrameWidth, staticHeight));
	
		contentPanel.add(staticElementsPanel);
		contentPanel.add(dynamicPanel);
	}
}
