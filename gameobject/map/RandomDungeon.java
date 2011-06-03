package gridwhack.gameobject.map;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

import gridwhack.RandomProvider;
import gridwhack.gameobject.grid.Grid;

public class RandomDungeon 
{
	protected DungeonSection dungeon;
	protected Random rand;
	
	public RandomDungeon(int width, int height, Grid grid)
	{
		rand = RandomProvider.getRand();
		
		dungeon = new DungeonSection(width, height, rand, grid);
		
		addRooms();
		
		addCorridors();
	}
	
	protected void addRooms()
	{
		dungeon.addRooms();
	}
	
	protected void addCorridors()
	{
		dungeon.addCorridors();
	}
	
	public void render(Graphics2D g)
	{
		ArrayList<Color> colors = new ArrayList<Color>();
		
		colors.add(Color.red);
		colors.add(Color.green);
		colors.add(Color.blue);
		colors.add(Color.pink);
		colors.add(Color.cyan);		
		
		dungeon.render(g, colors);
	}
}
