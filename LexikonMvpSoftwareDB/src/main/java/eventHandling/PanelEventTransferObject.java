package eventHandling;

import java.util.List;

import enums.ChosenLanguage;
import model.Specialty;
import model.TechnicalTerm;
import panels.DynamicPanel;

public class PanelEventTransferObject {
		
	private int mainFrameWidth;
	private int mainFrameHeight;
	
	private List<String> historyList;
	private List<Specialty> specialtyList;
	private ChosenLanguage currentLanguage;
	private boolean[] availableLetters;
		
	private Specialty currentSpecialty;
	private TechnicalTerm currentTechnicalTerm;
		
	private DynamicPanel dynamicPanel;
	
	
	public PanelEventTransferObject(int mainFrameWidth, int mainFrameHeight, List<String> historyList, List<Specialty> specialtyList, ChosenLanguage currentLanguage,
			boolean[] availableLetters, Specialty currentSpecialty, TechnicalTerm currentTechnicalTerm, DynamicPanel dynamicPanel) {
		this.mainFrameWidth = mainFrameWidth;
		this.mainFrameHeight = mainFrameHeight;
		this.historyList = historyList;
		this.specialtyList = specialtyList;
		this.currentLanguage = currentLanguage;
		this.availableLetters = availableLetters;
		this.currentSpecialty = currentSpecialty;
		this.currentTechnicalTerm = currentTechnicalTerm;
		this.dynamicPanel = dynamicPanel;
	}

	public int getMainFrameWidth() {
		return mainFrameWidth;
	}

	public int getMainFrameHeight() {
		return mainFrameHeight;
	}

	public boolean[] getAvailableLetters() {
		return availableLetters;
	}

	public ChosenLanguage getCurrentLanguage() {
		return currentLanguage;
	}
	
	public List<String> getHistoryList() {
		return historyList;
	}

	public List<Specialty> getSpecialtyList() {
		return specialtyList;
	}

	public Specialty getCurrentSpecialty() {
		return currentSpecialty;
	}

	public TechnicalTerm getCurrentTechnicalTerm() {
		return currentTechnicalTerm;
	}

	public DynamicPanel getDynamicPanel() {
		return dynamicPanel;
	}
}
