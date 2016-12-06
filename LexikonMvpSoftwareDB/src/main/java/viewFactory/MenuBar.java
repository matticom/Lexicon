package viewFactory;

import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;

import eventHandling.PanelEventTransferObject;
import eventHandling.Updatable;
import utilities.WinUtil;

public class MenuBar extends JMenuBar implements Updatable {

	private JMenu menuFile, menuEdit, menuView, miChooseLanguage, menuExtras;
	private JMenuItem miExit, miNew, miHistory;
	private JRadioButtonMenuItem miGerman, miSpanish;
	
	private ResourceBundle languageBundle;
	private Locale currentLocale;
	
	public MenuBar(ResourceBundle languageBundle) {
		
		this.languageBundle = languageBundle;
		initialize();
	}
	
	public void initialize() {
		
		menuFile = WinUtil.createMenu(this, languageBundle.getString("menuDatei"), "datei", 'a');
		menuEdit = WinUtil.createMenu(this, languageBundle.getString("menuBearbeiten"), "bearbeiten", 'e');
		menuView = WinUtil.createMenu(this, languageBundle.getString("menuAnsicht"), "ansicht", 'n');
		menuExtras = WinUtil.createMenu(this, languageBundle.getString("menuExtras"), "extras", 'x');
		miExit = WinUtil.createMenuItem(menuFile, "exit", WinUtil.MenuItemType.ITEM_PLAIN, null, languageBundle.getString("miBeenden"),
										new ImageIcon(this.getClass().getResource(".src/main/resources/images/Delete.png")), 't', null);
		miNew = WinUtil.createMenuItem(menuEdit, "neu", WinUtil.MenuItemType.ITEM_PLAIN, null, languageBundle.getString("miNeu"),
										new ImageIcon(this.getClass().getResource(".src/main/resources/images/New.png")), 'u', null);
		miChooseLanguage = WinUtil.createSubMenu(menuView, languageBundle.getString("miWaehlSprache"), "chooseLanguage", 'w');
		miHistory = WinUtil.createMenuItem(menuExtras, "history", WinUtil.MenuItemType.ITEM_PLAIN, null, languageBundle.getString("miHistory"), 
											new ImageIcon(this.getClass().getResource(".src/main/resources/images/Delete.png")), 'h', "Geschichte löschen");
				
		miGerman = (JRadioButtonMenuItem)WinUtil.createMenuItem(miChooseLanguage, "deutschBtn", WinUtil.MenuItemType.ITEM_RADIO , null,
																	languageBundle.getString("miDeutsch"), null, 0, null);
		
		miSpanish = (JRadioButtonMenuItem)WinUtil.createMenuItem(miChooseLanguage, "deutschBtn", WinUtil.MenuItemType.ITEM_RADIO , null,
																	languageBundle.getString("miSpanisch"), null, 0, null);
		ButtonGroup sprachButtonGroup = new ButtonGroup();	
		sprachButtonGroup.add(miGerman);
		sprachButtonGroup.add(miSpanish);
	}
	
	public void setMiExitActionListener(ActionListener l) {
		miExit.addActionListener(l);
	}
	
	public void setMiNewActionListener(ActionListener l) {
		miNew.addActionListener(l);
	}
	
	public void setMiHistoryActionListener(ActionListener l) {
		miNew.addActionListener(l);
	}
	
	public void setMiGermanActionListener(ActionListener l) {
		miGerman.addActionListener(l);
	}
	
	public void setMiSpanishActionListener(ActionListener l) {
		miSpanish.addActionListener(l);
	}
	
	@Override
	public void updateFrame(PanelEventTransferObject e) {
		// TODO Auto-generated method stub
		
	}

}
