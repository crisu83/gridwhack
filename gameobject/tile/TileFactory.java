package gridwhack.gameobject.tile;

import gridwhack.gameobject.IGameObjectType;
import gridwhack.gameobject.exception.InvalidGameObjectException;
import gridwhack.gameobject.grid.*;
import gridwhack.gameobject.tile.Tile.TileType;
import gridwhack.util.Vector2;

/**
 * Tile factory class.
 * Provides functionality for spawning tiles into the game.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class TileFactory
{
	// ----------
	// Properties
	// ----------

	private static final TileFactory instance = new TileFactory();

	// -------
	// Methods
	// -------

	/**
	 * Creates the object.
	 * Private to enforce the singleton pattern.
	 */
	private TileFactory() {}

	/**
	 * Returns the single instance of this object.
	 * @return The instance.
	 */
	public static TileFactory getInstance()
	{
		return instance;
	}

	/**
	 * Spawns a specific game object.
	 * @param type The game object type.
	 * @param gx The grid x-coordinate where to spawn the object.
	 * @param gy The grid y-coordinate where to spawn the object.
	 * @return The game object.
	 * @throws InvalidGameObjectException If the game object could not be created.
	 */
	public GridGameObject spawn(IGameObjectType type, int gx, int gy) throws InvalidGameObjectException
	{
		GridGameObject object;

		switch ((TileType) type)
		{
			case ARCH:
				object = new Arch();
				break;

			case FLOOR:
				object = new Floor();
				break;

			case STAIRS_DOWN:
				object = new StairsDown();
				break;

			case STAIRS_UP:
				object = new StairsUp();
				break;

			case WALL:
				object = new Wall();
				break;

			default:
				throw new InvalidGameObjectException("Invalid game object type.");
		}

		object.init();
		object.setGridPosition(new Vector2(gx, gy));

		return object;
	}
}
