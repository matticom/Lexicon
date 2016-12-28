package windows;

public class AssignmentTableRowObject {
	
	private int technicalTermId;
	private int specialtyId;
	private String technicalTermGermanName;
	private String technicalTermSpanishName;
	private String specialtyGermanName;
	private String specialtySpanishName;
	
	
	
	public AssignmentTableRowObject(int technicalTermId, int specialtyId) {
		this.technicalTermId = technicalTermId;
		this.specialtyId = specialtyId;
	}


	public AssignmentTableRowObject(int technicalTermId, int specialtyId, String technicalTermGermanName, String technicalTermSpanishName,
			String specialtyGermanName, String specialtySpanishName) {
	
		this.technicalTermId = technicalTermId;
		this.specialtyId = specialtyId;
		this.technicalTermGermanName = technicalTermGermanName;
		this.technicalTermSpanishName = technicalTermSpanishName;
		this.specialtyGermanName = specialtyGermanName;
		this.specialtySpanishName = specialtySpanishName;
	}


	public int getTechnicalTermId() {
		return technicalTermId;
	}


	public void setTechnicalTermId(int technicalTermId) {
		this.technicalTermId = technicalTermId;
	}


	public int getSpecialtyId() {
		return specialtyId;
	}


	public void setSpecialtyId(int specialtyId) {
		this.specialtyId = specialtyId;
	}


	public String getTechnicalTermGermanName() {
		return technicalTermGermanName;
	}


	public void setTechnicalTermGermanName(String technicalTermGermanName) {
		this.technicalTermGermanName = technicalTermGermanName;
	}


	public String getTechnicalTermSpanishName() {
		return technicalTermSpanishName;
	}


	public void setTechnicalTermSpanishName(String technicalTermSpanishName) {
		this.technicalTermSpanishName = technicalTermSpanishName;
	}


	public String getSpecialtyGermanName() {
		return specialtyGermanName;
	}


	public void setSpecialtyGermanName(String specialtyGermanName) {
		this.specialtyGermanName = specialtyGermanName;
	}


	public String getSpecialtySpanishName() {
		return specialtySpanishName;
	}


	public void setSpecialtySpanishName(String specialtySpanishName) {
		this.specialtySpanishName = specialtySpanishName;
	}
}
