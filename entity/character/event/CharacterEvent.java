package gridwhack.entity.character.event;

import gridwhack.event.CEvent;
import gridwhack.event.IEventType;

/**
 * Character event class file.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class CharacterEvent extends CEvent
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
	public CharacterEvent(IEventType type, Object source)
	{
		super(type, source);
	}
}
