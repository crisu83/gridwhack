package gridwhack.map;

import java.awt.Graphics2D;
import java.util.ArrayList;

import gridwhack.grid.Grid;

public class RandomDungeonMap extends GridMap 
{
	protected RandomDungeon dungeon;
	
	public RandomDungeonMap() 
	{
		super();
		
		grid = new Grid(50, 30);
		
		init();
	}
	
	public void init()
	{
		int width = grid.getGridWidth();
		int height = grid.getGridHeight();
		
		dungeon = new RandomDungeon(width, height, grid);
	}
	
	public void render(Graphics2D g)
	{
		super.render(g);

		dungeon.render(g);
	}
}
