package gridwhack.event;

import java.util.EventObject;

/**
 * Base event class file.
 * All events must be extended from this class.
 */
abstract public class CEvent extends EventObject 
{
	private int type; // type of event.
	
	/**
	 * Creates the event.
	 * @param type the type of this event.
	 * @param source the source of this event.
	 */
	public CEvent(int type, Object source) 
	{
		super(source);
		
		this.type = type;
	}
	
	/**
	 * Returns the type of this event.
	 * @return the type.
	 */
	public int getType()
	{
		return type;
	}
}
