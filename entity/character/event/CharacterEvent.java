package gridwhack.entity.character.event;

import gridwhack.event.CEvent;

/**
 * Character event class file.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class CharacterEvent extends CEvent
{
	private Type type;

	// Character event types.
	public static enum Type {
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
	public CharacterEvent(Type type, Object source)
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
