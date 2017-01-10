package interactElements;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.ListCellRenderer;
import javax.swing.text.JTextComponent;

import org.apache.derby.tools.sysinfo;

import javax.swing.JTextField;

import enums.ComboBoxes;
import eventHandling.PanelEventTransferObject;
import inputChecker.SearchWordChecker;
import utilities.ExtendedListItem;
import utilities.ListItem;
import utilities.WinUtil;

public class SearchComboBox extends ComboBox {

	private DefaultComboBoxModel<ListItem> searchComboBoxDefaultModel;
	private List<String> historyList;
	private SearchWordChecker keyChecker;

	public SearchComboBox(List<String> historyList, SearchWordChecker keyChecker, ComboBoxes comboBoxType) {
		
		super(comboBoxType);
		this.historyList = historyList;
		this.keyChecker = keyChecker;
		initialize();
	}

	private void initialize() {
		
		ListCellRenderer<Object> fontSizeRenderer = new ComboBoxCellRenderer();
		setRenderer(fontSizeRenderer);
		searchComboBoxDefaultModel = new DefaultComboBoxModel<ListItem>();
		setModel(searchComboBoxDefaultModel);
		setBounds(WinUtil.relW(20), WinUtil.relH(22), WinUtil.relW(260), WinUtil.relH(25));
		setEditable(true);
		getEditor().getEditorComponent().setBackground(Color.LIGHT_GRAY);
		getEditor().getEditorComponent().setForeground(WinUtil.ULTRA_DARK_GRAY);
		writeSearchWordsFromDbToHistory(historyList, 12);
		((JTextComponent) getEditor().getEditorComponent()).setText("");
		
		((JTextComponent) getEditor().getEditorComponent()).addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				((JTextComponent) SearchComboBox.this.getEditor().getEditorComponent()).selectAll();
			}
		});
		
		((JTextComponent) getEditor().getEditorComponent()).addKeyListener(new KeyAdapter() {
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
		
		setBounds(WinUtil.relW(20), WinUtil.relH(22), WinUtil.relW(260), WinUtil.relH(25));
		writeSearchWordsFromDbToHistory(e.getHistoryList(), 12);
		((JTextComponent) getEditor().getEditorComponent()).setText("");
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
	
	public void setSearchComboBoxActionListener(ActionListener l) {
		((JTextField) getEditor().getEditorComponent()).addActionListener(l);
	}
	
	public String getSearchWord() {
		return ((JTextComponent) getEditor().getEditorComponent()).getText();
	}
}
