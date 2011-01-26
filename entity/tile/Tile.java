package gridwhack.entity.tile;

import gridwhack.fov.IViewer;
import gridwhack.grid.Grid;
import gridwhack.grid.GridEntity;
import gridwhack.path.IMover;

public abstract class Tile extends GridEntity 
{
	/**
	 * Constructs the tile.
	 */
	public Tile(String filename, Grid grid) 
	{
		super(filename, grid);
	}
	
	public abstract boolean isBlocked(IMover mover);
	
	public abstract boolean isSolid(IViewer viewer);
}
