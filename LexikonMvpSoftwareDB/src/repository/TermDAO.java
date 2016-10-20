package repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.JOptionPane;

import TransferObjects.TechnicalTermDataset;
import globals.LanguageAlreadyExists;
import model.Language;
import model.Specialty;
import model.TechnicalTerm;
import model.Term;
import model.Translation;

public class TermDAO {

	private EntityManager entitymanager;
	
	public TermDAO() {}
	
	public TermDAO(EntityManager entitymanager) {
		this.entitymanager = entitymanager;
	}

	public static void main(String[] args) {

		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
		EntityManager entitymanager = emfactory.createEntityManager();
		
		TermDAO termDao = new TermDAO(entitymanager);
		
		Language langES = (new LanguageDAO(entitymanager)).selectLanguageById(1);
				
		entitymanager.getTransaction().begin();

		try {

			termDao.insertSpecialty("Beton", "fest", langES);
			
			Specialty specialty = entitymanager.find(Specialty.class, 101);
			
			termDao.insertTechnicalTerm("Bewaehrung", "Stahlzeugs", specialty, langES);
			
			System.out.println(specialty.getId());
			System.out.println(specialty.getTranslationList().size());
			System.out.println("iiiiiiiiiiiiiiiiiiiiiiiinfo" + specialty);
			

		} catch (NoResultException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "NoResultException", JOptionPane.ERROR_MESSAGE);
		} finally {

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
	
	public void insertSpecialty(String name, String description, Language lang) {
				
		Specialty specialty = new Specialty();
		
		Translation translation = new Translation(name, description, lang, specialty);
				
		List<Translation> transList;
		transList = specialty.getTranslationList();
		transList.add(translation);
				
		entitymanager.persist(specialty);
		
		entitymanager.persist(lang); //--> wenn es hinzugefügt werden muss --> BO muss vorher überprüfen, hier zu komplex
		
	}
	
	public void insertTechnicalTerm(String name, String description, Specialty specialty, Language lang) {
					
		TechnicalTerm technicalTerm = new TechnicalTerm();
				
		Translation translation = new Translation(name, description, lang, technicalTerm);
		
		List<Translation> transList;
		transList = technicalTerm.getTranslationList();
		transList.add(translation);
		
		if (specialty != null) {
			technicalTerm.setSpecialty(specialty);
			entitymanager.persist(specialty);
		}
		
		entitymanager.persist(technicalTerm);
		
		
		//entitymanager.persist(lang); --> wenn es hinzugefügt werden muss --> BO muss vorher überprüfen, hier zu komplex
		//vorher abprüfen: existiert schon sprache, wenn nicht anlegen; existiert schon specialty, wenn nicht anlegen 
		// (Standard: persist muss nich, durchgeführt werden -> nur von technicalterm und translation)
		
		
	}
	
	
	
	public void deleteSpecialty(String name) throws NoResultException {
		
		// public void deleteLinks(specialty.getTechnicalTermsList()) aus BO --> technicalterm suchen, verweis auf Specialty entfernen
		// --> dann diese Funktion
		
//		Specialty specialty = selectLanguageByName(name);
//		entitymanager.remove(lang);
			
	}
	
	public Specialty selectSpecialtyById(int id) {
		
		Specialty specialty = entitymanager.find(Specialty.class, id);
		if (specialty == null) {
			throw new NoResultException("Specialty ID ist nicht vorhanden!");
		}
				
		return specialty;
		
	}
	
	public Term selectTechnicalTermById(int id) {
		
		Term specialty = entitymanager.find(Term.class, id);
		if (specialty == null) {
			throw new NoResultException("Specialty ID ist nicht vorhanden!");
		}
				
		return specialty;
		
	}
		
	public Language selectLanguageByName(String name) throws NoResultException {
		
		Query query = entitymanager.createQuery("Select lang " + "from Language lang " + "where lang.name LIKE '" + name + "'");
		Language lang = (Language) query.getSingleResult();
		
		return lang;
	}
	
//	nächsthöhere Ebene:
	
//	public TechnicalTermDataset selectTechnicalTerm(String name, Language language) throws NoResultException {  
//		
//		Query query = entitymanager.createQuery("Select translation " + "from Translation translation " + "where lang.name LIKE '" + name + "' and ");
//		Language lang = (Language) query.getSingleResult();
//	}
}
