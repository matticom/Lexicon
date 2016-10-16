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
			Specialty specialty = entitymanager.find(Specialty.class, 51);
			System.out.println(specialty.getId());
			System.out.println(specialty.getTranslationList().size());
			//System.out.println("iiiiiiiiiiiiiiiiiiiiiiiinfo" + specialty);
			

		} catch (NoResultException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "NoResultException", JOptionPane.ERROR_MESSAGE);
		} finally {

			entitymanager.getTransaction().commit();
			entitymanager.close();
			emfactory.close();

		}

	}

	public void insertSpecialty(String name, String description, Language lang) {
		
		
		Language langDE = (new LanguageDAO(entitymanager)).selectLanguageById(2);
		//List<TechnicalTerm> tTermsList;
		Specialty specialty = new Specialty();
		//System.out.println(specialty.getId()+"ddddddddddddddddd");
		Translation translationES = new Translation(name, description, lang, specialty);
		Translation translationDE = new Translation(name, description, langDE, specialty);
		
		List<Translation> transList;
		transList = specialty.getTranslationList();
		transList.add(translationES);
		transList.add(translationDE);
		
		entitymanager.persist(specialty);
		
//		if (technicalTerm != null) {
//			tTermsList = specialty.getTechnicalTermsList();
//			tTermsList.add(technicalTerm);
//			tTermsList.add(technicalTerm2);
//			tTermsList.add(technicalTerm3);
//			tTermsList.add(technicalTerm4);
//			
//			technicalTerm.setSpecialty(specialty);
//			entitymanager.persist(technicalTerm);
//			entitymanager.persist(technicalTerm2);
//			entitymanager.persist(technicalTerm3);
//			entitymanager.persist(technicalTerm4);
//		}
		
		
				
		
		
		
		//entitymanager.persist(lang); --> wenn es hinzugefügt werden muss --> BO muss vorher überprüfen, hier zu komplex
		
		
		
	}
	
	public void insertTechnicalTerm(String name, String description, Specialty specialty, Language lang) {
		
		
		List<Translation> transList;
		Translation translation;
		
		TechnicalTerm technicalTerm = new TechnicalTerm();
		
		transList = technicalTerm.getTranslationList();
		translation = new Translation(name, description, lang, technicalTerm);
		transList.add(translation);
		
		if (specialty != null) {
			technicalTerm.setSpecialty(specialty);
			entitymanager.persist(specialty);
		}
		
		entitymanager.persist(technicalTerm);
		entitymanager.persist(translation);	
		
		//entitymanager.persist(lang); --> wenn es hinzugefügt werden muss --> BO muss vorher überprüfen, hier zu komplex
		//vorher abprüfen: existiert schon sprache, wenn nicht anlegen; existiert schon specialty, wenn nicht anlegen 
		// (Standard: persist muss nich, durchgeführt werden -> nur von technicalterm und translation)
		
		
	}
	
	
	
	public void deleteSpecialty(String name) throws NoResultException {
		
		// public void deleteLinks(specialty.getTechnicalTermsList()) aus BO --> technicalterm suchen, verweis auf Specialty entfernen
		// --> dann diese Funktion
		
		
//		Language lang = selectLanguageByName(name);
//		entitymanager.remove(lang);
		
		
	}
	
//	public TechnicalTermDataset selectTechnicalTerm(String name, Language language) throws NoResultException {
//		
//		Query query = entitymanager.createQuery("Select translation " + "from Translation translation " + "where lang.name LIKE '" + name + "' and ");
//		Language lang = (Language) query.getSingleResult();
//	}
}
