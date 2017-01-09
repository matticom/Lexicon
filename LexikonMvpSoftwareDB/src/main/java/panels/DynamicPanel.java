package panels;

import javax.swing.JPanel;

import enums.DynamicPanels;

public abstract class DynamicPanel extends JPanel{

	protected final int GERMAN = 1;
	protected final int SPANISH = 2;

	protected String searchWord;
	protected int dynamicPanelHeight;
			
	protected JPanel germanPanel, spanishPanel;
	
	protected DynamicPanels dynamicPanelType;
	
	
	public DynamicPanel(DynamicPanels dynamicPanelType) {
		this.dynamicPanelType = dynamicPanelType;
	}
	
	public abstract int getDynamicPanelHeight();
	public abstract String getSearchWord();

	public DynamicPanels getDynamicPanelType() {
		return dynamicPanelType;
	}
}
