package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Translations {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "TRANSLATIONS_ID")
	private int id;

	@Column(name = "TRANSLATIONS_NAME")
	private String name;

	@Column(name = "TRANSLATIONS_NORMALNAME")
	private String normalName;

	@Column(name = "TRANSLATIONS_DESCRIPTION") // ),
												// columnDefinition="CLOB(64000)")
	private String description;

	@OneToOne
	// @Column(name="LANGUAGE_ID")
	private Languages languages;

	@ManyToOne()
	// @Column(name="TERM_ID")
	private Term term;

	public Translations(String name, String normalName, String description, Languages languages, Term term) {
		this.name = name;
		this.normalName = normalName;
		this.description = description;
		this.languages = languages;
		this.term = term;
	}

	public Translations() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNormalName() {
		return normalName;
	}

	public void setNormalName(String normalName) {
		this.normalName = normalName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Languages getLanguages() {
		return languages;
	}

	public void setLanguages(Languages languages) {
		this.languages = languages;
	}

	public Term getTerm() {
		return term;
	}

	public void setTerm(Term term) {
		this.term = term;
	}

	@Override
	public int hashCode() {

		return super.hashCode();
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		if (getTerm() instanceof Specialty) {
			sb.append("Fachgebietsname : " + getName());
		} else {
			sb.append("Begriffsname : " + getName());
		}

		sb.append("   Beschreibung : " + getDescription());
		sb.append("   Sprache : " + getLanguages());
	
		return sb.toString();
	}

}
