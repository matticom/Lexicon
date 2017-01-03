package presenter;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.ListCellRenderer;
import javax.swing.SwingUtilities;
import javax.swing.text.JTextComponent;

import org.apache.derby.tools.ij;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.eclipse.persistence.internal.jpa.EntityManagerImpl;

import utilities.WinUtil;
import viewFactory.DialogWindowCreator;
import viewFactory.HeadBar;
import viewFactory.MainFrame;
import viewFactory.MenuBar;
import viewFactory.PanelCreator;
import viewFactory.StatusBar;
import businessOperations.LanguageBO;
import businessOperations.TermBO;
import businessOperations.TermBOTest;
import eventHandling.ChosenLanguage;
import eventHandling.PanelEventTransferObject;
import eventHandling.PanelUpdateObjects;
import eventHandling.Updatable;
import inputChecker.AssignmentDialogChecker;
import inputChecker.NewTechnicalTermDialogChecker;
import inputChecker.SearchWordChecker;
import interactElements.ChooseSpecialtyComboBox;
import interactElements.ComboBoxCellRenderer;
import interactElements.ComboBoxFactory;
import interactElements.ComboBoxes;
import interactElements.SearchComboBox;
import interactElements.SpecialtyButton;
import model.History;
import model.Specialty;
import model.TechnicalTerm;
import model.Translations;
import panels.TechnicalTermPanelStatic;
import panels.TermPanelDynamic;
import panels.MyPanel;
import panels.SearchResultPanelDynamic;
import panels.SearchResultPanelStatic;
import panels.SpecialtyPanelStatic;
import repository.HistoryDAO;
import repository.LanguageDAO;
import repository.TermDAO;
import windows.AssignTechnicalTermToSpecialtyWindow;
import windows.TechnicalTermContentWindow;
import windows.TechnicalTermCreationWindow;

public class Presenter {

	private IDatabaseConnection mDBUnitConnection;
	private IDataSet startDataset;
	private EntityManagerFactory emfactory;
	private EntityManager entitymanager;
	private TermBO repositoryService;
	private HistoryDAO historyListService;

	private ComboBoxFactory comboBoxFactory;
	private PanelCreator panelCreator;
	private DialogWindowCreator windowCreator;

	private SearchWordChecker searchWordChecker;
	private NewTechnicalTermDialogChecker newTTChecker;
	private AssignmentDialogChecker assignmentChecker;

	private SearchComboBox searchComboBox;
	private ChooseSpecialtyComboBox chooseSpecialtyComboBoxGerman;
	private ChooseSpecialtyComboBox chooseSpecialtyComboBoxSpanish;
	private ChooseSpecialtyComboBox assignSpecialtyComboBox;

	private TermPanelDynamic technicalTermDynamicPanel;
	private TermPanelDynamic specialtyDynamicPanel;
	private SearchResultPanelDynamic searchDynamicPanel;
	private SearchResultPanelDynamic letterDynamicPanel;

	private TechnicalTermPanelStatic technicalTermPanel;
	private SpecialtyPanelStatic specialtyPanel;
	private SearchResultPanelStatic searchPanel;
	private SearchResultPanelStatic letterPanel;

	private MenuBar menuBar;
	private HeadBar headBar;
	private StatusBar statusBar;
	private MainFrame mainFrame;

	private List<Updatable> updatableComponentList;
	private ResourceBundle languageBundle;

	private int mainFrameWidth;
	private int mainFrameHeight;

	private final double MAINFRAME_DISPLAY_RATIO;

	private Specialty currentSpecialty;
	private TechnicalTerm currentTechnicalTerm;
	
	private PanelEventTransferObject peto;
	private MyPanel currentCenterPanel;
	private Container centerContainer;

	public Presenter() {

		initializeDataBase();
		initializeAppSettings();
		initializeFactories();
		initializeInputChecker();
		initializeComboBoxes();
		initializeContentPanePanels();
		initializeMainFrameComponents();
		initializeMainFrame();
	}

	private void initializeDataBase() {

		emfactory = Persistence.createEntityManagerFactory("Eclipselink_JPA_Derby");
		entitymanager = emfactory.createEntityManager();
		Connection connection = ((EntityManagerImpl) (entitymanager.getDelegate())).getServerSession().getAccessor().getConnection();

		try {
			ij.runScript(connection, TermBOTest.class.getResourceAsStream("/Lexicon_Database_Schema_Derby.sql"), "UTF-8", System.out, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			mDBUnitConnection = new DatabaseConnection(connection);
			mDBUnitConnection.getConfig().setProperty(DatabaseConfig.FEATURE_ALLOW_EMPTY_FIELDS, true);
			IDataSet startDataset = new FlatXmlDataSetBuilder().build(new File("./src/test/resources/XML/Term/testSet_EntireDB.xml"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		TermDAO termDAOService = new TermDAO(entitymanager);
		LanguageDAO languageDAOSerive = new LanguageDAO(entitymanager);
		LanguageBO languageBOService = new LanguageBO(languageDAOSerive);
		repositoryService = new TermBO(languageBOService, termDAOService);
		historyListService = new HistoryDAO(entitymanager);

		try {
			DatabaseOperation.CLEAN_INSERT.execute(mDBUnitConnection, startDataset);
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	private void initializeAppSettings() {

		languageBundle = ResourceBundle.getBundle("languageBundles.lexikon", new Locale("de"));
		Dimension displayResolution = Toolkit.getDefaultToolkit().getScreenSize();
		mainFrameWidth = (int) (displayResolution.getWidth() * MAINFRAME_DISPLAY_RATIO);
		mainFrameHeight = (int) (displayResolution.getHeight() * MAINFRAME_DISPLAY_RATIO);
		updatableComponentList = new ArrayList<Updatable>();
		centerContainer = new JPanel(new GridLayout(1, 1));
		currentSpecialty = null;
		currentTechnicalTerm = null;
	}

	private void initializeFactories() {

		comboBoxFactory = new ComboBoxFactory();
		panelCreator = new PanelCreator();
		windowCreator = new DialogWindowCreator();
	}

	private void initializeInputChecker() {

		searchWordChecker = new SearchWordChecker();
		newTTChecker = new NewTechnicalTermDialogChecker();
		assignmentChecker = new AssignmentDialogChecker();
	}

	private void initializeComboBoxes() {

		searchComboBox = (SearchComboBox) comboBoxFactory.createComboBox(ComboBoxes.SearchComboBox, getHistoryListFromDB(), searchWordChecker);
		chooseSpecialtyComboBoxGerman = (ChooseSpecialtyComboBox) comboBoxFactory.createComboBox(ComboBoxes.GermanSpecialtyComboBox, languageBundle,
				repositoryService.selectAllSpecialties());
		chooseSpecialtyComboBoxSpanish = (ChooseSpecialtyComboBox) comboBoxFactory.createComboBox(ComboBoxes.SpanishSpecialtyComboBox, languageBundle,
				repositoryService.selectAllSpecialties());
		assignSpecialtyComboBox = (ChooseSpecialtyComboBox) comboBoxFactory.createComboBox(ComboBoxes.SpecialtyComboBox, languageBundle, repositoryService.selectAllSpecialties());

		ListCellRenderer<Object> fontSizeRenderer = new ComboBoxCellRenderer();
		searchComboBox.setRenderer(fontSizeRenderer);

		register(searchComboBox);
		register(chooseSpecialtyComboBoxGerman);
		register(chooseSpecialtyComboBoxSpanish);
		register(assignSpecialtyComboBox);
	}

	private void initializeContentPanePanels() {

		initializeDynamicPanels();
		initializeStaticPanels();
	}

	private void initializeDynamicPanels() {

		technicalTermDynamicPanel = new TermPanelDynamic(mainFrameWidth, mainFrameHeight, repositoryService.selectAllTechnicalTermsOfSpecialty(3), "SpecialtyName");
		specialtyDynamicPanel = new TermPanelDynamic(mainFrameWidth, mainFrameHeight, repositoryService.selectAllSpecialties());
		searchDynamicPanel = new SearchResultPanelDynamic(mainFrameWidth, mainFrameHeight, repositoryService.searchTechnicalTerms("a%"), "Hallo Suche");
		letterDynamicPanel = new SearchResultPanelDynamic(mainFrameWidth, mainFrameHeight, repositoryService.searchTechnicalTerms("A%"), ".A%");
	}

	private void initializeStaticPanels() {

		technicalTermPanel = (TechnicalTermPanelStatic) panelCreator.createPanel(PanelUpdateObjects.TechnicalTermPanel, languageBundle, MAINFRAME_DISPLAY_RATIO,
				technicalTermDynamicPanel);
		specialtyPanel = (SpecialtyPanelStatic) panelCreator.createPanel(PanelUpdateObjects.SpecialtyPanel, languageBundle, MAINFRAME_DISPLAY_RATIO, specialtyDynamicPanel);
		searchPanel = (SearchResultPanelStatic) panelCreator.createPanel(PanelUpdateObjects.SearchResultPanel, languageBundle, MAINFRAME_DISPLAY_RATIO, searchDynamicPanel);
		letterPanel = (SearchResultPanelStatic) panelCreator.createPanel(PanelUpdateObjects.LetterResultPanel, languageBundle, MAINFRAME_DISPLAY_RATIO, letterDynamicPanel);

		register(specialtyPanel);
	}

	private void initializeMainFrameComponents() {

		initializeMenuBar();
		initializeHeadBar();
		initializeStatusBar();
	}

	private void initializeMenuBar() {

		menuBar = new MenuBar(languageBundle);
		menuBar.setMiExitActionListener(e -> closeApplication());
		menuBar.setMiNewActionListener(e -> openNewTechnicalTermDialog(languageBundle, chooseSpecialtyComboBoxGerman, chooseSpecialtyComboBoxSpanish));
		menuBar.setMiAssignActionListener(e -> openAssignmentDialog(languageBundle, assignSpecialtyComboBox));
		menuBar.setMiHistoryActionListener(e -> deleteHistory());
		menuBar.setMiGermanActionListener(e -> changeToGerman());
		menuBar.setMiSpanishActionListener(e -> changeToSpanish());
		register(menuBar);
	}

	private void initializeHeadBar() {

		headBar = new HeadBar(MAINFRAME_DISPLAY_RATIO, languageBundle, searchComboBox, repositoryService.checkLetter());
		headBar.setSearchButtonActionListener(e -> openSearchResult(searchComboBox.getSearchWord()));
		headBar.setAlphabetButtonActionListener(e -> openLetterResult(e));
		headBar.setSpecialtyButtonActionListener(e -> openSpecialtyResult(false));
		headBar.setDeButtonActionListener(e -> changeToGerman());
		headBar.setEsButtonActionListener(e -> changeToSpanish());
		headBar.setNewTechnicalTermButtonActionListener(e -> openNewTechnicalTermDialog(languageBundle, chooseSpecialtyComboBoxGerman, chooseSpecialtyComboBoxSpanish));
		// nach ende der methode neues peto um komponenten zu aktualisieren
		register(headBar);
	}

	private void initializeStatusBar() {

		statusBar = new StatusBar(languageBundle);
		statusBar.setStatusBarStartpageButtonActionListener(e -> statusBar_specialtyResult());
		statusBar.setStatusBarSpecialtyButtonActionListener(e -> openSpecialtyResult(false));
		register(statusBar);
	}

	private void initializeMainFrame() {

		mainFrame = new MainFrame(MAINFRAME_DISPLAY_RATIO, languageBundle);

		mainFrame.setJMenuBar(menuBar);
		mainFrame.add(headBar, BorderLayout.PAGE_START);
		mainFrame.add(specialtyPanel, BorderLayout.CENTER);
		mainFrame.add(statusBar, BorderLayout.PAGE_END);
		mainFrame.setMainFrameComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				Component c = e.getComponent();
				mainFrameWidth = c.getWidth();
				mainFrameHeight = c.getHeight();

			}
		});
		mainFrame.setMainFrameWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				closeApplication();
			}
		});
		register(mainFrame);
	}

	private void statusBar_specialtyResult() {

	}

	private void statusBar_technicalTermResult() {
		PanelEventTransferObject peto = new PanelEventTransferObject();
		openTechnicalTermResult(peto);
	}

	private void openSearchResult(String searchWord) {

	}

	private void openLetterResult(ActionEvent e) {
		String searchWord = e.getActionCommand();
		SearchResultPanelDynamic letterDynamicPanel = new SearchResultPanelDynamic(mainFrameWidth, mainFrameHeight, repositoryService.searchTechnicalTerms(searchWord),
				"." + searchWord);
	}

	private void openSpecialtyResult(boolean forceToUpdate) {

		boolean panelChangeIsNeeded = true;
		if (currentCenterPanel instanceof SpecialtyPanelStatic) {
			panelChangeIsNeeded = false;
		}

		if (panelChangeIsNeeded || forceToUpdate) {
			specialtyDynamicPanel = new TermPanelDynamic(mainFrameWidth, mainFrameHeight, repositoryService.selectAllSpecialties());
			peto = new PanelEventTransferObject(mainFrameWidth, mainFrameHeight, getHistoryListFromDB(), repositoryService.selectAllSpecialties(), 
					WinUtil.getLanguage(languageBundle), repositoryService.checkLetter(), currentSpecialty, currentTechnicalTerm, specialtyDynamicPanel);
		}
		
		if (panelChangeIsNeeded) {
			changePanelInCenterContainer(specialtyPanel);
		}
		currentSpecialty = null;
		currentTechnicalTerm = null;
	}

	private void openTechnicalTermResult(PanelEventTransferObject peto) {
		int specialtyId = statusBar.getCurrentSpecialtyId();
	}

	private void deleteHistory() {
		historyListService.deleteAllWords();
		menuBar.setHistoryEnable(false);
	}

	private void changeToGerman() {
		if (languageBundle.getLocale().equals(new Locale("de"))) {
			menuBar.setGermanButtonSelected(true);
			menuBar.setSpanishButtonSelected(false);
			updateComponents(peto);
		}
	}

	private void changeToSpanish() {
		if (languageBundle.getLocale().equals(new Locale("es"))) {
			menuBar.setGermanButtonSelected(false);
			menuBar.setSpanishButtonSelected(true);
			updateComponents(peto);
		}
	}

	private void register(Updatable component) {
		updatableComponentList.add(component);
	}

	private void unregister(Updatable component) {
		updatableComponentList.remove(component);
	}

	private void updateComponents(PanelEventTransferObject peto) {
		for (Updatable component : updatableComponentList) {
			component.updatePanel(peto);
		}
	}

	private void changePanelInCenterContainer(MyPanel panel) {
		unregister(currentCenterPanel); // (Updatable) cast?
		register(panel);
		centerContainer.remove(currentCenterPanel);
		currentCenterPanel = panel;
		centerContainer.add(currentCenterPanel);
	}

	private List<String> getHistoryListFromDB() {
		List<History> historyList = historyListService.selectAllWords();
		List<String> historyEntryList = new ArrayList<String>();
		for (History historyEntry : historyList) {
			historyEntryList.add(historyEntry.getWord());
		}
		return historyEntryList;
	}

	private void closeApplication() {
		if (queryExit()) {
			mainFrame.dispose();
			System.exit(0);
		}
	}

	private boolean queryExit() {

		String[] options = { languageBundle.getString("close"), languageBundle.getString("abort") };
		int retValue = JOptionPane.showOptionDialog(mainFrame, languageBundle.getString("queryExitMF"), languageBundle.getString("queryExitTitleMF"), JOptionPane.YES_NO_OPTION,
				JOptionPane.WARNING_MESSAGE, null, options, options[1]);
		if (retValue == JOptionPane.YES_OPTION) {
			return true;
		}
		return false;
	}

	public static void main(String[] args) {
		Presenter presenter = new Presenter();
	}
}
