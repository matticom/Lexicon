package model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;


@Entity
public class Specialty extends Term {
	
	@OneToMany(mappedBy = "specialty", targetEntity = TechnicalTerm.class) //, fetch = FetchType.EAGER)
	private List<TechnicalTerm> technicalTermList;

	
	public Specialty() {
		technicalTermList = new ArrayList<TechnicalTerm>();
	}

	
	public List<TechnicalTerm> getTechnicalTermsList() {
		return technicalTermList;
	}

	public void setTechnicalTermsList(List<TechnicalTerm> technicalTermList) {
		this.technicalTermList = technicalTermList;
	}

	
	@Override
	public int hashCode() {
		
		return super.hashCode();
	}

	@Override
	public String toString() {
		
		return getTranslationList().toString();
	}

}