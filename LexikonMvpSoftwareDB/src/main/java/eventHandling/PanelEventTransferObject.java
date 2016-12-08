package eventHandling;

import java.awt.Component;
import java.util.List;

public class PanelEventTransferObject {
	
	ChosenLanguage currentLanguage;
	boolean[] availableLetters;
	int mainframeWidth;
	int mainframeHeight;
	List<String> entries;
		
	public int getMainframeWidth() {
		return mainframeWidth;
	}

	public void setMainframeWidth(int mainframeWidth) {
		this.mainframeWidth = mainframeWidth;
	}

	public int getMainframeHeight() {
		return mainframeHeight;
	}

	public void setMainframeHeight(int mainframeHeight) {
		this.mainframeHeight = mainframeHeight;
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
	
	public List<String> getEntries() {
		return entries;
	}

	public void setEntries(List<String> entries) {
		this.entries = entries;
	}
	
	
}
