package gridwhack.gameobject.character.effect.event;

import gridwhack.base.BaseObject;
import gridwhack.event.GameEvent;
import gridwhack.event.IEventType;

/**
 * Character effect event class.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class CharacterEffectEvent extends GameEvent
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
	public CharacterEffectEvent(IEventType type, BaseObject source)
	{
		super(type, source);
	}
}
