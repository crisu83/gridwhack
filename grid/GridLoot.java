package gridwhack.grid;

import java.util.ArrayList;
import java.util.Random;

import gridwhack.entity.item.Item;
import gridwhack.entity.item.ItemFactory;

/**
 * Grid loot class file.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class GridLoot extends GridEntity
{
	protected ArrayList<Item> items;

	/**
	 * Creates the loot.
	 * @param grid the grid the loot exists on.
	 */
	public GridLoot(Grid grid)
	{
		super("chest.png", grid);
		
		items = new ArrayList<Item>();
	}

	/**
	 * Adds an item to this loot.
	 * @param item the item.
	 */
	public synchronized void addItem(Item item)
	{
		items.add(item);
	}

	/**
	 * Removes an item from this loot.
	 * @param item the item.
	 */
	public synchronized void removeItem(Item item)
	{
		if( items.contains(item) )
		{
			items.remove(item);
		}
	}

	/**
	 * @return whether this loot has items.
	 */
	public boolean hasItems()
	{
		return !items.isEmpty();
	}

	/**
	 * Returns all the items in this loot.
	 * @return the items.
	 */
	public ArrayList<Item> getItems()
	{
		return items;
	}

	/**
	 * Returns the item count for this loot.
	 * @return the count.
	 */
	public int getItemCount()
	{
		return items.size();
	}

	public void createRandomItems()
	{
		Random random = new Random();
		int itemCount = random.nextInt(2); // 0-1

		Item item = null;

		for( int i=0; i<itemCount; i++ )
		{
			try
			{
				int n = rand.nextInt(Item.Type.values().length);
				item = ItemFactory.factory( Item.Type.values()[n] );
			}
			catch( Exception e )
			{
				System.out.println(e.getMessage());
				System.exit(1);
			}

			addItem(item);
		}
	}
}
