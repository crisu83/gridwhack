package gridwhack.entity.event;

import gridwhack.event.IEventListener;

public interface IEntityListener extends IEventListener 
{
	/**
	 * Actions to be taken when an entity is removed.
	 * @param e the event.
	 */
	public void onEntityRemove(EntityEvent e);
}
