package model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class TechnicalTerm extends Term {

	@ManyToOne
	private Specialty specialty;

	public Specialty getSpecialty() {
		return specialty;
	}

	public void setSpecialty(Specialty specialty) {
		this.specialty = specialty;
	}

}
