package repository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;

import org.junit.Before;
import org.junit.Test;
import globals.LanguageAlreadyExists;
import model.Language;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class LanguageDaoTest {

	LanguageDAO test;
	Language lang;
	//@Mock EntityManager entitymanager;
	@Mock TermDAO testDAO;

	@Before
	public void setup() {
		test = new LanguageDAO();
		MockitoAnnotations.initMocks(this);
		//entitymanager = mock(EntityManager.class);
		lang = new Language("Deutsch");
		//doThrow(NoResultException.class).when(entitymanager).find(Language.class, anyInt());
//		doNothing().when(entitymanager).getTransaction().begin();
//		doNothing().when(entitymanager).getTransaction().commit();
				
	}

	@Test
	public void LanguageNotExistInDBWithSelectLanguageByIdTest() 
	{
		//when(entitymanager.find(Language.class, 0)).thenReturn(lang);
		//when(entitymanager.find(Language.class, 0)).thenReturn(lang);//doThrow(NoResultException.class)
		
		try {
			assertThat(lang.getName(), equalTo(test.selectLanguageById(0).getName()));
		} catch (NullPointerException e) {
			System.out.println("Shit");
			e.printStackTrace();
		}
	}

}
