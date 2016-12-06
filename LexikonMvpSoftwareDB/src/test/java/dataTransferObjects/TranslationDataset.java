package dataTransferObjects;

public class TranslationDataset {

	private String translationName;
	private String translationNormalName;
	private String translationDescription;
	private int languageId;
	private int termId;
	
	public TranslationDataset() {
		
	}
	
	public TranslationDataset(String translationName, String translationNormalName, String translationDescription, int languageId, int termId) {
		
		this.translationName = translationName;
		this.translationNormalName = translationNormalName;
		this.translationDescription = translationDescription;
		this.languageId = languageId;
		this.termId = termId;
	}

	public String getTranslationName() {
		return translationName;
	}

	public void setTranslationName(String translationName) {
		this.translationName = translationName;
	}
	
	public String getTranslationNormalName() {
		return translationNormalName;
	}

	public void setTranslationNormalName(String translationNormalName) {
		this.translationNormalName = translationNormalName;
	}

	public String getTranslationDescription() {
		return translationDescription;
	}

	public void setTranslationDescription(String translationDescription) {
		this.translationDescription = translationDescription;
	}

	public int getLanguageId() {
		return languageId;
	}

	public void setLanguageId(int languageId) {
		this.languageId = languageId;
	}

	public int getTermId() {
		return termId;
	}

	public void setTermId(int termId) {
		this.termId = termId;
	}	
}
