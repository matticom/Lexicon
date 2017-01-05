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
	
	public List<Specialty> selectAllSpecialtiesTA() {
		entityTransaction.begin();
		List<Specialty> specialtyList = repositoryService.selectAllSpecialties();
		entityTransaction.commit();
		return specialtyList;
	}
	
	public List<TechnicalTerm> selectAllTechnicalTermsOfSpecialtyTA(int specialtyId) {
		entityTransaction.begin();
		List<TechnicalTerm> technicalTermList = repositoryService.selectAllTechnicalTermsOfSpecialty(specialtyId);
		entityTransaction.commit();
		return technicalTermList;
	}
	
	public boolean[] checkLetterTA() {
		entityTransaction.begin();
		boolean[] alphabet = repositoryService.checkLetter();
		entityTransaction.commit();
		return alphabet;
	}
	
	public List<Translations> searchTechnicalTermsTA(String name) {
		entityTransaction.begin();
		List<Translations> translationList = repositoryService.searchTechnicalTerms(name);
		entityTransaction.commit();
		return translationList;
	}

	public Specialty selectSpecialtyByIdTA(int specialtyId) {
		entityTransaction.begin();
		Specialty specialty = repositoryService.selectSpecialtyById(specialtyId);
		entityTransaction.commit();
		return specialty;
	}
	
	public TechnicalTerm selectTechnicalTermByIdTA(int technicalTermId) {
		entityTransaction.begin();
		TechnicalTerm technicalTerm = repositoryService.selectTechnicalTermById(technicalTermId);
		entityTransaction.commit();
		return technicalTerm;
	}
	
	public Specialty createSpecialtyTA(String name, String description, int languageId) {
		entityTransaction.begin();
		Specialty specialty = repositoryService.createSpecialty(name, description, languageId);
		entityTransaction.commit();
		return specialty;
	}

	public Translations createSpecialtyTranslationTA(int specialtyId, String newName, String newDescription, int languageId) {
		entityTransaction.begin();
		Translations translation = repositoryService.createSpecialtyTranslation(specialtyId, newName, newDescription, languageId);
		entityTransaction.commit();
		return translation;
	}
	
	public TechnicalTerm createTechnicalTermTA(String name, String description, int languageId, int specialtyId) {
		entityTransaction.begin();
		TechnicalTerm technicalTerm = repositoryService.createTechnicalTerm(name, description, languageId, specialtyId);
		entityTransaction.commit();
		return technicalTerm;
	}
	
	public Translations createTechnicalTermTranslationTA(int technicalTermId, String newName, String newDescription, int languageId) {
		entityTransaction.begin();
		Translations translation = repositoryService.createTechnicalTermTranslation(technicalTermId, newName, newDescription, languageId);
		entityTransaction.commit();
		return translation;
	}
	
	public List<TechnicalTerm> selectAllTechnicalTermsTA() {
		entityTransaction.begin();
		List<TechnicalTerm> technicalTermList = repositoryService.selectAllTechnicalTerms();
		entityTransaction.commit();
		return technicalTermList;
	}
	
	public Specialty assignTechnicalTermsToSpecialtyTA(int[] technicalTermIds, int specialtyId) {
		entityTransaction.begin();
		Specialty specialty = repositoryService.assignTechnicalTermsToSpecialty(technicalTermIds, specialtyId);
		entityTransaction.commit();
		return specialty;
	}
	
	public Translations updateTranslationTA(int termId, String newName, String newDescription, int languageId) {
		entityTransaction.begin();
		Translations translation = repositoryService.updateTranslation(termId, newName, newDescription, languageId);
		entityTransaction.commit();
		return translation;
	}
	
	
	
	public Specialty selectSpecialtyByNameTA(String name, int languageId) {
//		entityTransaction.begin();
		Specialty specialty = repositoryService.selectSpecialtyByName(name, languageId);
//		entityTransaction.commit();
		return specialty;
	}

	public TechnicalTerm selectTechnicalTermByNameTA(String name, int languageId) {
//		entityTransaction.begin();
		TechnicalTerm technicalTerm = repositoryService.selectTechnicalTermByName(name, languageId);
//		entityTransaction.commit();
		return technicalTerm;
	}


	public void deleteAllWordsTA() {
		entityTransaction.begin();
		historyService.deleteAllWords();
		entityTransaction.commit();
	}
	
	public List<History> selectAllWordsTA() {
		entityTransaction.begin();
		List<History> historyList = historyService.selectAllWords();
		entityTransaction.commit();
		return historyList;
	}
	
	public History insertWordTA(String word) {
		entityTransaction.begin();
		History historyEntry = historyService.insertWord(word);
		entityTransaction.commit();
		return historyEntry;
	}
}
