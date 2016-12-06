package businessOperations;

import java.util.List;
import javax.persistence.NoResultException;

import exceptions.LanguageAlreadyExists;
import exceptions.LanguageDoesNotExist;
import model.Languages;
import repository.LanguageDAO;

public class LanguageBO {

	LanguageDAO languageDAO;

	public LanguageBO(LanguageDAO languageDAO) {
		this.languageDAO = languageDAO;
	}

	
	public Languages createLanguage(String language) {
		
		try{
			selectLanguageByName(language);
			throw new LanguageAlreadyExists();
		} catch (LanguageDoesNotExist e) {
			return languageDAO.insertLanguage(new Languages(language));
		}
	}

	public void deleteLanguage(int languageId) {

		try {
			Languages language = languageDAO.selectLanguageById(languageId);
			languageDAO.deleteLanguage(language);
		} catch (NoResultException e) {
			throw new LanguageDoesNotExist();
		}
	}

	public Languages updateLanguage(int languageId, String newName) {

		try {
			if (selectLanguageByName(newName) != null) {
				throw new LanguageAlreadyExists("Update nicht möglich, Sprache schon vorhanden!");
			}
		} catch (LanguageDoesNotExist e) {	
		}
		
		try {
			Languages language = languageDAO.selectLanguageById(languageId);
			Languages newLanguage = new Languages(newName);
			return languageDAO.updateLanguage(language, newLanguage);
		} catch (NoResultException noResultException) {
			throw new LanguageDoesNotExist("Zu aktualisierende Sprache ist nicht vorhanden!");
		}
	}

	public Languages selectLanguageById(int languageId) {

		try {
			return languageDAO.selectLanguageById(languageId);
		} catch (NoResultException e) {
			throw new LanguageDoesNotExist();
		}
	}
	
	public Languages selectLanguageByName(String language) {

		try {
			return languageDAO.selectLanguageByName(language);
		} catch (NoResultException e) {
			throw new LanguageDoesNotExist();
		}
	}

	public List<Languages> selectAllLanguage() {

		return languageDAO.selectAllLanguages();
	}
}
