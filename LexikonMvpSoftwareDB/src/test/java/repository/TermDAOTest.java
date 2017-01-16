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
import javax.persistence.NoResultException;
import javax.persistence.Persistence;

import org.eclipse.persistence.internal.jpa.EntityManagerImpl;

import org.apache.derby.tools.ij;
import org.dbunit.Assertion;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
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
import util.UtilMethods;

public class TermDAOTest {

	public static final Logger log = LoggerFactory.getLogger("TermDAO.class");
	
	private static IDatabaseConnection mDBUnitConnection;
    private static IDataSet startDataset;
        
	private static EntityManagerFactory emfactory;
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
	
	@Before
	public void refreshDB() {
	
		emfactory = Persistence.createEntityManagerFactory("Eclipselink_JPA_Derby");
		entitymanager = emfactory.createEntityManager();
		connection = ((EntityManagerImpl) (entitymanager.getDelegate())).getServerSession().getAccessor().getConnection();

		try {
			ij.runScript(connection,TermDAOTest.class.getResourceAsStream("/Lexicon_Database_Schema_Derby.sql"),"UTF-8", System.out, "UTF-8"); 
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
		
		try {
			DatabaseOperation.CLEAN_INSERT.execute(mDBUnitConnection, startDataset);
		} catch (Exception e) {
			log.error("Exception bei DBUnit/DatabaseOperation: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	@Test
	public void testInsertNewSpecialty() throws Exception {
		
		Languages de = languageDAOTest.selectLanguageById(1);
		Specialty werkzeuge = new Specialty();		
		Translations translation = new Translations("Werkzeuge", "Werkzeuge", "zum Arbeiten", de, werkzeuge);
		werkzeuge.getTranslationList().add(translation);
		
		entitymanager.getTransaction().begin();
		termDAOTest.insertNewSpecialty(werkzeuge);
		entitymanager.getTransaction().commit();
		
		actualDatabaseDataSet = mDBUnitConnection.createDataSet();
		actualTranslationsTable = actualDatabaseDataSet.getTable("TRANSLATIONS");
				
		TranslationDataset actualTranslationEntry = UtilMethods.flexibleLastTranslationSetOfITable(actualTranslationsTable, 0);
				
		assertThat("Werkzeuge", is(equalTo(actualTranslationEntry.getTranslationName())));
		assertThat("zum Arbeiten", is(equalTo(actualTranslationEntry.getTranslationDescription())));
		assertThat(de.getId(), is(equalTo(actualTranslationEntry.getLanguageId())));
	}
	
	@Test
	public void testInsertSpecialtyTranslation() throws Exception {
		
		Languages es = languageDAOTest.selectLanguageById(2);
		Specialty beton = termDAOTest.selectSpecialtyById(3);
		Translations translation = new Translations("SpaBeton", "SpaBeton", "SpaHartesZeugs", es, beton);
		beton.getTranslationList().add(translation);
		
		entitymanager.getTransaction().begin();
		termDAOTest.insertSpecialtyTranslation(beton, translation);
		entitymanager.getTransaction().commit();
				
		actualDatabaseDataSet = mDBUnitConnection.createDataSet();
		actualTranslationsTable = actualDatabaseDataSet.getTable("TRANSLATIONS");
				
		TranslationDataset actualTranslationEntry = UtilMethods.flexibleLastTranslationSetOfITable(actualTranslationsTable, 0);
				
		assertThat("SpaBeton", is(equalTo(actualTranslationEntry.getTranslationName())));
		assertThat("SpaHartesZeugs", is(equalTo(actualTranslationEntry.getTranslationDescription())));
		assertThat(es.getId(), is(equalTo(actualTranslationEntry.getLanguageId())));
	}
	
	@Test
	public void testInsertNewTechnicalTerm() throws Exception {
		
		Languages de = languageDAOTest.selectLanguageById(1);
		Specialty betonSpecialty = termDAOTest.selectSpecialtyById(3);
		TechnicalTerm fluessigbeton = new TechnicalTerm();
		fluessigbeton.setSpecialty(betonSpecialty);
		Translations translation = new Translations("Fl�ssigbeton", "Fluessigbeton", "fl�ssig", de, fluessigbeton);
		fluessigbeton.getTranslationList().add(translation);
				
		entitymanager.getTransaction().begin();
		termDAOTest.insertNewTechnicalTerm(fluessigbeton);
		entitymanager.getTransaction().commit();
		
		actualDatabaseDataSet = mDBUnitConnection.createDataSet();
		actualTranslationsTable = actualDatabaseDataSet.getTable("TRANSLATIONS");
		actualTechnicalTermTable = actualDatabaseDataSet.getTable("TECHNICALTERM");
				
		TranslationDataset actualTranslationEntry = UtilMethods.flexibleLastTranslationSetOfITable(actualTranslationsTable, 0);
		int actualSpecialtyId = UtilMethods.flexibleLastTechnicalTermSpecialtyOfITable(actualTechnicalTermTable, 0);
				
		assertThat("Fl�ssigbeton", is(equalTo(actualTranslationEntry.getTranslationName())));
		assertThat("fl�ssig", is(equalTo(actualTranslationEntry.getTranslationDescription())));
		assertThat(de.getId(), is(equalTo(actualTranslationEntry.getLanguageId())));
		assertThat(3, is(equalTo(actualSpecialtyId)));
	}
	
	@Test
	public void testInsertTechnicalTermTranslation() throws Exception {
		
		Languages es = languageDAOTest.selectLanguageById(2);
		TechnicalTerm rolladen = termDAOTest.selectTechnicalTermById(14);
		Translations translation = new Translations("SpaRolladen", "SpaRolladen", "SpaZum Schutz der Fenster", es, rolladen);
		rolladen.getTranslationList().add(translation);
		
		entitymanager.getTransaction().begin();
		termDAOTest.insertTechnicalTermTranslation(rolladen, translation);
		entitymanager.getTransaction().commit();
		
		actualDatabaseDataSet = mDBUnitConnection.createDataSet();
		actualTranslationsTable = actualDatabaseDataSet.getTable("TRANSLATIONS");
		actualTechnicalTermTable = actualDatabaseDataSet.getTable("TECHNICALTERM");
				
		TranslationDataset actualTranslationEntry = UtilMethods.flexibleLastTranslationSetOfITable(actualTranslationsTable, 0);
		int actualSpecialtyId = UtilMethods.flexibleLastTechnicalTermSpecialtyOfITable(actualTechnicalTermTable, 0);
				
		assertThat("SpaRolladen", is(equalTo(actualTranslationEntry.getTranslationName())));
		assertThat("SpaZum Schutz der Fenster", is(equalTo(actualTranslationEntry.getTranslationDescription())));
		assertThat(es.getId(), is(equalTo(actualTranslationEntry.getLanguageId())));
	}
	
	@Test
	public void testDeleteSpecialty() throws Exception {
		
		Specialty betonSpecialty = termDAOTest.selectSpecialtyById(3);
		
		entitymanager.getTransaction().begin();
		termDAOTest.deleteSpecialty(betonSpecialty);
		entitymanager.getTransaction().commit();
						
		actualDatabaseDataSet = mDBUnitConnection.createDataSet();
		actualTranslationsTable = actualDatabaseDataSet.getTable("TRANSLATIONS");
		actualSpecialtyTable = actualDatabaseDataSet.getTable("SPECIALTY");
			
		expectedDataset = new FlatXmlDataSetBuilder().build(new File("./src/test/resources/XML/Term/testSet_termDAO_DeleteSpecialtyTest.xml"));
		expectedTranslationsTable = expectedDataset.getTable("TRANSLATIONS");
		expectedSpecialtyTable = expectedDataset.getTable("SPECIALTY");
				
		Assertion.assertEquals(expectedTranslationsTable, actualTranslationsTable);	
		Assertion.assertEquals(expectedSpecialtyTable, actualSpecialtyTable);	
	}
	
	@Test
	public void testDeleteTechnicalTerm() throws Exception {
			
		TechnicalTerm bewehrung = termDAOTest.selectTechnicalTermById(12);
		
		entitymanager.getTransaction().begin();
		termDAOTest.deleteTechnicalTerm(bewehrung);
		entitymanager.getTransaction().commit();
		
		actualDatabaseDataSet = mDBUnitConnection.createDataSet();
		actualTranslationsTable = actualDatabaseDataSet.getTable("TRANSLATIONS");
		actualTechnicalTermTable = actualDatabaseDataSet.getTable("TECHNICALTERM");
			
		expectedDataset = new FlatXmlDataSetBuilder().build(new File("./src/test/resources/XML/Term/testSet_termDAO_DeleteTechnicalTermTest.xml"));
		expectedTranslationsTable = expectedDataset.getTable("TRANSLATIONS");
		expectedTechnicalTermTable = expectedDataset.getTable("TECHNICALTERM");
				
		Assertion.assertEquals(expectedTranslationsTable, actualTranslationsTable);	
		Assertion.assertEquals(expectedTechnicalTermTable, actualTechnicalTermTable);	
	}

	@Test
	public void testDeleteTranslation() throws Exception {
		
		Specialty betonSpecialty = termDAOTest.selectSpecialtyById(3);
		Translations translation = betonSpecialty.getTranslationList().get(0);
		
		entitymanager.getTransaction().begin();
		termDAOTest.deleteTranslation(translation);
		entitymanager.getTransaction().commit();
				
		actualDatabaseDataSet = mDBUnitConnection.createDataSet();
		actualTranslationsTable = actualDatabaseDataSet.getTable("TRANSLATIONS");
					
		expectedDataset = new FlatXmlDataSetBuilder().build(new File("./src/test/resources/XML/Term/testSet_termDAO_DeleteTranslationTest.xml"));
		expectedTranslationsTable = expectedDataset.getTable("TRANSLATIONS");
						
		Assertion.assertEquals(expectedTranslationsTable, actualTranslationsTable);	
	}
		
	@Test
	public void testUpdateTranslation() throws Exception {
		
		Languages de = languageDAOTest.selectLanguageById(1);
		Specialty beton = termDAOTest.selectSpecialtyById(3);
		Translations newTranslation = new Translations("Beton updated", "Beton updated", "description updated", de, beton);
		Translations translation = beton.getTranslationList().get(0);
		
		entitymanager.getTransaction().begin();
		termDAOTest.updateTranslation(translation, newTranslation);
		entitymanager.getTransaction().commit();
				
		actualDatabaseDataSet = mDBUnitConnection.createDataSet();
		actualTranslationsTable = actualDatabaseDataSet.getTable("TRANSLATIONS");
			
		expectedDataset = new FlatXmlDataSetBuilder().build(new File("./src/test/resources/XML/Term/testSet_termDAO_updateTranslationTest.xml"));
		expectedTranslationsTable = expectedDataset.getTable("TRANSLATIONS");
				
		Assertion.assertEquals(expectedTranslationsTable, actualTranslationsTable);
	}
	
	@Test(expected = NoResultException.class)
	public void testSelectSpecialtyById_NoResultException() {

		Specialty noSuchSpecialty = termDAOTest.selectSpecialtyById(220);
	}
	
	@Test(expected = NoResultException.class)
	public void testSelectTechnicalTermById_NoResultException() {

		TechnicalTerm noSuchTechnicalTerm = termDAOTest.selectTechnicalTermById(220);
	}
	
	@Test
	public void testSelectTermById() {

		entitymanager.getTransaction().begin();
		Term bindemittel = termDAOTest.selectTermById(13);
		entitymanager.getTransaction().commit();
						
		String bindemittelName = bindemittel.getTranslationList().get(0).getName();
		assertThat("Bindemittel", is(equalTo(bindemittelName)));
	}
	
	@Test(expected = NoResultException.class)
	public void testSelectTermById_NoResultException() {

		Term noSuchTerm = termDAOTest.selectTermById(220);
	}
	
	@Test
	public void testSelectAllSpecialties() throws Exception {
		
		entitymanager.getTransaction().begin();
		List<Specialty> specialtyList = termDAOTest.selectAllSpecialties();
		entitymanager.getTransaction().commit();
			
		String[] expectedArray = new String[]{"Beton", "Hormig�n", "Fassade", "Fachada"};
		String[] actualArray = UtilMethods.extractAllTermsNamesFromTermList(specialtyList);
		
		assertThat(expectedArray, is(equalTo(actualArray)));		
	}
	
	@Test
	public void testSelectAllTechnicalTerms() throws Exception {
		
		entitymanager.getTransaction().begin();
		List<TechnicalTerm> technicalTermList = termDAOTest.selectAllTechnicalTerms();
		entitymanager.getTransaction().commit();
			
		String[] expectedArray = new String[]{"Bewehrung", "Armadura", "Bindemittel", "Conglomerante", "Rolladen", "Persiana", "Floatglas", "Vidrio flotado"};
		String[] actualArray = UtilMethods.extractAllTermsNamesFromTermList(technicalTermList);
		
		assertThat(expectedArray, is(equalTo(actualArray)));		
	}
	
	@Test
	public void testSelectSpecialtyByName() throws Exception {
		
		Languages de = languageDAOTest.selectLanguageById(1);
		entitymanager.getTransaction().begin();
		Specialty betonSpecialty = termDAOTest.selectSpecialtyByName("Beton", de);
		entitymanager.getTransaction().commit();
				
		String betonName = betonSpecialty.getTranslationList().get(0).getName();
		assertThat("Beton", is(equalTo(betonName)));		
	}
	
	@Test
	public void testSelectTechnicalTermByName() throws Exception {
		
		Languages de = languageDAOTest.selectLanguageById(1);
		entitymanager.getTransaction().begin();
		TechnicalTerm bindemittelSpecialty = termDAOTest.selectTechnicalTermByName("Bindemittel", de);
		entitymanager.getTransaction().commit();
						
		String bindemittelName = bindemittelSpecialty.getTranslationList().get(0).getName();
		assertThat("Bindemittel", is(equalTo(bindemittelName)));		
	}
				
	@Test
	public void testSelectAllTermTranslations() throws Exception {
		
		Specialty beton = termDAOTest.selectSpecialtyById(3);
		
		entitymanager.getTransaction().begin();
		List<Translations> termList = termDAOTest.selectAllTermTranslations(beton);
		entitymanager.getTransaction().commit();
			
		assertThat(2, is(equalTo(termList.size())));	
		assertThat("Beton", is(equalTo(termList.get(0).getName())));	
		assertThat("Hormig�n", is(equalTo(termList.get(1).getName())));
	}
	
	@Test
	public void testSelectTranslations() throws Exception {
		
		entitymanager.getTransaction().begin();
		List<Translations> termList = termDAOTest.selectTranslations("%on%");
		entitymanager.getTransaction().commit();
			
		assertThat(3, is(equalTo(termList.size())));	
		assertThat("Beton", is(equalTo(termList.get(0).getName())));	
		assertThat("Hormig�n", is(equalTo(termList.get(1).getName())));
		assertThat("Conglomerante", is(equalTo(termList.get(2).getName())));
	}
	
		
	@After
	public void cleanTesting() {

		try {
			mDBUnitConnection.close();
		} catch (SQLException e) {
			System.out.println("Es wurde eine Exception beim Schlie�en der IDataConnection geworfen: "+ e.getMessage());
			e.printStackTrace();
		}
		
		entitymanager.close();
		emfactory.close();
	}
}
