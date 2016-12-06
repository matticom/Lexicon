package interactElements;

import java.awt.Color;
import java.awt.event.FocusListener;
import java.awt.event.KeyListener;
import java.util.List;
import java.util.Locale;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.text.JTextComponent;

import eventHandling.ComboBoxEventTransferObject;
import utilities.ListItem;
import utilities.WinUtil;

public class SearchComboBox extends MyComboBox {

	private DefaultComboBoxModel<ListItem> searchComboBoxDefaultModel;

	private Locale currentLocale;

	public SearchComboBox() {

		initialize();
	}

	private void initialize() {
		searchComboBoxDefaultModel = new DefaultComboBoxModel<ListItem>();
		this.setModel(searchComboBoxDefaultModel);
		this.setBounds(40, 22, 260, 25);
		this.setEditable(true);
		this.getEditor().getEditorComponent().setBackground(Color.LIGHT_GRAY);
		this.getEditor().getEditorComponent().setForeground(WinUtil.ULTRA_DARK_GRAY);

		// ComboBox wird mit Suchhistory gefüllt

		((JTextComponent) this.getEditor().getEditorComponent()).setText("");
	}

	public void setSearchComboBoxKeyListener(KeyListener l) {
		this.getEditor().getEditorComponent().addKeyListener(l);
	}

	public void setSearchComboBoxFocusListener(FocusListener l) {
		this.getEditor().getEditorComponent().addFocusListener(l);
	}

	@Override
	public void refillComboBox(ComboBoxEventTransferObject e) {
		writeSearchWordsFromDbToHistory(e.getEntries());
	}

	private void writeSearchWordsFromDbToHistory(List<String> history) {
		// bei Programmstart wird Suchhistory aus der DB ausgelesen und in
		// Combobox eingefügt
		int rows = history.size();
		searchComboBoxDefaultModel.removeAllElements();

		try {
			for (int row = 0; row < rows; row++) {
				searchComboBoxDefaultModel.addElement(new ListItem(row, history.get(row)));
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "getHistoryStrings: " + ex.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
		}

	}

}
