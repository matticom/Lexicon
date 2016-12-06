package viewFactory;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import eventHandling.PanelEventTransferObject;
import eventHandling.Updatable;
import utilities.WinUtil;

public class StatusBar extends JMenuBar implements Updatable {

	private JButton startpageBtn, specialtyBtn, technicalTermBtn;
	private JLabel specialtyTierLbl, technicalTermTierLbl, freeSpace;

	private String specialtyDE, technicalTermDE;

	private ResourceBundle languageBundle;
	private Locale currentLocale;

	private final int VERY_LIGHT_GREY = 150;

	/**
	 * <li><b><i>ProjectStatusBar</i></b> <br>
	 * <br>
	 * public ProjectStatusBar(MainFrame mainFrame)<br>
	 * <br>
	 * Konstruktor für Statusbar<br>
	 * <br>
	 * 
	 * @param mainFrame
	 *            - Referenz auf MainFrame
	 */
	public StatusBar(ResourceBundle languageBundle) {
		
		this.languageBundle = languageBundle;
		initialize();
	}

	private void initialize() {
		
		// Sehr helles Grau umwandeln von RGB zu HSB
		Color VeryLightGrey = WinUtil.createColor(VERY_LIGHT_GREY, VERY_LIGHT_GREY, VERY_LIGHT_GREY);

		// Buttons
		startpageBtn = WinUtil.createButton(languageBundle.getString("startpageBtn"), 0, 0, 0, 0, new EmptyBorder(0, 5, 0, 5), Color.DARK_GRAY, null, null, null, false,
				false, Color.WHITE);
		startpageBtn.setHorizontalAlignment(SwingConstants.CENTER);
		specialtyBtn = WinUtil.createButton(specialtyDE, 0, 0, 0, 0, new EmptyBorder(0, 5, 0, 5), Color.DARK_GRAY, null, null, null, false, false, Color.WHITE);
		specialtyBtn.setHorizontalAlignment(SwingConstants.CENTER);
		specialtyBtn.setEnabled(false);
		specialtyBtn.setFocusable(false);
		technicalTermBtn = WinUtil.createButton(technicalTermDE, 0, 0, 0, 0, new EmptyBorder(0, 5, 0, 5), Color.DARK_GRAY, null, null, null, false, false, Color.WHITE);
		technicalTermBtn.setHorizontalAlignment(SwingConstants.CENTER);
		technicalTermBtn.setEnabled(false);
		technicalTermBtn.setFocusable(false);

		// Labels
		specialtyTierLbl = WinUtil.createLabel(" >> ", 0, 0, 0, 0, new EmptyBorder(0, 15, 0, 15), Color.DARK_GRAY, " >> ", " >> ", VeryLightGrey);
		specialtyTierLbl.setHorizontalAlignment(SwingConstants.CENTER);
		technicalTermTierLbl = WinUtil.createLabel(" >> ", 0, 0, 0, 0, new EmptyBorder(0, 15, 0, 15), Color.DARK_GRAY, " >> ", " >> ", VeryLightGrey);
		technicalTermTierLbl.setHorizontalAlignment(SwingConstants.CENTER);
		freeSpace = WinUtil.createLabel("", 0, 0, 0, 0, new EmptyBorder(0, 600, 0, 5), null, " >> ", " >> ", null);

		// Hinzufügen der Buttons und Labels
		this.add(freeSpace);
		this.add(startpageBtn);
		this.add(specialtyTierLbl);
		this.add(specialtyBtn);
		this.add(technicalTermTierLbl);
		this.add(technicalTermBtn);

		this.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		this.setBackground(Color.DARK_GRAY);

	}
	
	public void setSpecialtyPanelBtnActionListener(ActionListener l) {
		startpageBtn.addActionListener(l);
	}
	
	@Override
	public void updateFrame(PanelEventTransferObject e) {
		// TODO Auto-generated method stub

	}

}
