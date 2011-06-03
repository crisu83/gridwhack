package gridwhack.gameobject.character.event;

import gridwhack.base.BaseObject;
import gridwhack.event.GameEvent;
import gridwhack.event.IEventType;

/**
 * Character event class.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class CharacterEvent extends GameEvent
{
	// Character event types.
	public static enum Type implements IEventType {
		DEATH,
		HEALTHGAIN,
		HEALTHLOSS,
		MOVE,
		SPAWN,
	};

	/**
	 * Creates the event.
	 * @param type the type of this event.
	 * @param source the source of this event.
	 */
	public CharacterEvent(IEventType type, BaseObject source)
	{
		super(type, source);
	}
}
