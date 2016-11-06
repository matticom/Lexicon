package globals;

public class SpecialtyAlreadyExists extends RuntimeException {
	
	public SpecialtyAlreadyExists() {
		super("Das Fachgebiet existiert bereits!");
	}
	
	public SpecialtyAlreadyExists(String errorMessage) {
		super(errorMessage);
	}
	
}
