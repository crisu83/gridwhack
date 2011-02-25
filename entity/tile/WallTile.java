package gridwhack.entity.tile;

import gridwhack.fov.IViewer;
import gridwhack.grid.Grid;
import gridwhack.grid.GridTile;
import gridwhack.path.IMover;

/**
 * Wall tile class file.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class WallTile extends GridTile
{
	/**
	 * Creates the tile.
	 * @param grid the grid this tile belongs to.
	 */
	public WallTile(Grid grid) 
	{
		super("walltile.png", grid);
	}

	/**
	 * Returns whether a specific entity can walk on this tile.
	 * @param mover the entity.
	 * @return whether the tile is blocked.
	 */
	public boolean isBlocked(IMover mover)
	{
		return true; // blocked for all entities
	}

	/**
	 * Returns whether a specific entity can see through this tile.
	 * @param viewer the entity.
	 * @return whether the tile is solid.
	 */
	public boolean isSolid(IViewer viewer)
	{
		return true; // solid for all entities
	}
}