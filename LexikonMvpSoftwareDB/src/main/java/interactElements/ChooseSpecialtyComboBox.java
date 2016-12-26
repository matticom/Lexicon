package interactElements;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.FocusListener;
import java.awt.event.KeyListener;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.text.JTextComponent;

import eventHandling.PanelEventTransferObject;
import model.Specialty;
import model.Translations;
import utilities.ExtendedListItem;
import utilities.ListItem;
import utilities.WinUtil;

public class ChooseSpecialtyComboBox extends MyComboBox {

	private DefaultComboBoxModel<ListItem> specialtyComboBoxDefaultModel;
	private List<Specialty> specialtyList;
	private ResourceBundle languageBundle;

	private final int GERMAN = 1;
	private final int SPANISH = 2;

	private int languageId;

	public ChooseSpecialtyComboBox(ResourceBundle languageBundle, List<Specialty> specialtyList) {

		this.languageBundle = languageBundle;
		this.specialtyList = specialtyList;
		initialize();
	}

	private void initialize() {
		chooseLanguage();
		specialtyComboBoxDefaultModel = new DefaultComboBoxModel<ListItem>();
		this.setModel(specialtyComboBoxDefaultModel);
		this.setPreferredSize(new Dimension(WinUtil.relW(350), WinUtil.relH(25)));
		this.setEditable(true);
		this.getEditor().getEditorComponent().setBackground(Color.LIGHT_GRAY);
		this.getEditor().getEditorComponent().setForeground(WinUtil.ULTRA_DARK_GRAY);
		fillComboBoxWithSpecialties(specialtyList, 12);
		((JTextComponent) this.getEditor().getEditorComponent()).setText("");
	}

	private void chooseLanguage() {

		if (languageBundle.getLocale().equals("de")) {
			languageId = GERMAN;
		} else {
			languageId = SPANISH;
		}
	}

	@Override
	public void updatePanel(PanelEventTransferObject e) {

		languageBundle = e.getCurrentLanguageBundle();
		chooseLanguage();
		fillComboBoxWithSpecialties(e.getSpecialtyList(), 12);
		((JTextComponent) this.getEditor().getEditorComponent()).setText("");
	}

	private void fillComboBoxWithSpecialties(List<Specialty> specialtyList, int fontResize) {

		int rows = specialtyList.size();
		specialtyComboBoxDefaultModel.removeAllElements();
		Specialty specialty;

		try {
			for (int row = 0; row < rows; row++) {
				specialty = specialtyList.get(row);
				for (Translations translation : specialty.getTranslationList()) {
					if (translation.getLanguages().getId() == languageId) {
						specialtyComboBoxDefaultModel.addElement(new ListItem(row, new ExtendedListItem(translation.getName(), fontResize)));
					}
				}
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "fillComboBoxWithSpecialties: " + ex.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
		}

	}

	public void setSpecialtyComboBoxKeyListener(KeyListener l) {
		this.getEditor().getEditorComponent().addKeyListener(l);
	}

	public void setSpecialtyComboBoxFocusListener(FocusListener l) {
		this.getEditor().getEditorComponent().addFocusListener(l);
	}
}
