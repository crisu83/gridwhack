package gridwhack.grid;

import gridwhack.fov.IViewer;
import gridwhack.path.IMover;

/**
 * Grid tile class file.
 * All tiles must be extended from this class.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public abstract class GridTile extends GridEntity
{
	/**
	 * Creates the tile.
	 * @param filename the image filename.
	 * @param grid the grid this tile belongs to.
	 */
	public GridTile(String filename, Grid grid)
	{
		super(filename, grid);
	}

	/**
	 * Returns whether a specific entity can walk on this tile.
	 * @param mover the entity.
	 * @return whether the tile is blocked.
	 */
	public abstract boolean isBlocked(IMover mover);

	/**
	 * Returns whether a specific entity can see through this tile.
	 * @param viewer the entity.
	 * @return whether the tile is solid.
	 */
	public abstract boolean isSolid(IViewer viewer);
}
