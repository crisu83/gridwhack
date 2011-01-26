package gridwhack.entity.unit.hostile;

import gridwhack.entity.unit.FriendlyUnit;
import gridwhack.entity.unit.NonPlayerUnit;
import gridwhack.entity.unit.Player;
import gridwhack.entity.unit.Unit;
import gridwhack.grid.Grid;

public abstract class HostileUnit extends NonPlayerUnit 
{	
	/**
	 * Constructs the hostile unit.
	 * @param filename the sprite filename.
	 * @param grid the grid the hostile exists on.
	 */
	public HostileUnit(String filename, Grid grid) 
	{
		super(filename, grid);
	}
	
	/**
	 * Returns whether the target unit is hostile.
	 * @param target the target unit.
	 */
	public boolean isHostile(Unit target)
	{
		// players and friendly units are hostile towards hostile units.
		if( target instanceof Player || target instanceof FriendlyUnit )
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}