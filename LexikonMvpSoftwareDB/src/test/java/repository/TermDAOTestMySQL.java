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
import javax.persistence.Query;

import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.mysql.MySqlDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.eclipse.persistence.internal.jpa.EntityManagerImpl;
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

public class TermDAOTestMySQL {

	private static TermDAO termDaoTest;
	private static LanguageDAO languageDaoTest;
	private Specialty specialty;
	private Translations translation;
	private Languages language;
	
		
	public static final Logger log = LoggerFactory.getLogger("TermDAOTestMySQL.class");
	
	private static IDatabaseConnection mDBUnitConnection;
    private static IDataSet mDataset;
    
	private static EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
	private static EntityManager entitymanager;
	
	
	
	@BeforeClass
	public static void prepareForTesting() {
				
		entitymanager = emfactory.createEntityManager();
		Connection connection = ((EntityManagerImpl) (entitymanager.getDelegate())).getServerSession().getAccessor().getConnection();
		
		try {
			// Load the test datasets in the database
			
			mDBUnitConnection = new DatabaseConnection(connection, "alexiconjpa");
			log.info(mDBUnitConnection.getSchema());
	        mDBUnitConnection.getConfig().setProperty(DatabaseConfig.FEATURE_ALLOW_EMPTY_FIELDS, true);
	        mDBUnitConnection.getConfig().setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new MySqlDataTypeFactory());
	        mDBUnitConnection.getConfig().setProperty(DatabaseConfig.FEATURE_QUALIFIED_TABLE_NAMES, true);
//	        FlatDtdDataSet.write(mDBUnitConnection.createDataSet(), new FileOutputStream("test.dtd"));
	       	       
			mDataset = new FlatXmlDataSetBuilder().build(new File("./src/test/resources/testSet_FullDatabase_WithOneDescription.xml")); //testSet_FullDatabase_WithOneDescription
			// Sequence Wert wird aus Tabelle gelesen und +1 dort weiter machen, am Ende +50 // Achtung max 255 Zeichen
		} catch (Exception e) {
			System.out.println("Es wurde eine Exception bei IDataConnection geworfen: "+ e.getMessage());
			e.printStackTrace();
		}
				
		log.info("IDataConnection wird aufgebaut");
		try {
			DatabaseOperation.CLEAN_INSERT.execute(mDBUnitConnection, mDataset);
		
		} catch (Exception e) {
			System.out.println("Es wurde eine Exception bei DatabaseOperation geworfen: "+ e.getMessage());
			e.printStackTrace();
		}
		
		termDaoTest = new TermDAO(entitymanager);
		languageDaoTest = new LanguageDAO(entitymanager);
		
	}

	@Before
	public void setupTestById() {

		// Als Vorbereitung für TermDAOTest ein LanguageDAO "Test"
		entitymanager.getTransaction().begin();
		try {
			languageDaoTest.selectLanguageByName("Deutsch");
		} catch (NoResultException e) {
			languageDaoTest.insertLanguage("Deutsch");
		} finally {
			language = languageDaoTest.selectLanguageByName("Deutsch");
			entitymanager.getTransaction().commit();
		}
	}

	@Test
	public void insertAndSelectByIdSpecialtyTest() {
		
		entitymanager.getTransaction().begin();
		log.info("insertAndSelectByIdSpecialtyTest():   'Treppen' wird eingefügt");
		
		termDaoTest.insertNewSpecialty("Treppenn", "hochgehen", language);
		
		Query query = entitymanager.createQuery("Select translation " + "from Translations translation " + "where translation.name LIKE 'Treppenn'");
		translation = (Translations) query.getSingleResult();
		Query query2 = entitymanager.createQuery("SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE = 'BASE TABLE'");
		List txt = query2.getResultList();
		for (Object o: txt) {
			log.info(o.toString());
		}
//		int translationIdVonSpecialtyTreppe = translation.getId();
		int specialtyIdVonSpecialtyTreppe = translation.getTerm().getId();

		specialty = termDaoTest.selectSpecialtyById(specialtyIdVonSpecialtyTreppe);

		String specialtyName = specialty.getTranslationList().get(0).getName();
		for (Translations t: specialty.getTranslationList())
			System.out.println(t.toString());

		assertThat(specialtyName, is(equalTo("Treppenn")));// equalTo(test.selectLanguageById(0).getName()));
		
		entitymanager.getTransaction().commit();
	}
	
	@After
	public void finishTestById() {
		
		
	}
	
	@Before
	public void setupTestByName() {

		
	}

	@Test
	public void insertAndSelectByNameSpecialtyTest() {
		
		entitymanager.getTransaction().begin();
		log.info("insertAndSelectByNameSpecialtyTest():   'Dach' wird eingefügt");
		
		termDaoTest.insertNewSpecialty("Dach", "oben", language);
		specialty = termDaoTest.selectSpecialtyByName("Dach", "Deutsch");
		
		String specialtyName = specialty.getTranslationList().get(0).getName();
		for (Translations t: specialty.getTranslationList())
			System.out.println(t.toString());
			
		assertThat(specialtyName, is(equalTo("Dach")));// equalTo(test.selectLanguageById(0).getName()));

		entitymanager.getTransaction().commit();
	}

	@After
	public void finishTestByName() {
		
		
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
