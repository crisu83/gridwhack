package gridwhack.entity.character.effect.event;

import gridwhack.event.CEvent;

/**
 * Character effect event class file.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class CharacterEffectEvent extends CEvent
{
	public static enum Type {
		REMOVE,
	};

	/**
	 * Creates the event.
	 * @param type the type of this event.
	 * @param source the source of this event.
	 */
	public CharacterEffectEvent(Type type, Object source)
	{
		super(type, source);
	}
}
