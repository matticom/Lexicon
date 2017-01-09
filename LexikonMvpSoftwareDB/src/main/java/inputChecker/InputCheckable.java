package inputChecker;

import java.awt.event.KeyEvent;

public interface InputCheckable {
	
	public final int GERMAN = 1;
	public final int SPANISH = 2;
	
	public void keyPressedChecker(KeyEvent e);
	public void keyTypedChecker(KeyEvent e);
}
