package gridwhack;

import java.util.EventObject;

public class CEvent extends EventObject 
{
	private String type;
	
	public CEvent(String type, Object source) 
	{
		super(source);
		
		this.type = type;
	}
	
	public String getType()
	{
		return type;
	}
}
