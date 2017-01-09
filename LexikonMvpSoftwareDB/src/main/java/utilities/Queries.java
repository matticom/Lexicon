package utilities;

import java.util.ResourceBundle;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class Queries {

	public static boolean queryAbortEditContentDialog(ResourceBundle languageBundle, boolean textHasChangedInTextAreas, JDialog parent) {

		String[] options = { languageBundle.getString("close"), languageBundle.getString("abort") };

		if (!textHasChangedInTextAreas) {
			return true;
		}
		Object paneBG = UIManager.get("OptionPane.background");
	    Object panelBG = UIManager.get("Panel.background");
	    Object buttonBG = UIManager.get("Button.background");
	    Object buttonFG = UIManager.get("Button.foreground");
	    
	    UIManager.put("OptionPane.background", WinUtil.LIGHT_BLACK);
	    UIManager.put("Panel.background", WinUtil.LIGHT_BLACK);
	    UIManager.put("Button.background", WinUtil.LIGHT_BLACK);
	    UIManager.put("Button.foreground", WinUtil.DARK_WHITE);
	
		int retValue = JOptionPane.showOptionDialog(parent, languageBundle.getString("queryEdit"), languageBundle.getString("queryExitTitle"), JOptionPane.YES_NO_OPTION,
				JOptionPane.WARNING_MESSAGE, null, options, options[1]);
		
		UIManager.put("OptionPane.background", paneBG);
	    UIManager.put("Panel.background", panelBG);
	    UIManager.put("Button.background", buttonBG);
	    UIManager.put("Button.foreground", buttonFG);
	    
		if (retValue == JOptionPane.YES_OPTION) {
			return true;
		}
		return false;
	}
	
	public static boolean querySaveContentDialog(ResourceBundle languageBundle, boolean textHasChangedInTextAreas, JDialog parent)
	{
		
		String[] options = { languageBundle.getString("speichern"), languageBundle.getString("abort") };

		if (!textHasChangedInTextAreas)
			return true;
		
		Object paneBG = UIManager.get("OptionPane.background");
	    Object panelBG = UIManager.get("Panel.background");
	    Object buttonBG = UIManager.get("Button.background");
	    Object buttonFG = UIManager.get("Button.foreground");
	    
	    UIManager.put("OptionPane.background", WinUtil.LIGHT_BLACK);
	    UIManager.put("Panel.background", WinUtil.LIGHT_BLACK);
	    UIManager.put("Button.background", WinUtil.LIGHT_BLACK);
	    UIManager.put("Button.foreground", WinUtil.DARK_WHITE);
		
		int retValue = JOptionPane.showOptionDialog(parent, languageBundle.getString("speichernDaten"), languageBundle.getString("speichernDatenTitle"),
				JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[1]);

		UIManager.put("OptionPane.background", paneBG);
	    UIManager.put("Panel.background", panelBG);
	    UIManager.put("Button.background", buttonBG);
	    UIManager.put("Button.foreground", buttonFG);
		
		if (retValue == JOptionPane.YES_OPTION)
			return true;
		
		return false;
	}
	
	public static boolean queryExit(ResourceBundle languageBundle, JFrame parent) {

		String[] options = { languageBundle.getString("close"), languageBundle.getString("abort") };
		
		Object paneBG = UIManager.get("OptionPane.background");
	    Object panelBG = UIManager.get("Panel.background");
	    Object buttonBG = UIManager.get("Button.background");
	    Object buttonFG = UIManager.get("Button.foreground");
	    
	    UIManager.put("OptionPane.background", WinUtil.LIGHT_BLACK);
	    UIManager.put("Panel.background", WinUtil.LIGHT_BLACK);
	    UIManager.put("Button.background", WinUtil.LIGHT_BLACK);
	    UIManager.put("Button.foreground", WinUtil.DARK_WHITE);
		
		int retValue = JOptionPane.showOptionDialog(parent, languageBundle.getString("queryExitMF"), languageBundle.getString("queryExitTitleMF"), JOptionPane.YES_NO_OPTION,
				JOptionPane.WARNING_MESSAGE, null, options, options[1]);
		
		UIManager.put("OptionPane.background", paneBG);
	    UIManager.put("Panel.background", panelBG);
	    UIManager.put("Button.background", buttonBG);
	    UIManager.put("Button.foreground", buttonFG);
		
		if (retValue == JOptionPane.YES_OPTION) {
			return true;
		}
		return false;
	}
	
	
	public static boolean queryCreateNewTT(ResourceBundle languageBundle, JDialog parent) {

		String[] options = { languageBundle.getString("speichernDatenTitle"), languageBundle.getString("abort") };
		
		Object paneBG = UIManager.get("OptionPane.background");
	    Object panelBG = UIManager.get("Panel.background");
	    Object buttonBG = UIManager.get("Button.background");
	    Object buttonFG = UIManager.get("Button.foreground");
	    
	    UIManager.put("OptionPane.background", WinUtil.LIGHT_BLACK);
	    UIManager.put("Panel.background", WinUtil.LIGHT_BLACK);
	    UIManager.put("Button.background", WinUtil.LIGHT_BLACK);
	    UIManager.put("Button.foreground", WinUtil.DARK_WHITE);
		
		int retValue = JOptionPane.showOptionDialog(parent, languageBundle.getString("saveNewTTQuestion"), languageBundle.getString("newTTtitle"), JOptionPane.YES_NO_OPTION,
				JOptionPane.WARNING_MESSAGE, null, options, options[1]);
		
		UIManager.put("OptionPane.background", paneBG);
	    UIManager.put("Panel.background", panelBG);
	    UIManager.put("Button.background", buttonBG);
	    UIManager.put("Button.foreground", buttonFG);
		
		if (retValue == JOptionPane.YES_OPTION) {
			return true;
		}
		return false;
	}
	
}
