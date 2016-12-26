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
import model.Specialty;
import utilities.ExtendedListItem;
import utilities.ListItem;
import utilities.WinUtil;

public class SearchComboBox extends MyComboBox {

	private DefaultComboBoxModel<ListItem> searchComboBoxDefaultModel;
	private List<String> historyList;

	public SearchComboBox(List<String> historyList) {
		
		this.historyList = historyList;
		initialize();
	}

	private void initialize() {
		searchComboBoxDefaultModel = new DefaultComboBoxModel<ListItem>();
		this.setModel(searchComboBoxDefaultModel);
		this.setBounds(WinUtil.relW(20), WinUtil.relH(22), WinUtil.relW(260), WinUtil.relH(25));
		this.setEditable(true);
		this.getEditor().getEditorComponent().setBackground(Color.LIGHT_GRAY);
		this.getEditor().getEditorComponent().setForeground(WinUtil.ULTRA_DARK_GRAY);
		writeSearchWordsFromDbToHistory(historyList, 12);
		((JTextComponent) this.getEditor().getEditorComponent()).setText("");
	}
	
	@Override
	public void updatePanel(PanelEventTransferObject e) {
		
		this.setBounds(WinUtil.relW(20), WinUtil.relH(22), WinUtil.relW(260), WinUtil.relH(25));
		writeSearchWordsFromDbToHistory(e.getHistoryList(), 12);
		((JTextComponent) this.getEditor().getEditorComponent()).setText("");
	}

	private void writeSearchWordsFromDbToHistory(List<String> historyList, int fontResize) {
		
		int rows = historyList.size();
		searchComboBoxDefaultModel.removeAllElements();

		try {
			for (int row = 0; row < rows; row++) {
				searchComboBoxDefaultModel.addElement(new ListItem(row, new ExtendedListItem(historyList.get(row), fontResize)));
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
