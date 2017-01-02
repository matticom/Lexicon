package viewFactory;

import java.util.List;
import java.util.ResourceBundle;

import eventHandling.DialogWindows;
import interactElements.ChooseSpecialtyComboBox;
import model.TechnicalTerm;
import windows.AssignTechnicalTermToSpecialtyWindow;
import windows.MyWindow;
import windows.TechnicalTermContentWindow;
import windows.TechnicalTermCreationWindow;

public class DialogWindowCreator {

	public MyWindow createWindow(DialogWindows window, ResourceBundle languageBundle) {

		MyWindow myWindow = windowFactory(window, languageBundle, null, null, null, null, null);
		return myWindow;
	}
	
	public MyWindow createWindow(DialogWindows window, ResourceBundle languageBundle, TechnicalTerm technicalTerm) {

		MyWindow myWindow = windowFactory(window, languageBundle, null, null, null, null, technicalTerm);
		return myWindow;
	}

	public MyWindow createWindow(DialogWindows window, ResourceBundle languageBundle, List<TechnicalTerm> technicalTermList, ChooseSpecialtyComboBox specialtyComboBox) {

		MyWindow myWindow = windowFactory(window, languageBundle, null, null, technicalTermList, specialtyComboBox, null);
		return myWindow;
	}
		

	public MyWindow createWindow(DialogWindows window, ResourceBundle languageBundle, ChooseSpecialtyComboBox germanSpecialtyComboBox,
			ChooseSpecialtyComboBox spanishSpecialtyComboBox) {

		MyWindow myWindow = windowFactory(window, languageBundle, germanSpecialtyComboBox, spanishSpecialtyComboBox, null, null, null);
		return myWindow;
	}

	protected MyWindow windowFactory(DialogWindows window, ResourceBundle languageBundle, ChooseSpecialtyComboBox germanSpecialtyComboBox,
			ChooseSpecialtyComboBox spanishSpecialtyComboBox, List<TechnicalTerm> technicalTermList, ChooseSpecialtyComboBox specialtyComboBox, TechnicalTerm technicalTerm) {

		MyWindow myWindow = null;

		if (window == DialogWindows.AssignTechnicalTermToSpecialtyWindow) {
			myWindow = new AssignTechnicalTermToSpecialtyWindow(languageBundle, technicalTermList, specialtyComboBox);
		}
		if (window == DialogWindows.TechnicalTermContentWindow) {
			myWindow = new TechnicalTermContentWindow(languageBundle, technicalTerm);
		}
		if (window == DialogWindows.TechnicalTermCreationWindow) {
			myWindow = new TechnicalTermCreationWindow(languageBundle, germanSpecialtyComboBox, spanishSpecialtyComboBox);
		}

		return myWindow;
	}
}
