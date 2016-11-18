package repository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.eclipse.persistence.internal.jpa.EntityManagerImpl;

import org.apache.derby.tools.ij;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.stream.IDataSetProducer;
import org.dbunit.dataset.stream.StreamingDataSet;
import org.dbunit.dataset.xml.FlatDtdDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.dataset.xml.FlatXmlProducer;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;

import model.Languages;
import model.Specialty;
import model.TechnicalTerm;
import model.Translations;

public class TermDAOTest {

	private static TermDAO test;
	private static LanguageDAO langTest;
	private Specialty specialty;
	private Translations translation;
	private Languages language;
	private int languageID;
	
	
	// +++ new Parts from Tut for InMemoryDB
	
	public static final Logger log = LoggerFactory.getLogger("TermDAOTest.class");
	/** Connection to the database. */
    
	private static IDatabaseConnection mDBUnitConnection;
    /** Test dataset. */
    private static IDataSet mDataset;
        
	private static EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("Eclipselink_JPA_Derby"); //("Eclipselink_JPA_Derby");
	private static EntityManager entitymanager;
	
	
	
	@BeforeClass
	public static void prepareForTesting() {
		
		
		
		entitymanager = emfactory.createEntityManager();
		Connection connection = ((EntityManagerImpl) (entitymanager.getDelegate())).getServerSession().getAccessor().getConnection();
		
		try {
			// Load the test datasets in the database
			mDBUnitConnection = new DatabaseConnection(connection);
	        mDBUnitConnection.getConfig().setProperty(DatabaseConfig.FEATURE_ALLOW_EMPTY_FIELDS, true);
	        log.info(mDBUnitConnection.getSchema());
//	        DataFileLoader loader = new FlatXmlDataFileLoader();
//	        IDataSet ds = loader.load("/the/package/prepData.xml");   
	        
			mDataset = new FlatXmlDataSetBuilder().build(new File("./src/test/resources/testSet_FullDatabase_WithOneDescription.xml")); //testSet_FullDatabase_WithOneDescription
			// Sequence Wert wird aus Tabelle gelesen und +1 dort weiter machen, am Ende +50
		} catch (Exception e) {
			System.out.println("Es wurde eine Exception bei IDataConnection geworfen: "+ e.getMessage());
			e.printStackTrace();
		}
		
		test = new TermDAO(entitymanager);
		langTest = new LanguageDAO(entitymanager);
		
		log.info("IDataConnection wird aufgebaut");
		try {
			DatabaseOperation.CLEAN_INSERT.execute(mDBUnitConnection, mDataset);
		
		} catch (Exception e) {
			System.out.println("Es wurde eine Exception bei DatabaseOperation geworfen: "+ e.getMessage());
			e.printStackTrace();
		}
		
	}

	@Before
	public void setupById() {

		entitymanager.getTransaction().begin();
		language = langTest.selectLanguageByName("Deutsch");
		
		Languages[] langArr = langTest.selectAllLanguages();
		for (Languages lang: langArr)
			log.info(lang.getName());
		
		entitymanager.getTransaction().commit();

		
		
	}
	
	
	@Test
	public void insertAndSelectByIdSpecialtyTest() {
		
		entitymanager.getTransaction().begin();
		log.info("insertAndSelectByIdSpecialtyTest():   'Treppe' wird eingefügt");
		
		test.insertNewSpecialty("Treppenn", "hochgehen", language);
		
		specialty = test.selectSpecialtyByName("Treppenn", "Deutsch");
		log.info("SpecialtyID : " + specialty.getId() + "    " + specialty.getTranslationList().get(0).toString());
		log.info("TranslationsID : " + specialty.getTranslationList().get(0).getId());
		languageID = language.getId();
		log.info(languageID + " : " + language.toString());
		
		Query query = entitymanager.createQuery("Select translation " + "from Translations translation " + "where translation.name LIKE 'Treppenn'");
		translation = (Translations) query.getSingleResult();

		int specialtyIdVonSpecialtyTreppe = translation.getTerm().getId();

		specialty = test.selectSpecialtyById(specialtyIdVonSpecialtyTreppe);

		String specialtyName = specialty.getTranslationList().get(0).getName();
		for (Translations t: specialty.getTranslationList())
			System.out.println(t.toString());
		
		log.info(Integer.valueOf(languageID).toString());
		assertThat(specialtyName, is(equalTo("Treppenn")));// equalTo(test.selectLanguageById(0).getName()));
		entitymanager.getTransaction().commit();
	}
	
	@After
	public void finishById() {
		
		
	}
	
	@Before
	public void setupByName() {

		
	}

	@Test
	public void insertAndSelectByNameSpecialtyTest() {
		
		entitymanager.getTransaction().begin();
		log.info("insertAndSelectByNameSpecialtyTest():   'Dach' wird eingefügt");
		
		test.insertNewSpecialty("Dach", "oben", language);
		entitymanager.getTransaction().commit();
		
		specialty = test.selectSpecialtyByName("Dach", "Deutsch");
		log.info("SpecialtyID : " + specialty.getId() + "    " + specialty.getTranslationList().get(0).toString());
		log.info("TranslationsID : " + specialty.getTranslationList().get(0).getId());
		languageID = language.getId();
		log.info(languageID + " : " + language.toString());
		
		specialty = test.selectSpecialtyByName("Dach", "Deutsch");
		//entitymanager.merge(specialty);
				
		String specialtyName = specialty.getTranslationList().get(0).getName();
		for (Translations t: specialty.getTranslationList())
			System.out.println(t.toString());
				
		log.info(Integer.valueOf(languageID).toString());
		assertThat(specialtyName, is(equalTo("Dach")));// equalTo(test.selectLanguageById(0).getName()));
	
	}
	
	@Test
	public void selectByNameSpecialtyFromDataSetTest() {
		
		log.info("selectByNameSpecialtyFromDataSetTest():   beginnt......");
		
//		entitymanager.getTransaction().begin();
//						
//		specialty = test.selectSpecialtyByName("Fenster", "Deutsch");
//		entitymanager.getTransaction().commit();
//		
//		
//		log.info("SpecialtyID : " + specialty.getId() + "    " + specialty.getTranslationList().get(0).toString());
//		log.info("TranslationsID : " + specialty.getTranslationList().get(0).getId());
//		languageID = language.getId();
//		log.info(languageID + " : " + language.toString());
		//entitymanager.merge(specialty);
				
//		String specialtyName = specialty.getTranslationList().get(0).getName();
//		for (Translations t: specialty.getTranslationList())
//			System.out.println(t.toString());
//				
//		log.info(Integer.valueOf(languageID).toString());
		
		entitymanager.getTransaction().begin();
		Languages langDE = langTest.selectLanguageByName("Deutsch");
		entitymanager.getTransaction().commit();
		
		entitymanager.getTransaction().begin();
		Languages langES = langTest.selectLanguageByName("Spanisch");
		entitymanager.getTransaction().commit();
		
		
		entitymanager.getTransaction().begin();
		Specialty betonDE = test.insertNewSpecialty("Betonm", "fest", langDE);
		entitymanager.getTransaction().commit();
					
		entitymanager.getTransaction().begin();
		Specialty betonES = test.insertSpecialtyTranslation("Betonm", "Deutsch", "SpaBeton", "SpaDescription", langES);
		entitymanager.getTransaction().commit();
		
		entitymanager.getTransaction().begin();
		Specialty fenster = test.insertNewSpecialty("Fenster", "Glas", langDE);
		entitymanager.getTransaction().commit();
					
		entitymanager.getTransaction().begin();
		TechnicalTerm bewaehrungDE = test.insertNewTechnicalTerm("Bewaehrung", "Stahlzeugs", betonDE, langDE);
		entitymanager.getTransaction().commit();
		
		entitymanager.getTransaction().begin();
		TechnicalTerm bewaehrungES = test.insertTechnicalTermTranslation("Bewaehrung", "Deutsch", "SpaBewaehrung", "SpaStahlzeugs", langES);
		entitymanager.getTransaction().commit();
		
		assertThat(betonDE, is(equalTo(betonES)));// equalTo(test.selectLanguageById(0).getName()));
	}

	@After
	public void finishByName() {
		
		
	}
	
	@AfterClass
	public static void cleanTesting() {
		
		
		try {
			IDataSet fullDataSet = mDBUnitConnection.createDataSet();
			FlatXmlDataSet.write(fullDataSet, new FileOutputStream("full.xml"));
			
		} catch (Exception e) {
			System.out.println("Es wurde eine Exception bei FullDataSetXML geworfen: "+ e.getMessage());
			e.printStackTrace();
		}
		entitymanager.close();
		emfactory.close();
		try {
			mDBUnitConnection.close();
		} catch (SQLException e) {
			System.out.println("Es wurde eine Exception beim Schließen der IDataConnection geworfen: "+ e.getMessage());
			e.printStackTrace();
		}
		
		
	}
		
}
