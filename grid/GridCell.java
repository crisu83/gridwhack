package gridwhack.grid;

import gridwhack.entity.item.Item;
import gridwhack.entity.item.Loot;
import gridwhack.entity.tile.Tile;
import gridwhack.entity.unit.Player;
import gridwhack.fov.IViewer;
import gridwhack.path.IMover;

import java.util.ArrayList;

public class GridCell
{
	protected int gx;
	protected int gy;
	protected Tile tile;
	protected Loot loot;
	protected GridUnit unit;

	/**
	 * Constructs the grid cell.
	 * @param gx the x-coordinate on the grid.
	 * @param gy the y-coordinate on the grid.
	 */
	public GridCell(int gx, int gy)
	{
		this.gx = gx;
		this.gy = gy;
	}

	/**
	 * @param tile the tile in the cell.
	 */
	public void setTile(Tile tile)
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
	public void addLoot(Loot loot)
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
	 * @param unit the unit to add.
	 */
	public void addUnit(GridUnit unit)
	{
		// move the unit to the cell.
		unit.setGridX(gx);
		unit.setGridY(gy);

		this.unit = unit;
	}

	/**
	 * Removes the unit from the cell.
	 */
	public void removeUnit()
	{
		unit = null;
	}

	public void loot(Player player)
	{
		if( loot!=null )
		{
			player.pickUp(loot);
		}
	}

	/**
	 * @param mover the moving entity.
	 * @return whether the cell is blocked.
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
	 * @param viewer the viewing entity.
	 * @return whether the cell can be seen through.
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
	 * @return the cell x-coordinate on the grid.
	 */
	public int getGridX()
	{
		return gx;
	}

	/**
	 * @return the cell y-coordinate on the grid.
	 */
	public int getGridY()
	{
		return gy;
	}

	/**
	 * @return the loot.
	 */
	public Loot getLoot()
	{
		return loot;
	}

	/**
	 * @return all units in the cell.
	 */
	public GridUnit getUnit()
	{
		return unit;
	}
}
