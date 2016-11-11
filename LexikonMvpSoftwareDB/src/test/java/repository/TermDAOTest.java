package repository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.sql.Connection;
import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.eclipse.persistence.internal.jpa.EntityManagerImpl;

import org.apache.derby.tools.ij;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.Languages;
import model.Specialty;
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
    
    /** script to clean up and prepare the DB. */
    private static String mDDLFileName = "/lexiconjpaDerby.sql";//"/createStudentsDB_DERBY.sql";//"/lexiconjpaDerby.sql";

    // +++ end new Parts
    
	private static EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("Eclipselink_JPA_Derby");
	private static EntityManager entitymanager;
	
	
	
	@BeforeClass
	public static void prepareForTesting() {
		
		
		
		entitymanager = emfactory.createEntityManager();
		Connection connection = ((EntityManagerImpl) (entitymanager.getDelegate())).getServerSession().getAccessor().getConnection();
		
		try {
			ij.runScript(connection,TermDAOTest.class.getResourceAsStream(mDDLFileName),
			        "UTF-8", System.out, "UTF-8");
			// Load the test datasets in the database
	        
		} catch (Exception e) {
			System.out.println("Es wurde eine Exception bei ij geworfen: "+ e.getMessage());
			e.printStackTrace();
		}
		
		
		try {
			// Load the test datasets in the database
	        mDBUnitConnection = new DatabaseConnection(connection);
	        mDataset = new FlatXmlDataSetBuilder().build(Thread.currentThread().getContextClassLoader().getResourceAsStream("test1-datasets.xml"));
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
//		Query query = entitymanager.createQuery("Select sequence from SEQUENCE sequence");
//		Sequence seq = (Sequence) query.getSingleResult();
//		log.info(seq.seq_name);
//		log.info(" " + seq.seq_count);
		
		
	}

	@Test
	public void insertAndSelectByIdSpecialtyTest() {
		
		entitymanager.getTransaction().begin();
		log.info("insertAndSelectByIdSpecialtyTest():   'Treppe' wird eingefügt");
		
		test.insertNewSpecialty("Treppen", "hochgehen", language);
		
		specialty = test.selectSpecialtyByName("Treppen", "Deutsch");
		log.info("SpecialtyID : " + specialty.getId() + "    " + specialty.getTranslationList().get(0).toString());
		log.info("TranslationsID : " + specialty.getTranslationList().get(0).getId());
		languageID = language.getId();
		log.info(languageID + " : " + language.toString());
		
		Query query = entitymanager.createQuery("Select translation " + "from Translations translation " + "where translation.name LIKE 'Treppen'");
		translation = (Translations) query.getSingleResult();

		int specialtyIdVonSpecialtyTreppe = translation.getTerm().getId();

		specialty = test.selectSpecialtyById(specialtyIdVonSpecialtyTreppe);

		String specialtyName = specialty.getTranslationList().get(0).getName();
		for (Translations t: specialty.getTranslationList())
			System.out.println(t.toString());
		
		log.info(Integer.valueOf(languageID).toString());
		assertThat(specialtyName, is(equalTo("Treppen")));// equalTo(test.selectLanguageById(0).getName()));
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
		
		entitymanager.getTransaction().begin();
						
		specialty = test.selectSpecialtyByName("Fenster", "Deutsch");
		entitymanager.getTransaction().commit();
		
		
		log.info("SpecialtyID : " + specialty.getId() + "    " + specialty.getTranslationList().get(0).toString());
		log.info("TranslationsID : " + specialty.getTranslationList().get(0).getId());
		languageID = language.getId();
		log.info(languageID + " : " + language.toString());
		//entitymanager.merge(specialty);
				
		String specialtyName = specialty.getTranslationList().get(0).getName();
		for (Translations t: specialty.getTranslationList())
			System.out.println(t.toString());
				
		log.info(Integer.valueOf(languageID).toString());
		assertThat(specialtyName, is(equalTo("Fenster")));// equalTo(test.selectLanguageById(0).getName()));
	}

	@After
	public void finishByName() {
		
		
	}
	
	@AfterClass
	public static void cleanTesting() {
		
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
