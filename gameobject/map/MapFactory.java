package gridwhack.gameobject.map;

import gridwhack.base.BaseObject;
import gridwhack.gameobject.exception.InvalidGameObjectException;
import gridwhack.gameobject.map.Map.MapType;
import gridwhack.gameobject.map.dungeon.DungeonMap;

/**
 * Map factory class.
 * Provides functionality for creating maps.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class MapFactory extends BaseObject
{
	// ----------
	// Properties
	// ----------

	private static final MapFactory instance = new MapFactory();

	// -------
	// Methods
	// -------

	/**
	 * Creates the factory.
	 * Private to enforce the singleton pattern.
	 */
	private MapFactory() {}

	/**
	 * Returns the single instance of this object.
	 * @return The instance.
	 */
	public static MapFactory getInstance()
	{
		return instance;
	}


	/**
	 * Creates a specific map.
	 * @param type The map type.
	 * @param width The map width in grid cells.
	 * @param height The map width in grid cells.
	 * @return The map.
	 * @throws InvalidGameObjectException If the map could not be created.
	 */
	public Map create(MapType type, int width, int height) throws InvalidGameObjectException
	{
		Map object;

		switch (type)
		{
			case DUNGEON:
				object = new DungeonMap(width, height);
				break;

			default:
				throw new InvalidGameObjectException("Invalid map type.");
		}

		object.init();

		return object;		
	}
}
