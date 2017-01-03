package viewFactory;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;

import eventHandling.ChosenLanguage;
import eventHandling.PanelEventTransferObject;
import eventHandling.Updatable;
import utilities.WinUtil;

public class MenuBar extends JMenuBar implements Updatable {

	private JMenu menuFile, menuEdit, menuView, miChooseLanguage, menuExtras;
	private JMenuItem miExit, miNew, miAssign, miHistory;
	private JRadioButtonMenuItem miGerman, miSpanish;
	
	private ResourceBundle languageBundle;
			
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
										new ImageIcon(this.getClass().getResource("/images/Delete.png")), 't', null);
		miNew = WinUtil.createMenuItem(menuEdit, "neu", WinUtil.MenuItemType.ITEM_PLAIN, null, languageBundle.getString("miNeu"),
										new ImageIcon(this.getClass().getResource("/images/New.png")), 'u', null);
		miAssign = WinUtil.createMenuItem(menuEdit, "zuordnen", WinUtil.MenuItemType.ITEM_PLAIN, null, languageBundle.getString("miZuordnen"),
				new ImageIcon(this.getClass().getResource("/images/Edit.png")), 'o', null);
		miChooseLanguage = WinUtil.createSubMenu(menuView, languageBundle.getString("miWaehlSprache"), "chooseLanguage", 'w');
		miHistory = WinUtil.createMenuItem(menuExtras, "history", WinUtil.MenuItemType.ITEM_PLAIN, null, languageBundle.getString("miHistory"), 
											new ImageIcon(this.getClass().getResource("/images/Delete.png")), 'h', "Geschichte löschen");
				
		miGerman = (JRadioButtonMenuItem)WinUtil.createMenuItem(miChooseLanguage, "deutschBtn", WinUtil.MenuItemType.ITEM_RADIO , null,
																	languageBundle.getString("miDeutsch"), null, 0, null);
		
		miSpanish = (JRadioButtonMenuItem)WinUtil.createMenuItem(miChooseLanguage, "deutschBtn", WinUtil.MenuItemType.ITEM_RADIO , null,
																	languageBundle.getString("miSpanisch"), null, 0, null);
		ButtonGroup sprachButtonGroup = new ButtonGroup();	
		sprachButtonGroup.add(miGerman);
		sprachButtonGroup.add(miSpanish);
	}
	
	@Override
	public void updatePanel(PanelEventTransferObject e) {
		
		if (e.getCurrentLanguage() == ChosenLanguage.German) {
			languageBundle = ResourceBundle.getBundle("languageBundles.lexikon", new Locale("de"));
			((JRadioButtonMenuItem)miGerman).setSelected(true);
			((JRadioButtonMenuItem)miSpanish).setSelected(false);
			
		} else {
			languageBundle = ResourceBundle.getBundle("languageBundles.lexikon", new Locale("es"));
			((JRadioButtonMenuItem)miGerman).setSelected(false);
			((JRadioButtonMenuItem)miSpanish).setSelected(true);
		}
		changeGuiTextLanguage();
	}
	
	
	private void changeGuiTextLanguage() {

		menuFile.setText(languageBundle.getString("menuDatei"));
		menuEdit.setText(languageBundle.getString("menuBearbeiten"));
		menuView.setText(languageBundle.getString("menuAnsicht"));
		menuExtras.setText(languageBundle.getString("menuExtras"));
		miExit.setText(languageBundle.getString("miBeenden"));
		miNew.setText(languageBundle.getString("miNeu"));
		miAssign.setText(languageBundle.getString("miZuordnen"));
		miChooseLanguage.setText(languageBundle.getString("miWaehlSprache"));
		miHistory.setText(languageBundle.getString("miHistory"));
		miGerman.setText(languageBundle.getString("miDeutsch"));
		miSpanish.setText(languageBundle.getString("miSpanisch"));
	}
	
	public void setMiExitActionListener(ActionListener l) {
		miExit.addActionListener(l);
	}
	
	public void setMiNewActionListener(ActionListener l) {
		miNew.addActionListener(l);
	}
	
	public void setMiAssignActionListener(ActionListener l) {
		miAssign.addActionListener(l);
	}
	
	public void setMiHistoryActionListener(ActionListener l) {
		miHistory.addActionListener(l);
	}
	
	public void setMiGermanActionListener(ActionListener l) {
		miGerman.addActionListener(l);
	}
	
	public void setMiSpanishActionListener(ActionListener l) {
		miSpanish.addActionListener(l);
	}
	
	public void setHistoryEnable(boolean state) {
		miHistory.setEnabled(state);
	}
}
