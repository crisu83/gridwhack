package gridwhack.entity.unit.event;

import gridwhack.event.CEvent;

public class UnitEvent extends CEvent 
{
	public static final int UNIT_DEATH = 200;
	public static final int UNIT_HEALTHGAIN = 201;
	public static final int UNIT_HEALTHLOSS = 202;
	public static final int UNIT_MOVE = 203;
	public static final int UNIT_SPAWN = 204;

	public UnitEvent(int type, Object source) 
	{
		super(type, source);
	}
}
