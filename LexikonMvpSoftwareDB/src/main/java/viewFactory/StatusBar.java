package viewFactory;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import eventHandling.ChosenLanguage;
import eventHandling.PanelEventTransferObject;
import eventHandling.Updatable;
import model.Translations;
import utilities.WinUtil;

public class StatusBar extends JPanel implements Updatable {

	private JButton startpageBtn, specialtyBtn, technicalTermBtn;
	private JLabel specialtyTierLbl, technicalTermTierLbl;

	private String specialtyDE, technicalTermDE;

	private ResourceBundle languageBundle;
	private JPanel p1, p2, p3, p4, p5, p6, p7;
	
	private int counter = 0;
	
	/**
	 * <li><b><i>StatusBar</i></b> <br>
	 * <br>
	 * public StatusBar(ResourceBundle languageBundle)<br>
	 * <br>
	 * Konstruktor für Statusbar<br>
	 * <br>
	 * 
	 * @param languageBundle
	 *            - Referenz auf ResourceBundle
	 */
	public StatusBar(ResourceBundle languageBundle) {
		
		this.languageBundle = languageBundle;
		this.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		this.setBackground(Color.DARK_GRAY);
		initialize();
	}

	private void initialize() {
				
		// Buttons
		startpageBtn = new JButton(languageBundle.getString("startpageBtn"));
		WinUtil.configStaticButton(startpageBtn, 0, 0, 0, 0, new EmptyBorder(0, 5, 0, 5), Color.WHITE, Color.DARK_GRAY);
		
		specialtyBtn = new JButton(specialtyDE);
		WinUtil.configStaticButton(specialtyBtn, 0, 0, 0, 0, new EmptyBorder(0, 5, 0, 5), Color.WHITE, Color.DARK_GRAY);
		specialtyBtn.setEnabled(false);
		specialtyBtn.setFocusable(false);
		
		technicalTermBtn = new JButton(technicalTermDE);
		WinUtil.configStaticButton(technicalTermBtn, 0, 0, 0, 0, new EmptyBorder(0, 5, 0, 5), Color.WHITE, Color.DARK_GRAY);
		technicalTermBtn.setEnabled(false);
		technicalTermBtn.setFocusable(false);
		
		specialtyTierLbl = new JLabel(" >> ");
		WinUtil.configStaticLabel(specialtyTierLbl, 0, 0, 0, 0, WinUtil.ULTRA_LIGHT_GRAY, Color.DARK_GRAY, 12, Font.PLAIN);
		technicalTermTierLbl = new JLabel(" >> ");
		WinUtil.configStaticLabel(technicalTermTierLbl, 0, 0, 0, 0, WinUtil.ULTRA_LIGHT_GRAY, Color.DARK_GRAY, 12, Font.PLAIN);
				
		p1 = new JPanel(new BorderLayout(), true);
		p1.setPreferredSize(new Dimension(200, 21));
		p1.setBackground(Color.DARK_GRAY);
		
		p2 = new JPanel(new BorderLayout(), true);
		p2.setPreferredSize(new Dimension(286, 21));
		p2.setBackground(Color.DARK_GRAY);
		
		p3 = new JPanel(new BorderLayout(), true);
		p3.setPreferredSize(new Dimension(21, 21));
		p3.setBackground(Color.DARK_GRAY);
		
		p4 = new JPanel(new BorderLayout(), true);
		p4.setPreferredSize(new Dimension(286, 21));
		p4.setBackground(Color.DARK_GRAY);
		
		p5 = new JPanel(new BorderLayout(), true);
		p5.setPreferredSize(new Dimension(21, 21));
		p5.setBackground(Color.DARK_GRAY);
		
		p6 = new JPanel(new BorderLayout(), true);
		p6.setPreferredSize(new Dimension(286, 21));
		p6.setBackground(Color.DARK_GRAY);
		
		p7 = new JPanel(new BorderLayout(), true);
		p7.setPreferredSize(new Dimension(200, 21));
		p7.setBackground(Color.DARK_GRAY);
		
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		p2.add(startpageBtn, BorderLayout.CENTER);
		p3.add(technicalTermTierLbl, BorderLayout.CENTER);
		p4.add(specialtyBtn, BorderLayout.CENTER);
		p5.add(specialtyTierLbl, BorderLayout.CENTER);
		p6.add(technicalTermBtn, BorderLayout.CENTER);
		
		this.add(p1);
		this.add(p2);
		this.add(p3);
		this.add(p4);
		this.add(p5);
		this.add(p6);
		this.add(p7);
	}
		
	@Override
	public void updatePanel(PanelEventTransferObject e) {
		
		int languageId = 0;
		
		clearPanels();
		
		if (e.getCurrentLanguage() == ChosenLanguage.German) {
			languageBundle = ResourceBundle.getBundle("languageBundles.lexikon", new Locale("de"));
			startpageBtn.setText(languageBundle.getString("startpageBtn"));
			languageId = 1;			
		} else {
			languageBundle = ResourceBundle.getBundle("languageBundles.lexikon", new Locale("es"));
			startpageBtn.setText(languageBundle.getString("startpageBtn"));
			languageId = 2;	
		}
		
		if (e.getCurrentSpecialty() != null) {
			for(Translations translation : e.getCurrentSpecialty().getTranslationList()) {
				if (translation.getLanguages().getId() == languageId) {
					specialtyBtn.setText(translation.getName());
				}
			}
		}
		
		if (e.getCurrentTechnicalTerm() != null) {
			for(Translations translation : e.getCurrentTechnicalTerm().getTranslationList()) {
				if (translation.getLanguages().getId() == languageId) {
					technicalTermBtn.setText(translation.getName());
				}
			}
		}
				
		int spacePanelWidth = (int)(e.getMainFrameWidth() * 0.390);
		int namePanelWidth = (int)(e.getMainFrameWidth() * 0.220);
		int separatorPanelWidth = (int)(e.getMainFrameWidth() * 0.019);
					
		int panelsHeight = (int) (e.getDisplaySize().getHeight() * 0.0175);
				
		if (e.getCurrentSpecialty() == null && e.getCurrentTechnicalTerm() == null) {
			// nur Startbutton
			spacePanelWidth = (int)(e.getMainFrameWidth() * 0.390);
			this.add(p1);
			this.add(p2);
			this.add(p7);
		}
				
		if (e.getCurrentSpecialty() != null && e.getCurrentTechnicalTerm() == null) {
			// Specialty Ebene
			spacePanelWidth = (int)(e.getMainFrameWidth() * 0.270);
			this.add(p1);
			this.add(p2);
			this.add(p3);
			this.add(p4);
			this.add(p7);
		}
		
		if (e.getCurrentSpecialty() != null && e.getCurrentTechnicalTerm() != null) {
			// alle Ebenen werden angezeigt
			spacePanelWidth = (int)(e.getMainFrameWidth() * 0.151);
			this.add(p1);
			this.add(p2);
			this.add(p3);
			this.add(p4);
			this.add(p5);
			this.add(p6);
			this.add(p7);
		}
		
		setPreferredSize(spacePanelWidth, namePanelWidth, separatorPanelWidth, panelsHeight);
	
		System.out.println("Statusbar counter: "+ counter);
		counter++;
	}
	
	private void clearPanels() {
		
		this.remove(p1);
		this.remove(p2);
		this.remove(p3);
		this.remove(p4);
		this.remove(p5);
		this.remove(p6);
		this.remove(p7);
	}
	
	private void setPreferredSize(int spacePanelWidth, int namePanelWidth, int separatorPanelWidth, int panelsHeight) {
		
		p1.setPreferredSize(new Dimension(spacePanelWidth, panelsHeight));
		p2.setPreferredSize(new Dimension(namePanelWidth, panelsHeight));
		p3.setPreferredSize(new Dimension(separatorPanelWidth, panelsHeight));
		p4.setPreferredSize(new Dimension(namePanelWidth, panelsHeight));
		p5.setPreferredSize(new Dimension(separatorPanelWidth, panelsHeight));
		p6.setPreferredSize(new Dimension(namePanelWidth, panelsHeight));
		p7.setPreferredSize(new Dimension(spacePanelWidth, panelsHeight));
	}
	
	public void setStatusBarStartpageButtonActionListener(ActionListener l) {
		startpageBtn.addActionListener(l);
	}

	public void setStatusBarSpecialtyButtonActionListener(ActionListener l) {
		specialtyBtn.addActionListener(l);
	}

	public void setStatusBarTechnicalTermButtonActionListener(ActionListener l) {
		technicalTermBtn.addActionListener(l);
	}

}
