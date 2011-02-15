package gridwhack.path;

import java.util.ArrayList;

public class Path 
{	
	protected ArrayList<Step> steps;
	
	/**
	 * Creates the path.
	 */
	public Path() 
	{
		steps = new ArrayList<Step>();
	}
	
	/**
	 * Returns the length of this path.
	 * @return the length.
	 */
	public int getLength()
	{
		return steps.size();
	}
	
	/**
	 * Returns a specific step.
	 * @param index the index of the step to get.
	 * @return the step.
	 */
	public Step getStep(int index)
	{
		return steps.get(index);
	}
	
	/**
	 * Return the x-coordinate of a specific step.
	 * @param index the index of the step.
	 * @return the x-coordinate.
	 */
	public int getX(int index)
	{
		return getStep(index).x;
	}
	
	/**
	 * Return the y-coordinate of a specific step.
	 * @param index the index of the step.
	 * @return the y-coordinate.
	 */
	public int getY(int index)
	{
		return getStep(index).y;
	}
	
	/**
	 * Appends a step to the path.
	 * @param x the x-coordinate of the step.
	 * @param y the y-coordinate of the step.
	 */
	public void appendStep(int x, int y)
	{
		steps.add(new Step(x, y));
	}
	
	/**
	 * Prepend a step to the path.
	 * @param x the x-coordinate of the step.
	 * @param y the y-coordinate of the step.
	 */
	public void prependStep(int x, int y)
	{
		steps.add(0, new Step(x, y));
	}
	
	/**
	 * @return whether the path contains the specified coordinates.
	 */
	public boolean containsStep(int x, int y)
	{
		return steps.contains(new Step(x, y));
	}
	
	/**
	 * Inner class representing a single step.
	 */
	public class Step
	{
		private int x;
		private int y;
		
		/**
		 * Constructs the step.
		 * @param x the x-coordinate of the step.
		 * @param y the y-coordinate of the step.
		 */
		public Step(int x, int y)
		{
			this.x = x;
			this.y = y;
		}
		
		/**
		 * @return the step x-coordinate.
		 */
		public int getX()
		{
			return x;
		}
		
		/**
		 * @return the step y-coordinate.
		 */
		public int getY()
		{
			return y;
		}
		
		/**
		 * Returns whether this step is the same as the given step.
		 * @param step the step to compare this step to.
		 * @return whether the steps are identical.
		 */
		public boolean equals(Step step)
		{
			return x==step.x && y==step.y;
		}
	}

}
