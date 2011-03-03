package gridwhack.entity.tile;

import gridwhack.exception.ComponentNotFoundException;
import gridwhack.grid.Grid;
import gridwhack.grid.GridTile;

/**
 * Tile factory class file.
 * Allows for creating tiles.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class TileFactory 
{
	/**
	 * Returns new tiles of the given type.
	 * @param type the tile type.
	 * @param grid the grid the tile belongs to.
	 * @return the tile.
	 */
	public static GridTile factory(GridTile.Type type, Grid grid) throws ComponentNotFoundException
	{
		GridTile tile;

		// return the requested tile.
		switch( type )
		{
			case FLOOR:
				tile = new FloorTile(grid);
				break;

			case WALL:
				tile = new WallTile(grid);
				break;

			default:
				throw new ComponentNotFoundException("Failed to create tile, type '" + type + "' is invalid!");
		}

		// Initialize the tile.
		tile.init();

		return tile;
	}
}
