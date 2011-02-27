package gridwhack.entity.character.event;

import gridwhack.event.CEvent;

/**
 * Character event class file.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class CharacterEvent extends CEvent
{
	public static final int CHARACTER_DEATH = 10;
	public static final int CHARACTER_HEALTHGAIN = 20;
	public static final int CHARACTER_HEALTHLOSS = 30;
	public static final int CHARACTER_MOVE = 40;
	public static final int CHARACTER_SPAWN = 50;

	/**
	 * Creates the event.
	 * @param type the type of this event.
	 * @param source the source of this event.
	 */
	public CharacterEvent(int type, Object source)
	{
		super(type, source);
	}
}
