package gridwhack.gameobject.character.player.event;

import gridwhack.base.BaseObject;
import gridwhack.event.GameEvent;
import gridwhack.event.IEventType;

/**
 * Player event class.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class PlayerEvent extends GameEvent
{
	public static enum Type implements IEventType {
		EXPERIENCEGAIN,
		LEVELGAIN,
		MOVE,
		SPAWN,
	};

	// -------
	// Methods
	// -------

	/**
	 * Creates the event.
	 * @param type The type of this event.
	 * @param source The source of this event.
	 */
	public PlayerEvent(IEventType type, BaseObject source)
	{
		super(type, source);
	}
}
