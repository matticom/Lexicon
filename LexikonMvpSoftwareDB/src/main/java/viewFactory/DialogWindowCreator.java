package viewFactory;

import java.util.List;
import java.util.ResourceBundle;

import eventHandling.PanelEventTransferObject;
import eventHandling.DialogWindows;
import interactElements.ChooseSpecialtyComboBox;
import model.TechnicalTerm;
import windows.AssignTechnicalTermToSpecialtyWindow;
import windows.MyWindow;
import windows.TechnicalTermContentWindow;
import windows.TechnicalTermCreationWindow;

public class DialogWindowCreator {

	public MyWindow createWindow(DialogWindows window, ResourceBundle languageBundle) {
		
		MyWindow myWindow = windowFactory(window, languageBundle, null, null, null);
		return myWindow;
	}
	
	public MyWindow createWindow(DialogWindows window, ResourceBundle languageBundle, List<TechnicalTerm> technicalTermList) {
		
		MyWindow myWindow = windowFactory(window, languageBundle, null, null, technicalTermList);
		return myWindow;
	}
		
	public MyWindow createWindow(DialogWindows window, ResourceBundle languageBundle, ChooseSpecialtyComboBox germanSpecialtyComboBox, ChooseSpecialtyComboBox spanishSpecialtyComboBox) {
		
		MyWindow myWindow = windowFactory(window, languageBundle, germanSpecialtyComboBox, spanishSpecialtyComboBox, null);
		return myWindow;
	}
	
	protected MyWindow windowFactory(DialogWindows window, ResourceBundle languageBundle, ChooseSpecialtyComboBox germanSpecialtyComboBox, ChooseSpecialtyComboBox spanishSpecialtyComboBox, List<TechnicalTerm> technicalTermList) {
		
		MyWindow myWindow = null;
		
		if (window == DialogWindows.AssignTechnicalTermToSpecialtyWindow) {
			myWindow = new AssignTechnicalTermToSpecialtyWindow(languageBundle, technicalTermList);
		}
		if (window == DialogWindows.TechnicalTermContentWindow) {
			myWindow = new TechnicalTermContentWindow(languageBundle);
		}
		if (window == DialogWindows.TechnicalTermCreationWindow) {
			myWindow = new TechnicalTermCreationWindow(languageBundle, germanSpecialtyComboBox, spanishSpecialtyComboBox);
		}
		
		return myWindow;
	}
}
