package repository;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.History;
import util.UtilMethods;

public class HistoryDAOTest {

	public static final Logger log = LoggerFactory.getLogger("HistoryDAOTest.class");

	private static IDatabaseConnection mDBUnitConnection;
	private static IDataSet startDataset;
	private static Connection connection;

	private static EntityManagerFactory emfactory;
	private static EntityManager entitymanager;

	private static HistoryDAO historyDAOTest;
	private static ITable actualTable;
	private static ITable expectedTable;
	private static IDataSet actualDatabaseDataSet;
	private static IDataSet expectedDataset;

	
	@Before
	public void refreshDB() {
		
		emfactory = Persistence.createEntityManagerFactory("Eclipselink_JPA_Derby");
		entitymanager = emfactory.createEntityManager();

		connection = ((EntityManagerImpl) (entitymanager.getDelegate())).getServerSession().getAccessor().getConnection();
		
		try {
			ij.runScript(connection,HistoryDAOTest.class.getResourceAsStream("/Lexicon_Database_Schema_Derby.sql"),"UTF-8", System.out, "UTF-8"); // nicht ("./Lex...
			
		} catch (Exception e) {
			log.error("Exception bei Derby Runscript: " + e.getMessage());
			e.printStackTrace();
		}

		try {
			mDBUnitConnection = new DatabaseConnection(connection);
			mDBUnitConnection.getConfig().setProperty(DatabaseConfig.FEATURE_ALLOW_EMPTY_FIELDS, true);
			startDataset = new FlatXmlDataSetBuilder().build(new File("./src/test/resources/XML/History/testSet_HistoryDAO_Start.xml"));
		} catch (Exception e) {
			log.error("Exception bei DBUnit/IDataConnection: " + e.getMessage());
			e.printStackTrace();
		}

		historyDAOTest = new HistoryDAO(entitymanager);
		
		try {
			DatabaseOperation.CLEAN_INSERT.execute(mDBUnitConnection, startDataset);
		} catch (Exception e) {
			log.error("Exception bei DBUnit/DatabaseOperation: " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Test
	public void testInsertWord() throws Exception {

		entitymanager.getTransaction().begin();
		historyDAOTest.insertWord("letztes Wort");
		entitymanager.getTransaction().commit();
		
		actualDatabaseDataSet = mDBUnitConnection.createDataSet();
		actualTable = actualDatabaseDataSet.getTable("HISTORY");
			
		expectedDataset = new FlatXmlDataSetBuilder().build(new File("./src/test/resources/XML/History/testSet_HistoryDAO_InsertTest.xml"));
		expectedTable = expectedDataset.getTable("HISTORY");
			
		Assertion.assertEquals(expectedTable, actualTable);	
	}
	
	@Test
	public void testDeleteAll() throws Exception {

		entitymanager.getTransaction().begin();
		historyDAOTest.deleteAllWords();
		entitymanager.getTransaction().commit();
		
		actualDatabaseDataSet = mDBUnitConnection.createDataSet();
		actualTable = actualDatabaseDataSet.getTable("HISTORY");
			
		expectedDataset = new FlatXmlDataSetBuilder().build(new File("./src/test/resources/XML/History/testSet_HistoryDAO_DeleteAllTest.xml"));
		expectedTable = expectedDataset.getTable("HISTORY");
				
		Assertion.assertEquals(expectedTable, actualTable);	
	}
	
	@Test
	public void testSelectAllWords() throws Exception {

		entitymanager.getTransaction().begin();
		List<History> historyList = historyDAOTest.selectAllWords();
		entitymanager.getTransaction().commit();
				
		expectedDataset = mDBUnitConnection.createDataSet();
		expectedTable = expectedDataset.getTable("HISTORY");
		
		String[] expectedArray = UtilMethods.convertITableToArray(expectedTable, "HISTORY_WORD");
		String[] actualArray = UtilMethods.convertValuesFromObjectClasstypeAndPropertyFromListToArray(historyList, History.class, "getWord");
				
		assertThat(expectedArray, is(equalTo(actualArray)));
	}
		
	
	@After
	public void cleanTesting() {
		
		try {
			mDBUnitConnection.close();
			connection.close();
		} catch (SQLException e) {
			System.out.println("Es wurde eine Exception beim Schlieﬂen der IDataConnection geworfen: "+ e.getMessage());
			e.printStackTrace();
		}
		
		entitymanager.close();
		emfactory.close();
	}
	
}
