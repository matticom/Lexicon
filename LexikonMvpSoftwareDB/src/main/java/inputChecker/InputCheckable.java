package inputChecker;

import java.awt.Component;
import java.awt.event.KeyEvent;

public interface InputCheckable {
	
	public void keyPressedChecker(KeyEvent e, Component source);
	public void keyTypedChecker(KeyEvent e, Component source);
}
