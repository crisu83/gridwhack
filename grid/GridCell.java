package gridwhack.grid;

import gridwhack.entity.item.Item;
import gridwhack.fov.IViewer;
import gridwhack.path.IMover;

import java.util.ArrayList;

/**
 * Grid cell class file.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class GridCell
{
	protected int gx;
	protected int gy;
	protected GridTile tile;
	protected GridLoot loot;
	protected GridUnit unit;

	/**
	 * Creates the cell.
	 * @param gx the grid x-coordinate.
	 * @param gy the grid y-coordinate.
	 */
	public GridCell(int gx, int gy)
	{
		this.gx = gx;
		this.gy = gy;
	}

	/**
	 * @param tile the tile in the cell.
	 */
	public void setTile(GridTile tile)
	{
		// set the position of the tile.
		tile.setGridX(gx);
		tile.setGridY(gy);

		this.tile = tile;
	}

	/**
	 * Adds loot to the cell.
	 * @param loot the loot to add.
	 */
	public void addLoot(GridLoot loot)
	{
		// check if there is already loot in the cell,
		// if so we need to add the items in the new loot
		// to the exisiting loot.
		if( this.loot!=null )
		{
			ArrayList<Item> items = loot.getItems();

			// make sure the loot has items.
			if( !items.isEmpty() )
			{
				for( Item item : items )
				{
					this.loot.addItem(item);
				}
			}
		}
		// no loot in the cell.
		else
		{
			// move the loot to the cell.
			loot.setGridX(gx);
			loot.setGridY(gy);

			this.loot = loot;
		}
	}

	/**
	 * Returns the loot in this cell.
	 * @return the loot.
	 */
	public GridLoot getLoot()
	{
		return loot;
	}

	/**
	 * @param unit the unit to add.
	 */
	public void setUnit(GridUnit unit)
	{
		// move the unit to the cell.
		unit.setGridX(gx);
		unit.setGridY(gy);

		this.unit = unit;
	}

	/**
	 * Returns the unit in this cell.
	 * @return the unit.
	 */
	public GridUnit getUnit()
	{
		return unit;
	}

	/**
	 * Removes the unit from the cell.
	 */
	public void removeUnit()
	{
		unit = null;
	}

	/**
	 * Returns whether a specific entity can walk on this cell.
	 * @param mover the entity.
	 * @return whether this cell is blocked.
	 */
	public boolean isBlocked(IMover mover)
	{
		if( tile!=null )
		{
			return tile.isBlocked(mover);
		}

		// cell is blocked by default.
		return true;
	}

	/**
	 * Returns whether a specific entity can see through this cell.
	 * @param viewer the entity.
	 * @return whether this cell is solid.
	 */
	public boolean isSolid(IViewer viewer)
	{
		if( tile!=null )
		{
			return tile.isSolid(viewer);
		}

		// cells are solid by default.
		return true;
	}

	/**
	 * Returns the grid x-coordinate for this cell.
	 * @return the x-coordinate.
	 */
	public int getGridX()
	{
		return gx;
	}

	/**
	 * Returns the grid y-coordinate for this cell.
	 * @return the y-coordinate.
	 */
	public int getGridY()
	{
		return gy;
	}
}
