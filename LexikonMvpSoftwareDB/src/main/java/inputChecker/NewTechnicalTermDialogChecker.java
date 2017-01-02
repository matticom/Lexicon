package inputChecker;

import javax.persistence.NoResultException;
import javax.swing.JOptionPane;

import businessOperations.TermBO;
import exceptions.TechnicalTermAlreadyExistsAsSpecialty;
import windows.SpecialtyTextFieldsCheckable;
import windows.TechnicalTermCreationWindow;

public class NewTechnicalTermDialogChecker extends AssignmentDialogChecker {

	@Override
	public void checkDialog(SpecialtyTextFieldsCheckable dialog, TermBO termBOTest) {

		TechnicalTermCreationWindow newTTDialog = (TechnicalTermCreationWindow)dialog;
		testPassed = true;
		if (newTTDialog.isNewSpecialtySelected()) {
			checkBlankSpecialtyFields(newTTDialog);
			checkBlankTechnicalTermFields(newTTDialog);
			checkSpecialties(newTTDialog, termBOTest);
			checkTechnicalTerms(newTTDialog, termBOTest);
		} else {
			checkTechnicalTerms(newTTDialog, termBOTest);
			checkBlankTechnicalTermFields(newTTDialog);
		}
	}

	
	private void checkTechnicalTerms(TechnicalTermCreationWindow newTTDialog, TermBO termBOTest) {
		try {
			termBOTest.selectTechnicalTermByName(newTTDialog.getGermanTextField().getText(), GERMAN);
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
			termBOTest.selectTechnicalTermByName(newTTDialog.getSpanishTextField().getText(), SPANISH);
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

	private void checkBlankTechnicalTermFields(TechnicalTermCreationWindow newTTDialog) {
		if (newTTDialog.getGermanTextField().getText().equals("") || newTTDialog.getSpanishTextField().getText().equals("")) {
			JOptionPane.showMessageDialog(null, "Die Begriffsfelder dürfen nicht leer bleiben", "Fehler", JOptionPane.ERROR_MESSAGE);
			testPassed = false;
		}
	}
}
