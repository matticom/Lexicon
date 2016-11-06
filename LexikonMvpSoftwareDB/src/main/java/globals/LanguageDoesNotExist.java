package globals;

public class LanguageDoesNotExist extends RuntimeException {
	
	public LanguageDoesNotExist() {
		super("Die Sprache existiert nicht!");
	}
	
	public LanguageDoesNotExist(String errorMessage) {
		super(errorMessage);
	}
	
}
