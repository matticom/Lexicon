package TransferObjects;

public class TechnicalTermDataset extends TermDataset {
	
	private String 	specialty;

	public TechnicalTermDataset(String name, String description, String language, String specialty) {
		super(name, description, language);
		this.specialty = specialty;
	}
	
	public String getSpecialty() {
		return specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
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
