package repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.SetJoin;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import javax.swing.JOptionPane;

import globals.LanguageAlreadyExists;
import globals.SpecialtyDoesNotExist;
import model.Languages;
import model.Languages_;
import model.Specialty;
import model.TechnicalTerm;
import model.Term;
import model.Translations;
import model.Translations_;
import transferObjects.SpecialtyDataset;
import transferObjects.TechnicalTermDataset;

public class TermDAO {

	private EntityManager entitymanager;

	public TermDAO() {
	}

	public TermDAO(EntityManager entitymanager) {
		this.entitymanager = entitymanager;
	}

	public EntityManager getEntitymanager() {
		return entitymanager;
	}

	public void setEntitymanager(EntityManager entitymanager) {
		this.entitymanager = entitymanager;
	}

	public Specialty insertNewSpecialty(String name, String description, Languages lang) {

		Specialty specialty = new Specialty();
		insertSpecialty(specialty, name, description, lang);
		entitymanager.persist(specialty);
		return specialty;

	}

	public Specialty insertSpecialtyTranslation(String RefName, String RefLang, String name, String description, Languages lang) {

		Specialty specialty = selectSpecialtyByName(RefName, RefLang);
		insertSpecialty(specialty, name, description, lang);
		return specialty;

	}

	private void insertSpecialty(Specialty specialty, String name, String description, Languages lang) {

		Translations translation = new Translations(name, description, lang, specialty);

		List<Translations> transList;
		transList = specialty.getTranslationList();
		transList.add(translation);
		entitymanager.persist(translation);

	}

	public TechnicalTerm insertNewTechnicalTerm(String name, String description, Specialty specialty, Languages lang) {

		TechnicalTerm technicalTerm = new TechnicalTerm();
		insertTechnicalTerm(technicalTerm, name, description, specialty, lang);
		entitymanager.persist(technicalTerm);
		return technicalTerm;

	}

	public TechnicalTerm insertTechnicalTermTranslation(String RefName, String RefLang, String name, String description, Languages lang) {

		TechnicalTerm technicalTerm = selectTechnicalTermByName(RefName, RefLang);
		insertTechnicalTerm(technicalTerm, name, description, null, lang);
		return technicalTerm;

		// Specialty immer null bei new Translation -> beziehung schon gesetzt
		// bei Erzeugung oder extern durch entsprechende Befehle

	}

	private void insertTechnicalTerm(TechnicalTerm technicalTerm, String name, String description, Specialty specialty, Languages lang) {

		Translations translation = new Translations(name, description, lang, technicalTerm);

		List<Translations> transList;
		transList = technicalTerm.getTranslationList();
		transList.add(translation);

		if (specialty != null) {
			technicalTerm.setSpecialty(specialty);
		}
		entitymanager.persist(translation);

	}

	public void deleteSpecialty(String name, String lang) {

		Specialty specialty = selectSpecialtyByName(name, lang);
		removeSpecialtyForeignKeyOutOfTechnicalTerms(name, lang);
		deleteAllTranslations(name, lang);
		entitymanager.remove(specialty);

	}

	public void deleteTechnicalTerm(String name, String lang) {

		TechnicalTerm technicalTerm = selectTechnicalTermByName(name, lang);
		removeTechnicalTermForeignKeyOutOfSpecialty(name, lang);
		deleteAllTranslations(name, lang);
		entitymanager.remove(technicalTerm);

	}

	public void deleteAllTranslations(String name, String lang) {

		List<Translations> translationsList = selectAllTermTranslations(name, lang);
		for (Translations translation : translationsList) {
			entitymanager.remove(translation);
		}

	}

	public void deleteTranslation(String name, String lang) {

		Translations translation = selectTranslation(name, lang);
		entitymanager.remove(translation);
	}

	public Translations updateTranslation(String RefName, String RefLang, String newName, String description) {

		Translations translation = selectTranslation(RefName, RefLang);

		if (newName != null) {
			translation.setName(newName);
		}

		if (description != null) {
			translation.setDescription(description);
		}

		return translation;
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

	public List<Specialty> selectAllSpecialties() {

		CriteriaBuilder criteriaBuilder = entitymanager.getCriteriaBuilder();
		CriteriaQuery<Specialty> criteriaQuery = criteriaBuilder.createQuery(Specialty.class);

		Root<Specialty> specialty = criteriaQuery.from(Specialty.class);
		criteriaQuery.select(specialty);

		List<Specialty> specialtyList = entitymanager.createQuery(criteriaQuery).getResultList();

		return specialtyList;
	}

	public Specialty selectSpecialtyByName(String name, String lang) throws NoResultException {

		Specialty specialty = (Specialty) selectTermByName(name, lang);
		return specialty;
	}

	public TechnicalTerm selectTechnicalTermByName(String name, String lang) throws NoResultException {

		TechnicalTerm technicalTerm = (TechnicalTerm) selectTermByName(name, lang);
		return technicalTerm;
	}

	public List<Translations> selectAllTermTranslations(String name, String lang) throws NoResultException {

		Term term = selectTermByName(name, lang);

		List<Translations> translationsList = term.getTranslationList();

		return translationsList;
	}

	private Term selectTermByName(String name, String lang) throws NoResultException {

		Translations translation = selectTranslation(name, lang);

		Term term = translation.getTerm();
		return term;
	}

	public Translations selectTranslation(String name, String lang) throws NoResultException {

		CriteriaBuilder criteriaBuilder = entitymanager.getCriteriaBuilder();
		CriteriaQuery<Translations> criteriaQuery = criteriaBuilder.createQuery(Translations.class);

		Root<Translations> translation = criteriaQuery.from(Translations.class);
		Join<Translations, Languages> langJoin = translation.join(Translations_.languages);

		Predicate selectLanguage = criteriaBuilder.like(langJoin.get(Languages_.name), lang);
		Predicate selectTermName = criteriaBuilder.like(translation.get(Translations_.name), name);
		Predicate whereFilter = criteriaBuilder.and(selectLanguage, selectTermName);
		criteriaQuery.select(translation).where(whereFilter);

		Translations translationResult = entitymanager.createQuery(criteriaQuery).getSingleResult();
		//
		// Query query = entitymanager.createQuery("Select translation FROM
		// Translations translation JOIN translation.languages language "
		// + "where translation.name LIKE '" + name + "' and language.name like
		// '" + lang + "'");
		//
		// Translations translation = (Translations) query.getSingleResult();

		return translationResult;
	}

	public void removeSpecialtyForeignKeyOutOfTechnicalTerms(String specialtyName, String lang) {

		Specialty specialty = selectSpecialtyByName(specialtyName, lang);

		for (TechnicalTerm techTerm : specialty.getTechnicalTermsList()) {
			techTerm.setSpecialty(null);
		}
		specialty.getTechnicalTermsList().clear();
	}

	public void removeTechnicalTermForeignKeyOutOfSpecialty(String technicalTermName, String lang) {

		TechnicalTerm technicalTerm = selectTechnicalTermByName(technicalTermName, lang);

		Specialty specialty = technicalTerm.getSpecialty();
		specialty.getTechnicalTermsList().remove(technicalTerm);

		technicalTerm.setSpecialty(null);
	}

	public void removeForeignKeyConstraints(List<Specialty> specialtyList) {
		
		for (Specialty specialty : specialtyList) {
			for (TechnicalTerm techTerm : specialty.getTechnicalTermsList()) {
				techTerm.setSpecialty(null);
			}
//			specialty.getTechnicalTermsList().clear();
		}
	}
}
