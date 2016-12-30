package exceptions;

public class TechnicalTermAlreadyExistsAsSpecialty extends RuntimeException {
	
	public TechnicalTermAlreadyExistsAsSpecialty() {
		super("Der Begriff ist bereits als Fachgebiet registriert!");
	}
	
	public TechnicalTermAlreadyExistsAsSpecialty(String errorMessage) {
		super(errorMessage);
	}
	
}
