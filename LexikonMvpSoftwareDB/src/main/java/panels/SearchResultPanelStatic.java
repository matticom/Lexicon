package panels;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import utilities.GridBagLayoutUtilities;
import utilities.WinUtil;

public class SearchResultPanelStatic extends TechnicalTermPanelStatic {

	private JLabel germanResultTechnicalTerm;
	private JLabel germanResultSpecialty;
	private JLabel spanishResultTechnicalTerm; 
	private JLabel spanishResultSpecialty;
	
	private boolean isLetterResult;
	
	public SearchResultPanelStatic(ResourceBundle languageBundle, double MAINFRAME_DISPLAY_RATIO, DynamicPanel dynamicPanel, String labelTitle, int staticPanelHeight) {
		
		super(languageBundle, MAINFRAME_DISPLAY_RATIO, dynamicPanel, labelTitle, staticPanelHeight);
		isLetterResult = dynamicPanel.isLetterResult();
	}

	@Override
	protected void createLabelsExtends() {
		
		germanLabel = new JLabel(languageBundle.getString("miDeutsch"));
		WinUtil.configLabel(germanLabel, WinUtil.relW(300),  WinUtil.relH(30), WinUtil.COOL_BLUE, WinUtil.LIGHT_BLACK, 18, Font.BOLD);
		GridBagLayoutUtilities.addGB(staticElementsPanel, germanLabel, 1, 3, 2, 1, GridBagConstraints.BOTH, 1, 0, new Insets(WinUtil.relH(20), 0, 0, 0));
		
		spanishLabel = new JLabel(languageBundle.getString("miSpanisch"));
		WinUtil.configLabel(spanishLabel, WinUtil.relW(300),  WinUtil.relH(30), WinUtil.STRONG_ORANGE, WinUtil.LIGHT_BLACK, 18, Font.BOLD);
		GridBagLayoutUtilities.addGB(staticElementsPanel, spanishLabel, 3, 3, 2, 1, GridBagConstraints.BOTH, 1, 0, new Insets(WinUtil.relH(20), 0, 0, 0));
		
		germanResultTechnicalTerm = new JLabel(languageBundle.getString("resultsTerm"));
		WinUtil.configLabel(germanResultTechnicalTerm, WinUtil.relW(200),  WinUtil.relH(30), Color.WHITE, WinUtil.LIGHT_BLACK, 15, Font.BOLD);
		GridBagLayoutUtilities.addGB(staticElementsPanel, germanResultTechnicalTerm, 1, 4, 1, 1, GridBagConstraints.BOTH, 1, 1, new Insets(0, 0, 0, 0));
		
		germanResultSpecialty = new JLabel(languageBundle.getString("resultsSubject"));
		WinUtil.configLabel(germanResultSpecialty, WinUtil.relW(200),  WinUtil.relH(30), Color.WHITE, WinUtil.LIGHT_BLACK, 15, Font.BOLD);
		GridBagLayoutUtilities.addGB(staticElementsPanel, germanResultSpecialty, 2, 4, 1, 1, GridBagConstraints.BOTH, 1, 1, new Insets(0, 0, 0, 0));
		
		spanishResultTechnicalTerm = new JLabel(languageBundle.getString("resultsTerm"));
		WinUtil.configLabel(spanishResultTechnicalTerm, WinUtil.relW(200),  WinUtil.relH(30), Color.WHITE, WinUtil.LIGHT_BLACK, 15, Font.BOLD);
		GridBagLayoutUtilities.addGB(staticElementsPanel, spanishResultTechnicalTerm, 3, 4, 1, 1, GridBagConstraints.BOTH, 1, 1, new Insets(0, 0, 0, 0));
		
		spanishResultSpecialty = new JLabel(languageBundle.getString("resultsSubject"));
		WinUtil.configLabel(spanishResultSpecialty, WinUtil.relW(200),  WinUtil.relH(30), Color.WHITE, WinUtil.LIGHT_BLACK, 15, Font.BOLD);
		GridBagLayoutUtilities.addGB(staticElementsPanel, spanishResultSpecialty, 4, 4, 1, 1, GridBagConstraints.NONE, 1, 1, new Insets(0, 0, 0, 0));
	}

	@Override
	protected void changeLanguageExtends() {
		
		germanResultTechnicalTerm.setText(languageBundle.getString("resultsTerm"));
		germanResultSpecialty.setText(languageBundle.getString("resultsSubject"));
		spanishResultTechnicalTerm.setText(languageBundle.getString("resultsTerm"));
		spanishResultSpecialty.setText(languageBundle.getString("resultsSubject"));
	}
	
	public boolean isLetterResult() {
		return isLetterResult;
	}
}
