package model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;


@Entity


public class Specialty extends Term {
	
	@OneToMany(mappedBy = "specialty", targetEntity = TechnicalTerm.class)
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
		// TODO Auto-generated method stub
		return super.hashCode();
	}

	@Override
	public String toString() {
		
		return "Fachgebiet" + getTranslationList();
	}

}