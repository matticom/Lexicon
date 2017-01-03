package interactElements;

import java.util.List;
import java.util.ResourceBundle;

import inputChecker.SearchWordChecker;
import model.Specialty;
import utilities.WinUtil;

public class ComboBoxFactory {

	private final int GERMAN = 1;
	private final int SPANISH = 2;
	
	public MyComboBox createComboBox(ComboBoxes comboBox, ResourceBundle languageBundle, List<Specialty> specialtyList) {
		
		MyComboBox myComboBox = comboBoxFactory(comboBox, languageBundle, specialtyList, null, null);

		return myComboBox;
	}
	
	public MyComboBox createComboBox(ComboBoxes comboBox, List<String> historyList, SearchWordChecker keyChecker) {
		
		MyComboBox myComboBox = comboBoxFactory(comboBox, null, null, historyList, keyChecker);

		return myComboBox;
	}
	
	protected MyComboBox comboBoxFactory(ComboBoxes comboBox, ResourceBundle languageBundle, List<Specialty> specialtyList, List<String> historyList, SearchWordChecker keyChecker) {
		
		MyComboBox myComboBox = null;
		
		if (comboBox == ComboBoxes.SearchComboBox) {
			myComboBox = new SearchComboBox(historyList, keyChecker);
		}
		if (comboBox == ComboBoxes.GermanSpecialtyComboBox) {
			myComboBox = new ChooseSpecialtyComboBox(languageBundle, specialtyList, GERMAN);
		}
		if (comboBox == ComboBoxes.SpanishSpecialtyComboBox) {
			myComboBox = new ChooseSpecialtyComboBox(languageBundle, specialtyList, SPANISH);
		}
		if (comboBox == ComboBoxes.SpecialtyComboBox) {
			myComboBox = new ChooseSpecialtyComboBox(languageBundle, specialtyList, 0);
		}
		
		return myComboBox;
	}
}
