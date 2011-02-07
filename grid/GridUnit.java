package gridwhack.grid;

import java.awt.Graphics2D;

import gridwhack.GridWhack;
import gridwhack.entity.unit.Unit;
import gridwhack.fov.IViewer;
import gridwhack.grid.Grid.GridCell;
import gridwhack.path.IMover;

/**
 * Grid unit base class.
 */
public abstract class GridUnit extends Unit implements IMover, IViewer
{
	protected int viewRange;
	protected GridFov fov;
	protected GridPath path;
	
	/**
	 * Constructs the unit. 
	 * @param filename the sprite filename.
	 * @param grid the grid the unit exists on.
	 */
	public GridUnit(String filename, Grid grid) 
	{
		super(filename, grid);
		
		// units are blind by default.
		viewRange = 0;
		
		// create a new field of view for the unit.
		fov = new GridFov(grid, this);
	}
	
	/**
	 * Returns the path to the given target if available.
	 * @param tgx the target grid x-coordinate.
	 * @param tgy the target grid y-coordinate.
	 * @param maxPathLength the maximum length of path.
	 * @return the path or null if unavailable.
	 */
	public GridPath getPath(int tgx, int tgy, int maxPathLength)
	{
		return grid.getPath(this.getGridX(), this.getGridY(), tgx, tgy, maxPathLength, this);
	}
	
	/**
	 * Actions to be taken when the unit moves.
	 * @param direction the direction to move.
	 */
	public void move(Directions direction)
	{
		if( direction==Directions.LEFT )
		{
			grid.moveUnit(-1, 0, this);
		}
		else if( direction==Directions.RIGHT )
		{
			grid.moveUnit(1, 0, this);
		}
		else if( direction==Directions.UP )
		{
			grid.moveUnit(0, -1, this);
		}
		else if( direction==Directions.DOWN )
		{
			grid.moveUnit(0, 1, this);
		}
		else
		{
			// stand still.
		}
		
		// let all listeners know that the unit has moved.
		markMoved();
	}
	
	public synchronized void markDead()
	{
		super.markDead();
		
		/*
		GridCell cell = grid.getCell(this.getGridX(), this.getGridY());
		
		if( cell!=null )
		{
			// TODO: create a bogus item to test "dropping" items.
		}
		*/
	}
	
	/**
	 * Marks the unit to have moved.
	 */
	public synchronized void markMoved()
	{
		// update the field of view.
		updateFov();
		
		// let all listeners know that the unit has moved.
		super.markMoved();
	}
	
	/**
	 * Updates the units field of view.
	 */
	public void updateFov()
	{
		// update the field of view.
		fov.update(this.getGridX(), this.getGridY(), this.getViewRange());
	}
	
	/**
	 * Returns whether the cell in the given coordinates
	 * is visible to the owner of the FoV.
	 * @param gx the grid x-coordinate.
	 * @param gy the grid y-coordinate.
	 * @return whether the cell in the coordinate is visible.
	 */
	public boolean isVisible(int gx, int gy)
	{
		return fov.isVisible(gx, gy);
	}
	
	/**
	 * Returns the grid cell this unit is standing on.
	 * @return the grid cell.
	 */
	public GridCell getCell()
	{
		return grid.getCell(this.getGridX(), this.getGridY());
	}
	
	/**
	 * Renders the grid unit.
	 * @param g the 2d graphics object.
	 */
	public void render(Graphics2D g)
	{
		super.render(g);
		
		if( GridWhack.DEBUG )
		{			
			if( fov!=null )
			{
				//fov.render(g);
			}
			
			if( path!=null )
			{
				path.render(g);
			}
		}
	}
	
	/**
	 * @return the unit view range in grid cells.
	 */
	public int getViewRange()
	{
		return viewRange;
	}
	
	/**
	 * @param viewRange the unit view range in grid cells.
	 */
	public void setViewRange(int viewRange)
	{
		this.viewRange = viewRange;
	}
}
