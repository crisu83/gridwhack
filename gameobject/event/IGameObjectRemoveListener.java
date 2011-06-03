package gridwhack.gameobject.event;

import gridwhack.event.IEventListener;

/**
 * Game object remove listener interface.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public interface IGameObjectRemoveListener extends IEventListener
{
	/**
	 * Actions to be taken when a game object is removed.
	 * @param event The event.
	 */
	public void onGameObjectRemove(GameObjectEvent event);
}
