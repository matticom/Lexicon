package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class History {
		
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="HISTORY_ID")
	private int id;
		
	@Column(name="HISTORY_WORD")
	private String word;

	public History() {
	}
	
	public History(String word) {
		this.word = word;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}
	
	
}
