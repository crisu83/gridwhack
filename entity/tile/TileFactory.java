package gridwhack.entity.tile;

import gridwhack.grid.Grid;
import gridwhack.grid.GridTile;

/**
 * Tile factory class file.
 * Allows for creating tiles.
 */
public class TileFactory 
{
	/**
	 * List of all tile types.
	 */
	public static enum TileType {
		//DOOR,
		FLOOR,
		WALL,
		//WATER
	}

	/**
	 * Returns new tiles of the given type.
	 * @param type the tile type.
	 * @param grid the grid the tile belongs to.
	 * @return the tile.
	 */
	public static GridTile factory(TileType type, Grid grid)
	{
		// return the requested tile.
		switch( type )
		{
		case FLOOR:
			return new FloorTile(grid);
		case WALL:
			return new WallTile(grid);
		default:
			return null;
		}
	}
}
