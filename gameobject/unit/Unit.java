package gridwhack.gameobject.unit;

import gridwhack.exception.InvalidObjectException;
import gridwhack.fov.Fov.FovType;
import gridwhack.fov.FovFactory;
import gridwhack.fov.GridFov;
import gridwhack.fov.IViewer;
import gridwhack.gameobject.grid.Grid;
import gridwhack.gameobject.grid.GridGameObject;
import gridwhack.gameobject.grid.GridPath;
import gridwhack.path.IMover;
import gridwhack.util.Vector2;

/**
 * Unit class.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public abstract class Unit extends GridGameObject implements IMover, IViewer
{
	// ----------
	// Properties
	// ----------

	public static enum Directions { LEFT, RIGHT, UP, DOWN }

	protected int viewRange = 0; // units are blind by default
	protected GridFov fov;
	protected GridPath path;

	// -------
	// Methods
	// -------

	/**
	 * Creates the unit.
	 */
	public Unit()
	{
		super();
	}

	/**
	 * Initializes the unit.
	 */
	public void init()
	{
		try
		{
			// Create a new field of view for the unit.
			fov = FovFactory.getInstance().create(FovType.RAY_TRACING, viewRange, grid, this);
		}
		catch (InvalidObjectException e)
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}

		addListener(fov); // let the fov listen to this unit.
	}

	// -------------------
	// Getters and setters
	// -------------------

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