package viewFactory;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JFrame;


import org.junit.Before;
import org.junit.Test;

import eventHandling.ChosenLanguage;
import eventHandling.ComboBoxEventTransferObject;
import eventHandling.PanelEventTransferObject;
import interactElements.ComboBoxFactory;
import interactElements.ComboBoxes;
import interactElements.SearchComboBox;

public class HeadBarTest {

	JFrame mainFrame;
	
	@Before
	public void refreshMainFrame() {
		
		mainFrame = new JFrame();

		mainFrame.setTitle("TestFrame");
		mainFrame.setSize(1300, 800);
		mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		mainFrame.setResizable(false);
		mainFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				mainFrame.dispose();
			}
		});
		mainFrame.setLocationRelativeTo(null);
		
	}
	
	@Test
	public void createTechnicalTermTest() throws Exception {
		
		HeadBar headBar = new HeadBar(ResourceBundle.getBundle("languageBundles.lexikon",new Locale("de")));
		
		boolean[] expectedAlphabet = new boolean[26];
		for(boolean letter: expectedAlphabet) {
			letter = false;
		}
		expectedAlphabet[0] = true;
		expectedAlphabet[1] = true;
		expectedAlphabet[2] = true;
		expectedAlphabet[5] = true;
		expectedAlphabet[7] = true;
		expectedAlphabet[17] = true;
		expectedAlphabet[21] = true;
		
		PanelEventTransferObject peto = new PanelEventTransferObject();
		peto.setAvailableLetters(expectedAlphabet);
		peto.setCurrentLanguage(ChosenLanguage.Spanish);
		
		ComboBoxEventTransferObject cbeto = new ComboBoxEventTransferObject();
		List<String> history = new ArrayList<String>();
		history.add("Bewehrung");
		history.add("Beton");
		
		cbeto.setEntries(history);
		
		ComboBoxFactory comboBoxFactory = new ComboBoxFactory();
		SearchComboBox searchComboBox = (SearchComboBox) comboBoxFactory.createComboBox(ComboBoxes.SearchComboBox);
		searchComboBox.refillComboBox(cbeto);
		
		headBar.add(searchComboBox);
		
		headBar.updateFrame(peto);
		mainFrame.add(headBar);
		mainFrame.setVisible(true);
		Thread.sleep(200000);
	}
	

}
