package businessOperations;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import exceptions.LanguageEntryInSpecialtyAlreadyExists;
import exceptions.LanguageEntryInTechnicalTermAlreadyExists;
import exceptions.SpecialtyAlreadyExists;
import exceptions.SpecialtyDoesNotExist;
import exceptions.TechnicalTermAlreadyExists;
import exceptions.TechnicalTermDoesNotExist;
import exceptions.TermDoesNotExist;
import exceptions.TranslationDoesNotExist;
import model.Languages;
import model.Specialty;
import model.TechnicalTerm;
import model.Term;
import model.Translations;
import repository.TermDAO;
import utilities.PersistenceUtil;

public class TermBO {

	// Konzept: Id's werden sp�ter in Buttons usw integriert -> Id's k�nnen
	// ausgelesen werden und benutzt werden f�r aktionen

	LanguageBO languageBO;
	TermDAO termDAO;

	public TermBO(LanguageBO languageBO, TermDAO termDAO) {
		this.languageBO = languageBO;
		this.termDAO = termDAO;
	}

	public TechnicalTerm createTechnicalTerm(String name, String description, int languageId, int SpecialtyId) {

		// Sprache durch GUI Combolist ausw�hlbar -> zur Not dort ein Button f�r
		// Erstellung einer neuen Sprache
		Languages language = languageBO.selectLanguageById(languageId); // Sprache
																		// sollte
																		// da
																		// sein
																		// wegen
																		// GUI,falls
																		// nicht:
																		// wirft
																		// Exception
		String normalName = PersistenceUtil.convertSpecialChar(name);

		try {
			selectTechnicalTermByName(normalName, languageId);
			throw new TechnicalTermAlreadyExists();
		} catch (NoResultException e) {
		}

		try {
			Specialty specialty = selectSpecialtyById(SpecialtyId); // Specialty
																	// ist immer
																	// definiert
																	// durch
																	// Combolist,
																	// "leere"
																	// Specialty
																	// ist
																	// specialtyId
																	// = 1
																	// (vorher
																	// in DB
																	// injizieren)
			TechnicalTerm technicalTerm = new TechnicalTerm();
			technicalTerm.setSpecialty(specialty);
			Translations translation = new Translations(name, normalName, description, language, technicalTerm);
			technicalTerm.getTranslationList().add(translation);
			return termDAO.insertNewTechnicalTerm(technicalTerm);
		} catch (NoResultException e) {
			throw new SpecialtyDoesNotExist("Specialty kann nicht dem TechnicalTerm zugeordnet werden, da sie nicht in der Datenbank vorhanden ist!");
		}
	}

	public Specialty createSpecialty(String name, String description, int languageId) {

		Languages language = languageBO.selectLanguageById(languageId);
		String normalName = PersistenceUtil.convertSpecialChar(name);

		try {
			selectSpecialtyByName(normalName, languageId);
			throw new SpecialtyAlreadyExists();
		} catch (NoResultException e) {
		}

		Specialty specialty = new Specialty();
		Translations translation = new Translations(name, normalName, description, language, specialty);
		specialty.getTranslationList().add(translation);
		return termDAO.insertNewSpecialty(specialty);
	}

	public Translations createTechnicalTermTranslation(int technicalTermId, String newName, String newDescription, int languageId) {

		Languages language = languageBO.selectLanguageById(languageId);
		TechnicalTerm technicalTerm;
		String newNormalName = PersistenceUtil.convertSpecialChar(newName);

		try {
			technicalTerm = selectTechnicalTermById(technicalTermId);
		} catch (NoResultException e) {
			throw new TechnicalTermDoesNotExist();
		}

		try {
			selectTranslationWithLanguageOutOfTerm(technicalTerm, language);
			throw new LanguageEntryInTechnicalTermAlreadyExists();
		} catch (TranslationDoesNotExist e) {
		}

		Translations translation = new Translations(newName, newNormalName, newDescription, language, technicalTerm);
		return termDAO.insertTechnicalTermTranslation(technicalTerm, translation);

	}

	public Translations createSpecialtyTranslation(int specialtyId, String newName, String newDescription, int languageId) {

		Languages language = languageBO.selectLanguageById(languageId);
		Specialty specialty;
		String newNormalName = PersistenceUtil.convertSpecialChar(newName);

		try {
			specialty = selectSpecialtyById(specialtyId);
		} catch (NoResultException e) {
			throw new SpecialtyDoesNotExist();
		}

		try {
			selectTranslationWithLanguageOutOfTerm(specialty, language);
			throw new LanguageEntryInSpecialtyAlreadyExists();
		} catch (TranslationDoesNotExist e) {
		}

		Translations translation = new Translations(newName, newNormalName, newDescription, language, specialty);
		return termDAO.insertSpecialtyTranslation(specialty, translation);
	}

	public void deleteSpecialty(int specialtyId) {

		Specialty specialty;
		try {
			specialty = selectSpecialtyById(specialtyId);
		} catch (NoResultException e) {
			throw new SpecialtyDoesNotExist();
		}
		termDAO.deleteSpecialty(specialty);
	}

	public void deleteTechnicalTerm(int technicalTermId) {

		TechnicalTerm technicalTerm;
		try {
			technicalTerm = selectTechnicalTermById(technicalTermId);
		} catch (NoResultException e) {
			throw new TechnicalTermDoesNotExist();
		}
		termDAO.deleteTechnicalTerm(technicalTerm);
	}

	public void deleteTranslation(int termId, int languageId) {

		Languages language = languageBO.selectLanguageById(languageId);
		Term term;
		try {
			term = selectTermById(termId);
		} catch (NoResultException e) {
			throw new TermDoesNotExist();
		}
		Translations translation = selectTranslationWithLanguageOutOfTerm(term, language);
		termDAO.deleteTranslation(translation);
	}

	public Translations updateTranslation(int termId, String newName, String newDescription, int languageId) {

		Languages language = languageBO.selectLanguageById(languageId);
		Term term;
		String newNormalName = PersistenceUtil.convertSpecialChar(newName);

		try {
			term = selectTermById(termId);
		} catch (NoResultException e) {
			throw new TermDoesNotExist();
		}

		Translations translation = selectTranslationWithLanguageOutOfTerm(term, language);
		Translations newTranslation = new Translations(newName, newNormalName, newDescription, language, term);
		return termDAO.updateTranslation(translation, newTranslation);
	}

	public Specialty selectSpecialtyByName(String name, int languageId) throws NoResultException {
		String normalName = PersistenceUtil.convertSpecialChar(name);
		Languages language = languageBO.selectLanguageById(languageId);
		return termDAO.selectSpecialtyByName(normalName, language);
	}

	public TechnicalTerm selectTechnicalTermByName(String name, int languageId) throws NoResultException {
		String normalName = PersistenceUtil.convertSpecialChar(name);
		Languages language = languageBO.selectLanguageById(languageId);
		return termDAO.selectTechnicalTermByName(normalName, language);
	}

	public Specialty selectSpecialtyById(int specialtyId) throws NoResultException {
		return termDAO.selectSpecialtyById(specialtyId);
	}

	public TechnicalTerm selectTechnicalTermById(int specialtyId) throws NoResultException {
		return termDAO.selectTechnicalTermById(specialtyId);
	}

	public Term selectTermById(int termId) throws NoResultException {
		return termDAO.selectTermById(termId);
	}

	public List<Specialty> selectAllSpecialties() {
		return termDAO.selectAllSpecialties();
	}

	public List<Translations> selectAllTermTranslations(int termId) {

		Term term;
		try {
			term = selectTermById(termId);
		} catch (NoResultException e) {
			throw new TermDoesNotExist();
		}
		return termDAO.selectAllTermTranslations(term);
	}

	public List<TechnicalTerm> selectAllTechnicalTermsOfSpecialty(int specialtyId) {
		Specialty specialty = selectSpecialtyById(specialtyId);
		return specialty.getTechnicalTermsList();
		
	}

	
	public List<Translations> searchTechnicalTerms(String name) {

		String normalName = PersistenceUtil.convertSpecialChar(name);
		List<Translations> translationList = termDAO.selectTranslations(normalName);
		List<Translations> onlyTechnicalTermTranslationList = new ArrayList<Translations>();
		for (Translations translation : translationList) {
			try {
				if (selectTechnicalTermById(translation.getTerm().getId()) != null) {
					onlyTechnicalTermTranslationList.add(translation);
				}
			} catch (NoResultException e) {
			}
		}
		return onlyTechnicalTermTranslationList;
	}

	public boolean[] checkLetter() {

		boolean[] alphabet = new boolean[26];
		String strLetter = null;
		for (char letter = 'A'; letter <= 'Z'; letter++) {
			strLetter = String.valueOf(letter);
			if (termDAO.selectLetter(strLetter).isEmpty()) {
				alphabet[letter - 65] = false;
			} else {
				alphabet[letter - 65] = true;
			}
		}
		return alphabet;
	}

	public List<Translations> selectLetter(String letter) {

		return termDAO.selectLetter(letter);
	}

	public Specialty assignTechnicalTermsToSpecialty(int[] technicalTermIds, int specialtyId) {

		Specialty specialty;
		try {
			specialty = selectSpecialtyById(specialtyId);
		} catch (NoResultException e) {
			throw new SpecialtyDoesNotExist();
		}

		TechnicalTerm technicalTerm;
		for (int technicalTermId : technicalTermIds) {

			try {
				technicalTerm = selectTechnicalTermById(technicalTermId);
			} catch (NoResultException e) {
				throw new TechnicalTermDoesNotExist();
			}

			if (technicalTerm.getSpecialty() != null) {
				technicalTerm.getSpecialty().getTechnicalTermsList().remove(technicalTerm);
				// Testen ob das funktioniert!!!!!!!!!!!!
			}

			specialty.getTechnicalTermsList().add(technicalTerm);
			technicalTerm.setSpecialty(specialty);
			// Es wird das Field Specialty �berschrieben auch bei != null
			// Muss es auf der anderen Seite auch gemacht werden?
		}
		return specialty;
	}

	private Translations selectTranslationWithLanguageOutOfTerm(Term term, Languages language) {

		List<Translations> translationList = term.getTranslationList();
		for (Translations translation : translationList) {
			if (translation.getLanguages().getId() == language.getId()) {
				return translation;
			}
		}
		throw new TranslationDoesNotExist("Es gibt keine �bersetzung des Begriffs in dieser Sprache!");
	}
}
