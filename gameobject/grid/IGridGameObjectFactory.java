package gridwhack.gameobject.grid;

import gridwhack.gameobject.IGameObjectType;
import gridwhack.gameobject.exception.InvalidGameObjectException;

/**
 * Grid game object factory interface.
 * All grid game object factories must be extended from this class.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public interface IGridGameObjectFactory
{
	/**
	 * Spawns a specific game object.
	 * @param type The game object type.
	 * @param grid The grid the game object belongs to.
	 * @return The game object.
	 * @throws gridwhack.gameobject.exception.InvalidGameObjectException If the game object could not be created.
	 */
	public GridGameObject spawn(IGameObjectType type, int gx, int gy, Grid grid) throws InvalidGameObjectException;
}
