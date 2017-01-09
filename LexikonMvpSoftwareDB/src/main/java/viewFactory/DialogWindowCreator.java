package viewFactory;

import java.util.List;
import java.util.ResourceBundle;

import enums.DialogWindows;
import interactElements.ChooseSpecialtyComboBox;
import model.TechnicalTerm;
import windows.AssignTechnicalTermToSpecialtyWindow;
import windows.DialogWindow;
import windows.TechnicalTermContentWindow;
import windows.TechnicalTermCreationWindow;

public class DialogWindowCreator {

	public DialogWindow createWindow(DialogWindows dialogWindowType, ResourceBundle languageBundle) {

		DialogWindow dialogWindow = windowFactory(dialogWindowType, languageBundle, null, null, null, null, null);
		return dialogWindow;
	}
	
	public DialogWindow createWindow(DialogWindows dialogWindowType, ResourceBundle languageBundle, TechnicalTerm technicalTerm) {

		DialogWindow dialogWindow = windowFactory(dialogWindowType, languageBundle, null, null, null, null, technicalTerm);
		return dialogWindow;
	}

	public DialogWindow createWindow(DialogWindows dialogWindowType, ResourceBundle languageBundle, List<TechnicalTerm> technicalTermList, ChooseSpecialtyComboBox specialtyComboBox) {

		DialogWindow dialogWindow = windowFactory(dialogWindowType, languageBundle, null, null, technicalTermList, specialtyComboBox, null);
		return dialogWindow;
	}
		

	public DialogWindow createWindow(DialogWindows dialogWindowType, ResourceBundle languageBundle, ChooseSpecialtyComboBox germanSpecialtyComboBox,
			ChooseSpecialtyComboBox spanishSpecialtyComboBox) {

		DialogWindow dialogWindow = windowFactory(dialogWindowType, languageBundle, germanSpecialtyComboBox, spanishSpecialtyComboBox, null, null, null);
		return dialogWindow;
	}

	protected DialogWindow windowFactory(DialogWindows dialogWindowType, ResourceBundle languageBundle, ChooseSpecialtyComboBox germanSpecialtyComboBox,
			ChooseSpecialtyComboBox spanishSpecialtyComboBox, List<TechnicalTerm> technicalTermList, ChooseSpecialtyComboBox specialtyComboBox, TechnicalTerm technicalTerm) {

		DialogWindow dialogWindow = null;

		if (dialogWindowType == DialogWindows.AssignTechnicalTermToSpecialtyWindow) {
			dialogWindow = new AssignTechnicalTermToSpecialtyWindow(languageBundle, technicalTermList, specialtyComboBox, dialogWindowType);
		}
		if (dialogWindowType == DialogWindows.TechnicalTermContentWindow) {
			dialogWindow = new TechnicalTermContentWindow(languageBundle, technicalTerm, dialogWindowType);
		}
		if (dialogWindowType == DialogWindows.TechnicalTermCreationWindow) {
			dialogWindow = new TechnicalTermCreationWindow(languageBundle, germanSpecialtyComboBox, spanishSpecialtyComboBox, dialogWindowType);
		}

		return dialogWindow;
	}
}
