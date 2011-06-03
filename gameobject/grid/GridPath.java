package gridwhack.gameobject.grid;

import java.awt.Color;
import java.awt.Graphics2D;

import gridwhack.path.Path;

/**
 * Grid path class file.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class GridPath extends Path
{
	private Grid grid;
	private int currentIndex;
	
	/**
	 * Creates the path.
	 * @param grid the grid the path exists on.
	 */
	public GridPath(Grid grid)
	{
		super();
		
		this.grid = grid;

		currentIndex = 0; // path always starts at the beginning.
	}
	
	/**
	 * Returns the next step on this path.
	 * @return the step.
	 */
	public Step getNextStep()
	{
		currentIndex++;
		return getStep(currentIndex);
	}
	
	/**
	 * @return whether the path has a next step.
	 */
	public boolean hasNextStep()
	{
		return currentIndex<(getLength() - 1);
	}
	
	/**
	 * Draws this path (used for debug purposes).
	 * @param g The graphics context.
	 */
	public void draw(Graphics2D g)
	{
		int cellSize = grid.getCellSize();

		if (!steps.isEmpty())
		{
			for (Step step : steps)
			{
				g.setColor(Color.green);
				g.drawRect(step.getX()*cellSize, step.getY()*cellSize, cellSize, cellSize);
			}
		}
	}
}
