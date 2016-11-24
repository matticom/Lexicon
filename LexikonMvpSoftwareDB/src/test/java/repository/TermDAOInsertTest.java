package repository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.eclipse.persistence.internal.jpa.EntityManagerImpl;

import org.apache.derby.tools.ij;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.Languages;
import model.Specialty;
import transferObjects.TranslationDataset;
import util.UtilMethods;

public class TermDAOInsertTest {

	public static final Logger log = LoggerFactory.getLogger("TermDAOTest.class");
	
	private static IDatabaseConnection mDBUnitConnection;
    private static IDataSet startDataset;
        
	private static EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("Eclipselink_JPA_Derby");
	private static EntityManager entitymanager;
	
	private static TermDAO termDAOTest;
	private static LanguageDAO languageDAOTest;
	private static ITable actualTranslationsTable;
	private static ITable actualTechnicalTermTable;
	private static IDataSet actualDatabaseDataSet;
	
	
	@BeforeClass
	public static void prepareForTesting() {
		
		
		
		entitymanager = emfactory.createEntityManager();
				
		Connection connection = ((EntityManagerImpl) (entitymanager.getDelegate())).getServerSession().getAccessor().getConnection();

		try {
			ij.runScript(connection,HistoryDAOTest.class.getResourceAsStream("/Lexicon_Database_Schema_Derby.sql"),"UTF-8", System.out, "UTF-8"); 
			// nicht ("./Lex..., da es in Maven Ressourcen sucht, welche erst entstehen nach Run -> Maven build -> clean install
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
		
	}

	@Before
	public void refreshDB() {
		
		try {
			DatabaseOperation.CLEAN_INSERT.execute(mDBUnitConnection, startDataset);
		} catch (Exception e) {
			log.error("Exception bei DBUnit/DatabaseOperation: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void insertNewSpecialtyTest() throws Exception {
		
		entitymanager.getTransaction().begin();
		Languages deutschLanguage = languageDAOTest.selectLanguageByName("Deutsch");
		entitymanager.getTransaction().commit();
		
		entitymanager.getTransaction().begin();
		termDAOTest.insertNewSpecialty("Werkzeuge", "zum Arbeiten", deutschLanguage);
		entitymanager.getTransaction().commit();
		
		actualDatabaseDataSet = mDBUnitConnection.createDataSet();
		actualTranslationsTable = actualDatabaseDataSet.getTable("TRANSLATIONS");
				
		TranslationDataset actualTranslationEntry = UtilMethods.flexibleLastTranslationSetOfITable(actualTranslationsTable, 0);
				
		assertThat("Werkzeuge", is(equalTo(actualTranslationEntry.getTranslationName())));
		assertThat("zum Arbeiten", is(equalTo(actualTranslationEntry.getTranslationDescription())));
		assertThat(deutschLanguage.getId(), is(equalTo(actualTranslationEntry.getLanguageId())));
	}
	
	@Test
	public void insertSpecialtyTranslationTest() throws Exception {
		
		entitymanager.getTransaction().begin();
		Languages spanischLanguage = languageDAOTest.selectLanguageByName("Spanisch");
		entitymanager.getTransaction().commit();
		
		entitymanager.getTransaction().begin();
		termDAOTest.insertSpecialtyTranslation("Beton", "Deutsch", "SpaBeton", "SpaHartesZeugs", spanischLanguage);
		entitymanager.getTransaction().commit();
				
		actualDatabaseDataSet = mDBUnitConnection.createDataSet();
		actualTranslationsTable = actualDatabaseDataSet.getTable("TRANSLATIONS");
				
		TranslationDataset actualTranslationEntry = UtilMethods.flexibleLastTranslationSetOfITable(actualTranslationsTable, 0);
				
		assertThat("SpaBeton", is(equalTo(actualTranslationEntry.getTranslationName())));
		assertThat("SpaHartesZeugs", is(equalTo(actualTranslationEntry.getTranslationDescription())));
		assertThat(spanischLanguage.getId(), is(equalTo(actualTranslationEntry.getLanguageId())));
	}
	
	@Test
	public void insertNewTechnicalTermTest() throws Exception {
		
		entitymanager.getTransaction().begin();
		Languages deutschLanguage = languageDAOTest.selectLanguageByName("Deutsch");
		entitymanager.getTransaction().commit();
		
		entitymanager.getTransaction().begin();
		Specialty betonSpecialty = termDAOTest.selectSpecialtyById(3);
		entitymanager.getTransaction().commit();
		
		entitymanager.getTransaction().begin();
		termDAOTest.insertNewTechnicalTerm("Flüssigbeton", "flüssig", betonSpecialty, deutschLanguage);
		entitymanager.getTransaction().commit();
		
		actualDatabaseDataSet = mDBUnitConnection.createDataSet();
		actualTranslationsTable = actualDatabaseDataSet.getTable("TRANSLATIONS");
		actualTechnicalTermTable = actualDatabaseDataSet.getTable("TECHNICALTERM");
				
		TranslationDataset actualTranslationEntry = UtilMethods.flexibleLastTranslationSetOfITable(actualTranslationsTable, 0);
		int actualSpecialtyId = UtilMethods.flexibleLastTechnicalTermSpecialtyOfITable(actualTechnicalTermTable, 0);
				
		assertThat("Flüssigbeton", is(equalTo(actualTranslationEntry.getTranslationName())));
		assertThat("flüssig", is(equalTo(actualTranslationEntry.getTranslationDescription())));
		assertThat(deutschLanguage.getId(), is(equalTo(actualTranslationEntry.getLanguageId())));
		assertThat(3, is(equalTo(actualSpecialtyId)));
	}
	
	@Test
	public void insertTechnicalTermTranslationTest() throws Exception {
		
		entitymanager.getTransaction().begin();
		Languages spanischLanguage = languageDAOTest.selectLanguageByName("Spanisch");
		entitymanager.getTransaction().commit();
		
		entitymanager.getTransaction().begin();
		termDAOTest.insertTechnicalTermTranslation("Rolladen", "Deutsch", "SpaRolladen", "SpaZum Schutz der Fenster", spanischLanguage);
		entitymanager.getTransaction().commit();
		
		actualDatabaseDataSet = mDBUnitConnection.createDataSet();
		actualTranslationsTable = actualDatabaseDataSet.getTable("TRANSLATIONS");
		actualTechnicalTermTable = actualDatabaseDataSet.getTable("TECHNICALTERM");
				
		TranslationDataset actualTranslationEntry = UtilMethods.flexibleLastTranslationSetOfITable(actualTranslationsTable, 0);
		int actualSpecialtyId = UtilMethods.flexibleLastTechnicalTermSpecialtyOfITable(actualTechnicalTermTable, 0);
				
		assertThat("SpaRolladen", is(equalTo(actualTranslationEntry.getTranslationName())));
		assertThat("SpaZum Schutz der Fenster", is(equalTo(actualTranslationEntry.getTranslationDescription())));
		assertThat(spanischLanguage.getId(), is(equalTo(actualTranslationEntry.getLanguageId())));
	}
	
		
	@AfterClass
	public static void cleanTesting() {
				
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
