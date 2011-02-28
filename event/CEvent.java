package gridwhack.event;

import java.util.EventObject;

/**
 * Core event class file.
 * All events must be extended from this class.
 */
abstract public class CEvent extends EventObject 
{
	private Object type;

	/**
	 * Creates the event.
	 * @param type the type of this event.
	 * @param source the source of this event.
	 */
	public CEvent(Object type, Object source)
	{
		super(source);

		this.type = type;
	}

	/**
	 * Returns the event type.
	 * @return the type.
	 */
	public Object getType()
	{
		return type;
	}
}
