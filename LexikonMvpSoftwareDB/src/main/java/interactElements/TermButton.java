package interactElements;

import javax.swing.JButton;

public class TermButton extends JButton {

	private int termId;
	private int tranlationId;
	private int languageId;
	

	public TermButton(int termId, int tranlationId, int languageId, String specialtyName) {
		
		this.termId = termId;
		this.tranlationId = tranlationId;
		this.languageId = languageId;
		this.setText(specialtyName);
	}
	
	
	public int getTermId() {
		return termId;
	}
	
	public void setTermId(int termId) {
		this.termId = termId;
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
