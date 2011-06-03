package gridwhack.gameobject.loot;

import java.util.Random;

import gridwhack.RandomProvider;
import gridwhack.base.BaseObject;
import gridwhack.base.BaseCollection;
import gridwhack.core.ImageLoader;
import gridwhack.gameobject.exception.InvalidGameObjectException;
import gridwhack.gameobject.grid.Grid;
import gridwhack.gameobject.grid.GridGameObject;
import gridwhack.gameobject.item.Item;
import gridwhack.gameobject.item.Item.ItemType;
import gridwhack.gameobject.item.ItemFactory;
import gridwhack.sprite.SpriteManager;
import gridwhack.util.SortedArrayList;

/**
 * Loot class.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class Loot extends GridGameObject
{
	protected BaseCollection items;

	/**
	 * Creates the object.
	 */
	public Loot()
	{
		super();

		setImage(ImageLoader.getInstance().loadImage("Chest1.png"));
		
		items = new BaseCollection();
	}

	/**
	 * Adds an item to this loot.
	 * @param item the item.
	 */
	public void addItem(Item item)
	{
		items.add(item);
	}

	/**
	 * Removes an item from this loot.
	 * @param item the item.
	 */
	public void removeItem(Item item)
	{
		items.remove(item);
	}

	/**
	 * Returns all the items in this loot.
	 * @return the items.
	 */
	public SortedArrayList<BaseObject> getItems()
	{
		return items.getObjects();
	}

	/**
	 * Returns the item count for this loot.
	 * @return the count.
	 */
	public int getItemCount()
	{
		return items.getSize();
	}

	public void createRandomItems()
	{
		ItemFactory itemFactory = ItemFactory.getInstance();
		Random rand = new Random();
		int itemCount = rand.nextInt(2); // 0-1

		Item item = null;

		for (int i = 0; i < itemCount; i++)
		{
			try
			{
				int n = rand.nextInt(ItemType.values().length);
				item = (Item) itemFactory.spawn(ItemType.values()[n], 0, 0);
			}
			catch (InvalidGameObjectException e)
			{
				System.out.println(e.getMessage());
				e.printStackTrace();
				System.exit(1);
			}

			addItem(item);
		}

		items.applyChanges();
	}

	@Override
	public void update(BaseObject parent)
	{
		items.update(this);
	}
}
