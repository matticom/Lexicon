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
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import org.apache.derby.tools.ij;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.eclipse.persistence.internal.jpa.EntityManagerImpl;

import utilities.Queries;
import utilities.WinUtil;
import viewFactory.DialogCreator;
import viewFactory.DynamicPanelCreator;
import viewFactory.HeadBar;
import viewFactory.MainFrame;
import viewFactory.MenuBar;
import viewFactory.StaticPanelCreator;
import viewFactory.StatusBar;
import businessOperations.LanguageBO;
import businessOperations.TermBO;
import businessOperations.TermBOTest;
import businessOperations.TransactionBeginCommit;
import dialogs.AssignmentDialog;
import dialogs.TechnicalTermContentDialog;
import dialogs.TechnicalTermCreationDialog;
import dto.TechnicalTermDTO;
import enums.ComboBoxes;
import enums.DialogWindows;
import enums.DynamicPanels;
import enums.StaticPanels;
import eventHandling.PanelEventTransferObject;
import eventHandling.Updatable;
import inputChecker.AssignmentDialogChecker;
import inputChecker.CreationDialogChecker;
import inputChecker.SearchWordChecker;
import interactElements.ChooseSpecialtyComboBox;
import interactElements.ComboBoxCellRenderer;
import interactElements.ComboBoxFactory;
import interactElements.SearchComboBox;
import interactElements.SourceButton;
import interactElements.SpecialtyButton;
import interactElements.TermButton;
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
	private StaticPanelCreator staticPanelCreator;
	private DynamicPanelCreator dynamicPanelCreator;
	private DialogCreator dialogCreator;

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
	private StaticPanels staticPanelType;
	private Container centerContainer;

	public Presenter() {

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

	private void closeAndStoreDataBase() {

		try {
			IDataSet fullDataSet = mDBUnitConnection.createDataSet();
			FlatXmlDataSet.write(fullDataSet, new FileOutputStream("./src/main/resources/DatenbankContent.xml"));

		} catch (Exception e) {
			System.out.println("Es wurde eine Exception beim speichern der Datenbank geworfen: " + e.getMessage());
			e.printStackTrace();
		}
		try {
			mDBUnitConnection.close();
		} catch (SQLException e) {
			System.out.println("Es wurde eine Exception beim Schließen der IDataConnection geworfen: " + e.getMessage());
			e.printStackTrace();
		}
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
	}

	private void initializeFactories() {

		comboBoxFactory = new ComboBoxFactory();
		staticPanelCreator = new StaticPanelCreator();
		dynamicPanelCreator = new DynamicPanelCreator();
		dialogCreator = new DialogCreator();
	}

	private void initializeInputChecker() {

		searchWordChecker = new SearchWordChecker();
		creationDialogChecker = new CreationDialogChecker();
		assignmentDialogChecker = new AssignmentDialogChecker();
	}

	private void initializeComboBoxes() {

		searchComboBox = (SearchComboBox) comboBoxFactory.createComboBox(ComboBoxes.SearchComboBox, getHistoryListFromDB(), searchWordChecker);
		chooseSpecialtyComboBoxGerman = (ChooseSpecialtyComboBox) comboBoxFactory.createComboBox(ComboBoxes.GermanSpecialtyComboBox, languageBundle,
				repositoryTA.selectAllSpecialties());
		chooseSpecialtyComboBoxSpanish = (ChooseSpecialtyComboBox) comboBoxFactory.createComboBox(ComboBoxes.SpanishSpecialtyComboBox, languageBundle,
				repositoryTA.selectAllSpecialties());
		assignSpecialtyComboBox = (ChooseSpecialtyComboBox) comboBoxFactory.createComboBox(ComboBoxes.SpecialtyComboBox, languageBundle, repositoryTA.selectAllSpecialties());

		ListCellRenderer<Object> fontSizeRenderer = new ComboBoxCellRenderer();
		searchComboBox.setRenderer(fontSizeRenderer);

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

		technicalTermDynamicPanel = dynamicPanelCreator.createPanel(DynamicPanels.TechnicalTermPanel, null, technicalTermActionListener, mainFrameWidth, mainFrameHeight,
				"SpecialtyName", repositoryTA.selectAllTechnicalTermsOfSpecialty(3));

		specialtyDynamicPanel = dynamicPanelCreator.createPanel(DynamicPanels.SpecialtyPanel, null, specialtyActionListener, mainFrameWidth, mainFrameHeight,
				repositoryTA.selectAllSpecialties());

		searchDynamicPanel = dynamicPanelCreator.createPanel(DynamicPanels.SearchResultPanel, null, searchActionListener, mainFrameWidth, mainFrameHeight,
				repositoryTA.searchTechnicalTerms("a%"), "Hallo Suche");

		letterDynamicPanel = dynamicPanelCreator.createPanel(DynamicPanels.LetterResultPanel, null, searchActionListener, mainFrameWidth, mainFrameHeight,
				repositoryTA.searchTechnicalTerms("A%"), ".A%");
	}

	private void initializeStaticPanels() {

		technicalTermPanel = (TechnicalTermPanelStatic) staticPanelCreator.createPanel(StaticPanels.TechnicalTermPanel, languageBundle, MAINFRAME_DISPLAY_RATIO,
				technicalTermDynamicPanel);
		specialtyPanel = (SpecialtyPanelStatic) staticPanelCreator.createPanel(StaticPanels.SpecialtyPanel, languageBundle, MAINFRAME_DISPLAY_RATIO, specialtyDynamicPanel);
		searchPanel = (SearchResultPanelStatic) staticPanelCreator.createPanel(StaticPanels.SearchResultPanel, languageBundle, MAINFRAME_DISPLAY_RATIO, searchDynamicPanel);

		letterPanel = (SearchResultPanelStatic) staticPanelCreator.createPanel(StaticPanels.LetterResultPanel, languageBundle, MAINFRAME_DISPLAY_RATIO, letterDynamicPanel);

		register(specialtyPanel);
		currentCenterPanel = specialtyPanel;
		staticPanelType = currentCenterPanel.getStaticPanelType();
		centerContainer.add(currentCenterPanel);
	}

	private void initializeMainFrameComponents() {

		initializeMenuBar();
		initializeHeadBar();
		initializeStatusBar();
	}

	private void initializeMenuBar() {

		menuBar = new MenuBar(languageBundle);
		menuBar.setMiExitActionListener(e -> closeApplication());
		menuBar.setMiNewActionListener(e -> openTechnicalTermCreationDialog(languageBundle, chooseSpecialtyComboBoxGerman, chooseSpecialtyComboBoxSpanish));
		menuBar.setMiAssignActionListener(e -> openAssignmentDialog(languageBundle, assignSpecialtyComboBox));
		menuBar.setMiHistoryActionListener(e -> deleteHistory());
		menuBar.setMiGermanActionListener(e -> changeToGerman());
		menuBar.setMiSpanishActionListener(e -> changeToSpanish());
		register(menuBar);
	}

	private void initializeHeadBar() {

		headBar = new HeadBar(MAINFRAME_DISPLAY_RATIO, languageBundle, searchComboBox, repositoryTA.checkLetter());
		headBar.setSearchButtonActionListener(e -> openSearchResult(searchComboBox.getSearchWord()));
		headBar.setAlphabetButtonActionListener(e -> openLetterResult(e));
		headBar.setSpecialtyButtonActionListener(e -> openSpecialtyResult());
		headBar.setDeButtonActionListener(e -> changeToGerman());
		headBar.setEsButtonActionListener(e -> changeToSpanish());
		headBar.setNewTechnicalTermButtonActionListener(e -> openTechnicalTermCreationDialog(languageBundle, chooseSpecialtyComboBoxGerman, chooseSpecialtyComboBoxSpanish));
		register(headBar);
	}

	private void initializeStatusBar() {

		statusBar = new StatusBar(languageBundle);
		statusBar.setStatusBarStartpageButtonActionListener(e -> openSpecialtyResult());
		statusBar.setStatusBarSpecialtyButtonActionListener(e -> openTechnicalTermResult(currentSpecialty.getId()));
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
				Component c = e.getComponent();
				mainFrameWidth = c.getWidth();
				mainFrameHeight = c.getHeight();
				peto = createPetoForNonPanelChanges();
				updateComponents(peto);
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

	private void decideButtonIdentity(ActionEvent e) {

		int termId = ((TermButton) e.getSource()).getTermId();
		if (e.getSource() instanceof SpecialtyButton) {
			openTechnicalTermResult(termId);
		} else {
			openTechnicalTermContentDialog(languageBundle, e);
		}
	}

	private void openSearchResult(String newSearchWord) {

		repositoryTA.insertWord(newSearchWord);
		boolean panelChangeIsNeeded = true;
		boolean newSearchResult = true;

		if (staticPanelType.equals(StaticPanels.SearchResultPanel)) {
			panelChangeIsNeeded = false;
			if (searchWord.equals(newSearchWord)) {
				newSearchResult = false;
			}
		}

		currentSpecialty = null;
		currentTechnicalTerm = null;
		searchWord = newSearchWord;
		if (newSearchResult) {

			searchDynamicPanel = dynamicPanelCreator.createPanel(DynamicPanels.SearchResultPanel, searchDynamicPanel, searchActionListener, mainFrameWidth, mainFrameHeight,
					repositoryTA.searchTechnicalTerms(searchWord), searchWord);
			peto = new PanelEventTransferObject(mainFrameWidth, mainFrameHeight, getHistoryListFromDB(), repositoryTA.selectAllSpecialties(), WinUtil.getLanguage(languageBundle),
					repositoryTA.checkLetter(), currentSpecialty, currentTechnicalTerm, searchDynamicPanel);

			if (panelChangeIsNeeded) {
				changePanelInCenterContainer(searchPanel);
			}
			updateComponents(peto);
		}
	}

	private void openLetterResult(ActionEvent e) {

		String newSearchWord = e.getActionCommand();
		boolean panelChangeIsNeeded = true;
		boolean newLetterResult = true;

		if (staticPanelType.equals(StaticPanels.LetterResultPanel)) {
			panelChangeIsNeeded = false;
			if (searchWord.equals(newSearchWord)) {
				newLetterResult = false;
			}
		}

		currentSpecialty = null;
		currentTechnicalTerm = null;
		searchWord = newSearchWord;
		if (newLetterResult) {

			letterDynamicPanel = dynamicPanelCreator.createPanel(DynamicPanels.LetterResultPanel, letterDynamicPanel, searchActionListener, mainFrameWidth, mainFrameHeight,
					repositoryTA.searchTechnicalTerms(searchWord), "." + searchWord);
			peto = new PanelEventTransferObject(mainFrameWidth, mainFrameHeight, getHistoryListFromDB(), repositoryTA.selectAllSpecialties(), WinUtil.getLanguage(languageBundle),
					repositoryTA.checkLetter(), currentSpecialty, currentTechnicalTerm, letterDynamicPanel);

			if (panelChangeIsNeeded) {
				changePanelInCenterContainer(letterPanel);
			}
			updateComponents(peto);
		}
	}

	private void deleteHistory() {
		repositoryTA.deleteAllWords();
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

	private void openSpecialtyResult() {

		boolean panelChangeIsNeeded = true;
		if (staticPanelType.equals(StaticPanels.SpecialtyPanel)) {
			panelChangeIsNeeded = false;
		}

		currentSpecialty = null;
		currentTechnicalTerm = null;
		if (panelChangeIsNeeded) {

			specialtyDynamicPanel = dynamicPanelCreator.createPanel(DynamicPanels.SpecialtyPanel, specialtyDynamicPanel, specialtyActionListener, mainFrameWidth, mainFrameHeight,
					repositoryTA.selectAllSpecialties());
			peto = new PanelEventTransferObject(mainFrameWidth, mainFrameHeight, getHistoryListFromDB(), repositoryTA.selectAllSpecialties(), WinUtil.getLanguage(languageBundle),
					repositoryTA.checkLetter(), currentSpecialty, currentTechnicalTerm, specialtyDynamicPanel);

			changePanelInCenterContainer(specialtyPanel);
			updateComponents(peto);
		}
	}

	private void openTechnicalTermResult(int specialtyId) {

		boolean panelChangeIsNeeded = true;
		if (staticPanelType.equals(StaticPanels.TechnicalTermPanel)) {
			panelChangeIsNeeded = false;
		}

		currentSpecialty = repositoryTA.selectSpecialtyById(specialtyId);
		currentTechnicalTerm = null;

		if (panelChangeIsNeeded) {

			technicalTermDynamicPanel = dynamicPanelCreator.createPanel(DynamicPanels.TechnicalTermPanel, technicalTermDynamicPanel, technicalTermActionListener, mainFrameWidth,
					mainFrameHeight, getSpecialtyName(currentSpecialty), repositoryTA.selectAllTechnicalTermsOfSpecialty(specialtyId));
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

		TechnicalTermCreationDialog creationDialog = (TechnicalTermCreationDialog) dialogCreator.createWindow(DialogWindows.TechnicalTermCreationWindow, languageBundle,
				chooseSpecialtyComboBoxGerman, chooseSpecialtyComboBoxSpanish, new CreationDialogWindowListener(), new CreationDialogActionListener(), new CreationDialogKeyListener());
	}

	private void saveNewTechnicalTerm(ResourceBundle languageBundle, ChooseSpecialtyComboBox germanSpecialtyComboBox, ChooseSpecialtyComboBox spanishSpecialtyComboBox,
			TechnicalTermCreationDialog creationDialog) {
		int specialtyId = 0;
		if (creationDialog.isNewSpecialtySelected()) {
			Specialty newSpecialty = repositoryTA.createSpecialty(creationDialog.getGermanSpecialtyInput().getText(), "", GERMAN);
			specialtyId = newSpecialty.getId();
			repositoryTA.createSpecialtyTranslation(specialtyId, creationDialog.getSpanishSpecialtyInput().getText(), "", SPANISH);

		} else {
			if (WinUtil.getLanguageId(languageBundle) == GERMAN) {
				specialtyId = germanSpecialtyComboBox.getSelectedListItem().getValueMember();
			}
			if (WinUtil.getLanguageId(languageBundle) == SPANISH) {
				specialtyId = spanishSpecialtyComboBox.getSelectedListItem().getValueMember();
			}
		}

		int technicalTermId;
		technicalTermId = repositoryTA.createTechnicalTerm(creationDialog.getGermanTextField().getText(), creationDialog.getGermanTextArea().getText(), GERMAN, specialtyId).getId();
		repositoryTA.createTechnicalTermTranslation(technicalTermId, creationDialog.getSpanishTextField().getText(), creationDialog.getSpanishTextArea().getText(), SPANISH);
	}

	public void openAssignmentDialog(ResourceBundle languageBundle, ChooseSpecialtyComboBox assignSpecialtyComboBox) {

		register(assignSpecialtyComboBox);
		peto = createPetoForNonPanelChanges();
		updateComponents(peto);

		List<TechnicalTerm> technicalTermList = repositoryTA.selectAllTechnicalTerms();

		AssignmentDialog assignmentDialog = (AssignmentDialog) dialogCreator.createWindow(DialogWindows.AssignTechnicalTermToSpecialtyWindow, languageBundle, technicalTermList,
				assignSpecialtyComboBox, new AssignmentDialogWindowListener(), new AssignmentDialogActionListener(), new AssignmentDialogKeyListener());
	}

	public void changeButtonPressed(ChooseSpecialtyComboBox specialtyComboBox, AssignmentDialog newAssignDialog) {

		int specialtyId;
		if (newAssignDialog.isNewSpecialtySelected()) {
			Specialty newSpecialty = repositoryTA.createSpecialty(newAssignDialog.getGermanSpecialtyInput().getText(), "", GERMAN);
			specialtyId = newSpecialty.getId();
			repositoryTA.createSpecialtyTranslation(specialtyId, newAssignDialog.getSpanishSpecialtyInput().getText(), "", SPANISH);
		} else {
			specialtyId = specialtyComboBox.getSelectedListItem().getValueMember();
		}
		repositoryTA.assignTechnicalTermsToSpecialty(newAssignDialog.getTechnicalTermIds(), specialtyId);
		newAssignDialog.refreshAssignmentTableModel(repositoryTA.selectAllTechnicalTerms());
	}

	public void openTechnicalTermContentDialog(ResourceBundle languageBundle, ActionEvent event) {

		int technicalTermId = ((TermButton) event.getSource()).getTermId();
		TechnicalTerm technicalTerm = repositoryTA.selectTechnicalTermById(technicalTermId);

		currentSpecialty = repositoryTA.selectSpecialtyById(technicalTerm.getSpecialty().getId());
		currentTechnicalTerm = technicalTerm;
		peto = createPetoForNonPanelChanges();
		updateComponents(peto);

		TechnicalTermContentDialog contentDialog = (TechnicalTermContentDialog) dialogCreator.createWindow(DialogWindows.TechnicalTermContentWindow, languageBundle, technicalTerm,
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
		staticPanelType = currentCenterPanel.getStaticPanelType();
		centerContainer.add(currentCenterPanel);
		centerContainer.validate();
		centerContainer.repaint();
	}

	private List<String> getHistoryListFromDB() {
		List<History> historyList = repositoryTA.selectAllWords();
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

		switch (staticPanelType) {

		case SpecialtyPanel:

			specialtyDynamicPanel = dynamicPanelCreator.createPanel(DynamicPanels.SpecialtyPanel, specialtyDynamicPanel, specialtyActionListener, mainFrameWidth, mainFrameHeight,
					repositoryTA.selectAllSpecialties());
			return new PanelEventTransferObject(mainFrameWidth, mainFrameHeight, getHistoryListFromDB(), repositoryTA.selectAllSpecialties(), WinUtil.getLanguage(languageBundle),
					repositoryTA.checkLetter(), currentSpecialty, currentTechnicalTerm, specialtyDynamicPanel);

		case LetterResultPanel:

			letterDynamicPanel = dynamicPanelCreator.createPanel(DynamicPanels.LetterResultPanel, letterDynamicPanel, searchActionListener, mainFrameWidth, mainFrameHeight,
					repositoryTA.searchTechnicalTerms(searchWord), "." + searchWord);
			return new PanelEventTransferObject(mainFrameWidth, mainFrameHeight, getHistoryListFromDB(), repositoryTA.selectAllSpecialties(), WinUtil.getLanguage(languageBundle),
					repositoryTA.checkLetter(), currentSpecialty, currentTechnicalTerm, letterDynamicPanel);

		case SearchResultPanel:

			searchDynamicPanel = dynamicPanelCreator.createPanel(DynamicPanels.SearchResultPanel, searchDynamicPanel, searchActionListener, mainFrameWidth, mainFrameHeight,
					repositoryTA.searchTechnicalTerms(searchWord), searchWord);
			return new PanelEventTransferObject(mainFrameWidth, mainFrameHeight, getHistoryListFromDB(), repositoryTA.selectAllSpecialties(), WinUtil.getLanguage(languageBundle),
					repositoryTA.checkLetter(), currentSpecialty, currentTechnicalTerm, searchDynamicPanel);

		case TechnicalTermPanel:

			technicalTermDynamicPanel = dynamicPanelCreator.createPanel(DynamicPanels.TechnicalTermPanel, technicalTermDynamicPanel, technicalTermActionListener, mainFrameWidth,
					mainFrameHeight, getSpecialtyName(currentSpecialty), repositoryTA.selectAllTechnicalTermsOfSpecialty(currentSpecialty.getId()));
			return new PanelEventTransferObject(mainFrameWidth, mainFrameHeight, getHistoryListFromDB(), repositoryTA.selectAllSpecialties(), WinUtil.getLanguage(languageBundle),
					repositoryTA.checkLetter(), currentSpecialty, currentTechnicalTerm, technicalTermDynamicPanel);

		default:
			throw new RuntimeException("Kein gültiges CenterPanel");
		}
	}

	private void closeApplication() {

		if (Queries.queryExit(languageBundle, mainFrame)) {
			mainFrame.dispose();
			closeAndStoreDataBase();
			System.exit(0);
		}
	}

	class ContentDialogWindowListener extends WindowAdapter {

		@Override
		public void windowClosing(WindowEvent e) {
			JDialog dialog = (JDialog) e.getSource();
			dialog.dispose();
			currentTechnicalTerm = null;
			if (!(staticPanelType.equals(StaticPanels.TechnicalTermPanel))) {
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
			openTechnicalTermResult(((TermButton) e.getSource()).getTermId());
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

	public static void main(String[] args) {
		Presenter presenter = new Presenter();
	}
}
