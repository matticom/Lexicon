package interactElements;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;
import java.util.Locale;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.text.JTextComponent;

import eventHandling.ComboBoxEventTransferObject;
import eventHandling.PanelEventTransferObject;
import inputChecker.SearchWordChecker;
import model.Specialty;
import utilities.ExtendedListItem;
import utilities.ListItem;
import utilities.WinUtil;

public class SearchComboBox extends MyComboBox {

	private DefaultComboBoxModel<ListItem> searchComboBoxDefaultModel;
	private List<String> historyList;
	private SearchWordChecker keyChecker;

	public SearchComboBox(List<String> historyList, SearchWordChecker keyChecker) {
		
		this.historyList = historyList;
		this.keyChecker = keyChecker;
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
		
		((JTextComponent) this.getEditor().getEditorComponent()).addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				((JTextComponent) SearchComboBox.this.getEditor().getEditorComponent()).selectAll();
			}
		});
		
		((JTextComponent) this.getEditor().getEditorComponent()).addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				keyChecker.keyTypedChecker(e);
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				keyChecker.keyPressedChecker(e);
			}
		});
		
		
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
				searchComboBoxDefaultModel.addElement(new ListItem(row, new ExtendedListItem(historyList.get(row), null, fontResize)));
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
