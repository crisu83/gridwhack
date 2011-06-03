package gridwhack.gameobject.tile;

import gridwhack.fov.IViewer;
import gridwhack.gameobject.IGameObjectType;
import gridwhack.gameobject.grid.Grid;
import gridwhack.gameobject.grid.GridGameObject;
import gridwhack.path.IMover;

import java.util.Random;

/**
 * Tile class.
 * All tiles must be extended from this class.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public abstract class Tile extends GridGameObject
{
	public static enum TileType implements IGameObjectType
	{
		ARCH,
		FLOOR,
		WALL,
		STAIRS_DOWN,
		STAIRS_UP,
	}

	// -------
	// Methods
	// -------

	/**
	 * Creates the tile.
	 */
	public Tile()
	{
		super();
	}

	// ----------------
	// Abstract methods
	// ----------------

	/**
	 * Returns whether a specific game object can exist on this tile.
	 * @param mover the game object.
	 * @return whether the tile is blocked.
	 */
	public abstract boolean isBlocked(IMover mover);

	/**
	 * Returns whether a specific game object can see through this tile.
	 * @param viewer the game object.
	 * @return whether the tile is solid.
	 */
	public abstract boolean isSolid(IViewer viewer);
}
