package gridwhack.map;

import gridwhack.entity.tile.TileFactory;
import gridwhack.entity.unit.UnitFactory;
import gridwhack.grid.Grid;

public class DungeonMap extends GridMap 
{
	public DungeonMap() 
	{
		super();
		
		grid = new Grid(60, 30);
		
		init();
	}
	
	public void init()
	{
		// create some terrain.
		grid.createTileRect(0, 0, 120, 60, TileFactory.TileType.FLOOR);
		
		// create some hostiles.
		grid.createUnit(UnitFactory.UnitType.ORC);
		grid.createUnit(UnitFactory.UnitType.ORC);
		grid.createUnit(UnitFactory.UnitType.ORC);
		grid.createUnit(UnitFactory.UnitType.ORC);
		grid.createUnit(UnitFactory.UnitType.KOBOLD);
		grid.createUnit(UnitFactory.UnitType.KOBOLD);
		grid.createUnit(UnitFactory.UnitType.KOBOLD);
		grid.createUnit(UnitFactory.UnitType.KOBOLD);
		grid.createUnit(UnitFactory.UnitType.SKELETON);
		grid.createUnit(UnitFactory.UnitType.SKELETON);
		grid.createUnit(UnitFactory.UnitType.SKELETON);
		grid.createUnit(UnitFactory.UnitType.SKELETON);
	}
}
