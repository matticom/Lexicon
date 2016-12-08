package viewFactory;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.ListCellRenderer;
import javax.swing.SwingUtilities;

import org.junit.Before;
import org.junit.Test;

import interactElements.ComboBoxCellRenderer;
import eventHandling.ChosenLanguage;
import eventHandling.ComboBoxEventTransferObject;
import eventHandling.PanelEventTransferObject;
import interactElements.ComboBoxFactory;
import interactElements.ComboBoxes;
import interactElements.SearchComboBox;

public class HeadBarTest {

	JFrame mainFrame;
	HeadBar headBar;
	boolean[] expectedAlphabet;
	List<String> history;

	@Before
	public void refreshMainFrame() {

		mainFrame = new JFrame();

		mainFrame.setTitle("TestFrame");
		mainFrame.setSize(1300, 800);
		mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		mainFrame.setResizable(true);
		mainFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				mainFrame.dispose();
			}
		});
		mainFrame.setLocationRelativeTo(null);

		expectedAlphabet = new boolean[26];
		for (boolean letter : expectedAlphabet) {
			letter = false;
		}
		expectedAlphabet[0] = true;
		expectedAlphabet[1] = true;
		expectedAlphabet[2] = true;
		expectedAlphabet[5] = true;
		expectedAlphabet[7] = true;
		expectedAlphabet[17] = true;
		expectedAlphabet[21] = true;

	}

	@Test
	public void createTechnicalTermTest() throws Exception {

		HeadBar headBar = new HeadBar(ResourceBundle.getBundle("languageBundles.lexikon", new Locale("es")));
		HeadBar headBar2 = new HeadBar(ResourceBundle.getBundle("languageBundles.lexikon", new Locale("de")));

		PanelEventTransferObject peto = new PanelEventTransferObject();
		peto.setAvailableLetters(expectedAlphabet);
		peto.setCurrentLanguage(ChosenLanguage.Spanish);
		peto.setMainframeWidth(mainFrame.getWidth());
		peto.setMainframeHeight(mainFrame.getHeight());

		
		history = new ArrayList<String>();
		history.add("Bewehrung");
		history.add("Beton");

		peto.setEntries(history);

		ComboBoxFactory comboBoxFactory = new ComboBoxFactory();
		SearchComboBox searchComboBox = (SearchComboBox) comboBoxFactory.createComboBox(ComboBoxes.SearchComboBox);
		SearchComboBox searchComboBox2 = (SearchComboBox) comboBoxFactory.createComboBox(ComboBoxes.SearchComboBox);
		
		
		ListCellRenderer<Object> cboFontSizeRenderer = new ComboBoxCellRenderer();
		searchComboBox.setRenderer(cboFontSizeRenderer);
		searchComboBox2.setRenderer(cboFontSizeRenderer);
		
		headBar.add(searchComboBox);
		headBar2.add(searchComboBox2);

		mainFrame.add(headBar, BorderLayout.PAGE_START);
		mainFrame.add(headBar2, BorderLayout.CENTER);
		mainFrame.setVisible(true);
		
		mainFrame.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				
				Component c = e.getComponent();
				PanelEventTransferObject peto = new PanelEventTransferObject();
				peto.setMainframeWidth(c.getWidth());
				peto.setMainframeHeight(c.getHeight());
				peto.setAvailableLetters(expectedAlphabet);
				peto.setCurrentLanguage(ChosenLanguage.Spanish);
				peto.setEntries(history);
				
				headBar.updateFrame(peto);
				headBar2.updateFrame(peto);
				searchComboBox.updateFrame(peto);
				searchComboBox2.updateFrame(peto);
			}
		});
		Thread.sleep(200000);
	}
}
