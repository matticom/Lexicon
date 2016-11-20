package repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import model.History;

public class HistoryDAO {

	private EntityManager entitymanager;

	
	public HistoryDAO() {
	}

	public HistoryDAO(EntityManager entitymanager) {
		
		this.entitymanager = entitymanager;
	}

	

	public EntityManager getEntitymanager() {
		
		return entitymanager;
	}

	public void setEntitymanager(EntityManager entitymanager) {
		
		this.entitymanager = entitymanager;
	}

	
	public History insertWord(String word) {

		History historyEntry = new History(word);
		
		entitymanager.persist(historyEntry);
		return historyEntry;
	}

	public void deleteAllWords() {

		List<History> historyEntries = selectAllWords();
		for(History entry: historyEntries) {
			entitymanager.remove(entry);
		}
	}
		
	public List<History> selectAllWords() {
	
		CriteriaBuilder criteriaBuilder = entitymanager.getCriteriaBuilder();
		CriteriaQuery<History> criteriaQuery = criteriaBuilder.createQuery(History.class);
				
		Root<History> historyEntry = criteriaQuery.from(History.class);
		criteriaQuery.select(historyEntry);
		
		List<History> historyList = entitymanager.createQuery(criteriaQuery).getResultList();
						
		return historyList;
	}

}
	