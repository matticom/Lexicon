package businessOperations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.derby.tools.ij;
import org.dbunit.Assertion;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.eclipse.persistence.internal.jpa.EntityManagerImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import model.Languages;
import repository.LanguageDAO;
import util.UtilMethods;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import globals.LanguageAlreadyExists;
import globals.LanguageDoesNotExist;

public class LanguageBOTest {

	public static final Logger log = LoggerFactory.getLogger("LanguageBOTest.class");

	private static IDatabaseConnection mDBUnitConnection;
	private static IDataSet startDataset;

	private static EntityManagerFactory emfactory;
	private static EntityManager entitymanager;

	private static LanguageBO languageBOTest;
	private static LanguageDAO languageDAOTest;
	private static ITable actualTable;
	private static ITable expectedTable;
	private static IDataSet actualDatabaseDataSet;
	private static IDataSet expectedDataset;
	
	@Before
	public void refreshDB() {

		emfactory = Persistence.createEntityManagerFactory("Eclipselink_JPA_Derby");
		entitymanager = emfactory.createEntityManager();
		Connection connection = ((EntityManagerImpl) (entitymanager.getDelegate())).getServerSession().getAccessor().getConnection();

		try {
			ij.runScript(connection, LanguageBOTest.class.getResourceAsStream("/Lexicon_Database_Schema_Derby.sql"), "UTF-8", System.out, "UTF-8"); // nicht ("./Lex...
		} catch (Exception e) {
			log.error("Exception bei Derby Runscript: " + e.getMessage());
			e.printStackTrace();
		}

		try {
			mDBUnitConnection = new DatabaseConnection(connection);
			mDBUnitConnection.getConfig().setProperty(DatabaseConfig.FEATURE_ALLOW_EMPTY_FIELDS, true);
			startDataset = new FlatXmlDataSetBuilder().build(new File("./src/test/resources/XML/Languages/testSet_LanguageBO_Start.xml"));
		} catch (Exception e) {
			log.error("Exception bei DBUnit/IDataConnection: " + e.getMessage());
			e.printStackTrace();
		}

		languageDAOTest = new LanguageDAO(entitymanager);
		languageBOTest = new LanguageBO(languageDAOTest);
		
		try {
			DatabaseOperation.CLEAN_INSERT.execute(mDBUnitConnection, startDataset);
		} catch (Exception e) {
			log.error("Exception bei DBUnit/DatabaseOperation: " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Test
	public void createLanguageTest() throws Exception {

		entitymanager.getTransaction().begin();
		languageBOTest.createLanguage("Englisch");
		entitymanager.getTransaction().commit();

		actualDatabaseDataSet = mDBUnitConnection.createDataSet();
		actualTable = actualDatabaseDataSet.getTable("LANGUAGES");

		expectedDataset = new FlatXmlDataSetBuilder().build(new File("./src/test/resources/XML/Languages/testSet_LanguageBO_CreateLanguageTest.xml"));
		expectedTable = expectedDataset.getTable("LANGUAGES");

		Assertion.assertEquals(expectedTable, actualTable);
	}
	
	@Test(expected = LanguageAlreadyExists.class)
	public void createLanguage_LanguageAlreadyExistsTest() {

		entitymanager.getTransaction().begin();
		languageBOTest.createLanguage("Deutsch");
		entitymanager.getTransaction().commit();		
	}
	
	@Test
	public void deleteLanguageTest() throws Exception {
				
		entitymanager.getTransaction().begin();
		languageBOTest.deleteLanguage(1);
		entitymanager.getTransaction().commit();

		actualDatabaseDataSet = mDBUnitConnection.createDataSet();
		actualTable = actualDatabaseDataSet.getTable("LANGUAGES");

		expectedDataset = new FlatXmlDataSetBuilder().build(new File("./src/test/resources/XML/Languages/testSet_LanguageBO_DeleteLanguageTest.xml"));
		expectedTable = expectedDataset.getTable("LANGUAGES");

		Assertion.assertEquals(expectedTable, actualTable);
	}
	
	@Test(expected = LanguageDoesNotExist.class)
	public void deleteLanguage_LanguageDoesNotExistTest() {

		entitymanager.getTransaction().begin();
		languageBOTest.deleteLanguage(100);
		entitymanager.getTransaction().commit();		
	}
	

	@Test
	public void updateLanguageTest() throws Exception {
		
		entitymanager.getTransaction().begin();
		languageBOTest.updateLanguage(1, "Englisch");
		entitymanager.getTransaction().commit();
		
		actualDatabaseDataSet = mDBUnitConnection.createDataSet();
		actualTable = actualDatabaseDataSet.getTable("LANGUAGES");

		expectedDataset = new FlatXmlDataSetBuilder().build(new File("./src/test/resources/XML/Languages/testSet_LanguageBO_UpdateLanguageTest.xml"));
		expectedTable = expectedDataset.getTable("LANGUAGES");

		Assertion.assertEquals(expectedTable, actualTable);
	}
	
	@Test(expected = LanguageAlreadyExists.class)
	public void updateLanguage_LanguageAlreadyExistsTest() throws Exception {
		
		entitymanager.getTransaction().begin();
		languageBOTest.updateLanguage(1, "Spanisch");
		entitymanager.getTransaction().commit();
	}
	
	@Test(expected = LanguageDoesNotExist.class)
	public void updateLanguage_LanguageDoesNotExistTest() throws Exception {
		
		entitymanager.getTransaction().begin();
		languageBOTest.updateLanguage(6, "Englisch");
		entitymanager.getTransaction().commit();
	}

	@Test
	public void selectLanguageByIdTest() throws Exception {

		entitymanager.getTransaction().begin();
		Languages actualLanguage = languageBOTest.selectLanguageById(1);
		entitymanager.getTransaction().commit();

		assertThat("Deutsch", is(equalTo(actualLanguage.getName())));
	}
	
	@Test(expected = LanguageDoesNotExist.class)
	public void selectLanguageById_LanguageDoesNotExistTest() throws Exception {

		entitymanager.getTransaction().begin();
		Languages actualLanguage = languageBOTest.selectLanguageById(100);
		entitymanager.getTransaction().commit();
	}
	
	@Test
	public void selectLanguageByNameTest() throws Exception {

		entitymanager.getTransaction().begin();
		Languages actualLanguage = languageBOTest.selectLanguageByName("Deutsch");
		entitymanager.getTransaction().commit();

		assertThat("Deutsch", is(equalTo(actualLanguage.getName())));
	}

	@Test(expected = LanguageDoesNotExist.class)
	public void selectLanguageByName_LanguageDoesNotExistTest() {

		entitymanager.getTransaction().begin();
		Languages actualLanguage = languageBOTest.selectLanguageByName("Japanisch");
		entitymanager.getTransaction().commit();
	}
		
	@Test
	public void selectAllLanguageTest() throws Exception {

		entitymanager.getTransaction().begin();
		List<Languages> languageList = languageBOTest.selectAllLanguage();
		entitymanager.getTransaction().commit();

		expectedDataset = mDBUnitConnection.createDataSet();
		expectedTable = expectedDataset.getTable("LANGUAGES");

		String[] expectedArray = UtilMethods.convertITableToArray(expectedTable, "LANGUAGES_NAME");
		String[] actualArray = UtilMethods.convertValuesFromObjectClasstypeAndPropertyFromListToArray(languageList, Languages.class, "getName");

		assertThat(expectedArray, is(equalTo(actualArray)));
	}
	
	@After
	public void cleanTesting() {
		
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
