package model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Inheritance( strategy = InheritanceType.TABLE_PER_CLASS )
public abstract class Term
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="TERM_ID")
	private int id;
	
	@OneToMany(targetEntity = Translations.class, cascade=CascadeType.ALL, mappedBy="term")
//	@Column(name="TRANSLATION_ID")
	private List<Translations> 	translationsList;
		
	
	public Term()
	{
		translationsList = new ArrayList<Translations>();
	}
	
	public List<Translations> getTranslationList()
	{
		return translationsList;
	}
	
	public void setTranslationList(List<Translations> translationsList)
	{
		this.translationsList = translationsList;
	}
	
	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}

	
	
	
}
