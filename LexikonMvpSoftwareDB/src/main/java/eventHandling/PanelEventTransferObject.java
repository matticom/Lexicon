package eventHandling;

import java.awt.Dimension;
import java.util.List;

import javax.swing.JPanel;

import model.Specialty;
import model.TechnicalTerm;
import panels.TermPanelDynamic;
import panels.MyPanel;

public class PanelEventTransferObject {
	
	MyPanel source;
	MyPanel destination;
		
	int mainFrameWidth;
	int mainFrameHeight;
	Dimension displaySize;
	
	List<String> history;
	ChosenLanguage currentLanguage;
	boolean[] availableLetters;
	
	Specialty currentSpecialty;
	TechnicalTerm currentTechnicalTerm;
	
	JPanel dynamicPanel;
	

	
	public MyPanel getSource() {
		return source;
	}

	public void setSource(MyPanel source) {
		this.source = source;
	}

	public MyPanel getDestination() {
		return destination;
	}

	public void setDestination(MyPanel destination) {
		this.destination = destination;
	}

	public int getMainFrameWidth() {
		return mainFrameWidth;
	}

	public void setMainFrameWidth(int mainframeWidth) {
		this.mainFrameWidth = mainframeWidth;
	}

	public int getMainFrameHeight() {
		return mainFrameHeight;
	}

	public void setMainFrameHeight(int mainframeHeight) {
		this.mainFrameHeight = mainframeHeight;
	}

	public Dimension getDisplaySize() {
		return displaySize;
	}

	public void setDisplaySize(Dimension displaySize) {
		this.displaySize = displaySize;
	}

	public boolean[] getAvailableLetters() {
		return availableLetters;
	}

	public void setAvailableLetters(boolean[] availableLetters) {
		this.availableLetters = availableLetters;
	}

	public ChosenLanguage getCurrentLanguage() {
		return currentLanguage;
	}

	public void setCurrentLanguage(ChosenLanguage currentLanguage) {
		this.currentLanguage = currentLanguage;
	}

	public List<String> getHistory() {
		return history;
	}

	public void setHistory(List<String> history) {
		this.history = history;
	}

	public Specialty getCurrentSpecialty() {
		return currentSpecialty;
	}

	public void setCurrentSpecialty(Specialty currentSpecialty) {
		this.currentSpecialty = currentSpecialty;
	}

	public TechnicalTerm getCurrentTechnicalTerm() {
		return currentTechnicalTerm;
	}

	public void setCurrentTechnicalTerm(TechnicalTerm currentTechnicalTerm) {
		this.currentTechnicalTerm = currentTechnicalTerm;
	}

	public JPanel getDynamicPanel() {
		return dynamicPanel;
	}

	public void setDynamicPanel(JPanel dynamicPanel) {
		this.dynamicPanel = dynamicPanel;
	}

	
	

		
}
