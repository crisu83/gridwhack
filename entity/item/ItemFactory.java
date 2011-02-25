package gridwhack.entity.item;

/**
 * Item factory class file.
 * Allows for creating items.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class ItemFactory
{
	/**
	 * Returns new items of the given type.
	 * @param type the item type.
	 * @return the tile.
	 */
	public static Item factory(Item.Type type) throws Exception
	{
		Item item;

		// return the requested item.
		switch( type )
		{
			case HEALTH_ORB:
				item = new HealthOrb();
				break;

			default:
				throw new Exception("Failed to create item, type '" + type + "' is invalid!");
		}

		// Initialze the item.
		item.init();

		return item;
	}
}
