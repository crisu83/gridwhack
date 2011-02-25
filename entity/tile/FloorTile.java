package gridwhack.entity.tile;

import gridwhack.fov.IViewer;
import gridwhack.grid.Grid;
import gridwhack.grid.GridTile;
import gridwhack.path.IMover;

/**
 * Floor tile class file.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class FloorTile extends GridTile
{
	/**
	 * Creates the tile.
	 * @param grid the grid this tile belongs to.
	 */
	public FloorTile(Grid grid) 
	{
		super("floortile.png", grid);
	}

	/**
	 * Returns whether a specific entity can walk on this tile.
	 * @param mover the entity.
	 * @return whether the tile is blocked.
	 */
	public boolean isBlocked(IMover mover)
	{
		return false; // not blocked for any entities
	}

	/**
	 * Returns whether a specific entity can see through this tile.
	 * @param viewer the entity.
	 * @return whether the tile is solid.
	 */
	public boolean isSolid(IViewer viewer)
	{
		return false; // not solid for eny entities
	}
}