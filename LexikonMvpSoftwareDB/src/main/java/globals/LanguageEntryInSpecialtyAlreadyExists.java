package globals;

public class LanguageEntryInSpecialtyAlreadyExists extends RuntimeException {
	
	public LanguageEntryInSpecialtyAlreadyExists() {
		super("Es gibt bereits einen �bersetzung in dieser Sprache f�r das Fachgebiet!");
	}
	
	public LanguageEntryInSpecialtyAlreadyExists(String errorMessage) {
		super(errorMessage);
	}
	
}
