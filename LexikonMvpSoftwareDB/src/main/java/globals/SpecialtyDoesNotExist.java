package globals;

public class SpecialtyDoesNotExist extends RuntimeException {
	
	public SpecialtyDoesNotExist() {
		super("Das Fachgebiet existiert nicht!");
	}
	
	public SpecialtyDoesNotExist(String errorMessage) {
		super(errorMessage);
	}
	
}
