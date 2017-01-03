package inputChecker;

import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.persistence.NoResultException;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import businessOperations.TermBO;
import businessOperations.TransactionBeginCommit;
import exceptions.SpecialtyAlreadyExistsAsTechnicalTerm;
import exceptions.TechnicalTermAlreadyExistsAsSpecialty;
import model.Specialty;
import model.TechnicalTerm;
import windows.AssignTechnicalTermToSpecialtyWindow;
import windows.MyWindow;
import windows.TechnicalTermCreationWindow;
import windows.SpecialtyTextFieldsCheckable;

public class AssignmentDialogChecker implements InputCheckable {

	protected boolean testPassed;

	public AssignmentDialogChecker() {
	
		testPassed = false;
	}

	@Override
	public void keyPressedChecker(KeyEvent e) {

		if (e.getKeyCode() == KeyEvent.VK_ENTER && !((JTextField) (e.getSource())).equals("")) {
			KeyboardFocusManager.getCurrentKeyboardFocusManager().focusNextComponent();
		}
	}

	@Override
	public void keyTypedChecker(KeyEvent e) {

		if (Character.isISOControl(e.getKeyChar())) {// Ignoriert
														// Steuerkommandos
			return;
		}

		if (!Character.isAlphabetic(e.getKeyChar())) {
			Toolkit.getDefaultToolkit().beep();
			e.consume();
			return;
		}
	}

	public void checkDialog(SpecialtyTextFieldsCheckable dialog, TransactionBeginCommit repositoryTA) {
		
		testPassed = true;
		
		if (dialog.isNewSpecialtySelected()) {
			checkBlankSpecialtyFields(dialog);
			checkSpecialties(dialog, repositoryTA);
		}
		
		if (((AssignTechnicalTermToSpecialtyWindow)dialog).getTechnicalTermIds().length == 0) {
			JOptionPane.showMessageDialog(null, "Es wurde kein Element der Liste ausgewählt", "Fehler", JOptionPane.ERROR_MESSAGE);
			testPassed = false;
		}
	}

	protected void checkSpecialties(SpecialtyTextFieldsCheckable dialog, TransactionBeginCommit repositoryTA) {

		try {
			repositoryTA.selectSpecialtyByNameTA(dialog.getGermanSpecialtyInput().getText(), GERMAN);
			testPassed = false;
			JOptionPane.showMessageDialog(null, "Fachgebiet bereits vorhanden", "Fehler", JOptionPane.ERROR_MESSAGE);
			return;
		} catch (NoResultException e) {
		} catch (SpecialtyAlreadyExistsAsTechnicalTerm e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
			testPassed = false;
			return;
		}

		try {
			repositoryTA.selectSpecialtyByNameTA(dialog.getSpanishSpecialtyInput().getText(), SPANISH);
			testPassed = false;
			JOptionPane.showMessageDialog(null, "Fachgebiet bereits vorhanden", "Fehler", JOptionPane.ERROR_MESSAGE);
			return;
		} catch (NoResultException e) {
		} catch (SpecialtyAlreadyExistsAsTechnicalTerm e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
			testPassed = false;
			return;
		}
	}

	protected void checkBlankSpecialtyFields(SpecialtyTextFieldsCheckable dialog) {
		if (dialog.getGermanSpecialtyInput().getText().equals("") || dialog.getSpanishSpecialtyInput().getText().equals("")) {
			JOptionPane.showMessageDialog(null, "Die Fachgebietsfelder dürfen nicht leer bleiben", "Fehler", JOptionPane.ERROR_MESSAGE);
			testPassed = false;
		}
	}

	public boolean isTestPassed() {
		return testPassed;
	}
}
