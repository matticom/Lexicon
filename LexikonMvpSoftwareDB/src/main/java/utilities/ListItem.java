package utilities;

public class ListItem
{

	private int valueMember;
	private Object displayMember;
	
	public ListItem(int valueMember, Object displayMember)
	{
		
		this.valueMember = valueMember;
		this.displayMember = displayMember;
		
	}

	public int getValueMember()
	{
		return valueMember;
	}

	public Object getDisplayMember()
	{
		return displayMember;
	}

	@Override
	public String toString()
	{
		return displayMember.toString();
	}
	
}
