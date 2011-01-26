package gridwhack.grid;

import gridwhack.entity.CEntity;

/**
 * Grid entity base class.
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
	 * @return the grid the entity belongs to.
	 */
	public Grid getGrid()
	{
		return grid;
	}
	
	/**
	 * @return the x-coordinate in grid cells.
	 */
	public int getGridX()
	{
		return grid.getOffsetInCells(x);
	}
	
	/**
	 * @param x the x-coordinate in grid cells.
	 */
	public void setGridX(int x)
	{
		this.x = grid.getOffsetInPixels(x);
	}
	
	/**
	 * @return the y-coordinate in grid cells.
	 */
	public int getGridY()
	{
		return grid.getOffsetInCells(y);
	}
	
	/**
	 * @param y the y-coordinate in grid cells.
	 */
	public void setGridY(int y)
	{
		this.y = grid.getOffsetInPixels(y);
	}
	
	public void setDestination(int tgx, int tgy)
	{
		super.setDestination(tgx*grid.getCellSize(), tgy*grid.getCellSize());
	}
}
