package model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;



@Entity
// @PrimaryKeyJoinColumn(referencedColumnName="id") --> macht er auch alleine

public class TechnicalTerm extends Term
{
		
	@ManyToOne
	private Specialty 	specialty;

	public Specialty getSpecialty()
	{
		return specialty;
	}

	public void setSpecialty(Specialty specialty)
	{
		this.specialty = specialty;
	}
	
}
