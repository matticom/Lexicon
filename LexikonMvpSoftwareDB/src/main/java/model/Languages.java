package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Languages
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="LANGUAGES_ID")
	private int id;
	
	@Column(name="LANGUAGES_NAME")
	private String name;
	

	public Languages(String name)
	{
		this();
		this.name = name;
	}
	
	public Languages()
	{
		
	}
	
	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
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
		return getName().toString();
	}
	
	
}
