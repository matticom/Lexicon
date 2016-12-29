package windows;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;

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
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import AssignmentWindowComponents.AssignmentTableModel;
import businessOperations.LanguageBO;
import businessOperations.TermBO;
import dataTransferObjects.TranslationDataset;
import exceptions.LanguageDoesNotExist;
import exceptions.LanguageEntryInSpecialtyAlreadyExists;
import exceptions.LanguageEntryInTechnicalTermAlreadyExists;
import exceptions.SpecialtyAlreadyExists;
import exceptions.SpecialtyDoesNotExist;
import exceptions.TechnicalTermAlreadyExists;
import exceptions.TechnicalTermDoesNotExist;
import exceptions.TermDoesNotExist;
import model.Languages;
import model.Specialty;
import model.TechnicalTerm;
import model.Term;
import model.Translations;
import repository.LanguageDAO;
import repository.TermDAO;
import util.UtilMethods;


public class AssignmentTableModelTest {

	public static final Logger log = LoggerFactory.getLogger("TermBO.class");
	
	private static IDatabaseConnection mDBUnitConnection;
    private static IDataSet startDataset;
        
	private static EntityManagerFactory emfactory;
	private static EntityManager entitymanager;	
	private static Connection connection;	
	private static TermDAO termDAOTest;
	private static LanguageDAO languageDAOTest;
	private static TermBO termBOTest;
	private static LanguageBO languageBOTest;

	private AssignmentTableModel assignmentTableModel;
	
	@Before
	public void refreshDB() {
	
		emfactory = Persistence.createEntityManagerFactory("Eclipselink_JPA_Derby");
		entitymanager = emfactory.createEntityManager();
		connection = ((EntityManagerImpl) (entitymanager.getDelegate())).getServerSession().getAccessor().getConnection();

		try {
			ij.runScript(connection,AssignmentTableModelTest.class.getResourceAsStream("/Lexicon_Database_Schema_Derby.sql"),"UTF-8", System.out, "UTF-8"); 
		} catch (Exception e) {
			log.error("Exception bei Derby Runscript: " + e.getMessage());
			e.printStackTrace();
		}

		try {
			mDBUnitConnection = new DatabaseConnection(connection);
			mDBUnitConnection.getConfig().setProperty(DatabaseConfig.FEATURE_ALLOW_EMPTY_FIELDS, true);
			startDataset = new FlatXmlDataSetBuilder().build(new File("./src/test/resources/XML/Term/testSet_termBO_Start.xml"));
		} catch (Exception e) {
			log.error("Exception bei DBUnit/IDataConnection: " + e.getMessage());
			e.printStackTrace();
		}
					
		termDAOTest = new TermDAO(entitymanager);
		languageDAOTest = new LanguageDAO(entitymanager);
		languageBOTest = new LanguageBO(languageDAOTest);
		termBOTest = new TermBO(languageBOTest, termDAOTest);
		
		try {
			DatabaseOperation.CLEAN_INSERT.execute(mDBUnitConnection, startDataset);
		} catch (Exception e) {
			log.error("Exception bei DBUnit/DatabaseOperation: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	@Test
	public void createTechnicalTermTest() throws Exception {
						
		entitymanager.getTransaction().begin();
		List<TechnicalTerm> technicalTermList = termBOTest.selectAllTechnicalTerms();
		entitymanager.getTransaction().commit();
				
//		AssignmentTableRowObject[] tableRowObjectArray = assignmentTableModel.convertTechnicalTermListToArray(technicalTermList, new AssignmentTableRowObject[4]);
			
//		System.out.println(Arrays.toString(tableRowObjectArray));		
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
