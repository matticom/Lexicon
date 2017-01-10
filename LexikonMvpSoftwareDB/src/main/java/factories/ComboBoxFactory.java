package factories;

import java.util.List;
import java.util.ResourceBundle;

import enums.ComboBoxes;
import inputChecker.SearchWordChecker;
import interactElements.ChooseSpecialtyComboBox;
import interactElements.ComboBox;
import interactElements.SearchComboBox;
import model.Specialty;
import utilities.WinUtil;

public class ComboBoxFactory {

	private final int GERMAN = 1;
	private final int SPANISH = 2;
	
	public ComboBox createComboBox(ComboBoxes comboBoxType, ResourceBundle languageBundle, List<Specialty> specialtyList) {
		
		ComboBox comboBox = comboBoxFactory(comboBoxType, languageBundle, specialtyList, null, null);

		return comboBox;
	}
	
	public ComboBox createComboBox(ComboBoxes comboBoxType, List<String> historyList, SearchWordChecker keyChecker) {
		
		ComboBox comboBox = comboBoxFactory(comboBoxType, null, null, historyList, keyChecker);

		return comboBox;
	}
	
	protected ComboBox comboBoxFactory(ComboBoxes comboBoxType, ResourceBundle languageBundle, List<Specialty> specialtyList, List<String> historyList, SearchWordChecker keyChecker) {
		
		ComboBox comboBox = null;
		
		if (comboBoxType == ComboBoxes.SEARCH_COMBOBOX) {
			comboBox = new SearchComboBox(historyList, keyChecker, comboBoxType);
		}
		if (comboBoxType == ComboBoxes.GERMAN_SPECIALTY_COMBOBOX) {
			comboBox = new ChooseSpecialtyComboBox(languageBundle, specialtyList, GERMAN, comboBoxType);
		}
		if (comboBoxType == ComboBoxes.SPANISH_SPECIALTY_COMBOBOX) {
			comboBox = new ChooseSpecialtyComboBox(languageBundle, specialtyList, SPANISH, comboBoxType);
		}
		if (comboBoxType == ComboBoxes.SPECIALTY_COMBOBOX) {
			comboBox = new ChooseSpecialtyComboBox(languageBundle, specialtyList, 0, comboBoxType);
		}
		
		return comboBox;
	}
}
