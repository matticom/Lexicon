package businessOperations;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import model.History;
import model.Specialty;
import model.TechnicalTerm;
import model.Translations;
import repository.HistoryDAO;

public class TransactionBeginCommit {
	
	private EntityTransaction entityTransaction;
	private TermBO repositoryService;
	private HistoryDAO historyService;

	public TransactionBeginCommit(EntityManager entitymanager, TermBO repositoryService, HistoryDAO historyService) {
		entityTransaction = entitymanager.getTransaction();
		this.repositoryService = repositoryService;
		this.historyService = historyService;
	}
	
	public List<Specialty> selectAllSpecialties() {
		entityTransaction.begin();
		List<Specialty> specialtyList = repositoryService.selectAllSpecialties();
		entityTransaction.commit();
		return specialtyList;
	}
	
	public List<TechnicalTerm> selectAllTechnicalTermsOfSpecialty(int specialtyId) {
		entityTransaction.begin();
		List<TechnicalTerm> technicalTermList = repositoryService.selectAllTechnicalTermsOfSpecialty(specialtyId);
		entityTransaction.commit();
		return technicalTermList;
	}
	
	public boolean[] checkLetter() {
		entityTransaction.begin();
		boolean[] alphabet = repositoryService.checkLetter();
		entityTransaction.commit();
		return alphabet;
	}
	
	public List<Translations> searchTechnicalTerms(String name) {
		entityTransaction.begin();
		List<Translations> translationList = repositoryService.searchTechnicalTerms(name);
		entityTransaction.commit();
		return translationList;
	}

	public Specialty selectSpecialtyById(int specialtyId) {
		entityTransaction.begin();
		Specialty specialty = repositoryService.selectSpecialtyById(specialtyId);
		entityTransaction.commit();
		return specialty;
	}
	
	public TechnicalTerm selectTechnicalTermById(int technicalTermId) {
		entityTransaction.begin();
		TechnicalTerm technicalTerm = repositoryService.selectTechnicalTermById(technicalTermId);
		entityTransaction.commit();
		return technicalTerm;
	}
	
	public Specialty createSpecialty(String name, String description, int languageId) {
		entityTransaction.begin();
		Specialty specialty = repositoryService.createSpecialty(name, description, languageId);
		entityTransaction.commit();
		return specialty;
	}

	public Translations createSpecialtyTranslation(int specialtyId, String newName, String newDescription, int languageId) {
		entityTransaction.begin();
		Translations translation = repositoryService.createSpecialtyTranslation(specialtyId, newName, newDescription, languageId);
		entityTransaction.commit();
		return translation;
	}
	
	public TechnicalTerm createTechnicalTerm(String name, String description, int languageId, int specialtyId) {
		entityTransaction.begin();
		TechnicalTerm technicalTerm = repositoryService.createTechnicalTerm(name, description, languageId, specialtyId);
		entityTransaction.commit();
		return technicalTerm;
	}
	
	public Translations createTechnicalTermTranslation(int technicalTermId, String newName, String newDescription, int languageId) {
		entityTransaction.begin();
		Translations translation = repositoryService.createTechnicalTermTranslation(technicalTermId, newName, newDescription, languageId);
		entityTransaction.commit();
		return translation;
	}
	
	public List<TechnicalTerm> selectAllTechnicalTerms() {
		entityTransaction.begin();
		List<TechnicalTerm> technicalTermList = repositoryService.selectAllTechnicalTerms();
		entityTransaction.commit();
		return technicalTermList;
	}
	
	public Specialty assignTechnicalTermsToSpecialty(int[] technicalTermIds, int specialtyId) {
		entityTransaction.begin();
		Specialty specialty = repositoryService.assignTechnicalTermsToSpecialty(technicalTermIds, specialtyId);
		entityTransaction.commit();
		return specialty;
	}
	
	public Translations updateTranslation(int termId, String newName, String newDescription, int languageId) {
		entityTransaction.begin();
		Translations translation = repositoryService.updateTranslation(termId, newName, newDescription, languageId);
		entityTransaction.commit();
		return translation;
	}
	
	
	public Specialty selectSpecialtyByName(String name, int languageId) {

		Specialty specialty = repositoryService.selectSpecialtyByName(name, languageId);
		return specialty;
	}

	public TechnicalTerm selectTechnicalTermByName(String name, int languageId) {

		TechnicalTerm technicalTerm = repositoryService.selectTechnicalTermByName(name, languageId);
		return technicalTerm;
	}


	public void deleteAllWords() {
		entityTransaction.begin();
		historyService.deleteAllWords();
		entityTransaction.commit();
	}
	
	public List<History> selectAllWords() {
		entityTransaction.begin();
		List<History> historyList = historyService.selectAllWords();
		entityTransaction.commit();
		return historyList;
	}
	
	public History insertWord(String word) {
		entityTransaction.begin();
		History historyEntry = historyService.insertWord(word);
		entityTransaction.commit();
		return historyEntry;
	}
}
