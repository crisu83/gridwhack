package gridwhack.entity.event;

import gridwhack.event.CEvent;

/**
 * Entity event class file.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class EntityEvent extends CEvent 
{
	public static enum Type {
		REMOVE,
	};

	/**
	 * Creates the event.
	 * @param type the type of this event.
	 * @param source the source of this event.
	 */
	public EntityEvent(Type type, Object source)
	{
		super(type, source);
	}
}
