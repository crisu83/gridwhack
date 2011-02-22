package gridwhack.map;

import java.awt.Graphics2D;
import java.util.Random;

import gridwhack.RandomProvider;
import gridwhack.grid.Grid;

public class GridMap
{	
	protected Grid grid;
	protected Random rand;
	
	public GridMap()
	{
		rand = RandomProvider.getRand();
	}
	
	public Grid getGrid()
	{
		return grid;
	}
	
	public int getWidth()
	{
		return grid.getWidth();
	}
	
	public int getHeight()
	{
		return grid.getHeight();
	}
	
	public void update(long timePassed)
	{
		grid.update(timePassed);
	}
	
	public void render(Graphics2D g)
	{
		grid.render(g);
	}
}