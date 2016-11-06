package globals;

public class TranslationAlreadyExists extends RuntimeException {
	
	public TranslationAlreadyExists() {
		super("Die Übersetzung existiert bereits!");
	}
	
	public TranslationAlreadyExists(String errorMessage) {
		super(errorMessage);
	}
	
}
