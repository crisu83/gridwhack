package gridwhack.entity.tile;

import gridwhack.fov.IViewer;
import gridwhack.grid.Grid;
import gridwhack.path.IMover;

public class FloorTile extends Tile 
{
	public FloorTile(Grid grid) 
	{
		super("floortile.png", grid);
	}
	
	public boolean isBlocked(IMover mover)
	{
		return false;
	}
	
	public boolean isSolid(IViewer viewer)
	{
		return false;
	}
}