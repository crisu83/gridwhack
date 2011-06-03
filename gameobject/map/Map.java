package gridwhack.gameobject.map;

import java.awt.Graphics2D;

import gridwhack.base.BaseObject;
import gridwhack.gameobject.DrawableGameObject;
import gridwhack.gameobject.grid.Grid;
import gridwhack.util.Vector2;

/**
 * Map class.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public abstract class Map extends DrawableGameObject
{
	public static enum MapType
	{
		DUNGEON,
	}

	// ----------
	// Properties
	// ----------

	protected Grid grid;

	// -------
	// Methods
	// -------

	/**
	 * Creates the map.
	 * @param width The map width.
	 * @param height The map height.
	 */
	public Map(int width, int height)
	{
		super();

		grid = new Grid(width, height);

		setDimension(new Vector2(width, height));
	}
	
	// ------------------
	// Overridden methods
	// ------------------

	/**
	 * Updates this object.
	 * @param parent The parent object.
	 */
	@Override
	public void update(BaseObject parent)
	{
		grid.update(this);
	}

	/**
	 * Draws this object.
	 * @param g The graphics context.
	 */
	@Override
	public void draw(Graphics2D g)
	{
		grid.draw(g);
	}

	// -------------------
	// Getters and setters
	// -------------------

	/**
	 * @return The grid for this map.
	 */
	public Grid getGrid()
	{
		return grid;
	}
}