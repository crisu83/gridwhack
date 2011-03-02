package gridwhack.event;

import java.util.EventObject;

/**
 * Core event class file.
 * All events must be extended from this class.
 */
abstract public class CEvent extends EventObject 
{
	/**
	 * Creates the event.
	 * @param source the source of this event.
	 */
	public CEvent(Object source)
	{
		super(source);
	}
}
