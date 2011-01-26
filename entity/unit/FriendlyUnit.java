package gridwhack.entity.unit;

import gridwhack.entity.unit.hostile.HostileUnit;
import gridwhack.grid.Grid;

public class FriendlyUnit extends NonPlayerUnit {

	/**
	 * Constructs the friendly unit.
	 * @param filename the sprite filename.
	 * @param grid the grid the hostile exists on.
	 */
	public FriendlyUnit(String filename, Grid grid) 
	{
		super(filename, grid);
	}
	
	/**
	 * Returns whether the target unit is hostile.
	 * @param target the target unit.
	 */
	public boolean isHostile(Unit target) 
	{
		if( target instanceof HostileUnit )
		{
			return true;
		}
		else
		{
			return false;
		}
	}

}
