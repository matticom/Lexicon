package exceptions;

public class LanguageEntryInTechnicalTermAlreadyExists extends RuntimeException {
	
	public LanguageEntryInTechnicalTermAlreadyExists() {
		super("Es gibt bereits einen �bersetzung in dieser Sprache f�r diesen Fachbegriff!");
	}
	
	public LanguageEntryInTechnicalTermAlreadyExists(String errorMessage) {
		super(errorMessage);
	}
	
}
