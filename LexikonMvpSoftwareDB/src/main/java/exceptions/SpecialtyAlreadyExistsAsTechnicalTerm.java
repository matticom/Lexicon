package exceptions;

public class SpecialtyAlreadyExistsAsTechnicalTerm extends RuntimeException {
	
	public SpecialtyAlreadyExistsAsTechnicalTerm() {
		super("Das Fachgebiet ist bereits als Begriff registriert!");
	}
	
	public SpecialtyAlreadyExistsAsTechnicalTerm(String errorMessage) {
		super(errorMessage);
	}
	
}
