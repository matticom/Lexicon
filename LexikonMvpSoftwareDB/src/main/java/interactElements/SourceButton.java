package interactElements;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;

public class SourceButton extends JButton{
	
	private JDialog currentDialog;

	
	public SourceButton(String text, JDialog dialog, ActionListener actionListener) {
		super(text);
		currentDialog = dialog;
		addActionListener(actionListener);
	}

	public JDialog getCurrentDialog() {
		return currentDialog;
	}

	public void setCurrentDialog(JDialog currentDialog) {
		this.currentDialog = currentDialog;
	}
}
