package interactElements;

import javax.swing.JButton;

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
