package interactElements;

import javax.swing.JComboBox;

import eventHandling.Updatable;
import enums.ComboBoxes;

public abstract class ComboBox extends JComboBox implements Updatable {

	protected final int GERMAN = 1;
	protected final int SPANISH = 2;
	
	protected ComboBoxes comboBoxType;

		
	public ComboBox(ComboBoxes comboBoxType) {
		this.comboBoxType = comboBoxType;
	}

	public ComboBoxes getComboBoxType() {
		return comboBoxType;
	}
}
