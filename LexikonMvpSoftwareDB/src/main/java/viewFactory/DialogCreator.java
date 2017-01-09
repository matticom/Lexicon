package viewFactory;

import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.WindowAdapter;
import java.util.List;
import java.util.ResourceBundle;

import dialogs.AssignmentDialog;
import dialogs.Dialog;
import dialogs.TechnicalTermContentDialog;
import dialogs.TechnicalTermCreationDialog;
import enums.DialogWindows;
import interactElements.ChooseSpecialtyComboBox;
import model.TechnicalTerm;

public class DialogCreator {
	
	public Dialog createWindow(DialogWindows dialogWindowType, ResourceBundle languageBundle, TechnicalTerm technicalTerm, WindowAdapter windowAdapter, ActionListener actionListener) {

		Dialog dialogWindow = windowFactory(dialogWindowType, languageBundle, null, null, null, null, technicalTerm, windowAdapter, actionListener, null);
		return dialogWindow;
	}

	public Dialog createWindow(DialogWindows dialogWindowType, ResourceBundle languageBundle, List<TechnicalTerm> technicalTermList, ChooseSpecialtyComboBox specialtyComboBox, 
			WindowAdapter windowAdapter, ActionListener actionListener, KeyAdapter keyAdapter) {

		Dialog dialogWindow = windowFactory(dialogWindowType, languageBundle, null, null, technicalTermList, specialtyComboBox, null, windowAdapter, actionListener, keyAdapter);
		return dialogWindow;
	}
		

	public Dialog createWindow(DialogWindows dialogWindowType, ResourceBundle languageBundle, ChooseSpecialtyComboBox germanSpecialtyComboBox,
			ChooseSpecialtyComboBox spanishSpecialtyComboBox, WindowAdapter windowAdapter, ActionListener actionListener, KeyAdapter keyAdapter) {

		Dialog dialogWindow = windowFactory(dialogWindowType, languageBundle, germanSpecialtyComboBox, spanishSpecialtyComboBox, null, null, null, windowAdapter, 
				actionListener, keyAdapter);
		return dialogWindow;
	}

	protected Dialog windowFactory(DialogWindows dialogWindowType, ResourceBundle languageBundle, ChooseSpecialtyComboBox germanSpecialtyComboBox,
			ChooseSpecialtyComboBox spanishSpecialtyComboBox, List<TechnicalTerm> technicalTermList, ChooseSpecialtyComboBox specialtyComboBox, 
			TechnicalTerm technicalTerm, WindowAdapter windowAdapter, ActionListener actionListener, KeyAdapter keyAdapter) {

		Dialog dialogWindow = null;

		if (dialogWindowType == DialogWindows.AssignTechnicalTermToSpecialtyWindow) {
			dialogWindow = new AssignmentDialog(languageBundle, technicalTermList, specialtyComboBox, dialogWindowType, windowAdapter, actionListener, keyAdapter);
		}
		if (dialogWindowType == DialogWindows.TechnicalTermContentWindow) {
			dialogWindow = new TechnicalTermContentDialog(languageBundle, technicalTerm, dialogWindowType, windowAdapter, actionListener);
		}
		if (dialogWindowType == DialogWindows.TechnicalTermCreationWindow) {
			dialogWindow = new TechnicalTermCreationDialog(languageBundle, germanSpecialtyComboBox, spanishSpecialtyComboBox, dialogWindowType, windowAdapter, actionListener, keyAdapter);
		}

		return dialogWindow;
	}
}
