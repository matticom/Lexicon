package exceptions;

import javax.persistence.NoResultException;

public class TechnicalTermDoesNotExist extends RuntimeException {
	
	public TechnicalTermDoesNotExist() {
		super("Der Begriff existiert nicht!");
	}
	
	public TechnicalTermDoesNotExist(String errorMessage) {
		super(errorMessage);
	}
	
}
