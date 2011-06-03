package gridwhack.gameobject.character.hostile;

import gridwhack.gameobject.IGameObjectType;
import gridwhack.gameobject.character.hostile.HostileCharacter.HostileType;
import gridwhack.gameobject.exception.InvalidGameObjectException;
import gridwhack.gameobject.grid.Grid;
import gridwhack.gameobject.grid.GridGameObject;
import gridwhack.gameobject.grid.IGridGameObjectFactory;
import gridwhack.util.Vector2;

/**
 * Hostile character factory.
 * Provides the functionality for spawning hostile characters into the game.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class HostileFactory implements IGridGameObjectFactory
{
	// ----------
	// Properties
	// ----------

	private static final HostileFactory instance = new HostileFactory();

	// -------
	// Methods
	// -------

	/**
	 * Creates the factory.
	 * Private to enforce the singleton pattern.
	 */
	private HostileFactory() {}

	/**
	 * Returns the single instance of this object.
	 * @return The instance.
	 */
	public static HostileFactory getInstance()
	{
		return instance;
	}

	/**
	 * Spawns a specific game object.
	 * @param type The game object type.
	 * @param grid The grid the game object belongs to.
	 * @return The game object.
	 * @throws InvalidGameObjectException If the game object could not be created.
	 */
	public GridGameObject spawn(IGameObjectType type, int gx, int gy, Grid grid) throws InvalidGameObjectException
	{
		GridGameObject object;

		switch ((HostileType) type)
		{
			case IMP:
				object = new Imp();
				break;

			case SKELETON:
				object = new Skeleton();
				break;

			default:
				throw new InvalidGameObjectException("Invalid game object type.");
		}

		object.setGrid(grid);
		object.setGridPosition(new Vector2(gx, gy));
		object.init();

		return object;
	}
}
