package panels;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ResourceBundle;

import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

import eventHandling.Updatable;
import interactElements.MyScrollBarUI;

public abstract class MyPanel  extends JScrollPane implements Updatable {

	protected int mainFrameWidth;
	protected int mainFrameHeight;
	
	protected final int GERMAN = 1;
	protected final int SPANISH = 2;
	
	protected ResourceBundle 	languageBundle;
	
	protected Dimension displaySize;
	private JScrollBar scrollbar;	
	
	public MyPanel(ResourceBundle languageBundle, double MAINFRAME_DISPLAY_RATIO) {
		
		this.displaySize = Toolkit.getDefaultToolkit().getScreenSize();
		mainFrameWidth = (int)(displaySize.getWidth() * MAINFRAME_DISPLAY_RATIO);
		mainFrameHeight = (int)(displaySize.getHeight() * MAINFRAME_DISPLAY_RATIO);
		this.languageBundle = languageBundle;
		
		scrollbar = new JScrollBar(ScrollBar.VERTICAL);
		scrollbar.setUI(new MyScrollBarUI());
		this.setVerticalScrollBar(scrollbar);
		
		this.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.setBorder(new EmptyBorder(0, 0, 0, 0));
	}


}
