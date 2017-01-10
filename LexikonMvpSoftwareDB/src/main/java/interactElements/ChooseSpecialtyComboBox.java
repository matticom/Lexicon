package interactElements;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.FocusListener;
import java.awt.event.KeyListener;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.text.JTextComponent;

import enums.Language;
import enums.ComboBoxes;

import eventHandling.PanelEventTransferObject;
import model.Specialty;
import model.Translations;
import utilities.ExtendedListItem;
import utilities.ListItem;
import utilities.WinUtil;

public class ChooseSpecialtyComboBox extends ComboBox {

	private DefaultComboBoxModel<ListItem> specialtyComboBoxDefaultModel;
	private List<Specialty> specialtyList;

	private final int LANGUAGE_UNSIGNED = 0;
	private boolean isAssignComboBox;
	private int languageId;

	public ChooseSpecialtyComboBox(ResourceBundle languageBundle, List<Specialty> specialtyList, int languageId, ComboBoxes comboBoxType) {

		super(comboBoxType);
		this.specialtyList = specialtyList;

		if (languageId == LANGUAGE_UNSIGNED) {
			isAssignComboBox = true;
			this.languageId = WinUtil.getLanguageId(languageBundle);
		} else {
			isAssignComboBox = false;
			this.languageId = languageId;
		}

		initialize();
	}

	private void initialize() {
		specialtyComboBoxDefaultModel = new DefaultComboBoxModel<ListItem>();
		this.setModel(specialtyComboBoxDefaultModel);
		this.setPreferredSize(new Dimension(WinUtil.relW(350), WinUtil.relH(30)));
		this.setEditable(true);
		this.getEditor().getEditorComponent().setBackground(WinUtil.DARK_WHITE);
		this.getEditor().getEditorComponent().setForeground(WinUtil.ULTRA_DARK_GRAY);
		((JTextComponent) this.getEditor().getEditorComponent()).setMargin(new Insets(0, 13, 0, 0));
		fillComboBoxWithSpecialties(specialtyList, 12);
		((JTextComponent) this.getEditor().getEditorComponent()).setEditable(false);

	}

	@Override
	public void updatePanel(PanelEventTransferObject e) {

		if (isAssignComboBox) {
			if (e.getCurrentLanguage() == Language.GERMAN) {
				languageId = GERMAN;
			} else {
				languageId = SPANISH;
			}
		}
		
		fillComboBoxWithSpecialties(e.getSpecialtyList(), 12);
	}

	private void fillComboBoxWithSpecialties(List<Specialty> specialtyList, int fontResize) {

		int rows = specialtyList.size();
		specialtyComboBoxDefaultModel.removeAllElements();
		Specialty specialty;

		try {
			for (int row = 0; row < rows; row++) {
				specialty = specialtyList.get(row);
				String firstLanguage = "";
				String secondLanguage = "";
				if (languageId == GERMAN) {
					firstLanguage = selectTermName(specialty, GERMAN);
					secondLanguage = selectTermName(specialty, SPANISH);
				}
				if (languageId == SPANISH) {
					firstLanguage = selectTermName(specialty, SPANISH);
					secondLanguage = selectTermName(specialty, GERMAN);
				}
				specialtyComboBoxDefaultModel.addElement(new ListItem(specialty.getId(), new ExtendedListItem(firstLanguage, secondLanguage, fontResize)));
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "fillComboBoxWithSpecialties: " + ex.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
		}

	}

	private String selectTermName(Specialty specialty, int languageId) {

		String name = "";

		for (Translations translation : specialty.getTranslationList()) {
			if (translation.getLanguages().getId() == languageId) {
				name = translation.getName();
			}
		}
		return name;
	}

	public void setSpecialtyComboBoxKeyListener(KeyListener l) {
		this.getEditor().getEditorComponent().addKeyListener(l);
	}

	public void setSpecialtyComboBoxFocusListener(FocusListener l) {
		this.getEditor().getEditorComponent().addFocusListener(l);
	}

	public ListItem getSelectedListItem() {
		return (ListItem) specialtyComboBoxDefaultModel.getSelectedItem();
	}
}
