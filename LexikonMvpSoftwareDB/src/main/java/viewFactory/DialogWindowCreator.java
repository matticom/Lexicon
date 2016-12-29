package viewFactory;

import java.util.List;
import java.util.ResourceBundle;

import AssignmentWindowComponents.AssignmentTableRowObject;
import eventHandling.PanelEventTransferObject;
import eventHandling.DialogWindows;
import interactElements.ChooseSpecialtyComboBox;
import model.TechnicalTerm;
import windows.AssignConfirmationWindow;
import windows.AssignTechnicalTermToSpecialtyWindow;
import windows.MyWindow;
import windows.TechnicalTermContentWindow;
import windows.TechnicalTermCreationWindow;

public class DialogWindowCreator {

	public MyWindow createWindow(DialogWindows window, ResourceBundle languageBundle) {

		MyWindow myWindow = windowFactory(window, languageBundle, null, null, null, null);
		return myWindow;
	}

	public MyWindow createWindow(DialogWindows window, ResourceBundle languageBundle, List<TechnicalTerm> technicalTermList) {

		MyWindow myWindow = windowFactory(window, languageBundle, null, null, technicalTermList, null);
		return myWindow;
	}
	
	public MyWindow createWindow(DialogWindows window, ResourceBundle languageBundle, AssignmentTableRowObject[] tableRowObjectArray) {

		MyWindow myWindow = windowFactory(window, languageBundle, null, null, null, tableRowObjectArray);
		return myWindow;
	}

	public MyWindow createWindow(DialogWindows window, ResourceBundle languageBundle, ChooseSpecialtyComboBox germanSpecialtyComboBox,
			ChooseSpecialtyComboBox spanishSpecialtyComboBox) {

		MyWindow myWindow = windowFactory(window, languageBundle, germanSpecialtyComboBox, spanishSpecialtyComboBox, null, null);
		return myWindow;
	}

	protected MyWindow windowFactory(DialogWindows window, ResourceBundle languageBundle, ChooseSpecialtyComboBox germanSpecialtyComboBox,
			ChooseSpecialtyComboBox spanishSpecialtyComboBox, List<TechnicalTerm> technicalTermList, AssignmentTableRowObject[] tableRowObjectArray) {

		MyWindow myWindow = null;

		if (window == DialogWindows.AssignTechnicalTermToSpecialtyWindow) {
			myWindow = new AssignTechnicalTermToSpecialtyWindow(languageBundle, technicalTermList);
		}
		if (window == DialogWindows.AssignConfirmationWindow) {
			myWindow = new AssignConfirmationWindow(languageBundle, tableRowObjectArray);
		}
		if (window == DialogWindows.TechnicalTermContentWindow) {
			myWindow = new TechnicalTermContentWindow(languageBundle);
		}
		if (window == DialogWindows.TechnicalTermCreationWindow) {
			myWindow = new TechnicalTermCreationWindow(languageBundle, germanSpecialtyComboBox, spanishSpecialtyComboBox);
		}

		return myWindow;
	}
}
