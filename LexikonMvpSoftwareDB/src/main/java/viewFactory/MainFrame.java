package viewFactory;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JFrame;

import eventHandling.PanelEventTransferObject;
import eventHandling.Updatable;
import panels.MyPanel;

public class MainFrame extends JFrame  implements Updatable {

	public MainFrame(MenuBar menuBar, MenuBar headBar, MenuBar statusBar, MyPanel panel, ResourceBundle languageBundle, Locale currentLocale) {
		initializeComponents(menuBar, headBar, statusBar, panel, languageBundle, currentLocale);
	}
	
	private void initializeComponents(MenuBar menuBar, MenuBar headBar, MenuBar statusBar, MyPanel panel, ResourceBundle languageBundle, Locale currentLocale) {
		
	}
	
	@Override
	public void updateFrame(PanelEventTransferObject e) {
		// TODO Auto-generated method stub
		
	}

}
