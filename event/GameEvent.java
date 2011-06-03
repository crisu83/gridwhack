package gridwhack.event;

import gridwhack.base.BaseObject;

import java.util.EventObject;

/**
 * GameEvent class file.
 * All events must be extended from this class.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
abstract public class GameEvent extends EventObject
{
	private IEventType type;

	public GameEvent(BaseObject source)
	{
		super(source);
	}

	/**
	 * Creates the event.
	 * @param type the event type.
	 * @param source the source of this event.
	 */
	public GameEvent(IEventType type, BaseObject source)
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
