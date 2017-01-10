package factories;

import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.WindowAdapter;
import java.util.List;
import java.util.ResourceBundle;

import dialogs.AssignmentDialog;
import dialogs.Dialog;
import dialogs.TechnicalTermContentDialog;
import dialogs.TechnicalTermCreationDialog;
import enums.Dialogs;
import interactElements.ChooseSpecialtyComboBox;
import model.TechnicalTerm;

public class DialogFactory {
	
	public Dialog createDialog(Dialogs dialogType, ResourceBundle languageBundle, TechnicalTerm technicalTerm, WindowAdapter windowAdapter, ActionListener actionListener) {

		Dialog dialogWindow = dialogFactory(dialogType, languageBundle, null, null, null, null, technicalTerm, windowAdapter, actionListener, null);
		return dialogWindow;
	}

	public Dialog createDialog(Dialogs dialogType, ResourceBundle languageBundle, List<TechnicalTerm> technicalTermList, ChooseSpecialtyComboBox specialtyComboBox, 
			WindowAdapter windowAdapter, ActionListener actionListener, KeyAdapter keyAdapter) {

		Dialog dialogWindow = dialogFactory(dialogType, languageBundle, null, null, technicalTermList, specialtyComboBox, null, windowAdapter, actionListener, keyAdapter);
		return dialogWindow;
	}
		

	public Dialog createDialog(Dialogs dialogType, ResourceBundle languageBundle, ChooseSpecialtyComboBox germanSpecialtyComboBox,
			ChooseSpecialtyComboBox spanishSpecialtyComboBox, WindowAdapter windowAdapter, ActionListener actionListener, KeyAdapter keyAdapter) {

		Dialog dialogWindow = dialogFactory(dialogType, languageBundle, germanSpecialtyComboBox, spanishSpecialtyComboBox, null, null, null, windowAdapter, 
				actionListener, keyAdapter);
		return dialogWindow;
	}

	protected Dialog dialogFactory(Dialogs dialogType, ResourceBundle languageBundle, ChooseSpecialtyComboBox germanSpecialtyComboBox,
			ChooseSpecialtyComboBox spanishSpecialtyComboBox, List<TechnicalTerm> technicalTermList, ChooseSpecialtyComboBox specialtyComboBox, 
			TechnicalTerm technicalTerm, WindowAdapter windowAdapter, ActionListener actionListener, KeyAdapter keyAdapter) {

		Dialog dialog = null;

		if (dialogType == Dialogs.ASSIGNMENT_DIALOG) {
			dialog = new AssignmentDialog(languageBundle, technicalTermList, specialtyComboBox, dialogType, windowAdapter, actionListener, keyAdapter);
		}
		if (dialogType == Dialogs.TECHNICAL_TERM_CONTENT_DIALOG) {
			dialog = new TechnicalTermContentDialog(languageBundle, technicalTerm, dialogType, windowAdapter, actionListener);
		}
		if (dialogType == Dialogs.TECHNICAL_TERM_CREATION_DIALOG) {
			dialog = new TechnicalTermCreationDialog(languageBundle, germanSpecialtyComboBox, spanishSpecialtyComboBox, dialogType, windowAdapter, actionListener, keyAdapter);
		}

		return dialog;
	}
}
