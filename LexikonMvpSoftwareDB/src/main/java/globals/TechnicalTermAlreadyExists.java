package globals;

public class TechnicalTermAlreadyExists extends RuntimeException {
	
	public TechnicalTermAlreadyExists() {
		super("Der Begriff existiert bereits!");
	}
	
	public TechnicalTermAlreadyExists(String errorMessage) {
		super(errorMessage);
	}
	
}
