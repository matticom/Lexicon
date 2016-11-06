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

// Probleme: Umlaute/ Groﬂ- und Kleinschreibung egal

public class LanguageDAO {

	private EntityManager entitymanager;

	public LanguageDAO() {
	}

	public LanguageDAO(EntityManager entitymanager) {
		this.entitymanager = entitymanager;
	}

	

	public EntityManager getEntitymanager() {
		
		return entitymanager;
		
	}

	public void setEntitymanager(EntityManager entitymanager) {
		
		this.entitymanager = entitymanager;
		
	}

	
	
	public void insertLanguage(String name) {

		Languages lang = new Languages(name);
		entitymanager.persist(lang);
		
	}

	public void deleteLanguage(String name) {

		Languages lang = selectLanguageByName(name);
		entitymanager.remove(lang);
		
	}

	public void updateLanguage(String name, String newName) {

		Languages lang = selectLanguageByName(name);
		lang.setName(newName);

	}

	public Languages selectLanguageById(int id) {

		Languages lang = entitymanager.find(Languages.class, id);
		if (lang == null) {
			throw new NoResultException("Language ID ist nicht vorhanden!");
		}

		return lang;

	}

	public Languages selectLanguageByName(String name) throws NoResultException {

		Query query = entitymanager.createQuery("Select lang from Language lang " + "where lang.name LIKE '" + name + "'");
		Languages lang = (Languages) query.getSingleResult();

		return lang;
		
	}
	
	public Languages[] selectAllLanguages() {

		Query query = entitymanager.createQuery("Select lang from Language lang");
		List<Languages> langList = query.getResultList();
		Languages[] languages = langList.toArray(new Languages[langList.size()]);
		return languages;
		
	}

}
	