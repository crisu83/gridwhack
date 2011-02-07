package gridwhack.entity.event;

import gridwhack.event.CEvent;

public class EntityEvent extends CEvent 
{
	public static final int ENTITY_REMOVE = 200;
	
	public EntityEvent(int type, Object source) 
	{
		super(type, source);
	}
}
