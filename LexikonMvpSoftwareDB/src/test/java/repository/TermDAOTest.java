package repository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.util.logging.Level;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.eclipse.persistence.internal.jpa.EntityManagerImpl;

import org.apache.derby.tools.ij;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
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
	private Specialty specialty;
	private Translations translation;
	private Languages language;
	
	// +++ new Parts from Tut for InMemoryDB
	
	public static final Logger log = LoggerFactory.getLogger(TermDAOTest.class);
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
		} catch (Exception e) {
			System.out.println("Es wurde eine Exception bei ij geworfen: "+ e.getMessage());
			e.printStackTrace();
		}
		
		test = new TermDAO(entitymanager);
		
		
	}

	@Before
	public void setupById() {

		
		
		language = new Languages("Deutsch");
	}

	@Test
	public void insertAndSelectByIdSpecialtyTest() {
		
		entitymanager.getTransaction().begin();
		test.insertSpecialty("Treppen", "hochgehen", language);
		entitymanager.getTransaction().commit();

		System.out.println("Insert Done !!!!!!");

		Query query = entitymanager.createQuery("Select translation " + "from Translation translation " + "where translation.name LIKE 'Treppen'");
		translation = (Translations) query.getSingleResult();

		int translationIdVonSpecialtyTreppe = translation.getId();
		int specialtyIdVonSpecialtyTreppe = translation.getTerm().getId();

		specialty = test.selectSpecialtyById(specialtyIdVonSpecialtyTreppe);

		String specialtyName = specialty.getTranslationList().get(0).getName();
		System.out.println(specialtyName);

		assertThat(specialtyName, is(equalTo("Treppen")));// equalTo(test.selectLanguageById(0).getName()));

	}
	
	@After
	public void finishById() {
		
		
	}
	
	@Before
	public void setupByName() {

		language = new Languages("Deutsch");
	}

	@Test
	public void insertAndSelectByNameSpecialtyTest() {
		
		entitymanager.getTransaction().begin();
		test.insertSpecialty("Dach", "oben", language);
		log.debug("nach Insert!");
//		entitymanager.getTransaction().commit();
		
		System.out.println("Insert2 Done !!!!!!");

//		entitymanager.getTransaction().begin();
		specialty = test.selectSpecialtyByName("Dach");
		System.out.println(specialty+"!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		entitymanager.getTransaction().commit();
		
		String specialtyName = specialty.getTranslationList().get(0).getName();
		for (Translations t: specialty.getTranslationList())
			System.out.println(t.toString());
		System.out.println("\n\n\n\n" + specialtyName);
		
		
		log.info(specialtyName);
		assertThat(specialtyName, is(equalTo("Dach")));// equalTo(test.selectLanguageById(0).getName()));

//		ij.startJBMS();
		
	}

	@After
	public void finishByName() {
		
		
	}
	
	@AfterClass
	public static void cleanTesting() {
		
		entitymanager.close();
		emfactory.close();
	}

}
