package gridwhack.entity.unit.player.event;

import gridwhack.event.CEvent;

/**
 * Player event class file.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class PlayerEvent extends CEvent
{
	public static final int PLAYER_EXPERIENCEGAIN = 200;
	public static final int PLAYER_LEVELGAIN = 201;

	/**
	 * Creates the event.
	 * @param type the type of this event.
	 * @param source the source of this event.
	 */
	public PlayerEvent(int type, Object source)
	{
		super(type, source);
	}
}
