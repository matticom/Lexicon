package globals;

public class TranslationDoesNotExist extends RuntimeException {
	
	public TranslationDoesNotExist() {
		super("Die �bersetzung existiert nicht!");
	}
	
	public TranslationDoesNotExist(String errorMessage) {
		super(errorMessage);
	}
	
}
