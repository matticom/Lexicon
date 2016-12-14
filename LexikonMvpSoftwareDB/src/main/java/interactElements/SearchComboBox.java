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

		// ComboBox wird mit Suchhistory gef�llt

		((JTextComponent) this.getEditor().getEditorComponent()).setText("");
	}
	
	@Override
	public void updatePanel(PanelEventTransferObject e) {
		
		this.setBounds((int)(e.getDisplaySize().width*0.0104), (int) (e.getDisplaySize().height*0.0183), (int) (e.getDisplaySize().width*0.1354), (int) (e.getDisplaySize().height*0.0208));
//		this.setBounds((int)(0.031*headPanelWidth), (int)(0.22*headPanelHeight), (int)(0.2*headPanelWidth), (int)(0.25*headPanelHeight));
//		int fontResize = (int) (0.14 * headPanelHeight - 14);
//		this.getEditor().getEditorComponent().setFont(this.getEditor().getEditorComponent().getFont().deriveFont(Font.BOLD, 13 + fontResize));
		writeSearchWordsFromDbToHistory(e.getHistory(), 12);
		((JTextComponent) this.getEditor().getEditorComponent()).setText("");
	}

	private void writeSearchWordsFromDbToHistory(List<String> history, int fontResize) {
		// bei Programmstart wird Suchhistory aus der DB ausgelesen und in
		// Combobox eingef�gt
		int rows = history.size();
		searchComboBoxDefaultModel.removeAllElements();

		try {
			for (int row = 0; row < rows; row++) {
				searchComboBoxDefaultModel.addElement(new ListItem(row, new ExtendedListItem(history.get(row), 13 + fontResize)));
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
