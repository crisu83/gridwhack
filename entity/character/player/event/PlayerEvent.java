package gridwhack.entity.character.player.event;

import gridwhack.event.CEvent;
import gridwhack.event.IEventType;

/**
 * Player event class file.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class PlayerEvent extends CEvent
{
	// Player event types.
	public static enum Type implements IEventType {
		EXPERIENCEGAIN,
		LEVELGAIN,
		MOVE,
		SPAWN,
	};

	/**
	 * Creates the event.
	 * @param type the type of this event.
	 * @param source the source of this event.
	 */
	public PlayerEvent(IEventType type, Object source)
	{
		super(type, source);
	}
}
