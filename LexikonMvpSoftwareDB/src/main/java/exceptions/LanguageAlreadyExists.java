package exceptions;

public class LanguageAlreadyExists extends RuntimeException {
	
	public LanguageAlreadyExists() {
		super("Die Sprache ist bereits angelegt!");
	}
	
	public LanguageAlreadyExists(String errorMessage) {
		super(errorMessage);
	}
	
}
