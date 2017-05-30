package presenter;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JDialog;
import javax.swing.JPanel;

import org.apache.derby.tools.ij;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.eclipse.persistence.internal.jpa.EntityManagerImpl;

import utilities.Queries;
import utilities.WinUtil;
import businessOperations.LanguageBO;
import businessOperations.TermBO;
import businessOperations.TermBOTest;
import businessOperations.TransactionBeginCommit;
import dialogs.AssignmentDialog;
import dialogs.TechnicalTermContentDialog;
import dialogs.TechnicalTermCreationDialog;
import dto.TechnicalTermDTO;
import enums.ComboBoxes;
import enums.Dialogs;
import enums.DynamicPanels;
import enums.StaticPanels;
import eventHandling.LatestDynamicPanelDependencies;
import eventHandling.PanelEventTransferObject;
import eventHandling.Updatable;
import factories.ComboBoxFactory;
import factories.DialogFactory;
import factories.DynamicPanelFactory;
import factories.StaticPanelFactory;
import inputChecker.AssignmentDialogChecker;
import inputChecker.CreationDialogChecker;
import inputChecker.SearchWordChecker;
import interactElements.ChooseSpecialtyComboBox;
import interactElements.SearchComboBox;
import interactElements.SourceButton;
import interactElements.SpecialtyButton;
import interactElements.TermButton;
import mainFrameMainComponents.HeadBar;
import mainFrameMainComponents.MainFrame;
import mainFrameMainComponents.MenuBar;
import mainFrameMainComponents.StatusBar;
import model.History;
import model.Specialty;
import model.TechnicalTerm;
import model.Translations;
import panels.TechnicalTermPanelStatic;
import panels.TermPanelDynamic;
import panels.StaticPanel;
import panels.SearchResultPanelDynamic;
import panels.SearchResultPanelStatic;
import panels.SpecialtyPanelStatic;
import repository.HistoryDAO;
import repository.LanguageDAO;
import repository.TermDAO;

public class Presenter {

	private IDatabaseConnection mDBUnitConnection;
	private IDataSet startDataset;
	private EntityManagerFactory emfactory;
	private EntityManager entitymanager;
	private TransactionBeginCommit repositoryTA;

	private ComboBoxFactory comboBoxFactory;
	private StaticPanelFactory staticPanelFactory;
	private DynamicPanelFactory dynamicPanelFactory;
	private DialogFactory dialogFactory;

	private SearchWordChecker searchWordChecker;
	private CreationDialogChecker creationDialogChecker;
	private AssignmentDialogChecker assignmentDialogChecker;

	private SearchComboBox searchComboBox;
	private ChooseSpecialtyComboBox chooseSpecialtyComboBoxGerman;
	private ChooseSpecialtyComboBox chooseSpecialtyComboBoxSpanish;
	private ChooseSpecialtyComboBox assignSpecialtyComboBox;

	private SpecialtyActionListener specialtyActionListener;
	private SearchActionListener searchActionListener;
	private TechnicalTermActionListener technicalTermActionListener;

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

	private final int GERMAN = 1;
	private final int SPANISH = 2;
	private final double MAINFRAME_DISPLAY_RATIO = 0.8;

	private Specialty currentSpecialty;
	private TechnicalTerm currentTechnicalTerm;
	private String searchWord;

	private PanelEventTransferObject peto;
	private StaticPanel currentCenterPanel;
	private StaticPanels currentStaticPanelType;
	private Container centerContainer;
	
	private LatestDynamicPanelDependencies latestDynamicPanelDependencies;

	public Presenter() throws DataSetException, FileNotFoundException, SQLException, IOException {

		initializeDataBase();
		initializeAppSettings();
		initializeFactories();
		initializeInputChecker();
		initializeComboBoxes();
		initializeActionListener();
		initializeContentPanePanels();
		initializeMainFrameComponents();
		initializeMainFrame();

		peto = createPetoForNonPanelChanges();
		updateComponents(peto);

		mainFrame.setVisible(true);
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
			startDataset = new FlatXmlDataSetBuilder().build(new File("./src/main/resources/DatenbankContent.xml"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		TermDAO termDAOService = new TermDAO(entitymanager);
		LanguageDAO languageDAOSerive = new LanguageDAO(entitymanager);
		LanguageBO languageBOService = new LanguageBO(languageDAOSerive);
		TermBO repositoryService = new TermBO(languageBOService, termDAOService);
		HistoryDAO historyListService = new HistoryDAO(entitymanager);
		repositoryTA = new TransactionBeginCommit(entitymanager, repositoryService, historyListService);

		try {
			DatabaseOperation.CLEAN_INSERT.execute(mDBUnitConnection, startDataset);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void closeAndStoreDataBase() throws SQLException, DataSetException, FileNotFoundException, IOException {
		IDataSet fullDataSet = mDBUnitConnection.createDataSet();
		FlatXmlDataSet.write(fullDataSet, new FileOutputStream("./src/main/resources/DatenbankContent.xml"));
		mDBUnitConnection.close();
		entitymanager.close();
		emfactory.close();
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
		searchWord = null;
		latestDynamicPanelDependencies = new LatestDynamicPanelDependencies();
	}

	private void initializeFactories() {
		comboBoxFactory = new ComboBoxFactory();
		staticPanelFactory = new StaticPanelFactory();
		dynamicPanelFactory = new DynamicPanelFactory();
		dialogFactory = new DialogFactory();
	}

	private void initializeInputChecker() {
		searchWordChecker = new SearchWordChecker();
		creationDialogChecker = new CreationDialogChecker();
		assignmentDialogChecker = new AssignmentDialogChecker();
	}

	private void initializeComboBoxes() {
		searchComboBox = (SearchComboBox) comboBoxFactory.createComboBox(ComboBoxes.SEARCH_COMBOBOX, getHistoryListFromDB(), searchWordChecker);
		searchComboBox.setSearchComboBoxActionListener(e -> openSearchResultPanel(searchComboBox.getSearchWord()));
		chooseSpecialtyComboBoxGerman = (ChooseSpecialtyComboBox) comboBoxFactory.createComboBox(ComboBoxes.GERMAN_SPECIALTY_COMBOBOX, languageBundle,
				repositoryTA.selectAllSpecialties());
		chooseSpecialtyComboBoxSpanish = (ChooseSpecialtyComboBox) comboBoxFactory.createComboBox(ComboBoxes.SPANISH_SPECIALTY_COMBOBOX, languageBundle,
				repositoryTA.selectAllSpecialties());
		assignSpecialtyComboBox = (ChooseSpecialtyComboBox) comboBoxFactory.createComboBox(ComboBoxes.SPECIALTY_COMBOBOX, languageBundle, repositoryTA.selectAllSpecialties());

		register(searchComboBox);
	}

	private void initializeActionListener() {
		specialtyActionListener = new SpecialtyActionListener();
		searchActionListener = new SearchActionListener();
		technicalTermActionListener = new TechnicalTermActionListener();
	}

	private void initializeContentPanePanels() {
		initializeDynamicPanels();
		initializeStaticPanels();
	}

	private void initializeDynamicPanels() {
		technicalTermDynamicPanel = dynamicPanelFactory.createPanel(DynamicPanels.TECHNICAL_TERM_PANEL, null, technicalTermActionListener, mainFrameWidth, mainFrameHeight,
				"SpecialtyName", repositoryTA.selectAllTechnicalTermsOfSpecialty(3));

		latestDynamicPanelDependencies.setSpecialtyList(repositoryTA.selectAllSpecialties());
		specialtyDynamicPanel = dynamicPanelFactory.createPanel(DynamicPanels.SPECIALTY_PANEL, null, specialtyActionListener, mainFrameWidth, mainFrameHeight,
				latestDynamicPanelDependencies.getSpecialtyList());
		
		
		searchDynamicPanel = dynamicPanelFactory.createPanel(DynamicPanels.SEARCH_RESULT_PANEL, null, searchActionListener, mainFrameWidth, mainFrameHeight,
				repositoryTA.searchTechnicalTerms("a%"), "Hallo Suche");

		letterDynamicPanel = dynamicPanelFactory.createPanel(DynamicPanels.LETTER_RESULT_PANEL, null, searchActionListener, mainFrameWidth, mainFrameHeight,
				repositoryTA.searchTechnicalTerms("A%"), ".A%");
	}

	private void initializeStaticPanels() {

		technicalTermPanel = (TechnicalTermPanelStatic) staticPanelFactory.createPanel(StaticPanels.TECHNICAL_TERM_PANEL, languageBundle, MAINFRAME_DISPLAY_RATIO,
				technicalTermDynamicPanel);
		specialtyPanel = (SpecialtyPanelStatic) staticPanelFactory.createPanel(StaticPanels.SPECIALTY_PANEL, languageBundle, MAINFRAME_DISPLAY_RATIO, specialtyDynamicPanel);
		
		searchPanel = (SearchResultPanelStatic) staticPanelFactory.createPanel(StaticPanels.SEARCH_RESULT_PANEL, languageBundle, MAINFRAME_DISPLAY_RATIO, searchDynamicPanel);

		letterPanel = (SearchResultPanelStatic) staticPanelFactory.createPanel(StaticPanels.LETTER_RESULT_PANEL, languageBundle, MAINFRAME_DISPLAY_RATIO, letterDynamicPanel);

		register(specialtyPanel);
		currentCenterPanel = specialtyPanel;
		currentStaticPanelType = currentCenterPanel.getStaticPanelType();
		centerContainer.add(currentCenterPanel);
	}

	private void initializeMainFrameComponents() {

		initializeMenuBar();
		initializeHeadBar();
		initializeStatusBar();
	}

	private void initializeMenuBar() {

		menuBar = new MenuBar(languageBundle);
		menuBar.setMiExitActionListener(e -> {
			try {
				closeApplication();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		});
		menuBar.setMiNewActionListener(e -> openTechnicalTermCreationDialog(languageBundle, chooseSpecialtyComboBoxGerman, chooseSpecialtyComboBoxSpanish));
		menuBar.setMiAssignActionListener(e -> openAssignmentDialog(languageBundle, assignSpecialtyComboBox));
		menuBar.setMiHistoryActionListener(e -> deleteHistory());
		menuBar.setMiGermanActionListener(e -> changeToGerman());
		menuBar.setMiSpanishActionListener(e -> changeToSpanish());
		register(menuBar);
	}

	private void initializeHeadBar() {

		headBar = new HeadBar(MAINFRAME_DISPLAY_RATIO, languageBundle, searchComboBox, repositoryTA.checkLetter());
		headBar.setSearchButtonActionListener(e -> openSearchResultPanel(searchComboBox.getSearchWord()));
		headBar.setAlphabetButtonActionListener(e -> openLetterResultPanel(e));
		headBar.setSpecialtyButtonActionListener(e -> openSpecialtyPanel());
		headBar.setDeButtonActionListener(e -> changeToGerman());
		headBar.setEsButtonActionListener(e -> changeToSpanish());
		headBar.setNewTechnicalTermButtonActionListener(e -> openTechnicalTermCreationDialog(languageBundle, chooseSpecialtyComboBoxGerman, chooseSpecialtyComboBoxSpanish));
		register(headBar);
	}

	private void initializeStatusBar() {

		statusBar = new StatusBar(languageBundle);
		statusBar.setStatusBarStartpageButtonActionListener(e -> openSpecialtyPanel());
		statusBar.setStatusBarSpecialtyButtonActionListener(e -> openTechnicalTermPanel(currentSpecialty.getId()));
		register(statusBar);
	}

	private void initializeMainFrame() {

		mainFrame = new MainFrame(MAINFRAME_DISPLAY_RATIO, languageBundle);
		mainFrame.setJMenuBar(menuBar);
		mainFrame.add(headBar, BorderLayout.PAGE_START);
		mainFrame.add(centerContainer, BorderLayout.CENTER);
		mainFrame.add(statusBar, BorderLayout.PAGE_END);
		mainFrame.setMainFrameComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				Component component = e.getComponent();
				createResizePeto(component);
			}
		});
		mainFrame.setMainFrameWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					closeApplication();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		register(mainFrame);
	}
	
	private void createResizePeto(Component component) {
		mainFrameWidth = component.getWidth();
		mainFrameHeight = component.getHeight();
		peto = createPetoForResize();
		updateComponents(peto);
	}

	private void decideButtonIdentity(ActionEvent e) {

		int termId = ((TermButton) e.getSource()).getTermId();
		if (e.getSource() instanceof SpecialtyButton) {
			openTechnicalTermPanel(termId);
		} else {
			openTechnicalTermContentDialog(languageBundle, e);
		}
	}

	private void openSearchResultPanel(String newSearchWord) {

		repositoryTA.insertSearchWordToHistoryList(newSearchWord);
		boolean panelChangeIsNeeded = true;
		boolean newSearchResult = true;

		if (currentStaticPanelType.equals(StaticPanels.SEARCH_RESULT_PANEL)) {
			panelChangeIsNeeded = false;
			if (searchWord.equals(newSearchWord)) {
				newSearchResult = false;
			}
		}

		currentSpecialty = null;
		currentTechnicalTerm = null;
		searchWord = newSearchWord;
		if (newSearchResult) {

			latestDynamicPanelDependencies.setSearchTechnicalTermsList(repositoryTA.searchTechnicalTerms(searchWord));
			searchDynamicPanel = dynamicPanelFactory.createPanel(DynamicPanels.SEARCH_RESULT_PANEL, searchDynamicPanel, searchActionListener, mainFrameWidth, mainFrameHeight,
					latestDynamicPanelDependencies.getSearchTechnicalTermsList(), searchWord);
			peto = new PanelEventTransferObject(mainFrameWidth, mainFrameHeight, getHistoryListFromDB(), repositoryTA.selectAllSpecialties(), WinUtil.getLanguage(languageBundle),
					repositoryTA.checkLetter(), currentSpecialty, currentTechnicalTerm, searchDynamicPanel);

			if (panelChangeIsNeeded) {
				changePanelInCenterContainer(searchPanel);
			}
			updateComponents(peto);
		}
	}

	private void openLetterResultPanel(ActionEvent e) {

		String newSearchWord = e.getActionCommand();
		boolean panelChangeIsNeeded = true;
		boolean newLetterResult = true;

		if (currentStaticPanelType.equals(StaticPanels.LETTER_RESULT_PANEL)) {
			panelChangeIsNeeded = false;
			if (searchWord.equals(newSearchWord)) {
				newLetterResult = false;
			}
		}

		currentSpecialty = null;
		currentTechnicalTerm = null;
		searchWord = newSearchWord;
		if (newLetterResult) {

			latestDynamicPanelDependencies.setSearchTechnicalTermsList(repositoryTA.searchTechnicalTerms(searchWord));
			letterDynamicPanel = dynamicPanelFactory.createPanel(DynamicPanels.LETTER_RESULT_PANEL, letterDynamicPanel, searchActionListener, mainFrameWidth, mainFrameHeight,
					latestDynamicPanelDependencies.getSearchTechnicalTermsList(), "." + searchWord);
			peto = new PanelEventTransferObject(mainFrameWidth, mainFrameHeight, getHistoryListFromDB(), repositoryTA.selectAllSpecialties(), WinUtil.getLanguage(languageBundle),
					repositoryTA.checkLetter(), currentSpecialty, currentTechnicalTerm, letterDynamicPanel);

			if (panelChangeIsNeeded) {
				changePanelInCenterContainer(letterPanel);
			}
			updateComponents(peto);
		}
	}

	private void deleteHistory() {
		repositoryTA.deleteHistory();
		menuBar.setHistoryEnable(false);
		peto = createPetoForNonPanelChanges();
		updateComponents(peto);
	}

	private void changeToGerman() {
		if (WinUtil.getLanguageId(languageBundle) == SPANISH) {
			languageBundle = ResourceBundle.getBundle("languageBundles.lexikon", new Locale("de"));
			peto = createPetoForNonPanelChanges();
			updateComponents(peto);
		}
	}

	private void changeToSpanish() {
		if (WinUtil.getLanguageId(languageBundle) == GERMAN) {
			languageBundle = ResourceBundle.getBundle("languageBundles.lexikon", new Locale("es"));
			peto = createPetoForNonPanelChanges();
			updateComponents(peto);
		}
	}

	private void openSpecialtyPanel() {

		boolean panelChangeIsNeeded = true;
		if (currentStaticPanelType.equals(StaticPanels.SPECIALTY_PANEL)) {
			panelChangeIsNeeded = false;
		}

		currentSpecialty = null;
		currentTechnicalTerm = null;
		if (panelChangeIsNeeded) {
			latestDynamicPanelDependencies.setSpecialtyList(repositoryTA.selectAllSpecialties());
			specialtyDynamicPanel = dynamicPanelFactory.createPanel(DynamicPanels.SPECIALTY_PANEL, specialtyDynamicPanel, specialtyActionListener, mainFrameWidth, mainFrameHeight,
					latestDynamicPanelDependencies.getSpecialtyList());
			peto = new PanelEventTransferObject(mainFrameWidth, mainFrameHeight, getHistoryListFromDB(), repositoryTA.selectAllSpecialties(), WinUtil.getLanguage(languageBundle),
					repositoryTA.checkLetter(), currentSpecialty, currentTechnicalTerm, specialtyDynamicPanel);

			changePanelInCenterContainer(specialtyPanel);
			updateComponents(peto);
		}
	}

	private void openTechnicalTermPanel(int specialtyId) {

		boolean panelChangeIsNeeded = true;
		if (currentStaticPanelType.equals(StaticPanels.TECHNICAL_TERM_PANEL)) {
			panelChangeIsNeeded = false;
		}

		currentSpecialty = repositoryTA.selectSpecialtyById(specialtyId);
		currentTechnicalTerm = null;

		if (panelChangeIsNeeded) {
			latestDynamicPanelDependencies.setTechnicalTermsOfSpecialtyList(repositoryTA.selectAllTechnicalTermsOfSpecialty(specialtyId));
			technicalTermDynamicPanel = dynamicPanelFactory.createPanel(DynamicPanels.TECHNICAL_TERM_PANEL, technicalTermDynamicPanel, technicalTermActionListener, mainFrameWidth,
					mainFrameHeight, getSpecialtyName(currentSpecialty), latestDynamicPanelDependencies.getTechnicalTermsOfSpecialtyList());
			peto = new PanelEventTransferObject(mainFrameWidth, mainFrameHeight, getHistoryListFromDB(), repositoryTA.selectAllSpecialties(), WinUtil.getLanguage(languageBundle),
					repositoryTA.checkLetter(), currentSpecialty, currentTechnicalTerm, technicalTermDynamicPanel);

			changePanelInCenterContainer(technicalTermPanel);
			updateComponents(peto);
		}
	}

	public void openTechnicalTermCreationDialog(ResourceBundle languageBundle, ChooseSpecialtyComboBox chooseSpecialtyComboBoxGerman,
			ChooseSpecialtyComboBox chooseSpecialtyComboBoxSpanish) {

		register(chooseSpecialtyComboBoxGerman);
		register(chooseSpecialtyComboBoxSpanish);
		peto = createPetoForNonPanelChanges();
		updateComponents(peto);

		dialogFactory.createDialog(Dialogs.TECHNICAL_TERM_CREATION_DIALOG, languageBundle, chooseSpecialtyComboBoxGerman, chooseSpecialtyComboBoxSpanish, 
				new CreationDialogWindowListener(), new CreationDialogActionListener(), new CreationDialogKeyListener());
	}

	private void saveNewTechnicalTerm(ResourceBundle languageBundle, ChooseSpecialtyComboBox germanSpecialtyComboBox, ChooseSpecialtyComboBox spanishSpecialtyComboBox,
			TechnicalTermCreationDialog creationDialog) {
		
		int specialtyId = 0;
		if (creationDialog.isNewSpecialtySelected()) {
			specialtyId = createNewSpecialty(creationDialog);

		} else {
			if (WinUtil.getLanguageId(languageBundle) == GERMAN) {
				specialtyId = germanSpecialtyComboBox.getSelectedListItem().getValueMember();
			}
			if (WinUtil.getLanguageId(languageBundle) == SPANISH) {
				specialtyId = spanishSpecialtyComboBox.getSelectedListItem().getValueMember();
			}
		}
		createNewTechnicalTerm(creationDialog, specialtyId);
	}
	
	private int createNewSpecialty(TechnicalTermCreationDialog creationDialog) {
		
		Specialty newSpecialty = repositoryTA.createSpecialty(creationDialog.getGermanSpecialtyInput().getText(), "", GERMAN);
		int specialtyId = newSpecialty.getId();
		repositoryTA.createSpecialtyTranslation(specialtyId, creationDialog.getSpanishSpecialtyInput().getText(), "", SPANISH);
		return specialtyId;
	}
	
	private void createNewTechnicalTerm(TechnicalTermCreationDialog creationDialog, int specialtyId) {
		
		int technicalTermId = repositoryTA.createTechnicalTerm(creationDialog.getGermanTextField().getText(), creationDialog.getGermanTextArea().getText(), GERMAN, specialtyId).getId();
		repositoryTA.createTechnicalTermTranslation(technicalTermId, creationDialog.getSpanishTextField().getText(), creationDialog.getSpanishTextArea().getText(), SPANISH);
	}

	public void openAssignmentDialog(ResourceBundle languageBundle, ChooseSpecialtyComboBox assignSpecialtyComboBox) {

		register(assignSpecialtyComboBox);
		peto = createPetoForNonPanelChanges();
		updateComponents(peto);

		List<TechnicalTerm> technicalTermList = repositoryTA.selectAllTechnicalTerms();

		dialogFactory.createDialog(Dialogs.ASSIGNMENT_DIALOG, languageBundle, technicalTermList, assignSpecialtyComboBox, 
				new AssignmentDialogWindowListener(), new AssignmentDialogActionListener(), new AssignmentDialogKeyListener());
	}

	public void changeButtonPressed(ChooseSpecialtyComboBox specialtyComboBox, AssignmentDialog newAssignDialog) {

		int specialtyId;
		if (newAssignDialog.isNewSpecialtySelected()) {
			specialtyId = createNewSpecialty(newAssignDialog);
		} else {
			specialtyId = specialtyComboBox.getSelectedListItem().getValueMember();
		}
		repositoryTA.assignTechnicalTermsToSpecialty(newAssignDialog.getTechnicalTermIds(), specialtyId);
		newAssignDialog.refreshAssignmentTableModel(repositoryTA.selectAllTechnicalTerms());
	}
	
	private int createNewSpecialty(AssignmentDialog newAssignDialog) {
		
		Specialty newSpecialty = repositoryTA.createSpecialty(newAssignDialog.getGermanSpecialtyInput().getText(), "", GERMAN);
		int specialtyId = newSpecialty.getId();
		repositoryTA.createSpecialtyTranslation(specialtyId, newAssignDialog.getSpanishSpecialtyInput().getText(), "", SPANISH);
		return specialtyId;
	}

	public void openTechnicalTermContentDialog(ResourceBundle languageBundle, ActionEvent event) {

		int technicalTermId = ((TermButton) event.getSource()).getTermId();
		TechnicalTerm technicalTerm = repositoryTA.selectTechnicalTermById(technicalTermId);

		currentSpecialty = repositoryTA.selectSpecialtyById(technicalTerm.getSpecialty().getId());
		currentTechnicalTerm = technicalTerm;
		
		peto = createPetoForNonPanelChanges();
		updateComponents(peto);

		dialogFactory.createDialog(Dialogs.TECHNICAL_TERM_CONTENT_DIALOG, languageBundle, technicalTerm,
				new ContentDialogWindowListener(), new ContentDialogActionListener(technicalTerm));
	}

	private void saveNewTechnicalTermDescription(TechnicalTermContentDialog contentTTDialog, TechnicalTerm technicalTerm) {
		if (Queries.querySaveContentDialog(languageBundle, contentTTDialog.isTextHasChangedInTextAreas(), contentTTDialog)) {
			TechnicalTermDTO technicalTermDTO = new TechnicalTermDTO(technicalTerm);
			repositoryTA.updateTranslation(technicalTerm.getId(), technicalTermDTO.getGermanName(), contentTTDialog.getGermanTextAreaText(), GERMAN);
			repositoryTA.updateTranslation(technicalTerm.getId(), technicalTermDTO.getSpanishName(), contentTTDialog.getSpanishTextAreaText(), SPANISH);
			contentTTDialog.saveChangesEditMode();
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

	private void changePanelInCenterContainer(StaticPanel panel) {
		unregister(currentCenterPanel);
		register(panel);
		centerContainer.remove(currentCenterPanel);
		currentCenterPanel = panel;
		currentStaticPanelType = currentCenterPanel.getStaticPanelType();
		centerContainer.add(currentCenterPanel);
		centerContainer.validate();
		centerContainer.repaint();
	}

	private List<String> getHistoryListFromDB() {
		List<History> historyList = repositoryTA.getAllSearchWordsFromHistory();
		List<String> historyEntryList = new ArrayList<String>();
		for (History historyEntry : historyList) {
			historyEntryList.add(historyEntry.getWord());
		}
		return historyEntryList;
	}

	private String getSpecialtyName(Specialty specialty) {

		String name = null;
		List<Translations> translationList = specialty.getTranslationList();
		for (Translations translation : translationList) {
			if (translation.getLanguages().getId() == WinUtil.getLanguageId(languageBundle)) {
				name = translation.getName();
			}
		}
		return name;
	}

	private PanelEventTransferObject createPetoForNonPanelChanges() {

		switch (currentStaticPanelType) {

		case SPECIALTY_PANEL:

			specialtyDynamicPanel = dynamicPanelFactory.createPanel(DynamicPanels.SPECIALTY_PANEL, specialtyDynamicPanel, specialtyActionListener, mainFrameWidth, mainFrameHeight,
					repositoryTA.selectAllSpecialties());
			return new PanelEventTransferObject(mainFrameWidth, mainFrameHeight, getHistoryListFromDB(), repositoryTA.selectAllSpecialties(), WinUtil.getLanguage(languageBundle),
					repositoryTA.checkLetter(), currentSpecialty, currentTechnicalTerm, specialtyDynamicPanel);

		case LETTER_RESULT_PANEL:

			letterDynamicPanel = dynamicPanelFactory.createPanel(DynamicPanels.LETTER_RESULT_PANEL, letterDynamicPanel, searchActionListener, mainFrameWidth, mainFrameHeight,
					repositoryTA.searchTechnicalTerms(searchWord), "." + searchWord);
			return new PanelEventTransferObject(mainFrameWidth, mainFrameHeight, getHistoryListFromDB(), repositoryTA.selectAllSpecialties(), WinUtil.getLanguage(languageBundle),
					repositoryTA.checkLetter(), currentSpecialty, currentTechnicalTerm, letterDynamicPanel);

		case SEARCH_RESULT_PANEL:

			searchDynamicPanel = dynamicPanelFactory.createPanel(DynamicPanels.SEARCH_RESULT_PANEL, searchDynamicPanel, searchActionListener, mainFrameWidth, mainFrameHeight,
					repositoryTA.searchTechnicalTerms(searchWord), searchWord);
			return new PanelEventTransferObject(mainFrameWidth, mainFrameHeight, getHistoryListFromDB(), repositoryTA.selectAllSpecialties(), WinUtil.getLanguage(languageBundle),
					repositoryTA.checkLetter(), currentSpecialty, currentTechnicalTerm, searchDynamicPanel);

		case TECHNICAL_TERM_PANEL:

			technicalTermDynamicPanel = dynamicPanelFactory.createPanel(DynamicPanels.TECHNICAL_TERM_PANEL, technicalTermDynamicPanel, technicalTermActionListener, mainFrameWidth,
					mainFrameHeight, getSpecialtyName(currentSpecialty), repositoryTA.selectAllTechnicalTermsOfSpecialty(currentSpecialty.getId()));
			return new PanelEventTransferObject(mainFrameWidth, mainFrameHeight, getHistoryListFromDB(), repositoryTA.selectAllSpecialties(), WinUtil.getLanguage(languageBundle),
					repositoryTA.checkLetter(), currentSpecialty, currentTechnicalTerm, technicalTermDynamicPanel);

		default:
			throw new RuntimeException("Kein gültiges CenterPanel");
		}
	}
	
	private PanelEventTransferObject createPetoForResize() {

		switch (currentStaticPanelType) {

		case SPECIALTY_PANEL:

			specialtyDynamicPanel = dynamicPanelFactory.createPanel(DynamicPanels.SPECIALTY_PANEL, specialtyDynamicPanel, specialtyActionListener, mainFrameWidth, mainFrameHeight,
					latestDynamicPanelDependencies.getSpecialtyList());
			peto.resizeEvent(specialtyDynamicPanel, mainFrameWidth, mainFrameHeight);
			return peto;

		case LETTER_RESULT_PANEL:

			letterDynamicPanel = dynamicPanelFactory.createPanel(DynamicPanels.LETTER_RESULT_PANEL, letterDynamicPanel, searchActionListener, mainFrameWidth, mainFrameHeight,
					latestDynamicPanelDependencies.getSearchTechnicalTermsList(), "." + searchWord);
			peto.resizeEvent(letterDynamicPanel, mainFrameWidth, mainFrameHeight);
			return peto;

		case SEARCH_RESULT_PANEL:

			searchDynamicPanel = dynamicPanelFactory.createPanel(DynamicPanels.SEARCH_RESULT_PANEL, searchDynamicPanel, searchActionListener, mainFrameWidth, mainFrameHeight,
					latestDynamicPanelDependencies.getSearchTechnicalTermsList(), searchWord);
			peto.resizeEvent(searchDynamicPanel, mainFrameWidth, mainFrameHeight);
			return peto;

		case TECHNICAL_TERM_PANEL:

			technicalTermDynamicPanel = dynamicPanelFactory.createPanel(DynamicPanels.TECHNICAL_TERM_PANEL, technicalTermDynamicPanel, technicalTermActionListener, mainFrameWidth,
					mainFrameHeight, getSpecialtyName(currentSpecialty), latestDynamicPanelDependencies.getTechnicalTermsOfSpecialtyList());
			peto.resizeEvent(technicalTermDynamicPanel, mainFrameWidth, mainFrameHeight);
			return peto;

		default:
			throw new RuntimeException("Kein gültiges CenterPanel");
		}
	}
	

	class ContentDialogWindowListener extends WindowAdapter {

		@Override
		public void windowClosing(WindowEvent e) {
			JDialog dialog = (JDialog) e.getSource();
			dialog.dispose();
			currentTechnicalTerm = null;
			if (!(currentStaticPanelType.equals(StaticPanels.TECHNICAL_TERM_PANEL))) {
				currentSpecialty = null;
			}
			peto = createPetoForNonPanelChanges();
			updateComponents(peto);
		}
	}

	class ContentDialogActionListener implements ActionListener {

		TechnicalTerm technicalTerm;

		public ContentDialogActionListener(TechnicalTerm technicalTerm) {
			this.technicalTerm = technicalTerm;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			TechnicalTermContentDialog contentDialog = (TechnicalTermContentDialog) ((SourceButton) e.getSource()).getCurrentDialog();
			saveNewTechnicalTermDescription(contentDialog, technicalTerm);
		}
	}

	class AssignmentDialogWindowListener extends WindowAdapter {

		@Override
		public void windowClosing(WindowEvent e) {
			JDialog dialog = (JDialog) e.getSource();
			dialog.dispose();
			unregister(assignSpecialtyComboBox);
			peto = createPetoForNonPanelChanges();
			updateComponents(peto);
		}
	}

	class AssignmentDialogKeyListener extends KeyAdapter {

		@Override
		public void keyTyped(KeyEvent e) {
			assignmentDialogChecker.keyTypedChecker(e);
		}

		@Override
		public void keyPressed(KeyEvent e) {
			assignmentDialogChecker.keyPressedChecker(e);
		}
	}

	class AssignmentDialogActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			AssignmentDialog assignmentDialog = (AssignmentDialog) ((SourceButton) e.getSource()).getCurrentDialog();

			assignmentDialogChecker.checkDialog(assignmentDialog, repositoryTA);
			if (assignmentDialogChecker.isTestPassed()) {
				changeButtonPressed(assignSpecialtyComboBox, assignmentDialog);
			}
		}
	}

	class CreationDialogWindowListener extends WindowAdapter {

		@Override
		public void windowClosing(WindowEvent e) {
			TechnicalTermCreationDialog creationDialog = (TechnicalTermCreationDialog) e.getSource();
			closeCreationDialog(creationDialog);
		}
	}

	class CreationDialogKeyListener extends KeyAdapter {

		@Override
		public void keyTyped(KeyEvent e) {
			creationDialogChecker.keyTypedChecker(e);
		}

		@Override
		public void keyPressed(KeyEvent e) {
			creationDialogChecker.keyPressedChecker(e);
		}
	}

	class CreationDialogActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			TechnicalTermCreationDialog creationDialog = (TechnicalTermCreationDialog) ((SourceButton) e.getSource()).getCurrentDialog();

			creationDialogChecker.checkDialog(creationDialog, repositoryTA);
			if (creationDialogChecker.isTestPassed()) {
				if (Queries.queryCreateNewTT(languageBundle, creationDialog)) {
				saveNewTechnicalTerm(languageBundle, chooseSpecialtyComboBoxGerman, chooseSpecialtyComboBoxSpanish, creationDialog);
				closeCreationDialog(creationDialog);
				}
			}
		}
	}
	
	private void closeCreationDialog(TechnicalTermCreationDialog creationDialog) {
		creationDialog.removeItemListenerFromComboBoxes();
		creationDialog.dispose();
		unregister(chooseSpecialtyComboBoxGerman);
		unregister(chooseSpecialtyComboBoxSpanish);
		peto = createPetoForNonPanelChanges();
		updateComponents(peto);
	}

	class SpecialtyActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			openTechnicalTermPanel(((TermButton) e.getSource()).getTermId());
		}
	}

	class SearchActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			decideButtonIdentity(e);
		}
	}

	class TechnicalTermActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			openTechnicalTermContentDialog(languageBundle, e);
		}
	}

	private void closeApplication() throws DataSetException, FileNotFoundException, SQLException, IOException {

		if (Queries.queryExit(languageBundle, mainFrame)) {
			mainFrame.dispose();
			closeAndStoreDataBase();
			System.exit(0);
		}
	}
	
	public static void main(String[] args) throws DataSetException, FileNotFoundException, SQLException, IOException {
		new Presenter();
	}
}
