package gridwhack.gameobject.item;

import gridwhack.gameobject.IGameObjectFactory;
import gridwhack.gameobject.item.Item.ItemType;
import gridwhack.gameobject.GameObject;
import gridwhack.gameobject.IGameObjectType;
import gridwhack.gameobject.exception.InvalidGameObjectException;

/**
 * Item factory class.
 * Provides the functionality for spawning items into the game.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 * @license New BSD License http://www.opensource.org/licenses/bsd-license.php
 */
public class ItemFactory implements IGameObjectFactory
{
	// ----------
	// Properties
	// ----------

	private static final ItemFactory instance = new ItemFactory();

	// -------
	// Methods
	// -------

	/**
	 * Creates the factory.
	 * Private to enforce the singleton pattern.
	 */
	private ItemFactory() {}

	/**
	 * Returns the single instance of this object.
	 * @return The instance.
	 */
	public static ItemFactory getInstance()
	{
		return instance;
	}

	/**
	 * Spawns a specific game object.
	 * @param type The game object type.
	 * @param x The x-coordinate to spawn the game object to.
	 * @param y The y-coordinate to spawn the game object to.
	 * @return The game object.
	 * @throws InvalidGameObjectException If the game object could not be created.
	 */
	public GameObject spawn(IGameObjectType type, int x, int y) throws InvalidGameObjectException
	{
		GameObject object;

		switch ((ItemType) type)
		{
			case DAMAGE_ORB:
				object = new DamageOrb();
				break;

			case HEALTH_ORB:
				object = new HealthOrb();
				break;

			default:
				throw new InvalidGameObjectException("Invalid game object type.");
		}

		object.init();

		return object;
	}
}
