package viewFactory;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JFrame;
import javax.swing.ListCellRenderer;
import javax.swing.SwingUtilities;

import org.apache.derby.tools.ij;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.DatabaseSequenceFilter;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.FilteredDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.filter.ITableFilter;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.eclipse.persistence.internal.jpa.EntityManagerImpl;
import org.junit.Before;
import org.junit.Test;

import businessOperations.LanguageBO;
import businessOperations.TermBO;
import businessOperations.TermBOTest;
import interactElements.ComboBoxCellRenderer;
import eventHandling.ChosenLanguage;
import eventHandling.ComboBoxEventTransferObject;
import eventHandling.PanelEventTransferObject;
import interactElements.ComboBoxFactory;
import interactElements.ComboBoxes;
import interactElements.SearchComboBox;
import model.Translations;
import panels.PanelTest;
import panels.SpecialtyPanel;
import repository.LanguageDAO;
import repository.TermDAO;

public class HeadBarTest {

	JFrame mainFrame;
	
	boolean[] expectedAlphabet;
	List<String> history;
	
	private static IDatabaseConnection mDBUnitConnection;
    private static IDataSet startDataset;
        
	private static EntityManagerFactory emfactory;
	private static EntityManager entitymanager;	
	private static Connection connection;	
	private static TermDAO termDAOTest;
	private static LanguageDAO languageDAOTest;
	private static TermBO termBOTest;
	private static LanguageBO languageBOTest;
	
	int mainFrameWidth;
	int mainFrameHeight;
	private final double MAINFRAME_DISPLAY_RATIO = 1;
	
	private int counter = 0;

	@Before
	public void refreshMainFrame() {

		mainFrame = new JFrame();

		mainFrame.setTitle("TestFrame");
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		mainFrameWidth = (int)(dim.getWidth() * MAINFRAME_DISPLAY_RATIO);
		mainFrameHeight = (int)(dim.getHeight() * MAINFRAME_DISPLAY_RATIO);
			
		mainFrame.setSize(mainFrameWidth, mainFrameHeight);
		System.out.println(mainFrame.getSize());	
		mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		mainFrame.setResizable(true);
		mainFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				mainFrame.dispose();
				closeDB();
				System.exit(0);
			}
		});
		mainFrame.setLocationRelativeTo(null);
		
		initializeDB();

	}

	@Test
	public void createTechnicalTermTest() throws Exception {

		StatusBar statusBar = new StatusBar(ResourceBundle.getBundle("languageBundles.lexikon", new Locale("es")));
		mainFrame.add(statusBar, BorderLayout.PAGE_END);
		
		MenuBar menuBar = new MenuBar(ResourceBundle.getBundle("languageBundles.lexikon", new Locale("es")));
		mainFrame.setJMenuBar(menuBar);
		
		HeadBar headBar = new HeadBar(mainFrameWidth, mainFrameHeight, ResourceBundle.getBundle("languageBundles.lexikon", new Locale("de")));
//		HeadBar headBar2 = new HeadBar(mainFrameWidth, mainFrameHeight, ResourceBundle.getBundle("languageBundles.lexikon", new Locale("de")));
//		PanelTest test = new PanelTest();
		SpecialtyPanel specialtyPanel = new SpecialtyPanel(ResourceBundle.getBundle("languageBundles.lexikon", new Locale("es")));

		PanelEventTransferObject peto = new PanelEventTransferObject();
		peto.setAvailableLetters(expectedAlphabet);
		peto.setCurrentLanguage(ChosenLanguage.Spanish);
		peto.setMainFrameWidth(mainFrameWidth);
		peto.setMainFrameHeight(mainFrameHeight);
		peto.setCurrentSpecialty(termBOTest.selectSpecialtyById(6));
		peto.setCurrentTechnicalTerm(termBOTest.selectTechnicalTermById(19));

		List<Translations> translationList = termBOTest.selectLetter("f");
		
		expectedAlphabet = new boolean[26];
		for (boolean letter : expectedAlphabet) {
			letter = false;
		}
		expectedAlphabet = termBOTest.checkLetter();
		
		
		
		history = new ArrayList<String>();
		
		for (Translations translation : translationList) {
			history.add(translation.getName());
		}
		
		peto.setHistory(history);

		ComboBoxFactory comboBoxFactory = new ComboBoxFactory();
		SearchComboBox searchComboBox = (SearchComboBox) comboBoxFactory.createComboBox(ComboBoxes.SearchComboBox);
		SearchComboBox searchComboBox2 = (SearchComboBox) comboBoxFactory.createComboBox(ComboBoxes.SearchComboBox);
		
		
		ListCellRenderer<Object> cboFontSizeRenderer = new ComboBoxCellRenderer();
		searchComboBox.setRenderer(cboFontSizeRenderer);
		searchComboBox2.setRenderer(cboFontSizeRenderer);
		
		headBar.add(searchComboBox);
//		headBar2.add(searchComboBox2);

		mainFrame.add(headBar, BorderLayout.PAGE_START);
		mainFrame.add(specialtyPanel, BorderLayout.CENTER);
		mainFrame.setVisible(true);
		
		mainFrame.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				
				Component c = e.getComponent();
				PanelEventTransferObject peto = new PanelEventTransferObject();
				peto.setMainFrameWidth(c.getWidth());
				peto.setMainFrameHeight(c.getHeight());
				peto.setAvailableLetters(expectedAlphabet);
				peto.setCurrentLanguage(ChosenLanguage.Spanish);
				peto.setHistory(history);
				
				if (counter > 9 && counter < 19 || counter > 39 && counter < 49 ) {
					peto.setCurrentLanguage(ChosenLanguage.German);
					peto.setCurrentSpecialty(termBOTest.selectSpecialtyById(6));
					peto.setCurrentTechnicalTerm(termBOTest.selectTechnicalTermById(19));
				}
				
				if (counter > 20 && counter < 29 || counter > 49 && counter < 109 ) {
					peto.setCurrentSpecialty(termBOTest.selectSpecialtyById(9));
				}
																
				headBar.updatePanel(peto);
//				headBar2.updatePanel(peto);
				searchComboBox.updatePanel(peto);
				searchComboBox2.updatePanel(peto);
				menuBar.updatePanel(peto);
				statusBar.updatePanel(peto);
				counter++;
				System.out.println("Framegröße: " + mainFrame.getWidth() + " x " + mainFrame.getHeight());
				
			}
		});
		Thread.sleep(200000);
		closeDB();
	}
	
	public void initializeDB() {
		
		emfactory = Persistence.createEntityManagerFactory("Eclipselink_JPA_Derby");
		entitymanager = emfactory.createEntityManager();
		connection = ((EntityManagerImpl) (entitymanager.getDelegate())).getServerSession().getAccessor().getConnection();

		try {
			ij.runScript(connection,TermBOTest.class.getResourceAsStream("/Lexicon_Database_Schema_Derby.sql"),"UTF-8", System.out, "UTF-8"); 
		} catch (Exception e) {
			
			e.printStackTrace();
		}

		try {
			mDBUnitConnection = new DatabaseConnection(connection);
			mDBUnitConnection.getConfig().setProperty(DatabaseConfig.FEATURE_ALLOW_EMPTY_FIELDS, true);
			startDataset = new FlatXmlDataSetBuilder().build(new File("./src/test/resources/XML/Term/testSet_EntireDB.xml"));
		} catch (Exception e) {
			
			e.printStackTrace();
		}
			
		
		termDAOTest = new TermDAO(entitymanager);
		languageDAOTest = new LanguageDAO(entitymanager);
		languageBOTest = new LanguageBO(languageDAOTest);
		termBOTest = new TermBO(languageBOTest, termDAOTest);
		
		try {
			DatabaseOperation.CLEAN_INSERT.execute(mDBUnitConnection, startDataset);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
//		try {
//			ITableFilter filter = new DatabaseSequenceFilter(mDBUnitConnection);
//			IDataSet fullDataSet = new FilteredDataSet(filter, mDBUnitConnection.createDataSet());
//			FlatXmlDataSet.write(fullDataSet, new FileOutputStream("actual.xml"));
//			
//		} catch (Exception e) {
//			System.out.println("Es wurde eine Exception bei FullDataSetXML geworfen: "+ e.getMessage());
//			e.printStackTrace();
//		}	
	}
	
	public void closeDB() {

		try {
			mDBUnitConnection.close();
		} catch (SQLException e) {
			System.out.println("Es wurde eine Exception beim Schließen der IDataConnection geworfen: "+ e.getMessage());
			e.printStackTrace();
		}
		
		entitymanager.close();
		emfactory.close();
	}
}
