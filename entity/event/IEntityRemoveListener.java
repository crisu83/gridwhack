package gridwhack.entity.event;

import gridwhack.event.IEventListener;

/**
 * Entity remove listener interface file.
 * All entity remove listeners must implement this interface.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public interface IEntityRemoveListener extends IEntityListener
{
	/**
	 * Actions to be taken when an entity is removed.
	 * @param e the event.
	 */
	public void onEntityRemove(EntityEvent e);
}
