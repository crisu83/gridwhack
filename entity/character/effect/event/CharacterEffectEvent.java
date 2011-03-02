package gridwhack.entity.character.effect.event;

import gridwhack.event.CEvent;

/**
 * Character effect event class file.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class CharacterEffectEvent extends CEvent
{
	private Type type;

	// Character effect event types.
	public static enum Type {
		AFFECT,
		FADE,
	};

	/**
	 * Creates the event.
	 * @param type the type of this event.
	 * @param source the source of this event.
	 */
	public CharacterEffectEvent(Type type, Object source)
	{
		super(source);

		this.type = type;
	}

	/**
	 * Returns the event type.
	 * @return the type.
	 */
	public Type getType()
	{
		return type;
	}
}
