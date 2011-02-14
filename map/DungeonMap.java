package gridwhack.map;

import gridwhack.entity.tile.TileFactory;
import gridwhack.entity.unit.UnitFactory;
import gridwhack.grid.Grid;
import gridwhack.grid.GridCell;

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
		grid.createTileRect(0, 0, 60, 30, TileFactory.TileType.FLOOR);

		for(int i=0, wallCount=100; i<wallCount; i++)
		{
			GridCell cell = grid.getRandomCell();
			int gx = cell.getGridX();
			int gy = cell.getGridY();

			grid.createTileRect(gx, gy, 1, 1, TileFactory.TileType.WALL);
		}
		
		// create some hostiles.
		grid.createUnit(UnitFactory.UnitType.ORC);
		grid.createUnit(UnitFactory.UnitType.ORC);
		grid.createUnit(UnitFactory.UnitType.ORC);
		grid.createUnit(UnitFactory.UnitType.ORC);
		grid.createUnit(UnitFactory.UnitType.ORC);
		grid.createUnit(UnitFactory.UnitType.ORC);
		grid.createUnit(UnitFactory.UnitType.ORC);
		grid.createUnit(UnitFactory.UnitType.ORC);
		grid.createUnit(UnitFactory.UnitType.KOBOLD);
		grid.createUnit(UnitFactory.UnitType.KOBOLD);
		grid.createUnit(UnitFactory.UnitType.KOBOLD);
		grid.createUnit(UnitFactory.UnitType.KOBOLD);
		grid.createUnit(UnitFactory.UnitType.KOBOLD);
		grid.createUnit(UnitFactory.UnitType.KOBOLD);
		grid.createUnit(UnitFactory.UnitType.KOBOLD);
		grid.createUnit(UnitFactory.UnitType.KOBOLD);
		grid.createUnit(UnitFactory.UnitType.SKELETON);
		grid.createUnit(UnitFactory.UnitType.SKELETON);
		grid.createUnit(UnitFactory.UnitType.SKELETON);
		grid.createUnit(UnitFactory.UnitType.SKELETON);
		grid.createUnit(UnitFactory.UnitType.SKELETON);
		grid.createUnit(UnitFactory.UnitType.SKELETON);
		grid.createUnit(UnitFactory.UnitType.SKELETON);
		grid.createUnit(UnitFactory.UnitType.SKELETON);
	}
}
