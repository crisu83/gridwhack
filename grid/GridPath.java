package gridwhack.grid;

import java.awt.Color;
import java.awt.Graphics2D;

import gridwhack.path.Path;

public class GridPath extends Path
{
	private int currentIndex;
	
	/**
	 * Constructs the path.
	 */
	public GridPath() 
	{
		super();
		
		// path always starts at the beginning.
		currentIndex = 0;
	}
	
	/**
	 * @return the next step on the path.
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
	 * Renders the path (used for debug purposes).
	 * @param g the 2d graphics object.
	 */
	public void render(Graphics2D g)
	{
		if( !steps.isEmpty() )
		{
			for( Step step : steps )
			{
				g.setColor(Color.green);
				g.drawRect(step.getX()*32, step.getY()*32, 32, 32);
			}
		}
	}
}
