package gridwhack.entity.character.player.event;

import gridwhack.event.CEvent;

/**
 * Player event class file.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class PlayerEvent extends CEvent
{
	private Type type;

	// Player event types.
	public static enum Type {
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
	public PlayerEvent(Type type, Object source)
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
