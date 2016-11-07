package repository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

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
	
    
	private static EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
	private static EntityManager entitymanager;
	
	
	
	@BeforeClass
	public static void prepareForTesting() {
				
		entitymanager = emfactory.createEntityManager();
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
		log.info("insertAndSelectByIdSpecialtyTest():   'Treppe' wird eingefügt");
		
		termDaoTest.insertNewSpecialty("Treppen", "hochgehen", language);
		
		Query query = entitymanager.createQuery("Select translation " + "from Translations translation " + "where translation.name LIKE 'Treppen'");
		translation = (Translations) query.getSingleResult();
				
//		int translationIdVonSpecialtyTreppe = translation.getId();
		int specialtyIdVonSpecialtyTreppe = translation.getTerm().getId();

		specialty = termDaoTest.selectSpecialtyById(specialtyIdVonSpecialtyTreppe);

		String specialtyName = specialty.getTranslationList().get(0).getName();
		for (Translations t: specialty.getTranslationList())
			System.out.println(t.toString());

		assertThat(specialtyName, is(equalTo("Treppen")));// equalTo(test.selectLanguageById(0).getName()));
		
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
		
		entitymanager.close();
		emfactory.close();
	}

}
