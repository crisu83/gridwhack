package gridwhack.grid;

import gridwhack.fov.IViewer;
import gridwhack.path.IMover;

/**
 * Grid unit class file.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public abstract class GridUnit extends GridEntity implements IMover, IViewer
{
	public static enum Directions { LEFT, RIGHT, UP, DOWN }
	
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

		// create a new field of view for the unit.
		fov = new GridFov(grid, this);
	}

	/**
	 * Updates the unit's field of view.
	 * @param range the view range.
	 */
	public void updateFov(int range)
	{
		fov.update(getGridX(), getGridY(), range);
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
	 * Returns the units field of view.
	 * @return the fov.
	 */
	public GridFov getFov()
	{
		return fov;
	}
}