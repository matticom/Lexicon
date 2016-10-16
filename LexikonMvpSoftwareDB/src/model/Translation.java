package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Translation
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="TRANSLATION_ID")
	private int id;
		
	@Column(name="TRANSLATION_NAME")
	private String name;
	
	@Column(name="TRANSLATION_DESCRIPTION")
	private String description;
	
	@OneToOne
	private Language language;
	
	@ManyToOne()
//	@JoinColumn(name="TERM_ID", referencedColumnName="TERM_ID")
	private Term term;
	
		
	

	public Translation(String name, String description, Language language, Term term)
	{
		
		this.name = name;
		this.description = description;
		this.language = language;
		this.term = term;
	}
	
	public Translation()
	{
		
	}
		
	
	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}
			
	
	public String getName()
	{
		return name;
	}
		
	public void setName(String name)
	{
		this.name = name;
	}

	public String getDescription()
	{
		return description;
	}
	
	public void setDescription(String description)
	{
		this.description = description;
	}
	
	
	public Language getLanguage()
	{
		return language;
	}

	public void setLanguage(Language language)
	{
		this.language = language;
	}
	
	public Term getTerm() {
		return term;
	}

	public void setTerm(Term term) {
		this.term = term;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("sname : " + getName());
		sb.append("   Beschreibung : " + getDescription());
		sb.append("   Sprache : " + getLanguage());
		sb.append("   ID : " + getTerm());
		return sb.toString();
	}
	
	
	
}
