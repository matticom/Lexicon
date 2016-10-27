package repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.JOptionPane;

import globals.LanguageAlreadyExists;
import model.Languages;

// Probleme: Umlaute/ Groß- und Kleinschreibung egal

public class LanguageDAO {
	
	private EntityManager entitymanager;
	
	public LanguageDAO() {}
	
	public LanguageDAO(EntityManager entitymanager) {
		this.entitymanager = entitymanager;
	}


	public static void main(String[] args) {

		
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
		EntityManager entitymanager = emfactory.createEntityManager();
		
		
		LanguageDAO langDAO = new LanguageDAO(entitymanager);
		
		entitymanager.getTransaction().begin();
		
		try {
			
			langDAO.insertLanguage("Spanisch");
			langDAO.insertLanguage("Deutsch");
			//langDAO.updateLanguageById(1, "Russisch");
			//langDAO.updateLanguageByName("Russisch", "Deutsche");
//			System.out.println("---> selectLanguageById: " + langDAO.selectLanguageById(1).getName());
//			System.out.println("---> selectLanguageByName: " + langDAO.selectLanguageByName("Deutsch").getName());
//			langDAO.deleteLanguage("Deutsch");
			//langDAO.insertLanguage("Deutsch");
			
			
		}
		catch (LanguageAlreadyExists e) {
			JOptionPane.showMessageDialog(null, e.getMessage() , "LanguageAlreadyExists", JOptionPane.ERROR_MESSAGE);
		}
		catch (NoResultException e) {
			JOptionPane.showMessageDialog(null, e.getMessage() , "NoResultException", JOptionPane.ERROR_MESSAGE);
		}
		finally {
			
			entitymanager.getTransaction().commit();
			entitymanager.close();
			emfactory.close();
			
		}
		

	}

			
	public EntityManager getEntitymanager() {
		return entitymanager;
	}


	public void setEntitymanager(EntityManager entitymanager) {
		this.entitymanager = entitymanager;
	}
	
	public void insertLanguage(String name) throws LanguageAlreadyExists {
		
		isLanguageAlreadyExisting(name);			
		Languages lang = new Languages(name);
		entitymanager.persist(lang);			
			
	}
	
	public void deleteLanguage(String name) throws NoResultException {
		
		
		Languages lang = selectLanguageByName(name);
		entitymanager.remove(lang);
	}
	
	public Languages selectLanguageById(int id) {
		
		Languages lang = entitymanager.find(Languages.class, id);
		if (lang == null) {
			throw new NoResultException("Language ID ist nicht vorhanden!");
		}
				
		return lang;
		
	}
		
	public Languages selectLanguageByName(String name) throws NoResultException {
		
		Query query = entitymanager.createQuery("Select lang " + "from Language lang " + "where lang.name LIKE '" + name + "'");
		Languages lang = (Languages) query.getSingleResult();
		
		return lang;
	}
	
		
	public void updateLanguageById(int id, String name) throws NoResultException, LanguageAlreadyExists {
		
		isLanguageAlreadyExisting(name);
		System.out.println("bla");
		Languages lang = selectLanguageById(id);
		lang.setName(name);
	
	}
	
	public void updateLanguageByName(String name, String newName) throws NoResultException, LanguageAlreadyExists {
		
		isLanguageAlreadyExisting(newName);
		Languages lang = selectLanguageByName(name);
		lang.setName(newName);
			
	}
	
	public void isLanguageAlreadyExisting(String name) {
		
		try {
			if (selectLanguageByName(name) != null) {
				throw new LanguageAlreadyExists();
			}
		}
		catch (NoResultException e) {
			
		}
			
	}
		
}