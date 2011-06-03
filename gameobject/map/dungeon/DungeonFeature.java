package gridwhack.gameobject.map.dungeon;

import gridwhack.base.BaseCollection;
import gridwhack.gameobject.GameObject;

/**
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public abstract class DungeonFeature extends GameObject
{
	public static enum DungeonFeatureType
	{
		CORRIDOR,
		ROOM,
	};

	// ----------
	// Properties
	// ----------

	protected BaseCollection tiles;

	// -------
	// Methods
	// -------

	/**
	 * Creates the feature.
	 */
	public DungeonFeature()
	{
		super();

		this.tiles = new BaseCollection();
	}

	// -------------------
	// Getters and setters
	// -------------------

	/**
	 * @return The tiles for this feature.
	 */
	public BaseCollection getTiles()
	{
		return tiles;
	}
}
