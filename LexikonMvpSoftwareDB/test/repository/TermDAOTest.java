package repository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.junit.Before;
import org.junit.Test;
import globals.LanguageAlreadyExists;
import model.Language;
import model.Specialty;
import model.Translation;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class TermDAOTest {

	TermDAO test;
	Specialty specialty;
	Translation translation;
	Language language;
	
	EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
	EntityManager entitymanager = emfactory.createEntityManager();	

	@Before
	public void setup() {
		
		test = new TermDAO(entitymanager);
		
		language = new Language("Deutsch");
		
		
						
	}

	@Test
	public void insertAndSelectSpecialtyTest() 
	{
		entitymanager.getTransaction().begin();
		test.insertSpecialty("Treppen", "hochgehen", language);
		entitymanager.getTransaction().commit();
				
		System.out.println("Insert Done !!!!!!");
		
		Query query = entitymanager.createQuery("Select translation " + "from Translation translation " + "where translation.name LIKE 'Treppen'");
		translation = (Translation) query.getSingleResult();
		
		int translationIdVonSpecialtyTreppe = translation.getId();
		int specialtyIdVonSpecialtyTreppe = translation.getTerm().getId();
		
		specialty = (Specialty)test.selectTechnicalTermById(specialtyIdVonSpecialtyTreppe);
		
		String specialtyName = specialty.getTranslationList().get(0).getName();
		System.out.println(specialtyName);
		
		assertThat(specialtyName, is(equalTo("Treppen")));//equalTo(test.selectLanguageById(0).getName()));
		
		
		entitymanager.close();
		emfactory.close();
	}
	
	
	

}
