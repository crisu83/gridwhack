package gridwhack.gameobject.grid;

import gridwhack.gameobject.sprite.Sprite;
import gridwhack.util.Vector2;

/**
 * Grid game object class file.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 * @license New BSD License http://www.opensource.org/licenses/bsd-license.php
 */
public abstract class GridGameObject extends Sprite
{
	// ----------
	// Properties
	// ----------

	protected Grid grid;

	// -------
	// Methods
	// -------	

	/**
	 * Creates the object.
	 */
	public GridGameObject()
	{
		super();
	}

	/**
	 * @return The current x-coordinate on the grid.
	 */
	public int getGridX()
	{
		Vector2 gridPosition = getGridPosition();
		return (int) gridPosition.x;
	}

	/**
	 * @return The current y-coordinate on the grid.
	 */
	public int getGridY()
	{
		Vector2 gridPosition = getGridPosition();
		return (int) gridPosition.y;
	}

	// -------------------
	// Getters and setters
	// -------------------

	/**
	 * Returns the position of this game object on the grid.
	 * @return The current position on the grid.
	 */
	public Vector2 getGridPosition()
	{
		Vector2 currentPosition = position.copy();
		currentPosition.divide(32);
		return currentPosition;
	}

	/**
	 * Sets the position of this game object on the grid.
	 * @param position The new position on the grid.
	 */
	public void setGridPosition(Vector2 position)
	{
		Vector2 newPosition = position.copy();
		newPosition.multiply(32);
		setPosition(newPosition);
	}

	/**
	 * @param grid The grid this game object belongs to.
	 */
	public void setGrid(Grid grid)
	{
		this.grid = grid;
	}

	/**
	 * @return The grid this game object belongs to.
	 */
	public Grid getGrid()
	{
		return grid;
	}
}
