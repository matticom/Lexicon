package utilities;

public class ExtendedListItem {

	private String itemText;
	private int fontsize;
	
	
	public ExtendedListItem(String name, int fontsize) {
	
		this.itemText = name;
		this.fontsize = fontsize;
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


	@Override
	public String toString() {
		return getItemText();
	}
	
	
}
