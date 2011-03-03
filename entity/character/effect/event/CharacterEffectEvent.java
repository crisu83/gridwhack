package gridwhack.entity.character.effect.event;

import gridwhack.event.CEvent;
import gridwhack.event.IEventType;

/**
 * Character effect event class file.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class CharacterEffectEvent extends CEvent
{
	// Character effect event types.
	public static enum Type implements IEventType {
		APPLY,
		TICK,
		FADE,
	};

	/**
	 * Creates the event.
	 * @param type the type of this event.
	 * @param source the source of this event.
	 */
	public CharacterEffectEvent(IEventType type, Object source)
	{
		super(type, source);
	}
}
