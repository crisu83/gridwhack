package gridwhack.entity.unit;

import gridwhack.entity.unit.hostile.HostileUnit;
import gridwhack.grid.Grid;
import gridwhack.grid.GridUnit;

public class FriendlyUnit extends NPCUnit
{
	/**
	 * Creates the unit.
	 * @param filename the image filename.
	 * @param grid the grid the hostile belongs to.
	 */
	public FriendlyUnit(String filename, Grid grid) 
	{
		super(filename, grid);
	}
	
	/**
	 * Returns whether the target unit is hostile.
	 * @param target the target unit.
	 */
	public boolean isHostile(GridUnit target)
	{
		return target instanceof HostileUnit;
	}

}
