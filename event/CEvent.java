package gridwhack.event;

import java.util.EventObject;

/**
 * Core event class file.
 * All events must be extended from this class.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
abstract public class CEvent extends EventObject 
{
	private IEventType type;

	/**
	 * Creates the event.
	 * @param type the event type.
	 * @param source the source of this event.
	 */
	public CEvent(IEventType type, Object source)
	{
		super(source);

		this.type = type;
	}

	/**
	 * Returns the event type.
	 * @return the type.
	 */
	public IEventType getType()
	{
		return type;
	}
}
