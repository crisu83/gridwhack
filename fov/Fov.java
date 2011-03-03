package gridwhack.fov;

/**
 * Field of view class file.
 * All field of views must be extended from this class.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public abstract class Fov
{
	// Field of view types.
	public static enum Type {
		RAY_TRACING,
		SHADOW_CASTING,
	};

	private int width;
	private int height;
	private int radius;
	protected boolean[][] visible;
	protected boolean[][] complete;

	/**
	 * Creates the field of view.
	 * @param width the field width.
	 * @param height the field height.
	 * @param radius the field radius.
	 */
	public Fov(int width, int height, int radius)
	{
		this.width = width;
		this.height = height;
		this.radius = radius;

		init(); // Empty the field of view
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
	 * @param x the x-coordinate.
	 * @param y the y-coordinate.
	 * @return whether the node is visible.
	 */
	public boolean isVisible(int x, int y)
	{
		return visible[x][y];
	}

	/**
	 * Returns a matrix representation of which nodes are visible
	 * taking into account the view range.
	 * @return the visible matrix.
	 *
	 */
	public boolean[][] getVisible()
	{
		return visible;
	}

	/**
	 * Returns a matrix representation of which cells are visible
	 * NOT taking into account the view range.
	 * @return the complete matrix.
	 */
	public boolean[][] getComplete()
	{
		return complete;
	}

	/**
	 * Returns the width of this field of view.
	 * @return the width.
	 */
	public int getWidth()
	{
		return width;
	}

	/**
	 * Returns the height of this field of view.
	 * @return the height.
	 */
	public int getHeight()
	{
		return height;
	}

	/**
	 * Returns the radius of this field of view.
	 * @return the radius.
	 */
	public int getRadius()
	{
		return radius;
	}

	/**
	 * Updates the field of view.
	 * @param cx the current x-coordinate.
	 * @param cy the current y-coordinate.
	 */
	public abstract void update(int cx, int cy);
}
