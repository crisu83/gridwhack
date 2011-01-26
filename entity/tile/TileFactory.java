package gridwhack.entity.tile;

import gridwhack.grid.Grid;

public class TileFactory 
{
	public static enum TileType {
		DOOR,
		FLOOR,
		WALL,
		WATER
	}
	
	public static Tile factory(TileType type, Grid grid)
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
