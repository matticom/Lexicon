package exceptions;

public class LanguageEntryInTechnicalTermAlreadyExists extends RuntimeException {
	
	public LanguageEntryInTechnicalTermAlreadyExists() {
		super("Es gibt bereits einen Übersetzung in dieser Sprache für diesen Fachbegriff!");
	}
	
	public LanguageEntryInTechnicalTermAlreadyExists(String errorMessage) {
		super(errorMessage);
	}
	
}
