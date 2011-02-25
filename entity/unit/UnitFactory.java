package gridwhack.entity.unit;

import gridwhack.entity.unit.hostile.Kobold;
import gridwhack.entity.unit.hostile.Orc;
import gridwhack.entity.unit.hostile.Skeleton;
import gridwhack.entity.unit.player.Player;
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
	public static GridUnit factory(GridUnit.Type type, Grid grid) throws Exception
	{
		GridUnit unit = null;

		// return the requested type of unit.
		switch( type )
		{
			case PLAYER:
				unit = new Player(grid);
				break;

			case ORC:
				unit = new Orc(grid);
				break;

			case KOBOLD:
				unit = new Kobold(grid);
				break;

			case SKELETON:
				unit = new Skeleton(grid);
				break;

			default:
				throw new Exception("Failed to create unit, type '" + type + "' is invalid!");
		}

		// Initialize the unit.
		unit.init();

		return unit;
	}
}
