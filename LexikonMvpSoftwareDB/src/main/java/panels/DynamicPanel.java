package panels;

import javax.swing.JPanel;

import eventHandling.DynamicPanels;

public abstract class DynamicPanel extends JPanel{

	protected final int GERMAN = 1;
	protected final int SPANISH = 2;

	protected String searchWord;
	protected int dynamicPanelHeight;
			
	protected JPanel germanPanel, spanishPanel;
	
	protected DynamicPanels dynamicPanel;
	
	
	public DynamicPanel(DynamicPanels dynamicPanel) {
		this.dynamicPanel = dynamicPanel;
	}
	
	public abstract int getDynamicPanelHeight();
	public abstract String getSearchWord();
	public abstract boolean isLetterResult();
}
