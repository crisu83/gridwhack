package gridwhack.gameobject.map.dungeon;

import gridwhack.gameobject.map.dungeon.DungeonFeature.DungeonFeatureType;
import gridwhack.gameobject.exception.InvalidGameObjectException;

/**
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class DungeonFeatureFactory
{
	// ----------
	// Properties
	// ----------

	private static final DungeonFeatureFactory instance = new DungeonFeatureFactory();

	// -------
	// Methods
	// -------

	/**
	 * Creates the object.
	 * Private to enforce the singleton pattern.
	 */
	private DungeonFeatureFactory() {}

	/**
	 * Returns the single instance of this object.
	 * @return The instance.
	 */
	public static DungeonFeatureFactory getInstance()
	{
		return instance;
	}

	public DungeonFeature spawn(DungeonFeatureType type, int x, int y, int width, int height)
			throws InvalidGameObjectException
	{
		DungeonFeature object = null;

		switch (type)
		{
			case CORRIDOR:
				// TODO: Implement
				break;

			case ROOM:
				object = new DungeonRoom(x, y, width, height);
				break;

			default:
				throw new InvalidGameObjectException("Invalid game object type.");
		}

		object.init();

		return object;
	}
}
