package TransferObjects;

import java.util.List;

public class SpecialtyDataset extends TermDataset {

	private List<Integer> 	technicalTermList;
	
	public SpecialtyDataset(int termId, int translationId, String name, String description, List<Integer> technicalTermList) {
		super(termId, translationId, name, description);
		this.technicalTermList = technicalTermList;
	}

	public List<Integer> getTechnicalTermList() {
		return technicalTermList;
	}

	public void setTechnicalTermList(List<Integer> technicalTermList) {
		this.technicalTermList = technicalTermList;
	}

	@Override
	public boolean equals(Object arg0) {
		// TODO Auto-generated method stub
		return super.equals(arg0);
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
