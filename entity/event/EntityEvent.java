package gridwhack.entity.event;

import gridwhack.event.CEvent;

/**
 * Entity event class file.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class EntityEvent extends CEvent 
{
	public static final int ENTITY_REMOVE = 200;

	/**
	 * Creates the event.
	 * @param type the type of this event.
	 * @param source the source of this event.
	 */
	public EntityEvent(int type, Object source) 
	{
		super(type, source);
	}
}
