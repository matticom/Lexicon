package globals;

import javax.persistence.NoResultException;

public class TermDoesNotExist extends RuntimeException {
	
	public TermDoesNotExist() {
		super("Der Term existiert nicht!");
	}
	
	public TermDoesNotExist(String errorMessage) {
		super(errorMessage);
	}
	
}
