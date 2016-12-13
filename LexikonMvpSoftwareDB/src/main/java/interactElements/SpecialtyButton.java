package interactElements;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.border.Border;

public class SpecialtyButton extends JButton {

	private int specialtyId;
	private int tranlationId;
	private int languageId;
	
		
	public SpecialtyButton(int specialtyId, int tranlationId, int languageId, String specialtyName) {
		
		this.specialtyId = specialtyId;
		this.tranlationId = tranlationId;
		this.languageId = languageId;
		this.setText(specialtyName);
		
		
	}
	
	public void setImportantLookParameter(int x, int y, int width, int height, Border border, Color background, Color foreground, boolean setFocusPainted) {
		
		if (x != 0 && y != 0 && width != 0 && height != 0) {
			this.setBounds(x, y, width, height);
		}
		if (border != null) {
			this.setBorder(border);
		}
		if (background != null) {
			this.setBackground(background);
		}
		if (foreground != null) {
			this.setForeground(foreground);
		}
		this.setFocusPainted(setFocusPainted);
	}
	
	public int getSpecialtyId() {
		return specialtyId;
	}
	public void setSpecialtyId(int specialtyId) {
		this.specialtyId = specialtyId;
	}
	public int getTranlationId() {
		return tranlationId;
	}
	public void setTranlationId(int tranlationId) {
		this.tranlationId = tranlationId;
	}
	public int getLanguageId() {
		return languageId;
	}
	public void setLanguageId(int languageId) {
		this.languageId = languageId;
	}
	
	
}
