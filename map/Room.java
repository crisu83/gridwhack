package gridwhack.map;

import gridwhack.grid.Grid;
import gridwhack.grid.GridTile;

public class Room 
{
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	protected Grid grid;
	
	public Room(int x, int y, int width, int height, Grid grid)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.grid = grid;
		
		init();
	}
	
	public void init()
	{
		grid.createTileRect(x, y, width, height, GridTile.Type.FLOOR);
	}
	
	public Room compareClosest(Room r1, Room r2)
	{
		int dx1 = Math.abs(r1.x - x);
		int dy1 = Math.abs(r1.y - y);
		
		int cost1 = dx1 + dy1;
		
		int dx2 = Math.abs(r2.x - x);
		int dy2 = Math.abs(r2.y - y);
		
		int cost2 = dx2 + dy2;
		
		return cost1<cost2 ? r1 : r2;
	}
}
