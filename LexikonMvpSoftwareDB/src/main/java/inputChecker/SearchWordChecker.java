package inputChecker;

import java.awt.Component;
import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;

import javax.swing.text.JTextComponent;

public class SearchWordChecker implements InputCheckable {

	@Override
	public void keyPressedChecker(KeyEvent e) {

		if (e.getKeyCode() == KeyEvent.VK_ENTER && !((JTextComponent) e.getSource()).getText().equals(""))
			KeyboardFocusManager.getCurrentKeyboardFocusManager().focusNextComponent();
		// %s% delete vor s verbieten
		if (e.getKeyCode() == KeyEvent.VK_DELETE && ((JTextComponent) e.getSource()).getText().length() == 3
				&& ((JTextComponent) e.getSource()).getText().substring(0, 1).equals("%")
				&& ((JTextComponent) e.getSource()).getText().substring(2, 3).equals("%") && ((JTextComponent) e.getSource()).getCaretPosition() == 1)
			e.consume();
		// %s% backspace nach s verbieten
		if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE && ((JTextComponent) e.getSource()).getText().length() == 3
				&& ((JTextComponent) e.getSource()).getText().substring(0, 1).equals("%")
				&& ((JTextComponent) e.getSource()).getText().substring(2, 3).equals("%") && ((JTextComponent) e.getSource()).getCaretPosition() == 2)
			e.consume();
	}

	@Override
	public void keyTypedChecker(KeyEvent e) {

		if (Character.isISOControl(e.getKeyChar())) // Ignoriert Steuerkommandos
			return;

		if (!isCharacterAllowed(e.getKeyChar(), (JTextComponent) e.getSource())) {
			Toolkit.getDefaultToolkit().beep();
			e.consume();
			return;
		}

		if (e.getKeyChar() == '%')
			if (!isPositionAllowed((JTextComponent) e.getSource()))
				e.consume();

	}

	private boolean isCharacterAllowed(char c, JTextComponent tf) {
		// Prüfung der Eingabe in der Suchleiste (wildcard dürfen immer nur am
		// Anfang oder Ende eines Wortes stehen)
		// wenn versucht wird ein Buchstabe davor/dahinter einzufügen wird es
		// abgelehnt
		// nur Buchstaben dürfen eingefügt werden

		boolean retValue = false;
		int textLength = tf.getText().length();

		if (textLength > 1) {
			String textStart = tf.getText().substring(0, 1);
			String textEnd = tf.getText().substring(textLength - 1, textLength);
			int caretPosition = tf.getCaretPosition();
			String wildcard = "%";

			if (textStart.equals("%") && caretPosition == 0)
				return false;
			if (textEnd.equals("%") && caretPosition == textLength)
				return false;
		}

		// nur Buchstaben und * ist erlaubt

		if (Character.isAlphabetic(c))
			return true;
		else if (c == '%')
			retValue = true;

		return retValue;

	}

	private boolean isPositionAllowed(JTextComponent tf) {
		// Prüfung, ob Position für das Einfügen der Wildcard (%) okay ist
		// (wildcard dürfen immer nur am Anfang oder Ende eines Wortes stehen)

		int textLength = tf.getText().length();

		// Leere Feld
		if (textLength == 0)
			return true;

		String textStart = tf.getText().substring(0, 1);
		String textEnd = tf.getText().substring(textLength - 1, textLength);
		int caretPosition = tf.getCaretPosition();
		String wildcard = "%";

		// Nur eine Wildcard als einziges Zeichen
		if (textLength == 1 && tf.getText().contains("%"))
			return false;

		// ist Wildcard schon vorhanden?
		if (textStart.equals(wildcard) && textEnd.equals(wildcard))
			return false;
		if (textStart.equals(wildcard) && caretPosition == 0)
			return false;
		if (textEnd.equals(wildcard) && caretPosition == textLength)
			return false;

		// Wildcard darf nur am Anfang oder Ende hinzugefügt werden
		if (caretPosition == 0 || caretPosition == textLength)
			return true;

		return false;
	}

}
