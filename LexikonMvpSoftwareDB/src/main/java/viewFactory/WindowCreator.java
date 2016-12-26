package viewFactory;

import java.util.ResourceBundle;

import eventHandling.PanelEventTransferObject;
import eventHandling.WindowUpdateObjects;
import interactElements.ChooseSpecialtyComboBox;
import windows.AssignTechnicalTermToSpecialtyWindow;
import windows.MyWindow;
import windows.TechnicalTermContentWindow;
import windows.TechnicalTermCreationWindow;

public class WindowCreator {

	public MyWindow createWindow(WindowUpdateObjects window, ResourceBundle languageBundle) {
		
		MyWindow myWindow = windowFactory(window, languageBundle, null, null);
		return myWindow;
	}
		
	public MyWindow createWindow(WindowUpdateObjects window, ResourceBundle languageBundle, ChooseSpecialtyComboBox germanSpecialtyComboBox, ChooseSpecialtyComboBox spanishSpecialtyComboBox) {
		
		MyWindow myWindow = windowFactory(window, languageBundle, germanSpecialtyComboBox, spanishSpecialtyComboBox);
		return myWindow;
	}
	
	protected MyWindow windowFactory(WindowUpdateObjects window, ResourceBundle languageBundle, ChooseSpecialtyComboBox germanSpecialtyComboBox, ChooseSpecialtyComboBox spanishSpecialtyComboBox) {
		
		MyWindow myWindow = null;
		
		if (window == WindowUpdateObjects.AssignTechnicalTermToSpecialtyWindow) {
			myWindow = new AssignTechnicalTermToSpecialtyWindow(languageBundle);
		}
		if (window == WindowUpdateObjects.TechnicalTermContentWindow) {
			myWindow = new TechnicalTermContentWindow(languageBundle);
		}
		if (window == WindowUpdateObjects.TechnicalTermCreationWindow) {
			myWindow = new TechnicalTermCreationWindow(languageBundle, germanSpecialtyComboBox, spanishSpecialtyComboBox);
		}
		
		return myWindow;
	}
}
