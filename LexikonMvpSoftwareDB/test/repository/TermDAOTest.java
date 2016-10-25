package repository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.apache.derby.tools.ij;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import model.Language;
import model.Specialty;
import model.Translation;

public class TermDAOTest {

	private static TermDAO test;
	private Specialty specialty;
	private Translation translation;
	private Language language;

	private static EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
	private static EntityManager entitymanager;
	
	@BeforeClass
	public static void prepareForTesting() {
		
		entitymanager = emfactory.createEntityManager();
		test = new TermDAO(entitymanager);
	}

	@Before
	public void setupById() {

		
		
		language = new Language("Deutsch");
	}

	@Test
	public void insertAndSelectByIdSpecialtyTest() {
		
		entitymanager.getTransaction().begin();
		test.insertSpecialty("Treppen", "hochgehen", language);
		entitymanager.getTransaction().commit();

		System.out.println("Insert Done !!!!!!");

		Query query = entitymanager.createQuery("Select translation " + "from Translation translation " + "where translation.name LIKE 'Treppen'");
		translation = (Translation) query.getSingleResult();

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

		language = new Language("Deutsch");
	}

	@Test
	public void insertAndSelectByNameSpecialtyTest() {
		
		entitymanager.getTransaction().begin();
		test.insertSpecialty("Dach", "oben", language);
		entitymanager.getTransaction().commit();

		System.out.println("Insert2 Done !!!!!!");

		specialty = test.selectSpecialtyByName("Dach");

		String specialtyName = specialty.getTranslationList().get(0).getName();
		for (Translation t: specialty.getTranslationList())
			System.out.println(t.toString());
		System.out.println("\n\n\n\n" + specialtyName);
		Logger log = Logger.getLogger("TermDAOTest.SecondTest");
		log.logp(Level.FINE, "TermDAOTest", "insertAndSelectByNameSpecialtyTest", "Ist hier vorbei gekommen");
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
