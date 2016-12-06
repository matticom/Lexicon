package interactElements;

import eventHandling.ComboBoxEventTransferObject;

public class ComboBoxFactory {

	public MyComboBox createComboBox(ComboBoxes comboBox) {
		
		MyComboBox myComboBox = comboBoxFactory(comboBox);
		return myComboBox;
	}
	
	protected MyComboBox comboBoxFactory(ComboBoxes comboBox) {
		
		MyComboBox myComboBox = null;
		
		if (comboBox == ComboBoxes.SearchComboBox) {
			myComboBox = new SearchComboBox();
		}
		if (comboBox == ComboBoxes.ChooseSpecialtyComboBox) {
			myComboBox = new ChooseSpecialtyComboBox();
		}
		
		return myComboBox;
	}
}
