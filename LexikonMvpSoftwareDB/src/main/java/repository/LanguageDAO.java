package repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.swing.JOptionPane;

import globals.LanguageAlreadyExists;
import model.Languages;
import model.Languages_;
import model.Translations;
import model.Translations_;

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

	
	
	public Languages insertLanguage(Languages language) {

		entitymanager.persist(language);
		return language;
	}

	public void deleteLanguage(Languages language) {

		entitymanager.remove(language);
	}

	public Languages updateLanguage(Languages language, String newName) {

		language.setName(newName);
		return language;
	}

	public Languages selectLanguageById(int id) {

		Languages lang = entitymanager.find(Languages.class, id);
		if (lang == null) {
			throw new NoResultException("Language ID ist nicht vorhanden!");
		}
		return lang;
	}

	public Languages selectLanguageByName(String name) throws NoResultException {

//		Query query = entitymanager.createQuery("Select lang from Languages lang " + "where lang.name LIKE '" + name + "'");
//		Languages lang = (Languages) query.getSingleResult();
		
		CriteriaBuilder criteriaBuilder = entitymanager.getCriteriaBuilder();
		CriteriaQuery<Languages> criteriaQuery = criteriaBuilder.createQuery(Languages.class);
				
		Root<Languages> language = criteriaQuery.from(Languages.class);
				
		Predicate selectLanguage = criteriaBuilder.like(language.get(Languages_.name), name);
        criteriaQuery.select(language).where(selectLanguage);
        
        Languages languageResult = entitymanager.createQuery(criteriaQuery).getSingleResult();
        
        return languageResult;
		
	}
	
	public List<Languages> selectAllLanguages() {

//		Query query = entitymanager.createQuery("Select lang from Languages lang");
//		List<Languages> langList = query.getResultList();
		
		CriteriaBuilder criteriaBuilder = entitymanager.getCriteriaBuilder();
		CriteriaQuery<Languages> criteriaQuery = criteriaBuilder.createQuery(Languages.class);
				
		Root<Languages> language = criteriaQuery.from(Languages.class);
		criteriaQuery.select(language);
		
		List<Languages> languageList = entitymanager.createQuery(criteriaQuery).getResultList();
						
		return languageList;
		
	}

}
	