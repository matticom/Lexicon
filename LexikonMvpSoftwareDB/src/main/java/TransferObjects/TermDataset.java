package TransferObjects;


abstract public class TermDataset {
	
	private int			termId;
	private int			translationId;
	private String		name;
	private String		description;
	
	
	public TermDataset (int termId, int translationId, String name, String description) {
		this.termId = termId;
		this.translationId = translationId;
		this.name = name;
		this.description = description;
	}
	
	public int getTermId() {
		return termId;
	}
	public void setTermId(int termId) {
		this.termId = termId;
	}
	public int getTranslationId() {
		return translationId;
	}
	public void setTranslationId(int translationId) {
		this.translationId = translationId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
