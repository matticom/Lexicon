package mainFrameMainComponents;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ComponentListener;
import java.awt.event.WindowListener;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JFrame;

import enums.Language;
import eventHandling.PanelEventTransferObject;
import eventHandling.Updatable;
import utilities.WinUtil;


public class MainFrame extends JFrame implements Updatable {

	private ResourceBundle languageBundle;
	private double MAINFRAME_DISPLAY_RATIO;
	
	public MainFrame(double MAINFRAME_DISPLAY_RATIO, ResourceBundle languageBundle) {
		this.languageBundle = languageBundle;
		this.MAINFRAME_DISPLAY_RATIO = MAINFRAME_DISPLAY_RATIO;
		initialize();
	}
	
	private void initialize() {
		setTitle(languageBundle.getString("title"));
		Dimension displaySize = Toolkit.getDefaultToolkit().getScreenSize();
		int mainFrameWidth = (int) (displaySize.getWidth() * MAINFRAME_DISPLAY_RATIO);
		int mainFrameHeight = (int) (displaySize.getHeight() * MAINFRAME_DISPLAY_RATIO);

		setSize(mainFrameWidth, mainFrameHeight);
		setMinimumSize(new Dimension(WinUtil.relW(1315), WinUtil.relH(400)));
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setResizable(true);
		setLocationByPlatform(true);
	}

	@Override
	public void updatePanel(PanelEventTransferObject e) {
		
		if (e.getCurrentLanguage() == Language.GERMAN) {
			languageBundle = ResourceBundle.getBundle("languageBundles.lexikon", new Locale("de"));
		} else {
			languageBundle = ResourceBundle.getBundle("languageBundles.lexikon", new Locale("es"));
		}
		setTitle(languageBundle.getString("title"));
	}
	
	public void setMainFrameWindowListener(WindowListener l) {
		addWindowListener(l);
	}
	
	public void setMainFrameComponentListener(ComponentListener l) {
		addComponentListener(l);
	}
}
