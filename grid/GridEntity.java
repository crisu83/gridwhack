package gridwhack.grid;

import gridwhack.entity.CEntity;

/**
 * Grid entity class file.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public abstract class GridEntity extends CEntity
{	
	protected Grid grid;
	
	/**
	 * Constructs the entity.
	 * @param filename the sprite filename.
	 * @param grid the grid the entity exists on.
	 */
	public GridEntity(String filename, Grid grid)
	{
		super(filename);
		this.grid = grid;
	}

	/**
	 * Returns the cell this player stands in.
	 * @return the cell.
	 */
	public GridCell getCell()
	{
		return grid.getCell(grid.getOffsetInCells(x), grid.getOffsetInCells(y));
	}

	/**
	 * @param x the x-coordinate in grid cells.
	 */
	public void setGridX(int x)
	{
		this.x = grid.getOffsetInPixels(x);
	}

	/**
	 * @return the x-coordinate in grid cells.
	 */
	public int getGridX()
	{
		return grid.getOffsetInCells(x);
	}

	/**
	 * @param y the y-coordinate in grid cells.
	 */
	public void setGridY(int y)
	{
		this.y = grid.getOffsetInPixels(y);
	}

	/**
	 * @return the y-coordinate in grid cells.
	 */
	public int getGridY()
	{
		return grid.getOffsetInCells(y);
	}

	/**
	 * Sets the destination for this entity.
	 * @param tgx the target x-coordinates in grid cells.
	 * @param tgy the target y-coordinates in grid cells.
	 */
	public void setDestination(int tgx, int tgy)
	{
		super.setDestination(grid.getOffsetInCells(tgx), grid.getOffsetInCells(tgy));
	}
}
