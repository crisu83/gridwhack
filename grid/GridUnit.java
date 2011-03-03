package gridwhack.grid;

import gridwhack.exception.ComponentNotFoundException;
import gridwhack.fov.Fov;
import gridwhack.fov.FovFactory;
import gridwhack.fov.IViewer;
import gridwhack.path.IMover;

/**
 * Grid unit class file.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public abstract class GridUnit extends GridEntity implements IMover, IViewer
{
	public static enum Directions { LEFT, RIGHT, UP, DOWN }

	protected int viewRange = 0; // units are blind by default

	protected GridFov fov;
	protected GridPath path;
	
	/**
	 * Creates the unit.
	 * @param filename the sprite filename.
	 * @param grid the grid the unit exists on.
	 */
	public GridUnit(String filename, Grid grid)
	{
		super(filename, grid);
	}

	/**
	 * Initializes the unit.
	 */
	public void init()
	{
		try
		{
			// Create a new field of view for the unit.
			fov = FovFactory.factory(Fov.Type.RAY_TRACING, viewRange, grid, this);
		}
		catch( ComponentNotFoundException e )
		{
			System.out.println(e.getMessage());
			System.exit(1);
		}

		addListener(fov); // let the fov listen to this unit.
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
		return grid.getPath(getGridX(), getGridY(), tgx, tgy, maxPathLength, this);
	}

	/**
	 * Sets this unit view range.
	 * @param range the view range in grid cells.
	 */
	public void setViewRange(int range)
	{
		this.viewRange = range;
	}

	/**
	 * Returns the unit view range in grid cells.
	 * @return the view range.
	 */
	public int getViewRange()
	{
		return viewRange;
	}

	/**
	 * Returns the units field of view.
	 * @return the fov.
	 */
	public GridFov getFov()
	{
		return fov;
	}
}