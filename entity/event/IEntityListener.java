package gridwhack.entity.event;

import gridwhack.event.IEventListener;

/**
 * Entity listener interface file.
 * All entity listeners must implement this interface.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public interface IEntityListener extends IEventListener 
{
	/**
	 * Actions to be taken when an entity is removed.
	 * @param e the event.
	 */
	public void onEntityRemove(EntityEvent e);
}
