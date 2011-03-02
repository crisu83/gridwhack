package gridwhack.fov;

public abstract class Fov
{
	private int width;
	private int height;
	private int radius;
	protected boolean[][] visible;
	protected boolean[][] complete;

	public Fov(int width, int height, int radius)
	{
		this.width = width;
		this.height = height;
		this.radius = radius;

		// Empty the field of view.
		empty();
	}

	/**
	 * Empties the field of view.
	 */
	public void empty()
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

	public int getWidth()
	{
		return width;
	}

	public int getHeight()
	{
		return height;
	}

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
