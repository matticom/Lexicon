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
		
		entitymanager.persist(lang); //--> wenn es hinzugef�gt werden muss --> BO muss vorher �berpr�fen, hier zu komplex
		
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
		
		
		//entitymanager.persist(lang); --> wenn es hinzugef�gt werden muss --> BO muss vorher �berpr�fen, hier zu komplex
		//vorher abpr�fen: existiert schon sprache, wenn nicht anlegen; existiert schon specialty, wenn nicht anlegen 
		// (Standard: persist muss nich, durchgef�hrt werden -> nur von technicalterm und translation)
		
		
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
	
	public TechnicalTerm selectTechnicalTermById(int id) {
		
		TechnicalTerm technicalTerm = entitymanager.find(TechnicalTerm.class, id);
		if (technicalTerm == null) {
			throw new NoResultException("TechnicalTerm ID ist nicht vorhanden!");
		}
				
		return technicalTerm;
		
	}
		
	public Specialty selectSpecialtyByName(String name) throws NoResultException {
		
		Query query = entitymanager.createQuery("Select translation " + "from Translation translation " + "where translation.name LIKE '" + name + "'");
		Translation translation = (Translation) query.getSingleResult();
		
		Specialty specialty = (Specialty)translation.getTerm();
		return specialty;
	}
	
	public TechnicalTerm selectTechnicalTermByName(String name) throws NoResultException {
		
		Query query = entitymanager.createQuery("Select translation " + "from Translation translation " + "where translation.name LIKE '" + name + "'");
		Translation translation = (Translation) query.getSingleResult();
		
		TechnicalTerm technicalTerm = (TechnicalTerm)translation.getTerm();
		return technicalTerm;
	}
	
//	n�chsth�here Ebene:
	
//	public TechnicalTermDataset selectTechnicalTerm(String name, Language language) throws NoResultException {  
//		
//		Query query = entitymanager.createQuery("Select translation " + "from Translation translation " + "where lang.name LIKE '" + name + "' and ");
//		Language lang = (Language) query.getSingleResult();
//	}
}
