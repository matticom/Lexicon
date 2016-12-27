package interactElements;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.text.JTextComponent;
import javax.swing.JTextField;

import eventHandling.PanelEventTransferObject;
import model.Specialty;
import model.Translations;
import utilities.ExtendedListItem;
import utilities.ListItem;
import utilities.WinUtil;
import windows.TechnicalTermCreationWindow;

public class ChooseSpecialtyComboBox extends MyComboBox {

	private DefaultComboBoxModel<ListItem> specialtyComboBoxDefaultModel;
	private List<Specialty> specialtyList;
	private ResourceBundle languageBundle;

	private final int GERMAN = 1;
	private final int SPANISH = 2;

	private int languageId;

	public ChooseSpecialtyComboBox(ResourceBundle languageBundle, List<Specialty> specialtyList, int languageId) {

		this.languageBundle = languageBundle;
		this.specialtyList = specialtyList;
		this.languageId = languageId;
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
		((JTextComponent) this.getEditor().getEditorComponent()).setText(languageBundle.getString("newSpecialty"));
		
		((JTextComponent) this.getEditor().getEditorComponent()).addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e)
			{
				((JTextComponent) ChooseSpecialtyComboBox.this.getEditor().getEditorComponent()).selectAll();
			}
		});
		
	}

	@Override
	public void updatePanel(PanelEventTransferObject e) {

		languageBundle = e.getCurrentLanguageBundle();
		fillComboBoxWithSpecialties(e.getSpecialtyList(), 12);
		((JTextComponent) this.getEditor().getEditorComponent()).setText(languageBundle.getString("newSpecialty"));
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
