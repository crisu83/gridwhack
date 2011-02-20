package gridwhack.entity.tile;

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
	public static GridTile factory(GridTile.Type type, Grid grid)
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
