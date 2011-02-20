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
	public static Item factory(Item.Type type)
	{
		// return the requested item.
		switch( type )
		{
			case HEALTH_ORB:
				return new HealthOrb();
			default:
				return null;
		}
	}
}
