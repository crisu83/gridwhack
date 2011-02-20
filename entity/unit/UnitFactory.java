package gridwhack.entity.unit;

import gridwhack.entity.unit.hostile.Kobold;
import gridwhack.entity.unit.hostile.Orc;
import gridwhack.entity.unit.hostile.Skeleton;
import gridwhack.grid.Grid;
import gridwhack.grid.GridUnit;

/**
 * Unit factory class file.
 * Allows for creating units.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class UnitFactory 
{
	/**
	 * Returns new units of the given type.
	 * @param type the unit type.
	 * @param grid the grid the unit belongs to.
	 * @return the unit.
	 */
	public static GridUnit factory(GridUnit.Type type, Grid grid)
	{
		// return the requested type of unit.
		switch( type )
		{
		case ORC:
			return new Orc(grid);
		case KOBOLD:
			return new Kobold(grid);
		case SKELETON:
			return new Skeleton(grid);
		default:
			return null;
		}
	}
}
