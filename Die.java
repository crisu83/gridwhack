package gridwhack;

import java.util.Random;

/**
 * Die class file.
 * Allows for creating and rolling dice.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public class Die 
{
	private int sides;
	private Random rand;

	/**
	 * Creates the die.
	 * @param sides the amount of sides on this die.
	 * @param rand the random context.
	 */
	public Die(int sides, Random rand)
	{
		this.sides = sides;
		this.rand = rand;
	}

	/**
	 * Rolls the die.
	 * @return the result.
	 */
	public int roll()
	{
		return rand.nextInt(sides);
	}

	/**
	 * Rolls the die until the minimum has been rolled.
	 * @param min the minimum value.
	 * @return the result.
	 */
	public int roll(int min)
	{		
		int roll = 0;
		
		while( roll<min )
		{
			roll = roll();
		}
		
		return roll;
	}
}