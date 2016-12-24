package interactElements;

import javax.swing.JButton;

public class TermButton extends JButton {

	private int termId;
	private int tranlationId;
	private int languageId;
	

	public TermButton(int specialtyId, int tranlationId, int languageId, String specialtyName) {
		
		this.termId = specialtyId;
		this.tranlationId = tranlationId;
		this.languageId = languageId;
		this.setText(specialtyName);
	}
	
	
	public int getSpecialtyId() {
		return termId;
	}
	public void setSpecialtyId(int specialtyId) {
		this.termId = specialtyId;
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
