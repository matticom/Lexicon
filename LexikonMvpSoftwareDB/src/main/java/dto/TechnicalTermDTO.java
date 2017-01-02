package dto;

import model.Specialty;
import model.TechnicalTerm;
import model.Translations;

public class TechnicalTermDTO {
	
	private final int GERMAN = 1;
	private final int SPANISH = 2;
	
	private String germanName;
	private String spanishName;
	private String germanNormalName;
	private String spanishNormalName;
	private String germanDescription;
	private String spanishDescription;
	private String germanSpecialty;
	private String spanishSpecialty;
	private int termId;

	public TechnicalTermDTO(TechnicalTerm technicalTerm) {
		copyData(technicalTerm);
	}

	private void copyData(TechnicalTerm technicalTerm) {
		Specialty specialty = technicalTerm.getSpecialty();
		termId = technicalTerm.getId();
		
		for (Translations translation : specialty.getTranslationList()) {
			if (translation.getLanguages().getId() == GERMAN) {
				germanSpecialty = translation.getName();
			}
			if (translation.getLanguages().getId() == SPANISH) {
				spanishSpecialty = translation.getName();
			}
		}
		
		for (Translations translation : technicalTerm.getTranslationList()) {
			if (translation.getLanguages().getId() == GERMAN) {
				germanName = translation.getName();
				germanNormalName = translation.getNormalName();
				germanDescription = translation.getDescription();
			}
			if (translation.getLanguages().getId() == SPANISH) {
				spanishName = translation.getName();
				spanishNormalName = translation.getNormalName();
				spanishDescription = translation.getDescription();
			}
		}
	}

	public String getGermanName() {
		return germanName;
	}

	public String getSpanishName() {
		return spanishName;
	}

	public String getGermanNormalName() {
		return germanNormalName;
	}

	public String getSpanishNormalName() {
		return spanishNormalName;
	}

	public String getGermanDescription() {
		return germanDescription;
	}

	public String getSpanishDescription() {
		return spanishDescription;
	}

	public String getGermanSpecialty() {
		return germanSpecialty;
	}

	public String getSpanishSpecialty() {
		return spanishSpecialty;
	}

	public int getTermId() {
		return termId;
	}
}
