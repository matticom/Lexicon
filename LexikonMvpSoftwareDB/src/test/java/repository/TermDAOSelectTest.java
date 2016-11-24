package repository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.eclipse.persistence.internal.jpa.EntityManagerImpl;

import org.apache.derby.tools.ij;
import org.dbunit.Assertion;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.DatabaseSequenceFilter;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.FilteredDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.filter.ITableFilter;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.History;
import model.Languages;
import model.Specialty;
import model.TechnicalTerm;
import model.Translations;
import transferObjects.TranslationDataset;
import util.UtilMethods;

public class TermDAOSelectTest {

	public static final Logger log = LoggerFactory.getLogger("TermDAOTest.class");
	
	private static IDatabaseConnection mDBUnitConnection;
    private static IDataSet startDataset;
        
	private static EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("Eclipselink_JPA_Derby");
	private static EntityManager entitymanager;
	
	private static Connection connection;
	
	private static TermDAO termDAOTest;
	private static LanguageDAO languageDAOTest;
	private static ITable actualTranslationsTable;
	private static ITable actualSpecialtyTable;
	private static ITable actualTechnicalTermTable;
	private static ITable expectedTranslationsTable;
	private static ITable expectedSpecialtyTable;
	private static ITable expectedTechnicalTermTable;

	private static IDataSet actualDatabaseDataSet;
	private static IDataSet expectedDataset;
	
	
	@BeforeClass
	public static void prepareForTesting() {
		
		
		
		entitymanager = emfactory.createEntityManager();
				
		connection = ((EntityManagerImpl) (entitymanager.getDelegate())).getServerSession().getAccessor().getConnection();

		try {
			ij.runScript(connection,TermDAOSelectTest.class.getResourceAsStream("/Lexicon_Database_Schema_Derby.sql"),"UTF-8", System.out, "UTF-8"); 
		} catch (Exception e) {
			log.error("Exception bei Derby Runscript: " + e.getMessage());
			e.printStackTrace();
		}

		try {
			mDBUnitConnection = new DatabaseConnection(connection);
			mDBUnitConnection.getConfig().setProperty(DatabaseConfig.FEATURE_ALLOW_EMPTY_FIELDS, true);
			startDataset = new FlatXmlDataSetBuilder().build(new File("./src/test/resources/XML/Term/testSet_termDAO_Start.xml"));
		} catch (Exception e) {
			log.error("Exception bei DBUnit/IDataConnection: " + e.getMessage());
			e.printStackTrace();
		}
					
		termDAOTest = new TermDAO(entitymanager);
		languageDAOTest = new LanguageDAO(entitymanager);
		
		//Testreihenfolge:
		
		// 1. updateTranslationTest()
		// 2. selectSpecialtyByNameTest()
		
	}

	@Before
	public void refreshDB() {
		
		// alle ForeignKey Contraints werden entfernt, damit DbUnit Tabellen theoretisch s‰ubern kann 
		// (DatabaseOperation.CLEAN_INSERT.execute(mDBUnitConnection, startDataset) "funktioniert" auch ohne Contraints zu entfernen
		List<Specialty> specialtyList = termDAOTest.selectAllSpecialties();
		termDAOTest.removeForeignKeyConstraints(specialtyList); // Die Specialty ForeignKeys aus TechnicalTerm werden entfernt und umgekehrt 
		
		// Tabellen werden mit DbUnit ges‰ubert
		try {
			DatabaseOperation.CLEAN_INSERT.execute(mDBUnitConnection, startDataset);
			log.error("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!SELECT");
		} catch (Exception e) {
			log.error("Exception bei DBUnit/DatabaseOperation: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	@Test
	public void selectSpecialtyByNameTest() throws Exception {
		
		// Sehr komisch: aus der Datenbank wird die Specialty mit dem Namen "Beton" herausgesucht
		// (sucht nach Translations.name="Beton" und der Sprache "Deutsch" in Translations der Specialties)
		// er findet die Specialty mit dem Namen und gibt die Specialty zur¸ck, aber die zur¸ckgegebene Specialty
		// hat genau an den Punkt der R¸ckgabe den Translations.name "Beton updated" (also die geupdatede Bezeichnung
		// des vorherigen Tests)
		// eigentlich sollten die Tabellen ges‰ubert sein sobald der Test startet, was sie "oberfl‰chlich" 
		// (select Befehl findet die richtige Bezeichnung) auch passiert ist, aber noch vor dem commit 
		// zeigt er plˆtzlich die "wirklichen" Daten...
		// Es treten in dem gesamten Test keine Fehlermeldungen auf
		
		entitymanager.getTransaction().begin();
		Specialty betonSpecialty = termDAOTest.selectSpecialtyByName("Beton", "Deutsch");
		entitymanager.getTransaction().commit();
		
		// Aktuelles Abbild der Tabellen in XML
		try {
			ITableFilter filter = new DatabaseSequenceFilter(mDBUnitConnection);
			IDataSet fullDataSet = new FilteredDataSet(filter, mDBUnitConnection.createDataSet());
			FlatXmlDataSet.write(fullDataSet, new FileOutputStream("actual.xml"));
			
		} catch (Exception e) {
			System.out.println("Es wurde eine Exception bei FullDataSetXML geworfen: "+ e.getMessage());
			e.printStackTrace();
		}		
		
		String betonName = betonSpecialty.getTranslationList().get(0).getName();
		assertThat("Beton", is(equalTo(betonName)));
		// Test nicht bestanden da betonName= "Beton geupdated"
		
	}
	
	
	@Test
	public void updateTranslationTest() throws Exception {
		
		entitymanager.getTransaction().begin();
		termDAOTest.updateTranslation("Beton", "Deutsch", "Beton updated", "description updated");
		entitymanager.getTransaction().commit();
		
		// Specialty.name und Specialty.description wurden geupdated --> dann mit @Before sollte eigentlich Datenbank ges‰ubert werden
				
		actualDatabaseDataSet = mDBUnitConnection.createDataSet();
		actualTranslationsTable = actualDatabaseDataSet.getTable("TRANSLATIONS");
			
		expectedDataset = new FlatXmlDataSetBuilder().build(new File("./src/test/resources/XML/Term/testSet_termDAO_updateTranslationTest.xml"));
		expectedTranslationsTable = expectedDataset.getTable("TRANSLATIONS");
				
		Assertion.assertEquals(expectedTranslationsTable, actualTranslationsTable);
		// Test erfolgreich abgeschlossen...
		// n‰chste Testmethode: selectSpecialtyByNameTest()
	}
	
	
	@AfterClass
	public static void cleanTesting() {

		try {
			mDBUnitConnection.close();
		} catch (SQLException e) {
			System.out.println("Es wurde eine Exception beim Schlieﬂen der IDataConnection geworfen: "+ e.getMessage());
			e.printStackTrace();
		}
		
		entitymanager.close();
		emfactory.close();
	}
}
