package gridwhack.entity.unit;

import gridwhack.entity.unit.hostile.Kobold;
import gridwhack.entity.unit.hostile.Orc;
import gridwhack.entity.unit.hostile.Skeleton;
import gridwhack.grid.Grid;

public class UnitFactory 
{
	public static enum UnitType {
		ORC,
		KOBOLD,
		SKELETON
	}
	
	public static Unit factory(UnitType type, Grid grid)
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
