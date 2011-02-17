package gridwhack.entity.item;

import java.util.ArrayList;

import gridwhack.grid.Grid;
import gridwhack.grid.GridEntity;

public class GridLoot extends GridEntity
{
	protected ArrayList<Item> items;
	
	public GridLoot(Grid grid)
	{
		super("loot.png", grid);
		
		items = new ArrayList<Item>();
	}
	
	public synchronized void addItem(Item item)
	{
		items.add(item);
	}
	
	public synchronized void removeItem(Item item)
	{
		if( items.contains(item) )
		{
			items.remove(item);
		}
	}
	
	public ArrayList<Item> getItems()
	{
		return items;
	}
}
