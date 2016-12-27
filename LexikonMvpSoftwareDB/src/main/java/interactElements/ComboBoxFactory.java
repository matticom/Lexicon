package interactElements;

import java.util.List;
import java.util.ResourceBundle;

import model.Specialty;

public class ComboBoxFactory {

	private final int GERMAN = 1;
	private final int SPANISH = 2;
	
	public MyComboBox createComboBox(ComboBoxes comboBox, ResourceBundle languageBundle, List<Specialty> specialtyList) {
		
		MyComboBox myComboBox = comboBoxFactory(comboBox, languageBundle, specialtyList, null);

		return myComboBox;
	}
	
	public MyComboBox createComboBox(ComboBoxes comboBox, List<String> historyList) {
		
		MyComboBox myComboBox = comboBoxFactory(comboBox, null, null, historyList);

		return myComboBox;
	}
	
	protected MyComboBox comboBoxFactory(ComboBoxes comboBox, ResourceBundle languageBundle, List<Specialty> specialtyList, List<String> historyList) {
		
		MyComboBox myComboBox = null;
		
		if (comboBox == ComboBoxes.SearchComboBox) {
			myComboBox = new SearchComboBox(historyList);
		}
		if (comboBox == ComboBoxes.GermanSpecialtyComboBox) {
			myComboBox = new ChooseSpecialtyComboBox(languageBundle, specialtyList, GERMAN);
		}
		if (comboBox == ComboBoxes.SpanishSpecialtyComboBox) {
			myComboBox = new ChooseSpecialtyComboBox(languageBundle, specialtyList, SPANISH);
		}
		
		return myComboBox;
	}
}
