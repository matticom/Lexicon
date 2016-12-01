package repository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;

import org.apache.derby.tools.ij;
import org.dbunit.Assertion;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.eclipse.persistence.internal.jpa.EntityManagerImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import model.Languages;
import util.UtilMethods;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LanguageDaoTest {

	public static final Logger log = LoggerFactory.getLogger("LanguageDaoTest.class");

	private static IDatabaseConnection mDBUnitConnection;
	private static IDataSet startDataset;

	private static EntityManagerFactory emfactory;
	private static EntityManager entitymanager;

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
			ij.runScript(connection, LanguageDaoTest.class.getResourceAsStream("/Lexicon_Database_Schema_Derby.sql"), "UTF-8", System.out, "UTF-8"); // nicht ("./Lex...
		} catch (Exception e) {
			log.error("Exception bei Derby Runscript: " + e.getMessage());
			e.printStackTrace();
		}

		try {
			mDBUnitConnection = new DatabaseConnection(connection);
			mDBUnitConnection.getConfig().setProperty(DatabaseConfig.FEATURE_ALLOW_EMPTY_FIELDS, true);
			startDataset = new FlatXmlDataSetBuilder().build(new File("./src/test/resources/XML/Languages/testSet_LanguageDAO_Start.xml"));
		} catch (Exception e) {
			log.error("Exception bei DBUnit/IDataConnection: " + e.getMessage());
			e.printStackTrace();
		}

		languageDAOTest = new LanguageDAO(entitymanager);
		
		try {
			DatabaseOperation.CLEAN_INSERT.execute(mDBUnitConnection, startDataset);
		} catch (Exception e) {
			log.error("Exception bei DBUnit/DatabaseOperation: " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Test
	public void insertLanguageTest() throws Exception {

		Languages eng = new Languages("Englisch");
		
		entitymanager.getTransaction().begin();
		languageDAOTest.insertLanguage(eng);
		entitymanager.getTransaction().commit();

		actualDatabaseDataSet = mDBUnitConnection.createDataSet();
		actualTable = actualDatabaseDataSet.getTable("LANGUAGES");

		expectedDataset = new FlatXmlDataSetBuilder().build(new File("./src/test/resources/XML/Languages/testSet_LanguageDAO_InsertTest.xml"));
		expectedTable = expectedDataset.getTable("LANGUAGES");

		Assertion.assertEquals(expectedTable, actualTable);
	}

	@Test
	public void deleteLanguageTest() throws Exception {

		Languages de = languageDAOTest.selectLanguageById(1);
		
		entitymanager.getTransaction().begin();
		languageDAOTest.deleteLanguage(de);
		entitymanager.getTransaction().commit();

		actualDatabaseDataSet = mDBUnitConnection.createDataSet();
		actualTable = actualDatabaseDataSet.getTable("LANGUAGES");

		expectedDataset = new FlatXmlDataSetBuilder().build(new File("./src/test/resources/XML/Languages/testSet_LanguageDAO_DeleteTest.xml"));
		expectedTable = expectedDataset.getTable("LANGUAGES");

		Assertion.assertEquals(expectedTable, actualTable);
	}

	@Test
	public void updateLanguageTest() throws Exception {

		Languages de = languageDAOTest.selectLanguageById(1);
		Languages eng = new Languages("Englisch");
		
		entitymanager.getTransaction().begin();
		languageDAOTest.updateLanguage(de, eng);
		entitymanager.getTransaction().commit();
		
		actualDatabaseDataSet = mDBUnitConnection.createDataSet();
		actualTable = actualDatabaseDataSet.getTable("LANGUAGES");

		expectedDataset = new FlatXmlDataSetBuilder().build(new File("./src/test/resources/XML/Languages/testSet_LanguageDAO_UpdateTest.xml"));
		expectedTable = expectedDataset.getTable("LANGUAGES");

		Assertion.assertEquals(expectedTable, actualTable);
	}

	@Test
	public void selectLanguageByNameTest() throws Exception {

		entitymanager.getTransaction().begin();
		Languages actualLanguage = languageDAOTest.selectLanguageByName("Deutsch");
		entitymanager.getTransaction().commit();

		assertThat("Deutsch", is(equalTo(actualLanguage.getName())));
	}

	@Test(expected = NoResultException.class)
	public void selectLanguageByNameWithNoResultExceptionTest() {

		entitymanager.getTransaction().begin();
		Languages actualLanguage = null;
		try {
			actualLanguage = languageDAOTest.selectLanguageByName("Maori");
		} catch (NoResultException e) {
			entitymanager.getTransaction().commit();
			throw new NoResultException();
		}
		entitymanager.getTransaction().commit();

		assertThat("Maori", is(equalTo(actualLanguage.getName())));
	}
		
	@Test
	public void selectAllLanguageTest() throws Exception {

		entitymanager.getTransaction().begin();
		List<Languages> languageList = languageDAOTest.selectAllLanguages();
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
