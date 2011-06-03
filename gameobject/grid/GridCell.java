package gridwhack.gameobject.grid;

import gridwhack.base.BaseObject;
import gridwhack.gameobject.item.Item;
import gridwhack.fov.IViewer;
import gridwhack.gameobject.loot.Loot;
import gridwhack.gameobject.tile.Tile;
import gridwhack.gameobject.unit.Unit;
import gridwhack.path.IMover;
import gridwhack.util.SortedArrayList;
import gridwhack.util.Vector2;

/**
 * Grid cell class.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class GridCell
{
	protected Vector2 position;
	protected Tile tile;
	protected Loot loot;
	protected Unit unit;

	/**
	 * Creates the cell.
	 * @param gx The grid x-coordinate of the cell.
	 * @param gy The grid y-coordinate of the cell.
	 */
	public GridCell(int gx, int gy)
	{
		position = new Vector2(gx, gy);
	}

	/**
	 * Adds loot to this cell.
	 * @param loot The loot to add.
	 */
	public void addLoot(Loot loot)
	{
		// Check if there is already loot in the cell, if so we add the new items to the exisiting loot.
		if (this.loot != null)
		{
			SortedArrayList<BaseObject> items = loot.getItems();

			// make sure the loot has items.
			if (!items.isEmpty())
			{
				final int itemCount = items.size();

				for (int i = 0; i < itemCount; i++)
				{
					this.loot.addItem((Item) items.get(i));
				}
			}
		}
		// No loot in the cell.
		else
		{
			loot.setGridPosition(position);

			this.loot = loot;
		}
	}

	// -------------------
	// Getters and setters
	// -------------------

	/**
	 * @return The tile in this cell.
	 */
	public Tile getTile()
	{
		return tile;
	}

	/**
	 * @param tile The tile to place in this cell.
	 */
	public void setTile(Tile tile)
	{
		if (tile != null)
		{
			tile.setGridPosition(position);
		}

		this.tile = tile;
	}

	/**
	 * @return The loot in this cell.
	 */
	public Loot getLoot()
	{
		return loot;
	}

	/**
	 * @param unit The unit to place in this cell.
	 */
	public void setUnit(Unit unit)
	{
		if (unit != null)
		{
			unit.setGridPosition(position);
		}

		this.unit = unit;
	}

	/**
	 * Returns the unit in this cell.
	 * @return the unit.
	 */
	public Unit getUnit()
	{
		return unit;
	}

	/**
	 * Returns whether a specific mover can walk on this cell.
	 * @param mover the game object.
	 * @return whether this cell is blocked.
	 */
	public boolean isBlocked(IMover mover)
	{
		if (tile != null)
		{
			return tile.isBlocked(mover);
		}

		return true; // cells is blocked by default
	}

	/**
	 * Returns whether a specific viewer can see through this cell.
	 * @param viewer the game object.
	 * @return whether this cell is solid.
	 */
	public boolean isSolid(IViewer viewer)
	{
		if (tile != null)
		{
			return tile.isSolid(viewer);
		}

		return true; // cells are solid by default
	}

	/**
	 * @return The position of this cell on the grid..
	 */
	public Vector2 getPosition()
	{
		return position;
	}
}
