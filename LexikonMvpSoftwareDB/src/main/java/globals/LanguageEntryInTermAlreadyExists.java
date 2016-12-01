package globals;

public class LanguageEntryInTermAlreadyExists extends RuntimeException {
	
	public LanguageEntryInTermAlreadyExists() {
		super("Es gibt bereits einen �bersetzung in dieser Sprache f�r diesen Fachbegriff oder dieses Fachgebiet!");
	}
	
	public LanguageEntryInTermAlreadyExists(String errorMessage) {
		super(errorMessage);
	}
	
}
