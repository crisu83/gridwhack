package gridwhack.entity.event;

import gridwhack.event.CEvent;
import gridwhack.event.IEventType;

/**
 * Entity event class file.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class EntityEvent extends CEvent 
{
	// Entity event types.
	public static enum Type implements IEventType {
		REMOVE,
	};

	/**
	 * Creates the event.
	 * @param type the type of this event.
	 * @param source the source of this event.
	 */
	public EntityEvent(IEventType type, Object source)
	{
		super(type, source);
	}
}
