package interactElements;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.FocusListener;
import java.awt.event.KeyListener;
import java.util.List;
import java.util.Locale;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.text.JTextComponent;

import eventHandling.ComboBoxEventTransferObject;
import eventHandling.PanelEventTransferObject;
import utilities.ExtendedListItem;
import utilities.ListItem;
import utilities.WinUtil;

public class SearchComboBox extends MyComboBox {

	private DefaultComboBoxModel<ListItem> searchComboBoxDefaultModel;

	public SearchComboBox() {

		initialize();
	}

	private void initialize() {
		searchComboBoxDefaultModel = new DefaultComboBoxModel<ListItem>();
		this.setModel(searchComboBoxDefaultModel);
		this.setBounds(20, 22, 260, 25);
		this.setEditable(true);
		this.getEditor().getEditorComponent().setBackground(Color.LIGHT_GRAY);
		this.getEditor().getEditorComponent().setForeground(WinUtil.ULTRA_DARK_GRAY);

		// ComboBox wird mit Suchhistory gefüllt

		((JTextComponent) this.getEditor().getEditorComponent()).setText("");
	}
	
	@Override
	public void updatePanel(PanelEventTransferObject e) {
		
		this.setBounds((int)(e.getDisplaySize().width*0.0104), (int) (e.getDisplaySize().height*0.0183), (int) (e.getDisplaySize().width*0.1354), (int) (e.getDisplaySize().height*0.0208));
		writeSearchWordsFromDbToHistory(e.getHistory(), 12);
		((JTextComponent) this.getEditor().getEditorComponent()).setText("");
	}

	private void writeSearchWordsFromDbToHistory(List<String> history, int fontResize) {
		
		int rows = history.size();
		searchComboBoxDefaultModel.removeAllElements();

		try {
			for (int row = 0; row < rows; row++) {
				searchComboBoxDefaultModel.addElement(new ListItem(row, new ExtendedListItem(history.get(row), fontResize)));
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "getHistoryStrings: " + ex.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
		}

	}
	
	public void setSearchComboBoxKeyListener(KeyListener l) {
		this.getEditor().getEditorComponent().addKeyListener(l);
	}

	public void setSearchComboBoxFocusListener(FocusListener l) {
		this.getEditor().getEditorComponent().addFocusListener(l);
	}
}
