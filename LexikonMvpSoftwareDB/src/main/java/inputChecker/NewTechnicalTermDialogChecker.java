package inputChecker;

import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.persistence.NoResultException;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import businessOperations.TermBO;
import exceptions.SpecialtyAlreadyExistsAsTechnicalTerm;
import exceptions.TechnicalTermAlreadyExistsAsSpecialty;
import model.Specialty;
import model.TechnicalTerm;
import windows.TechnicalTermCreationWindow;

public class NewTechnicalTermDialogChecker implements InputCheckable {

	private List<Specialty> specialtyList;
	private List<TechnicalTerm> technicalTermList;
	private boolean testPassed;

	public NewTechnicalTermDialogChecker(List<Specialty> specialtyList, List<TechnicalTerm> technicalTermList) {

		this.specialtyList = specialtyList;
		this.technicalTermList = technicalTermList;
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

	public void checkNewTechnicalTermDialog(TechnicalTermCreationWindow newTTDialog, TermBO termBOTest) {

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

	private void checkSpecialties(TechnicalTermCreationWindow newTTDialog, TermBO termBOTest) {

		try {
			termBOTest.selectSpecialtyByName(newTTDialog.getGermanSpecialtyInput().getText(), GERMAN);
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
			termBOTest.selectSpecialtyByName(newTTDialog.getSpanishSpecialtyInput().getText(), SPANISH);
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

	private void checkBlankSpecialtyFields(TechnicalTermCreationWindow newTTDialog) {
		if (newTTDialog.getGermanSpecialtyInput().getText().equals("") || newTTDialog.getSpanishSpecialtyInput().getText().equals("")) {
			JOptionPane.showMessageDialog(null, "Die Fachgebietsfelder dürfen nicht leer bleiben", "Fehler", JOptionPane.ERROR_MESSAGE);
			testPassed = false;
		}
	}

	private void checkBlankTechnicalTermFields(TechnicalTermCreationWindow newTTDialog) {
		if (newTTDialog.getGermanTextField().getText().equals("") || newTTDialog.getSpanishTextField().getText().equals("")) {
			JOptionPane.showMessageDialog(null, "Die Begriffsfelder dürfen nicht leer bleiben", "Fehler", JOptionPane.ERROR_MESSAGE);
			testPassed = false;
		}
	}

	public boolean isTestPassed() {
		return testPassed;
	}
}
