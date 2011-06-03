package gridwhack.gameobject.map;

import java.awt.Graphics2D;

public class RandomDungeonMap extends Map
{
	protected RandomDungeon dungeon;
	
	public RandomDungeonMap(int width, int height)
	{
		super(width, height);
	}
	
	public void init()
	{
		int width = grid.getWidthInCells();
		int height = grid.getHeightInCells();
		
		dungeon = new RandomDungeon(width, height, grid);
	}
	
	public void draw(Graphics2D g)
	{
		super.draw(g);

		dungeon.render(g);
	}
}
