package TransferObjects;

public class TechnicalTermDataset extends TermDataset {
	
	private int 	specialtyId;

	public TechnicalTermDataset(int termId, int translationId, String name, String description, int specialtyId) {
		super(termId, translationId, name, description);
		this.specialtyId = specialtyId;
	}
	
	public int getSpecialtyId() {
		return specialtyId;
	}

	public void setSpecialtyId(int specialtyId) {
		this.specialtyId = specialtyId;
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
