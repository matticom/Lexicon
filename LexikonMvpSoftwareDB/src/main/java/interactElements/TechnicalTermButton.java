package interactElements;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.border.Border;

public class TechnicalTermButton extends JButton {

	private int technicalTermId;
	private int tranlationId;
	private int languageId;
	
	public TechnicalTermButton(int technicalTermId, int tranlationId, int languageId, String technicalTermName) {
		
		this.technicalTermId = technicalTermId;
		this.tranlationId = tranlationId;
		this.languageId = languageId;
		this.setText(technicalTermName);
	}
		
		
	public int getTechnicalTermId() {
		return technicalTermId;
	}

	public void setTechnicalTermId(int technicalTermId) {
		this.technicalTermId = technicalTermId;
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
