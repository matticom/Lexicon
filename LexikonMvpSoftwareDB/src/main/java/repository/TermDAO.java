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
import model.Specialty;
import model.TechnicalTerm;
import model.Term;
import model.Translations;
import transferObjects.TechnicalTermDataset;

public class TermDAO {

	private EntityManager entitymanager;

	public TermDAO() {
	}

	public TermDAO(EntityManager entitymanager) {
		this.entitymanager = entitymanager;
	}

	public static void main(String[] args) {

		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
		EntityManager entitymanager = emfactory.createEntityManager();

		TermDAO termDao = new TermDAO(entitymanager);

		Languages langES = (new LanguageDAO(entitymanager)).selectLanguageById(1);

		entitymanager.getTransaction().begin();

		try {

//			termDao.insertSpecialty("Beton", "fest", langES);

			Specialty specialty = entitymanager.find(Specialty.class, 51);

//			termDao.insertTechnicalTerm("Bewaehrung", "Stahlzeugs", specialty, langES);

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
	
	

	public void insertNewSpecialty(String name, String description, Languages lang) {

		Specialty specialty = new Specialty();

		insertSpecialty(specialty, name, description, lang);

	}

	public void insertSpecialtyTranslation(String RefName, String RefLang, String name, String description, Languages lang) {

		Specialty specialty = selectSpecialtyByName(RefName, RefLang);

		insertSpecialty(specialty, name, description, lang);

	}
	
	private void insertSpecialty(Specialty specialty, String name, String description, Languages lang) {

		Translations translation = new Translations(name, description, lang, specialty);

		List<Translations> transList;
		transList = specialty.getTranslationList();
		transList.add(translation);
		entitymanager.persist(specialty);

	}

	public void insertNewTechnicalTerm(String name, String description, Specialty specialty, Languages lang) {

		TechnicalTerm technicalTerm = new TechnicalTerm();
		
		insertTechnicalTerm(technicalTerm, name, description, specialty, lang);

	}
	
	public void insertTechnicalTermTranslation(String RefName, String RefLang, String name, String description, Languages lang) {

		TechnicalTerm technicalTerm = selectTechnicalTermByName(RefName, RefLang);
		
		insertTechnicalTerm(technicalTerm, name, description, null, lang);
		// Specialty immer null bei new Translation -> beziehung schon gesetzt bei Erzeugung oder extern durch entsprechende Befehle

	}
	
	private void insertTechnicalTerm(TechnicalTerm technicalTerm, String name, String description, Specialty specialty, Languages lang) {

		Translations translation = new Translations(name, description, lang, technicalTerm);

		List<Translations> transList;
		transList = technicalTerm.getTranslationList();
		transList.add(translation);

		if (specialty != null) {
			technicalTerm.setSpecialty(specialty);
		}

		entitymanager.persist(technicalTerm);

	}

	
	
	public void deleteSpecialty(String name, String lang) {

		Specialty specialty = selectSpecialtyByName(name, lang);
		entitymanager.remove(specialty);

	}
		
	public void deleteTechnicalTerm(String name, String lang) {

		TechnicalTerm technicalTerm = selectTechnicalTermByName(name, lang);
		entitymanager.remove(technicalTerm);

	}
	
	public void deleteTranslation(String name, String lang) {

		Translations translation = selectTranslation(name, lang);
		entitymanager.remove(translation);
	}
	
	
	
	public void updateTranslation(String name, String lang, String newName, String description) {
		
		Translations translation = selectTranslation(name, lang);
		translation.setName(newName);
		translation.setDescription(description);
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
	
	
	public Specialty selectSpecialtyByName(String name, String lang) throws NoResultException {

		Specialty specialty = (Specialty) selectTermByName(name, lang);
		return specialty;
	}

	public TechnicalTerm selectTechnicalTermByName(String name, String lang) throws NoResultException {

		TechnicalTerm technicalTerm = (TechnicalTerm) selectTermByName(name, lang);
		return technicalTerm;
	}
	
	public Translations[] selectAllTranslations(String name, String lang) throws NoResultException {

		Term term = selectTermByName(name, lang);
				
		List<Translations> translationsList = term.getTranslationList();
		Translations[] translations = translationsList.toArray(new Translations[translationsList.size()]);
		return translations;
	}
	
	
	
	private Term selectTermByName(String name, String lang) throws NoResultException {

		Translations translation = selectTranslation(name, lang);

		Term term = translation.getTerm();
		return term;
	}
	
	public Translations selectTranslation(String name, String lang) throws NoResultException {

//		Query query = entitymanager.createQuery("Select translation from Translation translation where translation.name LIKE '" + name + "'");
		Query query = entitymanager.createQuery("Select translation FROM Translations translation JOIN translation.languages language "
													+ "where translation.name LIKE '" + name + "' and language.name like '" + lang + "'");
		
		Translations translation = (Translations) query.getSingleResult();

		return translation;
	}
	
}
