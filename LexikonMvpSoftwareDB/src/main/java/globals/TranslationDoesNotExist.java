package globals;

public class TranslationDoesNotExist extends RuntimeException {
	
	public TranslationDoesNotExist() {
		super("Die Übersetzung existiert nicht!");
	}
	
	public TranslationDoesNotExist(String errorMessage) {
		super(errorMessage);
	}
	
}
