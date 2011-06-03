package gridwhack.gameobject.event;

import gridwhack.base.BaseObject;
import gridwhack.event.GameEvent;
import gridwhack.event.IEventType;

/**
 * Game object event class.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class GameObjectEvent extends GameEvent
{
	// Game object event types.
	public static enum GameObjectEventType implements IEventType
	{
		REMOVE,
	};

	/**
	 * Creates the event.
	 * @param source The source of this event.
	 */
	public GameObjectEvent(BaseObject source)
	{
		super(source);
	}
}
