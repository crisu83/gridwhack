package gridwhack.entity.unit.event;

import gridwhack.event.CEvent;

/**
 * Unit event class file.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class UnitEvent extends CEvent 
{
	public static final int UNIT_DEATH = 200;
	public static final int UNIT_HEALTHGAIN = 201;
	public static final int UNIT_HEALTHLOSS = 202;
	public static final int UNIT_MOVE = 203;
	public static final int UNIT_SPAWN = 204;

	/**
	 * Creates the event.
	 * @param type the type of this event.
	 * @param source the source of this event.
	 */
	public UnitEvent(int type, Object source) 
	{
		super(type, source);
	}
}
