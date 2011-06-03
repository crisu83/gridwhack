package gridwhack.gameobject;

import gridwhack.gameobject.exception.InvalidGameObjectException;

/**
 * Game object factory interface.
 * All game object factories must be extended from this class.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public interface IGameObjectFactory
{
	/**
	 * Spawns a specific game object.
	 * @param type The game object type.
	 * @param x The x-coordinate to spawn the game object to.
	 * @param y The y-coordinate to spawn the game object to.
	 * @return The game object.
	 * @throws InvalidGameObjectException If the game object could not be created.
	 */
	public GameObject spawn(IGameObjectType type, int x, int y) throws InvalidGameObjectException;
}
