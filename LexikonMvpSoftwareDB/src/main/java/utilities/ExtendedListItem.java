package utilities;

public class ExtendedListItem {

	private String itemText;
	private String otherLanguage;
	private int fontsize;
	
	
	public ExtendedListItem(String name, String otherLanguage, int fontsize) {
	
		this.itemText = name;
		this.fontsize = fontsize;
		this.otherLanguage = otherLanguage;
	}


	public String getItemText() {
		return itemText;
	}


	public void setItemText(String name) {
		this.itemText = name;
	}


	public int getFontsize() {
		return fontsize;
	}


	public void setFontsize(int fontsize) {
		this.fontsize = fontsize;
	}


	public String getOtherLanguage() {
		return otherLanguage;
	}


	public void setOtherLanguage(String otherLanguage) {
		this.otherLanguage = otherLanguage;
	}


	@Override
	public String toString() {
		return getItemText();
	}
	
	
}
