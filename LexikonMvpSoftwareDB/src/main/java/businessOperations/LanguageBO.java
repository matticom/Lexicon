package businessOperations;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.swing.JOptionPane;

import globals.LanguageAlreadyExists;
import globals.LanguageDoesNotExist;
import model.Languages;
import repository.LanguageDAO;

public class LanguageBO {

	LanguageDAO languageDAO;

	public LanguageBO(LanguageDAO languageDAO) {
		this.languageDAO = languageDAO;
	}

	public static void main(String[] args) {

		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
		EntityManager entitymanager = emfactory.createEntityManager();

		
		LanguageDAO langDAO = new LanguageDAO(entitymanager);
		LanguageBO langBO = new LanguageBO(langDAO);
		entitymanager.getTransaction().begin();

		try {

			langDAO.insertLanguage("Spanisch");
			langDAO.insertLanguage("Deutsch");
			// langDAO.updateLanguageById(1, "Russisch");
			// langDAO.updateLanguageByName("Russisch", "Deutsche");
			// System.out.println("---> selectLanguageById: " +
			// langDAO.selectLanguageById(1).getName());
			// System.out.println("---> selectLanguageByName: " +
			// langDAO.selectLanguageByName("Deutsch").getName());
			// langDAO.deleteLanguage("Deutsch");
			// langDAO.insertLanguage("Deutsch");

		} catch (LanguageAlreadyExists e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "LanguageAlreadyExists", JOptionPane.ERROR_MESSAGE);
		} catch (NoResultException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "NoResultException", JOptionPane.ERROR_MESSAGE);
		} finally {

			entitymanager.getTransaction().commit();
			entitymanager.close();
			emfactory.close();

		}

	}
	
	public Languages createLanguage(String language) {
		if (!isLanguageAlreadyExisting(language)) {
			return languageDAO.insertLanguage(language);
		} else {
			throw new LanguageAlreadyExists();
		}

	}

	public void deleteLanguage(String language) {

		if (isLanguageAlreadyExisting(language)) {
			languageDAO.deleteLanguage(language);
		} else {
			throw new LanguageDoesNotExist();
		}

	}

	public Languages updateLanguage(String language, String newName) {

		if (!isLanguageAlreadyExisting(language)) {
			throw new LanguageDoesNotExist();
		} else if (isLanguageAlreadyExisting(newName)) {
			throw new LanguageAlreadyExists("Update nicht möglich, Sprache schon vorhanden!");
		} else {
			return languageDAO.updateLanguage(language, newName);
		}

	}

	public Languages selectLanguage(String language) {

		try {
			return languageDAO.selectLanguageByName(language);
		} catch (NoResultException e) {
			throw new LanguageDoesNotExist();
		}
	}

	public List<Languages> selectAllLanguage() {

		return languageDAO.selectAllLanguages();
		
	}

	public boolean isLanguageAlreadyExisting(String language) {

		try {
			languageDAO.selectLanguageByName(language);
			return true;

		} catch (NoResultException e) {
			return false;
		}

	}
}
