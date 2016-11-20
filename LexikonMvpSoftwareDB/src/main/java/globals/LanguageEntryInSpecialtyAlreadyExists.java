package globals;

public class LanguageEntryInSpecialtyAlreadyExists extends RuntimeException {
	
	public LanguageEntryInSpecialtyAlreadyExists() {
		super("Es gibt bereits einen Übersetzung in dieser Sprache für das Fachgebiet!");
	}
	
	public LanguageEntryInSpecialtyAlreadyExists(String errorMessage) {
		super(errorMessage);
	}
	
}
