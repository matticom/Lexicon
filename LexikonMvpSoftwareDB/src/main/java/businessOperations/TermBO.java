package businessOperations;

import java.util.List;

import javax.persistence.NoResultException;

import globals.LanguageEntryInSpecialtyAlreadyExists;
import globals.LanguageEntryInTechnicalTermAlreadyExists;
import globals.SpecialtyAlreadyExists;
import globals.SpecialtyDoesNotExist;
import globals.TechnicalTermAlreadyExists;
import globals.TechnicalTermDoesNotExist;
import globals.TranslationAlreadyExists;
import globals.TranslationDoesNotExist;
import model.Languages;
import model.Specialty;
import model.TechnicalTerm;
import model.Term;
import model.Translations;
import repository.TermDAO;
import transferObjects.SpecialtyDataset;
import transferObjects.TechnicalTermDataset;
import transferObjects.TermDataset;

public class TermBO {

	LanguageBO languageBO;
	TermDAO termDAO;

	public TermBO(LanguageBO languageBO, TermDAO termDAO) {
		this.languageBO = languageBO;
		this.termDAO = termDAO;
	}

	public TechnicalTerm createTechnicalTerm(TechnicalTermDataset technicalTerm) {

		if (isTechnicalTermAlreadyExisting(technicalTerm)) {
			throw new TechnicalTermAlreadyExists();
		}

		if (!languageBO.isLanguageAlreadyExisting(technicalTerm.getLanguage())) {
			languageBO.createLanguage(technicalTerm.getLanguage());
		}
		Languages language = languageBO.selectLanguage(technicalTerm.getLanguage());

		if (technicalTerm.getSpecialty() == null) {
			return termDAO.insertNewTechnicalTerm(technicalTerm.getName(), technicalTerm.getDescription(), null, language);
		}

		if (isSpecialtyAlreadyExisting(new SpecialtyDataset(technicalTerm.getSpecialty(), null, technicalTerm.getLanguage()))) {

			Specialty specialty = termDAO.selectSpecialtyByName(technicalTerm.getSpecialty(), technicalTerm.getLanguage());
			return termDAO.insertNewTechnicalTerm(technicalTerm.getName(), technicalTerm.getDescription(), specialty, language);

		} else {
			throw new SpecialtyDoesNotExist("Specialty war beim erstellen einer TechnicalTerm nicht vorhanden");
			// Specialty muss per GUI erstellt werden bzw es kann per
			// SelectListe nur gültige Specialties oder null ausgewählt werden
			// -> eigentlich sollte man so niemals in diesen Zweig gelangen
			// können
		}

	}

	public Specialty createSpecialty(SpecialtyDataset specialty) {

		if (isSpecialtyAlreadyExisting(specialty)) {
			throw new SpecialtyAlreadyExists();
		}

		if (!languageBO.isLanguageAlreadyExisting(specialty.getLanguage())) {
			languageBO.createLanguage(specialty.getLanguage());
		}
		Languages language = languageBO.selectLanguage(specialty.getLanguage());

		return termDAO.insertNewSpecialty(specialty.getName(), specialty.getDescription(), language);

	}

	public TechnicalTerm createTechnicalTermTranslation(TechnicalTermDataset technicalTermRef, TechnicalTermDataset technicalTerm) {

		if (!isTechnicalTermAlreadyExisting(technicalTermRef)) {
			throw new TechnicalTermDoesNotExist();
		}

		if (isTechnicalTermAlreadyExisting(technicalTerm)) {
			throw new TechnicalTermAlreadyExists();
		}

		if (isTechnicalTermTranslationInThisLanguageAlreadyExisting(technicalTermRef, technicalTerm.getLanguage())) {
			throw new LanguageEntryInTechnicalTermAlreadyExists();
		}

		if (!languageBO.isLanguageAlreadyExisting(technicalTerm.getLanguage())) {
			languageBO.createLanguage(technicalTerm.getLanguage());
		}
		Languages language = languageBO.selectLanguage(technicalTerm.getLanguage());

		return termDAO.insertTechnicalTermTranslation(technicalTermRef.getName(), technicalTermRef.getLanguage(), technicalTerm.getName(), technicalTerm.getDescription(), language);

	}

	public Specialty createSpecialtyTranslation(SpecialtyDataset specialtyRef, SpecialtyDataset specialty) {

		if (!isSpecialtyAlreadyExisting(specialtyRef)) {
			throw new SpecialtyDoesNotExist();
		}

		if (isSpecialtyAlreadyExisting(specialty)) {
			throw new SpecialtyAlreadyExists();
		}

		if (isSpecialtyTranslationInThisLanguageAlreadyExisting(specialtyRef, specialty.getLanguage())) {
			throw new LanguageEntryInSpecialtyAlreadyExists();
		}

		if (!languageBO.isLanguageAlreadyExisting(specialty.getLanguage())) {
			languageBO.createLanguage(specialty.getLanguage());
		}
		Languages language = languageBO.selectLanguage(specialty.getLanguage());

		return termDAO.insertSpecialtyTranslation(specialtyRef.getName(), specialtyRef.getLanguage(), specialty.getName(), specialty.getDescription(), language);

	}

	public void deleteSpecialty(SpecialtyDataset specialty) {

		if (!isSpecialtyAlreadyExisting(specialty)) {
			throw new SpecialtyDoesNotExist();
		}

		termDAO.deleteSpecialty(specialty.getName(), specialty.getLanguage());

	}

	public void deleteTechnicalTerm(TechnicalTermDataset technicalTerm) {

		if (!isTechnicalTermAlreadyExisting(technicalTerm)) {
			throw new TechnicalTermDoesNotExist();
		}

		termDAO.deleteTechnicalTerm(technicalTerm.getName(), technicalTerm.getLanguage());

	}

	public void deleteTranslation(TermDataset term) {

		if (!isTranslationAlreadyExisting(term)) {
			throw new TranslationDoesNotExist();
		}

		termDAO.deleteTranslation(term.getName(), term.getLanguage());
	}

	public Translations updateTranslation(TermDataset termRef, TermDataset term) {

		if (!isTranslationAlreadyExisting(termRef)) {
			throw new TranslationDoesNotExist();
		}

		if (isTranslationAlreadyExisting(term)) {
			throw new TranslationAlreadyExists();
		}

		return termDAO.updateTranslation(termRef.getName(), termRef.getLanguage(), term.getName(), term.getDescription());

	}

	public Specialty selectSpecialtyByName(SpecialtyDataset specialty) throws NoResultException {
		return termDAO.selectSpecialtyByName(specialty.getName(), specialty.getLanguage());
	}

	public TechnicalTerm selectTechnicalTermByName(TechnicalTermDataset technicalTerm) throws NoResultException {
		return termDAO.selectTechnicalTermByName(technicalTerm.getName(), technicalTerm.getLanguage());
	}

	public List<Translations> selectAllTranslations(TermDataset term) throws NoResultException {
		return termDAO.selectAllTermTranslations(term.getName(), term.getLanguage());
	}

	public Translations selectTranslation(TermDataset term) throws NoResultException {
		return termDAO.selectTranslation(term.getName(), term.getLanguage());
	}

	public Specialty assignTechnicalTermsToSpecialty(TechnicalTerm[] technicalTerms, SpecialtyDataset specialty) {

		if (!isSpecialtyAlreadyExisting(specialty)) {
			throw new SpecialtyDoesNotExist();
		}

		Specialty specialtyEntry = selectSpecialtyByName(specialty);

		for (TechnicalTerm techTerm : technicalTerms) {

			if (techTerm.getSpecialty() != null) {
				techTerm.getSpecialty().getTechnicalTermsList().remove(techTerm);
				// Testen ob das funktioniert!!!!!!!!!!!!
			}

			specialtyEntry.getTechnicalTermsList().add(techTerm);
			techTerm.setSpecialty(specialtyEntry);
			// Es wird das Field Specialty überschrieben auch bei != null
			// Muss es auf der anderen Seite auch gemacht werden?
		}

		return specialtyEntry;

	}

	public boolean isTechnicalTermAlreadyExisting(TechnicalTermDataset technicalTerm) {

		try {
			selectTechnicalTermByName(technicalTerm);
			return true;

		} catch (NoResultException e) {
			return false;
		}
	}

	public boolean isTechnicalTermTranslationInThisLanguageAlreadyExisting(TechnicalTermDataset technicalTerm, String languages) {

		List<Translations> technicalTermList = selectAllTranslations(technicalTerm);
		if (checkDoubleLanguageEntries(technicalTermList, languages)) {
			return true;
		}
		return false;
	}

	public boolean isSpecialtyAlreadyExisting(SpecialtyDataset specialty) {

		try {
			selectSpecialtyByName(specialty);
			return true;

		} catch (NoResultException e) {
			return false;
		}
	}

	public boolean isSpecialtyTranslationInThisLanguageAlreadyExisting(SpecialtyDataset specialty, String languages) {

		List<Translations> specialtyList = selectAllTranslations(specialty);
		if (checkDoubleLanguageEntries(specialtyList, languages)) {
			return true;
		}
		return false;
	}

	public boolean isTranslationAlreadyExisting(TermDataset term) {

		try {
			selectTranslation(term);
			return true;

		} catch (NoResultException e) {
			return false;
		}

	}

	public boolean checkDoubleLanguageEntries(List<Translations> translationList, String languages) {

		for (Translations translation : translationList) {
			if (translation.getLanguages().getName().equals(languages)) {
				return true;
			}
		}
		return false;
	}

}
