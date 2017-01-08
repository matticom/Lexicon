package viewFactory;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.ListCellRenderer;
import javax.swing.SwingUtilities;
import javax.swing.text.JTextComponent;

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

import AssignmentWindowComponents.AssignmentTableRowObject;
import businessOperations.LanguageBO;
import businessOperations.TermBO;
import businessOperations.TermBOTest;
import businessOperations.TransactionBeginCommit;
import dto.TechnicalTermDTO;
import interactElements.ChooseSpecialtyComboBox;
import interactElements.ComboBoxCellRenderer;
import eventHandling.ChosenLanguage;
import eventHandling.ComboBoxEventTransferObject;
import eventHandling.DialogWindows;
import eventHandling.DynamicPanels;
import eventHandling.PanelEventTransferObject;
import eventHandling.StaticPanels;
import exceptions.SpecialtyAlreadyExistsAsTechnicalTerm;
import exceptions.TechnicalTermAlreadyExistsAsSpecialty;
import inputChecker.AssignmentDialogChecker;
import inputChecker.NewTechnicalTermDialogChecker;
import inputChecker.SearchWordChecker;
import interactElements.ComboBoxFactory;
import interactElements.ComboBoxes;
import interactElements.MyComboBox;
import interactElements.SearchComboBox;
import model.Translations;
import model.Specialty;
import model.TechnicalTerm;
import model.Term;
import panels.TermPanelDynamic;
import panels.SearchResultPanelDynamic;
import panels.SearchResultPanelStatic;
import panels.TechnicalTermPanelStatic;
import panels.SpecialtyPanelStatic;
import repository.HistoryDAO;
import repository.LanguageDAO;
import repository.TermDAO;
import utilities.WinUtil;
import windows.AssignTechnicalTermToSpecialtyWindow;
import windows.TechnicalTermContentWindow;
import windows.TechnicalTermCreationWindow;

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
	private TransactionBeginCommit repositoryTA;
	

	private DialogWindowCreator windowCreator;
	private AssignTechnicalTermToSpecialtyWindow newAssignDialog;
	private TechnicalTermContentWindow contentTTDialog;

	private AssignmentTableRowObject[] tableRowObjectArray;
	private NewTechnicalTermDialogChecker newTTChecker;
	private AssignmentDialogChecker assignmentChecker;

	int mainFrameWidth;
	int mainFrameHeight;
	private Dimension displaySize;
	private final double MAINFRAME_DISPLAY_RATIO = 0.8;
	
	private ResourceBundle 	languageBundle = ResourceBundle.getBundle("languageBundles.lexikon", new Locale("de"));

	private int counter = 0;

	private final int GERMAN = 1;
	private final int SPANISH = 2;

	@Before
	public void refreshMainFrame() {

		mainFrame = new JFrame();

		mainFrame.setTitle("TestFrame");
		displaySize = Toolkit.getDefaultToolkit().getScreenSize();
		mainFrameWidth = (int) (displaySize.getWidth() * MAINFRAME_DISPLAY_RATIO);
		mainFrameHeight = (int) (displaySize.getHeight() * MAINFRAME_DISPLAY_RATIO);

		mainFrame.setSize(mainFrameWidth, mainFrameHeight);
		mainFrame.setMinimumSize(new Dimension(WinUtil.relW(1315), WinUtil.relH(400)));

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
		mainFrame.setLocationByPlatform(true);

		initializeDB();
	}

	@Test
	public void createTechnicalTermTest() throws Exception {

		StatusBar statusBar = new StatusBar(ResourceBundle.getBundle("languageBundles.lexikon", new Locale("es")));
		mainFrame.add(statusBar, BorderLayout.PAGE_END);

		List<Specialty> allSpecialtyList = termBOTest.selectAllSpecialties();
		List<TechnicalTerm> allTechnicalTermList = termBOTest.selectAllTechnicalTerms();

		SearchWordChecker searchWordChecker = new SearchWordChecker();
		newTTChecker = new NewTechnicalTermDialogChecker();
		assignmentChecker = new AssignmentDialogChecker();

		ComboBoxFactory comboBoxFactory = new ComboBoxFactory();
		fillHistoryList();

		SearchComboBox searchComboBox = (SearchComboBox) comboBoxFactory.createComboBox(ComboBoxes.SearchComboBox, history, searchWordChecker);

		ChooseSpecialtyComboBox chooseSpecialtyComboBox = (ChooseSpecialtyComboBox) comboBoxFactory.createComboBox(ComboBoxes.GermanSpecialtyComboBox,
				ResourceBundle.getBundle("languageBundles.lexikon", new Locale("de")), allSpecialtyList);
		ChooseSpecialtyComboBox chooseSpecialtyComboBox2 = (ChooseSpecialtyComboBox) comboBoxFactory.createComboBox(
				ComboBoxes.SpanishSpecialtyComboBox, ResourceBundle.getBundle("languageBundles.lexikon", new Locale("de")), allSpecialtyList);
		ChooseSpecialtyComboBox assignSpecialtyComboBox = (ChooseSpecialtyComboBox) comboBoxFactory.createComboBox(ComboBoxes.SpecialtyComboBox,
				ResourceBundle.getBundle("languageBundles.lexikon", new Locale("de")), allSpecialtyList);

		ListCellRenderer<Object> cboFontSizeRenderer = new ComboBoxCellRenderer();
		searchComboBox.setRenderer(cboFontSizeRenderer);

		MenuBar menuBar = new MenuBar(ResourceBundle.getBundle("languageBundles.lexikon", new Locale("es")));
		mainFrame.setJMenuBar(menuBar);
		menuBar.setMiAssignActionListener(e -> openAssignmentDialog(ResourceBundle.getBundle("languageBundles.lexikon", new Locale("de")),
				assignSpecialtyComboBox));

		checkLetter();
		HeadBar headBar = new HeadBar(MAINFRAME_DISPLAY_RATIO, ResourceBundle.getBundle("languageBundles.lexikon", new Locale("de")),
				searchComboBox, expectedAlphabet);
		mainFrame.add(headBar, BorderLayout.PAGE_START);
		headBar.setNewTechnicalTermButtonActionListener(e -> openNewTechnicalTermDialog(
				ResourceBundle.getBundle("languageBundles.lexikon", new Locale("es")), chooseSpecialtyComboBox, chooseSpecialtyComboBox2));
		
		
		TechnicalTerm contentFloatglas = termBOTest.selectTechnicalTermById(15);
		headBar.setAlphabetButtonActionListener(e -> openShowTechnicalTermDialog(ResourceBundle.getBundle("languageBundles.lexikon", new Locale("de")), contentFloatglas));

		StaticPanelCreator panelCreator = new StaticPanelCreator();

		TermPanelDynamic technicalTermDynamicPanel = new TermPanelDynamic(mainFrameWidth, mainFrameHeight,
				termBOTest.selectAllTechnicalTermsOfSpecialty(3), "SpecialtyName", e -> {}, DynamicPanels.TechnicalTermPanel);
		TermPanelDynamic specialtyDynamicPanel = new TermPanelDynamic(mainFrameWidth, mainFrameHeight, allSpecialtyList, e -> {}, DynamicPanels.SpecialtyPanel);
		SearchResultPanelDynamic searchDynamicPanel = new SearchResultPanelDynamic(mainFrameWidth, mainFrameHeight,
				termBOTest.searchTechnicalTerms("a%"), "Hallo Suche", null, DynamicPanels.SearchResultPanel);
		SearchResultPanelDynamic letterDynamicPanel = new SearchResultPanelDynamic(mainFrameWidth, mainFrameHeight,
				termBOTest.searchTechnicalTerms("A%"), ".A%", null, DynamicPanels.LetterResultPanel);

		TechnicalTermPanelStatic technicalTermPanel = (TechnicalTermPanelStatic) panelCreator.createPanel(StaticPanels.TechnicalTermPanel,
				ResourceBundle.getBundle("languageBundles.lexikon", new Locale("de")), MAINFRAME_DISPLAY_RATIO, technicalTermDynamicPanel);
		SpecialtyPanelStatic specialtyPanel = (SpecialtyPanelStatic) panelCreator.createPanel(StaticPanels.SpecialtyPanel,
				ResourceBundle.getBundle("languageBundles.lexikon", new Locale("de")), MAINFRAME_DISPLAY_RATIO, specialtyDynamicPanel);
		SearchResultPanelStatic searchPanel = (SearchResultPanelStatic) panelCreator.createPanel(StaticPanels.SearchResultPanel,
				ResourceBundle.getBundle("languageBundles.lexikon", new Locale("de")), MAINFRAME_DISPLAY_RATIO, searchDynamicPanel);
		SearchResultPanelStatic letterPanel = (SearchResultPanelStatic) panelCreator.createPanel(StaticPanels.LetterResultPanel,
				ResourceBundle.getBundle("languageBundles.lexikon", new Locale("de")), MAINFRAME_DISPLAY_RATIO, letterDynamicPanel);

		windowCreator = new DialogWindowCreator();

		// --->
		// mainFrame.add(technicalTermPanel, BorderLayout.CENTER);
		mainFrame.add(specialtyPanel, BorderLayout.CENTER);
		// mainFrame.add(searchPanel, BorderLayout.CENTER);
		// mainFrame.add(letterPanel, BorderLayout.CENTER);
		mainFrame.setVisible(true);

		mainFrame.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {

				Component c = e.getComponent();
				TermPanelDynamic dynamicPanel = new TermPanelDynamic(c.getWidth(), c.getHeight(), termBOTest.selectAllSpecialties(), event -> {}, DynamicPanels.SpecialtyPanel);
				PanelEventTransferObject peto = new PanelEventTransferObject(c.getWidth(), c.getHeight(), history, null, ChosenLanguage.Spanish, expectedAlphabet, null, null, dynamicPanel);
				
				
				// peto.setSpecialtyList(termBOTest.selectAllSpecialties());
				// if (counter < 9) {
				// --->
				// TermPanelDynamic dynamicPanel = new
				// TermPanelDynamic(c.getWidth(), c.getHeight(),
				// termBOTest.selectAllTechnicalTermsOfSpecialty(3),
				// "Specialtyname");
				
				// SearchResultPanelDynamic dynamicPanel = new
				// SearchResultPanelDynamic(mainFrameWidth, mainFrameHeight,
				// termBOTest.searchTechnicalTerms("a%"), "Hallo Suche");
				// SearchResultPanelDynamic dynamicPanel = new
				// SearchResultPanelDynamic(mainFrameWidth, mainFrameHeight,
				// termBOTest.searchTechnicalTerms("A%"), ".A%");
				
				// }

				if (counter > 9 && counter < 19 || counter > 39 && counter < 49) {
					peto = new PanelEventTransferObject(c.getWidth(), c.getHeight(), history, null, ChosenLanguage.German, expectedAlphabet, 
							termBOTest.selectSpecialtyById(6), termBOTest.selectTechnicalTermById(19), dynamicPanel);
					
				}

				if (counter > 20 && counter < 29 || counter > 49 && counter < 109) {
					peto = new PanelEventTransferObject(c.getWidth(), c.getHeight(), history, null, ChosenLanguage.German, expectedAlphabet, 
							termBOTest.selectSpecialtyById(9), termBOTest.selectTechnicalTermById(19), dynamicPanel);
					
				}

				headBar.updatePanel(peto);
				// headBar2.updatePanel(peto);
				searchComboBox.updatePanel(peto);
				menuBar.updatePanel(peto);
				System.out.println("Statusbar wird aktualissssiiiiiieert");
				statusBar.updatePanel(peto);
				// --->
				// technicalTermPanel.updatePanel(peto);
				specialtyPanel.updatePanel(peto);
				// searchPanel.updatePanel(peto);
				// letterPanel.updatePanel(peto);
				counter++;
				System.out.println("HeadBarGr��e: " + headBar.getWidth() + " x " + headBar.getHeight());

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
			ij.runScript(connection, TermBOTest.class.getResourceAsStream("/Lexicon_Database_Schema_Derby.sql"), "UTF-8", System.out, "UTF-8");
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
		HistoryDAO historyDAO = new HistoryDAO(entitymanager);
		repositoryTA = new TransactionBeginCommit(entitymanager, termBOTest, historyDAO);

		try {
			DatabaseOperation.CLEAN_INSERT.execute(mDBUnitConnection, startDataset);
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public void closeDB() {

		try {
			mDBUnitConnection.close();
		} catch (SQLException e) {
			System.out.println("Es wurde eine Exception beim Schlie�en der IDataConnection geworfen: " + e.getMessage());
			e.printStackTrace();
		}

		entitymanager.close();
		emfactory.close();
	}

	private void checkLetter() {
		expectedAlphabet = new boolean[26];
		for (boolean letter : expectedAlphabet) {
			letter = false;
		}
		expectedAlphabet = termBOTest.checkLetter();
	}

	private void fillHistoryList() {
		// als Test: es werden alle TechnicalTerms mit F in die Liste gef�llt
		List<Translations> translationList = termBOTest.searchTechnicalTerms("f%");
		history = new ArrayList<String>();

		for (Translations translation : translationList) {
			history.add(translation.getName());
		}
	}

	public void openNewTechnicalTermDialog(ResourceBundle languageBundle, ChooseSpecialtyComboBox germanSpecialtyComboBox,
			ChooseSpecialtyComboBox spanishSpecialtyComboBox) {

		boolean testPassed = false;
		TechnicalTermCreationWindow newTTDialog = (TechnicalTermCreationWindow) windowCreator.createWindow(DialogWindows.TechnicalTermCreationWindow,
				languageBundle, germanSpecialtyComboBox, spanishSpecialtyComboBox);
		newTTDialog.setTextFieldListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				newTTChecker.keyTypedChecker(e);
			}

			@Override
			public void keyPressed(KeyEvent e) {
				newTTChecker.keyPressedChecker(e);
			}
		});

		newTTDialog.setInsertButtonsActionListener(e -> {
			newTTChecker.checkDialog(newTTDialog, repositoryTA);
			if (newTTChecker.isTestPassed()) {
				saveNewTechnicalTerm(languageBundle, germanSpecialtyComboBox, spanishSpecialtyComboBox, newTTDialog);
			}
		});
	}

	private void saveNewTechnicalTerm(ResourceBundle languageBundle, ChooseSpecialtyComboBox germanSpecialtyComboBox,
			ChooseSpecialtyComboBox spanishSpecialtyComboBox, TechnicalTermCreationWindow newTTDialog) {
		int specialtyId = 0;
		if (newTTDialog.isNewSpecialtySelected()) {
			
			entitymanager.getTransaction().begin();
			Specialty newSpecialty = termBOTest.createSpecialty(newTTDialog.getGermanSpecialtyInput().getText(), "", 1);
			entitymanager.getTransaction().commit();
			
			specialtyId = newSpecialty.getId();
			
			entitymanager.getTransaction().begin();
			termBOTest.createSpecialtyTranslation(specialtyId, newTTDialog.getSpanishSpecialtyInput().getText(), "", 2);
			entitymanager.getTransaction().commit();
		} else {
			if (WinUtil.getLanguageId(languageBundle) == GERMAN) {
				specialtyId = germanSpecialtyComboBox.getSelectedListItem().getValueMember();
				System.out.println("Specialty Id: " + specialtyId);
			}
			if (WinUtil.getLanguageId(languageBundle) == SPANISH) {
				specialtyId = spanishSpecialtyComboBox.getSelectedListItem().getValueMember();
				System.out.println("Specialty Id: " + specialtyId);
			}
		}
			
		int technicalTermId;
		entitymanager.getTransaction().begin();
		technicalTermId = termBOTest.createTechnicalTerm(newTTDialog.getGermanTextField().getText(), newTTDialog.getGermanTextArea().getText(), 1, specialtyId).getId();
		entitymanager.getTransaction().commit();
		
		entitymanager.getTransaction().begin();
		termBOTest.createTechnicalTermTranslation(technicalTermId, newTTDialog.getSpanishTextField().getText(), newTTDialog.getSpanishTextArea().getText(), 2);
		entitymanager.getTransaction().commit();
	}

		
	public void openAssignmentDialog(ResourceBundle languageBundle,	ChooseSpecialtyComboBox specialtyComboBox) {
		List<TechnicalTerm> technicalTermList = termBOTest.selectAllTechnicalTerms();
		newAssignDialog = (AssignTechnicalTermToSpecialtyWindow) windowCreator.createWindow(DialogWindows.AssignTechnicalTermToSpecialtyWindow,
				languageBundle, technicalTermList, specialtyComboBox);
		newAssignDialog.setTextFieldListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				assignmentChecker.keyTypedChecker(e);
			}

			@Override
			public void keyPressed(KeyEvent e) {
				assignmentChecker.keyPressedChecker(e);
			}
		});

		newAssignDialog.setChangeButtonActionListener(e -> {
			assignmentChecker.checkDialog(newAssignDialog, repositoryTA);
			if (assignmentChecker.isTestPassed()) {
				changeButtonPressed(specialtyComboBox);
			}
		});
	}

	public void changeButtonPressed(ChooseSpecialtyComboBox specialtyComboBox) {
		
			int specialtyId;
			if (newAssignDialog.isNewSpecialtySelected()) {
				
				entitymanager.getTransaction().begin();
				Specialty newSpecialty = termBOTest.createSpecialty(newAssignDialog.getGermanSpecialtyInput().getText(), "", 1);
				entitymanager.getTransaction().commit();
				
				specialtyId = newSpecialty.getId();
				
				entitymanager.getTransaction().begin();
				termBOTest.createSpecialtyTranslation(specialtyId, newAssignDialog.getSpanishSpecialtyInput().getText(), "", 2);
				entitymanager.getTransaction().commit();
			} else {
				specialtyId = specialtyComboBox.getSelectedListItem().getValueMember();
			}	
			termBOTest.assignTechnicalTermsToSpecialty(newAssignDialog.getTechnicalTermIds(), specialtyId);
			newAssignDialog.refreshAssignmentTableModel(termBOTest.selectAllTechnicalTerms());
			System.out.println("Fertig.... Fenster schlie�en");
	}
	
	public void openShowTechnicalTermDialog(ResourceBundle languageBundle, TechnicalTerm technicalTerm) {
		
		contentTTDialog = (TechnicalTermContentWindow) windowCreator.createWindow(DialogWindows.TechnicalTermContentWindow,
				languageBundle, technicalTerm);
		contentTTDialog.setSaveChangesButtonActionListener(e -> saveNewTechnicalTermDescription(contentTTDialog, technicalTerm));
		
	}
	
	private void saveNewTechnicalTermDescription(TechnicalTermContentWindow contentTTDialog, TechnicalTerm technicalTerm) {
		if (querySave(contentTTDialog)) {
			TechnicalTermDTO technicalTermDTO = new TechnicalTermDTO(technicalTerm);
			termBOTest.updateTranslation(technicalTerm.getId(), technicalTermDTO.getGermanName(), contentTTDialog.getGermanTextAreaText(), GERMAN);
			termBOTest.updateTranslation(technicalTerm.getId(), technicalTermDTO.getSpanishName(), contentTTDialog.getSpanishTextAreaText(), SPANISH);
			contentTTDialog.saveChangesEditMode();
		}
	}
	
	private boolean querySave(TechnicalTermContentWindow contentTTDialog) {

		String[] options = { languageBundle.getString("speichern"), languageBundle.getString("abort") };
		if (!contentTTDialog.isTextHasChangedInTextAreas()) {
			return true;
		}
		int retValue = JOptionPane.showOptionDialog(contentTTDialog, languageBundle.getString("speichernDaten"), languageBundle.getString("speichernDatenTitle"), JOptionPane.YES_NO_OPTION,
				JOptionPane.WARNING_MESSAGE, null, options, options[1]);
	
		if (retValue == JOptionPane.YES_OPTION) {
			return true;
		}
		return false;
	}

}
