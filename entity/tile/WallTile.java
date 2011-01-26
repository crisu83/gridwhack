package gridwhack.entity.tile;

import gridwhack.fov.IViewer;
import gridwhack.grid.Grid;
import gridwhack.path.IMover;

public class WallTile extends Tile 
{
	public WallTile(Grid grid) 
	{
		super("walltile.png", grid);
	}
	
	public boolean isBlocked(IMover mover)
	{
		return true;
	}
	
	public boolean isSolid(IViewer viewer)
	{
		return true;
	}
}