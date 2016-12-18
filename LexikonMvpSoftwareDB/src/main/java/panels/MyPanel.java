package panels;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ResourceBundle;

import javax.swing.JScrollPane;

import eventHandling.PanelEventTransferObject;
import eventHandling.Updatable;

public class MyPanel  extends JScrollPane implements Updatable {

	protected int panelWidth;
	protected int panelHeight;
	
	protected ResourceBundle 	languageBundle;
	
	protected Dimension displaySize;
	
	
	
	public MyPanel(ResourceBundle languageBundle, double MAINFRAME_DISPLAY_RATIO) {
		
		this.displaySize = Toolkit.getDefaultToolkit().getScreenSize();
		panelWidth = (int)(displaySize.getWidth() * MAINFRAME_DISPLAY_RATIO);
		this.languageBundle = languageBundle;
	}



	@Override
	public void updatePanel(PanelEventTransferObject e) {
		// TODO Auto-generated method stub
		
	}

}
