package businessOperations;

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

import dataTransferObjects.TranslationDataset;
import model.Languages;
import model.Specialty;
import model.TechnicalTerm;
import model.Term;
import model.Translations;
import repository.LanguageDAO;
import repository.TermDAO;
import util.UtilMethods;
import globals.TechnicalTermAlreadyExists;
import globals.SpecialtyDoesNotExist;
import globals.LanguageEntryInSpecialtyAlreadyExists;
import globals.LanguageEntryInTechnicalTermAlreadyExists;
import globals.SpecialtyAlreadyExists;
import globals.TechnicalTermDoesNotExist;
import globals.TermDoesNotExist;
import globals.LanguageDoesNotExist;


public class TermBOTest {

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

	private static ITable actualTranslationsTable;
	private static ITable actualSpecialtyTable;
	private static ITable actualTechnicalTermTable;
	private static ITable expectedTranslationsTable;
	private static ITable expectedSpecialtyTable;
	private static ITable expectedTechnicalTermTable;

	private static IDataSet actualDatabaseDataSet;
	private static IDataSet expectedDataset;
	
	@Before
	public void refreshDB() {
	
		emfactory = Persistence.createEntityManagerFactory("Eclipselink_JPA_Derby");
		entitymanager = emfactory.createEntityManager();
		connection = ((EntityManagerImpl) (entitymanager.getDelegate())).getServerSession().getAccessor().getConnection();

		try {
			ij.runScript(connection,TermBOTest.class.getResourceAsStream("/Lexicon_Database_Schema_Derby.sql"),"UTF-8", System.out, "UTF-8"); 
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
		termBOTest.createTechnicalTerm("Flüssigbeton", "flüssig", 1, 3);
		entitymanager.getTransaction().commit();
		
		actualDatabaseDataSet = mDBUnitConnection.createDataSet();
		actualTranslationsTable = actualDatabaseDataSet.getTable("TRANSLATIONS");
		actualTechnicalTermTable = actualDatabaseDataSet.getTable("TECHNICALTERM");
				
		TranslationDataset actualTranslationEntry = UtilMethods.flexibleLastTranslationSetOfITable(actualTranslationsTable, 0);
		int actualSpecialtyId = UtilMethods.flexibleLastTechnicalTermSpecialtyOfITable(actualTechnicalTermTable, 0);
				
		assertThat("Flüssigbeton", is(equalTo(actualTranslationEntry.getTranslationName())));
		assertThat("flüssig", is(equalTo(actualTranslationEntry.getTranslationDescription())));
		assertThat(1, is(equalTo(actualTranslationEntry.getLanguageId())));
		assertThat(3, is(equalTo(actualSpecialtyId)));
	}
	
	@Test(expected = TechnicalTermAlreadyExists.class)
	public void createTechnicalTerm_TechnicalTermAlreadyExistsTest() throws Exception {
						
		entitymanager.getTransaction().begin();
		termBOTest.createTechnicalTerm("Bindemittel", "flüssig", 1, 3);
		entitymanager.getTransaction().commit();
	}
	
	@Test(expected = SpecialtyDoesNotExist.class)
	public void createTechnicalTerm_SpecialtyDoesNotExistTest() throws Exception {
						
		entitymanager.getTransaction().begin();
		termBOTest.createTechnicalTerm("Flüssigbeton", "flüssig", 1, 200);
		entitymanager.getTransaction().commit();
	}
	
	@Test
	public void createSpecialtyTest() throws Exception {
		
		entitymanager.getTransaction().begin();
		termBOTest.createSpecialty("Werkzeuge", "zum Arbeiten", 1);
		entitymanager.getTransaction().commit();
		
		actualDatabaseDataSet = mDBUnitConnection.createDataSet();
		actualTranslationsTable = actualDatabaseDataSet.getTable("TRANSLATIONS");
				
		TranslationDataset actualTranslationEntry = UtilMethods.flexibleLastTranslationSetOfITable(actualTranslationsTable, 0);
				
		assertThat("Werkzeuge", is(equalTo(actualTranslationEntry.getTranslationName())));
		assertThat("zum Arbeiten", is(equalTo(actualTranslationEntry.getTranslationDescription())));
		assertThat(1, is(equalTo(actualTranslationEntry.getLanguageId())));
	}
	
	@Test(expected = SpecialtyAlreadyExists.class)
	public void createSpecialty_SpecialtyAlreadyExistsTest() throws Exception {
						
		entitymanager.getTransaction().begin();
		termBOTest.createSpecialty("Beton", "zum Arbeiten", 1);
		entitymanager.getTransaction().commit();
	}
	
	@Test
	public void createTechnicalTermTranslationTest() throws Exception {
		
		entitymanager.getTransaction().begin();
		termBOTest.createTechnicalTermTranslation(14, "SpaRolladen", "SpaZum Schutz der Fenster", 2);
		entitymanager.getTransaction().commit();
		
		actualDatabaseDataSet = mDBUnitConnection.createDataSet();
		actualTranslationsTable = actualDatabaseDataSet.getTable("TRANSLATIONS");
		actualTechnicalTermTable = actualDatabaseDataSet.getTable("TECHNICALTERM");
				
		TranslationDataset actualTranslationEntry = UtilMethods.flexibleLastTranslationSetOfITable(actualTranslationsTable, 0);
		int actualSpecialtyId = UtilMethods.flexibleLastTechnicalTermSpecialtyOfITable(actualTechnicalTermTable, 0);
				
		assertThat("SpaRolladen", is(equalTo(actualTranslationEntry.getTranslationName())));
		assertThat("SpaZum Schutz der Fenster", is(equalTo(actualTranslationEntry.getTranslationDescription())));
		assertThat(2, is(equalTo(actualTranslationEntry.getLanguageId())));
	}
	
	@Test(expected = TechnicalTermDoesNotExist.class)
	public void createTechnicalTermTranslation_TechnicalTermDoesNotExistTest() throws Exception {
						
		entitymanager.getTransaction().begin();
		termBOTest.createTechnicalTermTranslation(100, "SpaRolladen", "SpaZum Schutz der Fenster", 2);
		entitymanager.getTransaction().commit();
	}
	
	@Test(expected = LanguageEntryInTechnicalTermAlreadyExists.class)
	public void createTechnicalTermTranslation_LanguageEntryInTechnicalTermAlreadyExistsTest() throws Exception {
						
		entitymanager.getTransaction().begin();
		termBOTest.createTechnicalTermTranslation(13, "Conglomerante", "SpaZum fest werden", 2);
		entitymanager.getTransaction().commit();
	}
	
	@Test
	public void createSpecialtyTranslationTest() throws Exception {
		
		entitymanager.getTransaction().begin();
		termBOTest.createSpecialtyTranslation(4, "SpaFassade", "SpaFront", 2);
		entitymanager.getTransaction().commit();
				
		actualDatabaseDataSet = mDBUnitConnection.createDataSet();
		actualTranslationsTable = actualDatabaseDataSet.getTable("TRANSLATIONS");
				
		TranslationDataset actualTranslationEntry = UtilMethods.flexibleLastTranslationSetOfITable(actualTranslationsTable, 0);
				
		assertThat("SpaFassade", is(equalTo(actualTranslationEntry.getTranslationName())));
		assertThat("SpaFront", is(equalTo(actualTranslationEntry.getTranslationDescription())));
		assertThat(2, is(equalTo(actualTranslationEntry.getLanguageId())));
	}
	
	@Test(expected = SpecialtyDoesNotExist.class)
	public void createSpecialtyTranslation_SpecialtyDoesNotExistTest() throws Exception {
						
		entitymanager.getTransaction().begin();
		termBOTest.createSpecialtyTranslation(400, "SpaFassade", "SpaFront", 2);
		entitymanager.getTransaction().commit();
	}
	
	@Test(expected = LanguageEntryInSpecialtyAlreadyExists.class)
	public void createSpecialtyTranslation_LanguageEntryInSpecialtyAlreadyExistsTest() throws Exception {
						
		entitymanager.getTransaction().begin();
		termBOTest.createSpecialtyTranslation(3, "SpaBeton", "SpaZum bauen", 2);
		entitymanager.getTransaction().commit();
	}
	
	@Test
	public void deleteSpecialtyTest() throws Exception {
		
		entitymanager.getTransaction().begin();
		termBOTest.deleteSpecialty(3);
		entitymanager.getTransaction().commit();
					
		actualDatabaseDataSet = mDBUnitConnection.createDataSet();
		actualTranslationsTable = actualDatabaseDataSet.getTable("TRANSLATIONS");
		actualSpecialtyTable = actualDatabaseDataSet.getTable("SPECIALTY");
			
		expectedDataset = new FlatXmlDataSetBuilder().build(new File("./src/test/resources/XML/Term/testSet_termBO_DeleteSpecialtyTest.xml"));
		expectedTranslationsTable = expectedDataset.getTable("TRANSLATIONS");
		expectedSpecialtyTable = expectedDataset.getTable("SPECIALTY");
				
		Assertion.assertEquals(expectedTranslationsTable, actualTranslationsTable);	
		Assertion.assertEquals(expectedSpecialtyTable, actualSpecialtyTable);	
	}
	
	@Test(expected = SpecialtyDoesNotExist.class)
	public void deleteSpecialty_SpecialtyDoesNotExistTest() throws Exception {
						
		entitymanager.getTransaction().begin();
		termBOTest.deleteSpecialty(300);
		entitymanager.getTransaction().commit();
	}
	
	@Test
	public void deleteTechnicalTermTest() throws Exception {
		
		entitymanager.getTransaction().begin();
		termBOTest.deleteTechnicalTerm(12);
		entitymanager.getTransaction().commit();
		
		actualDatabaseDataSet = mDBUnitConnection.createDataSet();
		actualTranslationsTable = actualDatabaseDataSet.getTable("TRANSLATIONS");
		actualTechnicalTermTable = actualDatabaseDataSet.getTable("TECHNICALTERM");
			
		expectedDataset = new FlatXmlDataSetBuilder().build(new File("./src/test/resources/XML/Term/testSet_termBO_DeleteTechnicalTermTest.xml"));
		expectedTranslationsTable = expectedDataset.getTable("TRANSLATIONS");
		expectedTechnicalTermTable = expectedDataset.getTable("TECHNICALTERM");
				
		Assertion.assertEquals(expectedTranslationsTable, actualTranslationsTable);	
		Assertion.assertEquals(expectedTechnicalTermTable, actualTechnicalTermTable);	
	}
	
	@Test(expected = TechnicalTermDoesNotExist.class)
	public void deleteTechnicalTerm_TechnicalTermDoesNotExistTest() throws Exception {
						
		entitymanager.getTransaction().begin();
		termBOTest.deleteTechnicalTerm(120);
		entitymanager.getTransaction().commit();
	}

	@Test
	public void deleteTranslationTest() throws Exception {
		
		entitymanager.getTransaction().begin();
		termBOTest.deleteTranslation(3, 1);
		entitymanager.getTransaction().commit();
				
		actualDatabaseDataSet = mDBUnitConnection.createDataSet();
		actualTranslationsTable = actualDatabaseDataSet.getTable("TRANSLATIONS");
					
		expectedDataset = new FlatXmlDataSetBuilder().build(new File("./src/test/resources/XML/Term/testSet_termBO_DeleteTranslationTest.xml"));
		expectedTranslationsTable = expectedDataset.getTable("TRANSLATIONS");
						
		Assertion.assertEquals(expectedTranslationsTable, actualTranslationsTable);	
	}
	
	@Test(expected = TermDoesNotExist.class)
	public void deleteTranslation_TermDoesNotExistTest() throws Exception {
						
		entitymanager.getTransaction().begin();
		termBOTest.deleteTranslation(300, 1);
		entitymanager.getTransaction().commit();
	}
		
	@Test
	public void updateTranslationTest() throws Exception {
			
		entitymanager.getTransaction().begin();
		termBOTest.updateTranslation(3, "Beton updated", "description updated", 1);
		entitymanager.getTransaction().commit();
				
		actualDatabaseDataSet = mDBUnitConnection.createDataSet();
		actualTranslationsTable = actualDatabaseDataSet.getTable("TRANSLATIONS");
			
		expectedDataset = new FlatXmlDataSetBuilder().build(new File("./src/test/resources/XML/Term/testSet_termBO_updateTranslationTest.xml"));
		expectedTranslationsTable = expectedDataset.getTable("TRANSLATIONS");
				
		Assertion.assertEquals(expectedTranslationsTable, actualTranslationsTable);
	}
	
	@Test(expected = TermDoesNotExist.class)
	public void updateTranslation_TermDoesNotExistTest() throws Exception {
						
		entitymanager.getTransaction().begin();
		termBOTest.updateTranslation(300, "Beton updated", "description updated", 1);
		entitymanager.getTransaction().commit();
	}
	
	@Test
	public void selectSpecialtyByNameTest() throws Exception {
		
		entitymanager.getTransaction().begin();
		Specialty betonSpecialty = termBOTest.selectSpecialtyByName("Beton", 1);
		entitymanager.getTransaction().commit();
		
		String betonName = betonSpecialty.getTranslationList().get(0).getName();
		assertThat("Beton", is(equalTo(betonName)));		
	}
	
	@Test(expected = NoResultException.class)
	public void selectSpecialtyByName_NoResultExceptionTest() {

		entitymanager.getTransaction().begin();
		Specialty betonSpecialty = termBOTest.selectSpecialtyByName("Hammer", 1);
		entitymanager.getTransaction().commit();
	}
	
	@Test(expected = LanguageDoesNotExist.class)
	public void selectSpecialtyByName_LanguageDoesNotExistTest() {

		entitymanager.getTransaction().begin();
		Specialty betonSpecialty = termBOTest.selectSpecialtyByName("Beton", 3);
		entitymanager.getTransaction().commit();
	}
	
	@Test
	public void selectTechnicalTermByNameTest() throws Exception {
		
		entitymanager.getTransaction().begin();
		TechnicalTerm bindemittel = termBOTest.selectTechnicalTermByName("Bindemittel", 1);
		entitymanager.getTransaction().commit();
						
		String bindemittelName = bindemittel.getTranslationList().get(0).getName();
		assertThat("Bindemittel", is(equalTo(bindemittelName)));		
	}
	
	@Test(expected = NoResultException.class)
	public void selectTechnicalTermByName_NoResultExceptionTest() {

		entitymanager.getTransaction().begin();
		TechnicalTerm bindemittel = termBOTest.selectTechnicalTermByName("Fenster", 1);
		entitymanager.getTransaction().commit();
	}
	
	@Test(expected = LanguageDoesNotExist.class)
	public void selectTechnicalTermByName_LanguageDoesNotExistTest() {

		entitymanager.getTransaction().begin();
		TechnicalTerm bindemittel = termBOTest.selectTechnicalTermByName("Bindemittel", 4);
		entitymanager.getTransaction().commit();
	}
	
	@Test(expected = NoResultException.class)
	public void selectSpecialtyById_NoResultExceptionTest() {

		entitymanager.getTransaction().begin();
		Specialty noSuchSpecialty = termBOTest.selectSpecialtyById(220);
		entitymanager.getTransaction().commit();
	}
	
	@Test(expected = NoResultException.class)
	public void selectTechnicalTermByIdWithNoResultExceptionTest() {

		entitymanager.getTransaction().begin();
		TechnicalTerm noSuchTechnicalTerm = termBOTest.selectTechnicalTermById(220);
		entitymanager.getTransaction().commit();
	}
	
	@Test
	public void selectTermById() {

		entitymanager.getTransaction().begin();
		Term bindemittel = termBOTest.selectTermById(13);
		entitymanager.getTransaction().commit();
						
		String bindemittelName = bindemittel.getTranslationList().get(0).getName();
		assertThat("Bindemittel", is(equalTo(bindemittelName)));
	}
	
	@Test(expected = NoResultException.class)
	public void selectTermById_NoResultExceptionTest() {

		entitymanager.getTransaction().begin();
		Term noSuchTerm = termBOTest.selectTermById(220);
		entitymanager.getTransaction().commit();
	}
	
	@Test
	public void selectAllSpecialtiesTest() throws Exception {
		
		entitymanager.getTransaction().begin();
		List<Specialty> specialtyList = termBOTest.selectAllSpecialties();
		entitymanager.getTransaction().commit();
			
		String[] expectedArray = new String[]{"Beton", "Hormigón", "Fassade", null};
		String[] actualArray = UtilMethods.extractAllTermsNamesFromTermList(specialtyList);
		
		assertThat(expectedArray, is(equalTo(actualArray)));		
	}
					
	@Test
	public void selectAllTermTranslationsTest() throws Exception {
		
		Specialty beton = termDAOTest.selectSpecialtyById(3);
		entitymanager.getTransaction().begin();
		List<Translations> termList = termBOTest.selectAllTermTranslations(3);
		entitymanager.getTransaction().commit();
			
		String[] expectedArray = new String[]{"Beton", "Hormigón"};
		String[] actualArray = UtilMethods.extractAllTranslationNamesFromTerm(beton);
		
		assertThat(expectedArray, is(equalTo(actualArray)));		
	}
	
	@Test(expected = TermDoesNotExist.class)
	public void selectAllTermTranslations_TermDoesNotExistTest() throws Exception {
						
		entitymanager.getTransaction().begin();
		List<Translations> termList = termBOTest.selectAllTermTranslations(300);
		entitymanager.getTransaction().commit();
	}
	
	@Test
	public void assignTechnicalTermsToSpecialtyTest() throws Exception {
		
		Specialty specialtyBefore = termDAOTest.selectSpecialtyById(3);
		Specialty specialtyAfter = termDAOTest.selectSpecialtyById(4);
		TechnicalTerm bewehrung = termBOTest.selectTechnicalTermById(12);
		TechnicalTerm bindemittel = termBOTest.selectTechnicalTermById(13);
		int[] technicalTermsArray = new int[]{12, 13};
		
		entitymanager.getTransaction().begin();
		Specialty actualSpecialty = termBOTest.assignTechnicalTermsToSpecialty(technicalTermsArray, 4);
		entitymanager.getTransaction().commit();
				
		assertThat(specialtyAfter, is(equalTo(actualSpecialty)));
		assertThat(0, is(equalTo(specialtyBefore.getTechnicalTermsList().size())));
		assertThat(12, is(equalTo(specialtyAfter.getTechnicalTermsList().get(2).getId())));
		assertThat(13, is(equalTo(specialtyAfter.getTechnicalTermsList().get(3).getId())));
		assertThat(4, is(equalTo(bewehrung.getSpecialty().getId())));
		assertThat(4, is(equalTo(bindemittel.getSpecialty().getId())));
	}
		
	@After
	public void cleanTesting() {

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
