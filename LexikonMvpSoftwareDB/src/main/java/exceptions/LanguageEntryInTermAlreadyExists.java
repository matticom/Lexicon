package exceptions;

public class LanguageEntryInTermAlreadyExists extends RuntimeException {
	
	public LanguageEntryInTermAlreadyExists() {
		super("Es gibt bereits einen Übersetzung in dieser Sprache für diesen Fachbegriff oder dieses Fachgebiet!");
	}
	
	public LanguageEntryInTermAlreadyExists(String errorMessage) {
		super(errorMessage);
	}
	
}
