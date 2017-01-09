package inputChecker;

import javax.persistence.NoResultException;
import javax.swing.JOptionPane;

import businessOperations.TermBO;
import businessOperations.TransactionBeginCommit;
import dialogs.SpecialtyTextFieldsCheckable;
import dialogs.TechnicalTermCreationDialog;
import exceptions.TechnicalTermAlreadyExistsAsSpecialty;

public class CreationDialogChecker extends AssignmentDialogChecker {

	@Override
	public void checkDialog(SpecialtyTextFieldsCheckable dialog, TransactionBeginCommit repositoryTA) {

		TechnicalTermCreationDialog newTTDialog = (TechnicalTermCreationDialog) dialog;
		testPassed = true;
		if (newTTDialog.isNewSpecialtySelected()) {
			checkBlankSpecialtyFields(newTTDialog);
			checkBlankTechnicalTermFields(newTTDialog);
			if (testPassed == true) {
				checkSpecialties(newTTDialog, repositoryTA);
				checkTechnicalTerms(newTTDialog, repositoryTA);
			}
		} else {
			checkBlankTechnicalTermFields(newTTDialog);
			if (testPassed == true) {
				checkTechnicalTerms(newTTDialog, repositoryTA);
			}
		}
	}

	private void checkTechnicalTerms(TechnicalTermCreationDialog newTTDialog, TransactionBeginCommit repositoryTA) {
		try {
			repositoryTA.selectTechnicalTermByName(newTTDialog.getGermanTextField().getText(), GERMAN);
			testPassed = false;
			JOptionPane.showMessageDialog(null, "Begriff bereits vorhanden", "Fehler", JOptionPane.ERROR_MESSAGE);
			return;
		} catch (NoResultException e) {
		} catch (TechnicalTermAlreadyExistsAsSpecialty e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
			testPassed = false;
			return;
		}

		try {
			repositoryTA.selectTechnicalTermByName(newTTDialog.getSpanishTextField().getText(), SPANISH);
			testPassed = false;
			JOptionPane.showMessageDialog(null, "Begriff bereits vorhanden", "Fehler", JOptionPane.ERROR_MESSAGE);
			return;
		} catch (NoResultException e) {
		} catch (TechnicalTermAlreadyExistsAsSpecialty e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
			testPassed = false;
			return;
		}
	}

	private void checkBlankTechnicalTermFields(TechnicalTermCreationDialog newTTDialog) {
		if (newTTDialog.getGermanTextField().getText().equals("") || newTTDialog.getSpanishTextField().getText().equals("")) {
			JOptionPane.showMessageDialog(null, "Die Begriffsfelder dürfen nicht leer bleiben", "Fehler", JOptionPane.ERROR_MESSAGE);
			testPassed = false;
		}
	}
}
