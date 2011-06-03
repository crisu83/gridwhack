package gridwhack.fov;

import gridwhack.base.BaseObject;

/**
 * Field of view class.
 * All field of views must be extended from this class.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public abstract class Fov extends BaseObject
{
	public static enum FovType {
		RAY_TRACING,
		SHADOW_CASTING,
	};

	// ----------
	// Properties
	// ----------

	private int width;
	private int height;
	private int radius;
	protected boolean[][] visible;
	protected boolean[][] complete;

	// -------
	// Methods
	// -------

	/**
	 * Creates the field of view.
	 * @param width The field width.
	 * @param height The field height.
	 * @param radius The field radius.
	 */
	public Fov(int width, int height, int radius)
	{
		this.width = width;
		this.height = height;
		this.radius = radius;

		init(); // empty the field of view
	}

	/**
	 * Empties the field of view.
	 */
	public void init()
	{
		visible = new boolean[width][height];
		complete = new boolean[width][height];
	}

	/**
	 * Returns whether a specific node is visible.
	 * @param x The x-coordinate of the node.
	 * @param y The y-coordinate of the node.
	 * @return Whether the node is visible.
	 */
	public boolean isVisible(int x, int y)
	{
		return visible[x][y];
	}

	// ----------------
	// Abstract methods
	// ----------------
	
	/**
	 * Refreshes the field of view.
	 * @param cx The current x-coordinate.
	 * @param cy The current y-coordinate.
	 */
	public abstract void refresh(int cx, int cy);

	// -------------------
	// Getters and setters
	// -------------------

	/**
	 * Returns a matrix representation of which nodes are visible
	 * taking into account the view range.
	 * @return The visible matrix.
	 *
	 */
	public boolean[][] getVisible()
	{
		return visible;
	}

	/**
	 * Returns a matrix representation of which cells are visible
	 * NOT taking into account the view range.
	 * @return The complete matrix.
	 */
	public boolean[][] getComplete()
	{
		return complete;
	}

	/**
	 * @return The width of this field of view.
	 */
	public int getWidth()
	{
		return width;
	}

	/**
	 * @return The height of this field of view.
	 */
	public int getHeight()
	{
		return height;
	}

	/**
	 * @return The radius of this field of view.
	 */
	public int getRadius()
	{
		return radius;
	}
}
