package presenter;

import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.swing.SwingUtilities;
import javax.swing.text.JTextComponent;

import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;

import utilities.WinUtil;
import viewFactory.HeadBar;
import viewFactory.MainFrame;
import viewFactory.MenuBar;
import viewFactory.StatusBar;
import businessOperations.LanguageBO;
import businessOperations.TermBO;
import eventHandling.PanelEventTransferObject;
import eventHandling.Updatable;
import inputChecker.SearchWordChecker;
import interactElements.ComboBoxFactory;
import interactElements.ComboBoxes;
import interactElements.SearchComboBox;
import interactElements.SpecialtyButton;
import model.Translations;
import panels.TechnicalTermPanelStatic;
import panels.SpecialtyPanelStatic;
import repository.HistoryDAO;
import repository.LanguageDAO;
import repository.TermDAO;
import windows.AssignTechnicalTermToSpecialtyWindow;
import windows.TechnicalTermContentWindow;
import windows.TechnicalTermCreationWindow;

public class Presenter {

	private MainFrame mainFrame;
	private MenuBar menuBar;
	private HeadBar headBar;
	private StatusBar statusBar;

	private TechnicalTermPanelStatic searchResultPanel;
	private SpecialtyPanelStatic specialtyPanel;

	private AssignTechnicalTermToSpecialtyWindow assignTechnicalTermToSpecialtyWindow;
	private TechnicalTermContentWindow technicalTermContentWindow;
	private TechnicalTermCreationWindow technicalTermCreationWindow;

	private SearchWordChecker searchWordChecker;

	private SearchComboBox searchComboBox;

	private ComboBoxFactory comboBoxFactory;

	private TermBO termBO;
	private LanguageBO languageBO;
	private TermDAO termDAO;
	private LanguageDAO languageDAO;
	private HistoryDAO historyDAO;

	private EntityManager entitymanager;
	private EntityManagerFactory entityManagerFactory;
	private Connection connection;

	private IDatabaseConnection mDBUnitConnection;
	private IDataSet startDataset;

	private ResourceBundle languageBundle;
	private Locale currentLocale;

	private List<Updatable> componentList;

	public Presenter() {

		initializeMenuBar();
		initializeStatusBar();
		initializeHeadBar();

		// mainFrame = new MainFrame();
	}

	private void initializeMenuBar() {

		menuBar = new MenuBar(languageBundle);
		menuBar.setMiExitActionListener(e -> closeWindow());
		menuBar.setMiNewActionListener(e -> newEntryMenu());
		menuBar.setMiHistoryActionListener(e -> {
			deleteHistory();
			// HeadPanel.writeSearchWordsFromDbToHistory();
		});
		menuBar.setMiGermanActionListener(e -> switchDE());
		menuBar.setMiSpanishActionListener(e -> switchES());
		// updateMenuBar(); -> updateComponents
	}

	private void initializeStatusBar() {

		statusBar = new StatusBar(languageBundle);
		statusBar.setStatusBarSpecialtyButtonActionListener(e -> displaySpecialtyPanel());
		// updateStatusBar(); -> updateComponents
	}

	private void initializeHeadBar() {

		List<Translations> translationList = termBO.selectLetter("f");
		List<String> history = new ArrayList<String>();

		for (Translations translation : translationList) {
			history.add(translation.getName());
		}
		
		
//		headBar = new HeadBar(languageBundle);
		SearchWordChecker searchWordChecker = new SearchWordChecker();
		comboBoxFactory = new ComboBoxFactory();
		searchComboBox = (SearchComboBox) comboBoxFactory.createComboBox(ComboBoxes.SearchComboBox, history, searchWordChecker);
		headBar.add(searchComboBox);
		

		searchComboBox.setSearchComboBoxFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (e.getSource() instanceof JTextComponent) {
					((JTextComponent) e.getSource()).selectAll();
				}
			}
		});

		searchComboBox.setSearchComboBoxKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				searchWordChecker.keyPressedChecker(e);
			}

			@Override
			public void keyTyped(KeyEvent e) {
				searchWordChecker.keyTypedChecker(e);
			}
		});

//		headBar.setAlphabetButtonActionListener(e -> searchLetterResult(e));
//		headBar.setSearchButtonActionListener(l);
//		headBar.setDeButtonActionListener(l);
//		headBar.setEsButtonActionListener(l);
//		headBar.setSpecialtyButtonActionListener(l);
//		headBar.setNewTechnicalTermButtonActionListener(l);

	}

	private void addComponents(Updatable component) {

		componentList.add(component);
	}

	private void removeComponents(Updatable component) {

		componentList.remove(component);
	}

	private void updateComponents(PanelEventTransferObject e) {
		// welche Infos sollen übertragen werden? (Sprache, aktueller Begriff/Fachgebiet, welche Ebene)
		for (Updatable component : componentList) {
			component.updatePanel(e);
		}
		// es müssen nicht alle aktualisiert werden: zwingend MenuBar, HeadBar,
		// StatusBar; Abhängig welches Panel als nächstes zu sehen ist: die
		// Anderenpanels
	}

	private void searchLetterResult(ActionEvent e) {
		int id = ((SpecialtyButton)(e.getSource())).getTranlationId(); // Beispiel für Zugriff auf SpecialtyButton
		// Buchstabenfenster wird im CENTER geöffnet
		// mainFrame.letterResults_OpenLetterWindow(((JButton)
		// e.getSource()).getActionCommand());

	}

	private void displaySpecialtyPanel() {

	}

	private void deleteHistory() {
		historyDAO.deleteAllWords(); // Object ist noch nicht initialisiert
		// miHistory.setEnabled(false);
	}

	// Beenden Button gedrückt
	private void closeWindow() {
		// if (queryExit())
		// MainFrame.this.dispose();
	}

	// Neu Button gedrückt
	private void newEntryMenu() {
		// // Modaler Dialog
		// newEntryDialog dlg = new newEntryDialog(this);
		// dlg.showDialog(this);
		// // Dialogreferenz wird gelöscht, wenn geschlossen wurde
		// dlg = null;
	}

	// Deutsch RadioBox Button gedrückt
	private void switchDE() {
		// if (this.getCurrentLocale().toString().equals("es")) {
		// all_ActivteLanguageBtn("de");
		// ((JRadioButtonMenuItem) miDeutsch).setSelected(true);
		// ((JRadioButtonMenuItem) miSpanisch).setSelected(false);
		// HeadPanel.changeLangBtn("de");
		// }
	}

	// Spanische RadioBox Button gedrückt
	private void switchES() {
		// if (this.getCurrentLocale().toString().equals("de")) {
		// all_ActivteLanguageBtn("es");
		// ((JRadioButtonMenuItem) miDeutsch).setSelected(false);
		// ((JRadioButtonMenuItem) miSpanisch).setSelected(true);
		// HeadPanel.changeLangBtn("es");
		// }
	}

	private void keyPressedChecker(KeyEvent e) { // muss an Checker angepasst
													// werden

	}

	private void keyTypedChecker(KeyEvent e) {

	}

	public static void main(String[] args) {
		Presenter presenter = new Presenter();

		// SwingUtilities.invokeLater(() -> {
		// View view = new View();
		// view.setPresenter(new Presenter(view, new Model()));
		// });
	}
}
