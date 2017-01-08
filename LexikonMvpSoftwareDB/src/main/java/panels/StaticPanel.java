package panels;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ResourceBundle;

import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

import eventHandling.StaticPanels;
import eventHandling.Updatable;
import interactElements.MyScrollBarUI;

public abstract class StaticPanel  extends JScrollPane implements Updatable {

	protected int mainFrameWidth;
	protected int mainFrameHeight;
	
	protected final int GERMAN = 1;
	protected final int SPANISH = 2;
	
	protected ResourceBundle 	languageBundle;
	StaticPanels staticPanel;
	
	protected Dimension displayResolution;	
	
	public StaticPanel(ResourceBundle languageBundle, double MAINFRAME_DISPLAY_RATIO, StaticPanels staticPanel) {
		
		this.displayResolution = Toolkit.getDefaultToolkit().getScreenSize();
		mainFrameWidth = (int)(displayResolution.getWidth() * MAINFRAME_DISPLAY_RATIO);
		mainFrameHeight = (int)(displayResolution.getHeight() * MAINFRAME_DISPLAY_RATIO);
		this.languageBundle = languageBundle;
		this.staticPanel = staticPanel;
		
		JScrollBar scrollbar = new JScrollBar(ScrollBar.VERTICAL);
		scrollbar.setUI(new MyScrollBarUI());
		this.setVerticalScrollBar(scrollbar);
		
		this.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.setBorder(new EmptyBorder(0, 0, 0, 0));
	}


}
