package Util;

public class ListItem
{
	
	private Object valueMember;
	private Object displayMember;
	
	public ListItem(Object valueMember, Object displayMemeber)
	{
		this.valueMember = valueMember;
		this.displayMember = displayMemeber;
	}

	public Object getValueMember()
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
		return getDisplayMember().toString();
	}
	
	
	
	

}
