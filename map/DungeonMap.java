package gridwhack.map;

import gridwhack.entity.character.Character;
import gridwhack.grid.Grid;
import gridwhack.grid.GridCell;
import gridwhack.grid.GridTile;

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
		grid.createTileRect(0, 0, 60, 30, GridTile.Type.FLOOR);

		for(int i=0, wallCount=100; i<wallCount; i++)
		{
			GridCell cell = grid.getRandomCell();
			int gx = cell.getGridX();
			int gy = cell.getGridY();

			grid.createTileRect(gx, gy, 1, 1, GridTile.Type.WALL);
		}
		
		// create some hostiles.
		grid.createUnit(Character.Type.ORC);
		grid.createUnit(Character.Type.ORC);
		grid.createUnit(Character.Type.ORC);
		grid.createUnit(Character.Type.ORC);
		grid.createUnit(Character.Type.ORC);
		grid.createUnit(Character.Type.ORC);
		grid.createUnit(Character.Type.ORC);
		grid.createUnit(Character.Type.ORC);
		grid.createUnit(Character.Type.KOBOLD);
		grid.createUnit(Character.Type.KOBOLD);
		grid.createUnit(Character.Type.KOBOLD);
		grid.createUnit(Character.Type.KOBOLD);
		grid.createUnit(Character.Type.KOBOLD);
		grid.createUnit(Character.Type.KOBOLD);
		grid.createUnit(Character.Type.KOBOLD);
		grid.createUnit(Character.Type.KOBOLD);
		grid.createUnit(Character.Type.SKELETON);
		grid.createUnit(Character.Type.SKELETON);
		grid.createUnit(Character.Type.SKELETON);
		grid.createUnit(Character.Type.SKELETON);
		grid.createUnit(Character.Type.SKELETON);
		grid.createUnit(Character.Type.SKELETON);
		grid.createUnit(Character.Type.SKELETON);
		grid.createUnit(Character.Type.SKELETON);
	}
}
