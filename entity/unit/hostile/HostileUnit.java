package gridwhack.entity.unit.hostile;

import gridwhack.entity.unit.FriendlyUnit;
import gridwhack.entity.unit.NPCUnit;
import gridwhack.entity.unit.Player;
import gridwhack.grid.Grid;
import gridwhack.grid.GridUnit;

/**
 * Hostile unit class file.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public abstract class HostileUnit extends NPCUnit
{	
	/**
	 * Creates the unit.
	 * @param filename the image filename.
	 * @param grid the grid the hostile belongs to.
	 */
	public HostileUnit(String filename, Grid grid) 
	{
		super(filename, grid);
	}
	
	/**
	 * Returns whether the target unit is hostile.
	 * @param target the target unit.
	 * @return whether the target is hostile.
	 */
	public boolean isHostile(GridUnit target)
	{
		// players and friendly units are hostile towards hostile units.
		return target instanceof Player || target instanceof FriendlyUnit;
	}
}