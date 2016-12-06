package utilities;

public class ListItem
{

	private int valueMember;
	private String displayMember;
	
	public ListItem(int valueMember, String displayMember)
	{
		
		this.valueMember = valueMember;
		this.displayMember = displayMember;
		
	}

	public int getValueMember()
	{
		return valueMember;
	}

	public String getDisplayMember()
	{
		return displayMember;
	}

	@Override
	public String toString()
	{
		return displayMember.toString();
	}
	
}
