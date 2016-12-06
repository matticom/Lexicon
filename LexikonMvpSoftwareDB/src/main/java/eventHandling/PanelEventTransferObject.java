package eventHandling;

public class PanelEventTransferObject {
	
	ChosenLanguage currentLanguage;
	boolean[] availableLetters;

	
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
	
	
	
	
}
