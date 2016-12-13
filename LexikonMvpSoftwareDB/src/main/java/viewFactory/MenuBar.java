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
	private JMenuItem miExit, miNew, miHistory;
	private JRadioButtonMenuItem miGerman, miSpanish;
	
	private ResourceBundle languageBundle;
	
	double[] esButtonsWidth = new double[]{55, 53, 33, 49, 93, 123, 137, 203, 81, 81};
	double[] deButtonsWidth = new double[]{41, 75, 55, 49, 91, 117, 133, 129, 85, 87};
	double[] refButtonWidth = new double[10];
		
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
			refButtonWidth = deButtonsWidth;
		} else {
			languageBundle = ResourceBundle.getBundle("languageBundles.lexikon", new Locale("es"));
			refButtonWidth = esButtonsWidth;
		}
		changeGuiTextLanguage();
		changeComponentsSizeOnResize(e.getMainFrameHeight());
	}
	
	private void changeComponentsSizeOnResize(int mainFrameHeight) {

		int buttonHeight = (int) (mainFrameHeight * 0.026);
		resizeMenuElements(buttonHeight);
	}
	
	private void resizeMenuElements(int buttonHeight) {
		
		int fontResize = (int) (0.696 * buttonHeight - 15.063);
		menuFile.setFont(menuFile.getFont().deriveFont(Font.BOLD, 12 + fontResize));
		menuFile.setPreferredSize(new Dimension((int)(refButtonWidth[0]*(buttonHeight/20.0)), buttonHeight));
		menuEdit.setFont(menuEdit.getFont().deriveFont(Font.BOLD, 12 + fontResize));
		menuEdit.setPreferredSize(new Dimension((int)(refButtonWidth[1]*(buttonHeight/20.0)), buttonHeight));
		menuView.setFont(menuView.getFont().deriveFont(Font.BOLD, 12 + fontResize));
		menuView.setPreferredSize(new Dimension((int)(refButtonWidth[2]*(buttonHeight/20.0)), buttonHeight));
		menuExtras.setFont(menuExtras.getFont().deriveFont(Font.BOLD, 12 + fontResize));
		menuExtras.setPreferredSize(new Dimension((int)(refButtonWidth[3]*(buttonHeight/20.0)), buttonHeight));
		miExit.setFont(miExit.getFont().deriveFont(Font.BOLD, 12 + fontResize));
		miExit.setPreferredSize(new Dimension((int)(refButtonWidth[4]*(buttonHeight/20.0)), buttonHeight));
		miNew.setFont(miNew.getFont().deriveFont(Font.BOLD, 12 + fontResize));
		miNew.setPreferredSize(new Dimension((int)(refButtonWidth[5]*(buttonHeight/20.0)), buttonHeight));
		miChooseLanguage.setFont(miChooseLanguage.getFont().deriveFont(Font.BOLD, 12 + fontResize));
		miChooseLanguage.setPreferredSize(new Dimension((int)(refButtonWidth[6]*(buttonHeight/20.0)), buttonHeight));
		miHistory.setFont(miHistory.getFont().deriveFont(Font.BOLD, 12 + fontResize));
		miHistory.setPreferredSize(new Dimension((int)(refButtonWidth[7]*(buttonHeight/20.0)), buttonHeight));
		miGerman.setFont(miGerman.getFont().deriveFont(Font.BOLD, 12 + fontResize));
		miGerman.setPreferredSize(new Dimension((int)(refButtonWidth[8]*(buttonHeight/20.0)), buttonHeight));
		miSpanish.setFont(miSpanish.getFont().deriveFont(Font.BOLD, 12 + fontResize));
		miSpanish.setPreferredSize(new Dimension((int)(refButtonWidth[9]*(buttonHeight/20.0)), buttonHeight));
	}
	
	private void changeGuiTextLanguage() {

		menuFile.setText(languageBundle.getString("menuDatei"));
		menuEdit.setText(languageBundle.getString("menuBearbeiten"));
		menuView.setText(languageBundle.getString("menuAnsicht"));
		menuExtras.setText(languageBundle.getString("menuExtras"));
		miExit.setText(languageBundle.getString("miBeenden"));
		miNew.setText(languageBundle.getString("miNeu"));
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
	
	public void setMiHistoryActionListener(ActionListener l) {
		miNew.addActionListener(l);
	}
	
	public void setMiGermanActionListener(ActionListener l) {
		miGerman.addActionListener(l);
	}
	
	public void setMiSpanishActionListener(ActionListener l) {
		miSpanish.addActionListener(l);
	}
	
	

}
