package gridwhack.entity.character.player.event;

import gridwhack.event.CEvent;

/**
 * Player event class file.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class PlayerEvent extends CEvent
{
	public static enum Type {
		EXPERIENCEGAIN,
		LEVELGAIN,
	};

	/**
	 * Creates the event.
	 * @param type the type of this event.
	 * @param source the source of this event.
	 */
	public PlayerEvent(Type type, Object source)
	{
		super(type, source);
	}
}
